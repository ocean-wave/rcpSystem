package cn.com.cdboost.collect.dto;

/**
 * @author xzy
 * @desc 掌机补抄功能基本数据传输对象
 * @create 2017/9/12 0012
 **/
public class BaseHandheldReplenishDTO {

    /**
     * 设备cno
     */
    private String cno;

    /**
     * 用户唯一标识
     */
    private String customerNo;

    /**
     * 用户名称
     */
    private String customerName;

    /**
     * 用户地址
     */
    private String customerAddr;

    /**
     * 门牌编号
     */
    private String propertyName;

    /**
     * 表计户号
     */
    private String meterUserNo;

    /**
     * 设备编号
     */
    private String deviceNo;

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
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

    public String getMeterUserNo() {
        return meterUserNo;
    }

    public void setMeterUserNo(String meterUserNo) {
        this.meterUserNo = meterUserNo;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }
}
