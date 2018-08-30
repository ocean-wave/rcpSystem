package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.dto.param.ElectConsumptionParam;
import cn.com.cdboost.collect.dto.param.EnergyEfficiencyQueryVo;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EnergyEfficiencyControllerTest extends BaseTest{
    @Test
    public void queryVillageList() {
        String responsString = null;   //将相应的数据转换为字符串
        EnergyEfficiencyQueryVo queryVo=new EnergyEfficiencyQueryVo();

        queryVo.setBeginDate("2018-05-26");
        queryVo.setEndDate("2018-08-13");
        queryVo.setPageSize(20);
        queryVo.setPageNumber(1);
        String jsonString = com.alibaba.fastjson.JSON.toJSONString(queryVo);
        try {
            mockMvc.perform(
                    MockMvcRequestBuilders.post("/efficiency/queryVillageList")    //请求的url,请求的方法是get
                            .contentType(MediaType.APPLICATION_JSON)  //数据的格式
                            .characterEncoding("UTF-8")
                            .session(session)
                            .content(jsonString)//添加参数
            ).andExpect(status().isOk())    //返回的状态是200
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
