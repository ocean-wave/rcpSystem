package cn.com.cdboost.collect.dto;

import java.util.List;

/**
 * 站点使用列表返回对象
 */
public class ProjectUseCountDto {
    private List<ProjectUseCountListDto> list;
    private ProjectCountStatic statistics;

    public List<ProjectUseCountListDto> getList() {
        return list;
    }

    public void setList(List<ProjectUseCountListDto> list) {
        this.list = list;
    }

    public ProjectCountStatic getStatistics() {
        return statistics;
    }

    public void setStatistics(ProjectCountStatic statistics) {
        this.statistics = statistics;
    }
}
