package cn.com.cdboost.collect.dto;

import java.math.BigDecimal;

/**
 * @author wangjin
 * @desc IC卡实体详情
 * @create 2017-07-12 11:27
 **/
public class AcctDetail {
    private String icCardType;  //IC卡类型

    private String cardId;      //购电卡号

//    private Integer payCount;   //购电次数

    private BigDecimal alertFee1;   //报警金额1

    private BigDecimal alertFee2;   //报警金额2

    private BigDecimal overdraftFee;    //透支金额限值

//    private BigDecimal priceSolsCode;   //电价方案代码

    private int volTranfRto;        //电压互感器变比

    private int curTranfRto;        //电流互感器变比

    public void setIcCardType(String icCardType) {
        this.icCardType = icCardType;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public void setAlertFee1(BigDecimal alertFee1) {
        this.alertFee1 = alertFee1;
    }

    public void setAlertFee2(BigDecimal alertFee2) {
        this.alertFee2 = alertFee2;
    }

    public void setOverdraftFee(BigDecimal overdraftFee) {
        this.overdraftFee = overdraftFee;
    }

    public void setVolTranfRto(int volTranfRto) {
        this.volTranfRto = volTranfRto;
    }

    public void setCurTranfRto(int curTranfRto) {
        this.curTranfRto = curTranfRto;
    }

    public String getIcCardType() {
        return icCardType;
    }

    public String getCardId() {
        return cardId;
    }

    public BigDecimal getAlertFee1() {
        return alertFee1;
    }

    public BigDecimal getAlertFee2() {
        return alertFee2;
    }

    public BigDecimal getOverdraftFee() {
        return overdraftFee;
    }

    public int getVolTranfRto() {
        return volTranfRto;
    }

    public int getCurTranfRto() {
        return curTranfRto;
    }
}
