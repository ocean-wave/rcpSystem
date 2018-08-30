package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.dto.QueryDetailDto;
import cn.com.cdboost.collect.dto.QueryRefundRecordInfo;
import cn.com.cdboost.collect.dto.RefundQueryListInfo;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.dto.response.QueryRechargeRecordInfo;

import java.util.List;

public interface RefundService {

    /**
     * 3.4.1.1	客户退费列表信息查询接口
     * @param refundQueryListVo
     * @return
     */
    RefundQueryListInfo queryList(RefundQueryListVo refundQueryListVo, Integer userId);

    /**
     * 退费列表总数查询
     * @param refundQueryListVo
     * @param userId
     * @return
     */
    Long queryCount(RefundQueryListVo refundQueryListVo, Integer userId);

    /**
     * 3.4.1.3	客户退费操作
     * @param customerRefundVo
     * @param userId
     * @return
     */
    int customerRefund(CustomerRefundVo customerRefundVo, Integer userId);

    /**
     * 3.4.1.4	客户退费详细查询
     * @param queryDetialVo
     * @param userId
     * @return
     */
    QueryDetailDto queryDetai(QueryDetialVo queryDetialVo, Integer userId);

    /**
     * 3.4.1.5	查询客户的退费记录
     * @param queryListByCstVo
     * @return
     */
    List<QueryDetailDto> queryListByCst(QueryListByCstVo queryListByCstVo, Integer userId);

    /**
     * 客户退费记录
     * @param queryRefundRecordVo
     * @param userId
     * @return
     */
    QueryRefundRecordInfo queryRefundRecord(QueryRefundRecordVo queryRefundRecordVo, Integer userId);

    /**
     * 总记录数查询
     * @param queryRefundRecordVo
     * @return
     */
    Long queryRefundRecordCount(QueryRefundRecordVo queryRefundRecordVo, Integer userId);

    /**
     * 充值记录查询
     * @param queryVo
     * @param id
     * @return
     */
    QueryRechargeRecordInfo queryRechargeRecord(QueryPr0Vo queryVo, Integer id);
}
