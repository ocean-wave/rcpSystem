package cn.com.cdboost.collect.dto.param;

/**
 * 用户档案查询vo
 */
public class DeviceInfoQueryVo extends PageQueryVo{
    /**
     * 设备编号
     */
    private String deviceNo;
    private String deviceFactory;
    private String installDateSTime;
    private String installDateETime;
    private String deviceType;
    private String importGuid;
    /**
     * 在线状态
     */
    private String isOnline;
    private Long userId;
    private String nodeType;
    private String nodeId;
    private Integer isSearchChild;

    public Integer getIsSearchChild() {
        return isSearchChild;
    }

    public void setIsSearchChild(Integer isSearchChild) {
        this.isSearchChild = isSearchChild;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getImportGuid() {
        return importGuid;
    }

    public void setImportGuid(String importGuid) {
        this.importGuid = importGuid;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getDeviceFactory() {
        return deviceFactory;
    }

    public void setDeviceFactory(String deviceFactory) {
        this.deviceFactory = deviceFactory;
    }

    public String getInstallDateSTime() {
        return installDateSTime;
    }

    public void setInstallDateSTime(String installDateSTime) {
        this.installDateSTime = installDateSTime;
    }

    public String getInstallDateETime() {
        return installDateETime;
    }

    public void setInstallDateETime(String installDateETime) {
        this.installDateETime = installDateETime;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(String isOnline) {
        this.isOnline = isOnline;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
