package cn.com.cdboost.collect.enums;

/**
 * APP 补抄相关枚举类
 */
public interface ReplenishEnum {
    /**
     * APP 请求结果枚举
     */
    enum AppRequstResult {
        PASSWORD_DECRYPT_EXCEPTION(1, "密码解密失败"),
        AUTH_FAIL(2,"认证失败"),
        SYSTEM_EXCEPTION(3,"系统异常");

        private int code;
        private String message;

        AppRequstResult(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
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
     * 上报采集结果，数据库返回值枚举
     */
    enum UpaloadDbReturn {
        EXCEPTION(-1,"后端服务异常"),
        SUCCESS(1,"成功"),
        FAIL(0,"失败"),
        PARSE_JSON_FAIL(3,"解析JSON数据失败");

        private int code;
        private String message;

        UpaloadDbReturn(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
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
     * 参数校验相关枚举
     */
    enum ParamCheck {
        TASK_CONTENT_IS_NULL(1,"工单内容为空"),
        START_TIME_IS_NULL(2,"开始时间为空"),
        END_TIME_IS_NULL(3,"结束时间为空"),
        RUNTOR_IS_NULL(4,"执行人员id为空"),
        RUNTOR_NAME_IS_NULL(5,"执行人员名称为空"),
        TASK_NO_IS_NULL(6,"任务编号为空"),
        CREATE_TIME_IS_NULL(7,"创建时间为空"),
        METER_COUNT_IS_NULL(8,"采集数量为空"),
        DEVICE_TYEP_IS_NULL(9,"采集设备类型为空"),
        CREATE_NAME_IS_NULL(10,"创建人员姓名为空"),
        GROUP_GUID_IS_NULL(11,"采集数据标识为空"),
        CUSTOMER_NAME_IS_NULL(12,"客户名称为空"),
        CUSTOMER_NO_IS_NULL(13,"客户唯一标识为空"),
        CNO_IS_NULL(14,"CNO为空"),
        COLLECT_SORT_IS_NULL(15,"采集顺序为空"),
        FLAG_IS_NULL(16,"采集标识为空"),
        DATASRC_IS_NULL(17,"采集方式为空"),
        COLLECT_DATA_ITEMS_IS_NULL(18,"collectDataItems值为空"),
        COLLECT_TIME_IS_NULL(19,"采集时间为空"),
        ITEM_GROUP_GUID_IS_NULL(20,"CollectDataItem中采集数据标识为空"),
        MRFLAG_IS_NULL(21,"CollectDataItem中采集DI为空"),
        READTYPE_IS_NULL(22,"CollectDataItem中采集项标识为空"),
        READVALUE_IS_NULL(23,"CollectDataItem中采集值为空"),
        WORK_ORDER_IS_NULL(24,"workOrder对象为空"),
        WORK_ORDER_DETAILs_IS_NULL(25,"workOrderDetails对象List为空"),
        SUPPTIME_IS_NULL(26,"suppTime对象为空"),
        DATAS_IS_NULL(27,"datas对象为空"),
        METERS_IS_EMPTY(28,"meters参数为空"),
        GUID_IS_EMPTY(29,"guid参数为空"),
        METER_READ_QUEUES_IS_EMPTY(30,"meterReadQueues参数为空"),
        SEND_COLLECT_RESULT(31,"采集指令结果sendResult为%d");


        private int code;
        private String message;

        ParamCheck(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
