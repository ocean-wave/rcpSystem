package cn.com.cdboost.collect.dto.response;

import java.util.Date;
import java.util.List;

/**
 * 用户查询返回信息
 */
public class UserQueryInfo {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 移动电话
     */
    private String userMobile;

    /**
     * 邮箱
     */
    private String userMail;

    /**
     * 是否有效
     */
    private Integer isEnabled;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Integer createUserId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 所属组织机构
     */
    private Integer orgNo;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 组织名称
     */
    private String orgName;

    /**
     * 该用户拥有的数据权限列表
     */
    private List<Long> dataOrgList;

    /**
     * 该用户拥有的数据权限组织名称
     */
    private List<String> dataOrgNameList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public Integer getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(Integer orgNo) {
        this.orgNo = orgNo;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public List<Long> getDataOrgList() {
        return dataOrgList;
    }

    public void setDataOrgList(List<Long> dataOrgList) {
        this.dataOrgList = dataOrgList;
    }

    public List<String> getDataOrgNameList() {
        return dataOrgNameList;
    }

    public void setDataOrgNameList(List<String> dataOrgNameList) {
        this.dataOrgNameList = dataOrgNameList;
    }
}
