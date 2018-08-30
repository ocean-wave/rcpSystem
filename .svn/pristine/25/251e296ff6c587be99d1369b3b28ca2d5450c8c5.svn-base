package cn.com.cdboost.collect;

import cn.com.cdboost.collect.model.ChangeMeterOth;
import cn.com.cdboost.collect.service.ChangeMeterOthService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by Administrator on 2017/12/19 0019.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context.xml","classpath:spring-mvc.xml"})
@WebAppConfiguration
public class ServiceTest {
    @Autowired
    private ChangeMeterOthService changeMeterOthService;

    @Test
    public void test() {
        ChangeMeterOth oth = new ChangeMeterOth();
        oth.setDataValue("7777");
        oth.setItemCode("8888");
        oth.setChangeFlag("9");
        changeMeterOthService.insertSelective(oth);
    }

    @Test
    public void update() {
//        ChangeMeterOth oth = new ChangeMeterOth();
//        oth.setId(1);
//        oth.setChangeFlag("xxxxoooooo");
//        oth.setItemCode("9999");
//        oth.setDataValue("200000000");
//        changeMeterOthService.updateChangeMeterOth(oth);
    }

    @Test
    public void addUpdateTransaction() {
        changeMeterOthService.addUpdateTransaction(false);
    }

    @Test
    public void updateSingle() {
        changeMeterOthService.updateSingle();
    }

    @Test
    public void addUpdateTransaction2() {
        // 新增
        ChangeMeterOth oth = new ChangeMeterOth();
        oth.setDataValue("2222");
        oth.setItemCode("5678");
        oth.setChangeFlag("2");
        changeMeterOthService.insertSelective(oth);

        // 查询
        ChangeMeterOth oth1 = changeMeterOthService.selectByPrimaryKey(2);

        // 抛异常
        int i = 10;
        int j = 0;
        int result = i/j;
        System.out.println(result);

        // 更新
        oth1.setChangeFlag("fly");
        oth1.setItemCode("fly001");
        oth1.setDataValue("fly1002");
        changeMeterOthService.updateByPrimaryKey(oth1);
        System.out.println("执行成功");
    }
}
