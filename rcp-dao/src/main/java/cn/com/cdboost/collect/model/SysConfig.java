package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "em_s_sysconfig")
public class SysConfig implements Serializable {
    private static final long serialVersionUID = 5071979618833853327L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 系统配置ID
     */
    @Column(name = "config_id")
    private Long configId;

    /**
     * 配置项名称
     */
    @Column(name = "config_name")
    private String configName;

    /**
     * 配置值
     */
    @Column(name = "config_value")
    private String configValue;

    /**
     * 备注
     */
    private String remark;

    /**
     * 配置类型枚举  1 标识结算日
     */
    @Column(name = "config_type")
    private Integer configType;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取系统配置ID
     *
     * @return config_id - 系统配置ID
     */
    public Long getConfigId() {
        return configId;
    }

    /**
     * 设置系统配置ID
     *
     * @param configId 系统配置ID
     */
    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    /**
     * 获取配置项名称
     *
     * @return config_name - 配置项名称
     */
    public String getConfigName() {
        return configName;
    }

    /**
     * 设置配置项名称
     *
     * @param configName 配置项名称
     */
    public void setConfigName(String configName) {
        this.configName = configName;
    }

    /**
     * 获取配置值
     *
     * @return config_value - 配置值
     */
    public String getConfigValue() {
        return configValue;
    }

    /**
     * 设置配置值
     *
     * @param configValue 配置值
     */
    public void setConfigValue(String configValue) {
        this.configValue = configValue;
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
     * 获取配置类型枚举  1 标识结算日
     *
     * @return config_type - 配置类型枚举  1 标识结算日
     */
    public Integer getConfigType() {
        return configType;
    }

    /**
     * 设置配置类型枚举  1 标识结算日
     *
     * @param configType 配置类型枚举  1 标识结算日
     */
    public void setConfigType(Integer configType) {
        this.configType = configType;
    }
}