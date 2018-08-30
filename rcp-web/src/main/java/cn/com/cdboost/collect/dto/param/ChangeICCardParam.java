package cn.com.cdboost.collect.dto.param;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 换卡（补卡）参数
 */
public class ChangeICCardParam {
    /**
     * 设备cno
     */
    @NotBlank(message = "cno不能为空")
    private String cno;

    /**
     * 客户唯一标识
     */
    @NotBlank(message = "customerNo不能为空")
    private String customerNo;

    /**
     * 换表备注
     */
    @NotBlank(message = "changeRemark不能为空")
    private String changeRemark;

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

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
