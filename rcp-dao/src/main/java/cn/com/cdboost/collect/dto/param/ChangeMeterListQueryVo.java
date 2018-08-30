package cn.com.cdboost.collect.dto.param;

/**
 * Created by Administrator on 2017/12/18 0018.
 */
public class ChangeMeterListQueryVo extends PageQueryVo{
    private String userId;
    private String deviceNo;
    private String customerNo;
    private String customerName;
    private String customerAddr;
    private String customerContact;
    private String meterUserNo;
    private String deviceType;
    private String changeDateStart;
    private String changeDateEnd;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
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

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getChangeDateStart() {
        return changeDateStart;
    }

    public void setChangeDateStart(String changeDateStart) {
        this.changeDateStart = changeDateStart;
    }

    public String getChangeDateEnd() {
        return changeDateEnd;
    }

    public void setChangeDateEnd(String changeDateEnd) {
        this.changeDateEnd = changeDateEnd;
    }
}
