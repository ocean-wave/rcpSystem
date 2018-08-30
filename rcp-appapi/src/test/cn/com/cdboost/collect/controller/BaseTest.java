package cn.com.cdboost.collect.controller;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * 所有controller单元测试需要继承的基类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml","classpath:spring-mvc.xml"})
@WebAppConfiguration
public class BaseTest {
    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    protected MockHttpSession session;

    /**
     * 这个方法在每个方法执行之前都会执行一遍
     */
    @Before()
    public void setup() {
        //初始化MockMvc对象
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        // 手动注入securityManager
       // org.apache.shiro.mgt.DefaultSecurityManager securityManager = (DefaultSecurityManager) wac.getBean("securityManager");
       // SecurityUtils.setSecurityManager(securityManager);
        session = new MockHttpSession();
        String id = session.getId();
    }
}
