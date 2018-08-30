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
 * 告警级别三定时任务
 */
@Lazy(false)
@Component
@EnableScheduling
@PropertySource("classpath:job-config.properties")
public class AlarmThreeTaskJob {
    private static final Logger logger = LoggerFactory.getLogger(AlarmThreeTaskJob.class);
    @Value("${job3.time}")
    private String jobTime;
    @Value("${job3.num}")
    private String jobNum;
    @Value("${job.isOpen}")
    private String isOpen;

    @Autowired
    private SmsSchemeService smsSchemeService;

    @Scheduled(cron = "${job3.schedule}")
    public void alarmThreeCheck() {
        if ("0".equals(isOpen)) {
            logger.info("alarmThreeCheck开始运行");
            return;
        }
        logger.info("---------alarmThreeCheck任务开始----------");
        try {
            //更新消息方案数据
            smsSchemeService.renewData3();

            //查询满足发送条件的记录
            List<SmsScheme> smsSchemes = smsSchemeService.queryAlarmThreeSchemeInfo(Integer.valueOf(jobNum), Integer.valueOf(jobTime));
            if (CollectionUtils.isEmpty(smsSchemes)) {
                return;
            }

            // 发送短信和微信通知
            smsSchemeService.sendSmsWeixin(smsSchemes);
            logger.info("----------AlarmThreeTaskJob任务结束-----------");
        } catch (Exception e) {
            logger.error("后端AlarmThreeTaskJob告警定时任务执行异常",e);
        }
    }



}
