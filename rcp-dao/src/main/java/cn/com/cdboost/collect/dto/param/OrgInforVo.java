package cn.com.cdboost.collect.dto.param;

/**
 * Created by Administrator on 2017/12/18 0018.
 */
public class OrgInforVo extends PageQueryVo{
    private String porgNo;
    private String orgName;
    private String id;
    private Integer result;

    public String getPorgNo() {
        return porgNo;
    }

    public void setPorgNo(String porgNo) {
        this.porgNo = porgNo;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}
