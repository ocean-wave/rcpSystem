package cn.com.cdboost.collect.dto.param;

import javax.validation.constraints.NotNull;

/**
 * @author wt
 * @desc
 * @create in  2018/3/12
 **/
public class CustomElechartQueryParam extends  CustomElecListQueryParam{

    @NotNull(message = "查询类型不能为空")
    private String transformerNo;


    public String getTransformerNo() {
        return transformerNo;
    }


    public void setTransformerNo(String transformerNo) {
        this.transformerNo = transformerNo;
    }
}
