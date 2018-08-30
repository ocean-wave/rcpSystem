package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.dto.response.CustomerPhoneArray;
import cn.com.cdboost.collect.model.CustomerPhoneBind;

import java.util.LinkedList;
import java.util.List;

/**
 * 用户手机号关联表服务接口
 */
public interface CustomerPhoneBindService extends BaseService<CustomerPhoneBind> {
    // 根据用户唯一标识和手机号查询绑定记录
    CustomerPhoneBind queryByParam(String customerNo, String mobilePhone);
    // 根据用户唯一标识，查询绑定的所有记录
    List<CustomerPhoneBind> queryByCustomerNo(String customerNo);
    //添加customerPhoneBind信息
    Boolean addCustomerPhoneBind(LinkedList<CustomerPhoneArray> list, Integer currentUserId, String customerNo);
    //查询电话信息
    String selectCustomerPhoneBind (String customerNo);
    // 根据customerNo删除该用户绑定的手机号
    int deleteByCustomerNo(String customerNo);
}
