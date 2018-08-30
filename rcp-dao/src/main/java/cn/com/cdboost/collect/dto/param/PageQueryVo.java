package cn.com.cdboost.collect.dto.param;

/**
 * 分页查询vo
 */
public class PageQueryVo {


    private Integer pageIndex;
    private Integer pageEndIndex;
    /**
     * 每页显示数量
     */
    private Integer pageSize;

    /**
     * 页号
     */
    private Integer pageNumber;
    private Integer pageEndNum;

    public Integer getPageEndNum() {
        return pageEndNum;
    }

    public void setPageEndNum(Integer pageEndNum) {
        this.pageEndNum = pageEndNum;
    }
    /**
     * 分页查询满足条件的总数量
     */
    private Long total;
    /**
     * 分页查询满足条件的总数量,这个和total的含义一样，主要是存储过程返回有时候是total，有时候是rowCount
     */
    private Long rowCount;

    /**
     *  排序字段
     */
    private String sortName;

    /**
     * asc或desc
     */
    private String sortOrder;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageEndIndex() {
        return pageEndIndex;
    }

    public void setPageEndIndex(Integer pageEndIndex) {
        this.pageEndIndex = pageEndIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getRowCount() {
        return rowCount;
    }

    public void setRowCount(Long rowCount) {
        this.rowCount = rowCount;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}
