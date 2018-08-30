package cn.com.cdboost.collect.constant;

/**
 * 网易云短信相关常量
 */
public class NeteaseSmsConstant {

    /**
     * 短信发送状态
     */
    public enum SmsSendStatus {
        NOT_SEND(0,"未发送"),
        SEND_SUCCESS(1,"发送成功"),
        SEND_FAIL(2,"发送失败"),
        GARBAGE(3,"反垃圾");

        /**
         * 状态
         */
        private int status;

        /**
         * 描述
         */
        private String desc;

        SmsSendStatus(int status, String desc) {
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
