package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "em_d_monthsumramount")
public class MonthSumrAmount implements Serializable {
    private static final long serialVersionUID = 6334732364906738251L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 设备类型(01-LoRa网管，02-LoRa节点，04-集中器，05-采集器，07-电表...)',
     */
    @Column(name = "device_type")
    private String deviceType;

    /**
     * 设备唯一标识(生成规则:设备类型+补0(不足30位)+设备编号）
     */
    private String cno;

    /**
     * 汇总月份
     */
    @Column(name = "sum_month")
    private Integer sumMonth;

    /**
     * 创建人员ID
     */
    @Column(name = "create_user_id")
    private Integer createUserId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新人员ID
     */
    @Column(name = "update_user_id")
    private Integer updateUserId;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 汇总年
     */
    @Column(name = "sum_year")
    private Integer sumYear;

    /**
     * 缴费总额
     */
    @Column(name = "sum_payment")
    private BigDecimal sumPayment;

    /**
     * 剩余金额
     */
    @Column(name = "sum_remain_amount")
    private BigDecimal sumRemainAmount;

    /**
     * 消费金额
     */
    @Column(name = "sum_use_pay")
    private BigDecimal sumUsePay;

    /**
     * 汇总数据开始时间
     */
    @Column(name = "sum_ra_start_date")
    private Date sumRaStartDate;

    /**
     * 汇总金额的结束时间
     */
    @Column(name = "sum_ra_endt_date")
    private Date sumRaEndtDate;

    /**
     * 用户编号
     */
    @Column(name = "customer_no")
    private String customerNo;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取设备类型(01-LoRa网管，02-LoRa节点，04-集中器，05-采集器，07-电表...)',
     *
     * @return device_type - 设备类型(01-LoRa网管，02-LoRa节点，04-集中器，05-采集器，07-电表...)',
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * 设置设备类型(01-LoRa网管，02-LoRa节点，04-集中器，05-采集器，07-电表...)',
     *
     * @param deviceType 设备类型(01-LoRa网管，02-LoRa节点，04-集中器，05-采集器，07-电表...)',
     */
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
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
     * 获取汇总月份
     *
     * @return sum_month - 汇总月份
     */
    public Integer getSumMonth() {
        return sumMonth;
    }

    /**
     * 设置汇总月份
     *
     * @param sumMonth 汇总月份
     */
    public void setSumMonth(Integer sumMonth) {
        this.sumMonth = sumMonth;
    }

    /**
     * 获取创建人员ID
     *
     * @return create_user_id - 创建人员ID
     */
    public Integer getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置创建人员ID
     *
     * @param createUserId 创建人员ID
     */
    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
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
     * 获取更新人员ID
     *
     * @return update_user_id - 更新人员ID
     */
    public Integer getUpdateUserId() {
        return updateUserId;
    }

    /**
     * 设置更新人员ID
     *
     * @param updateUserId 更新人员ID
     */
    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
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

    /**
     * 获取汇总年
     *
     * @return sum_year - 汇总年
     */
    public Integer getSumYear() {
        return sumYear;
    }

    /**
     * 设置汇总年
     *
     * @param sumYear 汇总年
     */
    public void setSumYear(Integer sumYear) {
        this.sumYear = sumYear;
    }

    /**
     * 获取缴费总额
     *
     * @return sum_payment - 缴费总额
     */
    public BigDecimal getSumPayment() {
        return sumPayment;
    }

    /**
     * 设置缴费总额
     *
     * @param sumPayment 缴费总额
     */
    public void setSumPayment(BigDecimal sumPayment) {
        this.sumPayment = sumPayment;
    }

    /**
     * 获取剩余金额
     *
     * @return sum_remain_amount - 剩余金额
     */
    public BigDecimal getSumRemainAmount() {
        return sumRemainAmount;
    }

    /**
     * 设置剩余金额
     *
     * @param sumRemainAmount 剩余金额
     */
    public void setSumRemainAmount(BigDecimal sumRemainAmount) {
        this.sumRemainAmount = sumRemainAmount;
    }

    /**
     * 获取消费金额
     *
     * @return sum_use_pay - 消费金额
     */
    public BigDecimal getSumUsePay() {
        return sumUsePay;
    }

    /**
     * 设置消费金额
     *
     * @param sumUsePay 消费金额
     */
    public void setSumUsePay(BigDecimal sumUsePay) {
        this.sumUsePay = sumUsePay;
    }

    /**
     * 获取汇总数据开始时间
     *
     * @return sum_ra_start_date - 汇总数据开始时间
     */
    public Date getSumRaStartDate() {
        return sumRaStartDate;
    }

    /**
     * 设置汇总数据开始时间
     *
     * @param sumRaStartDate 汇总数据开始时间
     */
    public void setSumRaStartDate(Date sumRaStartDate) {
        this.sumRaStartDate = sumRaStartDate;
    }

    /**
     * 获取汇总金额的结束时间
     *
     * @return sum_ra_endt_date - 汇总金额的结束时间
     */
    public Date getSumRaEndtDate() {
        return sumRaEndtDate;
    }

    /**
     * 设置汇总金额的结束时间
     *
     * @param sumRaEndtDate 汇总金额的结束时间
     */
    public void setSumRaEndtDate(Date sumRaEndtDate) {
        this.sumRaEndtDate = sumRaEndtDate;
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
}