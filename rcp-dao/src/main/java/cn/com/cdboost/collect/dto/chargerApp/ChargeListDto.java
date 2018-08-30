package cn.com.cdboost.collect.dto.chargerApp;

public class ChargeListDto {
    /**
     * 价格ID
     */
    private Integer priceId;
    /**
     * 实际充值金额
     */
    private Float chageMoney;
    /**
     * chargeDesc
     * 充值描述
     */
    private String chargeDesc;
    /**
     * money
     * 用户到账金额
     */
    private Float money;

    public Integer getPriceId() {
        return priceId;
    }

    public void setPriceId(Integer priceId) {
        this.priceId = priceId;
    }

    public Float getChageMoney() {
        return chageMoney;
    }

    public void setChageMoney(Float chageMoney) {
        this.chageMoney = chageMoney;
    }

    public String getChargeDesc() {
        return chargeDesc;
    }

    public void setChargeDesc(String chargeDesc) {
        this.chargeDesc = chargeDesc;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }
}
