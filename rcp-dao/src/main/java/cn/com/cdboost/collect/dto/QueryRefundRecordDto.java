package cn.com.cdboost.collect.dto;

public class QueryRefundRecordDto {
    /**
     * 客户地址
     */
    private String customerAddr;
    /**
     * 客户联系方式
     */
    private String customerContact;
    /**
     * 表计户号
     */
    private String meterUserNo;
    /**
     * 客户姓名
     */
    private String customerName;
    /**
     * 设备编号
     */
    private String deviceNo;
    /**
     * 退费日期
     */
    private String refundDate;
    /**
     * 退费金额
     */
    private Float refundMoney;
    /**
     * 门牌号
     */
    private String propertyName;
    /**
     * 流水号
     */
    private String installAddr;

    public String getCustomerAddr() {
        return customerAddr;
    }

    public void setCustomerAddr(String customerAddr) {
        this.customerAddr = customerAddr;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public String getMeterUserNo() {
        return meterUserNo;
    }

    public void setMeterUserNo(String meterUserNo) {
        this.meterUserNo = meterUserNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(String refundDate) {
        this.refundDate = refundDate;
    }

    public Float getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(Float refundMoney) {
        this.refundMoney = refundMoney;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getInstallAddr() {
        return installAddr;
    }

    public void setInstallAddr(String installAddr) {
        this.installAddr = installAddr;
    }
}
