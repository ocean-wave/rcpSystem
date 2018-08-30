package cn.com.cdboost.collect.dto.param;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * 下发设备时，前端传入参数
 */
public class SyncMeterParam {

    /**
     * 前端生成的uuid
     */
    @NotBlank(message = "guid不能为空")
    private String guid;

    /**
     * 要同步下发的设备cno列表
     */
    @NotEmpty(message = "cnos列表不能为空")
    List<String> cnos;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public List<String> getCnos() {
        return cnos;
    }

    public void setCnos(List<String> cnos) {
        this.cnos = cnos;
    }
}
