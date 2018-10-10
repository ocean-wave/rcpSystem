package cn.com.cdboost.collect.dto.param;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 账户操作vo
 */
public class AccountOperateVo {
    /**
     * 本次账户操作唯一标识
     */
    private String guid;

    /**
     * 本次账户变动金额
     */
    private BigDecimal amount;

    /**
     * 本次账户变动充值次数
     */
    private Integer chargeCnt;

    /**
     * 充值次数过期时间
     */
    private Date expireTime;

    /**
     * 账户id
     */
    private Integer accountId;

    /**
     * 月卡账户id
     */
    private Integer monthAccountId;

    /**
     * IC卡卡号
     */
    private String cardId;

    /**
     * 业务类型
     */
    private Integer businessType;

    /**
     * 备注
     */
    private String reamrk;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getChargeCnt() {
        return chargeCnt;
    }

    public void setChargeCnt(Integer chargeCnt) {
        this.chargeCnt = chargeCnt;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public String getReamrk() {
        return reamrk;
    }

    public void setReamrk(String reamrk) {
        this.reamrk = reamrk;
    }

    public Integer getMonthAccountId() {
        return monthAccountId;
    }

    public void setMonthAccountId(Integer monthAccountId) {
        this.monthAccountId = monthAccountId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
