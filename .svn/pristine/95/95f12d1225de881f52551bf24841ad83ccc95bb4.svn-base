package cn.com.cdboost.collect.impl;


import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.FeePayOrderMapper;
import cn.com.cdboost.collect.model.FeePayOrder;
import cn.com.cdboost.collect.service.FeePayOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 充值订单服务接口实现类
 */
@Transactional
@Service
public class FeePayOrderServiceImpl extends BaseServiceImpl<FeePayOrder> implements FeePayOrderService {
    @Autowired
    private FeePayOrderMapper feePayOrderMapper;

    @Override
    public FeePayOrder queryByTradeNo(String tradeNo) {
        FeePayOrder param = new FeePayOrder();
        param.setTradeNo(tradeNo);
        return feePayOrderMapper.selectOne(param);
    }
}
