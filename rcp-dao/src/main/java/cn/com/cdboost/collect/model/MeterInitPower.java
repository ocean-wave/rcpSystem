package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "em_d_meter_init_power")
public class MeterInitPower implements Serializable{
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

    /**
     * 运行费率电价套数 1 标识第一套 2标识第二套
     */
    @Column(name = "rate_index")
    private Integer rateIndex;

    /**
     * 运行阶梯电价套数 1 标识第一套 2标识第二套
     */
    @Column(name = "step_index")
    private Integer stepIndex;

    /**
     * 费率数
     */
    @Column(name = "comm_factor_cnt")
    private Integer commFactorCnt;

    /**
     * 结算日
     */
    @Column(name = "account_date")
    private Date accountDate;

    /**
     * 正向尖示数 -1 无效
     */
    @Column(name = "read_value1")
    private BigDecimal readValue1;

    /**
     * 正向峰示数 -1 无效
     */
    @Column(name = "read_value2")
    private BigDecimal readValue2;

    /**
     * 正向平示数 -1 无效
     */
    @Column(name = "read_value3")
    private BigDecimal readValue3;

    /**
     * 正向谷示数 -1 无效
     */
    @Column(name = "read_value4")
    private BigDecimal readValue4;

    /**
     * 正向总示数 -1 无效
     */
    @Column(name = "read_value")
    private BigDecimal readValue;

    /**
     * 本次数据时间
     */
    @Column(name = "local_data_time")
    private Date localDataTime;

    /**
     * 数据计算日
     */
    @Column(name = "calc_data")
    private Date calcData;

    /**
     * 初始化时间
     */
    @Column(name = "create_time")
    private Date createTime;

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
     * 获取运行费率电价套数 1 标识第一套 2标识第二套
     *
     * @return rate_index - 运行费率电价套数 1 标识第一套 2标识第二套
     */
    public Integer getRateIndex() {
        return rateIndex;
    }

    /**
     * 设置运行费率电价套数 1 标识第一套 2标识第二套
     *
     * @param rateIndex 运行费率电价套数 1 标识第一套 2标识第二套
     */
    public void setRateIndex(Integer rateIndex) {
        this.rateIndex = rateIndex;
    }

    /**
     * 获取运行阶梯电价套数 1 标识第一套 2标识第二套
     *
     * @return step_index - 运行阶梯电价套数 1 标识第一套 2标识第二套
     */
    public Integer getStepIndex() {
        return stepIndex;
    }

    /**
     * 设置运行阶梯电价套数 1 标识第一套 2标识第二套
     *
     * @param stepIndex 运行阶梯电价套数 1 标识第一套 2标识第二套
     */
    public void setStepIndex(Integer stepIndex) {
        this.stepIndex = stepIndex;
    }

    /**
     * 获取费率数
     *
     * @return comm_factor_cnt - 费率数
     */
    public Integer getCommFactorCnt() {
        return commFactorCnt;
    }

    /**
     * 设置费率数
     *
     * @param commFactorCnt 费率数
     */
    public void setCommFactorCnt(Integer commFactorCnt) {
        this.commFactorCnt = commFactorCnt;
    }

    /**
     * 获取结算日
     *
     * @return account_date - 结算日
     */
    public Date getAccountDate() {
        return accountDate;
    }

    /**
     * 设置结算日
     *
     * @param accountDate 结算日
     */
    public void setAccountDate(Date accountDate) {
        this.accountDate = accountDate;
    }

    /**
     * 获取正向尖示数 -1 无效
     *
     * @return read_value1 - 正向尖示数 -1 无效
     */
    public BigDecimal getReadValue1() {
        return readValue1;
    }

    /**
     * 设置正向尖示数 -1 无效
     *
     * @param readValue1 正向尖示数 -1 无效
     */
    public void setReadValue1(BigDecimal readValue1) {
        this.readValue1 = readValue1;
    }

    /**
     * 获取正向峰示数 -1 无效
     *
     * @return read_value2 - 正向峰示数 -1 无效
     */
    public BigDecimal getReadValue2() {
        return readValue2;
    }

    /**
     * 设置正向峰示数 -1 无效
     *
     * @param readValue2 正向峰示数 -1 无效
     */
    public void setReadValue2(BigDecimal readValue2) {
        this.readValue2 = readValue2;
    }

    /**
     * 获取正向平示数 -1 无效
     *
     * @return read_value3 - 正向平示数 -1 无效
     */
    public BigDecimal getReadValue3() {
        return readValue3;
    }

    /**
     * 设置正向平示数 -1 无效
     *
     * @param readValue3 正向平示数 -1 无效
     */
    public void setReadValue3(BigDecimal readValue3) {
        this.readValue3 = readValue3;
    }

    /**
     * 获取正向谷示数 -1 无效
     *
     * @return read_value4 - 正向谷示数 -1 无效
     */
    public BigDecimal getReadValue4() {
        return readValue4;
    }

    /**
     * 设置正向谷示数 -1 无效
     *
     * @param readValue4 正向谷示数 -1 无效
     */
    public void setReadValue4(BigDecimal readValue4) {
        this.readValue4 = readValue4;
    }

    /**
     * 获取正向总示数 -1 无效
     *
     * @return read_value - 正向总示数 -1 无效
     */
    public BigDecimal getReadValue() {
        return readValue;
    }

    /**
     * 设置正向总示数 -1 无效
     *
     * @param readValue 正向总示数 -1 无效
     */
    public void setReadValue(BigDecimal readValue) {
        this.readValue = readValue;
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
     * 获取初始化时间
     *
     * @return create_time - 初始化时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置初始化时间
     *
     * @param createTime 初始化时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}