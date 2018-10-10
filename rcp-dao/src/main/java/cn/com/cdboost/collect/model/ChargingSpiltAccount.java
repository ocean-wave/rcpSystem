package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "em_d_charging_spilt_account")
public class ChargingSpiltAccount implements Serializable{
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 分账的用户guid
     */
    @Column(name = "customer_guid")
    private String customerGuid;

    /**
     * 充电使用记录表中chargingGuid
     */
    @Column(name = "charging_guid")
    private String chargingGuid;

    /**
     * 分账金额
     */
    private BigDecimal amount;

    /**
     * 博高从利润中分账金额
     */
    @Column(name = "boost_amount")
    private BigDecimal boostAmount;

    /**
     * 博高利润分账比例
     */
    @Column(name = "profit_ratio")
    private BigDecimal profitRatio;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;


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

    public String getCustomerGuid() {
        return customerGuid;
    }

    public void setCustomerGuid(String customerGuid) {
        this.customerGuid = customerGuid;
    }

    public String getChargingGuid() {
        return chargingGuid;
    }

    public void setChargingGuid(String chargingGuid) {
        this.chargingGuid = chargingGuid;
    }

    /**
     * 获取分账金额
     *
     * @return amount - 分账金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 设置分账金额
     *
     * @param amount 分账金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public BigDecimal getBoostAmount() {
        return boostAmount;
    }

    public void setBoostAmount(BigDecimal boostAmount) {
        this.boostAmount = boostAmount;
    }

    public BigDecimal getProfitRatio() {
        return profitRatio;
    }

    public void setProfitRatio(BigDecimal profitRatio) {
        this.profitRatio = profitRatio;
    }
}