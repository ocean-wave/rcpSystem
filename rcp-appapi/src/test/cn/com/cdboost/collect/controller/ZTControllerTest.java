package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.param.*;
import cn.com.cdboost.collect.util.DateUtil;
import cn.com.cdboost.collect.util.TokenUtil;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author wt
 * @desc
 * @create in  2018/7/27
 **/
public class ZTControllerTest extends BaseTest{

    @Test
    public void pushOnOffMessage() {
    }

    @Test
    public void pushArrearageMessage() {
    }

    @Test
    public void queryRemainAmount() {
        String responseString = null;
        try {
            RemainAmount remainAmount=new RemainAmount();
            remainAmount.setToken( TokenUtil.generate("ZT001", DateUtil.pareseToken()));
            remainAmount.setAddrCode("100012");
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
    public void queryPaymentRecord() {
        String responseString = null;
        try {
            PaymentRecordParam historyElectricAmountParam=new PaymentRecordParam();
            historyElectricAmountParam.setToken( TokenUtil.generate("ZT001", DateUtil.pareseToken()));
            historyElectricAmountParam.setAddrCode("110");
            String json= JSON.toJSONString(historyElectricAmountParam);
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
    public void queryDayElectricInfo() {
        String responseString = null;
        try {
            DayElectricInfoParam historyElectricAmountParam=new DayElectricInfoParam();
            historyElectricAmountParam.setToken( TokenUtil.generate("ZT001", DateUtil.pareseToken()));
            historyElectricAmountParam.setAddrCode("110");
            String json= JSON.toJSONString(historyElectricAmountParam);
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

    @Test
    public void queryHistoryElectricAmount() {
        String responseString = null;
        try {
            HistoryElectricAmountParam historyElectricAmountParam=new HistoryElectricAmountParam();
            historyElectricAmountParam.setToken( TokenUtil.generate("ZT001", DateUtil.pareseToken()));
            historyElectricAmountParam.setAddrCode("110");
            historyElectricAmountParam.setStartDate("2018-01");
            historyElectricAmountParam.setEndDate("2018-07");
            historyElectricAmountParam.setDataFlag("2");
            String json= JSON.toJSONString(historyElectricAmountParam);
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
    public void customerOnOff() {
        String responseString = null;
        try {
            CustomerOnOffParam historyElectricAmountParam=new CustomerOnOffParam();
            historyElectricAmountParam.setToken( TokenUtil.generate("ZT001", DateUtil.pareseToken()));
            historyElectricAmountParam.setAddrCode("100014");
            historyElectricAmountParam.setDeviceType("07");
            historyElectricAmountParam.setOnOff(1);
            String json= JSON.toJSONString(historyElectricAmountParam);
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/appapi/customerOnOff")    //请求的url,请求的方法
                            .contentType(MediaType.APPLICATION_JSON)  //数据的格式
                            .characterEncoding("UTF-8")
                            .session(session)
                            .content(json)
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(responseString);
    }
}