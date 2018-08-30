package cn.com.cdboost.collect.param;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 *
 */
public class ArrearageMessageParam extends BasequeryParam{
    @NotNull(message ="collectDate不能为空" )
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date collectDate;
    @NotBlank(message ="arrearsAmount不能为空" )
    private float arrearsAmount;//欠费金额
    @NotNull
    @Range(min = 0,max = 99998,message ="OverdraftAmount不正确" )
    private float OverdraftAmount; //允许透支金额

    public Date getCollectDate() {
        return collectDate;
    }

    public void setCollectDate(Date collectDate) {
        this.collectDate = collectDate;
    }

    public float getArrearsAmount() {
        return arrearsAmount;
    }

    public void setArrearsAmount(float arrearsAmount) {
        this.arrearsAmount = arrearsAmount;
    }

    public float getOverdraftAmount() {
        return OverdraftAmount;
    }

    public void setOverdraftAmount(float overdraftAmount) {
        OverdraftAmount = overdraftAmount;
    }
}
