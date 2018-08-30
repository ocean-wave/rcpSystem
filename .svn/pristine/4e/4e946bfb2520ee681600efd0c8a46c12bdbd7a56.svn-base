package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "em_d_meterscheme")
public class MeterScheme implements Serializable {
    private static final long serialVersionUID = 7245210263082858899L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 方案标识
     */
    @Column(name = "scheme_id")
    private String schemeId;

    /**
     * 方案名称
     */
    @Column(name = "scheme_name")
    private String schemeName;

    /**
     * 抄表标识（0000FF00）
     */
    @Column(name = "mr_flag")
    private String mrFlag;

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
     * 应用方案的设备类型
     */
    @Column(name = "device_type")
    private String deviceType;

    /**
     *  1标识1997表 30 标识2007表，
     */
    @Column(name = "meter_type")
    private Integer meterType;

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
     * 获取方案标识
     *
     * @return scheme_id - 方案标识
     */
    public String getSchemeId() {
        return schemeId;
    }

    /**
     * 设置方案标识
     *
     * @param schemeId 方案标识
     */
    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    /**
     * 获取方案名称
     *
     * @return scheme_name - 方案名称
     */
    public String getSchemeName() {
        return schemeName;
    }

    /**
     * 设置方案名称
     *
     * @param schemeName 方案名称
     */
    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    /**
     * 获取抄表标识（0000FF00）
     *
     * @return mr_flag - 抄表标识（0000FF00）
     */
    public String getMrFlag() {
        return mrFlag;
    }

    /**
     * 设置抄表标识（0000FF00）
     *
     * @param mrFlag 抄表标识（0000FF00）
     */
    public void setMrFlag(String mrFlag) {
        this.mrFlag = mrFlag;
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
     * 获取应用方案的设备类型
     *
     * @return device_type - 应用方案的设备类型
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * 设置应用方案的设备类型
     *
     * @param deviceType 应用方案的设备类型
     */
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * 获取 1标识1997表 30 标识2007表，
     *
     * @return meter_type -  1标识1997表 30 标识2007表，
     */
    public Integer getMeterType() {
        return meterType;
    }

    /**
     * 设置 1标识1997表 30 标识2007表，
     *
     * @param meterType  1标识1997表 30 标识2007表，
     */
    public void setMeterType(Integer meterType) {
        this.meterType = meterType;
    }
}