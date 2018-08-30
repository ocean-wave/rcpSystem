package cn.com.cdboost.collect.enums;

/**
 * 设备类型枚举
 */
public enum ElectCategory {
    GENERALMETER("1","总表"),
    SUBMETER("2","分表"),
    DEDUCTINGMETER("3","扣减表"),
    REFERENCEMETER("4","参考表");
    private String code;
    private String message;

    ElectCategory(String code, String message) {
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
        for (ElectCategory deviceType : ElectCategory.values()) {
            if (deviceType.getCode().equals(code)) {
                return deviceType.getMessage();
            }
        }
        return "";
    }
}
