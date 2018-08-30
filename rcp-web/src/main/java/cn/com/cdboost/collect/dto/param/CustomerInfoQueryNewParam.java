package cn.com.cdboost.collect.dto.param;

import javax.validation.constraints.NotNull;

/**
 * 客户档案分页查询参数
 */
public class CustomerInfoQueryNewParam extends CustomerInfoQueryParam{
    @NotNull(message = "isSearchChil不能为空")
    private Integer isSearchChild;
    private String importGuid;

    public Integer getIsSearchChild() {
        return isSearchChild;
    }

    public void setIsSearchChild(Integer isSearchChild) {
        this.isSearchChild = isSearchChild;
    }

    public String getImportGuid() {
        return importGuid;
    }

    public void setImportGuid(String importGuid) {
        this.importGuid = importGuid;
    }
}
