package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.dto.param.CustomerInfoParam;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author wt
 * @desc
 * @create in  2018/5/15
 **/
public class MeterRelationControllerTest extends BaseTest{

    @Test
    public void queryMeterByNodeId() {

        String responseString = null;   //将相应的数据转换为字符串
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/meterrelation/queryMeterByDeviceNo")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)  //数据的格式
                            .characterEncoding("UTF-8")
                            .session(session)
                    .param("deviceType","04")
                    .param("deviceNo","518208424")
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------返回的json = " + responseString);

    }
    @Test
    public void queryCustomerInfo() {

        String responseString = null;   //将相应的数据转换为字符串
        String string = null;   //将相应的数据转换为字符串
        CustomerInfoParam customerInfoParam=new CustomerInfoParam();
//        customerInfoParam.setCustomerAddr("大师傅");
//        customerInfoParam.setCustomerContact("13254762154");
        customerInfoParam.setCustomerName("fly");
//        customerInfoParam.setPropertyName("2332");
        customerInfoParam.setIsImportant(1);
        string= JSON.toJSONString(customerInfoParam);
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/meterrelation/queryCustomerInfo")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_JSON)  //数据的格式
                            .characterEncoding("UTF-8")
                            .session(session)
                            .content(string)
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------返回的json = " + responseString);

    }
}