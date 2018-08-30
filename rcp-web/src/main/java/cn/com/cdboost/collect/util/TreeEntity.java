package cn.com.cdboost.collect.util;

import java.util.List;

/**
 * 树形数据实体接口
 */
public interface TreeEntity<E> {
    /**
     * 获取自身id
     * @return
     */
    Long getId();

    /**
     * 获取父节点id
     * @return
     */
    Long getParentId();

    /**
     * 设置孩子节点列表
     * @param childList
     */
    void setChildList(List<E> childList);
}
