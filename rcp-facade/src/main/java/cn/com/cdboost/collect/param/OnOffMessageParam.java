package cn.com.cdboost.collect.param;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 *
 */
public class OnOffMessageParam extends BasequeryParam{
    @NotNull(message = "collectDate不能为空")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date collectDate;
    @NotNull(message = "balance不能为空")
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
