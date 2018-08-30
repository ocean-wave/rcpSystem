package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.dto.ChangeMeterInfo;
import cn.com.cdboost.collect.dto.param.ChangeMeterListQueryVo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ChangeMeterServiceReadTest extends BaseServiceReadTest {
    @Autowired
    private ChangeMeterService changeMeterService;

    @Test
    public void aaa() {
        ChangeMeterListQueryVo queryVo = new ChangeMeterListQueryVo();
        queryVo.setUserId("1");
        queryVo.setPageSize(20);
        queryVo.setPageNumber(1);
        queryVo.setChangeDateStart("2018-01-25");
        queryVo.setChangeDateEnd("2018-01-26");
        queryVo.setDeviceType("07");
        List<ChangeMeterInfo> changeMeterInfos = changeMeterService.queryChangeMeters(queryVo);
        System.out.println(changeMeterInfos.size());
    }
}
