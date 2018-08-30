package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.model.ChargingCst;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;

public interface ChargingCstMapper extends CommonMapper<ChargingCst> {

    // 更新账户余额
    int updateAccountAmount(@Param("id") Integer id,
                            @Param("amount") BigDecimal amount,
                            @Param("updateTime") Date updateTime);


    // 更新账户充值次数
    int updateAccountCnt(@Param("id") Integer id,
                         @Param("chargeCnt") Integer chargeCnt,
                         @Param("expireTime") Date expireTime,
                         @Param("updateTime") Date updateTime);
}