package cn.com.cdboost.collect.dto.param;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 客户档案明细页面，电表修改传入参数
 */
public class ElectricMeterEditParam extends ElectricMeterAddParam {

    /**
     * 组织编号
     */
    @NotNull(message = "orgNo不能为null")
    private Long orgNo;

    /**
     * 客户唯一标识
     */
    @NotBlank(message = "customerNo不能为空")
    private String customerNo;

    public Long getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(Long orgNo) {
        this.orgNo = orgNo;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }
}
