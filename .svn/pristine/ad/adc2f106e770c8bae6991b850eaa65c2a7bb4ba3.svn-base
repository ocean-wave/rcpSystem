package cn.com.cdboost.collect.dto;

import cn.com.cdboost.collect.model.Org;

import java.util.List;

/**
 * 组织树形结构信息
 */
public class OrgTreeInfo extends Org{
    /**
     * 递归遍历时用
     */
    private Boolean isUse = false;

    /**
     * 下级组织信息
     */
    private List<OrgTreeInfo> children;

    public Boolean getUse() {
        return isUse;
    }

    public void setUse(Boolean use) {
        isUse = use;
    }

    public List<OrgTreeInfo> getChildren() {
        return children;
    }

    public void setChildren(List<OrgTreeInfo> children) {
        this.children = children;
    }
}
