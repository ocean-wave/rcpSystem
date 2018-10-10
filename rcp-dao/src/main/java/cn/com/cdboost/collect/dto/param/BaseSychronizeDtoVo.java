package cn.com.cdboost.collect.dto.param;

/**
 * @author wt
 * @desc
 * @create in  2018/9/6
 **/
public class BaseSychronizeDtoVo {
    private String dataType;
    private String token;
    private String batchNo;
    private Integer result;

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

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
