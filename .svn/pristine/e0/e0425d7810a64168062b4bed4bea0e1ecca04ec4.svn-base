package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.model.ChangeMeterOth;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ChangeMeterOthServiceReadTest extends BaseServiceReadTest {

    @Autowired
    private ChangeMeterOthService changeMeterOthService;

    @Test
    public void query() {
        ChangeMeterOth param = new ChangeMeterOth();
        param.setItemCode("111");
        List<ChangeMeterOth> select = changeMeterOthService.select(param);
        System.out.println(select.size());
    }
}
