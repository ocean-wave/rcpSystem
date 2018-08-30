package cn.com.cdboost.collect.dto;

import java.util.List;

public class RefundQueryListInfo {
    /**
     * 退款列表
     */
    List<RefundQueryListDto> list;
    /**
     * 退款总额
     */
    private RefundQueryListMoneyCount statistics;

    public List<RefundQueryListDto> getList(){
        return list;
    }

    public void setList(List<RefundQueryListDto> list) {
        this.list = list;
    }

    public RefundQueryListMoneyCount getStatistics() {
        return statistics;
    }

    public void setStatistics(RefundQueryListMoneyCount statistics) {
        this.statistics = statistics;
    }
}
