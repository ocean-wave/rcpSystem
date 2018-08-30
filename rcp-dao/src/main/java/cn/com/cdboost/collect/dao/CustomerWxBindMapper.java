package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.model.CustomerWxBind;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface CustomerWxBindMapper extends CommonMapper<CustomerWxBind> {
    //查找微信信息
    ArrayList<CustomerWxBind> selWeiXinBind(HashMap<String,String> cusInfo);
    //删除微信信息
    void deleteWxBind(@Param("customerNo") String customerNo,
                      @Param("mobile_phone") List<String> ls);
}