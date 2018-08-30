package cn.com.cdboost.collect.dto;

/**
 * Created by Administrator on 2017/12/14 0014.
 */
public class SucRateByCjqVo {
    private String dateTime;
    private Integer meterCnt;
    private Integer roundCnt;
    private Integer failCnt;
    private String sucRate;
    private String getWay;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getMeterCnt() {
        return meterCnt;
    }

    public void setMeterCnt(Integer meterCnt) {
        this.meterCnt = meterCnt;
    }

    public Integer getRoundCnt() {
        return roundCnt;
    }

    public void setRoundCnt(Integer roundCnt) {
        this.roundCnt = roundCnt;
    }

    public Integer getFailCnt() {
        return failCnt;
    }

    public void setFailCnt(Integer failCnt) {
        this.failCnt = failCnt;
    }

    public String getSucRate() {
        return sucRate;
    }

    public void setSucRate(String sucRate) {
        this.sucRate = sucRate;
    }

    public String getGetWay() {
        return getWay;
    }

    public void setGetWay(String getWay) {
        this.getWay = getWay;
    }
}
