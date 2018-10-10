package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.model.ChargingCardList;

import java.util.Set;


public interface ChargingCardListService extends BaseService<ChargingCardList> {
    // 更新card list表下发标识，状态
    void updateSendFlag(Set<String> cardIdSet);
}
