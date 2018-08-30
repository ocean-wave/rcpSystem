package cn.com.cdboost.collect.param;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.format.annotation.NumberFormat;

import java.util.Date;

/**
 * @author wt
 * @desc
 * @create in  2018/5/7
 **/
public class RemainAmountResponse extends BaseresponseParam {
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date collectDate;
    @NumberFormat(style= NumberFormat.Style.NUMBER, pattern="#,##")
    private float balance;

    public Date getCollectDate() {
        return collectDate;
    }

    public void setCollectDate(Date collectDate) {
        this.collectDate = collectDate;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}
