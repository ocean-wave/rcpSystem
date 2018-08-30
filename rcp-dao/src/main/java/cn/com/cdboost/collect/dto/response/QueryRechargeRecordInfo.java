package cn.com.cdboost.collect.dto.response;

import cn.com.cdboost.collect.dto.QueryRechargeRecordDto;
import cn.com.cdboost.collect.dto.Statistics;

import java.util.List;

public class QueryRechargeRecordInfo {
    /**
     * 总的退费金额
     */
    private Statistics statistics;
    /**
     * 退费列表
     */
    private List<QueryRechargeRecordDto> list;

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public List<QueryRechargeRecordDto> getList() {
        return list;
    }

    public void setList(List<QueryRechargeRecordDto> list) {
        this.list = list;
    }
}
