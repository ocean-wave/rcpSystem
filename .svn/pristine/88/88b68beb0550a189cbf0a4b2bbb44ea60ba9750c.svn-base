package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.dto.param.RealTimeNewParam;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 实时数据查询接口单元测试
 */
public class RealTimeDataControllerTest extends BaseTest{

    @Test
    public void query() {
        String responseString = null;   //将相应的数据转换为字符串
        RealTimeNewParam testParam=new RealTimeNewParam();
        testParam.setCustomerNoList(Arrays.asList(new String[]{"1","2"}));
        testParam.setDeviceType("07");
        testParam.setGuid("1");
        testParam.setIsSearchChild(0);
        testParam.setPageNumber(1);
        testParam.setPageSize(20);
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
}
