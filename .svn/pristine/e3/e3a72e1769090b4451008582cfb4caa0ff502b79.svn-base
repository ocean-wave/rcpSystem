package cn.com.cdboost.collect.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;

import javax.annotation.Resource;


public class MQProducerImpl{
    private final static Logger logger = LoggerFactory.getLogger(MQProducerImpl.class);
    @Resource
    private AmqpTemplate amqpTemplate;


    //公共入队方法
    public void sendDataToQueue(String queueKey, Object object) {
        try {
            amqpTemplate.convertAndSend(queueKey, object);
        } catch (Exception e) {
            logger.error(e.toString());
        }

    }
}