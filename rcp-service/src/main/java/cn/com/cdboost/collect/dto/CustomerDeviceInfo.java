package cn.com.cdboost.collect.dto;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 实时数据、历史数据明细页面，用户相关信息
 */
public class CustomerDeviceInfo {
    /**
     * 客户唯一标识
     */
    private String customerNo;

    /**
     * 用户名称
     */
    private String customerName;

    /**
     * 联系电话
     */
    private String customerContact;

    /**
     * 用户地址
     */
    private String customerAddr;

    /**
     * 门牌编号
     */
    private String propertyName;

    /**
     * 倍率
     */
    private Integer ratio;
    @JSONField(name = "deviceNo")
    private String commAddr;
    @JSONField(name = "cjqNo")
    private String commCollectionNo;
    private Integer commPointCode;
    @JSONField(name = "jzqNo")
    private String jzqCno;
    private Integer commBaudrate;

    public String getCommAddr() {
        return commAddr;
    }

    public void setCommAddr(String commAddr) {
        this.commAddr = commAddr;
    }

    public String getCommCollectionNo() {
        return commCollectionNo;
    }

    public void setCommCollectionNo(String commCollectionNo) {
        this.commCollectionNo = commCollectionNo;
    }

    public Integer getCommPointCode() {
        return commPointCode;
    }

    public void setCommPointCode(Integer commPointCode) {
        this.commPointCode = commPointCode;
    }

    public String getJzqCno() {
        return jzqCno;
    }

    public void setJzqCno(String jzqCno) {
        this.jzqCno = jzqCno;
    }

    public Integer getCommBaudrate() {
        return commBaudrate;
    }

    public void setCommBaudrate(Integer commBaudrate) {
        this.commBaudrate = commBaudrate;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public String getCustomerAddr() {
        return customerAddr;
    }

    public void setCustomerAddr(String customerAddr) {
        this.customerAddr = customerAddr;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Integer getRatio() {
        return ratio;
    }

    public void setRatio(Integer ratio) {
        this.ratio = ratio;
    }
}
