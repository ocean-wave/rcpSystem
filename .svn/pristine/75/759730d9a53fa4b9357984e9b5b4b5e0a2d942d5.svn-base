package cn.com.cdboost.collect.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xzy
 * @desc 充值用户查询返回参数
 * @create 2017/7/7 0007
 **/
public class QueryUserPayDTO extends BaseQueryPayDTO{

    private Integer errorCnt;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date reWrtTime;
    private BigDecimal overdraftFee;
    private Integer curTranfRto;
    private Integer volTranfRto;
    private String cardId;

    public Integer getErrorCnt() {
        return errorCnt;
    }

    public void setErrorCnt(Integer errorCnt) {
        this.errorCnt = errorCnt;
    }

    public Date getReWrtTime() {
        return reWrtTime;
    }

    public void setReWrtTime(Date reWrtTime) {
        this.reWrtTime = reWrtTime;
    }

    public BigDecimal getOverdraftFee() {
        return overdraftFee;
    }

    public void setOverdraftFee(BigDecimal overdraftFee) {
        this.overdraftFee = overdraftFee;
    }

    public Integer getCurTranfRto() {
        return curTranfRto;
    }

    public void setCurTranfRto(Integer curTranfRto) {
        this.curTranfRto = curTranfRto;
    }

    public Integer getVolTranfRto() {
        return volTranfRto;
    }

    public void setVolTranfRto(Integer volTranfRto) {
        this.volTranfRto = volTranfRto;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
