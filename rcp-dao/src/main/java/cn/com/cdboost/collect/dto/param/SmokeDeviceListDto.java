package cn.com.cdboost.collect.dto.param;

import cn.com.cdboost.collect.vo.QueryListParamDate;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author wt
 * @desc
 * @create in  2018/8/22
 **/
public class SmokeDeviceListDto extends QueryListParamDate {

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
