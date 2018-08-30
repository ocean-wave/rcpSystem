package cn.com.cdboost.collect.dto.param;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 客户档案新增时，传入参数
 */
public class CustomerInfoAddParam {
    /**
     * 用户姓名
     */
    @NotBlank(message = "customerName不能为空")
    private String customerName;
    /**
     * 预留金额
     */
    @NotNull(message = "initAmount不能为null")
    private BigDecimal initAmount;
    /**
     * 联系电话
     */
    @NotBlank(message = "customerContact不能为空")
    private String customerContact;

    /**
     * 用户地址
     */
    @NotBlank(message = "customerAddr不能为空")
    private String customerAddr;

    /**
     * 门牌编号
     */
    @NotBlank(message = "propertyName不能为空")
    private String propertyName;

    /**
     * 部门机构编号
     */
    @NotNull(message = "orgNo不能为null")
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
    @NotBlank(message = "楼栋编号不能为空")
    private String buildNo;

    /**
     * 楼栋名称
     */
    @NotBlank(message = "楼栋名称不能为空")
    private String buildName;

    /**
     * 告警阈值1
     */
    @NotNull(message = "alarmThreshold不能为null")
    @Min(value = 0)
    private BigDecimal alarmThreshold;

    /**
     * 告警阈值2
     */
    @NotNull(message = "alarmThreshold1不能为null")
    @Min(value = 0)
    private BigDecimal alarmThreshold1;

    /**
     * 告警阈值3
     */
    @NotNull(message = "alarmThreshold2不能为null")
    @Min(value = 0)
    private BigDecimal alarmThreshold2;

    /**
     * 透支金额
     */
    private Integer overdraftAmount;

    /**
     * 0 立即拉闸 1-延时拉闸 2-不拉闸
     */
    @NotNull(message = "offScheme不能为null")
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
