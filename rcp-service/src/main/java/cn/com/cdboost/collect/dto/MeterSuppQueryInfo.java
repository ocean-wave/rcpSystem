package cn.com.cdboost.collect.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * 工单明细页面，派发状态页签，返回数据
 */
public class MeterSuppQueryInfo {

    /**
     * 请求人姓名
     */
    private String queryUserName;

    /**
     * 请求时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date queryTime;

    /**
     * 部门
     */
    private String orgName;

    /**
     * 联系电话
     */
    private String userMobile;



    public String getQueryUserName() {
        return queryUserName;
    }

    public void setQueryUserName(String queryUserName) {
        this.queryUserName = queryUserName;
    }

    public Date getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(Date queryTime) {
        this.queryTime = queryTime;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }
}
