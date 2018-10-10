package cn.com.cdboost.collect.vo;

import cn.com.cdboost.collect.param.QueryListParam;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.List;

/**
 * @author wt
 * @desc
 * @create in  2018/8/24
 **/
public class SmokeDeviceListNoTimeVo extends QueryListParam implements Serializable {
    private static final long serialVersionUID = 7413387823384245522L;
    @NotBlank(message ="status 不能为空")
    private String status;
    private String cno;
    private List<Long> orgNoList;

    public List<Long> getOrgNoList() {
        return orgNoList;
    }

    public void setOrgNoList(List<Long> orgNoList) {
        this.orgNoList = orgNoList;
    }

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
