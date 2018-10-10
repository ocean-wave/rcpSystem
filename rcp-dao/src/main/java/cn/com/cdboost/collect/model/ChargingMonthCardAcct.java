package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "em_d_charging_month_card_acct")
public class ChargingMonthCardAcct implements Serializable{
    /**
     * 标识id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 客户唯一标识
     */
    @Column(name = "customer_guid")
    private String customerGuid;

    /**
     * 购买次数
     */
    @Column(name = "pay_cnt")
    private Integer payCnt;

    /**
     * 月卡剩余次数
     */
    @Column(name = "remain_cnt")
    private Integer remainCnt;

    /**
     * 月卡有效截止日期
     */
    @Column(name = "expire_time")
    private Date expireTime;

    /**
     * 电瓶车功率类型
     */
    private Integer power;

    /**
     * 最小功率
     */
    @Column(name = "min_power")
    private Integer minPower;

    /**
     * 最大功率
     */
    @Column(name = "max_power")
    private Integer maxPower;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 方案唯一标识
     */
    @Column(name = "scheme_guid")
    private String schemeGuid;

    /**
     * 站点唯一标识
     */
    @Column(name = "project_guid")
    private String projectGuid;

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
     * 获取客户唯一标识
     *
     * @return customer_guid - 客户唯一标识
     */
    public String getCustomerGuid() {
        return customerGuid;
    }

    /**
     * 设置客户唯一标识
     *
     * @param customerGuid 客户唯一标识
     */
    public void setCustomerGuid(String customerGuid) {
        this.customerGuid = customerGuid;
    }

    /**
     * 获取购买次数
     *
     * @return pay_cnt - 购买次数
     */
    public Integer getPayCnt() {
        return payCnt;
    }

    /**
     * 设置购买次数
     *
     * @param payCnt 购买次数
     */
    public void setPayCnt(Integer payCnt) {
        this.payCnt = payCnt;
    }

    /**
     * 获取月卡剩余次数
     *
     * @return remain_cnt - 月卡剩余次数
     */
    public Integer getRemainCnt() {
        return remainCnt;
    }

    /**
     * 设置月卡剩余次数
     *
     * @param remainCnt 月卡剩余次数
     */
    public void setRemainCnt(Integer remainCnt) {
        this.remainCnt = remainCnt;
    }

    /**
     * 获取月卡有效截止日期
     *
     * @return expire_time - 月卡有效截止日期
     */
    public Date getExpireTime() {
        return expireTime;
    }

    /**
     * 设置月卡有效截止日期
     *
     * @param expireTime 月卡有效截止日期
     */
    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * 获取电瓶车功率类型
     *
     * @return power - 电瓶车功率类型
     */
    public Integer getPower() {
        return power;
    }

    /**
     * 设置电瓶车功率类型
     *
     * @param power 电瓶车功率类型
     */
    public void setPower(Integer power) {
        this.power = power;
    }

    /**
     * 获取最小功率
     *
     * @return min_power - 最小功率
     */
    public Integer getMinPower() {
        return minPower;
    }

    /**
     * 设置最小功率
     *
     * @param minPower 最小功率
     */
    public void setMinPower(Integer minPower) {
        this.minPower = minPower;
    }

    /**
     * 获取最大功率
     *
     * @return max_power - 最大功率
     */
    public Integer getMaxPower() {
        return maxPower;
    }

    /**
     * 设置最大功率
     *
     * @param maxPower 最大功率
     */
    public void setMaxPower(Integer maxPower) {
        this.maxPower = maxPower;
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
     * 获取方案唯一标识
     *
     * @return scheme_guid - 方案唯一标识
     */
    public String getSchemeGuid() {
        return schemeGuid;
    }

    /**
     * 设置方案唯一标识
     *
     * @param schemeGuid 方案唯一标识
     */
    public void setSchemeGuid(String schemeGuid) {
        this.schemeGuid = schemeGuid;
    }

    public String getProjectGuid() {
        return projectGuid;
    }

    public void setProjectGuid(String projectGuid) {
        this.projectGuid = projectGuid;
    }
}