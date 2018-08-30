package cn.com.cdboost.collect.dto;

import java.util.List;

/**
 * @author boost
 */
public class TotalLineLossInfo   {
    private List<LineLossCurve> loss;
    private List<LineLossList> list;
    private LineLossStatistics statistics;

    public List<LineLossCurve> getLoss() {
        return loss;
    }

    public void setLoss(List<LineLossCurve> loss) {
        this.loss = loss;
    }

    public List<LineLossList> getList() {
        return list;
    }

    public void setList(List<LineLossList> list) {
        this.list = list;
    }

    public LineLossStatistics getStatistics() {
        return statistics;
    }

    public void setStatistics(LineLossStatistics statistics) {
        this.statistics = statistics;
    }
}
