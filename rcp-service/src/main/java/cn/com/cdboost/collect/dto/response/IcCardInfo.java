package cn.com.cdboost.collect.dto.response;

import java.util.List;

/**
 * Created by Administrator on 2018/8/28 0028.
 */
public class IcCardInfo {
    private List<IcCardUseInfo> useInfoList;

    private List<IcCardPayInfo> payInfoList;

    public List<IcCardUseInfo> getUseInfoList() {
        return useInfoList;
    }

    public void setUseInfoList(List<IcCardUseInfo> useInfoList) {
        this.useInfoList = useInfoList;
    }

    public List<IcCardPayInfo> getPayInfoList() {
        return payInfoList;
    }

    public void setPayInfoList(List<IcCardPayInfo> payInfoList) {
        this.payInfoList = payInfoList;
    }
}
