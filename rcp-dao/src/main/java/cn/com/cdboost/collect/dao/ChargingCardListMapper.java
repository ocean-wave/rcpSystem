package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.dto.param.ChargerICCardListParam;
import cn.com.cdboost.collect.dto.param.ChargerICCardOptListParam;
import cn.com.cdboost.collect.model.ChargingCardList;

public interface ChargingCardListMapper extends CommonMapper<ChargingCardList> {
    void addCardList(ChargerICCardListParam param);

    void optCardList(ChargerICCardOptListParam param);

    void addDevCardList(ChargerICCardListParam chargingDevice);
}
