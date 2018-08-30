package cn.com.cdboost.collect.dto.param;

import java.io.Serializable;

/**
 * 发送微信模板消息，存储一些公共的字段
 */
public class BaseMessage implements Serializable{

    /**
     * 设备cno
     */
    private String cno;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 支付时间
     */
    private String payTime;

    /**
     * 支付金额
     */
    private String payMoney;

    /**
     * 支付方式
     */
    private Integer payMethod;

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }
}
