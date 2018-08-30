package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "em_d_monthsumdata")
public class MonthSumData implements Serializable {
    private static final long serialVersionUID = 2929424767196467955L;
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
     * 设备唯一标识(生成规则:设备类型+补0(不足30位)+设备编号）
     */
    private String cno;

    /**
     * 汇总月份
     */
    @Column(name = "sum_month")
    private Integer sumMonth;

    /**
     * 汇总最大示数
     */
    @Column(name = "max_data")
    private BigDecimal maxData;

    /**
     * 最小值
     */
    @Column(name = "min_data")
    private BigDecimal minData;

    /**
     * 汇总数据
     */
    @Column(name = "sum_data")
    private BigDecimal sumData;

    /**
     * 汇总数据的起始时间
     */
    @Column(name = "sum_start_date")
    private Date sumStartDate;

    /**
     * 汇总数据的截止时间
     */
    @Column(name = "sum_end_date")
    private Date sumEndDate;

    /**
     * 创建人员ID
     */
    @Column(name = "create_user_id")
    private Long createUserId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新人员ID
     */
    @Column(name = "update_user_id")
    private Long updateUserId;

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
     * 用户编号
     */
    @Column(name = "customer_no")
    private String customerNo;

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
     * 获取汇总最大示数
     *
     * @return max_data - 汇总最大示数
     */
    public BigDecimal getMaxData() {
        return maxData;
    }

    /**
     * 设置汇总最大示数
     *
     * @param maxData 汇总最大示数
     */
    public void setMaxData(BigDecimal maxData) {
        this.maxData = maxData;
    }

    /**
     * 获取最小值
     *
     * @return min_data - 最小值
     */
    public BigDecimal getMinData() {
        return minData;
    }

    /**
     * 设置最小值
     *
     * @param minData 最小值
     */
    public void setMinData(BigDecimal minData) {
        this.minData = minData;
    }

    /**
     * 获取汇总数据
     *
     * @return sum_data - 汇总数据
     */
    public BigDecimal getSumData() {
        return sumData;
    }

    /**
     * 设置汇总数据
     *
     * @param sumData 汇总数据
     */
    public void setSumData(BigDecimal sumData) {
        this.sumData = sumData;
    }

    /**
     * 获取汇总数据的起始时间
     *
     * @return sum_start_date - 汇总数据的起始时间
     */
    public Date getSumStartDate() {
        return sumStartDate;
    }

    /**
     * 设置汇总数据的起始时间
     *
     * @param sumStartDate 汇总数据的起始时间
     */
    public void setSumStartDate(Date sumStartDate) {
        this.sumStartDate = sumStartDate;
    }

    /**
     * 获取汇总数据的截止时间
     *
     * @return sum_end_date - 汇总数据的截止时间
     */
    public Date getSumEndDate() {
        return sumEndDate;
    }

    /**
     * 设置汇总数据的截止时间
     *
     * @param sumEndDate 汇总数据的截止时间
     */
    public void setSumEndDate(Date sumEndDate) {
        this.sumEndDate = sumEndDate;
    }

    /**
     * 获取创建人员ID
     *
     * @return create_user_id - 创建人员ID
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置创建人员ID
     *
     * @param createUserId 创建人员ID
     */
    public void setCreateUserId(Long createUserId) {
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
    public Long getUpdateUserId() {
        return updateUserId;
    }

    /**
     * 设置更新人员ID
     *
     * @param updateUserId 更新人员ID
     */
    public void setUpdateUserId(Long updateUserId) {
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