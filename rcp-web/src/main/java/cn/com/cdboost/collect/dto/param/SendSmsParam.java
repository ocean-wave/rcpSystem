package cn.com.cdboost.collect.dto.param;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 发送短信时，前端传入参数
 */
public class SendSmsParam {
    /**
     * 用户编号
     */
    @NotBlank(message = "customerNo不能为空")
    private String customerNo;

    /**
     * 用户名称
     */
    @NotBlank(message = "customerName不能为空")
    private String customerName;

    /**
     * 截止日期
     */
    @NotBlank(message = "collectDate不能为空")
    private String collectDate;

    /**
     * 剩余金额
     */
    @NotBlank(message = "readValue不能为空")
    private String readValue;

    /**
     * 告警阈值
     */
    @NotBlank(message = "alarmThreshold不能为空")
    private String alarmThreshold;

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCollectDate() {
        return collectDate;
    }

    public void setCollectDate(String collectDate) {
        this.collectDate = collectDate;
    }

    public String getReadValue() {
        return readValue;
    }

    public void setReadValue(String readValue) {
        this.readValue = readValue;
    }

    public String getAlarmThreshold() {
        return alarmThreshold;
    }

    public void setAlarmThreshold(String alarmThreshold) {
        this.alarmThreshold = alarmThreshold;
    }
}
