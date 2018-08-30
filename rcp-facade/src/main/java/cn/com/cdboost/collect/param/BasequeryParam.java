package cn.com.cdboost.collect.param;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author wt
 * @desc
 * @create in  2018/5/4
 **/
public class BasequeryParam implements Serializable {
    @NotBlank(message = "token 不能为空")
    private String token;
    @NotBlank(message = "addrCode 不能为空")
    private String addrCode;
    public String getToken() {
        return token;
    }

    public String getAddrCode() {
        return addrCode;
    }

    public void setAddrCode(String addrCode) {
        this.addrCode = addrCode;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
