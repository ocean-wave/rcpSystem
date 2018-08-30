package cn.com.cdboost.collect.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author wt
 * @desc 电量电费返回
 * @create 2017/7/11 0011
 **/
public class ElectResponseParamDto implements Serializable {
    private  BigDecimal currentMonthTotal;
    private  BigDecimal remainAmount;
    private  BigDecimal currentMonthFee;

    public BigDecimal getCurrentMonthTotal() {
        return currentMonthTotal;
    }

    public void setCurrentMonthTotal(BigDecimal currentMonthTotal) {
        this.currentMonthTotal = currentMonthTotal;
    }

    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }

    public BigDecimal getCurrentMonthFee() {
        return currentMonthFee;
    }

    public void setCurrentMonthFee(BigDecimal currentMonthFee) {
        this.currentMonthFee = currentMonthFee;
    }
}
