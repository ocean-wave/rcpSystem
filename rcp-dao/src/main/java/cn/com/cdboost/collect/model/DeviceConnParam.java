package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "em_d_deviceconnparam")
public class DeviceConnParam implements Serializable {
    private static final long serialVersionUID = 6427586599101066920L;
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
     * 主站IP
     */
    @Column(name = "website_ip")
    private String websiteIp;

    /**
     * 主站端口
     */
    @Column(name = "website_port")
    private String websitePort;

    /**
     * 备用IP
     */
    @Column(name = "spare_ip")
    private String spareIp;

    /**
     * 备用端口
     */
    @Column(name = "spare_port")
    private String sparePort;

    /**
     * APN
     */
    private String apn;

    /**
     * 心跳周期(minute)
     */
    @Column(name = "hb_cycle")
    private Integer hbCycle;

    /**
     * 是否在线
     */
    @Column(name = "is_online")
    private Integer isOnline;

    /**
     * 最后在线时间
     */
    @Column(name = "last_online_time")
    private Date lastOnlineTime;

    /**
     * 终端通信地址(0-65535)
     */
    @Column(name = "com_addr")
    private Integer comAddr;

    /**
     * 终端区域码
     */
    @Column(name = "area_code")
    private Integer areaCode;

    /**
     * 采集方式 1 标识国网1376.1 2 标识LoraWan
     */
    @Column(name = "collect_model")
    private Integer collectModel;

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
     * 获取主站IP
     *
     * @return website_ip - 主站IP
     */
    public String getWebsiteIp() {
        return websiteIp;
    }

    /**
     * 设置主站IP
     *
     * @param websiteIp 主站IP
     */
    public void setWebsiteIp(String websiteIp) {
        this.websiteIp = websiteIp;
    }

    /**
     * 获取主站端口
     *
     * @return website_port - 主站端口
     */
    public String getWebsitePort() {
        return websitePort;
    }

    /**
     * 设置主站端口
     *
     * @param websitePort 主站端口
     */
    public void setWebsitePort(String websitePort) {
        this.websitePort = websitePort;
    }

    /**
     * 获取备用IP
     *
     * @return spare_ip - 备用IP
     */
    public String getSpareIp() {
        return spareIp;
    }

    /**
     * 设置备用IP
     *
     * @param spareIp 备用IP
     */
    public void setSpareIp(String spareIp) {
        this.spareIp = spareIp;
    }

    /**
     * 获取备用端口
     *
     * @return spare_port - 备用端口
     */
    public String getSparePort() {
        return sparePort;
    }

    /**
     * 设置备用端口
     *
     * @param sparePort 备用端口
     */
    public void setSparePort(String sparePort) {
        this.sparePort = sparePort;
    }

    /**
     * 获取APN
     *
     * @return apn - APN
     */
    public String getApn() {
        return apn;
    }

    /**
     * 设置APN
     *
     * @param apn APN
     */
    public void setApn(String apn) {
        this.apn = apn;
    }

    /**
     * 获取心跳周期(minute)
     *
     * @return hb_cycle - 心跳周期(minute)
     */
    public Integer getHbCycle() {
        return hbCycle;
    }

    /**
     * 设置心跳周期(minute)
     *
     * @param hbCycle 心跳周期(minute)
     */
    public void setHbCycle(Integer hbCycle) {
        this.hbCycle = hbCycle;
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
     * 获取最后在线时间
     *
     * @return last_online_time - 最后在线时间
     */
    public Date getLastOnlineTime() {
        return lastOnlineTime;
    }

    /**
     * 设置最后在线时间
     *
     * @param lastOnlineTime 最后在线时间
     */
    public void setLastOnlineTime(Date lastOnlineTime) {
        this.lastOnlineTime = lastOnlineTime;
    }

    /**
     * 获取终端通信地址(0-65535)
     *
     * @return com_addr - 终端通信地址(0-65535)
     */
    public Integer getComAddr() {
        return comAddr;
    }

    /**
     * 设置终端通信地址(0-65535)
     *
     * @param comAddr 终端通信地址(0-65535)
     */
    public void setComAddr(Integer comAddr) {
        this.comAddr = comAddr;
    }

    /**
     * 获取终端区域码
     *
     * @return area_code - 终端区域码
     */
    public Integer getAreaCode() {
        return areaCode;
    }

    /**
     * 设置终端区域码
     *
     * @param areaCode 终端区域码
     */
    public void setAreaCode(Integer areaCode) {
        this.areaCode = areaCode;
    }

    /**
     * 获取采集方式 1 标识国网1376.1 2 标识LoraWan
     *
     * @return collect_model - 采集方式 1 标识国网1376.1 2 标识LoraWan
     */
    public Integer getCollectModel() {
        return collectModel;
    }

    /**
     * 设置采集方式 1 标识国网1376.1 2 标识LoraWan
     *
     * @param collectModel 采集方式 1 标识国网1376.1 2 标识LoraWan
     */
    public void setCollectModel(Integer collectModel) {
        this.collectModel = collectModel;
    }
}