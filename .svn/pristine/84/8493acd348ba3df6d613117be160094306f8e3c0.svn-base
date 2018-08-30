package cn.com.cdboost.collect.dto.param;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 项目修改
 */
public class ChargerProjectEditParam {
    @NotBlank(message = "projectName不能为空")
    @Valid
    private String projectName;

    @NotBlank(message = "projectGuid不能为空")
    private String projectGuid;

    @NotBlank(message = "communityName不能为空")
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

    public String getProjectGuid() {
        return projectGuid;
    }

    public void setProjectGuid(String projectGuid) {
        this.projectGuid = projectGuid;
    }
}
