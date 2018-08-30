package cn.com.cdboost.collect.dto.param;

import cn.com.cdboost.collect.vo.QueryListParam;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 通断操作结果，成功，失败结果查询
 */
public class CstOnOffOptRstGetQueryParam extends QueryListParam {

    /**
     * 前端通断操作，生成的guid
     */
    @NotBlank(message = "guid不能为空")
    private String guid;

    /**
     * 设备类型
     */
    @NotBlank(message = "deviceType不能为空")
    private String deviceType;

    /**
     * 数据标识，1查询成功的数据，2查询失败的数据
     */
    @NotBlank(message = "dataFlag不能为空")
    private String dataFlag;

    /**
     * 通断操作接口，后端返回给前端的时间
     */
    @NotBlank(message = "date不能为空")
    private String date;

    @Override
    protected String defaultSortName() {
        return null;
    }

    @Override
    protected String defaultSortOrder() {
        return null;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDataFlag() {
        return dataFlag;
    }

    public void setDataFlag(String dataFlag) {
        this.dataFlag = dataFlag;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
