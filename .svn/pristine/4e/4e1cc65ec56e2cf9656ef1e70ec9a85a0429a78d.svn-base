package cn.com.cdboost.collect.dto.param;


import java.util.List;

public class RefundQueryListVo extends PageQueryVo {
    /**
     * 客户地址
     */
    private String customerAddr;
    /**
     * 客户电话
     */
    private String customerContact;
    /**
     * 客户名
     */
    private String customerName;
    /**
     * 客户门牌编号
     */
    private String propertyName;
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

    public String getCustomerAddr() {
        return customerAddr;
    }

    public void setCustomerAddr(String customerAddr) {
        this.customerAddr = customerAddr;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
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
