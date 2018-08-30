package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "em_d_customerdevmap")
public class CustomerDevMap implements Serializable{
    private static final long serialVersionUID = -4747057101503581764L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户编号
     */
    @Column(name = "customer_no")
    private String customerNo;

    /**
     * 设备唯一标识(生成规则:设备类型+补0(不足30位)+设备编号）
     */
    private String cno;

    /**
     * 设备类型(01-LoRa网管，02-LoRa节点，04-集中器，05-采集器，07-电表...)
     */
    @Column(name = "device_type")
    private String deviceType;

    /**
     * 最近催费短信发送时间
     */
    @Column(name = "last_send_sms_time")
    private Date lastSendSmsTime;

    /**
     * 是否开户 0-未IC开户 1开户制卡失败 2-开户成功 
     */
    @Column(name = "is_account")
    private Integer isAccount;

    /**
     * IC卡开户时间
     */
    @Column(name = "acct_datetime")
    private Date acctDatetime;


    /**
     * 电表预留金额
     */
    @Column(name = "init_amount")
    private BigDecimal initAmount;

    /**
     * 制卡时间
     */
    @Column(name = "wirte_card_time")
    private Date wirteCardTime;

    /**
     * 补卡次数
     */
    @Column(name = "repet_count")
    private Integer repetCount;

    /**
     * 电表调整金额
     */
    @Column(name = "adjus_amount")
    private BigDecimal adjusAmount;

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
     * 是否更换 0标识正常 1标识更换
     */
    @Column(name = "is_change")
    private Integer isChange;

    /**
     * 设备编号
     */
    @Column(name = "device_no")
    private String deviceNo;


    /**
     * 告警阈值1(剩余金额)
     */
    @Column(name = "alarm_threshold")
    private BigDecimal alarmThreshold;

    /**
     * 告警阈值2
     */
    @Column(name = "alarm_threshold1")
    private BigDecimal alarmThreshold1;

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
     * 获取最近催费短信发送时间
     *
     * @return last_send_sms_time - 最近催费短信发送时间
     */
    public Date getLastSendSmsTime() {
        return lastSendSmsTime;
    }

    /**
     * 设置最近催费短信发送时间
     *
     * @param lastSendSmsTime 最近催费短信发送时间
     */
    public void setLastSendSmsTime(Date lastSendSmsTime) {
        this.lastSendSmsTime = lastSendSmsTime;
    }

    /**
     * 获取是否开户 0-未IC开户 1开户制卡失败 2-开户成功 
     *
     * @return is_account - 是否开户 0-未IC开户 1开户制卡失败 2-开户成功 
     */
    public Integer getIsAccount() {
        return isAccount;
    }

    /**
     * 设置是否开户 0-未IC开户 1开户制卡失败 2-开户成功 
     *
     * @param isAccount 是否开户 0-未IC开户 1开户制卡失败 2-开户成功 
     */
    public void setIsAccount(Integer isAccount) {
        this.isAccount = isAccount;
    }

    /**
     * 获取IC卡开户时间
     *
     * @return acct_datetime - IC卡开户时间
     */
    public Date getAcctDatetime() {
        return acctDatetime;
    }

    /**
     * 设置IC卡开户时间
     *
     * @param acctDatetime IC卡开户时间
     */
    public void setAcctDatetime(Date acctDatetime) {
        this.acctDatetime = acctDatetime;
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
     * 获取制卡时间
     *
     * @return wirte_card_time - 制卡时间
     */
    public Date getWirteCardTime() {
        return wirteCardTime;
    }

    /**
     * 设置制卡时间
     *
     * @param wirteCardTime 制卡时间
     */
    public void setWirteCardTime(Date wirteCardTime) {
        this.wirteCardTime = wirteCardTime;
    }

    /**
     * 获取补卡次数
     *
     * @return repet_count - 补卡次数
     */
    public Integer getRepetCount() {
        return repetCount;
    }

    /**
     * 设置补卡次数
     *
     * @param repetCount 补卡次数
     */
    public void setRepetCount(Integer repetCount) {
        this.repetCount = repetCount;
    }

    /**
     * 获取电表调整金额
     *
     * @return adjus_amount - 电表调整金额
     */
    public BigDecimal getAdjusAmount() {
        return adjusAmount;
    }

    /**
     * 设置电表调整金额
     *
     * @param adjusAmount 电表调整金额
     */
    public void setAdjusAmount(BigDecimal adjusAmount) {
        this.adjusAmount = adjusAmount;
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

    public Integer getIsChange() {
        return isChange;
    }

    public void setIsChange(Integer isChange) {
        this.isChange = isChange;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public BigDecimal getAlarmThreshold() {
        return alarmThreshold;
    }

    public void setAlarmThreshold(BigDecimal alarmThreshold) {
        this.alarmThreshold = alarmThreshold;
    }

    public BigDecimal getAlarmThreshold1() {
        return alarmThreshold1;
    }

    public void setAlarmThreshold1(BigDecimal alarmThreshold1) {
        this.alarmThreshold1 = alarmThreshold1;
    }
}