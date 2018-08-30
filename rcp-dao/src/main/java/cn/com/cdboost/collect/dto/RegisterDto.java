package cn.com.cdboost.collect.dto;

public class RegisterDto {
    /**
     * 用户注册是否成功（1-成功  0-失败）
     */
    private Integer isSuccess;
    /**
     * 1-注册成功  0-注册失败）
     */
    private String isSuccessDesc;

    public Integer getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Integer isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getIsSuccessDesc() {
        return isSuccessDesc;
    }

    public void setIsSuccessDesc(String isSuccessDesc) {
        this.isSuccessDesc = isSuccessDesc;
    }
}
