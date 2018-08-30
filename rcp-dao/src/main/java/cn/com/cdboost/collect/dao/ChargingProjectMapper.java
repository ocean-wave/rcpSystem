package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.dto.ChargingProjectDto;
import cn.com.cdboost.collect.dto.param.ChargerSchemeQueryVo;
import cn.com.cdboost.collect.model.ChargingProject;

import java.util.List;

public interface ChargingProjectMapper extends CommonMapper<ChargingProject> {
    List<ChargingProjectDto> queryList(ChargerSchemeQueryVo queryVo);
}