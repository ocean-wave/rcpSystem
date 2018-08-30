package cn.com.cdboost.collect.dto.param;

/**
 * 阿里云短信，充值到账短信模板参数
 */
public class RemoteSuccessTempParam {
    /**
     * 用户名称
     */
    private String customerName;

    /**
     * 支付时间
     */
    private String payTime;

    /**
     * 支付方式
     */
    private String payMethodName;

    /**
     * 支付金额（单位元）
     */
    private String payMoney;

    /**
     * 费用类型
     */
    private String feeType;

    /**
     * 设备编号
     */
    private String deviceNo;

    /**
     * 设备类型名称
     */
    private String deviceTypeName;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getPayMethodName() {
        return payMethodName;
    }

    public void setPayMethodName(String payMethodName) {
        this.payMethodName = payMethodName;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
    }
}
