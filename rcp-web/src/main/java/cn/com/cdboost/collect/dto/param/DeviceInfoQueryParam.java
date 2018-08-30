package cn.com.cdboost.collect.dto.param;

import cn.com.cdboost.collect.vo.QueryListParam;

/**
 * 设备信息查询参数
 */
public class DeviceInfoQueryParam extends QueryListParam{
    /**
     * 设备编号
     */
    private String deviceNo;
    private String importGuid;
    private String nodeType;
    private String nodeId;
    private Integer isSearchChild;
    /**
     * 设备厂家
     */
    private String deviceFactory;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 在线状态 1在线，0离线
     */
    private Integer isOnline;

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

    @Override
    protected String defaultSortName() {
        return null;
    }

    @Override
    protected String defaultSortOrder() {
        return null;
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

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }
}
