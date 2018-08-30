package cn.com.cdboost.collect.dto.response;

import cn.com.cdboost.collect.util.TreeEntity;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * 返回给前端的菜单树
 */
public class MenuTreeInfo implements TreeEntity<MenuTreeInfo> {
    /**
     * 菜单id
     */
    private Long menuId;

    /**
     * 父菜单id
     */
    private Long parentMenuId;

    /**
     * 菜单标题
     */
    private String menuTitle;

    /**
     * 菜单图标
     */
    private String menuIcon;

    /**
     * 菜单URL
     */
    private String menuUrl;

    /**
     * 孩子节点信息
     */
    private List<MenuTreeInfo> children;

    @Override
    @JSONField(serialize=false)
    public Long getId() {
        return menuId;
    }

    @Override
    @JSONField(serialize=false)
    public Long getParentId() {
        return parentMenuId;
    }

    @Override
    public void setChildList(List<MenuTreeInfo> childList) {
        this.children = childList;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(Long parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public List<MenuTreeInfo> getChildren() {
        return children;
    }

    public void setChildren(List<MenuTreeInfo> children) {
        this.children = children;
    }
}
