package cn.com.cdboost.collect.mock;

import cn.com.cdboost.collect.dto.param.MenuActionParam;
import cn.com.cdboost.collect.dto.param.RoleAddParam;
import cn.com.cdboost.collect.dto.param.RoleEditParam;
import cn.com.cdboost.collect.model.Role;
import cn.com.cdboost.collect.model.RoleRight;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/12/19 0019.
 */
public class RoleMockData {
    @Test
    public void add() {
        RoleAddParam param = new RoleAddParam();
        param.setRoleName("系统管理员");
        param.setDescription("系统管理员权限");
        List<MenuActionParam> list = Lists.newArrayList();
        MenuActionParam actionParam = new MenuActionParam();
        actionParam.setMenuId(1L);
        actionParam.setActionId(1L);
        actionParam.setActionId(2L);

        MenuActionParam actionParam2 = new MenuActionParam();
        actionParam2.setMenuId(2L);
        actionParam2.setActionId(1L);
        actionParam2.setActionId(2L);

        list.add(actionParam);
        list.add(actionParam2);

        param.setMenuActionList(list);

        System.out.println(JSON.toJSONString(param));
    }

    @Test
    public void update() {
        RoleEditParam param = new RoleEditParam();
        param.setRoleId(1);
    }

    @Test
    public void queryAll() {
        Result<List<Role>> result = new Result<>();
        List<Role> list = Lists.newArrayList();
        Role role = new Role();
        role.setId(1);
        role.setRoleName("角色1");
        role.setDescription("角色1描述");
        role.setCreateUserId(1L);
        role.setCreateTime(new Date());
        role.setIsEnabled(1);
        role.setIsSystem(1);

        Role role2 = new Role();
        role2.setId(1);
        role2.setRoleName("角色1");
        role2.setDescription("角色1描述");
        role2.setCreateUserId(1L);
        role2.setCreateTime(new Date());
        role2.setIsEnabled(1);
        role2.setIsSystem(1);

        list.add(role);
        list.add(role2);

        result.setData(list);
        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void queryRolePermissions() {
        Result<List<RoleRight>> result = new Result<>();
        List<RoleRight> list = Lists.newArrayList();
        RoleRight roleRight1 = new RoleRight();
        roleRight1.setRoleId(1L);
        roleRight1.setMenuId(1L);
        roleRight1.setActionId(1L);

        RoleRight roleRight2 = new RoleRight();
        roleRight2.setRoleId(1L);
        roleRight2.setMenuId(1L);
        roleRight2.setActionId(2L);

        RoleRight roleRight3 = new RoleRight();
        roleRight3.setRoleId(1L);
        roleRight3.setMenuId(2L);
        roleRight3.setActionId(1L);

        RoleRight roleRight4 = new RoleRight();
        roleRight4.setRoleId(1L);
        roleRight4.setMenuId(2L);
        roleRight4.setActionId(2L);

        list.add(roleRight1);
        list.add(roleRight2);
        list.add(roleRight3);
        list.add(roleRight4);

        result.setData(list);
        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void saveRolePermissions() {
        List<RoleRight> list = Lists.newArrayList();
        RoleRight roleRight = new RoleRight();
        roleRight.setRoleId(1L);
        roleRight.setMenuId(1L);
        roleRight.setActionId(1L);

        RoleRight roleRight2 = new RoleRight();
        roleRight2.setRoleId(1L);
        roleRight2.setMenuId(1L);
        roleRight2.setActionId(2L);

        RoleRight roleRight3 = new RoleRight();
        roleRight3.setRoleId(1L);
        roleRight3.setMenuId(2L);
        roleRight3.setActionId(1L);

        RoleRight roleRight4 = new RoleRight();
        roleRight4.setRoleId(1L);
        roleRight4.setMenuId(2L);
        roleRight4.setActionId(2L);

        list.add(roleRight);
        list.add(roleRight2);
        list.add(roleRight3);
        list.add(roleRight4);

        System.out.println(JSON.toJSONString(list));
    }
}
