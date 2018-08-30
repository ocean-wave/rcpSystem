package cn.com.cdboost.collect.dto.param;

/**
 * 通断查询vo
 */
public class QueryEventVo extends PageQueryVo{
    private String cno;
    private Integer id;
    /**
     *表计户号
     */
    private String meterUserNo="";
    /**
     * 设备编号
     */
    private String deviceNo="";
    /**
     * 设备安装地址
     */
    private String installAddr="";
    /**
     * "用户联系方式"
     */
    private String customerContact="";
    /**
     * 门牌编号
     */
    private String propertyName="";
    /**
     * 用户姓名
     */
    private String customerName="";
    /**
     * 事件等级
     */
    private String permissionId="" ;
    /**
     * 用户唯一标识
     */
    private String customerNo="";

    /**
     * 设备类型
     */
    private String deviceType="";

    /**
     * 查询开始日期
     */
    private String startDate="";

    /**
     * 查询结束日期
     */
    private String endDate="";
    /**
     * 全部类别
     */
    private String eventCategory="";
    /**
     * 全部状态
     */
    private String eventStatus="";

    private String[] permissionIdlist;
    public String getPermissionId() {
        return permissionId;
    }

    public String[] getPermissionIdlist() {
        return permissionIdlist;
    }

    public void setPermissionIdlist(String[] permissionIdlist) {
        this.permissionIdlist = permissionIdlist;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMeterUserNo() {
        return meterUserNo;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public void setMeterUserNo(String meterUserNo) {
        this.meterUserNo = meterUserNo;
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

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
