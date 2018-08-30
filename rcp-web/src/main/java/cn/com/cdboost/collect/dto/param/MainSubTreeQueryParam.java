package cn.com.cdboost.collect.dto.param;

/**
 * 实时数据，树形结构查询参数
 */
public class MainSubTreeQueryParam {
    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 节点id
     */
    private String nodeId;

    /**
     * 在线状态，1在线，0不在线，不传查所有状态
     */
    private Integer onlineStatus;

    /**
     * 是否重点用户 1重点用户，0非重点用户
     */
    private Integer isImportant;

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
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
}
