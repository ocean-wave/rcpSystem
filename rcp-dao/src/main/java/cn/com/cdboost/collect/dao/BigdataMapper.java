package cn.com.cdboost.collect.dao;

import cn.com.cdboost.collect.common.CommonMapper;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.dto.response.QueryCategoryInformationDB;
import cn.com.cdboost.collect.dto.response.QueryCurrentMonthDB;
import cn.com.cdboost.collect.dto.response.QueryElectTopListInfo;
import cn.com.cdboost.collect.model.Action;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BigdataMapper extends CommonMapper<Action> {
    List<QueryDayCollectSucceedRateInfo> queryDayCollectSucceedRate(@Param("queryDayLostDto") QueryDayLostDto queryDayLostDto);
    List<QueryConfessElectDetailInfo> queryConfessElectDetail(@Param("queryDayLostDto") QueryDayLostDto queryDayLostDto);
    List<QuerySupplyElectDetailInfo> querySupplyElectDetail(@Param("queryDayLostDto") QueryDayLostDto queryDayLostDto);
    List<QueryDayLostInfo> queryDayLost(@Param("queryDayLostDto") QueryDayLostDto queryDayLostDto);
    QueryBaseInformationDto queryBaseInformation(@Param("queryBaseInformationDto") QueryBaseInformationDto queryBaseInformationDto);
    List<QueryResidentialListInfo> queryResidentialList(@Param("queryResidentialListDto") QueryResidentialListDto queryResidentialListDto);
    List<QueryLineLostListInfo> queryLineLostList(@Param("queryLineLostListDto") QueryLineLostListDto  queryLineLostListDto);
    List<QueryElectTopListInfo> queryElectTopList(@Param("id")Integer id);

    QueryCategoryInformationDB queryCategoryInformation(@Param("queryCurrentMonthDto") QueryCurrentMonthDto queryCurrentMonthDto);

    List<QueryCurrentMonthDB> queryCurrentMonth(@Param("queryCurrentMonthDto")QueryCurrentMonthDto queryCurrentMonthDto);

    List<QueryCurrentMonthDB> queryCurrentYear(@Param("queryCurrentMonthDto")QueryCurrentMonthDto queryCurrentMonthDto);
}