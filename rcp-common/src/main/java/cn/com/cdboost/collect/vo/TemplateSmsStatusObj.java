package cn.com.cdboost.collect.vo;

/**
 * 网易云通知类和运营类短信发送状态返回对象中的obj对象
 */
public class TemplateSmsStatusObj {
    /**
     * 发送状态 0-未发送,1-发送成功,2-发送失败,3-反垃圾
     */
    private int status;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 更新时间
     */
    private Long updatetime;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Long updatetime) {
        this.updatetime = updatetime;
    }
}
