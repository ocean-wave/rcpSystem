package cn.com.cdboost.collect.service;


import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.dto.FeeChangeICCardInfo;
import cn.com.cdboost.collect.dto.param.CustomerReAcctVo;
import cn.com.cdboost.collect.model.FeeChangeIcCard;

import java.util.List;

/**
 * 费控IC卡补卡记录服务接口
 */
public interface FeeChangeIcCardService extends BaseService<FeeChangeIcCard>{

    // 根据customerNo查询换卡记录
    List<FeeChangeICCardInfo> queryChangeICCardByCustomerNo(String cno, String customerNo);

    // 重新开户
    void reAdd(CustomerReAcctVo acctVo);

}
