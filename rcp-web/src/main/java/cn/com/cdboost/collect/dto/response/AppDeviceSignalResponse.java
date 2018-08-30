package cn.com.cdboost.collect.dto.response;

public class AppDeviceSignalResponse {
    /**
     * 信号强度(1-弱 2-中等 3 –强)
     */
    private Integer signal;
    /**
     * 信号强度(1-弱 2-中等 3 –强)
     */
    private String signalDesc;

    public Integer getSignal() {
        return signal;
    }

    public void setSignal(Integer signal) {
        this.signal = signal;
    }

    public String getSignalDesc() {
        return signalDesc;
    }

    public void setSignalDesc(String signalDesc) {
        this.signalDesc = signalDesc;
    }
}
