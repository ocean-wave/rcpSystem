package cn.com.cdboost.collect.dto;

import cn.com.cdboost.collect.model.User;

/**
 * 用户登录后，保存的在session中的信息
 */
public class LoginUser extends User{
    /**
     * 用户所属角色id
     */
    private Long roleId;

    /**
     * 用户登录后的sessionId
     */
    private String sessionId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
