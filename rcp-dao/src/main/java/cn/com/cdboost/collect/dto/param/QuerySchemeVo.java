package cn.com.cdboost.collect.dto.param;

import java.util.List;

/**
 * 方案查询vo
 */
public class QuerySchemeVo extends PageQueryVo {
    /**
     * 方案类型
     */
    private String schemeType;
    /**
     * 方案名称
     */
    private String schemeName;
    /**
     * 描述
     */
    private String remark;
    /**
     * 页码
     */
    private Integer start;

    private List<Long> orgNos;

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public List<Long> getOrgNos() {
        return orgNos;
    }

    public void setOrgNos(List<Long> orgNos) {
        this.orgNos = orgNos;
    }
}
