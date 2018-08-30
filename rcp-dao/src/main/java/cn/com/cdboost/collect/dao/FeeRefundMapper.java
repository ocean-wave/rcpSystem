package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.dto.CustomerRefundCallVo;
import cn.com.cdboost.collect.dto.QueryDetailDto;
import cn.com.cdboost.collect.dto.QueryRefundRecordDto;
import cn.com.cdboost.collect.dto.RefundQueryListDto;
import cn.com.cdboost.collect.dto.param.QueryDetialVo;
import cn.com.cdboost.collect.dto.param.QueryListByCstVo;
import cn.com.cdboost.collect.dto.param.QueryRefundRecordVo;
import cn.com.cdboost.collect.dto.param.RefundQueryListVo;
import cn.com.cdboost.collect.model.FeeRefund;

import java.util.List;

public interface FeeRefundMapper extends CommonMapper<FeeRefund> {
    /**
     * 退费列表查询
     * @param refundQueryListVo
     * @return
     */
    List<RefundQueryListDto> queryList(RefundQueryListVo refundQueryListVo);

    /**
     * 退费列表总数查询
     * @param refundQueryListVo
     * @return
     */
    Long queryCount(RefundQueryListVo refundQueryListVo);

    /**
     * 退费金额统计
     * @param refundQueryListVo
     * @return
     */
    Float queryRefundMoney(RefundQueryListVo refundQueryListVo);

    /**
     * 用户退费详情查询
     * @param queryDetialVo
     * @return
     */
    QueryDetailDto queryDetial(QueryDetialVo queryDetialVo);

    /**
     * 查询客户退费记录
     * @param queryListByCstVo
     * @return
     */
    List<QueryDetailDto> queryListByCst(QueryListByCstVo queryListByCstVo);

    /**
     * 3.4.3.5	客户退费记录
     * @param queryRefundRecordVo
     * @return
     */
    List<QueryRefundRecordDto> queryRefundRecord(QueryRefundRecordVo queryRefundRecordVo);

    /**
     * 退费总金额数统计
     * @param queryRefundRecordVo
     * @return
     */
    Float queryRefundRecordMoneyCount(QueryRefundRecordVo queryRefundRecordVo);

    /**
     * 退费记录统计
     * @param queryRefundRecordVo
     * @return
     */
    Long queryRefundRecordCount(QueryRefundRecordVo queryRefundRecordVo);

    /**
     * 客户退费操作
     * @param customerRefundCallVo
     * @return
     */
    void customerRefund(CustomerRefundCallVo customerRefundCallVo);
}