package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "tmp_em_d_customerinfo")
public class TmpCustomerInfo implements Serializable {
    private static final long serialVersionUID = -6361529016410089338L;
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户编号
     */
    @Column(name = "customer_no")
    private String customerNo;

    /**
     * 用户名称
     */
    @Column(name = "customer_name")
    private String customerName;

    /**
     * 联系电话
     */
    @Column(name = "customer_contact")
    private String customerContact;

    /**
     * 用户地址
     */
    @Column(name = "customer_addr")
    private String customerAddr;

    /**
     * 单位名称
     */
    @Column(name = "property_name")
    private String propertyName;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 创建人
     */
    @Column(name = "create_user_id")
    private Long createUserId;

    /**
     * 设备类型(01-LoRa网管，02-LoRa节点，04-集中器，05-采集器，07-电表...)
     */
    @Column(name = "device_type")
    private String deviceType;

    /**
     * 设备编号
     */
    @Column(name = "device_no")
    private String deviceNo;

    /**
     * 电能表/交流采样装置序号(1~2040(也是电表的序号)唯一代码)
     */
    @Column(name = "comm_setup_sn")
    private Integer commSetupSn;

    /**
     * 测量点号(数值范围0~2040)
     */
    @Column(name = "comm_point_code")
    private Integer commPointCode;

    /*  *//**
     * 规约（0 默认，1 DL/T645-1997规约，30 DL/T645-2007，2 交流采样装置通信协议，31 串行接口连接窄带低压载波通信模块）
     *//*
    @Column(name = "comm_rule")
    private Integer commRule;*/

    /**
     * 集中器编号
     */
    @Column(name = "jzq_no")
    private String jzqNo;

    /**
     * 采集器编号
     */
    @Column(name = "collect_no")
    private String collectNo;

    /**
     * 安装位置
     */
    @Column(name = "install_addr")
    private String installAddr;

    /**
     * 告警阈值(剩余金额)
     */
    @Column(name = "alarm_threshold")
    private BigDecimal alarmThreshold;

    /**
     * 导入批次
     */
    @Column(name = "import_id")
    private String importId;

    /**
     * 数据异常 0 正常 1数据异常
     */
    @Column(name = "is_err")
    private Integer isErr;

    /**
     * 异常原因
     */
    @Column(name = "err_info")
    private String errInfo;

    /**
     * 设备唯一标识(生成规则:设备类型+补0(不足30位)+设备编号）
     */
    private String cno;

    /**
     * 集中器的CNO
     */
    @Column(name = "jzq_cno")
    private String jzqCno;

    /**
     * 通信端口号（直联总表端口:1，无线通信端口:5，载波通信端口:31）
     */
    @Column(name = "comm_port")
    private Integer commPort;

    /**
     * 电表预留金额
     */
    @Column(name = "init_amount")
    private BigDecimal initAmount;

    /**
     * 所属组织机构
     */
    @Column(name = "org_no")
    private Long orgNo;

    /**
     * 倍率
     */
    private Integer ratio;

    /**
     * 用户类型值,取字典表中的值
     */
    @Column(name = "dict_item_value")
    private String dictItemValue;

    /*   *//**
     * 设备类型 0其他 1 -09电表 2-13表
     *//*
    @Column(name = "meter_type")
    private Integer meterType;*/

    /**
     * 组织结构名称
     */
    @Column(name = "org_name")
    private String orgName;

    /**
     * 用户类型
     */
    @Column(name = "dict_item_name")
    private String dictItemName;

    /**
     * 1 标识节点 2标识转换器 3采集器
     */
    @Column(name = "collect_dev_type")
    private Integer collectDevType;

    /**
     * 一户多表情况，客户档案标识，相同值表示是同一客户
     */
    @Column(name = "customer_mark")
    private Integer customerMark;

    /**
     * 表计户号
     */
    @Column(name = "meter_user_no")
    private String meterUserNo;

    /**
     * 变压器号
     */
    @Column(name = "transformer_no")
    private String transformerNo;

    /**
     * 楼栋编号
     */
    @Column(name = "building_no")
    private String buildingNo;

    /**
     * 1 本地费控 2 远程费控 0 其他
     */
    @Column(name = "local_control")
    private Integer localControl;

    @Column(name = "param_flag")
    private String paramFlag;
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
     * 设备厂家标识
     */
    @Column(name = "device_factory_val")
    private String deviceFactoryVal;
    /**
     * 0 立即拉闸 1-延时拉闸 2-不拉闸
     */
    @Column(name = "off_scheme")
    private String offScheme;
    /**
     * 延时拉闸时间,单位小时
     */
    @Column(name = "off_param")
    private BigDecimal offParam;
    /**
     * 正向尖示数 -1 无效
     */
    @Column(name = "read_value1")
    private BigDecimal readValue1;
    /**
     * 正向峰示数 -1 无效
     */
    @Column(name = "read_value2")
    private BigDecimal readValue2;
    /**
     * 正向平示数 -1 无效
     */
    @Column(name = "read_value3")
    private BigDecimal readValue3;
    /**
     * 正向谷示数 -1 无效
     */
    @Column(name = "read_value4")
    private BigDecimal readValue4;
    /**
     * 正向总示数 -1 无效
     */
    @Column(name = "read_value")
    private BigDecimal readValue;
    /**
     * 告警阈值2
     */
    @Column(name = "alarm_threshold2")
    private BigDecimal alarmThreshold2;

    /**
     * 商家品牌
     */
    @Column(name = "customer_brand")
    private String customerBrand;

    /**
     * 上级总表
     */
    @Column(name = "parent_meter_no")
    private String parentMeterNo;
    /**
     * 用电类型值,取字典表中的值 code=19
     */
    @Column(name = "elec_type")
    private String elecType;

    /**
     * 电表类别,取字典表中的值 code=20
     */
    @Column(name = "elec_meter_category")
    private String elecMeterCategory;

    /**
     * 透支金额
     */
    @Column(name = "overdraft_amount")
    private BigDecimal overdraftAmount;

    /**
     * 告警阈值3
     */
    @Column(name = "alarm_threshold3")
    private BigDecimal alarmThreshold3;

    /**
     * '楼栋编号'
     */
    @Column(name = "build_no")
    private String buildNo;

    /**
     * '楼栋名称'
     */
    @Column(name = "build_name")
    private String buildName;

    public String getElecType() {
        return elecType;
    }

    public void setElecType(String elecType) {
        this.elecType = elecType;
    }

    public String getElecMeterCategory() {
        return elecMeterCategory;
    }

    public void setElecMeterCategory(String elecMeterCategory) {
        this.elecMeterCategory = elecMeterCategory;
    }

    public BigDecimal getOverdraftAmount() {
        return overdraftAmount;
    }

    public void setOverdraftAmount(BigDecimal overdraftAmount) {
        this.overdraftAmount = overdraftAmount;
    }

    public BigDecimal getAlarmThreshold3() {
        return alarmThreshold3;
    }

    public void setAlarmThreshold3(BigDecimal alarmThreshold3) {
        this.alarmThreshold3 = alarmThreshold3;
    }

    public String getBuildNo() {
        return buildNo;
    }

    public void setBuildNo(String buildNo) {
        this.buildNo = buildNo;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public String getParentMeterNo() {
        return parentMeterNo;
    }

    public void setParentMeterNo(String parentMeterNo) {
        this.parentMeterNo = parentMeterNo;
    }

    public String getCustomerBrand() {
        return customerBrand;
    }

    public void setCustomerBrand(String customerBrand) {
        this.customerBrand = customerBrand;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getParamFlag() {
        return paramFlag;
    }

    public void setParamFlag(String paramFlag) {
        this.paramFlag = paramFlag;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getDeviceFactory() {
        return deviceFactory;
    }

    public void setDeviceFactory(String deviceFactory) {
        this.deviceFactory = deviceFactory;
    }

    public String getDeviceFactoryVal() {
        return deviceFactoryVal;
    }

    public void setDeviceFactoryVal(String deviceFactoryVal) {
        this.deviceFactoryVal = deviceFactoryVal;
    }

    public String getOffScheme() {
        return offScheme;
    }

    public void setOffScheme(String offScheme) {
        this.offScheme = offScheme;
    }


    public BigDecimal getReadValue1() {
        return readValue1;
    }

    public void setReadValue1(BigDecimal readValue1) {
        this.readValue1 = readValue1;
    }

    public BigDecimal getOffParam() {
        return offParam;
    }

    public void setOffParam(BigDecimal offParam) {
        this.offParam = offParam;
    }

    public BigDecimal getReadValue2() {
        return readValue2;
    }

    public void setReadValue2(BigDecimal readValue2) {
        this.readValue2 = readValue2;
    }

    public BigDecimal getReadValue3() {
        return readValue3;
    }

    public void setReadValue3(BigDecimal readValue3) {
        this.readValue3 = readValue3;
    }

    public BigDecimal getReadValue4() {
        return readValue4;
    }

    public void setReadValue4(BigDecimal readValue4) {
        this.readValue4 = readValue4;
    }

    public BigDecimal getReadValue() {
        return readValue;
    }

    public void setReadValue(BigDecimal readValue) {
        this.readValue = readValue;
    }

    public BigDecimal getAlarmThreshold2() {
        return alarmThreshold2;
    }

    public void setAlarmThreshold2(BigDecimal alarmThreshold2) {
        this.alarmThreshold2 = alarmThreshold2;
    }

    /**
     * 获取主键ID
     *
     * @return id - 主键ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键ID
     *
     * @param id 主键ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取用户编号
     *
     * @return customer_no - 用户编号
     */
    public String getCustomerNo() {
        return customerNo;
    }

    /**
     * 设置用户编号
     *
     * @param customerNo 用户编号
     */
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    /**
     * 获取用户名称
     *
     * @return customer_name - 用户名称
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * 设置用户名称
     *
     * @param customerName 用户名称
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * 获取联系电话
     *
     * @return customer_contact - 联系电话
     */
    public String getCustomerContact() {
        return customerContact;
    }

    /**
     * 设置联系电话
     *
     * @param customerContact 联系电话
     */
    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    /**
     * 获取用户地址
     *
     * @return customer_addr - 用户地址
     */
    public String getCustomerAddr() {
        return customerAddr;
    }

    /**
     * 设置用户地址
     *
     * @param customerAddr 用户地址
     */
    public void setCustomerAddr(String customerAddr) {
        this.customerAddr = customerAddr;
    }

    /**
     * 获取单位名称
     *
     * @return property_name - 单位名称
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * 设置单位名称
     *
     * @param propertyName 单位名称
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
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
     * 获取创建人
     *
     * @return create_user_id - 创建人
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置创建人
     *
     * @param createUserId 创建人
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 获取设备类型(01-LoRa网管，02-LoRa节点，04-集中器，05-采集器，07-电表...)
     *
     * @return device_type - 设备类型(01-LoRa网管，02-LoRa节点，04-集中器，05-采集器，07-电表...)
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * 设置设备类型(01-LoRa网管，02-LoRa节点，04-集中器，05-采集器，07-电表...)
     *
     * @param deviceType 设备类型(01-LoRa网管，02-LoRa节点，04-集中器，05-采集器，07-电表...)
     */
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
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
     * 获取电能表/交流采样装置序号(1~2040(也是电表的序号)唯一代码)
     *
     * @return comm_setup_sn - 电能表/交流采样装置序号(1~2040(也是电表的序号)唯一代码)
     */
    public Integer getCommSetupSn() {
        return commSetupSn;
    }

    /**
     * 设置电能表/交流采样装置序号(1~2040(也是电表的序号)唯一代码)
     *
     * @param commSetupSn 电能表/交流采样装置序号(1~2040(也是电表的序号)唯一代码)
     */
    public void setCommSetupSn(Integer commSetupSn) {
        this.commSetupSn = commSetupSn;
    }

    /**
     * 获取测量点号(数值范围0~2040)
     *
     * @return comm_point_code - 测量点号(数值范围0~2040)
     */
    public Integer getCommPointCode() {
        return commPointCode;
    }

    /**
     * 设置测量点号(数值范围0~2040)
     *
     * @param commPointCode 测量点号(数值范围0~2040)
     */
    public void setCommPointCode(Integer commPointCode) {
        this.commPointCode = commPointCode;
    }

    /* *//**
     * 获取规约（0 默认，1 DL/T645-1997规约，30 DL/T645-2007，2 交流采样装置通信协议，31 串行接口连接窄带低压载波通信模块）
     *
     * @return comm_rule - 规约（0 默认，1 DL/T645-1997规约，30 DL/T645-2007，2 交流采样装置通信协议，31 串行接口连接窄带低压载波通信模块）
     *//*
    public Integer getCommRule() {
        return commRule;
    }

    *//**
     * 设置规约（0 默认，1 DL/T645-1997规约，30 DL/T645-2007，2 交流采样装置通信协议，31 串行接口连接窄带低压载波通信模块）
     *
     * @param commRule 规约（0 默认，1 DL/T645-1997规约，30 DL/T645-2007，2 交流采样装置通信协议，31 串行接口连接窄带低压载波通信模块）
     *//*
    public void setCommRule(Integer commRule) {
        this.commRule = commRule;
    }*/

    /**
     * 获取集中器编号
     *
     * @return jzq_no - 集中器编号
     */
    public String getJzqNo() {
        return jzqNo;
    }

    /**
     * 设置集中器编号
     *
     * @param jzqNo 集中器编号
     */
    public void setJzqNo(String jzqNo) {
        this.jzqNo = jzqNo;
    }

    /**
     * 获取采集器编号
     *
     * @return collect_no - 采集器编号
     */
    public String getCollectNo() {
        return collectNo;
    }

    /**
     * 设置采集器编号
     *
     * @param collectNo 采集器编号
     */
    public void setCollectNo(String collectNo) {
        this.collectNo = collectNo;
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
     * 获取告警阈值(剩余金额)
     *
     * @return alarm_threshold - 告警阈值(剩余金额)
     */
    public BigDecimal getAlarmThreshold() {
        return alarmThreshold;
    }

    /**
     * 设置告警阈值(剩余金额)
     *
     * @param alarmThreshold 告警阈值(剩余金额)
     */
    public void setAlarmThreshold(BigDecimal alarmThreshold) {
        this.alarmThreshold = alarmThreshold;
    }

    /**
     * 获取导入批次
     *
     * @return import_id - 导入批次
     */
    public String getImportId() {
        return importId;
    }

    /**
     * 设置导入批次
     *
     * @param importId 导入批次
     */
    public void setImportId(String importId) {
        this.importId = importId;
    }

    /**
     * 获取数据异常 0 正常 1数据异常
     *
     * @return is_err - 数据异常 0 正常 1数据异常
     */
    public Integer getIsErr() {
        return isErr;
    }

    /**
     * 设置数据异常 0 正常 1数据异常
     *
     * @param isErr 数据异常 0 正常 1数据异常
     */
    public void setIsErr(Integer isErr) {
        this.isErr = isErr;
    }

    /**
     * 获取异常原因
     *
     * @return err_info - 异常原因
     */
    public String getErrInfo() {
        return errInfo;
    }

    /**
     * 设置异常原因
     *
     * @param errInfo 异常原因
     */
    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
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
     * 获取集中器的CNO
     *
     * @return jzq_cno - 集中器的CNO
     */
    public String getJzqCno() {
        return jzqCno;
    }

    /**
     * 设置集中器的CNO
     *
     * @param jzqCno 集中器的CNO
     */
    public void setJzqCno(String jzqCno) {
        this.jzqCno = jzqCno;
    }

    /**
     * 获取通信端口号（直联总表端口:1，无线通信端口:5，载波通信端口:31）
     *
     * @return comm_port - 通信端口号（直联总表端口:1，无线通信端口:5，载波通信端口:31）
     */
    public Integer getCommPort() {
        return commPort;
    }

    /**
     * 设置通信端口号（直联总表端口:1，无线通信端口:5，载波通信端口:31）
     *
     * @param commPort 通信端口号（直联总表端口:1，无线通信端口:5，载波通信端口:31）
     */
    public void setCommPort(Integer commPort) {
        this.commPort = commPort;
    }

    /**
     * 获取电表预留金额
     *
     * @return init_amount - 电表预留金额
     */
    public BigDecimal getInitAmount() {
        return initAmount;
    }

    /**
     * 设置电表预留金额
     *
     * @param initAmount 电表预留金额
     */
    public void setInitAmount(BigDecimal initAmount) {
        this.initAmount = initAmount;
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

    /**
     * 获取倍率
     *
     * @return ratio - 倍率
     */
    public Integer getRatio() {
        return ratio;
    }

    /**
     * 设置倍率
     *
     * @param ratio 倍率
     */
    public void setRatio(Integer ratio) {
        this.ratio = ratio;
    }

    /**
     * 获取用户类型值,取字典表中的值
     *
     * @return dict_item_value - 用户类型值,取字典表中的值
     */
    public String getDictItemValue() {
        return dictItemValue;
    }

    /**
     * 设置用户类型值,取字典表中的值
     *
     * @param dictItemValue 用户类型值,取字典表中的值
     */
    public void setDictItemValue(String dictItemValue) {
        this.dictItemValue = dictItemValue;
    }

    /*    *//**
     * 获取设备类型 0其他 1 -09电表 2-13表
     *
     * @return meter_type - 设备类型 0其他 1 -09电表 2-13表
     *//*
    public Integer getMeterType() {
        return meterType;
    }

    *//**
     * 设置设备类型 0其他 1 -09电表 2-13表
     *
     * @param meterType 设备类型 0其他 1 -09电表 2-13表
     *//*
    public void setMeterType(Integer meterType) {
        this.meterType = meterType;
    }*/

    /**
     * 获取组织结构名称
     *
     * @return org_name - 组织结构名称
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * 设置组织结构名称
     *
     * @param orgName 组织结构名称
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * 获取用户类型
     *
     * @return dict_item_name - 用户类型
     */
    public String getDictItemName() {
        return dictItemName;
    }

    /**
     * 设置用户类型
     *
     * @param dictItemName 用户类型
     */
    public void setDictItemName(String dictItemName) {
        this.dictItemName = dictItemName;
    }

    public Integer getCollectDevType() {
        return collectDevType;
    }

    public void setCollectDevType(Integer collectDevType) {
        this.collectDevType = collectDevType;
    }

    public String getMeterUserNo() {
        return meterUserNo;
    }

    public void setMeterUserNo(String meterUserNo) {
        this.meterUserNo = meterUserNo;
    }

    public String getTransformerNo() {
        return transformerNo;
    }

    public void setTransformerNo(String transformerNo) {
        this.transformerNo = transformerNo;
    }

    public String getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }

    public Integer getCustomerMark() {
        return customerMark;
    }

    public void setCustomerMark(Integer customerMark) {
        this.customerMark = customerMark;
    }

    public Integer getLocalControl() {
        return localControl;
    }

    public void setLocalControl(Integer localControl) {
        this.localControl = localControl;
    }
}