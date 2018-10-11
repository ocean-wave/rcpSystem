package cn.com.cdboost.collect.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;

/**
 * 首页统计站点列表
 */
public class FirstProjectCountDto {
    private String projectName;
    //利用率
    private BigDecimal useRate = BigDecimal.ZERO;
    //设备数
    private Integer deviceNum = 0;
    //线损率
    private BigDecimal lossRate = BigDecimal.ZERO;
    //盈利
    private BigDecimal projectProfitable = BigDecimal.ZERO;
    //充电次数
    private Integer chargingNum = 0;

    @JSONField(format = "yyyy-mm-dd")
    private String createTime;
    private String projectGuid;
    private BigDecimal basePrice;
    private BigDecimal upPrice;
    private BigDecimal price;
    private String communityName;
    private String projectAddr;
    private String companyName;
    private String contact;
    private String contactTelphone;
    private int schemeType;
    private String meterNo;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public BigDecimal getUseRate() {
        return useRate;
    }

    public void setUseRate(BigDecimal useRate) {
        this.useRate = useRate;
    }

    public Integer getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(Integer deviceNum) {
        this.deviceNum = deviceNum;
    }

    public BigDecimal getLossRate() {
        return lossRate;
    }

    public void setLossRate(BigDecimal lossRate) {
        this.lossRate = lossRate;
    }

    public BigDecimal getProjectProfitable() {
        return projectProfitable;
    }

    public void setProjectProfitable(BigDecimal projectProfitable) {
        this.projectProfitable = projectProfitable;
    }

    public Integer getChargingNum() {
        return chargingNum;
    }

    public void setChargingNum(Integer chargingNum) {
        this.chargingNum = chargingNum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getProjectGuid() {
        return projectGuid;
    }

    public void setProjectGuid(String projectGuid) {
        this.projectGuid = projectGuid;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public BigDecimal getUpPrice() {
        return upPrice;
    }

    public void setUpPrice(BigDecimal upPrice) {
        this.upPrice = upPrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getProjectAddr() {
        return projectAddr;
    }

    public void setProjectAddr(String projectAddr) {
        this.projectAddr = projectAddr;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactTelphone() {
        return contactTelphone;
    }

    public void setContactTelphone(String contactTelphone) {
        this.contactTelphone = contactTelphone;
    }

    public int getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(int schemeType) {
        this.schemeType = schemeType;
    }

    public String getMeterNo() {
        return meterNo;
    }

    public void setMeterNo(String meterNo) {
        this.meterNo = meterNo;
    }
}
