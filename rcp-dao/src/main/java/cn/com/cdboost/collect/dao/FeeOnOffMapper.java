package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.dto.FeeOnOffInfo;
import cn.com.cdboost.collect.dto.param.CstOnOffByNoGetQueryVo;
import cn.com.cdboost.collect.dto.param.CstOnOffGetQueryNewVo;
import cn.com.cdboost.collect.dto.param.CstOnOffGetQueryVo;
import cn.com.cdboost.collect.dto.param.CstOnOffOptRstGetQueryVo;
import cn.com.cdboost.collect.model.FeeOnOff;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FeeOnOffMapper extends CommonMapper<FeeOnOff> {
    // 通断记录查询
    List<FeeOnOffInfo> query(CstOnOffGetQueryVo queryVo);
    // 通断记录查询
    List<FeeOnOffInfo> queryNew(CstOnOffGetQueryNewVo queryVo);
    // 通断结果查询
    List<FeeOnOffInfo> queryResult(CstOnOffOptRstGetQueryVo queryVo);
    // 通断历史查询
    List<FeeOnOffInfo> queryHistory(CstOnOffByNoGetQueryVo queryVo);
    // 通断记录查询
    List<FeeOnOffInfo> queryByGuid(@Param("guid") String guid, @Param("status") Integer status, @Param("createTime") String createTime);
}