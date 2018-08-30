package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.cache.DeviceCacheVo;
import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.dto.DeviceInfoDto;
import cn.com.cdboost.collect.dto.param.DeviceInfoQueryVo;
import cn.com.cdboost.collect.dto.response.OnlineStatus;
import cn.com.cdboost.collect.dto.response.OtherMainSubDto;
import cn.com.cdboost.collect.model.DeviceInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceInfoMapper extends CommonMapper<DeviceInfo> {

    // 查询设备资料列表
    List<DeviceInfoDto> listDeviceRecords(DeviceInfoQueryVo deviceInfoQueryVo);

    // 查询设备资料列表
    List<DeviceInfoDto> listDeviceRecordsNew(DeviceInfoQueryVo deviceInfoQueryVo);

    // 修改设备的部门
    int updateDeviceOrgNo(@Param("orgNo") Long orgNo, @Param("customerNo") String customer);

    // 根据设备类型（只会有这三个：集中器，采集器，转换器）
    List<OtherMainSubDto> queryOtherMainSubTree(@Param("deviceType") String deviceType,
                                                @Param("deviceNo") String deviceNo,
                                                @Param("onlineStatus") Integer onlineStatus,
                                                @Param("orgNoList") List<Long> orgNoList);

    // 根据cnoList查询设备的在线状态
    List<OnlineStatus> queryMainSubOnlineStatus(List<String> cnoList);

    // 查询所有设备缓存信息
    List<DeviceCacheVo> selectAllDeviceCache();
}