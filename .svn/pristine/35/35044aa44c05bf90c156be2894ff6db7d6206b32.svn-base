package cn.com.cdboost.collect.constant;

/**
 * 客户档案相关常量
 */
public class CustomerInfoConstant {
    /**
     * 通断方式
     */
    public enum OffScheme{
        instantOff(0,"立即拉闸"),
        delayOff(1,"延时拉闸"),
        NoOff(2,"不拉闸");
        private Integer code;
        private String desc;

        OffScheme(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
        /**
         * 通过编码查询描述信息
         * @param desc
         * @return
         */
        public static Integer getCodebyMessage(String desc) {
            for (OffScheme deviceType : OffScheme.values()) {
                if (deviceType.getDesc().equals(desc)) {
                    return deviceType.getCode();
                }
            }
            return null;
        }
    }
    /**
     * 用户启用状态
     */
    public enum EnableStatus {
        UNENABLE(0,"无效"),ENABLE(1,"有效");

        /**
         * 编码
         */
        private Integer status;

        /**
         * 描述信息
         */
        private String message;

        EnableStatus(Integer status, String message) {
            this.status = status;
            this.message = message;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    /**
     * 是否关联微信
     */
    public enum RelationWeChat {
        YES(1,"已关联"),NO(0,"未关联");

        /**
         * 编码
         */
        private Integer code;

        /**
         * 描述信息
         */
        private String message;

        RelationWeChat(Integer code, String message) {
            this.code = code;
            this.message = message;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    /**
     * 电表采集设备类型
     */
    public enum ElectricCollectDeviceType {
        MOTE(1,"节点"),COLLECTOR(3,"采集器");

        /**
         * 编码
         */
        private Integer code;

        /**
         * 描述信息
         */
        private String message;

        ElectricCollectDeviceType(Integer code, String message) {
            this.code = code;
            this.message = message;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        /**
         * 通过描述信息获取编码
         * @param message
         * @return
         */
        public static Integer getCodeByMessage(String message) {
            for (ElectricCollectDeviceType deviceType : ElectricCollectDeviceType.values()) {
                if (deviceType.getMessage().equals(message)) {
                    return deviceType.getCode();
                }
            }
            return null;
        }

        /**
         * 通过编码查询描述信息
         * @param code
         * @return
         */
        public static String getMessageByCode(Integer code) {
            for (ElectricCollectDeviceType deviceType : ElectricCollectDeviceType.values()) {
                if (deviceType.getCode().equals(code)) {
                    return deviceType.getMessage();
                }
            }
            return "";
        }
    }

    /**
     * 水表采集设备类型
     */
    public enum WaterCollectDeviceType {
        MOTE(1,"节点"),CONVERTER(2,"转换器");

        /**
         * 编码
         */
        private Integer code;

        /**
         * 描述信息
         */
        private String message;

        WaterCollectDeviceType(Integer code, String message) {
            this.code = code;
            this.message = message;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        /**
         * 通过描述信息获取编码
         * @param message
         * @return
         */
        public static Integer getCodeByMessage(String message) {
            for (WaterCollectDeviceType deviceType : WaterCollectDeviceType.values()) {
                if (deviceType.getMessage().equals(message)) {
                    return deviceType.getCode();
                }
            }
            return null;
        }

        /**
         * 通过编码查询描述信息
         * @param code
         * @return
         */
        public static String getMessageByCode(Integer code) {
            for (WaterCollectDeviceType deviceType : WaterCollectDeviceType.values()) {
                if (deviceType.getCode().equals(code)) {
                    return deviceType.getMessage();
                }
            }
            return "";
        }
    }

    /**
     * 气表采集设备类型
     */
    public enum GasCollectDeviceType {
        MOTE(1,"节点"),CONVERTER(2,"转换器");

        /**
         * 编码
         */
        private Integer code;

        /**
         * 描述信息
         */
        private String message;

        GasCollectDeviceType(Integer code, String message) {
            this.code = code;
            this.message = message;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        /**
         * 通过描述信息获取编码
         * @param message
         * @return
         */
        public static Integer getCodeByMessage(String message) {
            for (GasCollectDeviceType deviceType : GasCollectDeviceType.values()) {
                if (deviceType.getMessage().equals(message)) {
                    return deviceType.getCode();
                }
            }
            return null;
        }

        /**
         * 通过编码查询描述信息
         * @param code
         * @return
         */
        public static String getMessageByCode(Integer code) {
            for (GasCollectDeviceType deviceType : GasCollectDeviceType.values()) {
                if (deviceType.getCode().equals(code)) {
                    return deviceType.getMessage();
                }
            }
            return "";
        }
    }

    /**
     * 电表采集方式
     */
    public enum ElectricCommPort {
        LORA_WAN(32,"LoRaWan"),
        CARRIER(31,"载波"),
        DIRECT_ONE(1,"直连交采"),
        DIRECT_TWO(2,"直连485-1"),
        DIRECT_THREE(3,"直连485-2");

        /**
         * 编码
         */
        private Integer code;

        /**
         * 描述信息
         */
        private String message;

        ElectricCommPort(Integer code, String message) {
            this.code = code;
            this.message = message;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        /**
         * 根据code查找对应的message
         * @param code
         * @return
         */
        public static String getMessageByCode(Integer code) {
            for (ElectricCommPort electricCommPort : ElectricCommPort.values()) {
                if (electricCommPort.getCode().equals(code)) {
                    return electricCommPort.getMessage();
                }
            }
            return "";
        }
    }

    /**
     * 水表、气表的采集方式
     */
    public enum WaterCommPort {
        LORA_WAN(32,"LoRaWan"),CARRIER(31,"载波");

        /**
         * 编码
         */
        private Integer code;

        /**
         * 描述信息
         */
        private String message;

        WaterCommPort(Integer code, String message) {
            this.code = code;
            this.message = message;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        /**
         * 根据code查找对应的message
         * @param code
         * @return
         */
        public static String getMessageByCode(Integer code) {
            for (WaterCommPort waterCommPort : WaterCommPort.values()) {
                if (waterCommPort.getCode().equals(code)) {
                    return waterCommPort.getMessage();
                }
            }
            return "";
        }
    }

    /**
     * 气表的采集方式
     */
    public enum GasCommPort {
        LORA_WAN(32,"LoRaWan"),CARRIER(31,"载波");

        /**
         * 编码
         */
        private Integer code;

        /**
         * 描述信息
         */
        private String message;

        GasCommPort(Integer code, String message) {
            this.code = code;
            this.message = message;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        /**
         * 根据code查找对应的message
         * @param code
         * @return
         */
        public static String getMessageByCode(Integer code) {
            for (GasCommPort gasCommPort : GasCommPort.values()) {
                if (gasCommPort.getCode().equals(code)) {
                    return gasCommPort.getMessage();
                }
            }
            return "";
        }
    }

    /**
     * 节点类型
     */
    public enum MoteType {
        A_TYPE("A","A类节点"),B_TYPE("B","B类节点"),C_TYPE("C","C类节点");

        /**
         * 编码
         */
        private String code;

        /**
         * 描述信息
         */
        private String message;

        MoteType(String code, String message) {
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
    }

    /**
     * 费控方式
     */
    public enum FeeControlType {
        LOCAL(1,"本地费控"),REMOTE(2,"远程费控"),OTHER(0,"其他");

        /**
         * 编码
         */
        private Integer code;

        /**
         * 描述信息
         */
        private String message;

        FeeControlType(Integer code, String message) {
            this.code = code;
            this.message = message;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        /**
         * 根据message查询code
         * @param message
         * @return
         */
        public static Integer getCodeByMessage(String message) {
            for (FeeControlType controlType : FeeControlType.values()) {
                if (controlType.getMessage().equals(message)) {
                    return  controlType.getCode();
                }
            }
            return null;
        }

        /**
         * 根据code查询message
         * @param code
         * @return
         */
        public static String getMessageByCode(Integer code) {
            for (FeeControlType controlType : FeeControlType.values()) {
                if (controlType.getCode().equals(code)) {
                    return controlType.getMessage();
                }
            }
            return "";
        }
    }

    /**
     * 水表类型
     */
    public enum WaterMeterType {
        COLD_WATER("10","冷水水表"),HOT_WATER("11","生活热水水表"),DIRECT_WATER("12","直饮水水表"),WC_WATER("13","中水水表");

        /**
         * 编码
         */
        private String code;

        /**
         * 描述信息
         */
        private String message;

        WaterMeterType(String code, String message) {
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
         * 通过编码获取描述信息
         * @param message
         * @return
         */
        public static String getCodeByMessage(String message) {
            for (WaterMeterType waterMeterType : WaterMeterType.values()) {
                if (waterMeterType.getMessage().equals(message)) {
                    return waterMeterType.getCode();
                }
            }
            return "";
        }

        /**
         * 通过描述信息获取编码
         * @param code
         * @return
         */
        public static String getMessageByCode(String code) {
            for (WaterMeterType waterMeterType : WaterMeterType.values()) {
                if (waterMeterType.getCode().equals(code)) {
                    return waterMeterType.getMessage();
                }
            }
            return "";
        }

    }

    /**
     * 气表类型
     */
    public enum GasMeterType {
        ONE("30","燃气表");

        /**
         * 编码
         */
        private String code;

        /**
         * 描述信息
         */
        private String message;

        GasMeterType(String code, String message) {
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
         * 通过编码获取描述信息
         * @param message
         * @return
         */
        public static String getCodeByMessage(String message) {
            for (GasMeterType gasMeterType : GasMeterType.values()) {
                if (gasMeterType.getMessage().equals(message)) {
                    return gasMeterType.getCode();
                }
            }
            return "";
        }

        /**
         * 通过描述信息获取编码
         * @param code
         * @return
         */
        public static String getMessageByCode(String code) {
            for (GasMeterType gasMeterType : GasMeterType.values()) {
                if (gasMeterType.getCode().equals(code)) {
                    return gasMeterType.getMessage();
                }
            }
            return "";
        }
    }

    /**
     * 用电类型
     */
    public enum UseElectricType {
        NORMAL(1,"普通用电"),BUSINESS(2,"商业用电");

        /**
         * 编码
         */
        private Integer value;

        /**
         * 描述信息
         */
        private String message;

        UseElectricType(Integer value, String message) {
            this.value = value;
            this.message = message;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        /**
         * 通过用电类型描述，获得用电类型值
         * @param message
         * @return
         */
        public static Integer getValueByMessage(String message) {
            for (UseElectricType useElectricType : UseElectricType.values()) {
                if (useElectricType.getMessage().equals(message)) {
                    return useElectricType.getValue();
                }
            }
            return null;
        }
    }

    /**
     * 用水类型
     */
    public enum UseWaterType {
        NORMAL(3,"普通用水"),BUSINESS(4,"商业用水");

        /**
         * 编码
         */
        private Integer value;

        /**
         * 描述信息
         */
        private String message;

        UseWaterType(Integer value, String message) {
            this.value = value;
            this.message = message;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        /**
         * 通过用水类型描述，获得用水类型值
         * @param message
         * @return
         */
        public static Integer getValueByMessage(String message) {
            for (UseWaterType useWaterType : UseWaterType.values()) {
                if (useWaterType.getMessage().equals(message)) {
                    return useWaterType.getValue();
                }
            }
            return null;
        }
    }

    /**
     * 用气类型
     */
    public enum UseGasType {
        NORMAL(5,"普通用气"),BUSINESS(6,"商业用气");

        /**
         * 编码
         */
        private Integer value;

        /**
         * 描述信息
         */
        private String message;

        UseGasType(Integer value, String message) {
            this.value = value;
            this.message = message;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        /**
         * 通过用气类型描述，获得用气类型值
         * @param message
         * @return
         */
        public static Integer getValueByMessage(String message) {
            for (UseGasType useGasType : UseGasType.values()) {
                if (useGasType.getMessage().equals(message)) {
                    return useGasType.getValue();
                }
            }
            return null;
        }
    }

    /**
     * 电表规约
     */
    public enum ElectricCommRule {
        DL_T645_2007_13(30,2,"DL/T645-2007(13表)"),
        DL_T645_2007_09(30,1,"DL/T645-2007(09表)"),
        DL_T645_1997(1,0,"DL/T645-1997"),
        DL_T645_2018_29(10,0,"红相协议");


        /**
         * 规约
         */
        private Integer commRule;

        /**
         * 电表meterType
         */
        private Integer meterType;

        /**
         * 前端选择规约时的名称
         */
        private String name;

        ElectricCommRule(Integer commRule, Integer meterType, String name) {
            this.commRule = commRule;
            this.meterType = meterType;
            this.name = name;
        }

        public Integer getCommRule() {
            return commRule;
        }

        public void setCommRule(Integer commRule) {
            this.commRule = commRule;
        }

        public Integer getMeterType() {
            return meterType;
        }

        public void setMeterType(Integer meterType) {
            this.meterType = meterType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        /**
         * 根据commRule和meterType 查询对应的name
         * @param commRule
         * @param meterType
         * @return
         */
        public static String getNameByParams(Integer commRule, Integer meterType) {
            for (ElectricCommRule electricCommRule : ElectricCommRule.values()) {
                if (electricCommRule.getCommRule().equals(commRule) && electricCommRule.getMeterType().equals(meterType)) {
                    return electricCommRule.getName();
                }
            }
            return "";
        }
    }

    /**
     * 水表规约
     */
    public enum WaterCommRule {
        CJT188(40,0,"CJT188");

        /**
         * 规约
         */
        private Integer commRule;

        /**
         * 气表meterType
         */
        private Integer meterType;

        /**
         * 前端选择规约时的名称
         */
        private String name;

        WaterCommRule(Integer commRule, Integer meterType, String name) {
            this.commRule = commRule;
            this.meterType = meterType;
            this.name = name;
        }

        public Integer getCommRule() {
            return commRule;
        }

        public void setCommRule(Integer commRule) {
            this.commRule = commRule;
        }

        public Integer getMeterType() {
            return meterType;
        }

        public void setMeterType(Integer meterType) {
            this.meterType = meterType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        /**
         * 根据commRule和meterType 查询对应的name
         * @param commRule
         * @param meterType
         * @return
         */
        public static String getNameByParams(Integer commRule, Integer meterType) {
            for (WaterCommRule waterCommRule : WaterCommRule.values()) {
                if (waterCommRule.getCommRule().equals(commRule) && waterCommRule.getMeterType().equals(meterType)) {
                    return waterCommRule.getName();
                }
            }
            return "";
        }
    }

    /**
     * 气表规约
     */
    public enum GasCommRule {
        CJT188(40,0,"CJT188");

        /**
         * 规约
         */
        private Integer commRule;

        /**
         * 气表meterType
         */
        private Integer meterType;

        /**
         * 前端选择规约时的名称
         */
        private String name;

        GasCommRule(Integer commRule, Integer meterType, String name) {
            this.commRule = commRule;
            this.meterType = meterType;
            this.name = name;
        }

        public Integer getCommRule() {
            return commRule;
        }

        public void setCommRule(Integer commRule) {
            this.commRule = commRule;
        }

        public Integer getMeterType() {
            return meterType;
        }

        public void setMeterType(Integer meterType) {
            this.meterType = meterType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        /**
         * 根据commRule和meterType 查询对应的name
         * @param commRule
         * @param meterType
         * @return
         */
        public static String getNameByParams(Integer commRule, Integer meterType) {
            for (GasCommRule gasCommRule : GasCommRule.values()) {
                if (gasCommRule.getCommRule().equals(commRule) && gasCommRule.getMeterType().equals(meterType)) {
                    return gasCommRule.getName();
                }
            }
            return "";
        }
    }

    /**
     * 波特率枚举
     */
    public enum CommBaudrateEnum {
        ONE(1,600),
        TWO(2,1200),
        THREE(3,2400),
        FOUR(4,4800),
        FIVE(5,7200),
        SIX(6,9600),
        SEVEN(7,19200);

        /**
         * 数据库存储的值
         */
        private Integer value;

        /**
         * 速率，展示给前端的值
         */
        private Integer rate;

        CommBaudrateEnum(int value, int rate) {
            this.value = value;
            this.rate = rate;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public Integer getRate() {
            return rate;
        }

        public void setRate(Integer rate) {
            this.rate = rate;
        }

        /**
         * 根据波特率value值，查询对应的速率
         * @param value
         * @return
         */
        public static Integer getRateByValue(Integer value) {
            for (CommBaudrateEnum baudrateEnum : CommBaudrateEnum.values()) {
                if (baudrateEnum.getValue().equals(value)) {
                    return  baudrateEnum.getRate();
                }
            }
            return null;
        }
    }

    /**
     * 设备是否是重点设备
     */
    public enum DeviceIsImportant {
        NO(0,"非重点"),YES(1,"重点");

        /**
         * 编码
         */
        private Integer code;

        /**
         * 描述
         */
        private String desc;

        DeviceIsImportant(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

}
