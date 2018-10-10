package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.param.BaseSychronizeDto;
import cn.com.cdboost.collect.param.BaseSychronizeReseponse;

/**
 * 查询剩余金额
 */
public interface BaseSychronizeServiceDubbo {

   BaseSychronizeReseponse baseSychronizeService(BaseSychronizeDto baseSychronizeDto) ;
}
