package cn.com.cdboost.collect.dto.param;

import cn.com.cdboost.collect.dto.RealCollectMeterParam;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 批量发送采集指令，相关参数
 */
public class BatchSendCollectQueryParam {
    /**
     * 前端生成的guid
     */
    @NotBlank(message = "guid不能为空")
    private String guid;

    /**
     * 是否重点表
     */
    @NotNull(message = "isImportant不能为null")
    private Integer isImportant;

    /**
     * 要实时采集的设备信息
     */
    @NotEmpty(message = "meters列表不能为空")
    @Valid
    private List<RealCollectMeterParam> meters;

    /**
     * 召测的类型
     */
    @NotNull(message = "types不能为空")
    private String[] types;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Integer getIsImportant() {
        return isImportant;
    }

    public void setIsImportant(Integer isImportant) {
        this.isImportant = isImportant;
    }

    public List<RealCollectMeterParam> getMeters() {
        return meters;
    }

    public void setMeters(List<RealCollectMeterParam> meters) {
        this.meters = meters;
    }

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }
}
