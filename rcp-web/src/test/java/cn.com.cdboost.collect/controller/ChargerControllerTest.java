package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.dto.DayLineLossDto;
import cn.com.cdboost.collect.dto.TotalLineLossDto;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author wt
 * @desc
 * @create in  2018/8/10
 **/
public class ChargerControllerTest extends BaseTest{

    @Test
    public void queryChangeMeterList() {
        TotalLineLossDto totalLineLossDto = new TotalLineLossDto();
        totalLineLossDto.setStartDate("2018-05-01");
        totalLineLossDto.setEndDate("2018-10-15");
        totalLineLossDto.setSortName("lossRate");
        totalLineLossDto.setSortOrder("desc");
        totalLineLossDto.setPageNumber(1);
        totalLineLossDto.setPageSize(20);
        String jsonStr = JSON.toJSONString(totalLineLossDto);
        String responseString = null;   //将相应的数据转换为字符串
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/charger/totalLineLoss")    //请求的url,请求的方法是get
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
        DayLineLossDto totalLineLossDto = new DayLineLossDto();
        totalLineLossDto.setDate("2018-8-10");
        totalLineLossDto.setDeviceNo("2003500978");
        totalLineLossDto.setDeviceType("07");
        String jsonStr = JSON.toJSONString(totalLineLossDto);
        String responseString = null;   //将相应的数据转换为字符串
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/charger/dayLineLoss")    //请求的url,请求的方法是get
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