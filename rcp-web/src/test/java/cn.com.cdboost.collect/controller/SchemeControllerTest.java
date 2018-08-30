package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.dto.param.SchemeMeterQueryParam;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SchemeControllerTest extends BaseTest{
    @Test
    public void delete(){
        String responseString = null;   //将相应的数据转换为字符串
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/scheme/delete")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)  //数据的格式
                            .characterEncoding("UTF-8")
                            .param("schemeFlag","db26a078ba284564b2922364aa0b3785")
                            .session(session)       //添加参数
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------返回的json = " + responseString);
    }

    @Test
    public void queryList(){
        String responseString = null;   //将相应的数据转换为字符串
        QuerySchemeParam querySchemeParam = new QuerySchemeParam();
        querySchemeParam.setPageNumber(1);
        querySchemeParam.setPageSize(6);
        String jsonStr = JSON.toJSONString(querySchemeParam);
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/scheme/queryList")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_JSON_UTF8)  //数据的格式
                            .characterEncoding("UTF-8")
                            .content(jsonStr)
                            .session(session)      //添加参数
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------返回的json = " + responseString);
    }
    @Test
    public void queryByCnos(){
        String responseString = null;   //将相应的数据转换为字符串
        String[] cnos = { "070000000000000000000000065536","070000000000000000000000065537"
        };
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/scheme/queryByCnos")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_JSON_UTF8)  //数据的格式
                            .characterEncoding("UTF-8")
                            .param("cnos",cnos)
                            .session(session)      //添加参数
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------返回的json = " + responseString);
    }
    @Test
    public void add(){
        String responseString = null;   //将相应的数据转换为字符串
        AddSchemeParam addSchemeParam = new AddSchemeParam();
        List<AddSchemeNodeParam> meterList = new ArrayList<>();
        AddSchemeNodeParam addSchemeNodeParam1 = new AddSchemeNodeParam();
        AddSchemeNodeParam addSchemeNodeParam2 = new AddSchemeNodeParam();
        addSchemeParam.setSchemeName("接口测试YJB-2");
        addSchemeParam.setSchemeType(0);
        addSchemeParam.setStartDate("2018-06-14");
        addSchemeParam.setStartTime("09:57:07");
        addSchemeParam.setEndDate("2018-06-14");
        addSchemeParam.setEndTime("10:57:12");
        addSchemeParam.setRemark("接口测试-2");
        addSchemeNodeParam1.setCustomerNo("110302034");
        addSchemeNodeParam1.setDeviceNo("0055345345369");
        addSchemeNodeParam1.setCno("070000000000000000055345345369");
        addSchemeNodeParam1.setMeterUserNo("89754");
        addSchemeNodeParam2.setCustomerNo("110302054");
        addSchemeNodeParam2.setDeviceNo("0055345345357");
        addSchemeNodeParam2.setCno("070000000000000000055345345357");
        addSchemeNodeParam2.setMeterUserNo("89753");
        meterList.add(addSchemeNodeParam1);
        meterList.add(addSchemeNodeParam2);
        addSchemeParam.setMeterList(meterList);
        String jsonStr = JSON.toJSONString(addSchemeParam);
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/scheme/add")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_JSON_UTF8)  //数据的格式
                            .characterEncoding("UTF-8")
                            .content(jsonStr)
                            .session(session)      //添加参数
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------返回的json = " + responseString);
    }
    @Test
    public void edit(){
        String responseString = null;   //将相应的数据转换为字符串
        EditSchemeParam editSchemeParam = new EditSchemeParam();
        List<EditSchemeNodeParam> meterList = new ArrayList<>();
        EditSchemeNodeParam editSchemeNodeParam1 = new EditSchemeNodeParam();
        EditSchemeNodeParam editSchemeNodeParam2 = new EditSchemeNodeParam();
        editSchemeParam.setSchemeFlag("3916b196cbeb46ffbb93ce70f073640a");
        editSchemeParam.setSchemeName("接口测试YJBNEW");
        editSchemeParam.setSchemeType(1);
        editSchemeParam.setStartDate("2018-06-12");
        editSchemeParam.setStartTime("15:57:07");
        editSchemeParam.setEndDate("2018-06-12");
        editSchemeParam.setEndTime("15:57:12");
        editSchemeParam.setRemark("接口测试YHG");
        editSchemeNodeParam1.setFlag(1);
        editSchemeNodeParam1.setCustomerNo("110302021");
        editSchemeNodeParam1.setDeviceNo("0055345345327");
        editSchemeNodeParam1.setCno("070000000000000000055345345327");
        editSchemeNodeParam1.setMeterUserNo("89756");
        editSchemeNodeParam2.setFlag(0);
        editSchemeNodeParam2.setId(3);
        editSchemeNodeParam2.setCustomerNo("110302024");
        editSchemeNodeParam2.setDeviceNo("0055345345347");
        editSchemeNodeParam2.setCno("070000000000000000055345345347");
        editSchemeNodeParam2.setMeterUserNo("89758");
        meterList.add(editSchemeNodeParam1);
        meterList.add(editSchemeNodeParam2);
        editSchemeParam.setMeterList(meterList);
        String jsonStr = JSON.toJSONString(editSchemeParam);
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/scheme/edit")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_JSON_UTF8)  //数据的格式
                            .characterEncoding("UTF-8")
                            .content(jsonStr)
                            .session(session)      //添加参数
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------返回的json = " + responseString);
    }

    @Test
    public void queryMeterList(){
        String responseString = null;   //将相应的数据转换为字符串
        SchemeMeterQueryParam querySchemeParam = new SchemeMeterQueryParam();
        querySchemeParam.setSchemeFlag("1207456b4eb0464b81893dbdcb4e7952");
        querySchemeParam.setPageNumber(1);
        querySchemeParam.setPageSize(6);
        String jsonStr = JSON.toJSONString(querySchemeParam);
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/scheme/queryMeterList")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_JSON_UTF8)  //数据的格式
                            .characterEncoding("UTF-8")
                            .content(jsonStr)
                            .session(session)      //添加参数
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------返回的json = " + responseString);
    }

    @Test
    public void downloadMeterList(){
        String responseString = null;   //将相应的数据转换为字符串
        SchemeMeterQueryParam querySchemeParam = new SchemeMeterQueryParam();
        querySchemeParam.setSchemeFlag("01de8a52042a49c49d212d95f5f9baf3");
        querySchemeParam.setPageNumber(1);
        querySchemeParam.setPageSize(6);
        String jsonStr = JSON.toJSONString(querySchemeParam);
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/scheme/downloadMeterList")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_JSON)  //数据的格式
                            .characterEncoding("UTF-8")
                            .content(jsonStr)
                            .session(session)      //添加参数
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------返回的json = " + responseString);
    }
    @Test
    public void deviceCount(){
        String responseString = null;   //将相应的数据转换为字符串
        String cno = "070000000000000000032312312313";
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/scheme/deviceCount")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_JSON_UTF8)  //数据的格式
                            .characterEncoding("UTF-8")
                            .param("cno",cno)
                            .session(session)      //添加参数
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------返回的json = " + responseString);
    }
    @Test
    public void deviceUserList(){
        String responseString = null;   //将相应的数据转换为字符串
        String deviceNo = "65536";
        Integer pageNumber = 1;
        Integer pageSize =10;
        //实例化查询对象
        DeviceUserListVo deviceUserListVo = new DeviceUserListVo();
        //设置查询参数
        deviceUserListVo.setPageNumber(pageNumber);
        deviceUserListVo.setPageSize(pageSize);
        deviceUserListVo.setDeviceNo(deviceNo);
        String jsonStr = JSON.toJSONString(deviceUserListVo);
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/scheme/deviceUserList")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_JSON_UTF8)  //数据的格式
                            .characterEncoding("UTF-8")
                            .content(jsonStr)
                            .session(session)      //添加参数
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------返回的json = " + responseString);
    }
}
