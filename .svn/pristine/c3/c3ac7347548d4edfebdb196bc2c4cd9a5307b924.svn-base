package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "em_d_meter_day_power")
public class MeterDayPower implements Serializable{
    private static final long serialVersionUID = -4024713997459027371L;




    public static long getSerialVersionUID() {
        return serialVersionUID;
    }



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
     * 上一日数据费率数
     */
    @Column(name = "last_flnum")
    private Integer lastFlnum;

    /**
     * 上一次正向总示数 -1 无效
     */
    @Column(name = "last_read_value")
    private BigDecimal lastReadValue;

    /**
     * 上一次开始正向尖示数 -1 无效
     */
    @Column(name = "last_read_value1")
    private BigDecimal lastReadValue1;

    /**
     * 上一次开始正向峰示数 -1 无效
     */
    @Column(name = "last_read_value2")
    private BigDecimal lastReadValue2;

    /**
     * 上一次开始正向平示数 -1 无效
     */
    @Column(name = "last_read_value3")
    private BigDecimal lastReadValue3;

    /**
     * 上一次开始正向谷示数 -1 无效
     */
    @Column(name = "last_read_value4")
    private BigDecimal lastReadValue4;

    /**
     * 上一日数据采集时间
     */
    @Column(name = "last_collect_time")
    private Date lastCollectTime;

    /**
     * 当前数据费率
     */
    @Column(name = "read_flnum")
    private Integer readFlnum;

    /**
     * 正向总示数 -1 无效
     */
    @Column(name = "read_value")
    private BigDecimal readValue;

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
     * 当前数据采集日期
     */
    @Column(name = "read_collect_time")
    private Date readCollectTime;

    /**
     * 当日正向尖电量 -1 无效
     */
    @Column(name = "day_eq_value1")
    private BigDecimal dayEqValue1;

    /**
     * 当日正向峰电量 -1 无效
     */
    @Column(name = "day_eq_value2")
    private BigDecimal dayEqValue2;

    /**
     * 当日正向平电量 -1 无效
     */
    @Column(name = "day_eq_value3")
    private BigDecimal dayEqValue3;

    /**
     * 当日正向谷电量 -1 无效
     */
    @Column(name = "day_eq_value4")
    private BigDecimal dayEqValue4;

    /**
     * 当日正向总电量 -1 无效
     */
    @Column(name = "day_eq_value")
    private BigDecimal dayEqValue;

    /**
     * 本次数据时间
     */
    @Column(name = "calc_data")
    private Date calcData;

    /**
     * 0 标识冻结 1 标识实时
     */
    @Column(name = "calc_data_type")
    private Integer calcDataType;

    /**
     *  0 标识自动 其他为人员ID
     */
    @Column(name = "create_user_id")
    private Integer createUserId;

    /**
     * 计算时间
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
     * 获取上一日数据费率数
     *
     * @return last_flnum - 上一日数据费率数
     */
    public Integer getLastFlnum() {
        return lastFlnum;
    }

    /**
     * 设置上一日数据费率数
     *
     * @param lastFlnum 上一日数据费率数
     */
    public void setLastFlnum(Integer lastFlnum) {
        this.lastFlnum = lastFlnum;
    }

    /**
     * 获取上一次正向总示数 -1 无效
     *
     * @return last_read_value - 上一次正向总示数 -1 无效
     */
    public BigDecimal getLastReadValue() {
        return lastReadValue;
    }

    /**
     * 设置上一次正向总示数 -1 无效
     *
     * @param lastReadValue 上一次正向总示数 -1 无效
     */
    public void setLastReadValue(BigDecimal lastReadValue) {
        this.lastReadValue = lastReadValue;
    }

    /**
     * 获取上一次开始正向尖示数 -1 无效
     *
     * @return last_read_value1 - 上一次开始正向尖示数 -1 无效
     */
    public BigDecimal getLastReadValue1() {
        return lastReadValue1;
    }

    /**
     * 设置上一次开始正向尖示数 -1 无效
     *
     * @param lastReadValue1 上一次开始正向尖示数 -1 无效
     */
    public void setLastReadValue1(BigDecimal lastReadValue1) {
        this.lastReadValue1 = lastReadValue1;
    }

    /**
     * 获取上一次开始正向峰示数 -1 无效
     *
     * @return last_read_value2 - 上一次开始正向峰示数 -1 无效
     */
    public BigDecimal getLastReadValue2() {
        return lastReadValue2;
    }

    /**
     * 设置上一次开始正向峰示数 -1 无效
     *
     * @param lastReadValue2 上一次开始正向峰示数 -1 无效
     */
    public void setLastReadValue2(BigDecimal lastReadValue2) {
        this.lastReadValue2 = lastReadValue2;
    }

    /**
     * 获取上一次开始正向平示数 -1 无效
     *
     * @return last_read_value3 - 上一次开始正向平示数 -1 无效
     */
    public BigDecimal getLastReadValue3() {
        return lastReadValue3;
    }

    /**
     * 设置上一次开始正向平示数 -1 无效
     *
     * @param lastReadValue3 上一次开始正向平示数 -1 无效
     */
    public void setLastReadValue3(BigDecimal lastReadValue3) {
        this.lastReadValue3 = lastReadValue3;
    }

    /**
     * 获取上一次开始正向谷示数 -1 无效
     *
     * @return last_read_value4 - 上一次开始正向谷示数 -1 无效
     */
    public BigDecimal getLastReadValue4() {
        return lastReadValue4;
    }

    /**
     * 设置上一次开始正向谷示数 -1 无效
     *
     * @param lastReadValue4 上一次开始正向谷示数 -1 无效
     */
    public void setLastReadValue4(BigDecimal lastReadValue4) {
        this.lastReadValue4 = lastReadValue4;
    }

    /**
     * 获取上一日数据采集时间
     *
     * @return last_collect_time - 上一日数据采集时间
     */
    public Date getLastCollectTime() {
        return lastCollectTime;
    }

    /**
     * 设置上一日数据采集时间
     *
     * @param lastCollectTime 上一日数据采集时间
     */
    public void setLastCollectTime(Date lastCollectTime) {
        this.lastCollectTime = lastCollectTime;
    }

    /**
     * 获取当前数据费率
     *
     * @return read_flnum - 当前数据费率
     */
    public Integer getReadFlnum() {
        return readFlnum;
    }

    /**
     * 设置当前数据费率
     *
     * @param readFlnum 当前数据费率
     */
    public void setReadFlnum(Integer readFlnum) {
        this.readFlnum = readFlnum;
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
     * 获取当前数据采集日期
     *
     * @return read_collect_time - 当前数据采集日期
     */
    public Date getReadCollectTime() {
        return readCollectTime;
    }

    /**
     * 设置当前数据采集日期
     *
     * @param readCollectTime 当前数据采集日期
     */
    public void setReadCollectTime(Date readCollectTime) {
        this.readCollectTime = readCollectTime;
    }

    /**
     * 获取当日正向尖电量 -1 无效
     *
     * @return day_eq_value1 - 当日正向尖电量 -1 无效
     */
    public BigDecimal getDayEqValue1() {
        return dayEqValue1;
    }

    /**
     * 设置当日正向尖电量 -1 无效
     *
     * @param dayEqValue1 当日正向尖电量 -1 无效
     */
    public void setDayEqValue1(BigDecimal dayEqValue1) {
        this.dayEqValue1 = dayEqValue1;
    }

    /**
     * 获取当日正向峰电量 -1 无效
     *
     * @return day_eq_value2 - 当日正向峰电量 -1 无效
     */
    public BigDecimal getDayEqValue2() {
        return dayEqValue2;
    }

    /**
     * 设置当日正向峰电量 -1 无效
     *
     * @param dayEqValue2 当日正向峰电量 -1 无效
     */
    public void setDayEqValue2(BigDecimal dayEqValue2) {
        this.dayEqValue2 = dayEqValue2;
    }

    /**
     * 获取当日正向平电量 -1 无效
     *
     * @return day_eq_value3 - 当日正向平电量 -1 无效
     */
    public BigDecimal getDayEqValue3() {
        return dayEqValue3;
    }

    /**
     * 设置当日正向平电量 -1 无效
     *
     * @param dayEqValue3 当日正向平电量 -1 无效
     */
    public void setDayEqValue3(BigDecimal dayEqValue3) {
        this.dayEqValue3 = dayEqValue3;
    }

    /**
     * 获取当日正向谷电量 -1 无效
     *
     * @return day_eq_value4 - 当日正向谷电量 -1 无效
     */
    public BigDecimal getDayEqValue4() {
        return dayEqValue4;
    }

    /**
     * 设置当日正向谷电量 -1 无效
     *
     * @param dayEqValue4 当日正向谷电量 -1 无效
     */
    public void setDayEqValue4(BigDecimal dayEqValue4) {
        this.dayEqValue4 = dayEqValue4;
    }

    /**
     * 获取当日正向总电量 -1 无效
     *
     * @return day_eq_value - 当日正向总电量 -1 无效
     */
    public BigDecimal getDayEqValue() {
        return dayEqValue;
    }

    /**
     * 设置当日正向总电量 -1 无效
     *
     * @param dayEqValue 当日正向总电量 -1 无效
     */
    public void setDayEqValue(BigDecimal dayEqValue) {
        this.dayEqValue = dayEqValue;
    }

    /**
     * 获取本次数据时间
     *
     * @return calc_data - 本次数据时间
     */
    public Date getCalcData() {
        return calcData;
    }

    /**
     * 设置本次数据时间
     *
     * @param calcData 本次数据时间
     */
    public void setCalcData(Date calcData) {
        this.calcData = calcData;
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
}