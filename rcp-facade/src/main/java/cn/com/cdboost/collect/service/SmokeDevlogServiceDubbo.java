package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.vo.SmokeDeviceListVo;
import cn.com.cdboost.collect.vo.SmokeDevlogVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author wt
 * @desc
 * @create in  2018/8/23
 **/
public interface  SmokeDevlogServiceDubbo   {
    List<SmokeDevlogVo> smokeDeviceStatusList(SmokeDevlogVo smokeDevlogVo);
    SmokeDevlogVo selectOne(SmokeDevlogVo smokeDevlogVo);
    PageInfo smokeDeviceStatusList(SmokeDeviceListVo smokeDeviceListDto);

}
