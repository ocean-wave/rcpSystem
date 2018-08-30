package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "em_b_org")
public class Org implements Serializable {
    private static final long serialVersionUID = 3137229932381775606L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 机构编号
     */
    @Column(name = "org_no")
    private Long orgNo;

    /**
     * 机构名称
     */
    @Column(name = "org_name")
    private String orgName;

    /**
     * 上级机构编号
     */
    @Column(name = "p_org_no")
    private Long pOrgNo;

    /**
     * 首字母拼音
     */
    @Column(name = "first_pinyin")
    private String firstPinyin;

    /**
     * 全拼
     */
    @Column(name = "full_pinyin")
    private String fullPinyin;

    /**
     * 级别编码
     */
    @Column(name = "level_code")
    private String levelCode;

    /**
     * 级次
     */
    private Integer level;

    /**
     * 排序
     */
    @Column(name = "sort_no")
    private Integer sortNo;

    /**
     * 是否有效
     */
    @Column(name = "is_enabled")
    private Integer isEnabled;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 创建人
     */
    @Column(name = "create_user_id")
    private Long createUserId;
    /**
     * 是否系统参数
     */
    @Column(name = "is_sys")
    private Integer isSys;

    public Integer getIsSys() {
        return isSys;
    }

    public void setIsSys(Integer isSys) {
        this.isSys = isSys;
    }

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
     * 获取机构编号
     *
     * @return org_no - 机构编号
     */
    public Long getOrgNo() {
        return orgNo;
    }

    /**
     * 设置机构编号
     *
     * @param orgNo 机构编号
     */
    public void setOrgNo(Long orgNo) {
        this.orgNo = orgNo;
    }

    /**
     * 获取机构名称
     *
     * @return org_name - 机构名称
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * 设置机构名称
     *
     * @param orgName 机构名称
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * 获取上级机构编号
     *
     * @return p_org_no - 上级机构编号
     */
    public Long getpOrgNo() {
        return pOrgNo;
    }

    /**
     * 设置上级机构编号
     *
     * @param pOrgNo 上级机构编号
     */
    public void setpOrgNo(Long pOrgNo) {
        this.pOrgNo = pOrgNo;
    }

    /**
     * 获取首字母拼音
     *
     * @return first_pinyin - 首字母拼音
     */
    public String getFirstPinyin() {
        return firstPinyin;
    }

    /**
     * 设置首字母拼音
     *
     * @param firstPinyin 首字母拼音
     */
    public void setFirstPinyin(String firstPinyin) {
        this.firstPinyin = firstPinyin;
    }

    /**
     * 获取全拼
     *
     * @return full_pinyin - 全拼
     */
    public String getFullPinyin() {
        return fullPinyin;
    }

    /**
     * 设置全拼
     *
     * @param fullPinyin 全拼
     */
    public void setFullPinyin(String fullPinyin) {
        this.fullPinyin = fullPinyin;
    }

    /**
     * 获取级别编码
     *
     * @return level_code - 级别编码
     */
    public String getLevelCode() {
        return levelCode;
    }

    /**
     * 设置级别编码
     *
     * @param levelCode 级别编码
     */
    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }

    /**
     * 获取级次
     *
     * @return level - 级次
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * 设置级次
     *
     * @param level 级次
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 获取排序
     *
     * @return sort_no - 排序
     */
    public Integer getSortNo() {
        return sortNo;
    }

    /**
     * 设置排序
     *
     * @param sortNo 排序
     */
    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    /**
     * 获取是否有效
     *
     * @return is_enabled - 是否有效
     */
    public Integer getIsEnabled() {
        return isEnabled;
    }

    /**
     * 设置是否有效
     *
     * @param isEnabled 是否有效
     */
    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
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
     * 获取创建人
     *
     * @return create_user_id - 创建人
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置创建人
     *
     * @param createUserId 创建人
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }
}