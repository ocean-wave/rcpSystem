package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.constant.BusinessType;
import cn.com.cdboost.collect.constant.ChargeAppConstant;
import cn.com.cdboost.collect.dto.param.AccountOperateVo;
import cn.com.cdboost.collect.dto.param.CreateOrderParam;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.ChargingCard;
import cn.com.cdboost.collect.model.ChargingCst;
import cn.com.cdboost.collect.model.ChargingPay;
import cn.com.cdboost.collect.service.AlipayService;
import cn.com.cdboost.collect.service.ChargingCardService;
import cn.com.cdboost.collect.service.ChargingCstService;
import cn.com.cdboost.collect.service.ChargingPayService;
import cn.com.cdboost.collect.util.AccountUtil;
import cn.com.cdboost.collect.util.DateUtil;
import cn.com.cdboost.collect.util.MathUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeCreateRequest;
import com.alipay.api.response.AlipayTradeCreateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * 阿里支付相关服务接口
 */
@Service
public class AlipayServiceImpl implements AlipayService {
    private static final Logger logger = LoggerFactory.getLogger(AlipayServiceImpl.class);
    private static final String ALIPAY_NOTIFY_URL = "/appPay/aliPayCallback";
    private static final String SERVER_URL = "https://openapi.alipay.com/gateway.do";
    private static final String FORMAT = "json";
    private static final String CHARSET = "utf-8";
    private static final String SIGN_TYPE = "RSA2";

    @Autowired
    private ChargingPayService chargingPayService;
    @Autowired
    private ChargingCstService chargingCstService;
    @Autowired
    private ChargingCardService chargingCardService;

    @Value("${callback.url}")
    private String host;
    @Value("${alipay.appId}")
    private String alipayAppId;
    @Value("${alipay.app.private.key}")
    private String alipayAppPrivateKey;
    @Value("${alipay.public.key}")
    private String alipayPublicKey;

    @Override
    public ChargingPay order(CreateOrderParam orderParam) {
        // 生成支付订单
        ChargingPay chargingPay = new ChargingPay();
        BeanUtils.copyProperties(orderParam,chargingPay);
        chargingPay.setBuyCnt(orderParam.getChargingCnt());
        chargingPay.setWebcharNo(orderParam.getOpenNo());
        chargingPay.setCreateTime(new Date());
        chargingPay.setSerialNum(DateUtil.getSerialNum());
        // 充值后剩余金额，此处是先将充值前剩余金额赋值上，避免用户点击支付，然后又取消了，导致支付失败的订单上该字段为null
        // 充值成功后，在回调处还会更新此字段
        chargingPay.setAfterRemainAmount(orderParam.getRemainAmount());
        chargingPayService.insertSelective(chargingPay);
        return chargingPay;
    }

    @Override
    public String charge(ChargingPay chargingPay) {
        // 调用支付宝接口下单
        AlipayClient alipayClient = new DefaultAlipayClient(SERVER_URL, alipayAppId, alipayAppPrivateKey, FORMAT, CHARSET, alipayPublicKey, SIGN_TYPE); //获得初始化的AlipayClient
        AlipayTradeCreateRequest alipayRequest = new AlipayTradeCreateRequest();
        alipayRequest.setNotifyUrl(host + ALIPAY_NOTIFY_URL);//在公共参数中设置回调通知地址

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("out_trade_no",chargingPay.getPayFlag());
        jsonObject.put("total_amount", chargingPay.getPayMoney());
        jsonObject.put("subject","充电桩充电");
        jsonObject.put("buyer_id",chargingPay.getWebcharNo());
        alipayRequest.setBizContent(JSON.toJSONString(jsonObject));//填充业务参数
        String tradeNo = "";
        try {
            AlipayTradeCreateResponse response = alipayClient.execute(alipayRequest);
            if (response.isSuccess()) {
                tradeNo = response.getTradeNo();
                logger.info(tradeNo);
            } else {
                logger.info("支付宝下单接口调用失败response=" + JSON.toJSONString(response));
                throw new BusinessException("支付宝下单接口调用失败");
            }
        } catch (AlipayApiException e) {
            logger.error("创建订单异常",e);
        }

        return tradeNo;
    }

    @Override
    @Transactional
    public void orderNotify(String outTradeNo,String totalAmount,String sellerId,String appId,String tradeStatus) {
        ChargingPay chargingPay = chargingPayService.queryByPayFlag(outTradeNo);
        if (chargingPay == null) {
            // 非我方订单号，忽略
            return;
        }
        BigDecimal payMoney = chargingPay.getPayMoney();
        boolean flag = payMoney.equals(new BigDecimal(totalAmount));
        if (!flag) {
            // 与我方订单实际支付金额不等，忽略
            return;
        }

        // 校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）

        if (!alipayAppId.equals(appId)) {
            // 与我方appId不等，忽略
            return;
        }

        Integer payState = chargingPay.getPayState();
        if (payState == 1) {
            // 重复通知，忽略
            return;
        }

        Integer type = chargingPay.getType();

        //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
        if(tradeStatus.equals("TRADE_FINISHED")){
            //判断该笔订单是否在商户网站中已经做过处理
            //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
            //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
            //如果有做过处理，不执行商户的业务程序

            //注意：
            //如果签约的是可退款协议，退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            //如果没有签约可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
            if (ChargeAppConstant.PayScene.ICCARD_CHARGE.getType().equals(type)) {
                this.icCardPayOperate(chargingPay);
            } else {
                this.commonOperate(chargingPay);
            }
        } else if (tradeStatus.equals("TRADE_SUCCESS")){
            //判断该笔订单是否在商户网站中已经做过处理
            //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
            //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
            //如果有做过处理，不执行商户的业务程序

            //注意：
            //如果签约的是可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。

            if (ChargeAppConstant.PayScene.ICCARD_CHARGE.getType().equals(type)) {
                this.icCardPayOperate(chargingPay);
            } else {
                this.commonOperate(chargingPay);
            }
        }
    }

    /**
     * IC卡支付宝充值回调处理逻辑
     * @param chargingPay
     */
    @Override
    @Transactional
    public void icCardPayOperate(ChargingPay chargingPay) {
        // 更新订单状态
        ChargingPay updatePayParam = new ChargingPay();
        updatePayParam.setId(chargingPay.getId());
        updatePayParam.setUpdateTime(new Date());
        updatePayParam.setPayState(1);
        chargingPayService.updateByPrimaryKeySelective(updatePayParam);

        // IC卡账户充值
        String cardId = chargingPay.getCardId();
        ChargingCard card = chargingCardService.queryByCardIdForUpdate(cardId);
        ChargingCard param = new ChargingCard();
        param.setId(card.getId());
        BigDecimal totalAmount = card.getRemainAmount().add(chargingPay.getPayMoney());
        param.setRemainAmount(totalAmount);
        param.setUpdateTime(new Date());
        chargingCardService.updateByPrimaryKeySelective(param);
    }


    @Override
    @Transactional
    public void commonOperate(ChargingPay chargingPay) {
        ChargingCst chargingCst = chargingCstService.queryByCustomerGuid(chargingPay.getCustomerGuid());

        String payFlag = chargingPay.getPayFlag();
        Integer type = chargingPay.getType();
        if (ChargeAppConstant.PayScene.MONTH_CHARGE.getType().equals(type)) {
            BigDecimal accountDeductMoney = chargingPay.getAccountDeductMoney();
            boolean flag = MathUtil.isGreateThanZero(accountDeductMoney);
            BigDecimal amount = BigDecimal.ZERO;
            if (flag) {
                amount = accountDeductMoney;
            }
            AccountOperateVo operateVo = AccountUtil.getAccountOperateVo(BusinessType.MONTH_CARD_RECHARGE, amount);
            operateVo.setGuid(payFlag);
            operateVo.setAccountId(chargingCst.getId());
            operateVo.setChargeCnt(chargingPay.getBuyCnt());

            // 更新订单状态
            ChargingPay updatePayParam = new ChargingPay();
            updatePayParam.setId(chargingPay.getId());
            updatePayParam.setUpdateTime(new Date());
            updatePayParam.setPayState(1);

            // 月卡页面购买
            ChargingCst param = new ChargingCst();
            param.setId(chargingCst.getId());
            // 余额扣减
            if (flag) {
                BigDecimal sub = MathUtil.sub(chargingCst.getRemainAmount(), accountDeductMoney);
                param.setRemainAmount(sub);
                updatePayParam.setAfterRemainAmount(sub);
            }

            // 更新订单状态以及充值后剩余金额等
            chargingPayService.updateByPrimaryKeySelective(updatePayParam);

            // 设置月卡次数和有效期
            param.setRemainCnt(chargingPay.getBuyCnt());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date()); // 设置为当前时间
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1); // 设置为下一个月
            param.setExpireTime(calendar.getTime());
            param.setUpdateTime(new Date());
            chargingCstService.updateAccountNew(param,operateVo);
        } else if (ChargeAppConstant.PayScene.ACTIVITY_CHARGE.getType().equals(type)) {
            // 活动页面充值账户余额
            // 更新账户并记录流水
            AccountOperateVo operateVo = AccountUtil.getAccountOperateVo(BusinessType.ACTIVITY_RECHARGE, chargingPay.getAccountChargeMoney());
            operateVo.setGuid(payFlag);
            operateVo.setAccountId(chargingCst.getId());

            ChargingCst param = new ChargingCst();
            param.setId(chargingCst.getId());
            param.setUpdateTime(new Date());
            BigDecimal remainAmount = chargingCst.getRemainAmount().add(chargingPay.getAccountChargeMoney());
            param.setRemainAmount(remainAmount);
            chargingCstService.updateAccountNew(param,operateVo);

            // 更新订单状态
            ChargingPay updatePayParam = new ChargingPay();
            updatePayParam.setId(chargingPay.getId());
            updatePayParam.setUpdateTime(new Date());
            updatePayParam.setPayState(1);
            updatePayParam.setAfterRemainAmount(remainAmount);
            chargingPayService.updateByPrimaryKeySelective(updatePayParam);
        } else {
            // 充电时，第三方支付回调逻辑处理
            // 更新账户并记录流水
            AccountOperateVo operateVo = AccountUtil.getAccountOperateVo(BusinessType.CHARGE_ELECTRIC_RECHARGE, chargingPay.getAccountChargeMoney());
            operateVo.setGuid(payFlag);
            operateVo.setAccountId(chargingCst.getId());

            ChargingCst param = new ChargingCst();
            param.setId(chargingCst.getId());
            BigDecimal remainAmount = chargingCst.getRemainAmount().add(chargingPay.getAccountChargeMoney());
            param.setRemainAmount(MathUtil.setPrecision(remainAmount));
            param.setUpdateTime(new Date());
            chargingCstService.updateAccountNew(param,operateVo);

            // 更新订单状态
            ChargingPay updatePayParam = new ChargingPay();
            updatePayParam.setId(chargingPay.getId());
            updatePayParam.setUpdateTime(new Date());
            updatePayParam.setPayState(1);
            updatePayParam.setAfterRemainAmount(remainAmount);
            chargingPayService.updateByPrimaryKeySelective(updatePayParam);
        }
    }
}
