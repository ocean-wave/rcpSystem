package cn.com.cdboost.collect.job;

import cn.com.cdboost.collect.model.Sms;
import cn.com.cdboost.collect.service.AliyunSmsService;
import cn.com.cdboost.collect.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 主动查询短信状态定时任务
 */
@Lazy(false)
@Component
@EnableScheduling
@PropertySource("classpath:job-config.properties")
public class QuerySmsStatusJob {
    private static final Logger logger = LoggerFactory.getLogger(QuerySmsStatusJob.class);

    @Value("${job.isOpen}")
    private String isOpen;

    @Autowired
    private SmsService smsService;
    @Autowired
    private AliyunSmsService aliyunSmsService;

    @Scheduled(cron = "${job4.schedule}")
    public void checkSmsStatus() {
        if ("0".equals(isOpen)) {
            logger.info("checkSmsStatus开始运行");
            return;
        }
        logger.info("---------QuerySmsStatusJob任务开始----------");

        try {
            List<Sms> list = smsService.queryNotReceivedNoticeSmsList();
            if (CollectionUtils.isEmpty(list)) {
                return;
            }

            aliyunSmsService.checkSendStatus(list);
            logger.info("----------QuerySmsStatusJob任务结束-----------");
        } catch (Exception e) {
            logger.error("短信发送状态查询定时任务异常",e);
        }
    }
}
