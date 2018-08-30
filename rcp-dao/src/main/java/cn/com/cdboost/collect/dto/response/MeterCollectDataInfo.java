package cn.com.cdboost.collect.dto.response;

import java.math.BigDecimal;

/**
 * 数据库层返回,实时采集数据列表
 */
public class MeterCollectDataInfo {
    /**
     * 设备唯一标识
     */
    private String deviceCno;

    /**
     * 是否开户( 0未IC开户 1开户制卡失败 2开户成功)
     */
    private Integer isAccount;

    /**
     * 设备编号
     */
    private String deviceNo;

    /**
     * 门牌编号
     */
    private String propertyName;

    /**
     * 用户在系统的唯一标识
     */
    private String customerNo;

    /**
     * 表计户号
     */
    private String meterUserNo;

    /**
     * 用户名
     */
    private String customerName;

    /**
     * 用户联系方式
     */
    private String customerContact;

    /**
     * 用户地址
     */
    private String customerAddr;

    /**
     * 是否在线
     */
    private Integer isOnline;

    /**
     * 集中器唯一标识
     */
    private String jzqCno;

    /**
     * 发送短信标识
     */
    private Integer sendFlag;

    /**
     * 规约
     */
    private Integer commRule;

    /**
     * 所属组织名称
     */
    private String orgName;

    /**
     * 抄表数据总和
     */
    private BigDecimal pr0;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 采集时间
     */
    private String collectTime;

    /**
     * groupGuid
     */
    private String groupGuid;

    /**
     * 告警阀值
     */
    private BigDecimal alarmThreshold;

    /**
     * 招测标识（0未招测，1召测成功，-1召测失败）
     */
    private Integer readStatus;

    /**
     * 节点类型
     */
    private String moteType;
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

    public Integer getIsAccount() {
        return isAccount;
    }

    public void setIsAccount(Integer isAccount) {
        this.isAccount = isAccount;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
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

    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }

    public String getJzqCno() {
        return jzqCno;
    }

    public void setJzqCno(String jzqCno) {
        this.jzqCno = jzqCno;
    }

    public Integer getSendFlag() {
        return sendFlag;
    }

    public void setSendFlag(Integer sendFlag) {
        this.sendFlag = sendFlag;
    }

    public Integer getCommRule() {
        return commRule;
    }

    public void setCommRule(Integer commRule) {
        this.commRule = commRule;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
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

    public String getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(String collectTime) {
        this.collectTime = collectTime;
    }

    public String getGroupGuid() {
        return groupGuid;
    }

    public void setGroupGuid(String groupGuid) {
        this.groupGuid = groupGuid;
    }

    public BigDecimal getAlarmThreshold() {
        return alarmThreshold;
    }

    public void setAlarmThreshold(BigDecimal alarmThreshold) {
        this.alarmThreshold = alarmThreshold;
    }

    public Integer getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(Integer readStatus) {
        this.readStatus = readStatus;
    }

    public String getMoteType() {
        return moteType;
    }

    public void setMoteType(String moteType) {
        this.moteType = moteType;
    }
}
