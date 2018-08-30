package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.dto.ChangeMeterDetailInfo;
import cn.com.cdboost.collect.dto.ChangeMeterInfo;
import cn.com.cdboost.collect.dto.param.ChangeMeterListQueryVo;
import cn.com.cdboost.collect.dto.param.ChangeMeterVo;
import cn.com.cdboost.collect.model.ChangeMeter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChangeMeterMapper extends CommonMapper<ChangeMeter> {

    // 换表
    void updateChangeMeter(ChangeMeterVo meterVo);

    // 换表记录查询
    List<ChangeMeterInfo> queryChangeMeters(ChangeMeterListQueryVo queryVo);

    // 换表详细查询
    ChangeMeterDetailInfo queryChangeMeterDetail(@Param("cno")String cno,
                                                 @Param("customerNo") String customerNo,
                                                 @Param("deviceType") String deviceType,
                                                 @Param("meterUserNo") String meterUserNo);
}