package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.ChargerSchemeQueryVo;
import cn.com.cdboost.collect.dto.response.ProjectInfoDto;
import cn.com.cdboost.collect.model.ChargingProject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChargingProjectMapper extends CommonMapper<ChargingProject> {
    List<ProjectInfoDto> queryProjectTreeByName(@Param("list") List<Long> orgNo, @Param("projectName") String projectName);
    List<ChargingProjectDto> queryList(ChargerSchemeQueryVo queryVo);
    Long queryListTotal(ChargerSchemeQueryVo queryVo);

    List<ProjectUseCountListDto> queryProjectUseCountList(ChargerSchemeQueryVo queryVo);

    Integer queryProjectUseListTotal(ChargerSchemeQueryVo queryVo);

    ProjectCountStatic countProjectTotal(ChargerSchemeQueryVo queryVo);

    //首页统计商户信息
    MerchantCountDto queryMerchantCount(@Param("orgNoList") List orgNoList);

    MerchantCountDto queryCustomerCount(@Param("list") List<ProjectDto> projectDtos);

    //查询商户盈利信息
    CompareDataCountDto queryCompareDataCount(@Param("orgNoList") List orgNoList, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

    //首页统计站点排行
    List<FirstProjectCountDto> queryProjectCount(@Param("orgNoList") List orgNoList);
}