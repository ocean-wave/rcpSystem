package cn.com.cdboost.collect.service;


import cn.com.cdboost.collect.param.DayElectricInfoParam;
import cn.com.cdboost.collect.vo.DayElectricInfoResponseVo;

import java.util.List;

/**
 * 查询当日用电信息
 */
public interface DayElectricInfoService {

    List<DayElectricInfoResponseVo> queryDayElectricInfo(DayElectricInfoParam dayElectricInfoParam);
}
