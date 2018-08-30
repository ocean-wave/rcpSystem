package cn.com.cdboost.collect.dto;


import java.util.List;

/**
 * @author wt
 * @desc 用户用电排行
 * @create 2017/8/29 0029
 **/
public class ExcAccountDTO {


    private List<ExcAccountListDTO> list;


    private StatisticsDTO statistics;

    public List<ExcAccountListDTO> getList() {
        return list;
    }

    public void setList(List<ExcAccountListDTO> list) {
        this.list = list;
    }

    public StatisticsDTO getStatistics() {
        return statistics;
    }

    public void setStatistics(StatisticsDTO statistics) {
        this.statistics = statistics;
    }
}
