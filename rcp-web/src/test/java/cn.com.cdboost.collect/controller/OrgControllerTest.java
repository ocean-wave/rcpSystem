package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.dto.param.RealTimeNewParam;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 组织相关单元测试
 */
public class OrgControllerTest extends BaseTest{

    @Test
    public void queryOrgTreeInfoq() {
        String responseString = null;   //将相应的数据转换为字符串
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/org/queryOrgTreeInfo")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)  //数据的格式
                            .characterEncoding("UTF-8")
                            .param("orgNo","1002")
                            .session(session)
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------返回的json = " + responseString);
    }
    @Test
    public void queryOrgTreeInfo() {
        String responseString = null;   //将相应的数据转换为字符串
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/org/delete")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)  //数据的格式
                            .characterEncoding("UTF-8")
                            .param("orgNo","1002")
                            .session(session)
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------返回的json = " + responseString);
    }
    @Test
    public void queryOrgTreeInfo2() {
        String responseString = null;   //将相应的数据转换为字符串
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/org/edit")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)  //数据的格式
                            .characterEncoding("UTF-8")
                            .param("orgNo","1002")
                            .param("pOrgNo","1000")
                            .param("orgName","开发")
                            .session(session)
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------返回的json = " + responseString);
    }

    @Test
    public void queryOrgTreeInfoAdd1() {
        String responseString = null;   //将相应的数据转换为字符串
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/org/add")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)  //数据的格式
                            .characterEncoding("UTF-8")
                            .session(session)
                    .param("orgName","王涛")
                    .param("pOrgNo","")
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------返回的json = " + responseString);
    }

    @Test
    public void queryOrgTreeInfoAdd() {
        String responseString = null;   //将相应的数据转换为字符串
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/org/add")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)  //数据的格式
                            .characterEncoding("UTF-8")
                            .session(session)
                    .param("orgName","王涛")
                    .param("pOrgNo","")
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------返回的json = " + responseString);
    }
    @Test
    public void queryOrgTreeInfoAdd12() {
        String level ="/1001/1002";
        String replace = level.replace("/" + "1001", "|");
        replace.substring(replace.indexOf("|")+1, replace.length());
        if (replace.trim().length() > 0) {
           // nodeData.setHasChild(true);
        }
    }
    @Test
    public void query() {
        String responseString = null;   //将相应的数据转换为字符串
        TestParam testParam=new TestParam();
        testParam.setNane("111");
        String jsonStr = JSON.toJSONString(testParam);
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/realTimeData/queryListNew")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_JSON)  //数据的格式
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
    public void query11() {
        String responseString = null;   //将相应的数据转换为字符串
        RealTimeNewParam testParam=new RealTimeNewParam();
        testParam.setCustomerNoList(Arrays.asList(new String[]{"7e50ef20ad124549b89d1032b1a9c5fa","c734f9511d294ba7ac71e60698c6aae9"}));
        testParam.setDeviceType("07");
        testParam.setGuid("2b23bd36-9133-ee18-ad85-77648fdb4edf");
        testParam.setIsSearchChild(0);
        testParam.setPageNumber(1);
        testParam.setPageSize(20);
        String jsonStr = JSON.toJSONString(testParam);
        System.out.println(jsonStr);
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/realTimeData/queryListNew")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_JSON)  //数据的格式
                            .content(jsonStr)
                            .session(session)
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------返回的json = " + responseString);
    }
}
