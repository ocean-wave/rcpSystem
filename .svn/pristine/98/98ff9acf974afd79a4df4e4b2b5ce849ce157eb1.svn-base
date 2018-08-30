package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.CustomerWxBindMapper;
import cn.com.cdboost.collect.dto.response.SelectWXData;
import cn.com.cdboost.collect.model.CustomerWxBind;
import cn.com.cdboost.collect.service.CustomerWxBindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 用户微信绑定关联表服务接口实现类
 */
@Service
public class CustomerWxBindServiceImpl extends BaseServiceImpl<CustomerWxBind> implements CustomerWxBindService {

    @Autowired
    private CustomerWxBindMapper customerWxBindMapper;

    @Override
    public List<CustomerWxBind> queryByOpenId(String openId) {
        CustomerWxBind param = new CustomerWxBind();
        param.setOpenId(openId);
        return customerWxBindMapper.select(param);
    }

    @Override
    public List<CustomerWxBind> queryByDeviceType(String openId, String deviceType) {
        CustomerWxBind param = new CustomerWxBind();
        param.setOpenId(openId);
        param.setDeviceType(deviceType);
        return customerWxBindMapper.select(param);
    }

    @Override
    public CustomerWxBind queryByParams(String meterUserNo, String deviceType, String openId) {
        CustomerWxBind param = new CustomerWxBind();
        param.setMeterUserNo(meterUserNo);
        param.setDeviceType(deviceType);
        param.setOpenId(openId);
        return customerWxBindMapper.selectOne(param);
    }

    @Override
    public List<CustomerWxBind> queryByParams(String meterUserNo, String deviceType) {
        CustomerWxBind param = new CustomerWxBind();
        param.setMeterUserNo(meterUserNo);
        param.setDeviceType(deviceType);
        return customerWxBindMapper.select(param);
    }

    @Override
    public int batchUnBind(String ids) {
        return customerWxBindMapper.deleteByIds(ids);
    }

    @Override
    public ArrayList<SelectWXData> selCustomerWXBind(String customerNo, String cno){
        ArrayList<SelectWXData> wxBind = new ArrayList<SelectWXData>();
        HashMap customerInfo = new HashMap<String,String>();
        customerInfo.put("customerNo",customerNo);
        customerInfo.put("cno",cno);
        wxBind =customerWxBindMapper.selWeiXinBind(customerInfo);
        return wxBind;
    }

    @Override
    public int deleteByCustomerNo(String customerNo) {
        Condition condition = new Condition(CustomerWxBind.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("customerNo",customerNo);
        int i = customerWxBindMapper.deleteByCondition(condition);
        return i;
    }

    @Override
    public int deleteByParam(String customerNo, String meterUserNo,String deviceType) {
        Condition condition = new Condition(CustomerWxBind.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("customerNo",customerNo);
        criteria.andEqualTo("meterUserNo",meterUserNo);
        criteria.andEqualTo("deviceType",deviceType);
        int i = customerWxBindMapper.deleteByCondition(condition);
        return i;
    }
}
