package cn.com.cdboost.collect.dto.param;

/**
 * 用户档案查询vo
 */
public class CustomerInfoQueryVo extends PageQueryVo{
    /**
     * 用户姓名
     */
    private String customerName="";

    /**
     * 门牌编号
     */
    private String propertyName="";

    /**
     * 联系电话
     */
    private String customerContact="";

    /**
     * 用户地址
     */
    private String customerAddr="";

    /**
     * 表计户号
     */
    private String meterUserNo="";

    /**
     * 用户表号
     */
    private String deviceNo="";

    private Long userId;

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

    public String getMeterUserNo() {
        return meterUserNo;
    }

    public void setMeterUserNo(String meterUserNo) {
        this.meterUserNo = meterUserNo;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
