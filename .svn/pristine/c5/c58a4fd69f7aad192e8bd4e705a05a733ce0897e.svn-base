package cn.com.cdboost.collect.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 测试任务
 */
@Component
@PropertySource("classpath:job-config.properties")
public class TaskJob {
    private static final Logger logger = LoggerFactory.getLogger(TaskJob.class);

    @Value("${job.isOpen}")
    private String isOpen;

//    @Scheduled(cron = "${job.schedule}")
    public void job1() {
        if ("0".equals(isOpen)) {
            return;
        }
        System.out.println("我是job任务");
        logger.info("----------我是job1任务-----------");
    }
}
