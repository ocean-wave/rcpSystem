package cn.com.cdboost.collect.dto;

import java.util.List;

/**
 * Created by Administrator on 2017/12/15 0015.
 */
public class FeePayRecordInfo {
    private FeePayStatisticInfo statistics;
    private List<QueryProcDTO> list;

    public FeePayStatisticInfo getStatistics() {
        return statistics;
    }

    public void setStatistics(FeePayStatisticInfo statistics) {
        this.statistics = statistics;
    }

    public List<QueryProcDTO> getList() {
        return list;
    }

    public void setList(List<QueryProcDTO> list) {
        this.list = list;
    }
}
