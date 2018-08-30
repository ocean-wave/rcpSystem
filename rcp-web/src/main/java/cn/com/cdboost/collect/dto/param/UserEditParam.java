package cn.com.cdboost.collect.dto.param;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 系统用户修改，传入参数
 */
public class UserEditParam {
    /**
     * 用户id
     */
    @NotNull(message = "userId不能为空")
    private Integer userId;

    /**
     * 用户名称
     */
    @NotBlank(message = "userName不能是空字符串")
    private String userName;

    /**
     * 所属组织机构编号
     */
    @NotNull(message = "orgNo不能为null")
    private Long orgNo;

    /**
     * 所属角色id
     */
    @NotNull(message = "roleId不能为null")
    private Integer roleId;

    /**
     * 邮箱
     */
    private String userMail;

    /**
     * 移动电话
     */
    @NotBlank(message = "userMobile不能是空字符串")
    private String userMobile;

    /**
     * 备注
     */
    private String remark;

    /**
     * 用户所拥有的数据权限列表
     */
    private List<Long> dataOrgList;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(Long orgNo) {
        this.orgNo = orgNo;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Long> getDataOrgList() {
        return dataOrgList;
    }

    public void setDataOrgList(List<Long> dataOrgList) {
        this.dataOrgList = dataOrgList;
    }
}
