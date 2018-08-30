package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.dto.param.FuzzyQueryTreeParam;
import cn.com.cdboost.collect.dto.param.MainSubTreeQueryParam;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 */
public class TreeControllerTest extends BaseTest{
    @Test
    public void buildingTree() {
        String responseString = null;   //将相应的数据转换为字符串
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/tree/buildingTree")    //请求的url,请求的方法是get
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
    public void queryMainSubTree() {
        String responseString = null;   //将相应的数据转换为字符串
        MainSubTreeQueryParam param = new MainSubTreeQueryParam();
        param.setDeviceType("08");
        param.setIsImportant(0);
//        param.setNodeId("070000000000000000032312313123");
//        param.setOnlineStatus(0);
        String jsonStr = JSON.toJSONString(param);
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/tree/queryMainSubTree")    //请求的url,请求的方法是get
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
    public void fuzzyQueryTree() {
        FuzzyQueryTreeParam treeParam = new FuzzyQueryTreeParam();
        treeParam.setDeviceNo("6602");
        treeParam.setDeviceType("07");
//        treeParam.setOnlineStatus(1);
        treeParam.setIsImportant(0);
        String jsonStr = JSON.toJSONString(treeParam);
        System.out.println("jsonStr=" + jsonStr);
        String responseString = null;   //将相应的数据转换为字符串
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/tree/fuzzyQueryTree")    //请求的url,请求的方法是get
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
    public void queryOrgTreeByOrgNo() {
        String responseString = null;   //将相应的数据转换为字符串
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/org/queryOrgTreeByOrgNo")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)  //数据的格式
                            .characterEncoding("UTF-8")
                            .param("orgNo","1003")
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------返回的json = " + responseString);
    }

    @Test
    public void testQueryAllKey() {
        String responseString = null;   //将相应的数据转换为字符串
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/tree/queryAllKey")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)  //数据的格式
                            .characterEncoding("UTF-8")
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------返回的json = " + responseString);
    }
}
