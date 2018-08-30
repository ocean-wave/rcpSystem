package cn.com.cdboost.collect.service;


import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.dto.FeeOnOffDetailInfo;
import cn.com.cdboost.collect.dto.FeeOnOffInfo;
import cn.com.cdboost.collect.dto.FeeOnOffStatusListInfo;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.FeeOnOff;

import java.util.List;

/**
 * 远程通断记录服务接口
 */
public interface FeeOnOffService extends BaseService<FeeOnOff>{

    // 记录查询
    List<FeeOnOffInfo> query(CstOnOffGetQueryVo queryVo);
    // 记录查询
    List<FeeOnOffInfo> queryNew(CstOnOffGetQueryNewVo queryVo);
    // 通断操作
    Integer onOff(FeeOnOffParam param) throws BusinessException;

    // 通断状态查询
    FeeOnOffStatusListInfo queryStatus(String guid, String deviceType, long userID, String createTime);

    // 通断结果查询
    List<FeeOnOffInfo> queryResult(CstOnOffOptRstGetQueryVo queryVo);

    // 通断历史查询
    List<FeeOnOffInfo> queryHistory(CstOnOffByNoGetQueryVo queryVo);

    // 查询某一户的，通断历史记录
    List<FeeOnOffDetailInfo> queryHistory4Single(OnOffQueryVo queryVo);

    // 停止批量通断
    boolean stopCollectList(String guid);

    // 通断操作结果查询
    List<FeeOnOffInfo> queryByGuid(String guid,Integer status,String createTime);
}
