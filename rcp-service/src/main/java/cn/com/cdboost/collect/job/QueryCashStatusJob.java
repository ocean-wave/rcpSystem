package cn.com.cdboost.collect.job;

import cn.com.cdboost.collect.constant.ChargeAppConstant;
import cn.com.cdboost.collect.model.ChargingWithdrawCash;
import cn.com.cdboost.collect.service.ChargingWithdrawCashService;
import cn.com.cdboost.collect.service.WxChargerPayService;
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
 * 针对提现失败，并且error_code=SYSTEMERROR时，查询提现状态
 */
@Lazy(false)
@Component
@EnableScheduling
@PropertySource("classpath:job-config.properties")
public class QueryCashStatusJob {
    private static final Logger logger = LoggerFactory.getLogger(QueryCashStatusJob.class);

    @Value("${job.isOpen}")
    private String isOpen;

    @Autowired
    private ChargingWithdrawCashService chargingWithdrawCashService;
    @Autowired
    private WxChargerPayService wxChargerPayService;

    @Scheduled(cron = "${job5.schedule}")
    public void checkCashStatus() {
        if ("0".equals(isOpen)) {
            logger.info("checkCashStatus开始运行");
            return;
        }

        logger.info("---------QueryCashStatusJob任务开始----------");
        try {
            List<ChargingWithdrawCash> failFlows = chargingWithdrawCashService.queryCashFailFlows();
            if (CollectionUtils.isEmpty(failFlows)) {
                logger.info("不存在提现处理中的数据，直接返回");
                return;
            }

            for (ChargingWithdrawCash failFlow : failFlows) {
                String partnerTradeNo = failFlow.getPartnerTradeNo();
                Integer type = failFlow.getType();
                if (ChargeAppConstant.WithdrawCashType.WECHAT.getType().equals(type)) {
                    wxChargerPayService.checkCashFailFlows(failFlow.getId(),partnerTradeNo);
                } else {
                    wxChargerPayService.processAlipayWithdrawCashFlows(failFlow.getId(),partnerTradeNo);
                }
            }
            logger.info("---------QueryCashStatusJob任务结束----------");
        } catch (Exception e) {
            logger.error("检查提现流水记录定时任务异常",e);
        }
    }
}
