package cn.com.cdboost.collect.dto.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 换表时返回给前端的旧表的最后一次算费（总尖峰平谷）
 */
public class OldReadValueDto {
    /**
     * 旧表最后一次算费总示数
     */
    private BigDecimal lastReadValue;

    /**
     * 旧表最后一次算费尖示数
     */
    private BigDecimal lastReadValue1;

    /**
     * 旧表最后一次算费峰示数
     */
    private BigDecimal lastReadValue2;

    /**
     * 旧表最后一次算费平示数
     */
    private BigDecimal lastReadValue3;

    /**
     * 旧表最后一次算费谷示数
     */
    private BigDecimal lastReadValue4;

    /**
     * 本次数据时间（采集时间）
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date localDataTime;

    /**
     * 剩余金额
     */
    private BigDecimal remainAmount;

    public BigDecimal getLastReadValue() {
        return lastReadValue;
    }

    public void setLastReadValue(BigDecimal lastReadValue) {
        this.lastReadValue = lastReadValue;
    }

    public BigDecimal getLastReadValue1() {
        return lastReadValue1;
    }

    public void setLastReadValue1(BigDecimal lastReadValue1) {
        this.lastReadValue1 = lastReadValue1;
    }

    public BigDecimal getLastReadValue2() {
        return lastReadValue2;
    }

    public void setLastReadValue2(BigDecimal lastReadValue2) {
        this.lastReadValue2 = lastReadValue2;
    }

    public BigDecimal getLastReadValue3() {
        return lastReadValue3;
    }

    public void setLastReadValue3(BigDecimal lastReadValue3) {
        this.lastReadValue3 = lastReadValue3;
    }

    public BigDecimal getLastReadValue4() {
        return lastReadValue4;
    }

    public void setLastReadValue4(BigDecimal lastReadValue4) {
        this.lastReadValue4 = lastReadValue4;
    }

    public Date getLocalDataTime() {
        return localDataTime;
    }

    public void setLocalDataTime(Date localDataTime) {
        this.localDataTime = localDataTime;
    }

    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }
}
