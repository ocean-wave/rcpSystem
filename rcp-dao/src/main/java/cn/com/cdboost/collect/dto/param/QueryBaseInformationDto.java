package cn.com.cdboost.collect.dto.param;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Administrator on 2018/5/16 0016.
 */
public class QueryBaseInformationDto {
    private Integer id;
    /**
     * 拉闸用户数量
     */
    @JSONField(name = "userTrip")
    private String v_offCount;
    /**
     * 客户总数
     */
    @JSONField(name = "userTotal")
    private String v_cstTotal;
    /**
     * 告警客户数
     */
    @JSONField(name = "userAlerm")
    private String v_userAlerm;
    /**
     * 当月电量
     */
    @JSONField(name = "mounthEq")
    private String v_current_power;
    /**
     * 当月电费
     */
    @JSONField(name = "mounthCost")
    private String v_current_money;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getV_offCount() {
        return v_offCount;
    }

    public void setV_offCount(String v_offCount) {
        this.v_offCount = v_offCount;
    }

    public String getV_cstTotal() {
        return v_cstTotal;
    }

    public void setV_cstTotal(String v_cstTotal) {
        this.v_cstTotal = v_cstTotal;
    }

    public String getV_userAlerm() {
        return v_userAlerm;
    }

    public void setV_userAlerm(String v_userAlerm) {
        this.v_userAlerm = v_userAlerm;
    }

    public String getV_current_power() {
        return v_current_power;
    }

    public void setV_current_power(String v_current_power) {
        this.v_current_power = v_current_power;
    }

    public String getV_current_money() {
        return v_current_money;
    }

    public void setV_current_money(String v_current_money) {
        this.v_current_money = v_current_money;
    }
}
