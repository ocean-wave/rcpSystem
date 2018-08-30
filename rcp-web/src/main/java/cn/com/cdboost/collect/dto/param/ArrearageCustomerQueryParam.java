package cn.com.cdboost.collect.dto.param;

import cn.com.cdboost.collect.vo.QueryListParam;

/**
 * 首页欠费用户查询参数
 */
public class ArrearageCustomerQueryParam extends QueryListParam{
    @Override
    protected String defaultSortName() {
        return null;
    }

    @Override
    protected String defaultSortOrder() {
        return null;
    }
}
