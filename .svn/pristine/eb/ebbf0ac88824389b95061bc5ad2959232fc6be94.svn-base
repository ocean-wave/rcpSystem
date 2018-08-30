package cn.com.cdboost.collect.dto;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.util.List;

/**
 * app端发送采集设备信息
 */
public class SendCollectMeter {
    /**
     * 集中器
     */
    @NotBlank(message = "jzqCno不能为空")
    private String jzqCno;

    /**
     * 设备编号
     */
    @NotBlank(message = "deviceCno不能为空")
    private String deviceCno;

    /**
     * groupGuid
     */
    @NotBlank(message = "groupGuid不能为空")
    private String groupGuid;

    /**
     * DI和dateformat相关信息
     */
    @NotEmpty(message = "diArray列表不能为空")
    @Valid
    private List<DiAndDateFormat> diArray;

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

    public List<DiAndDateFormat> getDiArray() {
        return diArray;
    }

    public void setDiArray(List<DiAndDateFormat> diArray) {
        this.diArray = diArray;
    }
}
