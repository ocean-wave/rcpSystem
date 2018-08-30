package cn.com.cdboost.collect.dto;

import java.io.Serializable;

/**
 * 手机端登录成功返回DTO
 */
public class LoginSuccessResponse implements Serializable{
    private static final long serialVersionUID = -2729578972087684305L;

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 人员姓名
     */
    private String userName;
    /**
     * 联系电话
     */
    private String phoneNum;
    /**
     * 部门机构
     */
    private String department;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
