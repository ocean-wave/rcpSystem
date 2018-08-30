package cn.com.cdboost.collect.dto.response;

import java.math.BigDecimal;

/**
 * 返回给微信的充值记录信息
 */
public class FeePay4WeChat {

    /**
     * 购电日期
     */
    private String payDate;

    /**
     * 购电金额是否下发APP充值需要下发，购电卡充值不需要要下发 0- 未写入 1-写入 2-取消远程 3-远程下发失败
     */
    private Integer writeMeter;

    /**
     * 购电金额
     */
    private BigDecimal payMoney;

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public Integer getWriteMeter() {
        return writeMeter;
    }

    public void setWriteMeter(Integer writeMeter) {
        this.writeMeter = writeMeter;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }
}
