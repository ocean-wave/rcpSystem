package cn.com.cdboost.collect.service;


import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.dto.ChangeMeterDetailInfo;
import cn.com.cdboost.collect.dto.ChangeMeterInfo;
import cn.com.cdboost.collect.dto.param.ChangeMeterListQueryVo;
import cn.com.cdboost.collect.dto.param.ChangeMeterParamVo;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.ChangeMeter;

import java.util.List;

/**
 * 换表服务接口
 */
public interface ChangeMeterService extends BaseService<ChangeMeter>{
    // 换表操作
    int updateChangeMeter(ChangeMeterParamVo paramVo, Integer userId, String changeRemark,String guid) throws BusinessException;

    // 换表记录查询
    List<ChangeMeterInfo> queryChangeMeters(ChangeMeterListQueryVo queryVo);

    // 换表详细查询
    ChangeMeterDetailInfo queryChangeMeterDetail(String cno,String customerNo,String deviceType);

}
