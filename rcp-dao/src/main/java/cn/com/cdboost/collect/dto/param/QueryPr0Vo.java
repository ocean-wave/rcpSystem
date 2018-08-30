package cn.com.cdboost.collect.dto.param;

import java.util.List;

/**
 * 客户正向有功总报表
 */
public class QueryPr0Vo extends PageQueryVo {
    private String endDate;
    private String startDate;

    private Integer start;
    private List<Long> orgNoList;

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public List<Long> getOrgNoList() {
        return orgNoList;
    }

    public void setOrgNoList(List<Long> orgNoList) {
        this.orgNoList = orgNoList;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }
}
