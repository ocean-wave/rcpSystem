package cn.com.cdboost.collect.dto.param;

import javax.validation.constraints.NotNull;

/**
 * @author wt
 * @desc
 * @create in  2018/3/12
 **/
public class CustomElecdataQueryParam extends  CustomElecListQueryParam{

    @NotNull(message = "查询类型不能为空")
    private String sortOrder;
    @NotNull(message = "查询类型不能为空")
    private Integer pageSize;

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
