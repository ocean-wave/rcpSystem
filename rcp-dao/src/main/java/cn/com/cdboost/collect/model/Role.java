package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "em_b_role")
public class Role implements Serializable {
    private static final long serialVersionUID = -6091595575435822277L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 角色名
     */
    @Column(name = "role_name")
    private String roleName;

    /**
     * 描述
     */
    private String description;

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
     * 是否有效(0-无效,1-有效)
     */
    @Column(name = "is_enabled")
    private Integer isEnabled;

    /**
     * 是否为角色账户
     */
    @Column(name = "is_system")
    private Integer isSystem;

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
     * 获取角色名
     *
     * @return role_name - 角色名
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * 设置角色名
     *
     * @param roleName 角色名
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * 获取描述
     *
     * @return description - 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     *
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
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
     * 获取是否有效(0-无效,1-有效)
     *
     * @return is_enabled - 是否有效(0-无效,1-有效)
     */
    public Integer getIsEnabled() {
        return isEnabled;
    }

    /**
     * 设置是否有效(0-无效,1-有效)
     *
     * @param isEnabled 是否有效(0-无效,1-有效)
     */
    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }

    /**
     * 获取是否为角色账户
     *
     * @return is_system - 是否为角色账户
     */
    public Integer getIsSystem() {
        return isSystem;
    }

    /**
     * 设置是否为角色账户
     *
     * @param isSystem 是否为角色账户
     */
    public void setIsSystem(Integer isSystem) {
        this.isSystem = isSystem;
    }
}