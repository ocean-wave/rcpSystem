package cn.com.cdboost.collect.constant;

/**
 * 充电IC卡相关常量
 */
public class ChargeCardConstant {

    /**
     * ic卡list表状态
     */
    public enum State {
        DEL(-1,"移出"),NORMAL(1,"正常"),ARREARS(0,"欠费"),;
        /**
         * 运行状态
         */
        private Integer status;

        /**
         * 描述
         */
        private String desc;

        State(Integer status, String desc) {
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
}
