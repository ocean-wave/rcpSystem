package cn.com.cdboost.collect.service;

import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service层数据库写操作方法的单元测试基类，默认所有写操作都会回滚
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-service.xml")
@Rollback
@Transactional(transactionManager = "txManager")
public class BaseServiceWriteTest {

}
