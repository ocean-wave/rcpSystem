package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.dto.param.UserQueryParam;
import cn.com.cdboost.collect.dto.param.UserSaveOrUpdateParam;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 用户相关单元测试
 */
public class UserControllerTest extends BaseTest{

    @Test
    public void saveOrUpdate() {
        UserSaveOrUpdateParam param = new UserSaveOrUpdateParam();
        param.setUserId(4);
        param.setLoginName("zhangwujiaaa");
        param.setUserPassword("123456");
        param.setOrgNo(1L);
        param.setRoleId(1);
        param.setUserName("张无忌aaa");
        param.setUserMail("763541258@qq.com");
        param.setUserMobile("13987654321");
        param.setRemark("备注哈哈hahhaha");

        String responseString = null;   //将相应的数据转换为字符串
        try {
            String jsonStr = JSON.toJSONString(param);
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/user/saveOrUpdate")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_JSON)  //数据的格式
                            .characterEncoding("UTF-8")
                            .content(jsonStr)
                            .session(session)
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
            List<Integer> ids = Lists.newArrayList();
            ids.add(3);
            ids.add(4);
            String jsonStr = JSON.toJSONString(ids);
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/user/delete")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_JSON)  //数据的格式
                            .characterEncoding("UTF-8")
                            .content(jsonStr)
                            .session(session)
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------返回的json = " + responseString);
    }


    @Test
    public void queryList() {
        String responseString = null;   //将相应的数据转换为字符串
        try {
            UserQueryParam param = new UserQueryParam();
            param.setUserName("张三");
            String jsonStr = JSON.toJSONString(param);
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/user/queryList")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_JSON)  //数据的格式
                            .characterEncoding("UTF-8")
                            .content(jsonStr)
                            .session(session)
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------返回的json = " + responseString);
    }

    @Test
    public void updatePassword() {
        String responseString = null;   //将相应的数据转换为字符串
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/user/updatePassword")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)  //数据的格式
                            .characterEncoding("UTF-8")
                            .param("newPassword","987654")
                            .session(session)
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------返回的json = " + responseString);
    }

    @Test
    public void validatePassword() {
        String responseString = null;   //将相应的数据转换为字符串
        try {

            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/user/validatePassword")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)  //数据的格式
                            .characterEncoding("UTF-8")
                            .param("oldPassword","")
                            .session(session)
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------返回的json = " + responseString);
    }
}
