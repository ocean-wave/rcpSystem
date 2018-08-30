package cn.com.cdboost.collect.service;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class DeviceMeterParamServiceWriteTest extends BaseServiceWriteTest{

    @Autowired
    private DeviceMeterParamService deviceMeterParamService;

    @Test
    public void update() {
        List<String> cnos = Lists.newArrayList();
        cnos.add("070000000000000000000000345646");
        cnos.add("080000000000001000000005456321");
        cnos.add("090000000000003000000541553513");
        cnos.add("070000000000000000000001531321");
        cnos.add("080000000000001000000006545613");
        cnos.add("090000000000003000000045465352");
        deviceMeterParamService.batchUpdateCommPointCode2Zero(cnos);
    }
}
