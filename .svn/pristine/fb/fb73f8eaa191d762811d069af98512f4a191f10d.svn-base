package cn.com.cdboost.collect.service;


import cn.com.cdboost.collect.dto.AcctDetailInfo;
import cn.com.cdboost.collect.dto.AcctInfo;
import cn.com.cdboost.collect.dto.FeeChangeICCardInfo;
import cn.com.cdboost.collect.dto.param.ChangeICCardParamVo;
import cn.com.cdboost.collect.dto.param.CustomerReAcctVo;
import cn.com.cdboost.collect.dto.param.FeeAcctGetQueryVo;
import cn.com.cdboost.collect.dto.param.IcCardAddParamVo;
import cn.com.cdboost.collect.exception.BusinessException;

import java.util.List;
import java.util.Map;

/**
 * @author zc
 * @desc IC卡接口类
 * @create 2017-07-05 17:25
 **/
public interface ICCardService {

    // IC卡查询
    List<AcctInfo> query(FeeAcctGetQueryVo queryVo);

    // IC卡详细查询
    AcctDetailInfo queryDetail(String customerNo, String cno);

    // IC卡新增
    void add(IcCardAddParamVo param, Long currentUserId) throws BusinessException;

    // IC补卡查询
    List<FeeChangeICCardInfo> queryChangeICCard(String cno, String customerNo);

    // IC补卡/换卡
    Map addChangeICCard(ChangeICCardParamVo paramVo) throws BusinessException;

    // 卡开卡成功（回调）更新
    void update(String cNo, String customerNo, Long updateUserId, String payGuid, Integer flag) throws BusinessException;

    // 重新开卡
    void reAdd(CustomerReAcctVo acctVo) throws BusinessException;

    // 开户查询
    AcctDetailInfo queryAccount(String customerNo, String cno, int flag);

    // IC卡详细查询
    AcctDetailInfo queryRepectPay(String customerNo, String cno, String payGuid);
}
