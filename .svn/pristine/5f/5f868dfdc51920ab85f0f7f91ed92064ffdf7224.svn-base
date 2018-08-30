package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "em_d_devicemeterparam")
public class DeviceMeterParam implements Serializable {
    private static final long serialVersionUID = -4747883470711832057L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 所属集中器唯一标识
     */
    @Column(name = "jzq_cno")
    private String jzqCno;

    /**
     * 设备唯一标识(生成规则:设备类型+补0(不足30位)+设备编号）
     */
    private String cno;

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

    /**
     * 通信端口号（直联总表端口:1，无线通信端口:5，载波通信端口:31）
     */
    @Column(name = "comm_port")
    private Integer commPort;

    /**
     * 主站的电表通信地址
     */
    @Column(name = "comm_addr")
    private String commAddr;

    /**
     *  有功电能示值的整数位个数
     */
    @Column(name = "comm_power_integer")
    private Integer commPowerInteger;

    /**
     * 有功电能示值的小数位个数
     */
    @Column(name = "comm_power_decimal")
    private Integer commPowerDecimal;

    /**
     * 所属采集器通信地址
     */
    @Column(name = "comm_collection_no")
    private String commCollectionNo;

    /**
     * 用户分类号
     */
    @Column(name = "comm_cons_type_code")
    private Integer commConsTypeCode;

    /**
     * 电表分类号
     */
    @Column(name = "comm_elect_type_code")
    private Integer commElectTypeCode;

    /**
     * 下发标识(0-未发，1-已发)
     */
    @Column(name = "send_flag")
    private Integer sendFlag;

    /**
     * 终端EUI
     */
    @Column(name = "mote_eui")
    private String moteEui;

    /**
     * 应用EUI
     */
    @Column(name = "app_eui")
    private String appEui;

    /**
     * 应用密钥
     */
    @Column(name = "app_key")
    private String appKey;

    /**
     * 节点类型（A、B、C）
     */
    @Column(name = "mote_type")
    private String moteType;

    /**
     * 下发时间
     */
    @Column(name = "send_time")
    private Date sendTime;

    /**
     * 倍率
     */
    private Integer ratio;

    /**
     * 档案同步时间
     */
    @Column(name = "syn_record_time")
    private Date synRecordTime;

    /**
     *  1 本地费控 2 远程费控 0 其他
     */
    @Column(name = "local_control")
    private Integer localControl;

    /**
     * 设备类型 7 表示电表 8 表示水表 9气表
     */
    @Column(name = "device_type")
    private Integer deviceType;

    /**
     * 1 标识节点 2标识转换器
     */
    @Column(name = "collect_dev_type")
    private Integer collectDevType;

    /**
     * 表计计费类型,取字典表中的值
     */
    @Column(name = "dict_item_value")
    private String dictItemValue;

    /**
     * 是否重点表 0-非重点 1-重点
     */
    @Column(name = "is_important")
    private Integer isImportant;

    /**
     * 电价方案
     */
    @Column(name = "plan_index")
    private String planIndex;

    /**
     * 费率套数
     */
    @Column(name = "rate_index")
    private Integer rateIndex;

    /**
     * 阶梯套数
     */
    @Column(name = "step_index")
    private Integer stepIndex;

    /**
     * 用户唯一标识
     */
    @Column(name = "customer_no")
    private String customerNo;

    /**
     * 参数标识
     */
    @Column(name = "param_flag")
    private String paramFlag;

    /**
     * 规约（0 默认，1 DL/T645-1997规约，30 DL/T645-2007）
     */
    @Column(name = "comm_rule")
    private Integer commRule;

    /**
     * 电能费率个数
     */
    @Column(name = "comm_factor_cnt")
    private Integer commFactorCnt;


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
     * 获取所属集中器唯一标识
     *
     * @return jzq_cno - 所属集中器唯一标识
     */
    public String getJzqCno() {
        return jzqCno;
    }

    /**
     * 设置所属集中器唯一标识
     *
     * @param jzqCno 所属集中器唯一标识
     */
    public void setJzqCno(String jzqCno) {
        this.jzqCno = jzqCno;
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
     * 获取主站的电表通信地址
     *
     * @return comm_addr - 主站的电表通信地址
     */
    public String getCommAddr() {
        return commAddr;
    }

    /**
     * 设置主站的电表通信地址
     *
     * @param commAddr 主站的电表通信地址
     */
    public void setCommAddr(String commAddr) {
        this.commAddr = commAddr;
    }

    /**
     * 获取 有功电能示值的整数位个数
     *
     * @return comm_power_integer -  有功电能示值的整数位个数
     */
    public Integer getCommPowerInteger() {
        return commPowerInteger;
    }

    /**
     * 设置 有功电能示值的整数位个数
     *
     * @param commPowerInteger  有功电能示值的整数位个数
     */
    public void setCommPowerInteger(Integer commPowerInteger) {
        this.commPowerInteger = commPowerInteger;
    }

    /**
     * 获取有功电能示值的小数位个数
     *
     * @return comm_power_decimal - 有功电能示值的小数位个数
     */
    public Integer getCommPowerDecimal() {
        return commPowerDecimal;
    }

    /**
     * 设置有功电能示值的小数位个数
     *
     * @param commPowerDecimal 有功电能示值的小数位个数
     */
    public void setCommPowerDecimal(Integer commPowerDecimal) {
        this.commPowerDecimal = commPowerDecimal;
    }

    /**
     * 获取所属采集器通信地址
     *
     * @return comm_collection_no - 所属采集器通信地址
     */
    public String getCommCollectionNo() {
        return commCollectionNo;
    }

    /**
     * 设置所属采集器通信地址
     *
     * @param commCollectionNo 所属采集器通信地址
     */
    public void setCommCollectionNo(String commCollectionNo) {
        this.commCollectionNo = commCollectionNo;
    }

    /**
     * 获取用户分类号
     *
     * @return comm_cons_type_code - 用户分类号
     */
    public Integer getCommConsTypeCode() {
        return commConsTypeCode;
    }

    /**
     * 设置用户分类号
     *
     * @param commConsTypeCode 用户分类号
     */
    public void setCommConsTypeCode(Integer commConsTypeCode) {
        this.commConsTypeCode = commConsTypeCode;
    }

    /**
     * 获取电表分类号
     *
     * @return comm_elect_type_code - 电表分类号
     */
    public Integer getCommElectTypeCode() {
        return commElectTypeCode;
    }

    /**
     * 设置电表分类号
     *
     * @param commElectTypeCode 电表分类号
     */
    public void setCommElectTypeCode(Integer commElectTypeCode) {
        this.commElectTypeCode = commElectTypeCode;
    }

    /**
     * 获取下发标识(0-未发，1-已发)
     *
     * @return send_flag - 下发标识(0-未发，1-已发)
     */
    public Integer getSendFlag() {
        return sendFlag;
    }

    /**
     * 设置下发标识(0-未发，1-已发)
     *
     * @param sendFlag 下发标识(0-未发，1-已发)
     */
    public void setSendFlag(Integer sendFlag) {
        this.sendFlag = sendFlag;
    }

    /**
     * 获取终端EUI
     *
     * @return mote_eui - 终端EUI
     */
    public String getMoteEui() {
        return moteEui;
    }

    /**
     * 设置终端EUI
     *
     * @param moteEui 终端EUI
     */
    public void setMoteEui(String moteEui) {
        this.moteEui = moteEui;
    }

    /**
     * 获取应用EUI
     *
     * @return app_eui - 应用EUI
     */
    public String getAppEui() {
        return appEui;
    }

    /**
     * 设置应用EUI
     *
     * @param appEui 应用EUI
     */
    public void setAppEui(String appEui) {
        this.appEui = appEui;
    }

    /**
     * 获取应用密钥
     *
     * @return app_key - 应用密钥
     */
    public String getAppKey() {
        return appKey;
    }

    /**
     * 设置应用密钥
     *
     * @param appKey 应用密钥
     */
    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    /**
     * 获取节点类型（A、B、C）
     *
     * @return mote_type - 节点类型（A、B、C）
     */
    public String getMoteType() {
        return moteType;
    }

    /**
     * 设置节点类型（A、B、C）
     *
     * @param moteType 节点类型（A、B、C）
     */
    public void setMoteType(String moteType) {
        this.moteType = moteType;
    }

    /**
     * 获取下发时间
     *
     * @return send_time - 下发时间
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * 设置下发时间
     *
     * @param sendTime 下发时间
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
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
     * 获取档案同步时间
     *
     * @return syn_record_time - 档案同步时间
     */
    public Date getSynRecordTime() {
        return synRecordTime;
    }

    /**
     * 设置档案同步时间
     *
     * @param synRecordTime 档案同步时间
     */
    public void setSynRecordTime(Date synRecordTime) {
        this.synRecordTime = synRecordTime;
    }

    /**
     * 获取 1 本地费控 2 远程费控 0 其他
     *
     * @return local_control -  1 本地费控 2 远程费控 0 其他
     */
    public Integer getLocalControl() {
        return localControl;
    }

    /**
     * 设置 1 本地费控 2 远程费控 0 其他
     *
     * @param localControl  1 本地费控 2 远程费控 0 其他
     */
    public void setLocalControl(Integer localControl) {
        this.localControl = localControl;
    }

    /**
     * 获取设备类型 7 表示电表 8 表示水表 9气表
     *
     * @return device_type - 设备类型 7 表示电表 8 表示水表 9气表
     */
    public Integer getDeviceType() {
        return deviceType;
    }

    /**
     * 设置设备类型 7 表示电表 8 表示水表 9气表
     *
     * @param deviceType 设备类型 7 表示电表 8 表示水表 9气表
     */
    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * 获取1 标识节点 2标识转换器 3采集器
     *
     * @return collect_dev_type - 1 标识节点 2标识转换器 3采集器
     */
    public Integer getCollectDevType() {
        return collectDevType;
    }

    /**
     * 设置1 标识节点 2标识转换器 3采集器
     *
     * @param collectDevType 1 标识节点 2标识转换器 3采集器
     */
    public void setCollectDevType(Integer collectDevType) {
        this.collectDevType = collectDevType;
    }

    /**
     * 获取表计计费类型,取字典表中的值
     *
     * @return dict_item_value - 表计计费类型,取字典表中的值
     */
    public String getDictItemValue() {
        return dictItemValue;
    }

    /**
     * 设置表计计费类型,取字典表中的值
     *
     * @param dictItemValue 表计计费类型,取字典表中的值
     */
    public void setDictItemValue(String dictItemValue) {
        this.dictItemValue = dictItemValue;
    }

    public Integer getIsImportant() {
        return isImportant;
    }

    public void setIsImportant(Integer isImportant) {
        this.isImportant = isImportant;
    }

    /**
     * 获取电价方案
     *
     * @return plan_index - 电价方案
     */
    public String getPlanIndex() {
        return planIndex;
    }

    /**
     * 设置电价方案
     *
     * @param planIndex 电价方案
     */
    public void setPlanIndex(String planIndex) {
        this.planIndex = planIndex;
    }

    /**
     * 获取费率套数
     *
     * @return rate_index - 费率套数
     */
    public Integer getRateIndex() {
        return rateIndex;
    }

    /**
     * 设置费率套数
     *
     * @param rateIndex 费率套数
     */
    public void setRateIndex(Integer rateIndex) {
        this.rateIndex = rateIndex;
    }

    /**
     * 获取阶梯套数
     *
     * @return step_index - 阶梯套数
     */
    public Integer getStepIndex() {
        return stepIndex;
    }

    /**
     * 设置阶梯套数
     *
     * @param stepIndex 阶梯套数
     */
    public void setStepIndex(Integer stepIndex) {
        this.stepIndex = stepIndex;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getParamFlag() {
        return paramFlag;
    }

    public void setParamFlag(String paramFlag) {
        this.paramFlag = paramFlag;
    }

    public Integer getCommRule() {
        return commRule;
    }

    public void setCommRule(Integer commRule) {
        this.commRule = commRule;
    }

    public Integer getCommFactorCnt() {
        return commFactorCnt;
    }

    public void setCommFactorCnt(Integer commFactorCnt) {
        this.commFactorCnt = commFactorCnt;
    }
}