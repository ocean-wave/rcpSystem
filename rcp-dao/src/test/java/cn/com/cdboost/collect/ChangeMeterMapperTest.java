package cn.com.cdboost.collect;

import cn.com.cdboost.collect.dao.ChangeMeterMapper;
import cn.com.cdboost.collect.dto.ChangeMeterInfo;
import cn.com.cdboost.collect.dto.param.ChangeMeterListQueryVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by Administrator on 2018/1/26 0026.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-mybatis.xml")
public class ChangeMeterMapperTest {

//    @Autowired
//    private ChangeMeterMapper changeMeterMapper;
//
//    @Test
//    public void aaa() {
//        ChangeMeterListQueryVo queryVo = new ChangeMeterListQueryVo();
//        queryVo.setUserId("1");
//        queryVo.setPageSize(20);
//        queryVo.setPageNumber(1);
//        queryVo.setChangeDateStart("2018-01-25");
//        queryVo.setChangeDateEnd("2018-01-26");
//        queryVo.setDeviceType("07");
//        List<ChangeMeterInfo> changeMeterInfos = changeMeterMapper.queryChangeMeters(queryVo);
//        System.out.println(changeMeterInfos.size());
//    }
}
