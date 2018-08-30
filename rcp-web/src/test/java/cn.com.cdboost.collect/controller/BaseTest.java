package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.dto.LoginUser;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.model.RoleRight;
import cn.com.cdboost.collect.model.User;
import cn.com.cdboost.collect.service.RoleService;
import cn.com.cdboost.collect.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

/**
 * 所有controller单元测试需要继承的基类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context.xml","classpath:spring-mvc.xml"})
@WebAppConfiguration
public class BaseTest {
    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    protected MockHttpSession session;

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    /**
     * 这个方法在每个方法执行之前都会执行一遍
     */
    @Before()
    public void setup() {
        //初始化MockMvc对象
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        // 手动注入securityManager
        org.apache.shiro.mgt.DefaultSecurityManager securityManager = (DefaultSecurityManager) wac.getBean("securityManager");
        SecurityUtils.setSecurityManager(securityManager);

        // 登录session相关设置
        User currentUser = userService.getUserByLoginName("admin");
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(currentUser,loginUser);

        // 查询用户角色
        Long roleId = userService.getRoleIdByUserID(currentUser.getId());
        loginUser.setRoleId(roleId);

        // 查询用户角色权限信息
        List<RoleRight> rolePermissions = roleService.getRolePermissionByRoleId(roleId);

        session = new MockHttpSession();
        session.setAttribute(GlobalConstant.SESSION_AUTHS, rolePermissions);
        session.setAttribute(GlobalConstant.CURRENT_LOGIN_USER,loginUser);
    }
}
