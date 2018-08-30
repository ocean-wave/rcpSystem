package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "em_d_metercollectitem")
public class MeterCollectItem implements Serializable {
    private static final long serialVersionUID = -4024713997459027371L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 抄表项ID
     */
    @Column(name = "collect_item_id")
    private Long collectItemId;

    /**
     * 抄表项名称
     */
    @Column(name = "collect_name")
    private String collectName;

    /**
     * 示数类型：
            11-有功（总）
            12-有功（尖峰）
            13-有功（峰）
            14-有功（谷）
            15-有功（平）
            21-无功（总）
            22-无功（尖峰）
            23-无功（峰）
            24-无功（谷）
            25-无功（平）
            26-无功（Q1象限）
            27-无功（Q2象限）
            28-无功（Q3象限）
            29-无功（Q4象限）
            31-最大需量
            32-累加需量
            33-小时需量
            34-30分钟需量
            35-冻结量
            36-需量（尖峰）
            37-需量（峰）
            38-需量（谷）
            39-需量（平）
            
     */
    @Column(name = "read_type")
    private Integer readType;

    /**
     * 抄表标识（0000FF00）
     */
    @Column(name = "mr_flag")
    private String mrFlag;

    /**
     * 数据格式
     */
    @Column(name = "data_model")
    private String dataModel;

    /**
     * 示数单位
     */
    @Column(name = "read_unit")
    private String readUnit;

    /**
     * 排列序号
     */
    @Column(name = "sort_no")
    private Integer sortNo;

    /**
     * 是否启用 0标识禁用 1标识启用
     */
    @Column(name = "is_enabled")
    private Integer isEnabled;

    /**
     *  1标识1997表 30 标识2007表，
     */
    @Column(name = "meter_type")
    private Integer meterType;

    /**
     * 设备类型
     */
    @Column(name = "device_type")
    private String deviceType;

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
     * 获取抄表项ID
     *
     * @return collect_item_id - 抄表项ID
     */
    public Long getCollectItemId() {
        return collectItemId;
    }

    /**
     * 设置抄表项ID
     *
     * @param collectItemId 抄表项ID
     */
    public void setCollectItemId(Long collectItemId) {
        this.collectItemId = collectItemId;
    }

    /**
     * 获取抄表项名称
     *
     * @return collect_name - 抄表项名称
     */
    public String getCollectName() {
        return collectName;
    }

    /**
     * 设置抄表项名称
     *
     * @param collectName 抄表项名称
     */
    public void setCollectName(String collectName) {
        this.collectName = collectName;
    }

    /**
     * 获取示数类型：
            11-有功（总）
            12-有功（尖峰）
            13-有功（峰）
            14-有功（谷）
            15-有功（平）
            21-无功（总）
            22-无功（尖峰）
            23-无功（峰）
            24-无功（谷）
            25-无功（平）
            26-无功（Q1象限）
            27-无功（Q2象限）
            28-无功（Q3象限）
            29-无功（Q4象限）
            31-最大需量
            32-累加需量
            33-小时需量
            34-30分钟需量
            35-冻结量
            36-需量（尖峰）
            37-需量（峰）
            38-需量（谷）
            39-需量（平）
            
     *
     * @return read_type - 示数类型：
            11-有功（总）
            12-有功（尖峰）
            13-有功（峰）
            14-有功（谷）
            15-有功（平）
            21-无功（总）
            22-无功（尖峰）
            23-无功（峰）
            24-无功（谷）
            25-无功（平）
            26-无功（Q1象限）
            27-无功（Q2象限）
            28-无功（Q3象限）
            29-无功（Q4象限）
            31-最大需量
            32-累加需量
            33-小时需量
            34-30分钟需量
            35-冻结量
            36-需量（尖峰）
            37-需量（峰）
            38-需量（谷）
            39-需量（平）
            
     */
    public Integer getReadType() {
        return readType;
    }

    /**
     * 设置示数类型：
            11-有功（总）
            12-有功（尖峰）
            13-有功（峰）
            14-有功（谷）
            15-有功（平）
            21-无功（总）
            22-无功（尖峰）
            23-无功（峰）
            24-无功（谷）
            25-无功（平）
            26-无功（Q1象限）
            27-无功（Q2象限）
            28-无功（Q3象限）
            29-无功（Q4象限）
            31-最大需量
            32-累加需量
            33-小时需量
            34-30分钟需量
            35-冻结量
            36-需量（尖峰）
            37-需量（峰）
            38-需量（谷）
            39-需量（平）
            
     *
     * @param readType 示数类型：
            11-有功（总）
            12-有功（尖峰）
            13-有功（峰）
            14-有功（谷）
            15-有功（平）
            21-无功（总）
            22-无功（尖峰）
            23-无功（峰）
            24-无功（谷）
            25-无功（平）
            26-无功（Q1象限）
            27-无功（Q2象限）
            28-无功（Q3象限）
            29-无功（Q4象限）
            31-最大需量
            32-累加需量
            33-小时需量
            34-30分钟需量
            35-冻结量
            36-需量（尖峰）
            37-需量（峰）
            38-需量（谷）
            39-需量（平）
            
     */
    public void setReadType(Integer readType) {
        this.readType = readType;
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
     * 获取数据格式
     *
     * @return data_model - 数据格式
     */
    public String getDataModel() {
        return dataModel;
    }

    /**
     * 设置数据格式
     *
     * @param dataModel 数据格式
     */
    public void setDataModel(String dataModel) {
        this.dataModel = dataModel;
    }

    /**
     * 获取示数单位
     *
     * @return read_unit - 示数单位
     */
    public String getReadUnit() {
        return readUnit;
    }

    /**
     * 设置示数单位
     *
     * @param readUnit 示数单位
     */
    public void setReadUnit(String readUnit) {
        this.readUnit = readUnit;
    }

    /**
     * 获取排列序号
     *
     * @return sort_no - 排列序号
     */
    public Integer getSortNo() {
        return sortNo;
    }

    /**
     * 设置排列序号
     *
     * @param sortNo 排列序号
     */
    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    /**
     * 获取是否启用 0标识禁用 1标识启用
     *
     * @return is_enabled - 是否启用 0标识禁用 1标识启用
     */
    public Integer getIsEnabled() {
        return isEnabled;
    }

    /**
     * 设置是否启用 0标识禁用 1标识启用
     *
     * @param isEnabled 是否启用 0标识禁用 1标识启用
     */
    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
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

    /**
     * 获取设备类型
     *
     * @return device_type - 设备类型
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * 设置设备类型
     *
     * @param deviceType 设备类型
     */
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
}