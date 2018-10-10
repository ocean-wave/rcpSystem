package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "em_d_smoke_device")
public class SmokeDevice implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    /**
     * 设备编号
     */
    private String cno;

    /**
     * 通信编号
     */
    @Column(name = "comm_no")
    private String commNo;

    /**
     * 状态(在线-1、离线-2、停用-3、告警-4、欠压-5
     */
    private String status;

    /**
     * 小区信息
     */
    private String resident;

    /**
     * 安装位置
     */
    @Column(name = "install_addr")
    private String installAddr;

    /**
     * 经度坐标
     */
    private BigDecimal lng;

    /**
     * 纬度坐标
     */
    private BigDecimal lat;

    /**
     * 负责人姓名
     */
    @Column(name = "charge_name")
    private String chargeName;

    /**
     * 负责人电话
     */
    @Column(name = "charge_contact")
    private String chargeContact;

    /**
     * 是否发送短信(发送-1、不发-0)
     */
    @Column(name = "is_sms")
    private String isSms;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取设备编号
     *
     * @return cno - 设备编号
     */
    public String getCno() {
        return cno;
    }

    /**
     * 设置设备编号
     *
     * @param cno 设备编号
     */
    public void setCno(String cno) {
        this.cno = cno;
    }

    /**
     * 获取通信编号
     *
     * @return comm_no - 通信编号
     */
    public String getCommNo() {
        return commNo;
    }

    /**
     * 设置通信编号
     *
     * @param commNo 通信编号
     */
    public void setCommNo(String commNo) {
        this.commNo = commNo;
    }

    /**
     * 获取状态(在线-1、离线-2、停用-3、告警-4、欠压-5
     *
     * @return status - 状态(在线-1、离线-2、停用-3、告警-4、欠压-5
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态(在线-1、离线-2、停用-3、告警-4、欠压-5
     *
     * @param status 状态(在线-1、离线-2、停用-3、告警-4、欠压-5
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取小区信息
     *
     * @return resident - 小区信息
     */
    public String getResident() {
        return resident;
    }

    /**
     * 设置小区信息
     *
     * @param resident 小区信息
     */
    public void setResident(String resident) {
        this.resident = resident;
    }

    /**
     * 获取安装位置
     *
     * @return install_addr - 安装位置
     */
    public String getInstallAddr() {
        return installAddr;
    }

    /**
     * 设置安装位置
     *
     * @param installAddr 安装位置
     */
    public void setInstallAddr(String installAddr) {
        this.installAddr = installAddr;
    }

    /**
     * 获取经度坐标
     *
     * @return lng - 经度坐标
     */
    public BigDecimal getLng() {
        return lng;
    }

    /**
     * 设置经度坐标
     *
     * @param lng 经度坐标
     */
    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    /**
     * 获取纬度坐标
     *
     * @return lat - 纬度坐标
     */
    public BigDecimal getLat() {
        return lat;
    }

    /**
     * 设置纬度坐标
     *
     * @param lat 纬度坐标
     */
    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    /**
     * 获取负责人姓名
     *
     * @return charge_name - 负责人姓名
     */
    public String getChargeName() {
        return chargeName;
    }

    /**
     * 设置负责人姓名
     *
     * @param chargeName 负责人姓名
     */
    public void setChargeName(String chargeName) {
        this.chargeName = chargeName;
    }

    /**
     * 获取负责人电话
     *
     * @return charge_contact - 负责人电话
     */
    public String getChargeContact() {
        return chargeContact;
    }

    /**
     * 设置负责人电话
     *
     * @param chargeContact 负责人电话
     */
    public void setChargeContact(String chargeContact) {
        this.chargeContact = chargeContact;
    }

    /**
     * 获取是否发送短信(发送-1、不发-0)
     *
     * @return is_sms - 是否发送短信(发送-1、不发-0)
     */
    public String getIsSms() {
        return isSms;
    }

    /**
     * 设置是否发送短信(发送-1、不发-0)
     *
     * @param isSms 是否发送短信(发送-1、不发-0)
     */
    public void setIsSms(String isSms) {
        this.isSms = isSms;
    }

    /**
     * 获取备注信息
     *
     * @return remark - 备注信息
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注信息
     *
     * @param remark 备注信息
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}