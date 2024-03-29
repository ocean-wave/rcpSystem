package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.dto.param.AccountOperateVo;
import cn.com.cdboost.collect.model.ChargingCst;

/**
 * 客户信息接口服务
 */
public interface ChargingCstService extends BaseService<ChargingCst> {
    ChargingCst queryByOpenId(String openId);

    // 根据微信openId查询用户账户，并加悲观锁
    ChargingCst queryByOpenIdForUpdate(String openId);

    //根据openId和联系电话查询用户
    ChargingCst queryByOpenIdAndPnone(String openId,String phoneNumber);
    // 根据alipayUserId和联系电话查询
    ChargingCst queryByAlipayUserIdAndPhone(String alipayUserId,String phoneNumber);
    // 根据customerGuid 查询
    ChargingCst queryByCustomerGuid(String customerGuid);

    //根据openId更新客户电话
    Integer updateCustomer(String openId,String phoneNumber);
    Integer updatePhoneByAlipayUserId(String alipayUserId,String phoneNumber);


    void updateAccountNew(ChargingCst param,AccountOperateVo accountOperateVo);

    // 根据阿里用户id查询
    ChargingCst queryByAlipayUserId(String userId);

    // 根据阿里用户id查询用户账户，并加悲观锁
    ChargingCst queryByAlipayUserIdForUpdate(String userId);

    // 根据手机号查询账户信息
    ChargingCst queryByPhoneNumber(String phoneNumber);

    // 查询用户账户，并加悲观锁
    ChargingCst queryByCustomerGuidForUpdate(String customerGuid);

}
