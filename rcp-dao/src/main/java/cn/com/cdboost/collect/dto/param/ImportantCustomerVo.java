package cn.com.cdboost.collect.dto.param;

/**
 * Created by Administrator on 2017/12/14 0014.
 */
public class ImportantCustomerVo extends PageQueryVo {

    /**
     * 设备编号
     */
    private String propertyName="";

    /**
     * 用户姓名
     */
    private String customerName="";

    /**
     * 用户地址
     */
    private String customerAddr="";

    /**
     * 联系电话
     */
    private String customerContact="";

    /**
     * 表计户号
     */
    private String customerId="";

    /**
     * 设备编号
     */
    private String deviceNo="";

    private String meterUserNo="";

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

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }}
