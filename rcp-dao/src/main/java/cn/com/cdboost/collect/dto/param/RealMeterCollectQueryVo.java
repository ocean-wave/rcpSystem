package cn.com.cdboost.collect.dto.param;

import cn.com.cdboost.collect.vo.QueryListParam;

/**
 * 实时数据采集查询vo
 */
public class RealMeterCollectQueryVo extends QueryListParam {
    /**
     * 集中器编号
     */
    private String jzqNo;

    /**
     * 设备编号
     */
    private String deviceNo;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 用户姓名
     */
    private String customerName;

    /**
     * 用户地址
     */
    private String customerAddr;

    /**
     * 联系方式
     */
    private String customerContact;

    /**
     * 表计户号
     */
    private String meterUserNo;

    /**
     * 门牌编号
     */
    private String propertyName;

    /**
     * guid
     */
    private String guid;

    /**
     * searchDate
     */
    private String searchDate;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 0不在线 1在线
     */
    private String isOnline;

    public String getJzqNo() {
        return jzqNo;
    }

    public void setJzqNo(String jzqNo) {
        this.jzqNo = jzqNo;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
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

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(String searchDate) {
        this.searchDate = searchDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(String isOnline) {
        this.isOnline = isOnline;
    }

    @Override
    protected String defaultSortName() {
        return null;
    }

    @Override
    protected String defaultSortOrder() {
        return null;
    }
}
