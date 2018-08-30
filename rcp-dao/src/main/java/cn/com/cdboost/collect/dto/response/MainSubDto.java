package cn.com.cdboost.collect.dto.response;

/**
 * 总表信息
 */
public class MainSubDto {
    /**
     * 客户唯一标识
     */
    private String customerNo;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 设备编号
     */
    private String deviceNo;

    /**
     * 父节点cno，如果是总表为"0"
     */
    private String pMeterCno;

    /**
     * 设备cno
     */
    private String meterCno;

    /**
     * 层级关系
     */
    private String level;

    /**
     * 组织编号
     */
    private Long orgNo;

    /**
     * 在线状态
     */
    private Integer onlineStatus;

    /**
     * 是否重点表
     */
    private Integer isImportant;

    /**
     * 是否存在子表
     */
    private Boolean hasChild = false;

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

    public String getpMeterCno() {
        return pMeterCno;
    }

    public void setpMeterCno(String pMeterCno) {
        this.pMeterCno = pMeterCno;
    }

    public String getMeterCno() {
        return meterCno;
    }

    public void setMeterCno(String meterCno) {
        this.meterCno = meterCno;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Long getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(Long orgNo) {
        this.orgNo = orgNo;
    }

    public Integer getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Integer onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public Integer getIsImportant() {
        return isImportant;
    }

    public void setIsImportant(Integer isImportant) {
        this.isImportant = isImportant;
    }

    public Boolean getHasChild() {
        return hasChild;
    }

    public void setHasChild(Boolean hasChild) {
        this.hasChild = hasChild;
    }
}
