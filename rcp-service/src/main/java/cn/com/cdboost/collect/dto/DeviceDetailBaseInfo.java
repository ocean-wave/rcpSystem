package cn.com.cdboost.collect.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 设备档案，水电气表明细公共字段
 */
public class DeviceDetailBaseInfo {
    /**
     * 表号
     */
    private String deviceNo;

    /**
     * 资产编号
     */
    private String assetsNo;

    /**
     * 部门机构
     */
    private String orgName;

    /**
     * 通信地址
     */
    private String moteEui;

    /**
     * 测量点
     */
    private Integer commPointCode;

    /**
     * 下发状态
     */
    private Integer sendFlag;

    /**
     * 安装日期
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date installDate;

    /**
     * 采集方式
     */
    private Integer commPort;

    /**
     * 规约
     */
    private Integer commRule;

    /**
     * 采集设备类型  1标识节点 2标识转换器
     */
    private Integer collectDevType;

    /**
     * collectDevType=1，表示节点地址
     * collectDevType=2，表示转换器地址
     */
    private String commCollectionNo;

    /**
     * 生产厂家
     */
    private String deviceFactory;

    /**
     * 告警阀值1
     */
    private BigDecimal alarmThreshold;

    /**
     * 告警阀值2
     */
    private BigDecimal alarmThreshold1;

    /**
     * 安装位置
     */
    private String installAddr;

    /**
     * 集中器号
     */
    private String jzqNo;

    /**
     * 预留金额
     */
    private BigDecimal initAmount;

    /**
     * 表计户号
     */
    private String meterUserNo;

    /**
     * 变压器号
     */
    private String transformerNo;

    /**
     * 楼栋编号
     */
    private String buildingNo;

    /**
     * 表计计费类型
     */
    private String dictItemName;

    /**
     * 是否重点表
     */
    private Integer isImportant;
    /**
     * 用户姓名
     */
    private String customerName;

    /**
     * 联系电话
     */
    private String customerContact;

    /**
     * 用户地址
     */
    private String customerAddr;

    /**
     * 门牌编号
     */
    private String propertyName;

    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 设备备注
     */
    private String remark;

    /**
     * 用户备注
     */
    private String remark2;

    /**
     * 用户状态（前端判断用，销毁状态是不允许编辑的）
     */
    private Integer isEnabled;

    /**
     * 添加品牌
     */
    private String customerBrand;
    /**
     * 用电类型值,取字典表中的值 code=19
     */

    private String elecType;
    private String elecTypeName;
    /**
     * 电表类别,取字典表中的值 code=20
     */

    private String elecMeterCategory;
    private String elecMeterCategoryName;

    public String getElecTypeName() {
        return elecTypeName;
    }

    public void setElecTypeName(String elecTypeName) {
        this.elecTypeName = elecTypeName;
    }

    public String getElecMeterCategoryName() {
        return elecMeterCategoryName;
    }

    public void setElecMeterCategoryName(String elecMeterCategoryName) {
        this.elecMeterCategoryName = elecMeterCategoryName;
    }

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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public String getCustomerAddr() {
        return customerAddr;
    }

    public void setCustomerAddr(String customerAddr) {
        this.customerAddr = customerAddr;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getCustomerBrand() {
        return customerBrand;
    }

    public void setCustomerBrand(String customerBrand) {
        this.customerBrand = customerBrand;
    }
    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getAssetsNo() { return assetsNo;}

    public void setAssetsNo(String assetsNo) {this.assetsNo = assetsNo;}

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getMoteEui() {
        return moteEui;
    }

    public void setMoteEui(String moteEui) {
        this.moteEui = moteEui;
    }

    public Integer getCommPointCode() {
        return commPointCode;
    }

    public void setCommPointCode(Integer commPointCode) {
        this.commPointCode = commPointCode;
    }

    public Integer getSendFlag() {
        return sendFlag;
    }

    public void setSendFlag(Integer sendFlag) {
        this.sendFlag = sendFlag;
    }

    public Date getInstallDate() {
        return installDate;
    }

    public void setInstallDate(Date installDate) {
        this.installDate = installDate;
    }

    public Integer getCommPort() {
        return commPort;
    }

    public void setCommPort(Integer commPort) {
        this.commPort = commPort;
    }

    public Integer getCommRule() {
        return commRule;
    }

    public void setCommRule(Integer commRule) {
        this.commRule = commRule;
    }

    public Integer getCollectDevType() {
        return collectDevType;
    }

    public void setCollectDevType(Integer collectDevType) {
        this.collectDevType = collectDevType;
    }

    public String getCommCollectionNo() {
        return commCollectionNo;
    }

    public void setCommCollectionNo(String commCollectionNo) {
        this.commCollectionNo = commCollectionNo;
    }

    public String getDeviceFactory() {
        return deviceFactory;
    }

    public void setDeviceFactory(String deviceFactory) {
        this.deviceFactory = deviceFactory;
    }

    public BigDecimal getAlarmThreshold() {
        return alarmThreshold;
    }

    public void setAlarmThreshold(BigDecimal alarmThreshold) {
        this.alarmThreshold = alarmThreshold;
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

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public String getJzqNo() {
        return jzqNo;
    }

    public void setJzqNo(String jzqNo) {
        this.jzqNo = jzqNo;
    }

    public BigDecimal getInitAmount() {
        return initAmount;
    }

    public void setInitAmount(BigDecimal initAmount) {
        this.initAmount = initAmount;
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

    public String getDictItemName() {
        return dictItemName;
    }

    public void setDictItemName(String dictItemName) {
        this.dictItemName = dictItemName;
    }

    public Integer getIsImportant() {
        return isImportant;
    }

    public void setIsImportant(Integer isImportant) {
        this.isImportant = isImportant;
    }

    public BigDecimal getAlarmThreshold1() {
        return alarmThreshold1;
    }

    public void setAlarmThreshold1(BigDecimal alarmThreshold1) {
        this.alarmThreshold1 = alarmThreshold1;
    }
}
