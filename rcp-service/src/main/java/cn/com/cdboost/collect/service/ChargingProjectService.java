package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.common.BaseService;
import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.ChargerProjectEditParam;
import cn.com.cdboost.collect.dto.param.ChargerProjectSchemeAddParam;
import cn.com.cdboost.collect.dto.param.ChargerSchemeQueryVo;
import cn.com.cdboost.collect.dto.response.ProjectInfoDto;
import cn.com.cdboost.collect.model.ChargingProject;

import java.util.List;
import java.util.Set;

/**
 * 项目设备信息服务
 */
public interface ChargingProjectService extends BaseService<ChargingProject> {
    public List<ProjectInfoDto> queryProjectTreeByName(Set<Long> dataOrgNos, String projectName) ;

    List<ProjectDto> queryAllProject(Integer userId);

    // 根据项目guid查询
    ChargingProject queryByProjectGuid(String projectGuid);

    // 根据项目projectGuid集合，批量查询
    List<ChargingProject> batchQuery(Set<String> projectGuids);

    ChargingProject queryProjectInfo();

    List<ChargingProjectDto> queryList(ChargerSchemeQueryVo queryVo, Integer userId);

    void add(ChargerProjectSchemeAddParam param, Integer id);

    void edit(ChargerProjectEditParam param, Integer id);

    void deleteProject(List<String> projectGuids, Integer id);

    //首页商户信息统计
    MerchantCountDto queryMerchantCount(Integer userId);

    //首页统计最近两天数据
    CompareDataCountInfo queryCompareDataCount(Integer userId);

    //累计数值统计
    ChargerEnergyCountInfo queryEnergyCount(Integer id);

    //首页统计曲线
    FirstCurveInfo queryCountCurve(Integer id, String startDate, String endDate);

    //首页站点列表排行
    List<FirstProjectCountDto> queryProjectCount(Integer id);
}
