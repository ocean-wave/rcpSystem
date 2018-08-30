package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.param.HistoryElectricAmountParam;
import cn.com.cdboost.collect.vo.DayElectricInfoResponseVo;

import java.text.ParseException;
import java.util.List;

/**
 * 查询历史用电量
 */
public interface HistoryElectricAmountService {

    List<DayElectricInfoResponseVo> queryHistoryElectricAmount(HistoryElectricAmountParam historyElectricAmountParam) throws ParseException;
}
