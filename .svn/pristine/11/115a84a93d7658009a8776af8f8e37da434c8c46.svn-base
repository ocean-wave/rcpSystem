package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.dto.ProjectDto;
import cn.com.cdboost.collect.dto.param.ChargerProjectEditParam;
import cn.com.cdboost.collect.dto.param.ChargerProjectSchemeAddParam;
import cn.com.cdboost.collect.dto.param.ChargerSchemeQueryVo;
import cn.com.cdboost.collect.dto.ChargingProjectDto;
import cn.com.cdboost.collect.model.ChargingProject;

import java.util.List;

/**
 * 项目设备信息服务
 */
public interface ChargingProjectService extends BaseService<ChargingProject> {
    List<ProjectDto> queryAllProject();

    // 根据项目guid查询
    ChargingProject queryByProjectGuid(String projectGuid);

    ChargingProject queryProjectInfo();

    List<ChargingProjectDto> queryList(ChargerSchemeQueryVo queryVo);

    void add(ChargerProjectSchemeAddParam param, Integer id);

    void edit(ChargerProjectEditParam param, Integer id);

    void deleteProject(List<String> projectGuids, Integer id);
}
