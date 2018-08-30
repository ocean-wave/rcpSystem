package cn.com.cdboost.collect.impl;


import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.FeeChangeIcCardMapper;
import cn.com.cdboost.collect.dto.FeeChangeICCardInfo;
import cn.com.cdboost.collect.dto.param.CustomerReAcctVo;
import cn.com.cdboost.collect.model.FeeChangeIcCard;
import cn.com.cdboost.collect.service.FeeChangeIcCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 费控IC卡补卡记录服务接口实现类
 */
@Service
public class FeeChangeIcCardServiceImpl extends BaseServiceImpl<FeeChangeIcCard> implements FeeChangeIcCardService {

    @Autowired
    private FeeChangeIcCardMapper feeChangeIcCardMapper;

    @Override
    public List<FeeChangeICCardInfo> queryChangeICCardByCustomerNo(String cno, String customerNo) {
        return feeChangeIcCardMapper.queryChangeICCardByCustomerNo(cno, customerNo);
    }

    @Override
    @Transactional
    public void reAdd(CustomerReAcctVo acctVo) {
        feeChangeIcCardMapper.reAdd(acctVo);
    }
}
