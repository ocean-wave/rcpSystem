package cn.com.cdboost.collect.dto.response;

import cn.com.cdboost.collect.vo.Result;

/**
 * 微信实时召测返回
 */
public class WxResult<T> extends Result<T> {

    /**
     * 召测时间
     */
    private String amountCollectTime;

    public String getAmountCollectTime() {
        return amountCollectTime;
    }

    public void setAmountCollectTime(String amountCollectTime) {
        this.amountCollectTime = amountCollectTime;
    }
}
