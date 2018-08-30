package cn.com.cdboost.collect.dao;


import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.dto.KeyCollectDTO;
import cn.com.cdboost.collect.dto.param.KeyCollectVo;
import cn.com.cdboost.collect.model.EmDImpElecdata;

import java.util.List;

/**
 *
 */
public interface EmDImpElecdataMapper extends CommonMapper<EmDImpElecdata> {
    //根据批次查询成功数据
    List<KeyCollectDTO> queryKeyCollectSucc(KeyCollectVo queryVo);
    //根据批次查询失败数据
    List<KeyCollectDTO> queryKeyCollectFail(KeyCollectVo queryVo);
}