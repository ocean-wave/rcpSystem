package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.dto.ElectDetailParamDto;
import cn.com.cdboost.collect.dto.param.ElectConsumptionParam;
import cn.com.cdboost.collect.dto.param.SingleBaseQueryParam;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 综合查询单元测试
 */
public class ComprehensiveQueryControllerTest extends BaseTest {

    @Test
    public void queryChangeMeterList() {
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
    public void queryChangeMeterList1() {

        String responseString = null;   //将相应的数据转换为字符串
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/comprehensive/electDetail")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)  //数据的格式
                            .characterEncoding("UTF-8")
                            .param("customerNo","dc47d15f4f724d3aa5b8904a2a47d212")
                            .session(session)
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------返回的json = " + responseString);
    }
}
