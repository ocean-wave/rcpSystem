package cn.com.cdboost.collect.dto;


import javax.persistence.Column;

/**
 * @author wt
 * @desc 用户用电排行
 * @create 2017/8/29 0029
 **/
public class ExcAccountListDTO {


    private String eventCategory;

    private Integer accountNum;

    private Integer dealNum;

    private Integer undealNum;


    public String getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    public Integer getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(Integer accountNum) {
        this.accountNum = accountNum;
    }

    public Integer getDealNum() {
        return dealNum;
    }

    public void setDealNum(Integer dealNum) {
        this.dealNum = dealNum;
    }

    public Integer getUndealNum() {
        return undealNum;
    }

    public void setUndealNum(Integer undealNum) {
        this.undealNum = undealNum;
    }
}
