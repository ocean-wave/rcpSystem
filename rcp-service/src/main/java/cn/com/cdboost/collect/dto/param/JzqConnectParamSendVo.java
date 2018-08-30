package cn.com.cdboost.collect.dto.param;

/**
 * 集中器连接参数下发vo
 */
public class JzqConnectParamSendVo {
    /**
     * 集中器编号
     */
    private String jzqNo;

    /**
     * 主站IP
     */
    private String websiteIp;

    /**
     * 主站端口
     */
    private String websitePort;

    /**
     * 通道APN
     */
    private String apn;

    /**
     * 备用IP
     */
    private String spareIp;

    /**
     * 备用端口
     */
    private String sparePort;

    public String getJzqNo() {
        return jzqNo;
    }

    public void setJzqNo(String jzqNo) {
        this.jzqNo = jzqNo;
    }

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
