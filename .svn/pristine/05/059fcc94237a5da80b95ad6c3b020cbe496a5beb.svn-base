package cn.com.cdboost.collect.dto.param;

import java.util.List;

/**
 * 查询客户退费记录
 */
public class QueryRefundRecordVo extends PageQueryVo {
    /**
     * 查询结束时间
     */
    private String endDate;
    /**
     * 查询开始时间
     */
    private String startDate;
    /**
     * 当前页
     */
    private Integer pageIndex;
    /**
     * 组织
     */
    private List<Long> orgNos;

    public List<Long> getOrgNos() {
        return orgNos;
    }

    public void setOrgNos(List<Long> orgNos) {
        this.orgNos = orgNos;
    }

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

    public Integer getPageIndex() {
        return (this.getPageNumber()-1)*this.getPageSize();
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }
}
