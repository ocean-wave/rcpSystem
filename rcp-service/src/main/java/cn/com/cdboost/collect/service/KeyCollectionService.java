package cn.com.cdboost.collect.service;


import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.dto.ImpCollectionAnalysisDTO;
import cn.com.cdboost.collect.dto.KeyCollectDTO;
import cn.com.cdboost.collect.dto.KeyCollectionAnalysisDTO;
import cn.com.cdboost.collect.dto.param.KeyCollectVo;
import cn.com.cdboost.collect.model.EmDImpElecdata;

import java.text.ParseException;
import java.util.List;


/**
 * 集分析服务接口
 */
public interface KeyCollectionService extends BaseService<EmDImpElecdata>{

    List<ImpCollectionAnalysisDTO> queryKeyCollectionOriginalNew(Integer userId,String startDate) throws ParseException;

    /**
     * 根据批次查询成功数据
     * @return
     */
    List<KeyCollectDTO> queryKeyCollectSucc(KeyCollectVo queryVo);

    /**
     * 根据批次查询失败数据
     * @return
     */
    List<KeyCollectDTO> queryKeyCollectFail(KeyCollectVo queryVo);

}
