package cn.com.cdboost.collect.dto.param;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.util.List;

/**
 * 角色添加，传入参数
 */
public class RoleAddParam {

    /**
     * 角色名称
     */
    @NotBlank(message = "roleName不能为空")
    private String roleName;

    /**
     * 角色描述信息
     */
    private String description;

    /**
     * 该角色对应的菜单和动作权限列表
     */
    @NotEmpty(message = "权限列表不能为空")
    @Valid
    private List<MenuActionParam> menuActionList;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<MenuActionParam> getMenuActionList() {
        return menuActionList;
    }

    public void setMenuActionList(List<MenuActionParam> menuActionList) {
        this.menuActionList = menuActionList;
    }
}
