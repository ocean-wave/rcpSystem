package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.dto.FeePriceSolsDTO;
import cn.com.cdboost.collect.dto.param.FeePriceSolsQueryVo;
import cn.com.cdboost.collect.model.FeePriceSols;

import java.util.List;

public interface FeePriceSolsMapper extends CommonMapper<FeePriceSols> {
    FeePriceSolsDTO selectByPriceSolsCode(String priceSolsCode);
    // 查询并返回电价方案信息记录
    List<FeePriceSolsDTO> queryFeePriceSols(FeePriceSolsQueryVo queryVo);
}