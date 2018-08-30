package cn.com.cdboost.collect.dto.param;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 换表操作（水电气），需要传的一些公共参数
 */
public class ChangeMeterBaseParam {
    /**
     * 客户唯一标识
     */
    @NotBlank(message = "customerNo不能为空")
    private String customerNo;

    /**
     * 更换原因
     */
    @NotBlank(message = "changeRemark不能为空")
    private String changeRemark;

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getChangeRemark() {
        return changeRemark;
    }

    public void setChangeRemark(String changeRemark) {
        this.changeRemark = changeRemark;
    }
}
