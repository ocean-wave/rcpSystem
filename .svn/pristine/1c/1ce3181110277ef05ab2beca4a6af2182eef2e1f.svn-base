package cn.com.cdboost.collect.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "em_d_charging_account")
public class ChargingAccount implements Serializable {
    /**
     * 标识id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 微信号
     */
    @Column(name = "webchar_no")
    private String webcharNo;

    /**
     * 充值类别 1-购买次数  2-包月充值
     */
    @Column(name = "pay_category")
    private Integer payCategory;

    /**
     * 当日用时长(包月项目有效)
     */
    @Column(name = "day_time")
    private Integer dayTime;

    /**
     * 合计充电次数
     */
    @Column(name = "total_cnt")
    private Integer totalCnt;

    /**
     * 已使用次数
     */
    @Column(name = "use_cnt")
    private Integer useCnt;

    /**
     * 剩余充电次数(包月)
     */
    @Column(name = "remain_cnt")
    private Integer remainCnt;

    /**
     * 开始日期 包月项目有效
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 截止日期 包月项目有效
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

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
     * 获取微信号
     *
     * @return webchar_no - 微信号
     */
    public String getWebcharNo() {
        return webcharNo;
    }

    /**
     * 设置微信号
     *
     * @param webcharNo 微信号
     */
    public void setWebcharNo(String webcharNo) {
        this.webcharNo = webcharNo;
    }

    /**
     * 获取充值类别 1-购买次数  2-包月充值
     *
     * @return pay_category - 充值类别 1-购买次数  2-包月充值
     */
    public Integer getPayCategory() {
        return payCategory;
    }

    /**
     * 设置充值类别 1-购买次数  2-包月充值
     *
     * @param payCategory 充值类别 1-购买次数  2-包月充值
     */
    public void setPayCategory(Integer payCategory) {
        this.payCategory = payCategory;
    }

    /**
     * 获取当日用时长(包月项目有效)
     *
     * @return day_time - 当日用时长(包月项目有效)
     */
    public Integer getDayTime() {
        return dayTime;
    }

    /**
     * 设置当日用时长(包月项目有效)
     *
     * @param dayTime 当日用时长(包月项目有效)
     */
    public void setDayTime(Integer dayTime) {
        this.dayTime = dayTime;
    }

    /**
     * 获取合计充电次数
     *
     * @return total_cnt - 合计充电次数
     */
    public Integer getTotalCnt() {
        return totalCnt;
    }

    /**
     * 设置合计充电次数
     *
     * @param totalCnt 合计充电次数
     */
    public void setTotalCnt(Integer totalCnt) {
        this.totalCnt = totalCnt;
    }

    /**
     * 获取已使用次数
     *
     * @return use_cnt - 已使用次数
     */
    public Integer getUseCnt() {
        return useCnt;
    }

    /**
     * 设置已使用次数
     *
     * @param useCnt 已使用次数
     */
    public void setUseCnt(Integer useCnt) {
        this.useCnt = useCnt;
    }

    /**
     * 获取剩余充电次数(包月)
     *
     * @return remain_cnt - 剩余充电次数(包月)
     */
    public Integer getRemainCnt() {
        return remainCnt;
    }

    /**
     * 设置剩余充电次数(包月)
     *
     * @param remainCnt 剩余充电次数(包月)
     */
    public void setRemainCnt(Integer remainCnt) {
        this.remainCnt = remainCnt;
    }

    /**
     * 获取开始日期 包月项目有效
     *
     * @return start_time - 开始日期 包月项目有效
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 设置开始日期 包月项目有效
     *
     * @param startTime 开始日期 包月项目有效
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取截止日期 包月项目有效
     *
     * @return end_time - 截止日期 包月项目有效
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置截止日期 包月项目有效
     *
     * @param endTime 截止日期 包月项目有效
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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
}