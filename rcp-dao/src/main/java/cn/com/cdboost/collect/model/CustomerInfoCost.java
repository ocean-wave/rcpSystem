package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "em_d_customerinfo_cost")
public class CustomerInfoCost implements Serializable{
    /**
     * 主键
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
     * 剩余金额
     */
    @Column(name = "remain_amount")
    private BigDecimal remainAmount;

    /**
     * 充值次数
     */
    @Column(name = "pay_count")
    private Integer payCount;

    /**
     * 充值总额
     */
    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    /**
     * 电表剩余金额计算时间
     */
    @Column(name = "calc_time")
    private Date calcTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 告警阈值1
     */
    @Column(name = "alarm_threshold")
    private BigDecimal alarmThreshold;

    /**
     * 告警阈值2
     */
    @Column(name = "alarm_threshold1")
    private BigDecimal alarmThreshold1;

    /**
     * 告警阈值3
     */
    @Column(name = "alarm_threshold2")
    private BigDecimal alarmThreshold2;

    /**
     * 透支金额
     */
    @Column(name = "overdraft_amount")
    private Integer overdraftAmount;
    /**
     * 透支金额
     */
    @Column(name = "init_amount")
    private BigDecimal initAmount;

    public BigDecimal getInitAmount() {
        return initAmount;
    }

    public void setInitAmount(BigDecimal initAmount) {
        this.initAmount = initAmount;
    }

    public Integer getOverdraftAmount() {
        return overdraftAmount;
    }

    public void setOverdraftAmount(Integer overdraftAmount) {
        this.overdraftAmount = overdraftAmount;
    }

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
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
     * 获取充值次数
     *
     * @return pay_count - 充值次数
     */
    public Integer getPayCount() {
        return payCount;
    }

    /**
     * 设置充值次数
     *
     * @param payCount 充值次数
     */
    public void setPayCount(Integer payCount) {
        this.payCount = payCount;
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
     * 获取电表剩余金额计算时间
     *
     * @return calc_time - 电表剩余金额计算时间
     */
    public Date getCalcTime() {
        return calcTime;
    }

    /**
     * 设置电表剩余金额计算时间
     *
     * @param calcTime 电表剩余金额计算时间
     */
    public void setCalcTime(Date calcTime) {
        this.calcTime = calcTime;
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
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    public BigDecimal getAlarmThreshold2() {
        return alarmThreshold2;
    }

    public void setAlarmThreshold2(BigDecimal alarmThreshold2) {
        this.alarmThreshold2 = alarmThreshold2;
    }
}