package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "em_d_deviceinfo")
public class DeviceInfo implements Serializable {
    private static final long serialVersionUID = 266951197879727336L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 设备唯一标识(生成规则:设备类型+补0(不足30位)+设备编号）
     */
    private String cno;

    /**
     * 设备编号
     */
    @Column(name = "device_no")
    private String deviceNo;

    /**
     * 资产编号
     */
    @Column(name = "assets_no")
    private String assetsNo;

    /**
     * 设备类型(01-LoRa网管，02-LoRa节点，04-集中器，05-采集器，07-电表 08 标识水表 09 标识气表)
     */
    @Column(name = "device_type")
    private String deviceType;

    /**
     * 上级设备编号
     */
    @Column(name = "p_device_no")
    private String pDeviceNo;

    /**
     * 设备型号
     */
    @Column(name = "device_model")
    private String deviceModel;

    /**
     * 设备厂家
     */
    @Column(name = "device_factory")
    private String deviceFactory;

    /**
     * 安装日期
     */
    @Column(name = "install_date")
    private Date installDate;

    /**
     * 安装位置
     */
    @Column(name = "install_addr")
    private String installAddr;

    /**
     * 运行状态(拆卸,停电,停运,正常)
     */
    @Column(name = "run_state")
    private String runState;

    /**
     * 坐标类型（0 标识无坐标 1 原生GPS坐标2 基站坐标3 百度GPS坐标4 百度网络坐标  5 父级坐标 6手选坐标）
     */
    @Column(name = "coord_type")
    private Integer coordType;

    /**
     * 经度
     */
    private BigDecimal lng;

    /**
     * 纬度
     */
    private BigDecimal lat;

    /**
     * 创建人员ID
     */
    @Column(name = "create_user_id")
    private Long createUserId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新人员ID
     */
    @Column(name = "update_user_id")
    private Long updateUserId;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 是否自动发催费短信（0-否，1-是）
     */
    @Column(name = "is_auto_sms")
    private Integer isAutoSms;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否有效
     */
    @Column(name = "is_enabled")
    private Integer isEnabled;

    /**
     * 状态依赖设备编号
     */
    @Column(name = "rely_cno")
    private String relyCno;

    /**
     * 通断状态(0 -断开 1-连通)
     */
    @Column(name = "is_on")
    private Integer isOn;

    /**
     * 通断操作人
     */
    @Column(name = "on_off_opt_user")
    private Long onOffOptUser;

    /**
     * 最后一次通断的时间
     */
    @Column(name = "last_on_off_time")
    private Date lastOnOffTime;

    /**
     * 所属组织机构
     */
    @Column(name = "org_no")
    private Long orgNo;

    /**
     * 0 立即拉闸 1-延时拉闸 2-不拉闸
     */
    @Column(name = "off_scheme")
    private Integer offScheme;

    /**
     * 延时拉闸时间,单位小时
     */
    @Column(name = "off_param")
    private Integer offParam;

    /**
     * 设备厂家标识
     */
    @Column(name = "device_factory_val")
    private String deviceFactoryVal;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
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
     * 获取设备编号
     *
     * @return device_no - 设备编号
     */
    public String getDeviceNo() {
        return deviceNo;
    }

    /**
     * 设置设备编号
     *
     * @param deviceNo 设备编号
     */
    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    /**
     * 获取资产编号
     *
     * @return assets_no - 资产编号
     */
    public String getAssetsNo() {
        return assetsNo;
    }

    /**
     * 设置资产编号
     *
     * @param assetsNo 资产编号
     */
    public void setAssetsNo(String assetsNo) {
        this.assetsNo = assetsNo;
    }

    /**
     * 获取设备类型(01-LoRa网管，02-LoRa节点，04-集中器，05-采集器，07-电表 08 标识水表 09 标识气表)
     *
     * @return device_type - 设备类型(01-LoRa网管，02-LoRa节点，04-集中器，05-采集器，07-电表 08 标识水表 09 标识气表)
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * 设置设备类型(01-LoRa网管，02-LoRa节点，04-集中器，05-采集器，07-电表 08 标识水表 09 标识气表)
     *
     * @param deviceType 设备类型(01-LoRa网管，02-LoRa节点，04-集中器，05-采集器，07-电表 08 标识水表 09 标识气表)
     */
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * 获取上级设备编号
     *
     * @return p_device_no - 上级设备编号
     */
    public String getpDeviceNo() {
        return pDeviceNo;
    }

    /**
     * 设置上级设备编号
     *
     * @param pDeviceNo 上级设备编号
     */
    public void setpDeviceNo(String pDeviceNo) {
        this.pDeviceNo = pDeviceNo;
    }

    /**
     * 获取设备型号
     *
     * @return device_model - 设备型号
     */
    public String getDeviceModel() {
        return deviceModel;
    }

    /**
     * 设置设备型号
     *
     * @param deviceModel 设备型号
     */
    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    /**
     * 获取设备厂家
     *
     * @return device_factory - 设备厂家
     */
    public String getDeviceFactory() {
        return deviceFactory;
    }

    /**
     * 设置设备厂家
     *
     * @param deviceFactory 设备厂家
     */
    public void setDeviceFactory(String deviceFactory) {
        this.deviceFactory = deviceFactory;
    }

    /**
     * 获取安装日期
     *
     * @return install_date - 安装日期
     */
    public Date getInstallDate() {
        return installDate;
    }

    /**
     * 设置安装日期
     *
     * @param installDate 安装日期
     */
    public void setInstallDate(Date installDate) {
        this.installDate = installDate;
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
     * 获取运行状态(拆卸,停电,停运,正常)
     *
     * @return run_state - 运行状态(拆卸,停电,停运,正常)
     */
    public String getRunState() {
        return runState;
    }

    /**
     * 设置运行状态(拆卸,停电,停运,正常)
     *
     * @param runState 运行状态(拆卸,停电,停运,正常)
     */
    public void setRunState(String runState) {
        this.runState = runState;
    }

    /**
     * 获取坐标类型（0 标识无坐标 1 原生GPS坐标2 基站坐标3 百度GPS坐标4 百度网络坐标  5 父级坐标 6手选坐标）
     *
     * @return coord_type - 坐标类型（0 标识无坐标 1 原生GPS坐标2 基站坐标3 百度GPS坐标4 百度网络坐标  5 父级坐标 6手选坐标）
     */
    public Integer getCoordType() {
        return coordType;
    }

    /**
     * 设置坐标类型（0 标识无坐标 1 原生GPS坐标2 基站坐标3 百度GPS坐标4 百度网络坐标  5 父级坐标 6手选坐标）
     *
     * @param coordType 坐标类型（0 标识无坐标 1 原生GPS坐标2 基站坐标3 百度GPS坐标4 百度网络坐标  5 父级坐标 6手选坐标）
     */
    public void setCoordType(Integer coordType) {
        this.coordType = coordType;
    }

    /**
     * 获取经度
     *
     * @return lng - 经度
     */
    public BigDecimal getLng() {
        return lng;
    }

    /**
     * 设置经度
     *
     * @param lng 经度
     */
    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    /**
     * 获取纬度
     *
     * @return lat - 纬度
     */
    public BigDecimal getLat() {
        return lat;
    }

    /**
     * 设置纬度
     *
     * @param lat 纬度
     */
    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    /**
     * 获取创建人员ID
     *
     * @return create_user_id - 创建人员ID
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置创建人员ID
     *
     * @param createUserId 创建人员ID
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
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
     * 获取更新人员ID
     *
     * @return update_user_id - 更新人员ID
     */
    public Long getUpdateUserId() {
        return updateUserId;
    }

    /**
     * 设置更新人员ID
     *
     * @param updateUserId 更新人员ID
     */
    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
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

    /**
     * 获取是否自动发催费短信（0-否，1-是）
     *
     * @return is_auto_sms - 是否自动发催费短信（0-否，1-是）
     */
    public Integer getIsAutoSms() {
        return isAutoSms;
    }

    /**
     * 设置是否自动发催费短信（0-否，1-是）
     *
     * @param isAutoSms 是否自动发催费短信（0-否，1-是）
     */
    public void setIsAutoSms(Integer isAutoSms) {
        this.isAutoSms = isAutoSms;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取是否有效
     *
     * @return is_enabled - 是否有效
     */
    public Integer getIsEnabled() {
        return isEnabled;
    }

    /**
     * 设置是否有效
     *
     * @param isEnabled 是否有效
     */
    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }

    /**
     * 获取状态依赖设备编号
     *
     * @return rely_cno - 状态依赖设备编号
     */
    public String getRelyCno() {
        return relyCno;
    }

    /**
     * 设置状态依赖设备编号
     *
     * @param relyCno 状态依赖设备编号
     */
    public void setRelyCno(String relyCno) {
        this.relyCno = relyCno;
    }

    /**
     * 获取通断状态(0 -断开 1-连通)
     *
     * @return is_on - 通断状态(0 -断开 1-连通)
     */
    public Integer getIsOn() {
        return isOn;
    }

    /**
     * 设置通断状态(0 -断开 1-连通)
     *
     * @param isOn 通断状态(0 -断开 1-连通)
     */
    public void setIsOn(Integer isOn) {
        this.isOn = isOn;
    }

    /**
     * 获取通断操作人
     *
     * @return on_off_opt_user - 通断操作人
     */
    public Long getOnOffOptUser() {
        return onOffOptUser;
    }

    /**
     * 设置通断操作人
     *
     * @param onOffOptUser 通断操作人
     */
    public void setOnOffOptUser(Long onOffOptUser) {
        this.onOffOptUser = onOffOptUser;
    }

    /**
     * 获取最后一次通断的时间
     *
     * @return last_on_off_time - 最后一次通断的时间
     */
    public Date getLastOnOffTime() {
        return lastOnOffTime;
    }

    /**
     * 设置最后一次通断的时间
     *
     * @param lastOnOffTime 最后一次通断的时间
     */
    public void setLastOnOffTime(Date lastOnOffTime) {
        this.lastOnOffTime = lastOnOffTime;
    }

    /**
     * 获取所属组织机构
     *
     * @return org_no - 所属组织机构
     */
    public Long getOrgNo() {
        return orgNo;
    }

    /**
     * 设置所属组织机构
     *
     * @param orgNo 所属组织机构
     */
    public void setOrgNo(Long orgNo) {
        this.orgNo = orgNo;
    }

    public Integer getOffScheme() {
        return offScheme;
    }

    public void setOffScheme(Integer offScheme) {
        this.offScheme = offScheme;
    }

    public Integer getOffParam() {
        return offParam;
    }

    public void setOffParam(Integer offParam) {
        this.offParam = offParam;
    }

    public String getDeviceFactoryVal() {
        return deviceFactoryVal;
    }

    public void setDeviceFactoryVal(String deviceFactoryVal) {
        this.deviceFactoryVal = deviceFactoryVal;
    }
}