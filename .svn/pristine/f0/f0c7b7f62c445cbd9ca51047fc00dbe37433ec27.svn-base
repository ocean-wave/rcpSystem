package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.cache.CustomerCacheVo;
import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.dto.CustomerInfoDto;
import cn.com.cdboost.collect.dto.CustomerInfoDtodownload;
import cn.com.cdboost.collect.dto.QueryUserDTO;
import cn.com.cdboost.collect.dto.QueryUserPayDTO;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.dto.response.BuildInfoDto;
import cn.com.cdboost.collect.dto.response.FuzzyQueryUserDto;
import cn.com.cdboost.collect.dto.response.MainSubDto;
import cn.com.cdboost.collect.dto.response.UserFuzzyQueryInfo;
import cn.com.cdboost.collect.model.CustomerInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CustomerInfoMapper extends CommonMapper<CustomerInfo> {
    List<ElectConsumptionResponseParam> queryelectConsumption(ElectConsumptionParam electConsumptionParam);
    //根据条件获取用户档案
    List<CustomerInfoDto> query(CustomerInfoQueryVo customerInfoQueryVo);
    //根据条件获取用户档案
    List<CustomerInfoDtodownload> querynew(CustomerInfoQueryVo customerInfoQueryVo);
    //根据条件获取用户档案
    List<CustomerInfoDto> queryNew(CustomerInfoQueryNewVo customerInfoQueryVo);
    //批量导入用户档案
    void importExcel2DB(Map<String,Object> map);
    // 查询用户的最后一次购电记录
    List<QueryUserDTO> queryUser(RechargeUserQueryVo queryVo);
    // 查询用户的IC卡基础信息
    QueryUserPayDTO queryUserPay(@Param("customerNo") String customerNo,@Param("cno") String cno);
    // 用户信息模糊查询
    List<UserFuzzyQueryInfo> fuzzyQuery(UserFuzzyQueryVo param);
    // 查询统计楼栋信息
    List<BuildInfoDto> queryBuildInfo(List<Long> dataOrgList);
    // 查询总表信息
    List<MainSubDto> queryMainSubTree(@Param("orgNo") Long orgNo,@Param("deviceType") String deviceType);
    // 根据设备编号，模糊查询，返回设备相关的树的节点信息
    List<MainSubDto> fuzzyQueryTree(@Param("orgNo") Long orgNo,
                                    @Param("deviceType") String deviceType,
                                    @Param("deviceNo") String deviceNo);

    List<CustomerCacheVo> queryAll();

    // 用户模糊查询
    List<FuzzyQueryUserDto> fuzzyQueryUserInfo(FuzzyQueryUserParam param);
}