package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.dao.BigdataMapper;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.dto.response.*;
import cn.com.cdboost.collect.enums.DeviceType;
import cn.com.cdboost.collect.enums.Energy;
import cn.com.cdboost.collect.service.BigDataService;
import cn.com.cdboost.collect.util.MathUtil;
import cn.com.cdboost.collect.util.StringUtil;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimaps;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author wt
 * @desc
 * @create in  2018/7/5
 **/
@Service
public class BigDataServiceImpl implements BigDataService {
    @Autowired
    BigdataMapper bigdataMapper;


    @Override
    public List<QueryDayCollectSucceedRateInfo> queryDayCollectSucceedRate(QueryDayLostDto queryDayLostDto) {
        List<QueryDayCollectSucceedRateInfo> queryDayLostInfos = bigdataMapper.queryDayCollectSucceedRate(queryDayLostDto);
        for (QueryDayCollectSucceedRateInfo queryDayLostInfo : queryDayLostInfos) {
            if(!StringUtils.isEmpty(queryDayLostInfo.getSucceed_rate())){
                String precision = MathUtil.setPrecision(queryDayLostInfo.getSucceed_rate(), 4);
                if(new BigDecimal(precision).doubleValue()==0){
                    queryDayLostInfo.setSucceed_rate("0");
                }else{
                    if(StringUtil.isInteger(precision)){
                        queryDayLostInfo.setSucceed_rate(String.valueOf(new BigDecimal(precision).intValue()));
                    }else{
                        queryDayLostInfo.setSucceed_rate(precision);
                    }
                }
            }
        }

        return queryDayLostInfos;
    }

    @Override
    public List<QueryConfessElectDetailInfo> queryConfessElectDetail(QueryDayLostDto queryDayLostDto) {
        List<QueryConfessElectDetailInfo> queryDayLostInfos = bigdataMapper.queryConfessElectDetail(queryDayLostDto);
        return queryDayLostInfos;
    }

    @Override
    public List<QuerySupplyElectDetailInfo> querySupplyElectDetail(QueryDayLostDto queryDayLostDto) {
        List<QuerySupplyElectDetailInfo> queryDayLostInfos = bigdataMapper.querySupplyElectDetail(queryDayLostDto);
        return queryDayLostInfos;
    }

    @Override
    public List<QueryDayLostInfo> queryDayLost(QueryDayLostDto queryDayLostDto) {
        List<QueryDayLostInfo> queryDayLostInfos = bigdataMapper.queryDayLost(queryDayLostDto);
        for (QueryDayLostInfo queryDayLostInfo : queryDayLostInfos) {
            String meterCount = queryDayLostInfo.getMeter_count();
            String succeedAmount = queryDayLostInfo.getSucceed_amount();
            String powerSupply = queryDayLostInfo.getPower_supply();
            String electSale = queryDayLostInfo.getElect_sale();
            if(!StringUtils.isEmpty(powerSupply)){
                if(!StringUtils.isEmpty(electSale)){
                    queryDayLostInfo.setElect_loss(String.valueOf(new BigDecimal(powerSupply).subtract(new BigDecimal(electSale))));
                }
            }
            String electLoss = queryDayLostInfo.getElect_loss();
            if(!StringUtils.isEmpty(electLoss)){
                if(!StringUtils.isEmpty(powerSupply)&&new BigDecimal(powerSupply).doubleValue()!=0){
                    BigDecimal value =new BigDecimal(electLoss).divide(new BigDecimal(powerSupply), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2);
                    if (StringUtil.isInteger(value.toString())){
                        queryDayLostInfo.setLoss_rate(String.valueOf(value));
                    }else {
                        queryDayLostInfo.setLoss_rate(String.valueOf(value));
                    }
                }
            }
                if (!StringUtils.isEmpty(succeedAmount)) {
                    if(!StringUtils.isEmpty(meterCount)&&new BigDecimal(meterCount).doubleValue()!=0) {
                    BigDecimal divide = new BigDecimal(succeedAmount).divide(new BigDecimal(meterCount), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2);
                        if (StringUtil.isInteger(divide.toString())){
                            queryDayLostInfo.setSucceedRate(String.valueOf(divide.intValue()));
                        }else {
                            queryDayLostInfo.setSucceedRate(String.valueOf(divide));
                        }

                }
            }
        }

        return queryDayLostInfos;
    }

    @Override
    public List<QueryResidentialListInfo> queryResidentialList(QueryResidentialListDto queryResidentialListDto)   {
        List<QueryResidentialListInfo> queryResidentialListInfos = bigdataMapper.queryResidentialList(queryResidentialListDto);
        for (QueryResidentialListInfo queryResidentialListInfo : queryResidentialListInfos) {
            String lineLossRateLess0Platform = queryResidentialListInfo.getLine_loss_rate_less0_platform();
            String lineLossRateLess10Platform = queryResidentialListInfo.getLine_loss_rate_less10_platform();
            String lineLossRateBigger10Platform = queryResidentialListInfo.getLine_loss_rate_bigger10_platform();
            String platformTotal = queryResidentialListInfo.getPlatform_total();
            if(!StringUtils.isEmpty(platformTotal)){
                if(!StringUtils.isEmpty(lineLossRateLess0Platform)){
                    BigDecimal divide = new BigDecimal(lineLossRateLess0Platform).divide(new BigDecimal(platformTotal), 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(2);
                    if(StringUtil.isInteger(divide.toString())){
                        queryResidentialListInfo.setLineLossRateLess0occupation(String.valueOf(divide.intValue()));
                    }else{
                        queryResidentialListInfo.setLineLossRateLess0occupation(String.valueOf(divide));
                    }
                }
                if(!StringUtils.isEmpty(lineLossRateLess10Platform)){
                    BigDecimal divide = new BigDecimal(lineLossRateLess10Platform).divide(new BigDecimal(platformTotal), 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(2);
                    if(StringUtil.isInteger(divide.toString())){
                        queryResidentialListInfo.setLineLossRateLess10occupation(String.valueOf(divide.intValue()));
                    }else{
                        queryResidentialListInfo.setLineLossRateLess10occupation(String.valueOf(divide));
                    }
                }
                if(!StringUtils.isEmpty(lineLossRateBigger10Platform)){
                    BigDecimal divide = new BigDecimal(lineLossRateBigger10Platform).divide(new BigDecimal(platformTotal), 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(2);
                    if(StringUtil.isInteger(divide.toString())){
                        queryResidentialListInfo.setLineLossRateBigger10occupation(String.valueOf(divide.intValue()));
                    }else{
                        queryResidentialListInfo.setLineLossRateBigger10occupation(String.valueOf(divide));
                    }
                }
            }
        }
        return queryResidentialListInfos;
    }

    @Override
    public List<QueryLineLostListInfo> queryLineLostList(QueryLineLostListDto queryLineLostListDto) {
        List<QueryLineLostListInfo> queryLineLostListInfos = bigdataMapper.queryLineLostList(queryLineLostListDto);
        for (QueryLineLostListInfo queryLineLostListInfo : queryLineLostListInfos) {
            String powerSupply = queryLineLostListInfo.getPowerSupply();
            String electLoss = queryLineLostListInfo.getElectLoss();
            if(!StringUtils.isEmpty(powerSupply)&&new BigDecimal(powerSupply).doubleValue()!=0) {
                if(!StringUtils.isEmpty(electLoss)){
                    BigDecimal occupation = new BigDecimal(electLoss).divide(new BigDecimal(powerSupply), 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(2);
                    if (StringUtil.isInteger(occupation.toString())){
                        queryLineLostListInfo.setOccupation(String.valueOf(occupation.intValue()));
                    }else {
                        queryLineLostListInfo.setOccupation(occupation.toString());
                    }
                }
            }

        }
        return queryLineLostListInfos;
    }

    @Override
    public QueryBaseInformationDto queryBaseInformation(Integer id) {
        QueryBaseInformationDto queryBaseInformationDto = new QueryBaseInformationDto();
        queryBaseInformationDto.setId(id);
        bigdataMapper.queryBaseInformation(queryBaseInformationDto);
        return queryBaseInformationDto;
    }

    @Override
    public List<QueryElectTopListInfo> queryElectTopList(Integer id) {

        return bigdataMapper.queryElectTopList(id);
    }

    @Override
    public QueryCategoryInformationInfo queryCategoryInformation(QueryCurrentMonthDto queryCurrentMonthDto) {
        QueryCategoryInformationDB queryCategoryInformationDB = bigdataMapper.queryCategoryInformation(queryCurrentMonthDto);
        QueryCategoryInformationInfo queryCategoryInformationInfo = new QueryCategoryInformationInfo();
        if(queryCategoryInformationDB!=null){
            String item = queryCategoryInformationDB.getItem();
            if (Energy.ELECT.getCode().equals(item)) {
                queryCategoryInformationInfo.setElevator(queryCategoryInformationDB.getPower());
            }
        }
        return queryCategoryInformationInfo;
    }

    @Override
    public QueryCurrentMonthInfo queryCurrentMonth(QueryCurrentMonthDto queryCurrentMonthDto) {
        List<QueryCurrentMonthDB> queryCurrentMonthDBS = bigdataMapper.queryCurrentMonth(queryCurrentMonthDto);
        ImmutableListMultimap<Object, QueryCurrentMonthDB> multimap = Multimaps.index(queryCurrentMonthDBS, new Function<QueryCurrentMonthDB, Object>() {
            @Nullable
            @Override
            public Object apply(@Nullable QueryCurrentMonthDB input) {
                return input.getDevice_type();
            }
        });
        QueryCurrentMonthInfo queryCurrentMonthInfo = new QueryCurrentMonthInfo();
        for (Map.Entry<Object, Collection<QueryCurrentMonthDB>> entry : multimap.asMap().entrySet()) {
            List<String> day = Lists.newArrayList();
            List<String> power = Lists.newArrayList();
            EnergyInfo energyInfo = new EnergyInfo();
            String key = (String) entry.getKey();
            if (DeviceType.WATER_METER.getCode().equals(key)) {
                for (QueryCurrentMonthDB queryCurrentMonthDB : entry.getValue()) {
                    day.add(queryCurrentMonthDB.getDay());
                    power.add(queryCurrentMonthDB.getPower());
                }
                energyInfo.setDay(day.toArray(new String[day.size()]));
                energyInfo.setPower(power.toArray(new String[power.size()]));
                queryCurrentMonthInfo.setWater(energyInfo);
            }
            if (DeviceType.GAS_METER.getCode().equals(key)) {
                for (QueryCurrentMonthDB queryCurrentMonthDB : entry.getValue()) {
                    day.add(queryCurrentMonthDB.getDay());
                    power.add(queryCurrentMonthDB.getPower());
                }
                energyInfo.setDay(day.toArray(new String[day.size()]));
                energyInfo.setPower(power.toArray(new String[power.size()]));
                queryCurrentMonthInfo.setGas(energyInfo);
            }
            if (DeviceType.ELECTRIC_METER.getCode().equals(key)) {
                for (QueryCurrentMonthDB queryCurrentMonthDB : entry.getValue()) {
                    day.add(queryCurrentMonthDB.getDay());
                    power.add(queryCurrentMonthDB.getPower());
                }
                energyInfo.setDay(day.toArray(new String[day.size()]));
                energyInfo.setPower(power.toArray(new String[power.size()]));
                queryCurrentMonthInfo.setElectric(energyInfo);
            }
        }
        return queryCurrentMonthInfo;
    }

    @Override
    public QueryCurrentMonthInfo queryCurrentYear(QueryCurrentMonthDto queryCurrentMonthDto) {
        List<QueryCurrentMonthDB> queryCurrentMonthDBS = bigdataMapper.queryCurrentYear(queryCurrentMonthDto);
        ImmutableListMultimap<Object, QueryCurrentMonthDB> multimap = Multimaps.index(queryCurrentMonthDBS, new Function<QueryCurrentMonthDB, Object>() {
            @Nullable
            @Override
            public Object apply(@Nullable QueryCurrentMonthDB input) {
                return input.getDevice_type();
            }
        });
        QueryCurrentMonthInfo queryCurrentMonthInfo = new QueryCurrentMonthInfo();
        for (Map.Entry<Object, Collection<QueryCurrentMonthDB>> entry : multimap.asMap().entrySet()) {
            List<String> day = Lists.newArrayList();
            List<String> power = Lists.newArrayList();
            EnergyInfo energyInfo = new EnergyInfo();
            String key = (String) entry.getKey();
            if (DeviceType.WATER_METER.getCode().equals(key)) {
                for (QueryCurrentMonthDB queryCurrentMonthDB : entry.getValue()) {
                    day.add(queryCurrentMonthDB.getMonth());
                    power.add(queryCurrentMonthDB.getPower());
                }
                energyInfo.setDay(day.toArray(new String[day.size()]));
                energyInfo.setPower(power.toArray(new String[power.size()]));
                queryCurrentMonthInfo.setWater(energyInfo);
            }
            if (DeviceType.GAS_METER.getCode().equals(key)) {
                for (QueryCurrentMonthDB queryCurrentMonthDB : entry.getValue()) {
                    day.add(queryCurrentMonthDB.getMonth());
                    power.add(queryCurrentMonthDB.getPower());
                }
                energyInfo.setDay(day.toArray(new String[day.size()]));
                energyInfo.setPower(power.toArray(new String[power.size()]));
                queryCurrentMonthInfo.setGas(energyInfo);
            }
            if (DeviceType.ELECTRIC_METER.getCode().equals(key)) {
                for (QueryCurrentMonthDB queryCurrentMonthDB : entry.getValue()) {
                    day.add(queryCurrentMonthDB.getMonth());
                    power.add(queryCurrentMonthDB.getPower());
                }
                energyInfo.setDay(day.toArray(new String[day.size()]));
                energyInfo.setPower(power.toArray(new String[power.size()]));
                queryCurrentMonthInfo.setElectric(energyInfo);
            }
        }
        return queryCurrentMonthInfo;
    }
}
