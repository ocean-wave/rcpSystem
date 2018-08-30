package cn.com.cdboost.collect.dto.response;

import cn.com.cdboost.collect.vo.Result;

/**
 * 总表树结构返回
 */
public class MainSubResult<T> extends Result<T> {
    /**
     * 返回对应数据权限下的设备总数
     */
    private Integer total;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
