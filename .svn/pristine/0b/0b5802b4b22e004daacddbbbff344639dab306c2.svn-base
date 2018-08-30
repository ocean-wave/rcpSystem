package cn.com.cdboost.collect.dto;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * APP 上传采集结果
 */
public class UploadCollectResult implements Serializable{
    private static final long serialVersionUID = -5113191315846834977L;

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
     * 需要更新的补抄工单list
     */
    @NotEmpty(message = "datas列表不能为空")
    @Valid
    private List<WorkOrder4Upload> datas;

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

    public List<WorkOrder4Upload> getDatas() {
        return datas;
    }

    public void setDatas(List<WorkOrder4Upload> datas) {
        this.datas = datas;
    }
}
