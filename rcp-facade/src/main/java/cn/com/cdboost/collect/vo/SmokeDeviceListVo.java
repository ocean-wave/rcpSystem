package cn.com.cdboost.collect.vo;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author wt
 * @desc
 * @create in  2018/8/22
 **/
public class SmokeDeviceListVo extends QueryListParamDate implements Serializable{

    private static final long serialVersionUID = 8530357570400225442L;
    @NotBlank(message ="status 不能为空")
    private String status;
    private String cno;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    @Override
    protected String defaultSortName() {
        return null;
    }

    @Override
    protected String defaultSortOrder() {
        return null;
    }
}
