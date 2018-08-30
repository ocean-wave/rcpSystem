package cn.com.cdboost.collect.dto.param;

/**
 * Created by Administrator on 2017/12/14 0014.
 */
public class ChangeICCardParamVo {
    private String cno;
    private Integer userId;
    private String customerNo;
    private String changeRemark;

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getChangeRemark() {
        return changeRemark;
    }

    public void setChangeRemark(String changeRemark) {
        this.changeRemark = changeRemark;
    }
}
