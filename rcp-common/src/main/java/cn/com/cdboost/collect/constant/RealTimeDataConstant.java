package cn.com.cdboost.collect.constant;

/**
 * 实时数据模块相关枚举
 */
public class RealTimeDataConstant {

    /**
     * 抄表状态，对应em_d_meterreadqueue中read_status
     */
    public enum CollectMeterStatus {
        UN_COLLECT(0,"未抄"),COLLECT_SUCCESS(1,"成功"),COLLECT_FAIL(2,"失败");

        /**
         * 抄表状态
         */
        private Integer status;

        /**
         * 描述
         */
        private String desc;

        CollectMeterStatus(Integer status, String desc) {
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
     * 在线状态
     */
    public enum OnlineStatus {
        OFFLINE(0,"离线"),ONLINE(1,"在线");

        /**
         * 抄表状态
         */
        private Integer status;

        /**
         * 描述
         */
        private String desc;

        OnlineStatus(Integer status, String desc) {
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
