package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.dto.param.MenuActionParam;
import cn.com.cdboost.collect.dto.param.RoleAddParam;
import cn.com.cdboost.collect.dto.param.RoleEditParam;
import cn.com.cdboost.collect.model.RoleRight;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 角色相关单元测试
 */
public class RoleControllerTest extends BaseTest{

    @Test
    public void save() {
        RoleAddParam param = new RoleAddParam();
        param.setRoleName("fly测试系统2");
        param.setDescription("fly测试系统权限2");
        List<MenuActionParam> list = Lists.newArrayList();
        MenuActionParam actionParam = new MenuActionParam();
        actionParam.setMenuId(1L);
        actionParam.setActionId(1L);

        MenuActionParam actionParam3 = new MenuActionParam();
        actionParam3.setMenuId(1L);
        actionParam3.setActionId(2L);

        MenuActionParam actionParam2 = new MenuActionParam();
        actionParam2.setMenuId(2L);
        actionParam2.setActionId(2L);

        MenuActionParam actionParam4 = new MenuActionParam();
        actionParam4.setMenuId(2L);
        actionParam4.setActionId(1L);

        list.add(actionParam);
        list.add(actionParam2);
        list.add(actionParam3);
        list.add(actionParam4);

        param.setMenuActionList(list);
        String jsonStr = JSON.toJSONString(param);

        String responseString = null;   //将相应的数据转换为字符串
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/role/save")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_JSON)  //数据的格式
                            .characterEncoding("UTF-8")
                            .session(session)
                            .content(jsonStr)
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------返回的json = " + responseString);
    }

    @Test
    public void delete() {
        String responseString = null;   //将相应的数据转换为字符串
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/role/delete")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)  //数据的格式
                            .characterEncoding("UTF-8")
                            .session(session)
                            .param("roleId","5")
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------返回的json = " + responseString);
    }

    @Test
    public void update() {
        RoleEditParam param = new RoleEditParam();
        param.setRoleId(4);
        param.setRoleName("fly测试hahaha");
        param.setDescription("fly测试系统hahahh");
        List<MenuActionParam> list = Lists.newArrayList();
        MenuActionParam actionParam = new MenuActionParam();
        actionParam.setMenuId(1L);
        actionParam.setActionId(1L);

        MenuActionParam actionParam3 = new MenuActionParam();
        actionParam3.setMenuId(1L);
        actionParam3.setActionId(2L);

        MenuActionParam actionParam2 = new MenuActionParam();
        actionParam2.setMenuId(2L);
        actionParam2.setActionId(2L);

        MenuActionParam actionParam4 = new MenuActionParam();
        actionParam4.setMenuId(2L);
        actionParam4.setActionId(3L);

        list.add(actionParam);
        list.add(actionParam2);
        list.add(actionParam3);
        list.add(actionParam4);

        param.setMenuActionList(list);
        String jsonStr = JSON.toJSONString(param);

        String responseString = null;   //将相应的数据转换为字符串
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/role/update")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_JSON)  //数据的格式
                            .characterEncoding("UTF-8")
                            .session(session)
                            .content(jsonStr)
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------返回的json = " + responseString);
    }

    @Test
    public void queryAll() {
        String responseString = null;   //将相应的数据转换为字符串
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/role/queryAll")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)  //数据的格式
                            .characterEncoding("UTF-8")
                            .session(session)
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------返回的json = " + responseString);
    }

    @Test
    public void queryRolePermissions() {
        String responseString = null;   //将相应的数据转换为字符串
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/role/queryRolePermissions")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)  //数据的格式
                            .characterEncoding("UTF-8")
                            .session(session)
                            .param("roleId","4")
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------返回的json = " + responseString);
    }

    @Test
    public void saveRolePermissions() {
        RoleRight roleRight1 = new RoleRight();
        roleRight1.setRoleId(5L);
        roleRight1.setMenuId(1L);
        roleRight1.setActionId(1L);

        RoleRight roleRight2 = new RoleRight();
        roleRight2.setRoleId(5L);
        roleRight2.setMenuId(1L);
        roleRight2.setActionId(2L);

        RoleRight roleRight3 = new RoleRight();
        roleRight3.setRoleId(5L);
        roleRight3.setMenuId(2L);
        roleRight3.setActionId(2L);

        RoleRight roleRight4 = new RoleRight();
        roleRight4.setRoleId(5L);
        roleRight4.setMenuId(2L);
        roleRight4.setActionId(3L);

        List<RoleRight> list = Lists.newArrayList();
        list.add(roleRight1);
        list.add(roleRight2);
        list.add(roleRight3);
        list.add(roleRight4);

        String jsonStr = JSON.toJSONString(list);

        String responseString = null;   //将相应的数据转换为字符串
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/role/saveRolePermissions")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_JSON)  //数据的格式
                            .characterEncoding("UTF-8")
                            .session(session)
                            .content(jsonStr)
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------返回的json = " + responseString);
    }

}
