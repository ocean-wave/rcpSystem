package cn.com.cdboost.collect.vo;

import java.util.List;

/**
 * Created by Administrator on 2018/8/28 0028.
 */
public class PageData<T> {

    /**
     * 分页总数
     */
    private Long total;

    /**
     * 分页列表数据
     */
    private List<T> list;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
