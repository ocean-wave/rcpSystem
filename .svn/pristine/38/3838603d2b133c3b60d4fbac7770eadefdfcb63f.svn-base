package cn.com.cdboost.collect.dto.param;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 远程通断列表查询vo
 */
public class CstOnOffGetQueryNewParam extends CstOnOffGetQueryParam {
    private List<String> deviceNoList;
    private List<String> customerNoList;
    @NotNull(message = "isSearchChil不能为空")
    private Integer isSearchChild;
    private String importGuid;

    public List<String> getDeviceNoList() {
        return deviceNoList;
    }

    public void setDeviceNoList(List<String> deviceNoList) {
        this.deviceNoList = deviceNoList;
    }

    public List<String> getCustomerNoList() {
        return customerNoList;
    }

    public void setCustomerNoList(List<String> customerNoList) {
        this.customerNoList = customerNoList;
    }

    public Integer getIsSearchChild() {
        return isSearchChild;
    }

    public void setIsSearchChild(Integer isSearchChild) {
        this.isSearchChild = isSearchChild;
    }

    public String getImportGuid() {
        return importGuid;
    }

    public void setImportGuid(String importGuid) {
        this.importGuid = importGuid;
    }
}
