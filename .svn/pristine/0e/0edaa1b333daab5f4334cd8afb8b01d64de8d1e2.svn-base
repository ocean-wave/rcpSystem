package cn.com.cdboost.collect.param;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author wt
 * @desc
 * @create in  2018/9/6
 **/
public class BaseSychronizeDto implements Serializable{
    @NotBlank(message ="dataType不能为空" )
    private String dataType;
    @NotBlank(message ="batchNo不能为空" )
    private String batchNo;
    @NotBlank(message ="token不能为空" )
    private String token;

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
