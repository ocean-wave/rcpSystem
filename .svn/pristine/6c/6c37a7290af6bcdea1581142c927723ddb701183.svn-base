package cn.com.cdboost.collect.constant;

/**
 * 中间件相关常量
 */
public class ClientConstant {
    /**
     * 普通用户枚举
     */
    public enum CommonUserDIEnum {
        PR0("0001ff00","总"),
        BALANCE("00900200","剩余金额"),
        MONTH_FREEZE_P("00010001","月冻结总"),
        DAY_FREEZE_P("05060101","日冻结总");

        /**
         * DI 值
         */
        private String diValue;

        /**
         * DI 描述
         */
        private String desc;

        CommonUserDIEnum(String diValue, String desc) {
            this.diValue = diValue;
            this.desc = desc;
        }

        public String getDiValue() {
            return diValue;
        }

        public void setDiValue(String diValue) {
            this.diValue = diValue;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    /**
     * 重点用户枚举
     */
    public enum ImpUserDIEnum{
        PR0("0001ff00","正向有功总示数"),
        ABC_VOLTAGE("0201ff00","ABC电压"),
        ABC_CURRENT("0202ff00","ABC电流"),
        INSTANT_POWER("0203FF00","瞬时有功功率");

        /**
         * DI 值
         */
        private String diValue;

        /**
         * DI 描述
         */
        private String desc;

        ImpUserDIEnum(String diValue, String desc) {
            this.diValue = diValue;
            this.desc = desc;
        }

        public String getDiValue() {
            return diValue;
        }

        public void setDiValue(String diValue) {
            this.diValue = diValue;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
    /**
     * 前置机状态码
     */
    public enum FrontProcessorReturnCode {
        JZQ_NOT_ONLINE(-1,"集中器不在线"),
        FAIL(0,"集中器在线执行失败"),
        SUCCESS(1,"执行成功"),
        DENY_REPLY(2,"终端(电表)否认回复"),
        JZQ_BUSY(3,"集中器忙"),
        FORWARD_ERROR(4,"转发信息错误"),
        OTHER_ERROR(5,"其它异常");

        /**
         * 前置机返回状态码
         */
        private int code;

        /**
         * 描述
         */
        private String desc;

        FrontProcessorReturnCode(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        /**
         * 根据code查询对应的desc
         * @param code
         * @return
         */
        public static String getDescByCode(int code) {
            for (FrontProcessorReturnCode returnCode : FrontProcessorReturnCode.values()) {
                if (returnCode.getCode() == code) {
                    return returnCode.getDesc();
                }
            }
            return "未知异常吗";
        }
    }

    /**
     * 抄表数据类型常量
     */
    public enum ReadTypeEnum {
        PAY_MONEY("8","购电总额"),
        PAY_COUNT("9","购电次数"),
        BALANCE("10","剩余金额"),
        PR0("11","有功（总）"),
        PR1("12","有功（尖峰）"),
        PR2("13","有功（峰）"),
        PR3("14","有功（谷）"),
        PR4("15","有功（平）"),
        SMOKE_BATTERY_LEVEL("16","烟雾告警电池电量"),
        SMOKE_ALARM("17","烟雾告警数据"),
        TEMPERATURE("18","温度"),
        HUMIDITY("19","湿度"),
        ILLUMINANCE("20","光照度"),
        CURRENT_A("21","A相电流"),
        CURRENT_B("22","B相电流"),
        CURRENT_C("23","C相电流"),
        VOLTAGE_A("24","A相电压"),
        VOLTAGE_B("25","B相电压"),
        VOLTAGE_C("26","C相电压"),
        DAY_FREEZE_P("27","日冻结总"),
        MONTH_FREEZE_P("28","月冻结总"),
        INSTANT_POWER("29","瞬时正向有功总"),
        INSTANT_POWER_A("30","瞬时正向有功A相"),
        INSTANT_POWER_B("31","瞬时正向有功B相"),
        INSTANT_POWER_C("32","瞬时正向有功C相");

        /**
         * 码值
         */
        private String code;

        /**
         * 描述
         */
        private String desc;

        ReadTypeEnum(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    /**
     * 集中器读取时，返回的数据采集端口
     */
    public enum DataCollectPort {
        DIRECT(2,"直连"),
        WIRELESS(5,"无线"),
        CARRIER(31,"载波");

        /**
         * 系统设备参数表中comm_port字段
         */
        private Integer commPort;

        /**
         * 描述
         */
        private String desc;

        DataCollectPort(Integer commPort, String desc) {
            this.commPort = commPort;
            this.desc = desc;
        }

        public Integer getCommPort() {
            return commPort;
        }

        public void setCommPort(Integer commPort) {
            this.commPort = commPort;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        /**
         * 根据commPort查询对应的desc
         * @param commPort
         * @return
         */
        public static String getDescByCommPort(Integer commPort) {
            for (DataCollectPort collectPort : DataCollectPort.values()) {
                if (collectPort.getCommPort().equals(commPort)) {
                    return collectPort.getDesc();
                }
            }
            return "";
        }
    }

    /**
     * 集中器读取时，返回给前端的规约描述信息
     */
    public enum CommRuleDescEnum {
        DLT_645_1997(1,"DL/T645-1997"),
        DLT_645_2007(30,"DL/T645-2007");

        /**
         * 系统设备参数表中comm_rule字段
         */
        private Integer commRule;

        /**
         * 前端需要展示的信息
         */
        private String desc;

        CommRuleDescEnum(Integer commRule, String desc) {
            this.commRule = commRule;
            this.desc = desc;
        }

        public Integer getCommRule() {
            return commRule;
        }

        public void setCommRule(Integer commRule) {
            this.commRule = commRule;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        /**
         * 根据commPort查询对应的desc
         * @param commRule
         * @return
         */
        public static String getDescByCommRule(Integer commRule) {
            for (CommRuleDescEnum descEnum : CommRuleDescEnum.values()) {
                if (descEnum.getCommRule().equals(commRule)) {
                    return descEnum.getDesc();
                }
            }
            return "";
        }
    }

    /**
     * 集中器心跳的读取，下发
     */
    public enum AFN07ObjectFlag {
        READ("0","读取"),SEND("1","下发");

        /**
         * 标志
         */
        private String flag;

        /**
         * 描述
         */
        private String desc;

        AFN07ObjectFlag(String flag, String desc) {
            this.flag = flag;
            this.desc = desc;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    /**
     * 任务处理状态
     */
    public enum TaskProcessStatus {
        YES(1,"已完成"),NO(0,"未完成");

        /**
         * 处理状态 1已完成 0未完成
         */
        private int status;

        /**
         * 描述
         */
        private String desc;

        TaskProcessStatus(int status, String desc) {
            this.status = status;
            this.desc = desc;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
