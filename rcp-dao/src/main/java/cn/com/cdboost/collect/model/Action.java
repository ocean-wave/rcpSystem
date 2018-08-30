package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "em_b_action")
public class Action implements Serializable{
    private static final long serialVersionUID = 4954985016733563266L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 动作ID
     */
    @Column(name = "action_id")
    private Long actionId;

    /**
     * 动作名
     */
    @Column(name = "action_name")
    private String actionName;

    /**
     * 动作标识
     */
    @Column(name = "action_flag")
    private Integer actionFlag;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 创建人
     */
    @Column(name = "create_user_id")
    private Long createUserId;

    /**
     * 是否有效
     */
    @Column(name = "is_enabled")
    private Integer isEnabled;

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
     * 获取动作ID
     *
     * @return action_id - 动作ID
     */
    public Long getActionId() {
        return actionId;
    }

    /**
     * 设置动作ID
     *
     * @param actionId 动作ID
     */
    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    /**
     * 获取动作名
     *
     * @return action_name - 动作名
     */
    public String getActionName() {
        return actionName;
    }

    /**
     * 设置动作名
     *
     * @param actionName 动作名
     */
    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    /**
     * 获取动作标识
     *
     * @return action_flag - 动作标识
     */
    public Integer getActionFlag() {
        return actionFlag;
    }

    /**
     * 设置动作标识
     *
     * @param actionFlag 动作标识
     */
    public void setActionFlag(Integer actionFlag) {
        this.actionFlag = actionFlag;
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
     * 获取创建人
     *
     * @return create_user_id - 创建人
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置创建人
     *
     * @param createUserId 创建人
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 获取是否有效
     *
     * @return is_enabled - 是否有效
     */
    public Integer getIsEnabled() {
        return isEnabled;
    }

    /**
     * 设置是否有效
     *
     * @param isEnabled 是否有效
     */
    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }
}