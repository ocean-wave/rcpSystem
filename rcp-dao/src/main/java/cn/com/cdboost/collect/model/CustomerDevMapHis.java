package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "em_d_customerdevmap_his")
public class CustomerDevMapHis implements Serializable{
    private static final long serialVersionUID = 8117043522107098291L;
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
     * 是否开户 0-未IC开户 1已IC开户
     */
    @Column(name = "is_account")
    private Integer isAccount;

    /**
     * IC卡开户时间
     */
    @Column(name = "acct_datetime")
    private Date acctDatetime;

    /**
     * 剩余金额
     */
    @Column(name = "remain_amount")
    private BigDecimal remainAmount;

    /**
     * 删除时间
     */
    @Column(name = "delete_time")
    private Date deleteTime;

    /**
     * 充值总额
     */
    @Column(name = "total_amount")
    private BigDecimal totalAmount;

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
     * 购电次数
     */
    @Column(name = "pay_count")
    private Integer payCount;

    /**
     * 电表剩余金额采集时间
     */
    @Column(name = "amount_collect_time")
    private Date amountCollectTime;

    /**
     * 电表的购电总额
     */
    @Column(name = "meter_total_amount")
    private BigDecimal meterTotalAmount;

    /**
     * 电表购电次数
     */
    @Column(name = "meter_pay_count")
    private Integer meterPayCount;

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
     * 获取是否开户 0-未IC开户 1已IC开户
     *
     * @return is_account - 是否开户 0-未IC开户 1已IC开户
     */
    public Integer getIsAccount() {
        return isAccount;
    }

    /**
     * 设置是否开户 0-未IC开户 1已IC开户
     *
     * @param isAccount 是否开户 0-未IC开户 1已IC开户
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
     * 获取剩余金额
     *
     * @return remain_amount - 剩余金额
     */
    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    /**
     * 设置剩余金额
     *
     * @param remainAmount 剩余金额
     */
    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }

    /**
     * 获取删除时间
     *
     * @return delete_time - 删除时间
     */
    public Date getDeleteTime() {
        return deleteTime;
    }

    /**
     * 设置删除时间
     *
     * @param deleteTime 删除时间
     */
    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    /**
     * 获取充值总额
     *
     * @return total_amount - 充值总额
     */
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    /**
     * 设置充值总额
     *
     * @param totalAmount 充值总额
     */
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
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
     * 获取购电次数
     *
     * @return pay_count - 购电次数
     */
    public Integer getPayCount() {
        return payCount;
    }

    /**
     * 设置购电次数
     *
     * @param payCount 购电次数
     */
    public void setPayCount(Integer payCount) {
        this.payCount = payCount;
    }

    /**
     * 获取电表剩余金额采集时间
     *
     * @return amount_collect_time - 电表剩余金额采集时间
     */
    public Date getAmountCollectTime() {
        return amountCollectTime;
    }

    /**
     * 设置电表剩余金额采集时间
     *
     * @param amountCollectTime 电表剩余金额采集时间
     */
    public void setAmountCollectTime(Date amountCollectTime) {
        this.amountCollectTime = amountCollectTime;
    }

    /**
     * 获取电表的购电总额
     *
     * @return meter_total_amount - 电表的购电总额
     */
    public BigDecimal getMeterTotalAmount() {
        return meterTotalAmount;
    }

    /**
     * 设置电表的购电总额
     *
     * @param meterTotalAmount 电表的购电总额
     */
    public void setMeterTotalAmount(BigDecimal meterTotalAmount) {
        this.meterTotalAmount = meterTotalAmount;
    }

    /**
     * 获取电表购电次数
     *
     * @return meter_pay_count - 电表购电次数
     */
    public Integer getMeterPayCount() {
        return meterPayCount;
    }

    /**
     * 设置电表购电次数
     *
     * @param meterPayCount 电表购电次数
     */
    public void setMeterPayCount(Integer meterPayCount) {
        this.meterPayCount = meterPayCount;
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
}