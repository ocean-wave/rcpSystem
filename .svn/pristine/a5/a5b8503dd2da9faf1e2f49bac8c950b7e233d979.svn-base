package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.constant.ChargeAppConstant;
import cn.com.cdboost.collect.dao.ChargingWithdrawCashMapper;
import cn.com.cdboost.collect.dto.response.WithdrawCashFlow;
import cn.com.cdboost.collect.model.ChargingCst;
import cn.com.cdboost.collect.model.ChargingWithdrawCash;
import cn.com.cdboost.collect.service.ChargingCstService;
import cn.com.cdboost.collect.service.ChargingWithdrawCashService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 充电用户体现记录表实现类
 */
@Service
public class ChargingWithdrawCashServiceImpl extends BaseServiceImpl<ChargingWithdrawCash> implements ChargingWithdrawCashService {

    @Autowired
    private ChargingWithdrawCashMapper chargingWithdrawCashMapper;
    @Autowired
    private ChargingCstService chargingCstService;

    @Override
    public ChargingWithdrawCash queryByGuid(String guid) {
        ChargingWithdrawCash param = new ChargingWithdrawCash();
        param.setGuid(guid);
        return chargingWithdrawCashMapper.selectOne(param);
    }

    @Override
    public Map<String,Object> queryCashFlow(Integer appType,String openId, Integer pageNumber, Integer pageSize) {
        ChargingCst chargingCst;
        if (ChargeAppConstant.AppType.WECHAT.getType().equals(appType)) {
            chargingCst = chargingCstService.queryByOpenId(openId);
        } else {
            chargingCst = chargingCstService.queryByAlipayUserId(openId);
        }

        ChargingWithdrawCash param = new ChargingWithdrawCash();
        param.setCustomerGuid(chargingCst.getCustomerGuid());
        PageHelper.startPage(pageNumber,pageSize,"id desc");
        List<ChargingWithdrawCash> select = chargingWithdrawCashMapper.select(param);
        List<WithdrawCashFlow> list = Lists.newArrayList();
        for (ChargingWithdrawCash withdrawCash : select) {
            WithdrawCashFlow flow = new WithdrawCashFlow();
            flow.setAmount(withdrawCash.getAmount());
            flow.setAfterRemainAmount(withdrawCash.getAfterRemainAmount());
            flow.setStatus(withdrawCash.getStatus());
            flow.setTime(withdrawCash.getCreateTime());
            String descByStatus = ChargeAppConstant.WithdrawCashStatus.getDescByStatus(withdrawCash.getStatus());
            flow.setErrorMsg(descByStatus);
            String descByType = ChargeAppConstant.WithdrawCashType.getDescByType(withdrawCash.getType());
            flow.setWithdrawCashType(descByType);
            list.add(flow);
        }
        PageInfo<ChargingWithdrawCash> pageInfo= new PageInfo<>(select);
        long total = pageInfo.getTotal();

        Map<String,Object> dataMap = Maps.newHashMap();
        dataMap.put("list",list);
        dataMap.put("total",total);
        return dataMap;
    }

    @Override
    public List<ChargingWithdrawCash> queryCashFailFlows() {
        ChargingWithdrawCash param = new ChargingWithdrawCash();
        param.setStatus(ChargeAppConstant.WithdrawCashStatus.PROCESSING.getStatus());
        return chargingWithdrawCashMapper.select(param);
    }
}
