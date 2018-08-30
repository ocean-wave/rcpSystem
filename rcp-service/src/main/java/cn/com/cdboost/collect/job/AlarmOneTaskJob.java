package cn.com.cdboost.collect.job;

import cn.com.cdboost.collect.model.SmsScheme;
import cn.com.cdboost.collect.service.SmsSchemeService;
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
 * 告警级别一定时任务
 */
@Lazy(false)
@Component
@EnableScheduling
@PropertySource("classpath:job-config.properties")
public class AlarmOneTaskJob {
    private static final Logger logger = LoggerFactory.getLogger(AlarmOneTaskJob.class);
    @Value("${job.isOpen}")
    private String isOpen;

    @Autowired
    private SmsSchemeService smsSchemeService;

    @Scheduled(cron = "${job1.schedule}")
    public void alarmOneCheck() {
        if ("0".equals(isOpen)) {
            logger.info("alarmOneCheck开始运行");
            return;
        }
        logger.info("---------AlarmOneTaskJob任务开始----------");
        try {
            // 查询满足告警级别一的数据
            smsSchemeService.renewData1();

            //查询满足发送条件的记录
            List<SmsScheme> smsSchemes = smsSchemeService.queryAlarmOneSchemeInfo();
            if (CollectionUtils.isEmpty(smsSchemes)) {
                return;
            }

            // 发送短信和微信通知
            smsSchemeService.sendSmsWeixin(smsSchemes);
            logger.info("----------AlarmOneTaskJob任务结束-----------");
        } catch (Exception e) {
            logger.error("后端AlarmOneTaskJob告警定时任务执行异常",e);
        }
    }
}
