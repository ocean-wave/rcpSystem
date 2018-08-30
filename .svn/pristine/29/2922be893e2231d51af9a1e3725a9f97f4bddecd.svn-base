package cn.com.cdboost.collect.controller;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author wt
 * @desc
 * @create in  2018/7/23
 **/
public class DeviceInfoControllerTest extends BaseTest{

    @Test
    public void queryJzqStateDetail() {
        try {
            mockMvc.perform(
                    MockMvcRequestBuilders.post("/deviceInfo/queryJzqStateDetail")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)  //数据的格式
                            .characterEncoding("UTF-8")
                            .session(session)
                            .param("deviceCno", "1")
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}