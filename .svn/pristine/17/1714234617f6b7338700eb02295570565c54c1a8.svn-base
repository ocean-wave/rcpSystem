package cn.com.cdboost.collect.service;

import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.dto.response.QueryCategoryInformationInfo;
import cn.com.cdboost.collect.dto.response.QueryCurrentMonthInfo;
import cn.com.cdboost.collect.dto.response.QueryElectTopListInfo;

import java.util.List;

/**
 * 电表更换的其他参数项目服务接口
 */
public interface BigDataService {
    List<QueryDayCollectSucceedRateInfo> queryDayCollectSucceedRate(QueryDayLostDto queryDayLostDto);
    List<QueryConfessElectDetailInfo> queryConfessElectDetail( QueryDayLostDto queryDayLostDto);
    List<QuerySupplyElectDetailInfo> querySupplyElectDetail( QueryDayLostDto queryDayLostDto);
    List<QueryDayLostInfo> queryDayLost( QueryDayLostDto queryDayLostDto);
    List<QueryResidentialListInfo> queryResidentialList(QueryResidentialListDto queryResidentialListDto);
    List<QueryLineLostListInfo> queryLineLostList(QueryLineLostListDto queryLineLostListDto);
    QueryBaseInformationDto queryBaseInformation(Integer id);

    List<QueryElectTopListInfo> queryElectTopList(Integer id);

    QueryCategoryInformationInfo queryCategoryInformation(QueryCurrentMonthDto queryCurrentMonthDto);

    QueryCurrentMonthInfo queryCurrentMonth(QueryCurrentMonthDto queryCurrentMonthDto);

    QueryCurrentMonthInfo queryCurrentYear(QueryCurrentMonthDto queryCurrentMonthDto);
}
