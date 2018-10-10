package cn.com.cdboost.collect.dto.chargerApp;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

public class PriceDto {
    private Integer priceId;
    private Integer payCategory;
    private String payCategoryDesc;
    private BigDecimal money;
    private String payDesc;

    /**
     * 月卡过期时间
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date expireTime;

    /**
     * 月卡剩余次数
     */
    private Integer remainCnt;

    private Integer chargeTime;
    /**
     * 0 功率小于300瓦，1 功率 300-800瓦之间， 2功率大于800瓦
     */
    private Integer power;

    /**
     * 功率描述
     */
    private String powerDesc;

    public String getPayDesc() {
        return payDesc;
    }

    public void setPayDesc(String payDesc) {
        this.payDesc = payDesc;
    }

    public Integer getPriceId() {
        return priceId;
    }

    public void setPriceId(Integer priceId) {
        this.priceId = priceId;
    }

    public Integer getPayCategory() {
        return payCategory;
    }

    public void setPayCategory(Integer payCategory) {
        this.payCategory = payCategory;
    }

    public String getPayCategoryDesc() {
        if(payCategory==1){
            payCategoryDesc="临时用户购买时长";
        }else if(payCategory==2){
            payCategoryDesc="包月用户购买次数";
        }else{
            payCategoryDesc="一次充满";
        }
        return payCategoryDesc;
    }

    public void setPayCategoryDesc(String payCategoryDesc) {
        this.payCategoryDesc = payCategoryDesc;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getChargeTime() {
        return chargeTime;
    }

    public void setChargeTime(Integer chargeTime) {
        this.chargeTime = chargeTime;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public String getPowerDesc() {
        return powerDesc;
    }

    public void setPowerDesc(String powerDesc) {
        this.powerDesc = powerDesc;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getRemainCnt() {
        return remainCnt;
    }

    public void setRemainCnt(Integer remainCnt) {
        this.remainCnt = remainCnt;
    }
}
