package cn.com.cdboost.collect.constant;

import cn.com.cdboost.collect.exception.ErrorEnumValueException;

/**
 * 费控管理模块下相关常量
 */
public class FeeControlConstant {

    /**
     * 支付方式
     */
    public enum PayMethod {
        CASH(1,"现金"),
        WE_CHAT(2,"微信"),
        UNION_PAY(3,"银联"),
        ALI_PAY(4,"支付宝");

        private Integer code;
        private String desc;

        PayMethod(Integer code, String desc) {
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
         * 根据code查询对应的desc
         * @param code
         * @return
         */
        public static final String getDescByCode(Integer code) {
            for (PayMethod payMethod : PayMethod.values()) {
                if (payMethod.getCode().equals(code)) {
                    return payMethod.getDesc();
                }
            }
            return "";
        }
    }

    /**
     * 费用类型
     */
    public enum FeeType {
        ELECTRIC_FEE("07","电费"),
        WATER_FEE("08","水费"),
        GAS_FEE("09","气费");

        private String code;
        private String desc;

        FeeType(String code, String desc) {
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

        /**
         * 根据code查询对应的desc
         * @param code
         * @return
         */
        public static final String getDescByCode(String code) {
            for (FeeType feeType : FeeType.values()) {
                if (feeType.getCode().equals(code)) {
                    return feeType.getDesc();
                }
            }
            return "";
        }
    }


    /**
     * 充值类型
     */
    public enum RechargeType {
        REMOTE_RECHARGE(1,"远程充值"),
        COMMON_RECHARGE(2,"普通充值");

        private Integer code;
        private String desc;

        RechargeType(Integer code, String desc) {
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
         * 该常量是否在枚举常量内
         * @param code
         */
        public static void validate(Integer code) {
            for (RechargeType rechargeType : RechargeType.values()) {
                if (rechargeType.getCode().equals(code)) {
                    return;
                }
            }
            throw new ErrorEnumValueException();
        }
    }

    /**
     * 开户状态
     */
    public enum OpenAccountStatus {
        NOT_OPEN_ACCOUNT(0,"未IC开户"),CREATE_IC_CARD_FAIL(1,"开户制卡失败"),OPEN_ACCOUNT_SUCCESS(2,"开户制卡成功");

        /**
         * 状态常量
         */
        private Integer status;

        /**
         * 状态描述
         */
        private String desc;

        OpenAccountStatus(Integer status, String desc) {
            this.status = status;
            this.desc = desc;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
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
     * 开户的三种情况
     */
    public enum FeeAccountFlag {
        ZERO(0,"开户"),ONE(1,"补开户卡"),THREE(3,"重新开户");

        /**
         * 操作标记
         */
        private Integer flag;

        /**
         * 状态描述
         */
        private String desc;

        FeeAccountFlag(Integer flag, String desc) {
            this.flag = flag;
            this.desc = desc;
        }

        public Integer getFlag() {
            return flag;
        }

        public void setFlag(Integer flag) {
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
     * 充值方式
     */
    public enum PayModelValue {
        APP(1,"APP充值"),IC_CARD(2,"购电卡充值");

        /**
         * 编码
         */
        private Integer code;

        /**
         * 描述信息
         */
        private String desc;

        PayModelValue(Integer code, String desc) {
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


    /**
     * 银联支付方式
     */
    public enum UnionPayType {
        NO_CARD_CHANNEL(1,"无卡支付"),
        WEIXIN_CHANNEL(2,"微信支付通道");

        /**
         * 类型
         */
        private Integer type;

        /**
         * 描述
         */
        private String desc;

        UnionPayType(Integer type, String desc) {
            this.type = type;
            this.desc = desc;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
