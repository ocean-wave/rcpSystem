package cn.com.cdboost.collect.dto.response;

import java.math.BigDecimal;

/**
 * 实时数据列表返回字段
 */
public class CollectDataGetInfo {
    /**
     * 设备cno
     */
    private String deviceCno;

    /**
     * 设备编号
     */
    private String deviceNo;

    /**
     * 用户唯一标识
     */
    private String customerNo;

    /**
     * 用户名称
     */
    private String customerName;

    /**
     * 联系方式
     */
    private String customerContact;

    /**
     * 用户地址
     */
    private String customerAddr;

    /**
     * 采集时间
     */
    private String collectTime;

    /**
     * 是否实时
     */
    private Integer isRealTime ;

    /**
     * 组织名称
     */
    private String orgName;

    /**
     * 门牌编号
     */
    private String propertyName;

    /**
     * 开户状态
     */
    private int isAccount;

    /**
     * 有功总
     */
    private BigDecimal pr0;

    /**
     * 剩余金额
     */
    private BigDecimal balance;

    /**
     * 表计户号
     */
    private String meterUserNo;
    /**
     * 日冻结总
     */
    private BigDecimal dayFreezeP;

    /**
     * 月冻结总
     */
    private BigDecimal monthFreezeP;

    public BigDecimal getDayFreezeP() {
        return dayFreezeP;
    }

    public void setDayFreezeP(BigDecimal dayFreezeP) {
        this.dayFreezeP = dayFreezeP;
    }

    public BigDecimal getMonthFreezeP() {
        return monthFreezeP;
    }

    public void setMonthFreezeP(BigDecimal monthFreezeP) {
        this.monthFreezeP = monthFreezeP;
    }

    public String getDeviceCno() {
        return deviceCno;
    }

    public void setDeviceCno(String deviceCno) {
        this.deviceCno = deviceCno;
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

    public String getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(String collectTime) {
        this.collectTime = collectTime;
    }

    public Integer getIsRealTime() {
        return isRealTime;
    }

    public void setIsRealTime(Integer isRealTime) {
        this.isRealTime = isRealTime;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public int getIsAccount() {
        return isAccount;
    }

    public void setIsAccount(int isAccount) {
        this.isAccount = isAccount;
    }

    public BigDecimal getPr0() {
        return pr0;
    }

    public void setPr0(BigDecimal pr0) {
        this.pr0 = pr0;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getMeterUserNo() {
        return meterUserNo;
    }

    public void setMeterUserNo(String meterUserNo) {
        this.meterUserNo = meterUserNo;
    }
}
