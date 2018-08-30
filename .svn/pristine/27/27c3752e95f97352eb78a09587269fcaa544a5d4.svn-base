package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "em_d_cost_calculate")
public class CostCalculate implements Serializable{
    /**
     * 唯一标识
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 客户标识
     */
    @Column(name = "customer_no")
    private String customerNo;

    /**
     * 表计户号
     */
    @Column(name = "meter_user_no")
    private String meterUserNo;

    /**
     * 电表标识
     */
    private String cno;

    /**
     * 电价方案
     */
    @Column(name = "price_sols_code")
    private String priceSolsCode;

    @Column(name = "rate_index")
    private Integer rateIndex;

    @Column(name = "step_index")
    private Integer stepIndex;

    /**
     * 费率数
     */
    @Column(name = "comm_factor_cnt")
    private Integer commFactorCnt;

    @Column(name = "account_date")
    private Date accountDate;

    /**
     * 正向尖示数
     */
    @Column(name = "read_value1")
    private BigDecimal readValue1;

    /**
     * 正向峰示数
     */
    @Column(name = "read_value2")
    private BigDecimal readValue2;

    /**
     * 正向平示数
     */
    @Column(name = "read_value3")
    private BigDecimal readValue3;

    /**
     * 正向谷示数
     */
    @Column(name = "read_value4")
    private BigDecimal readValue4;

    /**
     * 正向总示数
     */
    @Column(name = "read_value")
    private BigDecimal readValue;

    /**
     * 正向尖电量
     */
    @Column(name = "eq_value1")
    private BigDecimal eqValue1;

    /**
     * 正向峰电量
     */
    @Column(name = "eq_value2")
    private BigDecimal eqValue2;

    /**
     * 正向平电量
     */
    @Column(name = "eq_value3")
    private BigDecimal eqValue3;

    /**
     * 正向谷电量
     */
    @Column(name = "eq_value4")
    private BigDecimal eqValue4;

    /**
     * 正向总电量
     */
    @Column(name = "eq_value")
    private BigDecimal eqValue;

    /**
     * 本次数据时间
     */
    @Column(name = "local_data_time")
    private Date localDataTime;

    @Column(name = "day_eq_value1")
    private BigDecimal dayEqValue1;

    @Column(name = "day_eq_value2")
    private BigDecimal dayEqValue2;

    @Column(name = "day_eq_value3")
    private BigDecimal dayEqValue3;

    @Column(name = "day_eq_value4")
    private BigDecimal dayEqValue4;

    @Column(name = "day_eq_value")
    private BigDecimal dayEqValue;

    /**
     * 数据计算日
     */
    @Column(name = "calc_data")
    private Date calcData;

    /**
     * 结算金额
     */
    @Column(name = "calc_money")
    private BigDecimal calcMoney;

    /**
     * 0 标识冻结 1 标识实时
     */
    @Column(name = "calc_data_type")
    private Integer calcDataType;

    /**
     * 计算时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     *  0 标识自动 其他为人员ID
     */
    @Column(name = "create_user_id")
    private Integer createUserId;

    @Column(name = "day_calc_money")
    private BigDecimal dayCalcMoney;

    @Column(name = "last_read_value")
    private BigDecimal lastReadValue;

    @Column(name = "day_money")
    private BigDecimal dayMoney;
    @Column(name = "is_month_settlement")
    private int isMonthSettlement;
    @Column(name = "group_guid")
    private String groupGuid;
    @Column(name = "last_data_time")
    private String lastDataTime;

    public String getLastDataTime() {
        return lastDataTime;
    }

    public void setLastDataTime(String lastDataTime) {
        this.lastDataTime = lastDataTime;
    }

    public int getIsMonthSettlement() {
        return isMonthSettlement;
    }

    public void setIsMonthSettlement(int isMonthSettlement) {
        this.isMonthSettlement = isMonthSettlement;
    }

    public String getGroupGuid() {
        return groupGuid;
    }

    public void setGroupGuid(String groupGuid) {
        this.groupGuid = groupGuid;
    }

    public BigDecimal getDayMoney() {
        return dayMoney;
    }

    public void setDayMoney(BigDecimal dayMoney) {
        this.dayMoney = dayMoney;
    }
    /**
     * 获取唯一标识
     *
     * @return id - 唯一标识
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置唯一标识
     *
     * @param id 唯一标识
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取客户标识
     *
     * @return customer_no - 客户标识
     */
    public String getCustomerNo() {
        return customerNo;
    }

    /**
     * 设置客户标识
     *
     * @param customerNo 客户标识
     */
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getMeterUserNo() {
        return meterUserNo;
    }

    public void setMeterUserNo(String meterUserNo) {
        this.meterUserNo = meterUserNo;
    }

    /**
     * 获取电表标识
     *
     * @return cno - 电表标识
     */
    public String getCno() {
        return cno;
    }

    /**
     * 设置电表标识
     *
     * @param cno 电表标识
     */
    public void setCno(String cno) {
        this.cno = cno;
    }

    /**
     * 获取电价方案
     *
     * @return price_sols_code - 电价方案
     */
    public String getPriceSolsCode() {
        return priceSolsCode;
    }

    /**
     * 设置电价方案
     *
     * @param priceSolsCode 电价方案
     */
    public void setPriceSolsCode(String priceSolsCode) {
        this.priceSolsCode = priceSolsCode;
    }

    /**
     * 获取正向尖示数
     *
     * @return read_value1 - 正向尖示数
     */
    public BigDecimal getReadValue1() {
        return readValue1;
    }

    /**
     * 设置正向尖示数
     *
     * @param readValue1 正向尖示数
     */
    public void setReadValue1(BigDecimal readValue1) {
        this.readValue1 = readValue1;
    }

    /**
     * 获取正向峰示数
     *
     * @return read_value2 - 正向峰示数
     */
    public BigDecimal getReadValue2() {
        return readValue2;
    }

    /**
     * 设置正向峰示数
     *
     * @param readValue2 正向峰示数
     */
    public void setReadValue2(BigDecimal readValue2) {
        this.readValue2 = readValue2;
    }

    /**
     * 获取正向平示数
     *
     * @return read_value3 - 正向平示数
     */
    public BigDecimal getReadValue3() {
        return readValue3;
    }

    /**
     * 设置正向平示数
     *
     * @param readValue3 正向平示数
     */
    public void setReadValue3(BigDecimal readValue3) {
        this.readValue3 = readValue3;
    }

    /**
     * 获取正向谷示数
     *
     * @return read_value4 - 正向谷示数
     */
    public BigDecimal getReadValue4() {
        return readValue4;
    }

    /**
     * 设置正向谷示数
     *
     * @param readValue4 正向谷示数
     */
    public void setReadValue4(BigDecimal readValue4) {
        this.readValue4 = readValue4;
    }

    /**
     * 获取正向总示数
     *
     * @return read_value - 正向总示数
     */
    public BigDecimal getReadValue() {
        return readValue;
    }

    /**
     * 设置正向总示数
     *
     * @param readValue 正向总示数
     */
    public void setReadValue(BigDecimal readValue) {
        this.readValue = readValue;
    }

    /**
     * 获取正向尖电量
     *
     * @return eq_value1 - 正向尖电量
     */
    public BigDecimal getEqValue1() {
        return eqValue1;
    }

    /**
     * 设置正向尖电量
     *
     * @param eqValue1 正向尖电量
     */
    public void setEqValue1(BigDecimal eqValue1) {
        this.eqValue1 = eqValue1;
    }

    /**
     * 获取正向峰电量
     *
     * @return eq_value2 - 正向峰电量
     */
    public BigDecimal getEqValue2() {
        return eqValue2;
    }

    /**
     * 设置正向峰电量
     *
     * @param eqValue2 正向峰电量
     */
    public void setEqValue2(BigDecimal eqValue2) {
        this.eqValue2 = eqValue2;
    }

    /**
     * 获取正向平电量
     *
     * @return eq_value3 - 正向平电量
     */
    public BigDecimal getEqValue3() {
        return eqValue3;
    }

    /**
     * 设置正向平电量
     *
     * @param eqValue3 正向平电量
     */
    public void setEqValue3(BigDecimal eqValue3) {
        this.eqValue3 = eqValue3;
    }

    /**
     * 获取正向谷电量
     *
     * @return eq_value4 - 正向谷电量
     */
    public BigDecimal getEqValue4() {
        return eqValue4;
    }

    /**
     * 设置正向谷电量
     *
     * @param eqValue4 正向谷电量
     */
    public void setEqValue4(BigDecimal eqValue4) {
        this.eqValue4 = eqValue4;
    }

    /**
     * 获取正向总电量
     *
     * @return eq_value - 正向总电量
     */
    public BigDecimal getEqValue() {
        return eqValue;
    }

    /**
     * 设置正向总电量
     *
     * @param eqValue 正向总电量
     */
    public void setEqValue(BigDecimal eqValue) {
        this.eqValue = eqValue;
    }

    /**
     * 获取本次数据时间
     *
     * @return local_data_time - 本次数据时间
     */
    public Date getLocalDataTime() {
        return localDataTime;
    }

    /**
     * 设置本次数据时间
     *
     * @param localDataTime 本次数据时间
     */
    public void setLocalDataTime(Date localDataTime) {
        this.localDataTime = localDataTime;
    }

    /**
     * 获取数据计算日
     *
     * @return calc_data - 数据计算日
     */
    public Date getCalcData() {
        return calcData;
    }

    /**
     * 设置数据计算日
     *
     * @param calcData 数据计算日
     */
    public void setCalcData(Date calcData) {
        this.calcData = calcData;
    }

    /**
     * 获取结算金额
     *
     * @return calc_money - 结算金额
     */
    public BigDecimal getCalcMoney() {
        return calcMoney;
    }

    /**
     * 设置结算金额
     *
     * @param calcMoney 结算金额
     */
    public void setCalcMoney(BigDecimal calcMoney) {
        this.calcMoney = calcMoney;
    }

    /**
     * 获取0 标识冻结 1 标识实时
     *
     * @return calc_data_type - 0 标识冻结 1 标识实时
     */
    public Integer getCalcDataType() {
        return calcDataType;
    }

    /**
     * 设置0 标识冻结 1 标识实时
     *
     * @param calcDataType 0 标识冻结 1 标识实时
     */
    public void setCalcDataType(Integer calcDataType) {
        this.calcDataType = calcDataType;
    }

    /**
     * 获取计算时间
     *
     * @return create_time - 计算时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置计算时间
     *
     * @param createTime 计算时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取 0 标识自动 其他为人员ID
     *
     * @return create_user_id -  0 标识自动 其他为人员ID
     */
    public Integer getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置 0 标识自动 其他为人员ID
     *
     * @param createUserId  0 标识自动 其他为人员ID
     */
    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getRateIndex() {
        return rateIndex;
    }

    public void setRateIndex(Integer rateIndex) {
        this.rateIndex = rateIndex;
    }

    public Integer getStepIndex() {
        return stepIndex;
    }

    public void setStepIndex(Integer stepIndex) {
        this.stepIndex = stepIndex;
    }

    public Date getAccountDate() {
        return accountDate;
    }

    public void setAccountDate(Date accountDate) {
        this.accountDate = accountDate;
    }

    public BigDecimal getDayEqValue1() {
        return dayEqValue1;
    }

    public void setDayEqValue1(BigDecimal dayEqValue1) {
        this.dayEqValue1 = dayEqValue1;
    }

    public BigDecimal getDayEqValue2() {
        return dayEqValue2;
    }

    public void setDayEqValue2(BigDecimal dayEqValue2) {
        this.dayEqValue2 = dayEqValue2;
    }

    public BigDecimal getDayEqValue3() {
        return dayEqValue3;
    }

    public void setDayEqValue3(BigDecimal dayEqValue3) {
        this.dayEqValue3 = dayEqValue3;
    }

    public BigDecimal getDayEqValue4() {
        return dayEqValue4;
    }

    public void setDayEqValue4(BigDecimal dayEqValue4) {
        this.dayEqValue4 = dayEqValue4;
    }

    public BigDecimal getDayEqValue() {
        return dayEqValue;
    }

    public void setDayEqValue(BigDecimal dayEqValue) {
        this.dayEqValue = dayEqValue;
    }

    public BigDecimal getDayCalcMoney() {
        return dayCalcMoney;
    }

    public void setDayCalcMoney(BigDecimal dayCalcMoney) {
        this.dayCalcMoney = dayCalcMoney;
    }

    public BigDecimal getLastReadValue() {
        return lastReadValue;
    }

    public void setLastReadValue(BigDecimal lastReadValue) {
        this.lastReadValue = lastReadValue;
    }

    public Integer getCommFactorCnt() {
        return commFactorCnt;
    }

    public void setCommFactorCnt(Integer commFactorCnt) {
        this.commFactorCnt = commFactorCnt;
    }
}