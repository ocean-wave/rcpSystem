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
 * 告警级别二定时任务
 */
@Lazy(false)
@Component
@EnableScheduling
@PropertySource("classpath:job-config.properties")
public class AlarmTwoTaskJob {
    private static final Logger logger = LoggerFactory.getLogger(AlarmTwoTaskJob.class);

    @Value("${job.isOpen}")
    private String isOpen;

    @Autowired
    private SmsSchemeService smsSchemeService;

    @Scheduled(cron = "${job2.schedule}")
    public void alarmTwoCheck() {
        if ("0".equals(isOpen)) {
            logger.info("alarmTwoCheck开始运行");
            return;
        }
        logger.info("---------AlarmTwoTaskJob任务开始----------");
        try {
            //更新消息方案数据
            smsSchemeService.renewData2();

            //查询满足发送条件的记录
            List<SmsScheme> smsSchemes = smsSchemeService.queryAlarmTwoSchemeInfo();
            if (CollectionUtils.isEmpty(smsSchemes)) {
                return;
            }

            // 发送短信和微信通知
            smsSchemeService.sendSmsWeixin(smsSchemes);
            logger.info("----------AlarmTwoTaskJob任务结束-----------");
        } catch (Exception e) {
            logger.error("后端AlarmTwoTaskJob告警定时任务执行异常",e);
        }
    }



}
