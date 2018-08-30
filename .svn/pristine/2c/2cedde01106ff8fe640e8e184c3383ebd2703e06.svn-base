package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "em_d_charging_use_detailed")
public class ChargingUseDetailed implements Serializable {
    /**
     * 标识id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "charging_plie_guid")
    private String chargingPlieGuid;

    /**
     * 客户唯一标识
     */
    @Column(name = "customer_guid")
    private String customerGuid;

    /**
     * 使用客户微信号
     */
    @Column(name = "webchar_no")
    private String webcharNo;

    /**
     * 同充值方案表一样，充值类别 1-临时充值  2-包月充值 3-一次充满 4-余额活动充值
     */
    @Column(name = "pay_category")
    private Integer payCategory;

    /**
     * 充电方案标识
     */
    @Column(name = "scheme_guid")
    private String schemeGuid;

    /**
     * 1-按时充电 2-按电量充电 3-充满断电
     */
    @Column(name = "charging_way")
    private Integer chargingWay;

    /**
     * 0 - 不限制 其他值标识充电时长，单位小时
     */
    @Column(name = "charging_time")
    private Integer chargingTime;

    /**
     * 应充电电量 0-不限制 其他为电量
     */
    @Column(name = "charging_power")
    private Integer chargingPower;

    /**
     * 开始充电时间
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 结束充电时间
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 充电结束方式 0 - 自动 1 -手动
     */
    @Column(name = "end_method")
    private Integer endMethod;

    /**
     * 实际充电时长 单位(分钟)
     */
    @Column(name = "use_time")
    private Integer useTime;

    /**
     * 实际用电量
     */
    @Column(name = "use_power")
    private BigDecimal usePower;

    /**
     * 电价
     */
    private BigDecimal price;

    /**
     * 实际消费
     */
    @Column(name = "consumption_money")
    private BigDecimal consumptionMoney;

    /**
     * 充电扣除费用
     */
    @Column(name = "deduct_money")
    private BigDecimal deductMoney;

    /**
     * 盈利费用
     */
    @Column(name = "profitable")
    private BigDecimal profitable;

    /**
     * 当前状态 0-正在充电 1-已完成充电
     */
    @Column(name = "state")
    private Integer state;

    /**
     * 充电记录唯一标识
     */
    @Column(name = "charging_guid")
    private String chargingGuid;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 本次充电后剩余次数，包月用户有效
     */
    @Column(name = "after_remain_cnt")
    private Integer afterRemainCnt;

    /**
     * 本次充电后剩余金额
     */
    @Column(name = "after_remain_amount")
    private BigDecimal afterRemainAmount;

    /**
     * 设备上报记录日志表主键
     */
    @Column(name = "dev_log_id")
    private Integer devLogId;

    /**
     * 开启方式 1-微信 2-支付宝 3-IC卡
     */
    @Column(name = "open_means")
    private Integer openMeans;

    /**
     * 开启编号根据开启方式对应编号
     */
    @Column(name = "open_no")
    private String openNo;

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
     * @return charging_plie_guid
     */
    public String getChargingPlieGuid() {
        return chargingPlieGuid;
    }

    /**
     * @param chargingPlieGuid
     */
    public void setChargingPlieGuid(String chargingPlieGuid) {
        this.chargingPlieGuid = chargingPlieGuid;
    }

    /**
     * 获取使用客户微信号
     *
     * @return webchar_no - 使用客户微信号
     */
    public String getWebcharNo() {
        return webcharNo;
    }

    /**
     * 设置使用客户微信号
     *
     * @param webcharNo 使用客户微信号
     */
    public void setWebcharNo(String webcharNo) {
        this.webcharNo = webcharNo;
    }

    /**
     * 获取1-按时充电 2-按电量充电 3-充满断电
     *
     * @return charging_way - 1-按时充电 2-按电量充电 3-充满断电
     */
    public Integer getChargingWay() {
        return chargingWay;
    }

    /**
     * 设置1-按时充电 2-按电量充电 3-充满断电
     *
     * @param chargingWay 1-按时充电 2-按电量充电 3-充满断电
     */
    public void setChargingWay(Integer chargingWay) {
        this.chargingWay = chargingWay;
    }

    /**
     * 获取0 - 不限制 其他值标识充电时长，单位小时
     *
     * @return charging_time - 0 - 不限制 其他值标识充电时长，单位小时
     */
    public Integer getChargingTime() {
        return chargingTime;
    }

    /**
     * 设置0 - 不限制 其他值标识充电时长，单位小时
     *
     * @param chargingTime 0 - 不限制 其他值标识充电时长，单位小时
     */
    public void setChargingTime(Integer chargingTime) {
        this.chargingTime = chargingTime;
    }

    /**
     * 获取应充电电量 0-不限制 其他为电量
     *
     * @return charging_power - 应充电电量 0-不限制 其他为电量
     */
    public Integer getChargingPower() {
        return chargingPower;
    }

    /**
     * 设置应充电电量 0-不限制 其他为电量
     *
     * @param chargingPower 应充电电量 0-不限制 其他为电量
     */
    public void setChargingPower(Integer chargingPower) {
        this.chargingPower = chargingPower;
    }

    /**
     * 获取开始充电时间
     *
     * @return start_time - 开始充电时间
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 设置开始充电时间
     *
     * @param startTime 开始充电时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取结束充电时间
     *
     * @return end_time - 结束充电时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置结束充电时间
     *
     * @param endTime 结束充电时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取充电结束方式 0 - 自动 1 -手动
     *
     * @return end_method - 充电结束方式 0 - 自动 1 -手动
     */
    public Integer getEndMethod() {
        return endMethod;
    }

    /**
     * 设置充电结束方式 0 - 自动 1 -手动
     *
     * @param endMethod 充电结束方式 0 - 自动 1 -手动
     */
    public void setEndMethod(Integer endMethod) {
        this.endMethod = endMethod;
    }

    /**
     * 获取实际充电时长 单位(分钟)
     *
     * @return use_time - 实际充电时长 单位(分钟)
     */
    public Integer getUseTime() {
        return useTime;
    }

    /**
     * 设置实际充电时长 单位(分钟)
     *
     * @param useTime 实际充电时长 单位(分钟)
     */
    public void setUseTime(Integer useTime) {
        this.useTime = useTime;
    }

    /**
     * 获取实际用电量
     *
     * @return use_power - 实际用电量
     */
    public BigDecimal getUsePower() {
        return usePower;
    }

    /**
     * 设置实际用电量
     *
     * @param usePower 实际用电量
     */
    public void setUsePower(BigDecimal usePower) {
        this.usePower = usePower;
    }

    /**
     * 获取电价
     *
     * @return price - 电价
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置电价
     *
     * @param price 电价
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取实际消费
     *
     * @return consumption_money - 实际消费
     */
    public BigDecimal getConsumptionMoney() {
        return consumptionMoney;
    }

    /**
     * 设置实际消费
     *
     * @param consumptionMoney 实际消费
     */
    public void setConsumptionMoney(BigDecimal consumptionMoney) {
        this.consumptionMoney = consumptionMoney;
    }

    /**
     * 获取充电扣除费用
     *
     * @return deduct_money - 充电扣除费用
     */
    public BigDecimal getDeductMoney() {
        return deductMoney;
    }

    /**
     * 设置充电扣除费用
     *
     * @param deductMoney 充电扣除费用
     */
    public void setDeductMoney(BigDecimal deductMoney) {
        this.deductMoney = deductMoney;
    }

    /**
     * 获取盈利费用
     *
     * @return profitable - 盈利费用
     */
    public BigDecimal getProfitable() {
        return profitable;
    }

    /**
     * 设置盈利费用
     *
     * @param profitable 盈利费用
     */
    public void setProfitable(BigDecimal profitable) {
        this.profitable = profitable;
    }

    /**
     * 获取当前状态 0-正在充电 1-已完成充电
     *
     * @return state - 当前状态 0-正在充电 1-已完成充电
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置当前状态 0-正在充电 1-已完成充电
     *
     * @param state 当前状态 0-正在充电 1-已完成充电
     */
    public void setState(Integer state) {
        this.state = state;
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

    public String getCustomerGuid() {
        return customerGuid;
    }

    public void setCustomerGuid(String customerGuid) {
        this.customerGuid = customerGuid;
    }

    public Integer getAfterRemainCnt() {
        return afterRemainCnt;
    }

    public void setAfterRemainCnt(Integer afterRemainCnt) {
        this.afterRemainCnt = afterRemainCnt;
    }

    public BigDecimal getAfterRemainAmount() {
        return afterRemainAmount;
    }

    public void setAfterRemainAmount(BigDecimal afterRemainAmount) {
        this.afterRemainAmount = afterRemainAmount;
    }

    public Integer getDevLogId() {
        return devLogId;
    }

    public void setDevLogId(Integer devLogId) {
        this.devLogId = devLogId;
    }

    public Integer getPayCategory() {
        return payCategory;
    }

    public void setPayCategory(Integer payCategory) {
        this.payCategory = payCategory;
    }

    public Integer getOpenMeans() {
        return openMeans;
    }

    public void setOpenMeans(Integer openMeans) {
        this.openMeans = openMeans;
    }

    public String getOpenNo() {
        return openNo;
    }

    public void setOpenNo(String openNo) {
        this.openNo = openNo;
    }

    public String getSchemeGuid() {
        return schemeGuid;
    }

    public void setSchemeGuid(String schemeGuid) {
        this.schemeGuid = schemeGuid;
    }
}