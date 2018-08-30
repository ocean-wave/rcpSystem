package cn.com.cdboost.collect.dto.param;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/12/15 0015.
 */
public class RechargePaymentParam {
    private String payMoney;
    private String payCount;
    private String payModel;
    private String customerNo;
    private String cno;
    private String payGuid;
    private String createUserId;
    private Integer payMethod;
    private String serialNum;
    private String result;
    /**
     * 充值后的剩余金额，根据此金额，判断是否发送跳闸指令
     */
    private BigDecimal afterAmunt;

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public String getPayCount() {
        return payCount;
    }

    public void setPayCount(String payCount) {
        this.payCount = payCount;
    }

    public String getPayModel() {
        return payModel;
    }

    public void setPayModel(String payModel) {
        this.payModel = payModel;
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

    public String getPayGuid() {
        return payGuid;
    }

    public void setPayGuid(String payGuid) {
        this.payGuid = payGuid;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public BigDecimal getAfterAmunt() {
        return afterAmunt;
    }

    public void setAfterAmunt(BigDecimal afterAmunt) {
        this.afterAmunt = afterAmunt;
    }
}
