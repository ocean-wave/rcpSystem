package cn.com.cdboost.collect.enums;

/**
 * 设备类型枚举
 */
public enum DeviceType {
    JZQ("04","集中器"),
    CJQ("05","采集器"),
    CONVERTER("06","转换器"),
    ELECTRIC_METER("07","电表"),
    WATER_METER("08","水表"),
    GAS_METER("09","气表");

    private String code;
    private String message;

    DeviceType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 根据code获取对应的message
     * @param code
     * @return
     */
    public static final String getMessageByCode(String code) {
        for (DeviceType deviceType : DeviceType.values()) {
            if (deviceType.getCode().equals(code)) {
                return deviceType.getMessage();
            }
        }
        return "";
    }
}
