package cn.com.cdboost.collect.service;


import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.dto.FeePriceSolsDTO;
import cn.com.cdboost.collect.dto.param.FeePriceSolsQueryVo;
import cn.com.cdboost.collect.model.FeePriceSols;

import java.util.List;

/**
 * 电价方案服务接口
 */
public interface FeePriceSolsService extends BaseService<FeePriceSols>{
    //电价方案详情
    FeePriceSolsDTO queryData(String priceSolsCode);

    // 查询并返回电价方案信息
    List<FeePriceSolsDTO> query(FeePriceSolsQueryVo queryVo);
}
