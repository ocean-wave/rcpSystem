package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.param.PaymentRecordParam;
import com.github.pagehelper.PageInfo;

/**
 * 查询缴费记录
 */
public interface PaymentRecordService {

    PageInfo queryPaymentRecord(PaymentRecordParam paymentRecordParam);
}
