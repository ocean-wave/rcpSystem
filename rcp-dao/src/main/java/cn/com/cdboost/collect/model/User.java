package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "em_b_user")
public class User implements Serializable{
    private static final long serialVersionUID = 8011032694475364780L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户名称
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 登录名
     */
    @Column(name = "login_name")
    private String loginName;

    /**
     * 用户密码
     */
    @Column(name = "user_password")
    private String userPassword;

    /**
     * 移动电话
     */
    @Column(name = "user_mobile")
    private String userMobile;

    /**
     * 邮箱
     */
    @Column(name = "user_mail")
    private String userMail;

    /**
     * 是否有效
     */
    @Column(name = "is_enabled")
    private Integer isEnabled;

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
     * 备注
     */
    private String remark;

    /**
     * 所属组织机构
     */
    @Column(name = "org_no")
    private Long orgNo;

    /**
     * 是否为系统账户
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
     * 获取用户名称
     *
     * @return user_name - 用户名称
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户名称
     *
     * @param userName 用户名称
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取登录名
     *
     * @return login_name - 登录名
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * 设置登录名
     *
     * @param loginName 登录名
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * 获取用户密码
     *
     * @return user_password - 用户密码
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * 设置用户密码
     *
     * @param userPassword 用户密码
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * 获取移动电话
     *
     * @return user_mobile - 移动电话
     */
    public String getUserMobile() {
        return userMobile;
    }

    /**
     * 设置移动电话
     *
     * @param userMobile 移动电话
     */
    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    /**
     * 获取邮箱
     *
     * @return user_mail - 邮箱
     */
    public String getUserMail() {
        return userMail;
    }

    /**
     * 设置邮箱
     *
     * @param userMail 邮箱
     */
    public void setUserMail(String userMail) {
        this.userMail = userMail;
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
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取所属组织机构
     *
     * @return org_no - 所属组织机构
     */
    public Long getOrgNo() {
        return orgNo;
    }

    /**
     * 设置所属组织机构
     *
     * @param orgNo 所属组织机构
     */
    public void setOrgNo(Long orgNo) {
        this.orgNo = orgNo;
    }

    /**
     * 获取是否为系统账户
     *
     * @return is_system - 是否为系统账户
     */
    public Integer getIsSystem() {
        return isSystem;
    }

    /**
     * 设置是否为系统账户
     *
     * @param isSystem 是否为系统账户
     */
    public void setIsSystem(Integer isSystem) {
        this.isSystem = isSystem;
    }
}