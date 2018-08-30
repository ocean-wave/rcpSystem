package cn.com.cdboost.collect.dto.chargerApp.vo;

import cn.com.cdboost.collect.dto.param.PageQueryVo;

public class HistoryVo extends PageQueryVo {

    /**
     * 微信用户唯一标识
     */
    private String customerGuid;

    /**
     * 分页下标
     */
    private Integer pageIndex;

    @Override
    public Integer getPageIndex() {
        return (this.getPageNumber()-1)*this.getPageSize();
    }

    @Override
    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getCustomerGuid() {
        return customerGuid;
    }

    public void setCustomerGuid(String customerGuid) {
        this.customerGuid = customerGuid;
    }
}
