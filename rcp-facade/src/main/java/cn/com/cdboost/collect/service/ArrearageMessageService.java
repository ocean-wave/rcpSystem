package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.param.ArrearageMessageParam;

/**
 * 博高能效系统推送欠费消息
 */
public interface ArrearageMessageService {

    String pushArrearageMessage(ArrearageMessageParam arrearageMessageParam);
}
