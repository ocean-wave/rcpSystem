package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "em_b_roleright")
public class RoleRight implements Serializable {
    private static final long serialVersionUID = -1789434617322862556L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 菜单ID
     */
    @Column(name = "menu_id")
    private Long menuId;

    /**
     * 动作ID（0-所有）
     */
    @Column(name = "action_id")
    private Long actionId;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取角色ID
     *
     * @return role_id - 角色ID
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * 设置角色ID
     *
     * @param roleId 角色ID
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取菜单ID
     *
     * @return menu_id - 菜单ID
     */
    public Long getMenuId() {
        return menuId;
    }

    /**
     * 设置菜单ID
     *
     * @param menuId 菜单ID
     */
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    /**
     * 获取动作ID（0-所有）
     *
     * @return action_id - 动作ID（0-所有）
     */
    public Long getActionId() {
        return actionId;
    }

    /**
     * 设置动作ID（0-所有）
     *
     * @param actionId 动作ID（0-所有）
     */
    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }
}