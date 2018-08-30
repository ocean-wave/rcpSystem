package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.dto.FeeChangeICCardInfo;
import cn.com.cdboost.collect.dto.param.CustomerReAcctVo;
import cn.com.cdboost.collect.model.FeeChangeIcCard;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FeeChangeIcCardMapper extends CommonMapper<FeeChangeIcCard> {

    List<FeeChangeICCardInfo> queryChangeICCardByCustomerNo(@Param("cno") String cno, @Param("customerNo") String customerNo);

    void reAdd(CustomerReAcctVo acctVo);
}