package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.vo.SmokeDeviceListNoTimeVo;
import cn.com.cdboost.collect.vo.SmokeDeviceSelectTotalInfo;
import cn.com.cdboost.collect.vo.SmokeDeviceVo;

import java.util.List;

/**
 * @author wt
 * @desc
 * @create in  2018/8/22
 **/
public interface SmokeDeviceServiceDubbo  {
     List<SmokeDeviceVo>smokeDeviceList(SmokeDeviceListNoTimeVo smokeDeviceListNoTimeVo);
     List<SmokeDeviceVo>smokeDeviceDetect(SmokeDeviceVo smokeDeviceVo);
     SmokeDeviceVo selectOne(SmokeDeviceVo smokeDeviceVo);
     int insertSelective(SmokeDeviceVo smokeDeviceVo);
     int deleteByCno(List<String> cnos);
     int updateByCno(SmokeDeviceVo smokeDevice);
     SmokeDeviceSelectTotalInfo SmokeDeviceSelectTotal();

}