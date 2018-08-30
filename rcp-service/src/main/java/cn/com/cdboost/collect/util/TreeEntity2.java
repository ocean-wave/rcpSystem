package cn.com.cdboost.collect.util;

import java.util.List;

/**
 * 树形数据实体接口
 */
public interface TreeEntity2<E> {
    /**
     * 获取自身id
     * @return
     */
    String getId();

    /**
     * 获取父节点id
     * @return
     */
    String getParentId();

    /**
     * 设置孩子节点列表
     * @param childList
     */
    void setChildList(List<E> childList);

    /**
     * 设置节点是否展开
     * @param isOpen
     */
    void setIsOpen(boolean isOpen);
}
