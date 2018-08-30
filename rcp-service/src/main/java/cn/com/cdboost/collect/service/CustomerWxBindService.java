package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.dto.response.SelectWXData;
import cn.com.cdboost.collect.model.CustomerWxBind;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户微信绑定关联表服务接口
 */
public interface CustomerWxBindService extends BaseService<CustomerWxBind> {
    // 根据openId查询该微信绑定的用户设备信息
    List<CustomerWxBind> queryByOpenId(String openId);
    // 根据openId和设备类型查询
    List<CustomerWxBind> queryByDeviceType(String openId,String deviceType);
    // 根据参数查询对应的微信绑定记录
    CustomerWxBind queryByParams(String meterUserNo,String deviceType, String openId);
    // 根据参数查询对应的微信绑定记录列表
    List<CustomerWxBind> queryByParams(String meterUserNo,String deviceType);
    //查询微信信息
    ArrayList<SelectWXData> selCustomerWXBind(String phoneNo, String customerNo);
    // 微信批量解绑
    int batchUnBind(String ids);
    // 根据customerNo删除该用户关联的所有微信
    int deleteByCustomerNo(String customerNo);
    // 根据参数删除对应记录
    int deleteByParam(String customerNo,String meterUserNo,String deviceType);
}
