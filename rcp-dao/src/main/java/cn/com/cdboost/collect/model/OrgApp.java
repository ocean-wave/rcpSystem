package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "em_b_org_app")
public class OrgApp implements Serializable{
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 机构编号
     */
    @Column(name = "org_no")
    private Long orgNo;

    /**
     * appkey
     */
    @Column(name = "app_key")
    private String appKey;

    /**
     * appeui
     */
    @Column(name = "app_Eui")
    private String appEui;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取机构编号
     *
     * @return org_no - 机构编号
     */
    public Long getOrgNo() {
        return orgNo;
    }

    /**
     * 设置机构编号
     *
     * @param orgNo 机构编号
     */
    public void setOrgNo(Long orgNo) {
        this.orgNo = orgNo;
    }

    /**
     * 获取appkey
     *
     * @return app_key - appkey
     */
    public String getAppKey() {
        return appKey;
    }

    /**
     * 设置appkey
     *
     * @param appKey appkey
     */
    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    /**
     * 获取appeui
     *
     * @return app_Eui - appeui
     */
    public String getAppEui() {
        return appEui;
    }

    /**
     * 设置appeui
     *
     * @param appEui appeui
     */
    public void setAppEui(String appEui) {
        this.appEui = appEui;
    }
}