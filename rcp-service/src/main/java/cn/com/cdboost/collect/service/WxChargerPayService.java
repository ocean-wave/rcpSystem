package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.dto.chargerApp.Ajax;
import cn.com.cdboost.collect.dto.param.CreateOrderParam;
import cn.com.cdboost.collect.model.ChargingCst;
import cn.com.cdboost.collect.model.ChargingDevice;
import cn.com.cdboost.collect.model.ChargingPayCheme;
import cn.com.cdboost.collect.model.ChargingUseDetailed;
import cn.com.cdboost.collect.vo.Result;
import com.alipay.api.AlipayApiException;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

public interface WxChargerPayService {
    /**
     * 微信充值下单
     * @param pay
     * @param openid
     * @return
     */
    Ajax pay(Double pay, String ip, ChargingPayCheme chargingPayCheme, String customerGuid, String openid, HttpServletRequest request);

    /**
     * 微信通知
      * @param outTradeNo
     * @return
     */
    void notify(String outTradeNo,String totalFee);
    /**
     * 扣费接口
     * @param openId
     * @return
     */
    void deductions(ChargingDevice chargingDevice,String chargingGUID, Integer openMeans, String openId, String priceId);

    void rechargeDeductions(ChargingUseDetailed useDetailed);

    /**
     *  用户购买月卡余额扣减
     * @param chargingCst
     * @param chargingPayCheme
     */
    void deductionsOfMonth(ChargingCst chargingCst, ChargingPayCheme chargingPayCheme, CreateOrderParam orderParam);

    /**
     * 微信下单接口
     * @param pay
     * @param ip
     * @param chargingPayCheme
     * @param customerGuid
     * @param openid
     * @return
     */
    Ajax chargePay(Integer type,BigDecimal deductAmount,BigDecimal pay, String ip, ChargingPayCheme chargingPayCheme, String customerGuid, String openid);

    Map<String,String> weChatPrePay(String payMoney, String outTradeNo, String openId, String ip);

    // 微信提现
    Result withdrawCash(String guid,String openId, BigDecimal amount, HttpServletRequest request);

    // 支付宝提现
    Result alipayWithdrawCash(String guid,String alipayUserId, BigDecimal amount);

    // 查询提现流水状态
    void checkCashFailFlows(Integer id,String partnerTradeNo);

    // 支付，查询转账订单，并更新商户内部提现记录订单状态
    void processAlipayWithdrawCashFlows(Integer id,String partnerTradeNo) throws AlipayApiException;

    // 支付宝，查询转账订单，并更新商户内部分账记录订单状态
    void processAlipaySplitRecord(Integer id,String partnerTradeNo) throws AlipayApiException;
}
