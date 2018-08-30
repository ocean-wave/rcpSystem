package cn.com.cdboost.collect.cache;

import java.io.Serializable;

/**
 * 组织缓存信息
 */
public class OrgCacheVo implements Serializable{
    /**
     * 主键
     */
    private Integer id;

    /**
     * 机构编号
     */
    private Long orgNo;

    /**
     * 机构名称
     */
    private String orgName;

    /**
     * 上级机构编号
     */
    private Long pOrgNo;

    /**
     * 级别编码
     */
    private String levelCode;

    /**
     * 级次
     */
    private Integer level;

    /**
     * 排序
     */
    private Integer sortNo;

    /**
     * 是否系统参数
     */
    private Integer isSys;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(Long orgNo) {
        this.orgNo = orgNo;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Long getpOrgNo() {
        return pOrgNo;
    }

    public void setpOrgNo(Long pOrgNo) {
        this.pOrgNo = pOrgNo;
    }

    public String getLevelCode() {
        return levelCode;
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public Integer getIsSys() {
        return isSys;
    }

    public void setIsSys(Integer isSys) {
        this.isSys = isSys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrgCacheVo)) return false;

        OrgCacheVo that = (OrgCacheVo) o;

        return orgNo.equals(that.orgNo);
    }

    @Override
    public int hashCode() {
        return orgNo.hashCode();
    }
}
