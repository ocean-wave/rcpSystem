package cn.com.cdboost.collect.dto;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.util.List;

/**
 * app发送过来的抄表指令参数
 */
public class SendCollectCommandParam {
    /**
     * App登录用户名
     */
    @NotBlank(message = "loginName不能为空")
    private String loginName;

    /**
     * App登录密码
     */
    @NotBlank(message = "password不能为空")
    private String password;

    /**
     * guid
     */
    @NotBlank(message = "guid不能为空")
    private String guid;

    /**
     * 要采集的设备信息
     */
    @NotEmpty(message = "meters列表不能为空")
    @Valid
    private List<SendCollectMeter> meters;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public List<SendCollectMeter> getMeters() {
        return meters;
    }

    public void setMeters(List<SendCollectMeter> meters) {
        this.meters = meters;
    }
}
