package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.model.CustomerPhoneBind;
import cn.com.cdboost.collect.model.CustomerWxBind;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface CustomerPhoneBindMapper extends CommonMapper<CustomerPhoneBind> {
    //根据条件插入电话
    void insertPhoneBind(List<CustomerPhoneBind> list);
    //根据条件更新电话信息
    void batchDeletePhoneBind(@Param("customer_no") String customer_no, @Param("mobile_phone") List<String> ls);
    //根据条件查找用户信息
    List<CustomerPhoneBind> selPhone (@Param("customerno") String customerno);

}