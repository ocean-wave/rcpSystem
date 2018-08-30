package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "em_d_dayfreesum")
public class DayFreeSum implements Serializable {
    private static final long serialVersionUID = -9116732382999306643L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 抄表日期
     */
    @Column(name = "collect_date")
    private Date collectDate;

    /**
     * 总户数
     */
    @Column(name = "customer_count")
    private Integer customerCount;

    /**
     * 全部采集的数
     */
    @Column(name = "success_count")
    private Integer successCount;

    /**
     * 抄收失败的数包括全部失败和部分项失败
     */
    @Column(name = "fail_count")
    private Integer failCount;

    /**
     * 部分项目采集失败
     */
    @Column(name = "portion_fail_count")
    private Integer portionFailCount;

    @Column(name = "all_fail_count")
    private Integer allFailCount;

    /**
     * 成功率
     */
    @Column(name = "success_rate")
    private BigDecimal successRate;

    /**
     * 组织结构
     */
    @Column(name = "org_no")
    private Integer orgNo;

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
     * 应用方案的设备类型
     */
    @Column(name = "device_type")
    private String deviceType;

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
     * 获取抄表日期
     *
     * @return collect_date - 抄表日期
     */
    public Date getCollectDate() {
        return collectDate;
    }

    /**
     * 设置抄表日期
     *
     * @param collectDate 抄表日期
     */
    public void setCollectDate(Date collectDate) {
        this.collectDate = collectDate;
    }

    /**
     * 获取总户数
     *
     * @return customer_count - 总户数
     */
    public Integer getCustomerCount() {
        return customerCount;
    }

    /**
     * 设置总户数
     *
     * @param customerCount 总户数
     */
    public void setCustomerCount(Integer customerCount) {
        this.customerCount = customerCount;
    }

    /**
     * 获取全部采集的数
     *
     * @return success_count - 全部采集的数
     */
    public Integer getSuccessCount() {
        return successCount;
    }

    /**
     * 设置全部采集的数
     *
     * @param successCount 全部采集的数
     */
    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }

    /**
     * 获取抄收失败的数包括全部失败和部分项失败
     *
     * @return fail_count - 抄收失败的数包括全部失败和部分项失败
     */
    public Integer getFailCount() {
        return failCount;
    }

    /**
     * 设置抄收失败的数包括全部失败和部分项失败
     *
     * @param failCount 抄收失败的数包括全部失败和部分项失败
     */
    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }

    /**
     * 获取部分项目采集失败
     *
     * @return portion_fail_count - 部分项目采集失败
     */
    public Integer getPortionFailCount() {
        return portionFailCount;
    }

    /**
     * 设置部分项目采集失败
     *
     * @param portionFailCount 部分项目采集失败
     */
    public void setPortionFailCount(Integer portionFailCount) {
        this.portionFailCount = portionFailCount;
    }

    /**
     * @return all_fail_count
     */
    public Integer getAllFailCount() {
        return allFailCount;
    }

    /**
     * @param allFailCount
     */
    public void setAllFailCount(Integer allFailCount) {
        this.allFailCount = allFailCount;
    }

    /**
     * 获取成功率
     *
     * @return success_rate - 成功率
     */
    public BigDecimal getSuccessRate() {
        return successRate;
    }

    /**
     * 设置成功率
     *
     * @param successRate 成功率
     */
    public void setSuccessRate(BigDecimal successRate) {
        this.successRate = successRate;
    }

    /**
     * 获取组织结构
     *
     * @return org_no - 组织结构
     */
    public Integer getOrgNo() {
        return orgNo;
    }

    /**
     * 设置组织结构
     *
     * @param orgNo 组织结构
     */
    public void setOrgNo(Integer orgNo) {
        this.orgNo = orgNo;
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

    /**
     * 获取应用方案的设备类型
     *
     * @return device_type - 应用方案的设备类型
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * 设置应用方案的设备类型
     *
     * @param deviceType 应用方案的设备类型
     */
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
}