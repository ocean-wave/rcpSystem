package cn.com.cdboost.collect.dto.param;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 补抄工单对应的用户设备信息
 */
public class WorkOrderDeviceParam {
    /**
     * 要补抄的设备cno
     */
    @NotBlank(message = "cno不能为空")
    private String cno;

    /**
     * 客户唯一标识
     */
    @NotBlank(message = "customerNo不能为空")
    private String customerNo;

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


}
