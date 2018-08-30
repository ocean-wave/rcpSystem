package cn.com.cdboost.collect.dto;

public class DeviceUserListDto {
    /**
     * 用户名称
     */
    private String customerName;
    /**
     * 用户编号
     */
    private String customerNo;
    /**
     * 表号
     */
    private String deviceNo;
    /**
     * 设备cno
     */
    private String cno;
    /**
     * 表计户号
     */
    private String meterUserNo;
    /**
     * 楼栋编号
     */
    private String buildNo;
    /**
     * 变压器号
     */
    private String transformerNo;

    /**
     * 安装地址
     * @return
     */
    private String installAddr;

    public String getInstallAddr() {
        return installAddr;
    }

    public void setInstallAddr(String installAddr) {
        this.installAddr = installAddr;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public String getMeterUserNo() {
        return meterUserNo;
    }

    public void setMeterUserNo(String meterUserNo) {
        this.meterUserNo = meterUserNo;
    }

    public String getBuildNo() {
        return buildNo;
    }

    public void setBuildNo(String buildNo) {
        this.buildNo = buildNo;
    }

    public String getTransformerNo() {
        return transformerNo;
    }

    public void setTransformerNo(String transformerNo) {
        this.transformerNo = transformerNo;
    }
}
