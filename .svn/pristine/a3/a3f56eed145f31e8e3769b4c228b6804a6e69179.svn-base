package cn.com.cdboost.collect.dto.param;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 系统配置传入参数
 */
public class SystemConfigParam {
    /**
     * 前置机IP
     */
    @NotBlank(message = "frontProcessorIp不能为空")
    private String frontProcessorIp;

    /**
     * 前置机端口
     */
    @NotNull(message = "frontProcessorPort不能为null")
    private Integer frontProcessorPort;

    /**
     * APN
     */
    @NotBlank(message = "apn不能为空")
    private String apn;

    /**
     * 系统名称
     */
    @NotBlank(message = "sysName不能为空")
    private String sysName;

    /**
     * 结算日
     */
    @NotBlank(message = "balanceDate不能为空")
    private String balanceDate;

    /**
     * 支付地址
     */
    @NotBlank(message = "payAddr不能为空")
    private String payAddr;

    /**
     * 公司名
     */
    @NotBlank(message = "companyName不能为空")
    private String companyName;

    /**
     * webSocket的url地址
     */
    @NotBlank(message = "webSocketUrl不能为空")
    private String webSocketUrl;

    private String copyright;

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getFrontProcessorIp() {
        return frontProcessorIp;
    }

    public void setFrontProcessorIp(String frontProcessorIp) {
        this.frontProcessorIp = frontProcessorIp;
    }

    public Integer getFrontProcessorPort() {
        return frontProcessorPort;
    }

    public void setFrontProcessorPort(Integer frontProcessorPort) {
        this.frontProcessorPort = frontProcessorPort;
    }

    public String getApn() {
        return apn;
    }

    public void setApn(String apn) {
        this.apn = apn;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getBalanceDate() {
        return balanceDate;
    }

    public void setBalanceDate(String balanceDate) {
        this.balanceDate = balanceDate;
    }

    public String getPayAddr() {
        return payAddr;
    }

    public void setPayAddr(String payAddr) {
        this.payAddr = payAddr;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getWebSocketUrl() {
        return webSocketUrl;
    }

    public void setWebSocketUrl(String webSocketUrl) {
        this.webSocketUrl = webSocketUrl;
    }
}
