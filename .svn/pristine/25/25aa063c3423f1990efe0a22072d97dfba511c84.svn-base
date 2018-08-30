package cn.com.cdboost.collect.dto.param;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 用户重新开户，传入参数
 */
public class CustomerReAcctParam {

    /**
     * 用户唯一标识
     */
    @NotBlank(message = "customerNo不能为空")
    private String customerNo;

    /**
     * 表cno
     */
    @NotBlank(message = "cno不能为空")
    private String cno;

    /**
     * 缴费金额
     */
    @NotBlank(message = "payment不能为空")
    private String payment;

    /**
     * 购电金额
     */
    @NotBlank(message = "payMoney不能为空")
    private String payMoney;

    /**
     * 预留金额
     */
    @NotBlank(message = "initAmount不能为空")
    private String initAmount;

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

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public String getInitAmount() {
        return initAmount;
    }

    public void setInitAmount(String initAmount) {
        this.initAmount = initAmount;
    }
}
