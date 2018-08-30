package cn.com.cdboost.collect.controller;


import cn.com.cdboost.collect.param.DayElectricInfoParam;
import cn.com.cdboost.collect.param.HistoryElectricAmountParam;
import cn.com.cdboost.collect.param.PaymentRecordParam;
import cn.com.cdboost.collect.param.RemainAmount;
import cn.com.cdboost.collect.util.DateUtil;
import cn.com.cdboost.collect.util.TokenUtil;
import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by Administrator on 2018/5/4 0004.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:applicationContext.xml","classpath:spring-mvc.xml"})
@WebAppConfiguration
public class OnOffControllerTest {
    protected MockMvc mockMvc;
    private MockHttpSession session;

    @Autowired
    protected WebApplicationContext wac;

    @Before()  //这个方法在每个方法执行之前都会执行一遍
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();  //初始化MockMvc对象
        session = new MockHttpSession();
    }

    @Test
    public void customerOnOff() {
        String responseString = null;
        try {
            RemainAmount remainAmount=new RemainAmount();
            remainAmount.setToken( TokenUtil.generate("ZT001", DateUtil.pareseToken()));
            remainAmount.setAddrCode("");
            String json= JSON.toJSONString(remainAmount);
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/appapi/queryRemainAmount")    //请求的url,请求的方法
                            .contentType(MediaType.APPLICATION_JSON)  //数据的格式
                            .characterEncoding("UTF-8")
                            .content(json)
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(responseString);
    }
    @Test
    public void customerOnOff1() {
        String responseString = null;
        try {
            PaymentRecordParam remainAmount=new PaymentRecordParam();
            remainAmount.setStartDate("11");
            remainAmount.setToken( TokenUtil.generate("ZT001", DateUtil.pareseToken()));
            String json= JSON.toJSONString(remainAmount);
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/appapi/queryPaymentRecord")    //请求的url,请求的方法
                            .contentType(MediaType.APPLICATION_JSON)  //数据的格式
                            .characterEncoding("UTF-8")
                            .content(json)
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(responseString);
    }
    @Test
    public void customerOnOff12() {
        String responseString = null;
        try {
            HistoryElectricAmountParam remainAmount=new HistoryElectricAmountParam();
            remainAmount.setStartDate("11");
            remainAmount.setToken( TokenUtil.generate("ZT001", DateUtil.pareseToken()));
            String json= JSON.toJSONString(remainAmount);
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/appapi/queryHistoryElectricAmount")    //请求的url,请求的方法
                            .contentType(MediaType.APPLICATION_JSON)  //数据的格式
                            .characterEncoding("UTF-8")
                            .content(json)
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(responseString);
    }
    @Test
    public void customerOnOff123() {
        String responseString = null;
        try {
            DayElectricInfoParam remainAmount=new DayElectricInfoParam();
            remainAmount.setAddrCode("1000010");
            remainAmount.setToken( TokenUtil.generate("ZT001", DateUtil.pareseToken()));
            String json= JSON.toJSONString(remainAmount);
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/appapi/queryDayElectricInfo")    //请求的url,请求的方法
                            .contentType(MediaType.APPLICATION_JSON)  //数据的格式
                            .characterEncoding("UTF-8")
                            .content(json)
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(responseString);
    }
}
