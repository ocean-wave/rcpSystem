package cn.com.cdboost.collect.impl;


import cn.com.cdboost.collect.constant.BusinessType;
import cn.com.cdboost.collect.constant.ChargeAppConstant;
import cn.com.cdboost.collect.constant.ChargeConstant;
import cn.com.cdboost.collect.dao.ChargingDeductMapper;
import cn.com.cdboost.collect.dao.ChargingPayMapper;
import cn.com.cdboost.collect.dao.ChargingUseDetailedMapper;
import cn.com.cdboost.collect.dto.chargerApp.Ajax;
import cn.com.cdboost.collect.dto.param.AccountOperateVo;
import cn.com.cdboost.collect.dto.param.CreateOrderParam;
import cn.com.cdboost.collect.dto.param.WxTransfers;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.lock.Lock;
import cn.com.cdboost.collect.model.*;
import cn.com.cdboost.collect.service.*;
import cn.com.cdboost.collect.service.util.XMLUtil;
import cn.com.cdboost.collect.util.*;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransOrderQueryRequest;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.response.AlipayFundTransOrderQueryResponse;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WxChargerPayImpl implements WxChargerPayService {
    private static final Logger logger = LoggerFactory.getLogger(WxChargerPayImpl.class);
    // 支付宝回调地址
    private static final String ALIPAY_NOTIFY_URL = "/appPay/aliPayCallback";
    // 微信回调地址
    private static final String WECHAT_NOTIFY_URL = "/back/notify";
    private static final String CHARSET = "utf-8";

    @Autowired
    ChargingPayMapper chargingPayMapper;
    @Autowired
    ChargingUseDetailedMapper chargingUseDetailedMapper;
    @Autowired
    ChargingDeductMapper chargingDeductMapper;
    @Autowired
    private ChargingDeviceService chargingDeviceService;
    @Autowired
    private ChargingPayChemeService chargingPayChemeService;
    @Autowired
    private ChargingCstService chargingCstService;
    @Autowired
    private ChargingPayService chargingPayService;
    @Autowired
    private ChargingProjectService chargingProjectService;
    @Autowired
    private ChargingWithdrawCashService chargingWithdrawCashService;
    @Autowired
    private ChargingUseDetailedService chargingUseDetailedService;
    @Autowired
    private AlipayService alipayService;
    @Autowired
    private ChargingMonthCardAcctService monthCardAcctService;
    @Autowired
    private ChargingAccountFlowService accountFlowService;
    @Autowired
    private ChargingSpiltAccountService spiltAccountService;
    @Autowired
    private XMLUtil xmlUtil;

    @Value("${callback.url}")
    private String host;
    @Value("${appId}")
    private String appId;
    @Value("${mchId}")
    private String mchId;
    @Value("${partnerkey}")
    private String partnerkey;
    @Value("${secret}")
    private String appSecret;

    @Value("${alipay.appId}")
    private String alipayAppId;
    @Value("${alipay.app.private.key}")
    private String alipayAppPrivateKey;
    @Value("${alipay.public.key}")
    private String alipayPublicKey;

    /**
     * 统一下单地址
     */
    private final String WX_UNIFIEDORDER_URL="https://api.mch.weixin.qq.com/pay/unifiedorder";
    @Override
    public Ajax pay(Double pay, String ip, ChargingPayCheme chargingPayCheme, String customerGuid, String openid, HttpServletRequest request) {

        //测试，暂时写死
        String appid = "wx7f484e7b57ce4a98";
        String appsecret = "e47ceafba85961d7a2e5f45d318a5a8e";
        String mch_id = "1485651082";
        //这个参数partnerkey是在商户后台配置的一个32位的key,微信商户平台-账户设置-安全设置-api安全
        String partnerkey = "HYwu7NzyaDZ8axVYozseVqhwSP5Z3eEU";

        Ajax ajax = new Ajax();
        String orderSn=UUID.randomUUID().toString().replace("-","");
        int penny = (int)(pay*100);
        String body = "chargeApp";
        String spbill_create_ip = request.getRemoteAddr();
        System.out.println("客户端-------------------ip="+ip);

        //实例化用户充值记录对象
        ChargingPay chargingPay = new ChargingPay();
        //设置客户唯一标识
        chargingPay.setCustomerGuid(customerGuid);
        //设置用户充值金额
        if(chargingPayCheme.getPayCategory()==4){
            chargingPay.setPayMoney(BigDecimal.valueOf(chargingPayCheme.getMoney().floatValue()));
        }else{
            chargingPay.setPayMoney(BigDecimal.valueOf(pay));
        }
        //设置用户充值类型
        chargingPay.setPayCategory(chargingPayCheme.getPayCategory());
        //设置用户购买次数
        chargingPay.setBuyCnt(chargingPayCheme.getChargingCnt());
        //设置用户包月数
        //if(chargingPayCheme.getPayCategory()==2)
          //chargingPay.setNumMonths(chargingPayCheme.get);
        //设置用户openId
        chargingPay.setWebcharNo(openid);
        //设置更新时间
        chargingPay.setCreateTime(new Timestamp(System.currentTimeMillis()));
        //设置充值标志
        chargingPay.setPayFlag(orderSn);
        //设置充值状态，待支付
        chargingPay.setPayState(0);
        //设置充值流水号
        chargingPay.setSerialNum(DateUtil.getSerialNum());
        try {
            //将生成的待支付订单插入数据库
            int pay_db_success = chargingPayMapper.insertSelective(chargingPay);
            String nonceStr = StringUtil.getNonceStr();
            //判断是否添加成功
            if(pay_db_success!=0)
            {
                //加密，这里只列举必填字段
                Map<String, String> map = new HashMap<String, String>();
                map.put("body", body);//商品描述
                map.put("mch_id", mch_id);//商户平台id
                map.put("appid", appid);//公众号id
                map.put("nonce_str", nonceStr);//随机字符串
                map.put("notify_url","http://cdz.cdboost.cn/back/notify");//异步回调api
                map.put("spbill_create_ip",ip );//支付ip
                map.put("out_trade_no", orderSn);//商品订单号
                map.put("total_fee", penny+"");//真实金额
                map.put("trade_type", "JSAPI");//JSAPI、h5调用
                map.put("openid", openid);//支付用户openid

                String sign = WxPaySignatureUtils.signature(map,partnerkey);

                map.put("sign", sign);
                String xml = XMLBeanUtils.map2XmlString(map);

                System.out.println("发送给微信的报文："+xml);

                String response =  WxHttpUtil.sendPost(WX_UNIFIEDORDER_URL, xml, "utf-8");
                System.out.println("收到微信的报文(下单接口后返回数据)："+response);

                Map<String, String> map_unifiedorder =  XMLBeanUtils.readStringXmlOut(response);

                //验证是否统一下单成功
                if(map_unifiedorder.get("return_code").equals("SUCCESS") && map_unifiedorder.get("result_code").equals("SUCCESS") )
                {
                    //获取关键prepay_id数据进行二次签名
                    String prepay_id = map_unifiedorder.get("prepay_id");
                    System.out.println("prepay_id="+prepay_id);

                    HashMap<String, String> back = new HashMap<String, String>();
                    String timestamp = Sha1Util.getTimeStamp();
                    String tempNonceStr = StringUtil.getNonceStr();
                    back.put("appId", appid);
                    back.put("timeStamp", timestamp);
                    back.put("nonceStr", tempNonceStr);
                    back.put("package", "prepay_id=" + prepay_id);
                    back.put("signType", "MD5");
                    //二次签名后返回给前端的签名证书字符串
                    String sign2 = WxPaySignatureUtils.signature(back, partnerkey);
                    Map<String,String> params = new HashMap<>();

                    params.put("appId", appid);
                    params.put("timeStamp", timestamp);
                    params.put("nonceStr", tempNonceStr);
                    params.put("package", "prepay_id=" + prepay_id);
                    params.put("signType", "MD5");
                    params.put("paySign", sign2);
                    ajax.setStatus(1);
                    ajax.setMessage("下单成功等待微信端支付！");
                    ajax.setParams(params);
                    return ajax;
                }
                else
                {
                    ajax.setStatus(0);
                    ajax.setMessage(map_unifiedorder.get("return_msg"));
                    return ajax;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ajax.setMessage(AjaxMessage.operateSuccess);
        ajax.setStatus(1);
        return ajax;
    }

    @Override
    @Transactional
    public void notify(String outTradeNo,String totalFee) {
        logger.info("进入微信通知处理业务");
        ChargingPay chargingPay = chargingPayService.queryByPayFlag(outTradeNo);
        if (chargingPay == null) {
            // 非我方订单号，忽略
            return;
        }

        BigDecimal payMoney = chargingPay.getPayMoney();
        // 微信支付金额单位时分
        String temp = MathUtil.fen2yuan(totalFee);
        boolean flag = payMoney.equals(new BigDecimal(temp));
        if (!flag) {
            // 与我方订单实际支付金额不等，忽略
            return;
        }

        // TODO 查询微信appid是否有返回
//        if (!appId.equals("")) {
//            // 与我方appId不等，忽略
//            return;
//        }

        if (chargingPay.getPayState() == 1) {
            // 重复通知，忽略
            return;
        }

        Integer type = chargingPay.getType();
        if (ChargeAppConstant.PayScene.ICCARD_CHARGE.getType().equals(type)) {
            alipayService.icCardPayOperate(chargingPay);
        } else {
            alipayService.commonOperate(chargingPay);
        }

    }

    @Override
    @Transactional
    public void deductions(ChargingDevice chargingDevice,String chargingGuid, Integer openMeans,String openId,String priceId) {
        //获取价格方案
        ChargingPayCheme chargingPayCheme = chargingPayChemeService.selectByPrimaryKey(Integer.parseInt(priceId));

        // 客户信息
        ChargingCst chargingCst;
        if (ChargeAppConstant.OpenMeansConstant.WECHAT.getType().equals(openMeans)) {
            chargingCst = chargingCstService.queryByOpenId(openId);
        } else {
            chargingCst = chargingCstService.queryByAlipayUserId(openId);
        }

        Integer payCategory = chargingPayCheme.getPayCategory();
        if(ChargeConstant.SchemePayCategory.TEMPORARY_RECHARGE.getType().equals(payCategory) || ChargeConstant.SchemePayCategory.RECHARGE_FULL.getType().equals(payCategory)){
            //临时用户，则扣除相应金额
            BusinessType businessType;
            if (ChargeConstant.SchemePayCategory.TEMPORARY_RECHARGE.getType().equals(payCategory) ) {
                businessType = BusinessType.TEMPORARY_CHARGE_SUCCESS;
            } else {
                businessType = BusinessType.FULL_CHARGE_SUCCESS;
            }

            AccountOperateVo operateVo = AccountUtil.getAccountOperateVo(businessType, chargingPayCheme.getMoney(),null);
            operateVo.setGuid(chargingGuid);
            operateVo.setAccountId(chargingCst.getId());

            // 扣除用户账户余额
            ChargingCst updateParam = new ChargingCst();
            updateParam.setId(chargingCst.getId());
            BigDecimal remainAmount = chargingCst.getRemainAmount().subtract(chargingPayCheme.getMoney());
            updateParam.setRemainAmount(remainAmount);
            updateParam.setUpdateTime(new Date());
            chargingCstService.updateAccountNew(updateParam,operateVo);

            // 维护充电桩使用记录表
            this.addUseDetailedRecord4TempUser(chargingCst,chargingDevice,chargingPayCheme,openMeans,openId,chargingGuid,remainAmount);

            // 维护客户扣费记录表
            String remark = "临时用户充电扣费";
            this.addChargingDeductRecord4TempUser(chargingCst,chargingPayCheme,chargingGuid,remainAmount,remark);
        } else if(ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getType().equals(payCategory)){
            //包月用户扣除次数
            ChargingMonthCardAcct cardAcct = monthCardAcctService.queryByParams(chargingCst.getCustomerGuid(), chargingPayCheme.getSchemeGuid());

            // 月卡次数扣减，并记录流水
            AccountOperateVo operateVo = AccountUtil.getAccountOperateVo(BusinessType.MONTH_CARD_CHARGE_SUCCESS, null,1);
            operateVo.setGuid(chargingGuid);
            operateVo.setMonthAccountId(cardAcct.getId());
            ChargingMonthCardAcct acctParam = new ChargingMonthCardAcct();
            acctParam.setId(cardAcct.getId());
            Integer remainCnt = cardAcct.getRemainCnt() - 1;
            acctParam.setRemainCnt(remainCnt);
            acctParam.setUpdateTime(new Date());
            monthCardAcctService.updateAccount(acctParam,operateVo);

            // 维护充电桩使用记录表
            this.addUseDetailedRecord4MonthUser(chargingCst,chargingDevice,chargingPayCheme,openMeans,openId,chargingGuid,remainCnt);

            // 维护客户扣费记录表
            String remark = "包月用户充电扣减次数";
            this.addChargingDeductRecord4MonthUser(chargingCst,chargingGuid,remainCnt,remark);
        }
    }

    @Override
    public void rechargeDeductions(ChargingUseDetailed useDetailed) {
        String chargingGuid = useDetailed.getChargingGuid();
        // 查询用户账户信息
        ChargingCst chargingCst = chargingCstService.queryByCustomerGuid(useDetailed.getCustomerGuid());

        // 查询价格方案
        ChargingPayCheme chargingPayCheme = chargingPayChemeService.queryBySchemeGuid(useDetailed.getSchemeGuid());
        Integer payCategory = chargingPayCheme.getPayCategory();
        if(ChargeConstant.SchemePayCategory.TEMPORARY_RECHARGE.getType().equals(payCategory) || ChargeConstant.SchemePayCategory.RECHARGE_FULL.getType().equals(payCategory)){
            //临时用户，则扣除相应金额
            BusinessType businessType;
            if (ChargeConstant.SchemePayCategory.TEMPORARY_RECHARGE.getType().equals(payCategory) ) {
                businessType = BusinessType.TEMPORARY_RE_CHARGE_SUCCESS;
            } else {
                businessType = BusinessType.FULL_RE_CHARGE_SUCCESS;
            }

            AccountOperateVo operateVo = AccountUtil.getAccountOperateVo(businessType, chargingPayCheme.getMoney(),null);
            operateVo.setGuid(chargingGuid);
            operateVo.setAccountId(chargingCst.getId());

            // 扣除用户账户余额
            ChargingCst updateParam = new ChargingCst();
            updateParam.setId(chargingCst.getId());
            BigDecimal remainAmount = chargingCst.getRemainAmount().subtract(chargingPayCheme.getMoney());
            updateParam.setRemainAmount(remainAmount);
            updateParam.setUpdateTime(new Date());
            chargingCstService.updateAccountNew(updateParam,operateVo);

            // 更新充电桩使用记录表
            ChargingUseDetailed param = new ChargingUseDetailed();
            param.setId(useDetailed.getId());
            Integer chargingTime = useDetailed.getChargingTime() + chargingPayCheme.getChargingTime();
            param.setChargingTime(chargingTime);
//            Integer chargingPower = useDetailed.getChargingPower() * 2;
//            param.setChargingPower(chargingPower);
            BigDecimal totalDeductMoney = useDetailed.getDeductMoney().add(chargingPayCheme.getMoney());
            param.setDeductMoney(totalDeductMoney);
            param.setAfterRemainAmount(remainAmount);
            param.setRechargeCount(useDetailed.getRechargeCount() + 1);
            chargingUseDetailedService.updateByPrimaryKeySelective(param);

            // 维护客户扣费记录表
            String remark = "临时用户续充电扣费";
            this.addChargingDeductRecord4TempUser(chargingCst,chargingPayCheme,chargingGuid,remainAmount,remark);
        } else if(ChargeConstant.SchemePayCategory.MONTH_RECHARGE.getType().equals(payCategory)){
            //包月用户扣除次数
            ChargingMonthCardAcct cardAcct = monthCardAcctService.queryByParams(useDetailed.getCustomerGuid(), useDetailed.getSchemeGuid());

            // 月卡次数扣减，并记录流水
            AccountOperateVo operateVo = AccountUtil.getAccountOperateVo(BusinessType.MONTH_CARD_RE_CHARGE_SUCCESS, null,1);
            operateVo.setGuid(chargingGuid);
            operateVo.setMonthAccountId(cardAcct.getId());
            ChargingMonthCardAcct acctParam = new ChargingMonthCardAcct();
            acctParam.setId(cardAcct.getId());
            int remainCnt = cardAcct.getRemainCnt() - 1;
            acctParam.setRemainCnt(remainCnt);
            acctParam.setUpdateTime(new Date());
            monthCardAcctService.updateAccount(acctParam,operateVo);

            // 更新充电桩使用记录表
            ChargingUseDetailed param = new ChargingUseDetailed();
            param.setId(useDetailed.getId());
            Integer chargingTime = useDetailed.getChargingTime() + chargingPayCheme.getChargingTime();
            param.setChargingTime(chargingTime);
//            Integer chargingPower = useDetailed.getChargingPower() * 2;
//            param.setChargingPower(chargingPower);
            BigDecimal amount = MathUtil.divide(chargingPayCheme.getMoney(), new BigDecimal(chargingPayCheme.getChargingCnt()));
            BigDecimal totalDeductMoney = useDetailed.getDeductMoney().add(amount);
            param.setDeductMoney(totalDeductMoney);
            param.setDeductCnt(useDetailed.getDeductCnt() + 1);
            param.setAfterRemainCnt(remainCnt);
            param.setRechargeCount(useDetailed.getRechargeCount() + 1);
            chargingUseDetailedService.updateByPrimaryKeySelective(param);

            // 维护客户扣费记录表
            String remark = "包月用户续充电扣减次数";
            this.addChargingDeductRecord4MonthUser(chargingCst,chargingGuid,remainCnt,remark);
        }
    }

    /**
     * 用户购买月卡余额扣减
     * @param chargingCst
     * @param chargingPayCheme
     */
    @Override
    @Transactional
    public void deductionsOfMonth(ChargingCst chargingCst, ChargingPayCheme chargingPayCheme,CreateOrderParam orderParam) {
        //月卡购买，用户扣减余额
        AccountOperateVo operateVo = AccountUtil.getAccountOperateVo(BusinessType.MONTH_CARD_RECHARGE_DEDUCT_AMOUNT, chargingPayCheme.getMoney(),null);
        operateVo.setGuid(orderParam.getPayFlag());
        operateVo.setAccountId(chargingCst.getId());
        BigDecimal remainAmount = chargingCst.getRemainAmount().subtract(chargingPayCheme.getMoney());
        ChargingCst param = new ChargingCst();
        param.setId(chargingCst.getId());
        param.setRemainAmount(MathUtil.setPrecision(remainAmount));
        param.setUpdateTime(new Date());
        chargingCstService.updateAccountNew(param,operateVo);

        // 纯余额支付购买月卡
        ChargingPay order = alipayService.order(orderParam);

        //用户包月过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date()); // 设置为当前时间
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1); // 设置为下一个月
        Date expired = calendar.getTime();

        // 查询用户月卡账户是否存在
        ChargingMonthCardAcct cardAcct = monthCardAcctService.queryByParams(chargingCst.getCustomerGuid(), chargingPayCheme.getSchemeGuid());
        if (cardAcct != null) {
            // 更新月卡信息，并记录流水
            AccountOperateVo operateVo2 = AccountUtil.getAccountOperateVo(BusinessType.MONTH_CARD_RECHARGE_REMAIN_CNT, null,order.getBuyCnt());
            operateVo2.setGuid(order.getPayFlag());
            operateVo2.setMonthAccountId(cardAcct.getId());
            ChargingMonthCardAcct acctParam = new ChargingMonthCardAcct();
            acctParam.setId(cardAcct.getId());
            acctParam.setPayCnt(cardAcct.getPayCnt() + 1);
            // 月卡剩余次数可能欠费，充值月卡时需要扣除之前欠费次数
            int remainCnt = order.getBuyCnt() + cardAcct.getRemainCnt();
            acctParam.setRemainCnt(remainCnt);
            acctParam.setExpireTime(expired);
            acctParam.setUpdateTime(new Date());
            monthCardAcctService.updateAccount(acctParam,operateVo2);
        } else {
            ChargingMonthCardAcct acctParam = new ChargingMonthCardAcct();
            acctParam.setCustomerGuid(chargingCst.getCustomerGuid());
            acctParam.setPayCnt(1);
            acctParam.setRemainCnt(order.getBuyCnt());
            acctParam.setExpireTime(expired);
            acctParam.setPower(chargingPayCheme.getPower());
            acctParam.setMinPower(chargingPayCheme.getMinPower());
            acctParam.setMaxPower(chargingPayCheme.getMaxPower());
            acctParam.setCreateTime(new Date());
            acctParam.setUpdateTime(new Date());
            acctParam.setSchemeGuid(chargingPayCheme.getSchemeGuid());
            acctParam.setProjectGuid(chargingPayCheme.getProjectGuid());
            monthCardAcctService.insertSelective(acctParam);

            // 记录月卡账户流水
            ChargingAccountFlow flow = new ChargingAccountFlow();
            flow.setAccountId(0);
            flow.setMonthAccountId(acctParam.getId());
            flow.setChargeCnt(order.getBuyCnt());
            flow.setBusinessType(BusinessType.MONTH_CARD_RECHARGE_REMAIN_CNT.getType());
            flow.setGuid(order.getPayFlag());
            flow.setCreateTime(new Date());
            flow.setRemark(BusinessType.MONTH_CARD_RECHARGE_REMAIN_CNT.getDesc());
            accountFlowService.insertSelective(flow);
        }
    }

    /**
     * 包月用户，新增客户扣费记录表
     * @param chargingCst
     * @param chargingGuid
     * @param deductRemainCnt
     */
    private void addChargingDeductRecord4MonthUser(ChargingCst chargingCst, String chargingGuid, Integer deductRemainCnt,String remark) {
        ChargingDeduct chargingDeduct = new ChargingDeduct();
        chargingDeduct.setCustomerGuid(chargingCst.getCustomerGuid());
        chargingDeduct.setChargingGuid(chargingGuid);
        chargingDeduct.setDeductCnt(1);
        chargingDeduct.setDeductRemainCnt(deductRemainCnt);
        // 扣费前剩余金额
        chargingDeduct.setDeductBefore(chargingCst.getRemainAmount());
        // 扣费后剩余金额
        chargingDeduct.setRemainAmount(chargingCst.getRemainAmount());
        chargingDeduct.setRemark(remark);
        chargingDeduct.setCreateTime(new Date());
        chargingDeduct.setSerialNum(DateUtil.getSerialNum());
        chargingDeductMapper.insertSelective(chargingDeduct);
    }

    /**
     * 临时用户，新增客户扣费记录表
     * @param chargingCst
     * @param chargingPayCheme
     * @param chargingGuid
     * @param afterRemainAmount
     */
    private void addChargingDeductRecord4TempUser(ChargingCst chargingCst, ChargingPayCheme chargingPayCheme, String chargingGuid, BigDecimal afterRemainAmount,String remark) {
        ChargingDeduct chargingDeduct = new ChargingDeduct();
        chargingDeduct.setCustomerGuid(chargingCst.getCustomerGuid());
        chargingDeduct.setChargingGuid(chargingGuid);
        chargingDeduct.setDeductMoney(chargingPayCheme.getMoney());
        // 扣费后剩余金额
        chargingDeduct.setRemainAmount(afterRemainAmount);
        // 扣费前剩余金额
        chargingDeduct.setDeductBefore(chargingCst.getRemainAmount());
        chargingDeduct.setCreateTime(new Date());
        chargingDeduct.setRemark(remark);
        chargingDeduct.setSerialNum(DateUtil.getSerialNum());
        chargingDeductMapper.insertSelective(chargingDeduct);
    }

    /**
     * 临时用户，新增充值记录明细表
     * @param chargingCst
     * @param chargingDevice
     * @param chargingPayCheme
     * @param openId
     */
    private void addUseDetailedRecord4TempUser(ChargingCst chargingCst, ChargingDevice chargingDevice, ChargingPayCheme chargingPayCheme, Integer openMeans,String openId, String chargingGuid, BigDecimal afterRemainAmount) {
        // 查询充电桩电价
        ChargingProject chargingProject = chargingProjectService.queryByProjectGuid(chargingDevice.getProjectGuid());

        // 新增充电使用记录表
        ChargingUseDetailed detailed = new ChargingUseDetailed();
        detailed.setCustomerGuid(chargingCst.getCustomerGuid());
        detailed.setChargingPlieGuid(chargingDevice.getChargingPlieGuid());

        Integer payCategory = chargingPayCheme.getPayCategory();
        detailed.setPayCategory(payCategory);
        detailed.setSchemeGuid(chargingPayCheme.getSchemeGuid());
        if (ChargeConstant.SchemePayCategory.TEMPORARY_RECHARGE.getType().equals(payCategory)) {
            // 临时充电
            detailed.setChargingWay(1);
        } else if (ChargeConstant.SchemePayCategory.RECHARGE_FULL.getType().equals(payCategory)) {
            // 一次充满
            detailed.setChargingWay(3);
        }

        // 充电扣除费用
        detailed.setDeductMoney(chargingPayCheme.getMoney());
        // 本次充电后剩余金额
        detailed.setAfterRemainAmount(afterRemainAmount);
        detailed.setPrice(chargingProject.getPrice());
        detailed.setStartTime(new Date());
        detailed.setState(0);
        //设置充电时间
        detailed.setChargingTime(chargingPayCheme.getChargingTime());
        detailed.setChargingGuid(chargingGuid);
        detailed.setCreateTime(new Date());
        detailed.setOpenMeans(openMeans);
        detailed.setOpenNo(openId);
        chargingUseDetailedMapper.insertSelective(detailed);
    }


    /**
     * 包月用户，新增充值记录明细表
     * @param chargingCst
     * @param chargingDevice
     * @param chargingPayCheme
     * @param openId
     */
    private void addUseDetailedRecord4MonthUser(ChargingCst chargingCst, ChargingDevice chargingDevice, ChargingPayCheme chargingPayCheme, Integer openMeans,String openId, String chargingGuid, Integer afterRemainCnt) {
        // 查询充电桩电价
        ChargingProject chargingProject = chargingProjectService.queryByProjectGuid(chargingDevice.getProjectGuid());

        // 新增充电使用记录表
        ChargingUseDetailed detailed = new ChargingUseDetailed();
        detailed.setCustomerGuid(chargingCst.getCustomerGuid());
        detailed.setChargingPlieGuid(chargingDevice.getChargingPlieGuid());
        detailed.setPayCategory(chargingPayCheme.getPayCategory());
        detailed.setSchemeGuid(chargingPayCheme.getSchemeGuid());
        // 包月用户，每次均是充满断电
        detailed.setChargingWay(3);
        detailed.setAfterRemainCnt(afterRemainCnt);

        // 充电扣除费用
        BigDecimal deductMoney = MathUtil.divide(chargingPayCheme.getMoney(), new BigDecimal(chargingPayCheme.getChargingCnt()));
        detailed.setDeductMoney(MathUtil.setPrecision(deductMoney));
        detailed.setDeductCnt(1);
        detailed.setPrice(chargingProject.getPrice());
        detailed.setStartTime(new Date());
        detailed.setState(0);
        detailed.setChargingTime(chargingPayCheme.getChargingTime());
        detailed.setChargingGuid(chargingGuid);
        detailed.setCreateTime(new Date());
        detailed.setOpenMeans(openMeans);
        detailed.setOpenNo(openId);
        chargingUseDetailedMapper.insertSelective(detailed);
    }

    @Override
    public Ajax chargePay(Integer type,BigDecimal deductAmount,BigDecimal pay, String ip, ChargingPayCheme chargingPayCheme, String customerGuid, String openid) {
        String notifyUrl = host + WECHAT_NOTIFY_URL;

        // 总金额以分为单位，不带小数点
        String payMoney = MathUtil.yuan2Fen(String.valueOf(pay));
        String body = "充电桩充电";

        String trade_type = "JSAPI";
        // 随机字符串
        String nonce_str = StringUtil.getNonceStr();
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

        String time_start = format.format(new Date());
        // 商户订单号
        String out_trade_no = UuidUtil.getUuid();

        //String body = "微信测试支付";
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", appId);
        packageParams.put("body", body);
        packageParams.put("openid",openid);
        packageParams.put("time_start",time_start);
        packageParams.put("mch_id", mchId);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("notify_url", notifyUrl);
        packageParams.put("out_trade_no", out_trade_no);
        packageParams.put("spbill_create_ip", ip);
        packageParams.put("total_fee", payMoney);
        packageParams.put("trade_type", trade_type);
        packageParams.put("sign_type", "MD5");

        RequestHandler reqHandler = new RequestHandler(null, null);
        reqHandler.init(appId,appSecret,partnerkey);

        String sign = reqHandler.createSign(packageParams);
        String xml = "<xml>"
                + "<appid>" + appId + "</appid>"
                + "<mch_id>"+ mchId + "</mch_id>"
                + "<nonce_str>" + nonce_str+ "</nonce_str>"
                + "<body><![CDATA[" + body + "]]></body>"
                + "<time_start>" + time_start + "</time_start>"
                + "<out_trade_no>" + out_trade_no+ "</out_trade_no>"
                + "<total_fee>" + payMoney + "</total_fee>"
                + "<spbill_create_ip>" + ip+ "</spbill_create_ip>"
                + "<notify_url>" + notifyUrl+ "</notify_url>"
                + "<openid>" + openid + "</openid>"
                + "<trade_type>" + trade_type+ "</trade_type>"
                + "<sign>" + sign + "</sign>"
                + "<sign_type>MD5</sign_type>"
                + "</xml>";
        logger.info("weixin"+"-xml:"+xml);
        String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

        String prepayId = new GetWxOrderno().getPayNo(createOrderURL, xml);
        // 预订单生成后，本地保存相应的订单号

        if(!StringUtils.isEmpty(prepayId)){
            // 查询充值前账户余额
            ChargingCst chargingCst = chargingCstService.queryByOpenId(openid);

            //实例化用户充值记录对象
            ChargingPay chargingPay = new ChargingPay();
            chargingPay.setCustomerGuid(customerGuid);
            //设置用户充值金额,单位元
            Integer payCategory = chargingPayCheme.getPayCategory();
            if(ChargeConstant.SchemePayCategory.BALANCE_RECHARGE.getType().equals(payCategory)){
                chargingPay.setPayMoney(chargingPayCheme.getPayMoney());
                chargingPay.setAccountChargeMoney(chargingPayCheme.getMoney());
            } else {
                chargingPay.setPayMoney(pay);
                chargingPay.setAccountChargeMoney(pay);
            }
            chargingPay.setSchemeGuid(chargingPayCheme.getSchemeGuid());
            chargingPay.setAccountDeductMoney(deductAmount);
            chargingPay.setChargingTime(chargingPayCheme.getChargingTime());
            chargingPay.setPayCategory(chargingPayCheme.getPayCategory());
            chargingPay.setBuyCnt(chargingPayCheme.getChargingCnt());
            chargingPay.setWebcharNo(openid);
            chargingPay.setCreateTime(new Date());
            chargingPay.setPayFlag(out_trade_no);
            chargingPay.setPayState(0);
            chargingPay.setSerialNum(DateUtil.getSerialNum());
            // 充值后剩余金额，此处是先将充值前剩余金额赋值上，避免用户点击支付，然后又取消了，导致支付失败的订单上该字段为null
            // 充值成功后，在回调处还会更新此字段
            chargingPay.setAfterRemainAmount(chargingCst.getRemainAmount());
            chargingPay.setType(type);
            chargingPayMapper.insertSelective(chargingPay);
        }


        //获取prepay_id后，拼接最后请求支付所需要的package
        SortedMap<String, String> finalpackage = new TreeMap<String, String>();
        String timestamp = Sha1Util.getTimeStamp();
        nonce_str = StringUtil.getNonceStr();
        finalpackage.put("appId", appId);
        finalpackage.put("timeStamp", timestamp);
        finalpackage.put("nonceStr", nonce_str);
        finalpackage.put("package", "prepay_id=" + prepayId);
        finalpackage.put("signType", "MD5");
        //要签名
        String finalsign = reqHandler.createSign(finalpackage);

        Map<String,String> params = new HashMap<>();
        params.put("appId", appId);
        params.put("timeStamp", timestamp);
        params.put("nonceStr", nonce_str);
        params.put("package", "prepay_id=" + prepayId);
        params.put("signType", "MD5");
        params.put("paySign", finalsign);

        Ajax ajax = new Ajax();
        ajax.setStatus(1);
        ajax.setMessage("下单成功等待微信端支付！");
        ajax.setParams(params);
        return ajax;
    }

    @Override
    public Map<String,String> weChatPrePay(String payMoney,String outTradeNo,String openId,String ip) {
        String notifyUrl = host + WECHAT_NOTIFY_URL;
        String body = "充电桩充电";

        String trade_type = "JSAPI";
        // 随机字符串
        String nonce_str = StringUtil.getNonceStr();
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String time_start = format.format(new Date());
        //String body = "微信测试支付";
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", appId);
        packageParams.put("body", body);
        packageParams.put("openid",openId);
        packageParams.put("time_start",time_start);
        packageParams.put("mch_id", mchId);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("notify_url", notifyUrl);
        packageParams.put("out_trade_no", outTradeNo);
        packageParams.put("spbill_create_ip", ip);
        packageParams.put("total_fee", payMoney);
        packageParams.put("trade_type", trade_type);
        packageParams.put("sign_type", "MD5");

        RequestHandler reqHandler = new RequestHandler(null, null);
        reqHandler.init(appId,appSecret,partnerkey);

        String sign = reqHandler.createSign(packageParams);
        String xml = "<xml>"
                + "<appid>" + appId + "</appid>"
                + "<mch_id>"+ mchId + "</mch_id>"
                + "<nonce_str>" + nonce_str+ "</nonce_str>"
                + "<body><![CDATA[" + body + "]]></body>"
                + "<time_start>" + time_start + "</time_start>"
                + "<out_trade_no>" + outTradeNo+ "</out_trade_no>"
                + "<total_fee>" + payMoney + "</total_fee>"
                + "<spbill_create_ip>" + ip+ "</spbill_create_ip>"
                + "<notify_url>" + notifyUrl+ "</notify_url>"
                + "<openid>" + openId + "</openid>"
                + "<trade_type>" + trade_type+ "</trade_type>"
                + "<sign>" + sign + "</sign>"
                + "<sign_type>MD5</sign_type>"
                + "</xml>";
        logger.info("weixin-xml:"+xml);
        String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

        String prepayId = new GetWxOrderno().getPayNo(createOrderURL, xml);

        //获取prepay_id后，拼接最后请求支付所需要的package
        SortedMap<String, String> finalpackage = new TreeMap<String, String>();
        String timestamp = Sha1Util.getTimeStamp();
        nonce_str = StringUtil.getNonceStr();
        finalpackage.put("appId", appId);
        finalpackage.put("timeStamp", timestamp);
        finalpackage.put("nonceStr", nonce_str);
        finalpackage.put("package", "prepay_id=" + prepayId);
        finalpackage.put("signType", "MD5");
        //要签名
        String finalsign = reqHandler.createSign(finalpackage);

        Map<String,String> params = new HashMap<>();
        params.put("appId", appId);
        params.put("timeStamp", timestamp);
        params.put("nonceStr", nonce_str);
        params.put("package", "prepay_id=" + prepayId);
        params.put("signType", "MD5");
        params.put("paySign", finalsign);

        return params;
    }

    @Override
    @Transactional
    public Result withdrawCash(String guid,String openId, BigDecimal amount, HttpServletRequest request) {
        Lock lock = new Lock();

        Result result = new Result("提现成功");
        try {
            // 加锁
            lock.lock(openId);

            // 处理重复请求
            ChargingWithdrawCash withdrawCash = chargingWithdrawCashService.queryByGuid(guid);
            if (withdrawCash != null) {
                result.setMessage(withdrawCash.getErrorMsg());
                return result;
            }

            // 判断是否可提现
            ChargingCst chargingCst = chargingCstService.queryByOpenIdForUpdate(openId);

            BigDecimal remainAmount = chargingCst.getRemainAmount();
            BigDecimal subtract = remainAmount.subtract(amount);
            boolean flag = MathUtil.isLessThanZero(subtract);
            if (flag) {
                result.setMessage("账户余额不足");
                return result;
            }

            // 扣除用户账户余额,并记录账户流水
            AccountOperateVo operateVo = AccountUtil.getAccountOperateVo(BusinessType.WECHAT_WITHDRAW_CASH, amount,null);
            operateVo.setGuid(guid);
            operateVo.setAccountId(chargingCst.getId());

            ChargingCst updateParam = new ChargingCst();
            updateParam.setId(chargingCst.getId());
            updateParam.setRemainAmount(subtract);
            updateParam.setUpdateTime(new Date());
            chargingCstService.updateAccountNew(updateParam,operateVo);

            // 添加一条提现记录
            ChargingWithdrawCash record = new ChargingWithdrawCash();
            record.setCustomerGuid(chargingCst.getCustomerGuid());
            record.setWebcharNo(openId);
            record.setAmount(amount);
            record.setAfterRemainAmount(subtract);
            record.setStatus(0);
            record.setCreateTime(new Date());
            record.setGuid(guid);
            record.setType(ChargeAppConstant.AppType.WECHAT.getType());
            chargingWithdrawCashService.insertSelective(record);

            // 调用微信提现接口
            String companyPayUrl = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
            String nonce_str = StringUtil.getNonceStr();
            String partner_trade_no = UuidUtil.getUuid();//商户订单号
            String check_name = "NO_CHECK";//校验用户姓名选项 //	NO_CHECK：不校验真实姓名        FORCE_CHECK：强校验真实姓名
            String desc = "充电用户账户余额提现";//企业付款描述信息
            String spbill_create_ip = request.getRemoteAddr();//调用接口的终端ip

            Map<String, String> data = new HashMap<>();
            data.put("mch_appid", appId); //商户号
            data.put("mchid",mchId);//注意这里没下划线 ,微信支付的时候这里是带下划线的,
            data.put("nonce_str",nonce_str);
            data.put("partner_trade_no",partner_trade_no);
            data.put("openid",openId);
            data.put("check_name",check_name);
            // 元转分
            String cashMoney = MathUtil.yuan2Fen(String.valueOf(amount));
            data.put("amount",cashMoney);
            data.put("desc",desc);
            data.put("spbill_create_ip",spbill_create_ip);
            String sign = WxPaySignatureUtils.signature(data,partnerkey);
            data.put("sign",sign);

            WxTransfers transfers = new WxTransfers();
            transfers.setMch_appid(appId);
            transfers.setMchid(mchId);
            transfers.setNonce_str(nonce_str);
            transfers.setPartner_trade_no(partner_trade_no);
            transfers.setOpenid(openId);
            transfers.setCheck_name(check_name);
            transfers.setAmount(cashMoney);
            transfers.setDesc(desc);
            transfers.setSpbill_create_ip(spbill_create_ip);
            transfers.setSign(sign);

            //***********************企业付款请求*********************************
            xmlUtil.xstream().alias("xml", WxTransfers.class);
            String xml = xmlUtil.xstream().toXML(transfers);
            String ssl = WxHttpUtil.ssl(request, companyPayUrl, mchId, xml);
            if (StringUtils.isEmpty(ssl)) {
                result.error("系统异常");
                return result;
            }

            logger.info("提现返回值：" + ssl);
            Document document = XmlUtil.getDocument(ssl);
            Map<String, String> map = new HashMap<>();
            XmlUtil.getMapByDocument(document.getRootElement(), map);

            String return_code = map.get("return_code");
            String result_code = map.get("result_code");
            if (!"SUCCESS".equals(return_code)) {
                // 通信失败
                result.setMessage("提现接口返回通信失败");
                return result;
            }

            if(!"SUCCESS".equals(result_code)) {
                logger.info("提现接口返回FAIL");
                String errCode = map.get("err_code");
                String errCodeDes = map.get("err_code_des");

                if ("SYSTEMERROR".equals(errCode)) {
                    // 这种情况，不能理解为转账失败，需要主动查询状态
//                    // 更新提现记录状态
//                    ChargingWithdrawCash cashParam = new ChargingWithdrawCash();
//                    cashParam.setId(record.getId());
//                    cashParam.setStatus(ChargeAppConstant.WithdrawCashStatus.FAIL.getStatus());
//                    cashParam.setErrorCode(errCode);
//                    cashParam.setErrorMsg(errCodeDes);
//                    cashParam.setUpdateTime(new Date());
//                    chargingWithdrawCashService.updateByPrimaryKeySelective(cashParam);

                    // 主动查询一次
                    String xmlData = this.queryTransferInfo(request,partner_trade_no);
                    if (StringUtils.isEmpty(xmlData)) {
                        result.error("系统异常");
                        return result;
                    }

                    Document document2 = XmlUtil.getDocument(xmlData);
                    Map<String, String> queryMap = new HashMap<>();
                    XmlUtil.getMapByDocument(document2.getRootElement(), queryMap);
                    String returnCode = queryMap.get("return_code");
                    String resultcode = queryMap.get("result_code");
                    if ("SUCCESS".equals(returnCode) && "SUCCESS".equals(resultcode)) {
                        String status = queryMap.get("status");
                        String reason = queryMap.get("reason");

                        if ("SUCCESS".equals(status)) {
                            // 成功
                            ChargingWithdrawCash temp = new ChargingWithdrawCash();
                            temp.setId(record.getId());
                            temp.setStatus(ChargeAppConstant.WithdrawCashStatus.SUCCESS.getStatus());
                            temp.setUpdateTime(new Date());
                            chargingWithdrawCashService.updateByPrimaryKeySelective(temp);
                            return result;
                        } else if("PROCESSING".equals(status)) {
                            // 处理中
                            result.setMessage("提现处理中");
                            return result;
                        } else {
                            // 转账失败
                            this.doWechatError(chargingCst,guid,amount,record.getId(),"",reason);
                            result.setMessage("提现失败");
                            return result;
                        }
                    }

                    // 直接返回
                    result.error("系统繁忙");
                    return result;
                } else {
                    this.doWechatError(chargingCst,guid,amount,record.getId(),errCode,errCodeDes);
                    result.setMessage(errCodeDes);
                    return result;
                }
            }

            // 提现成功处理逻辑
            String partnerTradeNo = map.get("partner_trade_no");
            String paymentTime = map.get("payment_time");

            ChargingWithdrawCash cashParam = new ChargingWithdrawCash();
            cashParam.setId(record.getId());
            cashParam.setPartnerTradeNo(partnerTradeNo);
            cashParam.setStatus(ChargeAppConstant.WithdrawCashStatus.SUCCESS.getStatus());
            cashParam.setUpdateTime(new Date());
            chargingWithdrawCashService.updateByPrimaryKeySelective(cashParam);
        } catch (Exception e) {
            logger.error("提现接口异常",e);
            result.error("微信提现异常");
            throw new BusinessException("微信提现异常");
        } finally {
            lock.unLock();
        }
        return result;
    }

    private void doWechatError(ChargingCst chargingCst,String guid,BigDecimal amount, Integer id, String errorCode, String errorMsg) {
        AccountOperateVo operateVo = AccountUtil.getAccountOperateVo(BusinessType.WECHAT_WITHDRAW_CASH_REFUND, amount,null);
        operateVo.setGuid(guid);
        operateVo.setAccountId(chargingCst.getId());

        // 扣减的余额加回去
        ChargingCst updateParam = new ChargingCst();
        updateParam.setId(chargingCst.getId());
        updateParam.setRemainAmount(chargingCst.getRemainAmount());
        updateParam.setUpdateTime(new Date());
        chargingCstService.updateAccountNew(updateParam,operateVo);

        // 更新提现记录状态
        ChargingWithdrawCash cashParam = new ChargingWithdrawCash();
        cashParam.setId(id);
        cashParam.setStatus(ChargeAppConstant.WithdrawCashStatus.FAIL.getStatus());
        cashParam.setErrorCode(errorCode);
        cashParam.setErrorMsg(errorMsg);
        cashParam.setUpdateTime(new Date());
        chargingWithdrawCashService.updateByPrimaryKeySelective(cashParam);
    }

    @Override
    @Transactional
    public Result alipayWithdrawCash(String guid, String alipayUserId, BigDecimal amount) {
        Result result = new Result();
        // 处理重复请求
        ChargingWithdrawCash withdrawCash = chargingWithdrawCashService.queryByGuid(guid);
        if (withdrawCash != null) {
            result.setMessage(withdrawCash.getErrorMsg());
            return result;
        }

        // 判断是否可提现
        ChargingCst chargingCst = chargingCstService.queryByAlipayUserIdForUpdate(alipayUserId);
        BigDecimal remainAmount = chargingCst.getRemainAmount();
        BigDecimal subtract = remainAmount.subtract(amount);
        boolean flag = MathUtil.isLessThanZero(subtract);
        if (flag) {
            result.setMessage("账户余额不足");
            return result;
        }

        // 扣除用户账户余额,并记录账户流水
        AccountOperateVo operateVo = AccountUtil.getAccountOperateVo(BusinessType.ALIPAY_WITHDRAW_CASH, amount,null);
        operateVo.setGuid(guid);
        operateVo.setAccountId(chargingCst.getId());

        ChargingCst updateParam = new ChargingCst();
        updateParam.setId(chargingCst.getId());
        updateParam.setRemainAmount(subtract);
        updateParam.setUpdateTime(new Date());
        chargingCstService.updateAccountNew(updateParam,operateVo);

        // 添加一条提现记录
        ChargingWithdrawCash record = new ChargingWithdrawCash();
        record.setCustomerGuid(chargingCst.getCustomerGuid());
        record.setWebcharNo(alipayUserId);
        record.setAmount(amount);
        record.setAfterRemainAmount(subtract);
        record.setStatus(0);
        record.setCreateTime(new Date());
        record.setGuid(guid);
        record.setType(ChargeAppConstant.AppType.ALIPAY.getType());
        chargingWithdrawCashService.insertSelective(record);

        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",alipayAppId,alipayAppPrivateKey,"json",CHARSET,alipayPublicKey,"RSA2");
        AlipayFundTransToaccountTransferRequest transferRequest = new AlipayFundTransToaccountTransferRequest();

        JSONObject jsonObject = new JSONObject();
        String outBizNo = UuidUtil.getUuid();
        jsonObject.put("out_biz_no",outBizNo);
        jsonObject.put("payee_type","ALIPAY_USERID");
        jsonObject.put("payee_account",alipayUserId);
        jsonObject.put("amount",amount);
        transferRequest.setBizContent(JSON.toJSONString(jsonObject));

        try {
            AlipayFundTransToaccountTransferResponse response = alipayClient.execute(transferRequest);
            logger.info("支付宝提现接口返回：" + JSON.toJSONString(response));
            if(response.isSuccess()){
                logger.info(alipayUserId + "第一次调用成功");
                this.doAlipaySuccess(record.getId(),outBizNo);
                result.setMessage("提现成功");
                return result;
            }

            String subCode = response.getSubCode();
            String subMsg = response.getSubMsg();
            if ("SYSTEM_ERROR".equals(subCode)) {
                // 采用相同的outBizNo 重发请求
                AlipayFundTransToaccountTransferResponse response2 = alipayClient.execute(transferRequest);
                if (response2.isSuccess()) {
                    logger.info(alipayUserId + "第二次调用成功");
                    this.doAlipaySuccess(record.getId(),outBizNo);
                    result.setMessage("提现成功");
                    return result;
                }

                String subCode2 = response2.getSubCode();
                String subMsg2 = response2.getSubMsg();
                if ("SYSTEM_ERROR".equals(subCode2)) {
                    // 处理中
                    result.setMessage("处理中");
                    return result;
                }

                // 处理失败
                this.doAlipayError(chargingCst,guid,amount,record.getId(),subCode2,subMsg2);
                result.setMessage("提现失败");
                return result;
            }

            this.doAlipayError(chargingCst,guid,amount,record.getId(),subCode,subMsg);
            result.setMessage("提现失败");
        } catch (AlipayApiException e) {
            logger.error("调用支付宝转账接口异常",e);
            result.setMessage("支付宝提现异常");
            throw new BusinessException("支付宝提现异常");
        }
        return result;
    }

    /**
     * 提现成功操作
     * @param id
     * @param partnerTradeNo
     */
    private void doAlipaySuccess(Integer id, String partnerTradeNo) {
        ChargingWithdrawCash cashParam = new ChargingWithdrawCash();
        cashParam.setId(id);
        cashParam.setPartnerTradeNo(partnerTradeNo);
        cashParam.setStatus(ChargeAppConstant.WithdrawCashStatus.SUCCESS.getStatus());
        cashParam.setUpdateTime(new Date());
        chargingWithdrawCashService.updateByPrimaryKeySelective(cashParam);
    }

    /**
     * 提现失败操作
     * @param amount
     * @param id
     * @param errorCode
     * @param errorMsg
     */
    private void doAlipayError(ChargingCst chargingCst,String guid, BigDecimal amount, Integer id, String errorCode, String errorMsg) {
        AccountOperateVo operateVo = AccountUtil.getAccountOperateVo(BusinessType.ALIPAY_WITHDRAW_CASH_REFUND, amount,null);
        operateVo.setGuid(guid);
        operateVo.setAccountId(chargingCst.getId());

        // 扣减的余额加回去
        ChargingCst updateParam = new ChargingCst();
        updateParam.setId(chargingCst.getId());
        updateParam.setRemainAmount(chargingCst.getRemainAmount());
        updateParam.setUpdateTime(new Date());
        chargingCstService.updateAccountNew(updateParam,operateVo);

        // 更新提现记录状态
        ChargingWithdrawCash cashParam = new ChargingWithdrawCash();
        cashParam.setId(id);
        cashParam.setStatus(ChargeAppConstant.WithdrawCashStatus.FAIL.getStatus());
        cashParam.setErrorCode(errorCode);
        cashParam.setErrorMsg(errorMsg);
        cashParam.setUpdateTime(new Date());
        chargingWithdrawCashService.updateByPrimaryKeySelective(cashParam);
    }

    @Override
    public void checkCashFailFlows(Integer id,String partnerTradeNo) {
        String url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/gettransferinfo";
        String nonceStr = StringUtil.getNonceStr();
        Map<String, String> data = new HashMap<>();
        data.put("mch_id", mchId);
        data.put("appid",appId);
        data.put("nonce_str",nonceStr);
        data.put("partner_trade_no",partnerTradeNo);

        String sign = WxPaySignatureUtils.signature(data,partnerkey);
        data.put("sign",sign);

        String dataXML = XMLBeanUtils.map2XmlString(data);
        String response = WxHttpUtil.ssl4Job(url, mchId, dataXML);
        logger.info("网络请求返回：" + response);
        if (StringUtils.isEmpty(response)) {
            return;
        }

        Document document2 = XmlUtil.getDocument(response);
        Map<String, String> queryMap = new HashMap<>();
        XmlUtil.getMapByDocument(document2.getRootElement(), queryMap);
        String returnCode = queryMap.get("return_code");
        String resultcode = queryMap.get("result_code");
        if ("SUCCESS".equals(returnCode) && "SUCCESS".equals(resultcode)) {
            String status = queryMap.get("status");
            String reason = queryMap.get("reason");

            if ("SUCCESS".equals(status)) {
                // 成功
                ChargingWithdrawCash temp = new ChargingWithdrawCash();
                temp.setId(id);
                temp.setStatus(ChargeAppConstant.WithdrawCashStatus.SUCCESS.getStatus());
                temp.setUpdateTime(new Date());
                chargingWithdrawCashService.updateByPrimaryKeySelective(temp);
            } else if("PROCESSING".equals(status)) {
                // 处理中
                // do nothing
            } else {
                // 转账失败
                ChargingWithdrawCash temp = new ChargingWithdrawCash();
                temp.setId(id);
                temp.setStatus(ChargeAppConstant.WithdrawCashStatus.FAIL.getStatus());
                temp.setErrorMsg(reason);
                temp.setUpdateTime(new Date());
                chargingWithdrawCashService.updateByPrimaryKeySelective(temp);
            }
        }
    }

    @Override
    public void processAlipayWithdrawCashFlows(Integer id, String partnerTradeNo) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",alipayAppId,alipayAppPrivateKey,"json",CHARSET,alipayPublicKey,"RSA2");
        AlipayFundTransOrderQueryRequest request = new AlipayFundTransOrderQueryRequest();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("out_biz_no",partnerTradeNo);
        request.setBizContent(JSON.toJSONString(jsonObject));

        AlipayFundTransOrderQueryResponse response = alipayClient.execute(request);
        logger.info("支付宝提现接口返回：" + JSON.toJSONString(response));
        if(response.isSuccess()){
            String status = response.getStatus();
            String errorCode = response.getErrorCode();
            String failReason = response.getFailReason();
            if ("SUCCESS".equals(status)) {
                // 成功
                logger.info("调用成功");
                // 更新提现记录状态为提现成功
                ChargingWithdrawCash withdrawCash = new ChargingWithdrawCash();
                withdrawCash.setId(id);
                withdrawCash.setStatus(ChargeAppConstant.WithdrawCashStatus.SUCCESS.getStatus());
                withdrawCash.setUpdateTime(new Date());
                chargingWithdrawCashService.updateByPrimaryKeySelective(withdrawCash);
            } else if ("FAIL".equals(status)) {
                // 失败
                ChargingWithdrawCash withdrawCash = new ChargingWithdrawCash();
                withdrawCash.setId(id);
                withdrawCash.setStatus(ChargeAppConstant.WithdrawCashStatus.FAIL.getStatus());
                withdrawCash.setErrorCode(errorCode);
                withdrawCash.setErrorMsg(failReason);
                withdrawCash.setUpdateTime(new Date());
                chargingWithdrawCashService.updateByPrimaryKeySelective(withdrawCash);
            } else if ("INIT".equals(status)) {
                // 等待处理

            } else if ("DEALING".equals(status)) {
                // 处理中

            } else if ("REFUND".equals(status)) {
                // 退票

            } else if ("UNKNOWN".equals(status)) {
                // 状态未知
            }
        } else {
            logger.info("调用失败");
        }

    }

    @Override
    public void processAlipaySplitRecord(Integer id, String partnerTradeNo) throws AlipayApiException {
//        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",alipayAppId,alipayAppPrivateKey,"json",CHARSET,alipayPublicKey,"RSA2");
//        AlipayFundTransOrderQueryRequest request = new AlipayFundTransOrderQueryRequest();
//
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("out_biz_no",partnerTradeNo);
//        request.setBizContent(JSON.toJSONString(jsonObject));
//
//        AlipayFundTransOrderQueryResponse response = alipayClient.execute(request);
//        logger.info("支付宝分账订单查询返回：" + JSON.toJSONString(response));
//        if(response.isSuccess()){
//            String status = response.getStatus();
//            String errorCode = response.getErrorCode();
//            String failReason = response.getFailReason();
//            if ("SUCCESS".equals(status)) {
//                // 成功
//                logger.info("调用成功");
//                // 更新提现记录状态为提现成功
//                ChargingSpiltAccount account = new ChargingSpiltAccount();
//                account.setId(id);
//                account.setStatus(ChargeAppConstant.WithdrawCashStatus.SUCCESS.getStatus());
//                account.setUpdateTime(new Date());
//                spiltAccountService.updateByPrimaryKeySelective(account);
//            } else if ("FAIL".equals(status)) {
//                // 失败
//                ChargingSpiltAccount account = new ChargingSpiltAccount();
//                account.setId(id);
//                account.setStatus(ChargeAppConstant.WithdrawCashStatus.FAIL.getStatus());
//                account.setErrorCode(errorCode);
//                account.setErrorMsg(failReason);
//                account.setUpdateTime(new Date());
//                spiltAccountService.updateByPrimaryKeySelective(account);
//            } else if ("INIT".equals(status)) {
//                // 等待处理
//
//            } else if ("DEALING".equals(status)) {
//                // 处理中
//
//            } else if ("REFUND".equals(status)) {
//                // 退票
//
//            } else if ("UNKNOWN".equals(status)) {
//                // 状态未知
//            }
//        } else {
//            logger.info("调用失败");
//        }

    }

    /**
     * 根据商户订单号，查询企业付款信息
     * @param request
     * @param partnerTradeNo
     */
    private String queryTransferInfo(HttpServletRequest request,String partnerTradeNo) {
        String url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/gettransferinfo";
        String nonceStr = StringUtil.getNonceStr();
        Map<String, String> data = new HashMap<>();
        data.put("mch_id", mchId);
        data.put("appid",appId);
        data.put("nonce_str",nonceStr);
        data.put("partner_trade_no",partnerTradeNo);

        String sign = WxPaySignatureUtils.signature(data,partnerkey);
        data.put("sign",sign);

        String dataXML = XMLBeanUtils.map2XmlString(data);
        String ssl = WxHttpUtil.ssl(request, url, mchId, dataXML);
        return ssl;
    }
}
