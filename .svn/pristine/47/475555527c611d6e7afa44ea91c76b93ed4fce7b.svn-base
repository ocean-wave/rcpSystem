package cn.com.cdboost.collect.service;


import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.dto.AcctDetailInfo;
import cn.com.cdboost.collect.dto.AcctInfo;
import cn.com.cdboost.collect.dto.param.FeeAcctGetQueryVo;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.FeeAcct;

import java.util.List;

/**
 * 费控IC卡信息服务接口
 */
public interface FeeAcctService extends BaseService<FeeAcct>{

    //新增
    void addFeeAcct(FeeAcct feeAcct);

    //删除
    int deleteFeeAcct(String cno);

    // 按用户删除某块表的记录
    int deleteByParams(String customerNo, String cno);

    // 根据cnos列表，批量查询
    List<FeeAcct> batchQueryByCnos(List<String> cnos);

    void batchDeleteByCnos(List<String> cnos);

    // 根据用户唯一标识删除
    int deleteByCustomerNo(String customerNo);

    //  查询IC卡信息记录
    List<AcctInfo> query(FeeAcctGetQueryVo queryVo);

    // 根据cno修改信息
    void updateFeeAcctByCno(FeeAcct feeAcct) throws BusinessException;

    //  查询用户IC卡详细信息记录
    AcctDetailInfo queryDetail(String customerNo, String cno);

    //  查询IC卡信息记录
    FeeAcct query(String cno, String customerNo);

    //  开户查询
    AcctDetailInfo queryAccount(String customerNo, String cno, int flag);

    //  补卡查询
    AcctDetailInfo queryRepectPay(String customerNo, String cno, String payGuid);

}
