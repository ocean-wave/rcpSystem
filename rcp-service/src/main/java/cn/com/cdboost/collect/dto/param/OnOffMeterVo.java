package cn.com.cdboost.collect.dto.param;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 通断电设备相关信息
 */
public class OnOffMeterVo implements Serializable{
    private static final long serialVersionUID = 4020349750382084532L;
    /**
     * 集中器cno
     */
    @NotBlank(message = "jzqCno不能为空")
    private String jzqCno;

    /**
     * 设备cno
     */
    @NotBlank(message = "deviceCno不能为空")
    private String deviceCno;

    /**
     * 客户唯一标识
     */
    @NotBlank(message = "customerNo不能为空")
    private String customerNo;

    public String getJzqCno() {
        return jzqCno;
    }

    public void setJzqCno(String jzqCno) {
        this.jzqCno = jzqCno;
    }

    public String getDeviceCno() {
        return deviceCno;
    }

    public void setDeviceCno(String deviceCno) {
        this.deviceCno = deviceCno;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    @Override
    public String toString() {
        return deviceCno ;

    }
}
