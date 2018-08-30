package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.dto.BaseParam;
import cn.com.cdboost.collect.dto.QueryProcDTO;
import cn.com.cdboost.collect.dto.QueryRechargeRecordDto;
import cn.com.cdboost.collect.dto.param.DisablePayVo;
import cn.com.cdboost.collect.dto.param.QueryPr0Vo;
import cn.com.cdboost.collect.dto.param.RechargePaymentParam;
import cn.com.cdboost.collect.dto.response.CancelOffResponse;
import cn.com.cdboost.collect.dto.response.FeePayDetailDto;
import cn.com.cdboost.collect.model.FeePay;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface FeePayMapper extends CommonMapper<FeePay> {
    // 查询并返回用户充值信息记录
    List<QueryProcDTO> queryByProc(BaseParam param);
    // 充值缴费调用的存储过程
    Integer rechargePayment(RechargePaymentParam param);
    // 通过payGuid查找信息
    FeePayDetailDto selectByPayGuid(String payGuid);
    // 设置充值无效
    void disablePay(DisablePayVo disablePayVo);
    // 查询最后N次充值记录
    List<FeePay> getLastNFeePay(@Param("cno") String cno, @Param("n") int n);

    List<QueryRechargeRecordDto> queryRechargeRecord(QueryPr0Vo queryVo);

    Integer queryTotal(QueryPr0Vo queryVo);

    BigDecimal queryPayMoneyTotal(QueryPr0Vo queryVo);

    // 按用户查询，取消拉闸指令相关信息
    @MapKey("cno")
    Map<String,CancelOffResponse> queryCancelOffMap(String customerNo);

}