package cn.com.cdboost.collect.constant;

/**
 * 阿里云短信发送状态常量
 */
public class AliyunSmsConstant {
    /**
     * 短信发送状态
     */
    public enum SmsSendStatus {
         WAIT_RETURN(1,"等待回执"),
         SEND_FAIL(2,"发送失败"),
         SEND_SUCCESS(3,"发送成功");

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

    /**
     * 阿里云短信接口错误码
     */
    public enum SmsErrorCode {
        RAM_PERMISSION_DENY("isp.RAM_PERMISSION_DENY","RAM权限DENY"),
        OUT_OF_SERVICE("isv.OUT_OF_SERVICE","业务停机"),
        PRODUCT_UN_SUBSCRIPT("isv.PRODUCT_UN_SUBSCRIPT","未开通云通信产品的阿里云客户"),
        PRODUCT_UNSUBSCRIBE("isv.PRODUCT_UNSUBSCRIBE","产品未开通"),
        ACCOUNT_NOT_EXISTS("isv.ACCOUNT_NOT_EXISTS","账户不存在"),
        ACCOUNT_ABNORMAL("isv.ACCOUNT_ABNORMAL","账户异常"),
        SMS_TEMPLATE_ILLEGAL("isv.SMS_TEMPLATE_ILLEGAL","短信模板不合法"),
        SMS_SIGNATURE_ILLEGAL("isv.SMS_SIGNATURE_ILLEGAL","短信签名不合法"),
        INVALID_PARAMETERS("isv.INVALID_PARAMETERS","参数异常"),
        SYSTEM_ERROR("isp.SYSTEM_ERROR","请重试接口调用，如仍存在此情况请创建工单反馈工程师查看"),
        MOBILE_NUMBER_ILLEGAL("isv.MOBILE_NUMBER_ILLEGAL","非法手机号"),
        MOBILE_COUNT_OVER_LIMIT("isv.MOBILE_COUNT_OVER_LIMIT","手机号码数量超过限制"),
        TEMPLATE_MISSING_PARAMETERS("isv.TEMPLATE_MISSING_PARAMETERS","模板缺少变量"),
        BUSINESS_LIMIT_CONTROL("isv.BUSINESS_LIMIT_CONTROL","业务限流"),
        INVALID_JSON_PARAM("isv.INVALID_JSON_PARAM","JSON参数不合法，只接受字符串值"),
        BLACK_KEY_CONTROL_LIMIT("isv.BLACK_KEY_CONTROL_LIMIT","黑名单管控"),
        PARAM_LENGTH_LIMIT("isv.PARAM_LENGTH_LIMIT","参数超出长度限制"),
        PARAM_NOT_SUPPORT_URL("isv.PARAM_NOT_SUPPORT_URL","不支持URL"),
        AMOUNT_NOT_ENOUGH("isv.AMOUNT_NOT_ENOUGH","账户余额不足"),
        TEMPLATE_PARAMS_ILLEGAL("isv.TEMPLATE_PARAMS_ILLEGAL","模板变量里包含非法关键字");

        /**
         * 错误编码
         */
        private String code;

        /**
         * 对应的错误描述
         */
        private String message;

        SmsErrorCode(String code, String message) {
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
}
