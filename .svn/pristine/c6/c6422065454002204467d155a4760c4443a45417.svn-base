package cn.com.cdboost.collect.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析树形数据工具类
 */
public class TreeParser4 {
    /**
     * 解析树形数据
     * @param topId
     * @param entityList
     * @param <E>
     * @return
     */
    public static <E extends TreeEntity4<E>> List<E> getTreeList(Long topId, List<E> entityList) {
        List<E> resultList=new ArrayList<>();

        //获取顶层元素集合
        Long parentId;
        for (E entity : entityList) {
            parentId=entity.getParentId();
            if(parentId==null||topId.equals(parentId)){
                resultList.add(entity);
            }
        }

        //获取每个顶层元素的子数据集合
        for (E entity : resultList) {
            List<E> subList = getSubList(entity.getId(), entityList);
            entity.setChildList(subList);
        }

        return resultList;
    }

    /**
     * 获取子数据集合
     * @param id
     * @param entityList
     * @param <E>
     * @return
     */
    private static <E extends TreeEntity4<E>>  List<E> getSubList(Long id, List<E> entityList) {
        List<E> childList=new ArrayList<>();
        Long parentId;

        //子集的直接子对象
        for (E entity : entityList) {
            parentId=entity.getParentId();
            if(id.equals(parentId)){
                childList.add(entity);
            }
        }

        //子集的间接子对象
        for (E entity : childList) {
            List<E> subList = getSubList(entity.getId(), entityList);
            entity.setChildList(subList);
        }

        //递归退出条件
        if(childList.size()==0){
            return null;
        }

        return childList;
    }
}
