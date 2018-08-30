package cn.com.cdboost.collect.dto.param;

import java.util.List;

public class DeviceUserListVo extends PageQueryVo {
    /**
     *设备编号
     */
    private String deviceNo;
    /**
     * 分页下标
     */
    private Integer pageIndex;
    /**
     * 组织结构集合
     */
    private List<Long> orgNos;

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public Integer getPageIndex() {
        return (this.getPageNumber()-1)*this.getPageSize();
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public List<Long> getOrgNos() {
        return orgNos;
    }

    public void setOrgNos(List<Long> orgNos) {
        this.orgNos = orgNos;
    }
}
