package cn.com.cdboost.collect.model;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "em_d_fee_pay")
public class FeePay implements Serializable {
    private static final long serialVersionUID = -7787441555732155166L;
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
     * 购电卡号
     */
    @Column(name = "card_id")
    private String cardId;

    /**
     * 购电次数
     */
    @Column(name = "pay_count")
    private Integer payCount;

    /**
     * 购电日期
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "pay_date")
    private Date payDate;

    /**
     * 购电金额
     */
    @Column(name = "pay_money")
    private BigDecimal payMoney;

    /**
     * 1  APP 充值 2 购电卡充值
     */
    @Column(name = "pay_model")
    private Integer payModel;

    /**
     * 售电人员
     */
    @Column(name = "create_user_id")
    private Long createUserId;

    /**
     * 购电金额是否下发APP充值需要下发，购电卡充值不需要要下发 0- 未写入 1-写入 2-取消远程 3-远程下发失败
     */
    @Column(name = "write_meter")
    private Integer writeMeter;

    /**
     * 下发电表时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "wit_met_time")
    private Date witMetTime;

    /**
     * 支付标识
     */
    @Column(name = "pay_guid")
    private String payGuid;

    /**
     * 缴费的金额
     */
    private BigDecimal payment;

    /**
     * 调整金额
     */
    @Column(name = "adjus_amount")
    private BigDecimal adjusAmount;

    /**
     * 充值记录是否有效 1标识有效0 标识无效
     */
    @Column(name = "is_valid")
    private Integer isValid;

    /**
     * 备注说明
     */
    private String remark;

    /**
     * 是否能够重新补卡
     */
    @Column(name = "is_repeat_card")
    private Integer isRepeatCard;

    /**
     * 设备类型(01-LoRa网管，02-LoRa节点，04-集中器，05-采集器，07-电表...)
     */
    @Column(name = "device_type")
    private String deviceType;

    /**
     * 表计户号
     */
    @Column(name = "meter_user_no")
    private String meterUserNo;

    /**
     * 1-现金 2-微信 3-银联 4-支付宝
     */
    @Column(name = "pay_method")
    private Integer payMethod;

    /**
     * 缴费流水号
     */
    @Column(name = "serial_num")
    private String serialNum;
    @Column(name = "remain_amount")
    private BigDecimal remainAmount;
    @Column(name = "befor_remain_amount")
    private BigDecimal beforRemainAmount;

    public BigDecimal getBeforRemainAmount() {
        return beforRemainAmount;
    }

    public void setBeforRemainAmount(BigDecimal beforRemainAmount) {
        this.beforRemainAmount = beforRemainAmount;
    }

    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
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
     * 获取购电卡号
     *
     * @return card_id - 购电卡号
     */
    public String getCardId() {
        return cardId;
    }

    /**
     * 设置购电卡号
     *
     * @param cardId 购电卡号
     */
    public void setCardId(String cardId) {
        this.cardId = cardId;
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
     * 获取购电日期
     *
     * @return pay_date - 购电日期
     */
    public Date getPayDate() {
        return payDate;
    }

    /**
     * 设置购电日期
     *
     * @param payDate 购电日期
     */
    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    /**
     * 获取购电金额
     *
     * @return pay_money - 购电金额
     */
    public BigDecimal getPayMoney() {
        return payMoney;
    }

    /**
     * 设置购电金额
     *
     * @param payMoney 购电金额
     */
    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    /**
     * 获取1  APP 充值 2 购电卡充值
     *
     * @return pay_model - 1  APP 充值 2 购电卡充值
     */
    public Integer getPayModel() {
        return payModel;
    }

    /**
     * 设置1  APP 充值 2 购电卡充值
     *
     * @param payModel 1  APP 充值 2 购电卡充值
     */
    public void setPayModel(Integer payModel) {
        this.payModel = payModel;
    }

    /**
     * 获取售电人员
     *
     * @return create_user_id - 售电人员
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置售电人员
     *
     * @param createUserId 售电人员
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 获取购电金额是否下发APP充值需要下发，购电卡充值不需要要下发 0- 未写入 1-写入 2-取消远程 3-远程下发失败
     *
     * @return write_meter - 购电金额是否下发APP充值需要下发，购电卡充值不需要要下发 0- 未写入 1-写入 2-取消远程 3-远程下发失败
     */
    public Integer getWriteMeter() {
        return writeMeter;
    }

    /**
     * 设置购电金额是否下发APP充值需要下发，购电卡充值不需要要下发 0- 未写入 1-写入 2-取消远程 3-远程下发失败
     *
     * @param writeMeter 购电金额是否下发APP充值需要下发，购电卡充值不需要要下发 0- 未写入 1-写入 2-取消远程 3-远程下发失败
     */
    public void setWriteMeter(Integer writeMeter) {
        this.writeMeter = writeMeter;
    }

    /**
     * 获取下发电表时间
     *
     * @return wit_met_time - 下发电表时间
     */
    public Date getWitMetTime() {
        return witMetTime;
    }

    /**
     * 设置下发电表时间
     *
     * @param witMetTime 下发电表时间
     */
    public void setWitMetTime(Date witMetTime) {
        this.witMetTime = witMetTime;
    }

    /**
     * 获取支付标识
     *
     * @return pay_guid - 支付标识
     */
    public String getPayGuid() {
        return payGuid;
    }

    /**
     * 设置支付标识
     *
     * @param payGuid 支付标识
     */
    public void setPayGuid(String payGuid) {
        this.payGuid = payGuid;
    }

    /**
     * 获取缴费的金额
     *
     * @return payment - 缴费的金额
     */
    public BigDecimal getPayment() {
        return payment;
    }

    /**
     * 设置缴费的金额
     *
     * @param payment 缴费的金额
     */
    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    /**
     * 获取调整金额
     *
     * @return adjus_amount - 调整金额
     */
    public BigDecimal getAdjusAmount() {
        return adjusAmount;
    }

    /**
     * 设置调整金额
     *
     * @param adjusAmount 调整金额
     */
    public void setAdjusAmount(BigDecimal adjusAmount) {
        this.adjusAmount = adjusAmount;
    }

    /**
     * 获取充值记录是否有效 1标识有效0 标识无效
     *
     * @return is_valid - 充值记录是否有效 1标识有效0 标识无效
     */
    public Integer getIsValid() {
        return isValid;
    }

    /**
     * 设置充值记录是否有效 1标识有效0 标识无效
     *
     * @param isValid 充值记录是否有效 1标识有效0 标识无效
     */
    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    /**
     * 获取备注说明
     *
     * @return remark - 备注说明
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注说明
     *
     * @param remark 备注说明
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取是否能够重新补卡
     *
     * @return is_repeat_card - 是否能够重新补卡
     */
    public Integer getIsRepeatCard() {
        return isRepeatCard;
    }

    /**
     * 设置是否能够重新补卡
     *
     * @param isRepeatCard 是否能够重新补卡
     */
    public void setIsRepeatCard(Integer isRepeatCard) {
        this.isRepeatCard = isRepeatCard;
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

    public String getMeterUserNo() {
        return meterUserNo;
    }

    public void setMeterUserNo(String meterUserNo) {
        this.meterUserNo = meterUserNo;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }
}