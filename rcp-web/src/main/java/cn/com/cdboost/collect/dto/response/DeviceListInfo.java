package cn.com.cdboost.collect.dto.response;

import java.util.Date;

/**
 * 设备档案，列表显示信息
 */
public class DeviceListInfo {
    /**
     * 在线状态
     */
    private Integer isOnline;

    /**
     * 设备编号
     */
    private String deviceNo;

    /**
     * 设备cno
     */
    private String deviceCno;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 设备厂家
     */
    private String deviceFactory;

    /**
     * 安装位置
     */
    private String installAddr;

    /**
     * 备注
     */
    private String remark;

    private Date lastOnlineTime;

    public Date getLastOnlineTime() {
        return lastOnlineTime;
    }

    public void setLastOnlineTime(Date lastOnlineTime) {
        this.lastOnlineTime = lastOnlineTime;
    }

    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getDeviceCno() {
        return deviceCno;
    }

    public void setDeviceCno(String deviceCno) {
        this.deviceCno = deviceCno;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDeviceFactory() {
        return deviceFactory;
    }

    public void setDeviceFactory(String deviceFactory) {
        this.deviceFactory = deviceFactory;
    }

    public String getInstallAddr() {
        return installAddr;
    }

    public void setInstallAddr(String installAddr) {
        this.installAddr = installAddr;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
