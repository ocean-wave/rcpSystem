package cn.com.cdboost.collect.dto.param;

/**
 * 充电项目方案查询vo
 */
public class ChargerSchemeQueryVo  extends PageQueryVo {

    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 开始时间
     */
    private String beginTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 项目标识
     */
    private String projectGuid;

    /**
     * 方案标识
     */
    private String schemeGuid;

    /**
     * 营收分析统计类型（1：近一周2：近一月3：近三月）
     */
    private Integer countType;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectGuid() {
        return projectGuid;
    }

    public void setProjectGuid(String projectGuid) {
        this.projectGuid = projectGuid;
    }

    public Integer getCountType() {
        return countType;
    }

    public void setCountType(Integer countType) {
        this.countType = countType;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSchemeGuid() {
        return schemeGuid;
    }

    public void setSchemeGuid(String schemeGuid) {
        this.schemeGuid = schemeGuid;
    }

    @Override
    public Integer getPageIndex() {
        return (this.getPageNumber()-1)*this.getPageSize();
    }
}
