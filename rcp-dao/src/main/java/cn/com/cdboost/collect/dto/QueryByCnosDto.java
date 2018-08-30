package cn.com.cdboost.collect.dto;

public class QueryByCnosDto {
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 设备编号
     */
    private String deviceNo;
    /**
     * 安装地址
     */
    private String installAddr;
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
    private String buildingNo;
    /**
     * 变压器号
     */
    private String transformerNo;
    /**
     * 用户编号
     * @return
     */
    private String customerNo;

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

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getInstallAddr() {
        return installAddr;
    }

    public void setInstallAddr(String installAddr) {
        this.installAddr = installAddr;
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

    public String getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }

    public String getTransformerNo() {
        return transformerNo;
    }

    public void setTransformerNo(String transformerNo) {
        this.transformerNo = transformerNo;
    }
}
