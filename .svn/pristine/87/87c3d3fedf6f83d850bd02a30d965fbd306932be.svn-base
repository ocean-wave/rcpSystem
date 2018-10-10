package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.ChargingCardListMapper;
import cn.com.cdboost.collect.model.ChargingCardList;
import cn.com.cdboost.collect.service.ChargingCardListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.Set;


@Service
public class ChargingCardListServiceImpl extends BaseServiceImpl<ChargingCardList> implements ChargingCardListService{

    @Autowired
    private ChargingCardListMapper chargingCardListMapper;

    @Override
    @Transactional
    public void updateSendFlag(Set<String> cardIdSet) {
        Condition condition = new Condition(ChargingCardList.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andIn("cardId",cardIdSet);
        criteria.andEqualTo("state",0);

        ChargingCardList param = new ChargingCardList();
        param.setState(1);
        param.setSendFlag(0);
        chargingCardListMapper.updateByConditionSelective(param,condition);
    }
}
