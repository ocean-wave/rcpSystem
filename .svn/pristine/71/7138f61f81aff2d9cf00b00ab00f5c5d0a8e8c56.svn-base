package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.dto.ExcAccountDTO;
import cn.com.cdboost.collect.dto.ExcAccountListDTO;
import cn.com.cdboost.collect.dto.QueryEventDTO;
import cn.com.cdboost.collect.dto.param.QueryEventVo;
import cn.com.cdboost.collect.model.DeviceEvent;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface DeviceEventMapper extends CommonMapper<DeviceEvent> {

    /**
     * 查询事件
     */
    List<QueryEventDTO> getDeviceEvent(QueryEventVo queryVo);

    /**
     * 更新事件
     */
    int  updateDeviceEvent(QueryEventDTO queryEventDTO);

    List<ExcAccountDTO> queryExcAccountList(String startDate, String endDate);

    List<ExcAccountListDTO> queryExcAccountListDTO(@Param("start") String start,
                                                   @Param("end")String end,
                                                   @Param("eventLevel")String eventLevel,
                                                   @Param("userid") Integer userid  ,
                                                   @Param("deviceType") String deviceType
    );

    List<QueryEventDTO> getDeviceEventcall(QueryEventVo queryVo);
}

