package cn.com.cdboost.collect.param;

import java.io.Serializable;

/**
 * @author wt
 * @desc
 * @create in  2018/5/7
 **/
public class BaseresponseParam implements Serializable {
    String meterNo;

    public String getMeterNo() {
        return meterNo;
    }

    public void setMeterNo(String meterNo) {
        this.meterNo = meterNo;
    }
}
