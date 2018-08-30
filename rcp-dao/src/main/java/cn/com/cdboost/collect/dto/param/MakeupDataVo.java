package cn.com.cdboost.collect.dto.param;


/**
 * 待补录数据查询vo
 */
public class MakeupDataVo extends PageQueryVo{
    /**
     * 查询结束时间
     */
    private String endDate;
    /**
     * 查询开始时间
     */
    private String startDate;
    /**
     * 客户编号
     */
    private String customerNo;

    /**
     * 用户id
     */
    private Integer userId;

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
