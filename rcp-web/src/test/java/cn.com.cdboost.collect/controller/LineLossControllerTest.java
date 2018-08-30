package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.dto.param.QueryLineLostListDto;
import cn.com.cdboost.collect.dto.param.QueryResidentialListDto;
import cn.com.cdboost.collect.util.UuidUtil;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author wt
 * @desc
 * @create in  2018/7/18
 **/
public class LineLossControllerTest extends  BaseTest{

    @Test
    public void queryResidentialList() {
        String responseString = null;   //将相应的数据转换为字符串
        QueryResidentialListDto queryResidentialListDto=new QueryResidentialListDto();
        queryResidentialListDto.setStartDate("2018-7-18");
        queryResidentialListDto.setEndDate("2018-7-18");
        queryResidentialListDto.setResidential("");
        queryResidentialListDto.setQueryGuid(UuidUtil.getUuid());
        queryResidentialListDto.setPageNumber(1);
        queryResidentialListDto.setPageSize(20);
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/lineLoss/queryResidentialList")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_JSON)  //数据的格式
                            .characterEncoding("UTF-8")
                            .content(JSON.toJSONString(queryResidentialListDto))    //添加参数
                            .session(session)
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------返回的json = " + responseString);
    }

    @Test
    public void queryLineLostList() {
        String responseString = null;   //将相应的数据转换为字符串
        QueryLineLostListDto queryLineLostListDto=new QueryLineLostListDto();
        queryLineLostListDto.setLossType("0");
        queryLineLostListDto.setPlatform("");
        queryLineLostListDto.setQueryGuid(UuidUtil.getUuid());
        queryLineLostListDto.setPageNumber(1);
        queryLineLostListDto.setPageSize(20);
        try {
            responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/lineLoss/queryLineLostList")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_JSON)  //数据的格式
                            .characterEncoding("UTF-8")
                            .content(JSON.toJSONString(queryLineLostListDto))    //添加参数
                            .session(session)
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------返回的json = " + responseString);
    }

    @Test
    public void queryLineLostListDownload() {
    }

    @Test
    public void queryDayLost() {
    }

    @Test
    public void queryDayLostDownload() {
    }

    @Test
    public void querySupplyElectDetail() {
    }

    @Test
    public void querySupplyElectDetailDownload() {
    }

    @Test
    public void queryConfessElectDetail() {
    }

    @Test
    public void queryConfessElectDetailDownload() {
    }

    @Test
    public void queryDayCollectSucceedRate() {
    }

    @Test
    public void queryDayCollectSucceedRateDownload() {
    }
}