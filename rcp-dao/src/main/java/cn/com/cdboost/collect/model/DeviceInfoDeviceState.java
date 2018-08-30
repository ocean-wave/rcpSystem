package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "em_d_deviceinfo_devicestate")
public class DeviceInfoDeviceState implements Serializable {
    private static final long serialVersionUID = -5933388319331598368L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 设备唯一标识(生成规则:设备类型+补0(不足30位)+设备编号）
     */
    private String cno;

    /**
     * 是否在线
     */
    @Column(name = "is_online")
    private Integer isOnline;

    /**
     * 最后一次活动时间
     */
    @Column(name = "last_online_time")
    private Date lastOnlineTime;

    /**
     * 应用EUI
     */
    @Column(name = "app_eui")
    private String appEui;

    /**
     * 通信端口号（直联-1，载波-31,lora-32 ）
     */
    @Column(name = "comm_port")
    private Integer commPort;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取设备唯一标识(生成规则:设备类型+补0(不足30位)+设备编号）
     *
     * @return cno - 设备唯一标识(生成规则:设备类型+补0(不足30位)+设备编号）
     */
    public String getCno() {
        return cno;
    }

    /**
     * 设置设备唯一标识(生成规则:设备类型+补0(不足30位)+设备编号）
     *
     * @param cno 设备唯一标识(生成规则:设备类型+补0(不足30位)+设备编号）
     */
    public void setCno(String cno) {
        this.cno = cno;
    }

    /**
     * 获取是否在线
     *
     * @return is_online - 是否在线
     */
    public Integer getIsOnline() {
        return isOnline;
    }

    /**
     * 设置是否在线
     *
     * @param isOnline 是否在线
     */
    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }

    /**
     * 获取最后一次活动时间
     *
     * @return last_online_time - 最后一次活动时间
     */
    public Date getLastOnlineTime() {
        return lastOnlineTime;
    }

    /**
     * 设置最后一次活动时间
     *
     * @param lastOnlineTime 最后一次活动时间
     */
    public void setLastOnlineTime(Date lastOnlineTime) {
        this.lastOnlineTime = lastOnlineTime;
    }

    public String getAppEui() {
        return appEui;
    }

    public void setAppEui(String appEui) {
        this.appEui = appEui;
    }

    public Integer getCommPort() {
        return commPort;
    }

    public void setCommPort(Integer commPort) {
        this.commPort = commPort;
    }
}