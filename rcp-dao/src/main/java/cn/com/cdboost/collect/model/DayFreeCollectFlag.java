package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "em_d_dayfreecollectflag")
public class DayFreeCollectFlag implements Serializable {
    private static final long serialVersionUID = 145785925252247800L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 设备唯一标识(生成规则:设备类型+补0(不足30位)+设备编号）
     */
    private String cno;

    /**
     * 抄表日期
     */
    @Column(name = "collect_date")
    private Date collectDate;

    /**
     * 冻结数据的抄收项目 0 标识全部抄完 255标识全部未抄 抄收成功一项&1
     */
    @Column(name = "collect_item")
    private Integer collectItem;

    /**
     * 冻结数据Guid
     */
    @Column(name = "collect_guid")
    private String collectGuid;

    /**
     * 更新数据时间
     */
    @Column(name = "update_time")
    private Date updateTime;

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
     * 获取冻结数据的抄收项目 0 标识全部抄完 255标识全部未抄 抄收成功一项&1
     *
     * @return collect_item - 冻结数据的抄收项目 0 标识全部抄完 255标识全部未抄 抄收成功一项&1
     */
    public Integer getCollectItem() {
        return collectItem;
    }

    /**
     * 设置冻结数据的抄收项目 0 标识全部抄完 255标识全部未抄 抄收成功一项&1
     *
     * @param collectItem 冻结数据的抄收项目 0 标识全部抄完 255标识全部未抄 抄收成功一项&1
     */
    public void setCollectItem(Integer collectItem) {
        this.collectItem = collectItem;
    }

    /**
     * 获取冻结数据Guid
     *
     * @return collect_guid - 冻结数据Guid
     */
    public String getCollectGuid() {
        return collectGuid;
    }

    /**
     * 设置冻结数据Guid
     *
     * @param collectGuid 冻结数据Guid
     */
    public void setCollectGuid(String collectGuid) {
        this.collectGuid = collectGuid;
    }

    /**
     * 获取更新数据时间
     *
     * @return update_time - 更新数据时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新数据时间
     *
     * @param updateTime 更新数据时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}