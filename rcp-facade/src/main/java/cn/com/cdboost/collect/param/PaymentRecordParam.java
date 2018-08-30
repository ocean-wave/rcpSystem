package cn.com.cdboost.collect.param;


import cn.com.cdboost.collect.util.MyDate;

import javax.validation.constraints.NotNull;

/**
 * @author wt
 * @desc
 * @create in  2018/5/7
 **/
public class PaymentRecordParam extends BasequeryParam {

    @NotNull(message = "startDate不能为空")
    @MyDate(message = "startDate 格式不正确")
    private String startDate;
    @NotNull(message = "endDate不能为空")
    @MyDate(message = "endDate 格式不正确")
    //@DateTimeFormat(pattern="yyyy-MM-dd")
    private String endDate;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
