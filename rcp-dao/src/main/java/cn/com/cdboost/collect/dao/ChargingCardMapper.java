package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.dto.ChargingICCardDto;
import cn.com.cdboost.collect.dto.param.ChargerICCardQueryVo;
import cn.com.cdboost.collect.model.ChargingCard;

import java.util.List;

/**
 * 充电ic卡
 */
public interface ChargingCardMapper extends CommonMapper<ChargingCard> {
    List<ChargingICCardDto> queryList(ChargerICCardQueryVo queryVo);

    Integer queryListTotal(ChargerICCardQueryVo queryVo);
}