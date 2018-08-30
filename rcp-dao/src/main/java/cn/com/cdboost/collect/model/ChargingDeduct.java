package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "em_d_charging_deduct")
public class ChargingDeduct implements Serializable {
    /**
     * 标识id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 客户唯一标识
     */
    @Column(name = "customer_guid")
    private String customerGuid;

    /**
     * 充电记录唯一标识
     */
    @Column(name = "charging_guid")
    private String chargingGuid;

    /**
     * 扣费金额
     */
    @Column(name = "deduct_money")
    private BigDecimal deductMoney;

    /**
     * 扣除充电次数(包月)
     */
    @Column(name = "deduct_cnt")
    private Integer deductCnt;

    /**
     * 扣费前剩余金额
     */
    @Column(name = "deduct_before")
    private BigDecimal deductBefore;

    /**
     * 扣费后剩余金额
     */
    @Column(name = "remain_amount")
    private BigDecimal remainAmount;

    /**
     * 扣除后剩余充值次数
     */
    @Column(name = "deduct_remain_cnt")
    private Integer deductRemainCnt;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    private String remark;

    /**
     * 流水号
     */
    @Column(name = "serial_num")
    private String serialNum;

    /**
     * 获取标识id
     *
     * @return id - 标识id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置标识id
     *
     * @param id 标识id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取客户唯一标识
     *
     * @return customer_guid - 客户唯一标识
     */
    public String getCustomerGuid() {
        return customerGuid;
    }

    /**
     * 设置客户唯一标识
     *
     * @param customerGuid 客户唯一标识
     */
    public void setCustomerGuid(String customerGuid) {
        this.customerGuid = customerGuid;
    }

    /**
     * 获取充电记录唯一标识
     *
     * @return charging_guid - 充电记录唯一标识
     */
    public String getChargingGuid() {
        return chargingGuid;
    }

    /**
     * 设置充电记录唯一标识
     *
     * @param chargingGuid 充电记录唯一标识
     */
    public void setChargingGuid(String chargingGuid) {
        this.chargingGuid = chargingGuid;
    }

    /**
     * 获取扣费金额
     *
     * @return deduct_money - 扣费金额
     */
    public BigDecimal getDeductMoney() {
        return deductMoney;
    }

    /**
     * 设置扣费金额
     *
     * @param deductMoney 扣费金额
     */
    public void setDeductMoney(BigDecimal deductMoney) {
        this.deductMoney = deductMoney;
    }

    /**
     * 获取扣除充电次数(包月)
     *
     * @return deduct_cnt - 扣除充电次数(包月)
     */
    public Integer getDeductCnt() {
        return deductCnt;
    }

    /**
     * 设置扣除充电次数(包月)
     *
     * @param deductCnt 扣除充电次数(包月)
     */
    public void setDeductCnt(Integer deductCnt) {
        this.deductCnt = deductCnt;
    }

    /**
     * 获取扣费前剩余金额
     *
     * @return deduct_before - 扣费前剩余金额
     */
    public BigDecimal getDeductBefore() {
        return deductBefore;
    }

    /**
     * 设置扣费前剩余金额
     *
     * @param deductBefore 扣费前剩余金额
     */
    public void setDeductBefore(BigDecimal deductBefore) {
        this.deductBefore = deductBefore;
    }

    /**
     * 获取扣费后剩余金额
     *
     * @return remain_amount - 扣费后剩余金额
     */
    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    /**
     * 设置扣费后剩余金额
     *
     * @param remainAmount 扣费后剩余金额
     */
    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }

    /**
     * 获取扣除后剩余充值次数
     *
     * @return deduct_remain_cnt - 扣除后剩余充值次数
     */
    public Integer getDeductRemainCnt() {
        return deductRemainCnt;
    }

    /**
     * 设置扣除后剩余充值次数
     *
     * @param deductRemainCnt 扣除后剩余充值次数
     */
    public void setDeductRemainCnt(Integer deductRemainCnt) {
        this.deductRemainCnt = deductRemainCnt;
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
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
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
}