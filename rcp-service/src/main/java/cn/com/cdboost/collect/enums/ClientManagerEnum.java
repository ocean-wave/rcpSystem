package cn.com.cdboost.collect.enums;

/**
 * 中间件枚举类
 */
public class ClientManagerEnum {
    /**
     * 中间件任务状态枚举
     */
    public enum TaskStatus {
        DATA_FORMAT_ERROR(-2,"发送数据格式异常"),
        JZQ_NOT_NOLINE(-1,"集中器不在线"),
        EXECUTE_FAIL(0,"集中器在线执行失败"),
        SUCCESS(1,"成功"),
        DENY_REPLY(2,"终端(电表)否认回复"),
        JZQ_BUSY(3,"集中器忙"),
        FORWARD_ERROR(4,"转发信息错误"),
        OTHER_EXCEPTION(5,"其它异常"),
        EXECUTING(101,"正在执行中"),
        TIMEOUT(-101,"超时"),
        SOCKET_EXCEPTION(-105,"Socket异常"),
        GUID_NOT_EXIST(-106,"无此GUID任务"),
        CONECT_DOWN(-107,"连接断开");

        /**
         * 枚举常量值
         */
        private Integer code;

        /**
         * 描述信息
         */
        private String message;

        TaskStatus(Integer code, String message) {
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
         * 根据code查询对应的message
         * @param code
         * @return
         */
        public static String getMessageByCode(Integer code) {
            TaskStatus[] values = TaskStatus.values();
            for (TaskStatus status : values) {
                if (status.getCode().equals(code)) {
                    return status.getMessage();
                }
            }
            return "不存在此状态值";
        }
    }
}
