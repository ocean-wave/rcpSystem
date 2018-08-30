package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "em_d_fee_refund")
public class FeeRefund implements Serializable{
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
     * 表计户号
     */
    @Column(name = "meter_user_no")
    private String meterUserNo;

    /**
     * 设备唯一标识
     */
    private String cno;

    /**
     * 设备类型
     */
    @Column(name = "device_type")
    private String deviceType;

    /**
     * 退款金额
     */
    @Column(name = "refund_money")
    private BigDecimal refundMoney;

    /**
     * 退款标识
     */
    @Column(name = "refund_guid")
    private String refundGuid;

    /**
     * 退费原因说明
     */
    private String remark;

    /**
     * 1-现金 2-微信 3-银联 4-支付宝
     */
    @Column(name = "refund_method")
    private Integer refundMethod;

    /**
     * 流水号
     */
    @Column(name = "serial_num")
    private String serialNum;

    /**
     * 退费前的剩余金额
     */
    @Column(name = "befor_remain_amount")
    private BigDecimal beforRemainAmount;

    /**
     * 退款记录是否有效 1标识有效0 标识无效
     */
    @Column(name = "is_valid")
    private Integer isValid;

    /**
     * 退款日期
     */
    @Column(name = "refund_date")
    private Date refundDate;

    /**
     * 退款人员
     */
    @Column(name = "create_user_id")
    private Long createUserId;

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
     * 获取表计户号
     *
     * @return meter_user_no - 表计户号
     */
    public String getMeterUserNo() {
        return meterUserNo;
    }

    /**
     * 设置表计户号
     *
     * @param meterUserNo 表计户号
     */
    public void setMeterUserNo(String meterUserNo) {
        this.meterUserNo = meterUserNo;
    }

    /**
     * 获取设备唯一标识
     *
     * @return cno - 设备唯一标识
     */
    public String getCno() {
        return cno;
    }

    /**
     * 设置设备唯一标识
     *
     * @param cno 设备唯一标识
     */
    public void setCno(String cno) {
        this.cno = cno;
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

    /**
     * 获取退款金额
     *
     * @return refund_money - 退款金额
     */
    public BigDecimal getRefundMoney() {
        return refundMoney;
    }

    /**
     * 设置退款金额
     *
     * @param refundMoney 退款金额
     */
    public void setRefundMoney(BigDecimal refundMoney) {
        this.refundMoney = refundMoney;
    }

    /**
     * 获取退款标识
     *
     * @return refund_guid - 退款标识
     */
    public String getRefundGuid() {
        return refundGuid;
    }

    /**
     * 设置退款标识
     *
     * @param refundGuid 退款标识
     */
    public void setRefundGuid(String refundGuid) {
        this.refundGuid = refundGuid;
    }

    /**
     * 获取退费原因说明
     *
     * @return remark - 退费原因说明
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置退费原因说明
     *
     * @param remark 退费原因说明
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取1-现金 2-微信 3-银联 4-支付宝
     *
     * @return refund_method - 1-现金 2-微信 3-银联 4-支付宝
     */
    public Integer getRefundMethod() {
        return refundMethod;
    }

    /**
     * 设置1-现金 2-微信 3-银联 4-支付宝
     *
     * @param refundMethod 1-现金 2-微信 3-银联 4-支付宝
     */
    public void setRefundMethod(Integer refundMethod) {
        this.refundMethod = refundMethod;
    }

    /**
     * 获取流水号
     *
     * @return serial_num - 流水号
     */
    public String getSerialNum() {
        return serialNum;
    }

    /**
     * 设置流水号
     *
     * @param serialNum 流水号
     */
    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    /**
     * 获取退费前的剩余金额
     *
     * @return befor_remain_amount - 退费前的剩余金额
     */
    public BigDecimal getBeforRemainAmount() {
        return beforRemainAmount;
    }

    /**
     * 设置退费前的剩余金额
     *
     * @param beforRemainAmount 退费前的剩余金额
     */
    public void setBeforRemainAmount(BigDecimal beforRemainAmount) {
        this.beforRemainAmount = beforRemainAmount;
    }

    /**
     * 获取退款记录是否有效 1标识有效0 标识无效
     *
     * @return is_valid - 退款记录是否有效 1标识有效0 标识无效
     */
    public Integer getIsValid() {
        return isValid;
    }

    /**
     * 设置退款记录是否有效 1标识有效0 标识无效
     *
     * @param isValid 退款记录是否有效 1标识有效0 标识无效
     */
    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    /**
     * 获取退款日期
     *
     * @return refund_date - 退款日期
     */
    public Date getRefundDate() {
        return refundDate;
    }

    /**
     * 设置退款日期
     *
     * @param refundDate 退款日期
     */
    public void setRefundDate(Date refundDate) {
        this.refundDate = refundDate;
    }

    /**
     * 获取退款人员
     *
     * @return create_user_id - 退款人员
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置退款人员
     *
     * @param createUserId 退款人员
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }
}