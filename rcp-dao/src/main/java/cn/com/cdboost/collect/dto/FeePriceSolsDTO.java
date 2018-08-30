package cn.com.cdboost.collect.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;
import java.util.List;

/**
 * @author xzy
 * @desc 电价方案查询传送对象
 * @create 2017/7/10 0010
 **/
public class FeePriceSolsDTO {

    private String priceSolsCode;
    private String priceSolsName;
    private String dictItemValue;
    private String dictItemName;
    @JSONField(format = "yyyy-MM-dd")
    private Date createTime;
    private String createUserName;
    private String updateUserName;
    @JSONField(format = "yyyy-MM-dd")
    private Date updateTime;
    private Integer isEnabled;
    private String solsRemark;
    @JSONField(format = "yyyy-MM-dd")
    private Date effectiveDate;

    private List parameters;

    public String getPriceSolsCode() {
        return priceSolsCode;
    }

    public void setPriceSolsCode(String priceSolsCode) {
        this.priceSolsCode = priceSolsCode;
    }

    public String getPriceSolsName() {
        return priceSolsName;
    }

    public void setPriceSolsName(String priceSolsName) {
        this.priceSolsName = priceSolsName;
    }

    public String getDictItemValue() {
        return dictItemValue;
    }

    public void setDictItemValue(String dictItemValue) {
        this.dictItemValue = dictItemValue;
    }

    public String getDictItemName() {
        return dictItemName;
    }

    public void setDictItemName(String dictItemName) {
        this.dictItemName = dictItemName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getSolsRemark() {
        return solsRemark;
    }

    public void setSolsRemark(String solsRemark) {
        this.solsRemark = solsRemark;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public List getParameters() {
        return parameters;
    }

    public void setParameters(List parameters) {
        this.parameters = parameters;
    }
}
