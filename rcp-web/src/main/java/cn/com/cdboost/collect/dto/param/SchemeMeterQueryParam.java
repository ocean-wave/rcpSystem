package cn.com.cdboost.collect.dto.param;

import cn.com.cdboost.collect.vo.QueryListParam;

public class SchemeMeterQueryParam extends QueryListParam{

    /**
     * 方案标识
     */
    private String schemeFlag;
    @Override
    protected String defaultSortName() {
        return null;
    }

    @Override
    protected String defaultSortOrder() {
        return null;
    }

    public String getSchemeFlag() {
        return schemeFlag;
    }

    public void setSchemeFlag(String schemeFlag) {
        this.schemeFlag = schemeFlag;
    }
}
