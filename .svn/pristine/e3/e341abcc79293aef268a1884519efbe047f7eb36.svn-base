package cn.com.cdboost.collect.dto.param;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 集中器明细界面，主站IP和端口页签，编辑和读取操作的公共的属性
 */
public class CommonJzqConnectionParam {
    /**
     * 主站IP
     */
    @NotBlank(message = "websiteIp不能为空")
    private String websiteIp;

    /**
     * 主站端口
     */
    @NotBlank(message = "websitePort不能为空")
    private String websitePort;

    /**
     * 通道APN
     */
    @NotBlank(message = "apn不能为空")
    private String apn;

    /**
     * 备用IP
     */
    private String spareIp;

    /**
     * 备用端口
     */
    private String sparePort;

    public String getWebsiteIp() {
        return websiteIp;
    }

    public void setWebsiteIp(String websiteIp) {
        this.websiteIp = websiteIp;
    }

    public String getWebsitePort() {
        return websitePort;
    }

    public void setWebsitePort(String websitePort) {
        this.websitePort = websitePort;
    }

    public String getApn() {
        return apn;
    }

    public void setApn(String apn) {
        this.apn = apn;
    }

    public String getSpareIp() {
        return spareIp;
    }

    public void setSpareIp(String spareIp) {
        this.spareIp = spareIp;
    }

    public String getSparePort() {
        return sparePort;
    }

    public void setSparePort(String sparePort) {
        this.sparePort = sparePort;
    }
}
