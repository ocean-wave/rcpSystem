package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.dto.param.CreateOrderParam;
import cn.com.cdboost.collect.model.ChargingPay;

/**
 * 阿里支付相关服务
 */
public interface AlipayService {

    ChargingPay order(CreateOrderParam orderParam);

    String charge(ChargingPay chargingPay);

    void orderNotify(String outTradeNo,String totalAmount,String sellerId,String appId,String tradeStatus);

    void commonOperate(ChargingPay chargingPay);

    void icCardPayOperate(ChargingPay chargingPay);
}