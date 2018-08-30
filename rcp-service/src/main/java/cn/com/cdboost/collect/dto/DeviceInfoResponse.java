package cn.com.cdboost.collect.dto;

/**
 * 设备信息返回
 */
public class DeviceInfoResponse {
    /**
     * 用户唯一标识加上表计户号，用逗号分隔
     */
    private String value;

    /**
     * 设备表号
     */
    private String text;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
