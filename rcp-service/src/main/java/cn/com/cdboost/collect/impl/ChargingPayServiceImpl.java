package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.ChargingPayMapper;
import cn.com.cdboost.collect.dto.ChargingICPayDto;
import cn.com.cdboost.collect.dto.param.ChargerICCardQueryVo;
import cn.com.cdboost.collect.model.ChargingPay;
import cn.com.cdboost.collect.service.ChargingPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 客户充值记录信息接口实现类
 */
@Service
public class ChargingPayServiceImpl extends BaseServiceImpl<ChargingPay> implements ChargingPayService {

    @Autowired
    private ChargingPayMapper chargingPayMapper;

    @Override
    public List<ChargingPay> queryListByOpenId(String openId) {
        Condition condition = new Condition(ChargingPay.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("webcharNo",openId);
        criteria.andEqualTo("payState",1);
        return chargingPayMapper.selectByCondition(condition);
    }

    @Override
    public ChargingPay queryByPayFlag(String payFlag) {
        ChargingPay param = new ChargingPay();
        param.setPayFlag(payFlag);
        return chargingPayMapper.selectOne(param);
    }

    @Override
    public List<ChargingICPayDto> queryICCardPayList(ChargerICCardQueryVo queryVo) {

        List<ChargingICPayDto> chargingICPayDtos = chargingPayMapper.queryICCardPayList(queryVo);
        for (ChargingICPayDto chargingICPayDto : chargingICPayDtos) {
            if (chargingICPayDto.getPayWay() == 1){
                chargingICPayDto.setPayUser(chargingICPayDto.getCustomerName());
            }else if (chargingICPayDto.getPayWay() == 2){
                chargingICPayDto.setPayUser(chargingICPayDto.getAlipayNickName());
            }
        }

        Integer total = chargingPayMapper.queryICCardPayTotal(queryVo);
        queryVo.setTotal(total.longValue());
        /*Condition condition = new Condition(ChargingPay.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("customerGuid",queryVo.getCardId());
        criteria.andEqualTo("payState",1);
        criteria.andBetween("createTime",queryVo.getStartDate()+"00:00:00",queryVo.getEndDate()+"23:59:59");
        // 设置分页信息
        PageHelper.startPage(queryVo.getPageNumber(),queryVo.getPageSize(),"create_time desc");
        List<ChargingPay> chargingPays = chargingPayMapper.selectByCondition(condition);
        // 设置分页总条数
        PageInfo<ChargingPay> pageInfo = new PageInfo<>(chargingPays);*/
        return chargingICPayDtos;
    }

    @Override
    public List<ChargingPay> queryByCustomerGuid(String customerGuid) {
        ChargingPay param = new ChargingPay();
        param.setCustomerGuid(customerGuid);
        param.setPayState(1);
        return chargingPayMapper.select(param);
    }

    @Override
    public List<ChargingPay> queryRecentNlist(String cardId, Integer number) {
        Condition condition = new Condition(ChargingPay.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("cardId",cardId);
        criteria.andEqualTo("payState",1);
        condition.setOrderByClause("id desc limit " + number);
        return chargingPayMapper.selectByCondition(condition);
    }
}
