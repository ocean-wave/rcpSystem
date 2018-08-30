package cn.com.cdboost.collect.dto.response;

/**
 * 返回给前端的菜单动作信息
 */
public class MenuActionInfo {
    /**
     * 菜单ID
     */
    private Integer menuId;

    /**
     * 动作ID
     */
    private Integer actionId;

    /**
     * 动作名称
     */
    private String actionName;

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
}
