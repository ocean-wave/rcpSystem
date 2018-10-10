package cn.com.cdboost.collect.dto.response;

/**
 * 充电中的IC卡信息
 */
public class ChargingIcCardInfo {

    /**
     * 充电记录唯一标识
     */
    private String chargingGuid;

    /**
     * IC卡卡号
     */
    private String cardId;

    public String getChargingGuid() {
        return chargingGuid;
    }

    public void setChargingGuid(String chargingGuid) {
        this.chargingGuid = chargingGuid;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
