package cn.com.cdboost.collect.dto;

/**
 * Created by Administrator on 2017/12/15 0015.
 */
public class SumMonthPerInfo {
    private String lastMonth;
    private String currentMonth;
    private String percent;
    private String currentDay;

    public String getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(String currentDay) {
        this.currentDay = currentDay;
    }

    public String getLastMonth() {
        return lastMonth;
    }

    public void setLastMonth(String lastMonth) {
        this.lastMonth = lastMonth;
    }

    public String getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(String currentMonth) {
        this.currentMonth = currentMonth;
    }

    public String getPercent() {
        return percent;
    }


    public void setPercent(String percent) {
        this.percent = percent;
    }
}
