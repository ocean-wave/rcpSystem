package cn.com.cdboost.collect.dto;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 要实时采集的设备信息
 */
public class RealCollectMeterParam {
    /**
     * 集中器cno
     */
    @NotBlank(message = "jzqCno不能为空")
    private String jzqCno;

    /**
     * 表编号
     */
    @NotBlank(message = "deviceCno不能为空")
    private String deviceCno;

    /**
     * groupGuid
     */
    @NotBlank(message = "groupGuid不能为空")
    private String groupGuid;

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

    public String getGroupGuid() {
        return groupGuid;
    }

    public void setGroupGuid(String groupGuid) {
        this.groupGuid = groupGuid;
    }
}
