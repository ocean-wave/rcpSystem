package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.dto.AcctDetailInfo;
import cn.com.cdboost.collect.dto.AcctInfo;
import cn.com.cdboost.collect.dto.param.FeeAcctGetQueryVo;
import cn.com.cdboost.collect.model.FeeAcct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FeeAcctMapper extends CommonMapper<FeeAcct> {

    // IC卡查询
    List<AcctInfo> query(FeeAcctGetQueryVo queryVo);
    // IC卡详细查询
    AcctDetailInfo queryDetail(@Param("customerNo") String customerNo, @Param("cno") String cno);
    // 开户查询
    AcctDetailInfo queryAccount(@Param("customerNo") String customerNo,
                                @Param("cno") String cno,
                                @Param("flag") int flag);
    // 补卡查询
    AcctDetailInfo queryRepectPay(@Param("customerNo") String customerNo, @Param("cno") String cno,@Param("payGuid") String payGuid);
}