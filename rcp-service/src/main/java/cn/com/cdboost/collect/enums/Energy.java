package cn.com.cdboost.collect.enums;

/**
 * 用能类型枚举
 */
public enum Energy {
    LIGHTING("1","照明"),
    WATERPUMP("2","水泵"),
    FAN("3","风机"),
    ELEVATOR("4","电梯"),
    POWER("5","动力"),
    OTHER("6","其他"),
    ELECT("07","电能");
    private String code;
    private String message;

    Energy(String code, String message) {
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
        for (Energy deviceType : Energy.values()) {
            if (deviceType.getCode().equals(code)) {
                return deviceType.getMessage();
            }
        }
        return "";
    }
}
