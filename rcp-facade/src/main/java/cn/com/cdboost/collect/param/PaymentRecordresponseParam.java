package cn.com.cdboost.collect.param;


import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author wt
 * @desc
 * @create in  2018/5/7
 **/
public class PaymentRecordresponseParam extends BasequeryParam {
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date payDate;
    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,##")
    private float payMoney;
    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,##")
    private float balance;
    private int payCount;

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public float getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(float payMoney) {
        this.payMoney = payMoney;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public int getPayCount() {
        return payCount;
    }

    public void setPayCount(int payCount) {
        this.payCount = payCount;
    }
}

