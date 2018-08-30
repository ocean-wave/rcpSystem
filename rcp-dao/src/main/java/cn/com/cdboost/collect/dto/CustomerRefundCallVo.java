package cn.com.cdboost.collect.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CustomerRefundCallVo {
    /**
     * 用户唯一标识
     */
    @NotNull(message = "客户唯一标识不能为空")
    private  String customerNo;
    /**
     * 设备cno
     */
    @NotNull(message = "设备cno不能为空")
    private  String cno;
    /**
     * 退款金额
     */
    @NotNull(message = "退款金额不能为空")
    private BigDecimal refundMoney;
    /**
     * 退款方式
     */
    @NotNull(message = "退款方式不能为空")
    private  Integer refundMethod;
    /**
     * 退款标识
     */
    @NotNull(message = "退款标识不能为空")
    private  String refundGuid;
    /**
     * 退款原因
     */
    private  String remark;
    /**
     * 操作人员ID
     */
    private  Integer createUserId;

    private Integer result;

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public BigDecimal getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(BigDecimal refundMoney) {
        this.refundMoney = refundMoney;
    }

    public Integer getRefundMethod() {
        return refundMethod;
    }

    public void setRefundMethod(Integer refundMethod) {
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
