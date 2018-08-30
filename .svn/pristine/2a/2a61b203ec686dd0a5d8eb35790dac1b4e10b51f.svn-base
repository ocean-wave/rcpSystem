package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.dto.param.ElectConsumptionParam;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author wt
 * @desc
 * @create in  2018/7/16
 **/
public class BigDataControllerTest extends BaseTest {

    @Test
    public void queryBaseInformation() {

        try {
             mockMvc.perform(
                    MockMvcRequestBuilders.post("/bigData/queryBaseInformation")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_JSON)  //数据的格式
                            .characterEncoding("UTF-8")
                            .session(session)
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void queryElectTopList() {

        ElectConsumptionParam param = new ElectConsumptionParam();
        param.setStartDate("2018-05-01");
        param.setEndDate("2018-06-15");
        param.setPageNumber(1);
        param.setPageSize(20);
        String jsonStr =JSON.toJSONString(param);
        String responseString = null;   //将相应的数据转换为字符串
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/comprehensive/downelectDetailList")    //请求的url,请求的方法是get
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
    public void queryCategoryInformation() {

        ElectConsumptionParam param = new ElectConsumptionParam();
        param.setStartDate("2018-05-01");
        param.setEndDate("2018-06-15");
        param.setPageNumber(1);
        param.setPageSize(20);
        String jsonStr =JSON.toJSONString(param);
        String responseString = null;   //将相应的数据转换为字符串
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/comprehensive/downelectDetailList")    //请求的url,请求的方法是get
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
    public void queryCurrentMonth() {

        ElectConsumptionParam param = new ElectConsumptionParam();
        param.setStartDate("2018-05-01");
        param.setEndDate("2018-06-15");
        param.setPageNumber(1);
        param.setPageSize(20);
        String jsonStr =JSON.toJSONString(param);
        String responseString = null;   //将相应的数据转换为字符串
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/comprehensive/downelectDetailList")    //请求的url,请求的方法是get
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
    public void queryCurrentYear() {

        ElectConsumptionParam param = new ElectConsumptionParam();
        param.setStartDate("2018-05-01");
        param.setEndDate("2018-06-15");
        param.setPageNumber(1);
        param.setPageSize(20);
        String jsonStr =JSON.toJSONString(param);
        String responseString = null;   //将相应的数据转换为字符串
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/comprehensive/downelectDetailList")    //请求的url,请求的方法是get
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