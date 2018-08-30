package cn.com.cdboost.collect.dto.param;

import java.math.BigDecimal;

/**
 * 用户档案编辑传入vo
 */
public class CustomerInfoEditVo {

    /**
     * 主键
     */
    private Long id;

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
     * 部门机构编号
     */
    private Long orgNo;

    /**
     * 备注
     */
    private String remark;


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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(Long orgNo) {
        this.orgNo = orgNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCustomerBrand() {
        return customerBrand;
    }

    public void setCustomerBrand(String customerBrand) {
        this.customerBrand = customerBrand;
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
