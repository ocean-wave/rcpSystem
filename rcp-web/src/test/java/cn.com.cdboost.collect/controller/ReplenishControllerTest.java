package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.dto.param.CreateManualRecordParam;
import cn.com.cdboost.collect.dto.param.MakeupDataParam;
import cn.com.cdboost.collect.dto.param.ManualRecordVo;
import cn.com.cdboost.collect.dto.param.QueryPr0Param;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReplenishControllerTest extends BaseTest {
    @Test
    public void queryData() {
        String responseString = null;   //将相应的数据转换为字符串
        MakeupDataParam param = new MakeupDataParam();
        param.setCustomerNo("dc47d15f4f724d3aa5b8904a2a47d212");
        param.setStartDate("2018-05-06");
        param.setEndDate("2018-06-21");
        String jsonStr = JSON.toJSONString(param);
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/replenish/queryData")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_JSON_UTF8)  //数据的格式
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
    public void manualRecordData() {
        String responseString = null;   //将相应的数据转换为字符串
        CreateManualRecordParam param = new CreateManualRecordParam();
        param.setCustomerNo("11");
        List<ManualRecordVo> list = new ArrayList<>();
        ManualRecordVo manualRecordVo = new ManualRecordVo();
        manualRecordVo.setCno("21");
        manualRecordVo.setCollectDate("2018-05-12");
        manualRecordVo.setReadValue(111);
        list.add(manualRecordVo);
        param.setManualRecordVoList(list);
        String jsonStr = JSON.toJSONString(param);
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/replenish/manualRecordData")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_JSON_UTF8)  //数据的格式
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
    public void queryPr0() {
        String responseString = null;   //将相应的数据转换为字符串
        QueryPr0Param param = new QueryPr0Param();
        param.setStartDate("2018-05-04");
        param.setEndDate("2018-06-21");
        param.setPageNumber(1);
        param.setPageSize(20);
        String jsonStr = JSON.toJSONString(param);
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/report/queryPr0")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_JSON_UTF8)  //数据的格式
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
    public void queryPr0Down() {
        String responseString = null;   //将相应的数据转换为字符串
        QueryPr0Param param = new QueryPr0Param();
        param.setStartDate("2018-05-05");
        param.setEndDate("2018-06-22");
        param.setPageNumber(1);
        param.setPageSize(20);
        String jsonStr = JSON.toJSONString(param);
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/report/queryPr0Down")    //请求的url,请求的方法是get
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
    public void queryRechargeRecord() {
        String responseString = null;   //将相应的数据转换为字符串
        QueryPr0Param param = new QueryPr0Param();
        param.setStartDate("2018-05-05");
        param.setEndDate("2018-06-22");
        param.setPageNumber(1);
        param.setPageSize(20);
        String jsonStr = JSON.toJSONString(param);
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/report/queryRechargeRecord")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_JSON_UTF8)  //数据的格式
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
    public void queryRechargeRecordDown() {
        String responseString = null;   //将相应的数据转换为字符串
        QueryPr0Param param = new QueryPr0Param();
        param.setStartDate("2018-05-05");
        param.setEndDate("2018-06-22");
        param.setPageNumber(1);
        param.setPageSize(20);
        String jsonStr = JSON.toJSONString(param);
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/report/queryRechargeRecordDown")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_JSON_UTF8)  //数据的格式
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
}
