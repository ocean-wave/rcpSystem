package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "em_b_menuaction")
public class MenuAction implements Serializable {
    private static final long serialVersionUID = -6544102876498750100L;
    /**
     * 数据标识ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 菜单ID
     */
    @Column(name = "menu_id")
    private Integer menuId;

    /**
     * 动作ID
     */
    @Column(name = "action_id")
    private Integer actionId;

    /**
     * 动作名称
     */
    @Column(name = "action_name")
    private String actionName;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 创建人员
     */
    @Column(name = "create_user_id")
    private Integer createUserId;

    /**
     * 获取数据标识ID
     *
     * @return id - 数据标识ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置数据标识ID
     *
     * @param id 数据标识ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取菜单ID
     *
     * @return menu_id - 菜单ID
     */
    public Integer getMenuId() {
        return menuId;
    }

    /**
     * 设置菜单ID
     *
     * @param menuId 菜单ID
     */
    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    /**
     * 获取动作ID
     *
     * @return action_id - 动作ID
     */
    public Integer getActionId() {
        return actionId;
    }

    /**
     * 设置动作ID
     *
     * @param actionId 动作ID
     */
    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    /**
     * 获取动作名称
     *
     * @return action_name - 动作名称
     */
    public String getActionName() {
        return actionName;
    }

    /**
     * 设置动作名称
     *
     * @param actionName 动作名称
     */
    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取创建人员
     *
     * @return create_user_id - 创建人员
     */
    public Integer getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置创建人员
     *
     * @param createUserId 创建人员
     */
    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }
}