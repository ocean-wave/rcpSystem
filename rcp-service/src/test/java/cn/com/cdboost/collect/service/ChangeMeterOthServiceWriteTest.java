package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.model.ChangeMeterOth;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ChangeMeterOthServiceWriteTest extends BaseServiceWriteTest{

    @Autowired
    private ChangeMeterOthService changeMeterOthService;

    @Test
    public void add() {
        ChangeMeterOth oth = new ChangeMeterOth();
        oth.setDataValue("555");
        oth.setItemCode("6666");
        oth.setChangeFlag("7");
        int i = changeMeterOthService.insertSelective(oth);
        System.out.println(i);
    }
}
