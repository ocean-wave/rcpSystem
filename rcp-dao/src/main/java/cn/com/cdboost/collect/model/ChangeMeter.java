package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "em_d_changemeter")
public class ChangeMeter implements Serializable{
    private static final long serialVersionUID = -8153397761810431109L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 设备类型(01-LoRa网管，02-LoRa节点，04-集中器，05-采集器，07-电表...)
     */
    @Column(name = "device_type")
    private String deviceType;

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
     * 旧表表号
     */
    @Column(name = "meter_no")
    private String meterNo;

    /**
     * 旧表通信地址
     */
    @Column(name = "meter_addr")
    private String meterAddr;

    /**
     * 旧表总示示数
     */
    private BigDecimal power;

    /**
     * 旧表剩余金额
     */
    @Column(name = "remain_amount")
    private BigDecimal remainAmount;

    /**
     * 更换时间
     */
    @Column(name = "change_time")
    private Date changeTime;

    /**
     * 设备唯一标识(生成规则:设备类型+补0(不足30位)+设备编号）
     */
    @Column(name = "new_cno")
    private String newCno;

    /**
     * 新表表号
     */
    @Column(name = "new_meter_no")
    private String newMeterNo;

    /**
     * 新表通信地址
     */
    @Column(name = "new_meter_addr")
    private String newMeterAddr;

    /**
     * 新表总示数
     */
    @Column(name = "new_power")
    private BigDecimal newPower;

    /**
     * 新表剩余金额
     */
    @Column(name = "new_remain_amount")
    private BigDecimal newRemainAmount;

    /**
     * 更换原因
     */
    @Column(name = "change_remark")
    private String changeRemark;

    /**
     * 更换人员
     */
    @Column(name = "change_user_id")
    private Long changeUserId;

    /**
     * 当前唯一标识,UUID去掉中间的-
     */
    @Column(name = "change_unique")
    private String changeUnique;

    /**
     * 旧表是否IC卡开户  0-未IC开户 1开户制卡失败 2-开户成功 
     */
    @Column(name = "is_account")
    private Integer isAccount;

    /**
     * 旧表开户时间
     */
    @Column(name = "acct_datetime")
    private Date acctDatetime;

    /**
     * 旧表充值总额
     */
    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    /**
     * 旧表电表预留金额
     */
    @Column(name = "init_amount")
    private BigDecimal initAmount;

    /**
     * 旧表购电次数
     */
    @Column(name = "pay_count")
    private Integer payCount;

    /**
     * 旧表电表中的购电次数
     */
    @Column(name = "meter_pay_count")
    private Integer meterPayCount;

    /**
     * 旧表中的购电总额
     */
    @Column(name = "meter_total_amount")
    private BigDecimal meterTotalAmount;

    /**
     * 旧表倍率
     */
    private Integer ratio;

    /**
     * 旧表规约
     */
    @Column(name = "comm_rule")
    private Integer commRule;

    /**
     * 设备类型 0其他 1 -09电表 2-13表
     */
    @Column(name = "meter_type")
    private Integer meterType;

    /**
     * 表厂家
     */
    @Column(name = "meter_factory")
    private String meterFactory;

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
     * 获取旧表表号
     *
     * @return meter_no - 旧表表号
     */
    public String getMeterNo() {
        return meterNo;
    }

    /**
     * 设置旧表表号
     *
     * @param meterNo 旧表表号
     */
    public void setMeterNo(String meterNo) {
        this.meterNo = meterNo;
    }

    /**
     * 获取旧表通信地址
     *
     * @return meter_addr - 旧表通信地址
     */
    public String getMeterAddr() {
        return meterAddr;
    }

    /**
     * 设置旧表通信地址
     *
     * @param meterAddr 旧表通信地址
     */
    public void setMeterAddr(String meterAddr) {
        this.meterAddr = meterAddr;
    }

    /**
     * 获取旧表总示示数
     *
     * @return power - 旧表总示示数
     */
    public BigDecimal getPower() {
        return power;
    }

    /**
     * 设置旧表总示示数
     *
     * @param power 旧表总示示数
     */
    public void setPower(BigDecimal power) {
        this.power = power;
    }

    /**
     * 获取旧表剩余金额
     *
     * @return remain_amount - 旧表剩余金额
     */
    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    /**
     * 设置旧表剩余金额
     *
     * @param remainAmount 旧表剩余金额
     */
    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }

    /**
     * 获取更换时间
     *
     * @return change_time - 更换时间
     */
    public Date getChangeTime() {
        return changeTime;
    }

    /**
     * 设置更换时间
     *
     * @param changeTime 更换时间
     */
    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    /**
     * 获取设备唯一标识(生成规则:设备类型+补0(不足30位)+设备编号）
     *
     * @return new_cno - 设备唯一标识(生成规则:设备类型+补0(不足30位)+设备编号）
     */
    public String getNewCno() {
        return newCno;
    }

    /**
     * 设置设备唯一标识(生成规则:设备类型+补0(不足30位)+设备编号）
     *
     * @param newCno 设备唯一标识(生成规则:设备类型+补0(不足30位)+设备编号）
     */
    public void setNewCno(String newCno) {
        this.newCno = newCno;
    }

    /**
     * 获取新表表号
     *
     * @return new_meter_no - 新表表号
     */
    public String getNewMeterNo() {
        return newMeterNo;
    }

    /**
     * 设置新表表号
     *
     * @param newMeterNo 新表表号
     */
    public void setNewMeterNo(String newMeterNo) {
        this.newMeterNo = newMeterNo;
    }

    /**
     * 获取新表通信地址
     *
     * @return new_meter_addr - 新表通信地址
     */
    public String getNewMeterAddr() {
        return newMeterAddr;
    }

    /**
     * 设置新表通信地址
     *
     * @param newMeterAddr 新表通信地址
     */
    public void setNewMeterAddr(String newMeterAddr) {
        this.newMeterAddr = newMeterAddr;
    }

    /**
     * 获取新表总示数
     *
     * @return new_power - 新表总示数
     */
    public BigDecimal getNewPower() {
        return newPower;
    }

    /**
     * 设置新表总示数
     *
     * @param newPower 新表总示数
     */
    public void setNewPower(BigDecimal newPower) {
        this.newPower = newPower;
    }

    /**
     * 获取新表剩余金额
     *
     * @return new_remain_amount - 新表剩余金额
     */
    public BigDecimal getNewRemainAmount() {
        return newRemainAmount;
    }

    /**
     * 设置新表剩余金额
     *
     * @param newRemainAmount 新表剩余金额
     */
    public void setNewRemainAmount(BigDecimal newRemainAmount) {
        this.newRemainAmount = newRemainAmount;
    }

    /**
     * 获取更换原因
     *
     * @return change_remark - 更换原因
     */
    public String getChangeRemark() {
        return changeRemark;
    }

    /**
     * 设置更换原因
     *
     * @param changeRemark 更换原因
     */
    public void setChangeRemark(String changeRemark) {
        this.changeRemark = changeRemark;
    }

    /**
     * 获取更换人员
     *
     * @return change_user_id - 更换人员
     */
    public Long getChangeUserId() {
        return changeUserId;
    }

    /**
     * 设置更换人员
     *
     * @param changeUserId 更换人员
     */
    public void setChangeUserId(Long changeUserId) {
        this.changeUserId = changeUserId;
    }

    /**
     * 获取当前唯一标识,UUID去掉中间的-
     *
     * @return change_unique - 当前唯一标识,UUID去掉中间的-
     */
    public String getChangeUnique() {
        return changeUnique;
    }

    /**
     * 设置当前唯一标识,UUID去掉中间的-
     *
     * @param changeUnique 当前唯一标识,UUID去掉中间的-
     */
    public void setChangeUnique(String changeUnique) {
        this.changeUnique = changeUnique;
    }

    /**
     * 获取旧表是否IC卡开户  0-未IC开户 1开户制卡失败 2-开户成功 
     *
     * @return is_account - 旧表是否IC卡开户  0-未IC开户 1开户制卡失败 2-开户成功 
     */
    public Integer getIsAccount() {
        return isAccount;
    }

    /**
     * 设置旧表是否IC卡开户  0-未IC开户 1开户制卡失败 2-开户成功 
     *
     * @param isAccount 旧表是否IC卡开户  0-未IC开户 1开户制卡失败 2-开户成功 
     */
    public void setIsAccount(Integer isAccount) {
        this.isAccount = isAccount;
    }

    /**
     * 获取旧表开户时间
     *
     * @return acct_datetime - 旧表开户时间
     */
    public Date getAcctDatetime() {
        return acctDatetime;
    }

    /**
     * 设置旧表开户时间
     *
     * @param acctDatetime 旧表开户时间
     */
    public void setAcctDatetime(Date acctDatetime) {
        this.acctDatetime = acctDatetime;
    }

    /**
     * 获取旧表充值总额
     *
     * @return total_amount - 旧表充值总额
     */
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    /**
     * 设置旧表充值总额
     *
     * @param totalAmount 旧表充值总额
     */
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * 获取旧表电表预留金额
     *
     * @return init_amount - 旧表电表预留金额
     */
    public BigDecimal getInitAmount() {
        return initAmount;
    }

    /**
     * 设置旧表电表预留金额
     *
     * @param initAmount 旧表电表预留金额
     */
    public void setInitAmount(BigDecimal initAmount) {
        this.initAmount = initAmount;
    }

    /**
     * 获取旧表购电次数
     *
     * @return pay_count - 旧表购电次数
     */
    public Integer getPayCount() {
        return payCount;
    }

    /**
     * 设置旧表购电次数
     *
     * @param payCount 旧表购电次数
     */
    public void setPayCount(Integer payCount) {
        this.payCount = payCount;
    }

    /**
     * 获取旧表电表中的购电次数
     *
     * @return meter_pay_count - 旧表电表中的购电次数
     */
    public Integer getMeterPayCount() {
        return meterPayCount;
    }

    /**
     * 设置旧表电表中的购电次数
     *
     * @param meterPayCount 旧表电表中的购电次数
     */
    public void setMeterPayCount(Integer meterPayCount) {
        this.meterPayCount = meterPayCount;
    }

    /**
     * 获取旧表中的购电总额
     *
     * @return meter_total_amount - 旧表中的购电总额
     */
    public BigDecimal getMeterTotalAmount() {
        return meterTotalAmount;
    }

    /**
     * 设置旧表中的购电总额
     *
     * @param meterTotalAmount 旧表中的购电总额
     */
    public void setMeterTotalAmount(BigDecimal meterTotalAmount) {
        this.meterTotalAmount = meterTotalAmount;
    }

    /**
     * 获取旧表倍率
     *
     * @return ratio - 旧表倍率
     */
    public Integer getRatio() {
        return ratio;
    }

    /**
     * 设置旧表倍率
     *
     * @param ratio 旧表倍率
     */
    public void setRatio(Integer ratio) {
        this.ratio = ratio;
    }

    /**
     * 获取旧表规约
     *
     * @return comm_rule - 旧表规约
     */
    public Integer getCommRule() {
        return commRule;
    }

    /**
     * 设置旧表规约
     *
     * @param commRule 旧表规约
     */
    public void setCommRule(Integer commRule) {
        this.commRule = commRule;
    }

    /**
     * 获取设备类型 0其他 1 -09电表 2-13表
     *
     * @return meter_type - 设备类型 0其他 1 -09电表 2-13表
     */
    public Integer getMeterType() {
        return meterType;
    }

    /**
     * 设置设备类型 0其他 1 -09电表 2-13表
     *
     * @param meterType 设备类型 0其他 1 -09电表 2-13表
     */
    public void setMeterType(Integer meterType) {
        this.meterType = meterType;
    }

    /**
     * 获取表厂家
     *
     * @return meter_factory - 表厂家
     */
    public String getMeterFactory() {
        return meterFactory;
    }

    /**
     * 设置表厂家
     *
     * @param meterFactory 表厂家
     */
    public void setMeterFactory(String meterFactory) {
        this.meterFactory = meterFactory;
    }
}