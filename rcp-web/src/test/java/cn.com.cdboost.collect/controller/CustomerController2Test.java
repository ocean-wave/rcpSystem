package cn.com.cdboost.collect.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Administrator on 2017/12/27 0027.
 */
public class CustomerController2Test extends BaseTest {
    @Test
    public void batchDelete() {
        List<String> customerNos = Lists.newArrayList();
        customerNos.add("ff7b9218-a10d-4d93-9133-cbbf8cfe936d");
        customerNos.add("e77f39d5-d17e-4d60-9c90-b1d3275ceb3f");

        String jsonStr = JSON.toJSONString(customerNos);
        System.out.println(jsonStr);
        String responseString = null;   //将相应的数据转换为字符串
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/customerInfo2/batchDelete")    //请求的url,请求的方法是get
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
