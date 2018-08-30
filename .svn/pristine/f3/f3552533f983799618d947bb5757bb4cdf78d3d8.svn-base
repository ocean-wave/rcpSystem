package cn.com.cdboost.collect.controller;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 菜单动作单元测试
 */
public class MenuActionControllerTest extends BaseTest {

    @Test
    public void queryAllMenuAction() {
        String responseString = null;   //将相应的数据转换为字符串
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/menuAction/queryAllMenuAction")    //请求的url,请求的方法是get
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
                    MockMvcRequestBuilders.post("/menuAction/queryRolePermissions")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)  //数据的格式
                            .characterEncoding("UTF-8")
                            .session(session)
                            .param("menuId","100011")
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------返回的json = " + responseString);
    }
}
