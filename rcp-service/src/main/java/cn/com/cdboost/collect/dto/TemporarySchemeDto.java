package cn.com.cdboost.collect.dto;

import java.math.BigDecimal;

public class TemporarySchemeDto {
    private String schemeGuid;
    private BigDecimal price;
    private Integer time;

    private Integer payCategory;

    //0添加，1删除，2修改
    private Integer flag;
    //电瓶车功率
    private Integer powerType;

    public String getSchemeGuid() {
        return schemeGuid;
    }

    public void setSchemeGuid(String schemeGuid) {
        this.schemeGuid = schemeGuid;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getPayCategory() {
        return payCategory;
    }

    public void setPayCategory(Integer payCategory) {
        this.payCategory = payCategory;
    }

    public Integer getPowerType() {
        return powerType;
    }

    public void setPowerType(Integer powerType) {
        this.powerType = powerType;
    }
}
