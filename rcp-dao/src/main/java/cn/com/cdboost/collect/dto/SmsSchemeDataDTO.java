package cn.com.cdboost.collect.dto;

import java.math.BigDecimal;

/**
 *
 * @desc 汇总当前月的的电、水、气量和费用返回值的数据传输对象
 *
 **/
public class SmsSchemeDataDTO {

    private String deviceType;          //
    private BigDecimal sumPR;               // 汇总电量值
    private BigDecimal sumRemainAmount;     // 汇总剩余金额
    private String cltMonth;            // 月份


}
