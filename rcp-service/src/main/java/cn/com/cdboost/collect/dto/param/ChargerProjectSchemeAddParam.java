package cn.com.cdboost.collect.dto.param;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 项目方案新增
 */
public class ChargerProjectSchemeAddParam {
    @NotBlank(message = "projectName不能为空")
    @Valid
    private String projectName;

    private String communityName;

    @NotBlank(message = "projectAddr不能为空")
    private String projectAddr;

    @NotNull(message = "basePrice不能为null")
    private BigDecimal basePrice;

    @NotNull(message = "upPrice不能为null")
    private BigDecimal upPrice;
    //物业名称
    private String companyName;
    //联系人员
    private String contact;
    //联系电话
    private String contactTelphone;

    private String remark;

    //所属组织
    @NotNull(message = "orgNo不能为null")
    private Long orgNo;

    //方案类型
    private Integer schemeType;
    /**
     *总表表号
     */
    @NotBlank(message = "meterNo不能为空")
    private String meterNo;

    /*@Valid
    private ChargingSchemeDto monthly;
    @Valid
    private List<TemporarySchemeDto> temporary;*/

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(Long orgNo) {
        this.orgNo = orgNo;
    }

    public Integer getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(Integer schemeType) {
        this.schemeType = schemeType;
    }

    public String getMeterNo() {
        return meterNo;
    }

    public void setMeterNo(String meterNo) {
        this.meterNo = meterNo;
    }
}
