package cn.com.cdboost.collect.constant;

/**
 * 用户相关
 */
public class UserConstant {
    /**
     * 用户状态
     */
    public enum EnableStatus {
        UNENABLE(0, "无效"),
        ENABLE(1, "有效");

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
     * 是否系统用户
     */
    public enum IsSystem {
        ISSYSTEM(1, "是系统用户"),
        NOTSYSTEM(0, "不是系统用户");

        /**
         * 编码
         */
        private Integer status;

        /**
         * 描述信息
         */
        private String message;

        IsSystem(Integer status, String message) {
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
     * 是否为系统管理员角色
     */
    public enum IsAdminRole {
        ISADMINROLE(1L, "系统管理员角色"),
        NOADMINROLE(0L, "不是系统管理员角色");

        /**
         * 编码
         */
        private Long status;

        /**
         * 描述信息
         */
        private String message;

        IsAdminRole(Long status, String message) {
            this.status = status;
            this.message = message;
        }

        public Long getStatus() {
            return status;
        }

        public void setStatus(Long status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
