package cn.com.cdboost.collect.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "em_d_query_scheme")
public class QueryScheme implements Serializable {

    private static final long serialVersionUID = -1840924174791692612L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 方案名称
     */
    @Column(name = "scheme_name")
    private String schemeName;

    /**
     * 电表数量
     */
    @Column(name = "user_count")
    private Integer userCount;

    /**
     * 方案标识
     */
    @Column(name = "scheme_flag")
    private String schemeFlag;

    /**
     * 方案类型 1-查询方案 2-通断方案
     */
    @Column(name = "scheme_type")
    private Integer schemeType;

    /**
     * 通执行日期
     */
    @JSONField(format = "yyyy-MM-dd")
    @Column(name = "on_date")
    private Date onDate;

    /**
     * 通执行时间
     */
    @JSONField(format = "HH:mm:ss")
    @Column(name = "on_time")
    private Date onTime;

    /**
     * 断执行日期
     */
    @JSONField(format = "yyyy-MM-dd")
    @Column(name = "off_date")
    private Date offDate;

    /**
     * 断执行时间
     */
    @JSONField(format = "HH:mm:ss")
    @Column(name = "off_time")
    private Date offTime;

    /**
     * 是否周期执行 0-非周期执行 1标识周期执行
     */
    @Column(name = "is_cycle_run")
    private Integer isCycleRun;

    /**
     * 创建人员
     */
    @Column(name = "create_user_id")
    private Integer createUserId;

    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 方案备注
     */
    @Column(name = "scheme_remark")
    private String schemeRemark;

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
     * 获取方案名称
     *
     * @return scheme_name - 方案名称
     */
    public String getSchemeName() {
        return schemeName;
    }

    /**
     * 设置方案名称
     *
     * @param schemeName 方案名称
     */
    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    /**
     * 获取电表数量
     *
     * @return user_count - 电表数量
     */
    public Integer getUserCount() {
        return userCount;
    }

    /**
     * 设置电表数量
     *
     * @param userCount 电表数量
     */
    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    /**
     * 获取方案标识
     *
     * @return scheme_flag - 方案标识
     */
    public String getSchemeFlag() {
        return schemeFlag;
    }

    /**
     * 设置方案标识
     *
     * @param schemeFlag 方案标识
     */
    public void setSchemeFlag(String schemeFlag) {
        this.schemeFlag = schemeFlag;
    }

    /**
     * 获取方案类型 1-查询方案 2-通断方案
     *
     * @return scheme_type - 方案类型 1-查询方案 2-通断方案
     */
    public Integer getSchemeType() {
        return schemeType;
    }

    /**
     * 设置方案类型 1-查询方案 2-通断方案
     *
     * @param schemeType 方案类型 1-查询方案 2-通断方案
     */
    public void setSchemeType(Integer schemeType) {
        this.schemeType = schemeType;
    }

    /**
     * 获取通执行日期
     *
     * @return on_date - 通执行日期
     */
    public Date getOnDate() {
        return onDate;
    }

    /**
     * 设置通执行日期
     *
     * @param onDate 通执行日期
     */
    public void setOnDate(Date onDate) {
        this.onDate = onDate;
    }

    /**
     * 获取通执行时间
     *
     * @return on_time - 通执行时间
     */
    public Date getOnTime() {
        return onTime;
    }

    /**
     * 设置通执行时间
     *
     * @param onTime 通执行时间
     */
    public void setOnTime(Date onTime) {
        this.onTime = onTime;
    }

    /**
     * 获取断执行日期
     *
     * @return off_date - 断执行日期
     */
    public Date getOffDate() {
        return offDate;
    }

    /**
     * 设置断执行日期
     *
     * @param offDate 断执行日期
     */
    public void setOffDate(Date offDate) {
        this.offDate = offDate;
    }

    /**
     * 获取断执行时间
     *
     * @return off_time - 断执行时间
     */
    public Date getOffTime() {
        return offTime;
    }

    /**
     * 设置断执行时间
     *
     * @param offTime 断执行时间
     */
    public void setOffTime(Date offTime) {
        this.offTime = offTime;
    }

    /**
     * 获取是否周期执行 0-非周期执行 1标识周期执行
     *
     * @return is_cycle_run - 是否周期执行 0-非周期执行 1标识周期执行
     */
    public Integer getIsCycleRun() {
        return isCycleRun;
    }

    /**
     * 设置是否周期执行 0-非周期执行 1标识周期执行
     *
     * @param isCycleRun 是否周期执行 0-非周期执行 1标识周期执行
     */
    public void setIsCycleRun(Integer isCycleRun) {
        this.isCycleRun = isCycleRun;
    }

    /**
     * 获取创建人员
     *
     * @return create_user_id - 创建人员
     */
    public Integer getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置创建人员
     *
     * @param createUserId 创建人员
     */
    public void setCreateUserId(Integer createUserId) {
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
     * 获取方案备注
     *
     * @return scheme_remark - 方案备注
     */
    public String getSchemeRemark() {
        return schemeRemark;
    }

    /**
     * 设置方案备注
     *
     * @param schemeRemark 方案备注
     */
    public void setSchemeRemark(String schemeRemark) {
        this.schemeRemark = schemeRemark;
    }
}