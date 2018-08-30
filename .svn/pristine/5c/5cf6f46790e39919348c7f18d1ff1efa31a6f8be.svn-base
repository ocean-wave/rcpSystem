package cn.com.cdboost.collect.impl;


import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.FeePriceSolsMapper;
import cn.com.cdboost.collect.dto.FeePriceItemParamEntity;
import cn.com.cdboost.collect.dto.FeePriceSolsDTO;
import cn.com.cdboost.collect.dto.param.FeePriceSolsQueryVo;
import cn.com.cdboost.collect.model.FeePriceSols;
import cn.com.cdboost.collect.service.FeePriceItemService;
import cn.com.cdboost.collect.service.FeePriceSolsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 电价方案服务接口实现类
 */
@Service("feePriceSolsService")
public class FeePriceSolsServiceImpl extends BaseServiceImpl<FeePriceSols> implements FeePriceSolsService {
    private static final Logger logger = LoggerFactory.getLogger(FeePriceSolsServiceImpl.class);

    @Autowired
    private FeePriceSolsMapper feePriceSolsMapper;
    @Autowired
    private FeePriceItemService feePriceItemService;

    @Override
    public FeePriceSolsDTO queryData(String priceSolsCode) {
        logger.info("FeePriceSolsServiceImpl-queryData query:"+priceSolsCode);
        // 查询方案信息
        FeePriceSolsDTO feePriceSolsDTO = feePriceSolsMapper.selectByPriceSolsCode(priceSolsCode);
        List<FeePriceItemParamEntity> list = feePriceItemService.queryData(priceSolsCode);

        if(list != null){
            feePriceSolsDTO.setParameters(list);
        }

        return feePriceSolsDTO;
    }


    // 查询并返回电价方案信息
    @Override
    public List<FeePriceSolsDTO> query(FeePriceSolsQueryVo queryVo) {
        String isEnabled = queryVo.getIsEnabled();
        if (isEnabled == null) {
            queryVo.setIsEnabled("");
        }

        List<FeePriceSolsDTO> list = feePriceSolsMapper.queryFeePriceSols(queryVo);
        return list;
    }

}
