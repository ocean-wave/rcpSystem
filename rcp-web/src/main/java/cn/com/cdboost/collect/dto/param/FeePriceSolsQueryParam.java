package cn.com.cdboost.collect.dto.param;

import cn.com.cdboost.collect.vo.QueryListParam;

/**
 * 电价方案查询，输入参数
 */
public class FeePriceSolsQueryParam extends QueryListParam {
    /**
     * 是否启用 1启用，0停用，不传表示查所有
     */
    private Integer isEnabled;

    @Override
    protected String defaultSortName() {
        return null;
    }

    @Override
    protected String defaultSortOrder() {
        return null;
    }

    public Integer getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }
}
