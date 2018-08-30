package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.dto.DeviceCountDbDto;
import cn.com.cdboost.collect.dto.DeviceUserListDto;
import cn.com.cdboost.collect.dto.QuerySchemeDto;
import cn.com.cdboost.collect.dto.param.AddSchemeNodeParam;
import cn.com.cdboost.collect.dto.param.DeviceCountVo;
import cn.com.cdboost.collect.dto.param.DeviceUserListVo;
import cn.com.cdboost.collect.dto.param.QuerySchemeVo;
import cn.com.cdboost.collect.model.QueryScheme;

import java.util.List;

public interface QuerySchemeMapper extends CommonMapper<QueryScheme> {
    List<QuerySchemeDto> queryList(QuerySchemeVo querySchemeVo);

    Long selectTotal(QuerySchemeVo querySchemeVo);


    int saveSchememet(AddSchemeNodeParam addSchemeNodeParam);

    List<DeviceCountDbDto> queryDeviceCount(DeviceCountVo deviceCountVo);

    List<DeviceUserListDto> queryDeviceUserList(DeviceUserListVo deviceUserListVo);

    Long queryDeviceTotal(DeviceUserListVo deviceUserListVo);

    // 根据id，批量更新电表数量
    int updateUserCountByIds(List<QueryScheme> list);
}