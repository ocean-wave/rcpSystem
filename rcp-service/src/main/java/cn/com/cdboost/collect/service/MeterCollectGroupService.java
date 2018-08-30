package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.dto.MeterCollectGroupDto;
import cn.com.cdboost.collect.dto.param.QueryPr0Vo;
import cn.com.cdboost.collect.model.MeterCollectGroup;

import java.util.List;

/**
 *	客户正向有功总报表
 */
public interface MeterCollectGroupService extends BaseService<MeterCollectGroup> {
    List<MeterCollectGroupDto> queryPr0(Integer userId, QueryPr0Vo queryVo);
}
