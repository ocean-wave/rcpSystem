package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.dto.param.ElectConsumptionParam;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author wt
 * @desc
 * @create in  2018/6/11
 **/
public class CustomerInfoControllerTest extends BaseTest {


    @Test
    public void queryByDictCode() {
        String responsString = null;   //将相应的数据转换为字符串
        ElectConsumptionParam electConsumptionParam=new ElectConsumptionParam();
        electConsumptionParam.setCustomerAddr("");
        electConsumptionParam.setCustomerName("");
        electConsumptionParam.setCustomerContact("");
        electConsumptionParam.setPropertyName("");
        electConsumptionParam.setStartDate("2018-05-26");
        electConsumptionParam.setEndDate("2018-06-13");
        electConsumptionParam.setPageSize(20);
        electConsumptionParam.setPageNumber(1);
        String jsonString = com.alibaba.fastjson.JSON.toJSONString(electConsumptionParam);
        try {
              mockMvc.perform(
                    MockMvcRequestBuilders.post("/customerInfo/electConsumption")    //请求的url,请求的方法是get
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