package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.CustomerPhoneBindMapper;
import cn.com.cdboost.collect.dao.CustomerWxBindMapper;
import cn.com.cdboost.collect.dto.response.CustomerPhoneArray;
import cn.com.cdboost.collect.model.CustomerPhoneBind;
import cn.com.cdboost.collect.service.CustomerPhoneBindService;
import cn.com.cdboost.collect.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * 用户手机号关联表服务接口实现类
 */
@Service
public class CustomerPhoneBindServiceImpl extends BaseServiceImpl<CustomerPhoneBind> implements CustomerPhoneBindService {
    @Autowired
    private CustomerPhoneBindMapper customerPhoneBindMapper;
    @Autowired
    private CustomerWxBindMapper customerWxBindMapper;

    @Override
    public CustomerPhoneBind queryByParam(String customerNo, String mobilePhone) {
        CustomerPhoneBind param = new CustomerPhoneBind();
        param.setCustomerNo(customerNo);
        param.setMobilePhone(mobilePhone);
        CustomerPhoneBind customerPhoneBind = customerPhoneBindMapper.selectOne(param);
        return customerPhoneBind;
    }

    @Override
    public List<CustomerPhoneBind> queryByCustomerNo(String customerNo) {
        CustomerPhoneBind param = new CustomerPhoneBind();
        param.setCustomerNo(customerNo);
        return customerPhoneBindMapper.select(param);
    }

    @Override
    public Boolean addCustomerPhoneBind(LinkedList<CustomerPhoneArray> phoneInfolist, Integer currentUserId, String customerNo){

        ArrayList<CustomerPhoneBind> listinfo = new ArrayList<CustomerPhoneBind>();
        List<String> delist = new ArrayList<String>();
        Date createTime = new Date();
        for(CustomerPhoneArray phoneList : phoneInfolist){
            if(phoneList.getOptType().equals("add")){
                CustomerPhoneBind customerPhoneBind = new CustomerPhoneBind();
                customerPhoneBind.setUserId(currentUserId);
                customerPhoneBind.setCustomerNo(customerNo);
                customerPhoneBind.setMobilePhone(phoneList.getPhone());
                customerPhoneBind.setLastSendSmsTime(createTime);
                customerPhoneBind.setCreateTime(createTime);
                listinfo.add(customerPhoneBind);
            }
            else if(phoneList.getOptType().equals("delete")){
                delist.add(phoneList.getPhone());
            }

        }
        try
        {
            if( 0 != listinfo.size())
            {
                customerPhoneBindMapper.insertPhoneBind(listinfo);
            }
            if(0 != delist.size())
            {
                customerPhoneBindMapper.batchDeletePhoneBind(customerNo,delist);
                customerWxBindMapper.deleteWxBind(customerNo,delist);
            }
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;

    }
    @Override
    public String selectCustomerPhoneBind (String customerNo){
        StringBuffer phoneArr = new StringBuffer();
        String phoneResult = "";
        List<CustomerPhoneBind> phoneBinds;
        if(!StringUtil.isEmpty(customerNo)){
            try{
                phoneBinds = customerPhoneBindMapper.selPhone(customerNo);
                for(CustomerPhoneBind phone: phoneBinds){
                    phoneArr.append(phone.getMobilePhone()+",");
                }
            }
            catch (Exception sql)
            {
                sql.printStackTrace();
            }
        }
        if( 0 != phoneArr.length()){
            phoneResult = phoneArr.substring(0,phoneArr.length()-1).toString();
        }

        return phoneResult;
    }

    @Override
    public int deleteByCustomerNo(String customerNo) {
        Condition condition = new Condition(CustomerPhoneBind.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("customerNo",customerNo);

        int i = customerPhoneBindMapper.deleteByCondition(condition);
        return i;
    }
}
