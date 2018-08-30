package cn.com.cdboost.collect.dto.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 客户档案明细，关于客户相关的基本信息
 */
public class CustomerInfoDetailInfo {
    /**
     * 剩余金额
     */
    private BigDecimal initAmount;
    /**
     * 用户姓名
     */
    private String customerName;

    /**
     * 联系电话
     */
    private String customerContact;

    /**
     * 用户地址
     */
    private String customerAddr;

    /**
     * 门牌编号
     */
    private String propertyName;

    /**
     * 组织名称
     */
    private String orgName;

    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 客户唯一标识
     */
    private String customerNo;

    /**
     * 组织编号
     */
    private Long orgNo;

    /**
     * 用户状态（前端判断用，销毁状态是不允许编辑的）
     */
    private Integer isEnabled;

    /**
     * 主键
     */
    private Long id;

    /**
     * 电话号码
     */

    private String mobilePhones;


    /**
     * 添加品牌
     */
    private String customerBrand;
    /**
     * 楼栋编号
     */
    private String buildNo;

    /**
     * 楼栋名称
     */
    private String buildName;

    /**
     * 告警阈值1
     */
    private BigDecimal alarmThreshold;

    /**
     * 告警阈值2
     */
    private BigDecimal alarmThreshold1;

    /**
     * 告警阈值3
     */
    private BigDecimal alarmThreshold2;

    /**
     * 透支金额
     */
    private Integer overdraftAmount;

    /**
     * 0 立即拉闸 1-延时拉闸 2-不拉闸
     */
    private Integer offScheme;

    /**
     * 延时拉闸时间,单位小时
     */
    private Integer offParam;

    public BigDecimal getInitAmount() {
        return initAmount;
    }

    public void setInitAmount(BigDecimal initAmount) {
        this.initAmount = initAmount;
    }

    public String getBuildNo() {
        return buildNo;
    }

    public void setBuildNo(String buildNo) {
        this.buildNo = buildNo;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
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

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public Long getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(Long orgNo) {
        this.orgNo = orgNo;
    }

    public Integer getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getMobilePhones() {
        return mobilePhones;
    }

    public void setMobilePhones(String mobilePhones) {
        this.mobilePhones = mobilePhones;
    }

    public String getCustomerBrand() {
        return customerBrand;
    }

    public void setCustomerBrand(String customerBrand) {
        this.customerBrand = customerBrand;
    }

    public BigDecimal getAlarmThreshold() {
        return alarmThreshold;
    }

    public void setAlarmThreshold(BigDecimal alarmThreshold) {
        this.alarmThreshold = alarmThreshold;
    }

    public BigDecimal getAlarmThreshold1() {
        return alarmThreshold1;
    }

    public void setAlarmThreshold1(BigDecimal alarmThreshold1) {
        this.alarmThreshold1 = alarmThreshold1;
    }

    public BigDecimal getAlarmThreshold2() {
        return alarmThreshold2;
    }

    public void setAlarmThreshold2(BigDecimal alarmThreshold2) {
        this.alarmThreshold2 = alarmThreshold2;
    }

    public Integer getOverdraftAmount() {
        return overdraftAmount;
    }

    public void setOverdraftAmount(Integer overdraftAmount) {
        this.overdraftAmount = overdraftAmount;
    }

    public Integer getOffScheme() {
        return offScheme;
    }

    public void setOffScheme(Integer offScheme) {
        this.offScheme = offScheme;
    }

    public Integer getOffParam() {
        return offParam;
    }

    public void setOffParam(Integer offParam) {
        this.offParam = offParam;
    }
}
