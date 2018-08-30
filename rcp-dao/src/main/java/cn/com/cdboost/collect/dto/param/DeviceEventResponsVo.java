package cn.com.cdboost.collect.dto.param;

import java.util.Date;

/**
 * 通断查询vo
 */
public class DeviceEventResponsVo {


    private String cno;
    private Date alarmTime;

    private Integer eventStatus;

    private Date alarmDealTime;
    private String alarmDealDescribe;
    private String alarmDealPerson;
    private Integer Id;
    private String eventCategory;
    private String eventContent;
    /**
     *表计户号
     */
    private String meterUserNo;
    /**
     * 设备编号
     */
    private String deviceNo;
    /**
     * 用户地址
     */
    private String customerAddr;
    /**
     * "用户联系方式"
     */
    private String customerContact;
    /**
     * 门牌编号
     */
    private String propertyName;
    /**
     * 用户姓名
     */
    private String customerName;



    /**
     * 用户唯一标识
     */
    private String customerNo;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 查询开始日期
     */
    private String startDate;

    /**
     * 查询结束日期
     */
    private String endDate;


    public String getMeterUserNo() {
        return meterUserNo;
    }




    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public Date getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(Date alarmTime) {
        this.alarmTime = alarmTime;
    }

    public Date getAlarmDealTime() {
        return alarmDealTime;
    }

    public void setAlarmDealTime(Date alarmDealTime) {
        this.alarmDealTime = alarmDealTime;
    }

    public String getAlarmDealDescribe() {
        return alarmDealDescribe;
    }

    public void setAlarmDealDescribe(String alarmDealDescribe) {
        this.alarmDealDescribe = alarmDealDescribe;
    }

    public String getAlarmDealPerson() {
        return alarmDealPerson;
    }

    public void setAlarmDealPerson(String alarmDealPerson) {
        this.alarmDealPerson = alarmDealPerson;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getEventContent() {
        return eventContent;
    }

    public void setEventContent(String eventContent) {
        this.eventContent = eventContent;
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

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    public Integer getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(Integer eventStatus) {
        this.eventStatus = eventStatus;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
