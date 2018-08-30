package cn.com.cdboost.collect.dto.param;

import javax.validation.constraints.NotNull;

/**
 * 客户退费操作
 */
public class CustomerRefundVo {
    /**
     * 用户唯一标识
     */
    @NotNull(message = "客户唯一标识不能为空")
    private  String customerNo;
    /**
     * 退款金额
     */
    @NotNull(message = "退款金额不能为空")
    private  String refundMoney;
    /**
     * 退款方式
     */
    @NotNull(message = "退款方式不能为空")
    private  String refundMethod;
    /**
     * 退款标识
     */
    @NotNull(message = "退款标识不能为空")
    private  String refundGuid;
    /**
     * 退款原因
     */
    private  String remark;

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(String refundMoney) {
        this.refundMoney = refundMoney;
    }

    public String getRefundMethod() {
        return refundMethod;
    }

    public void setRefundMethod(String refundMethod) {
        this.refundMethod = refundMethod;
    }

    public String getRefundGuid() {
        return refundGuid;
    }

    public void setRefundGuid(String refundGuid) {
        this.refundGuid = refundGuid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
