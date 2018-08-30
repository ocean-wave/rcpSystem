package cn.com.cdboost.collect.impl;


import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.DeviceEventMapper;
import cn.com.cdboost.collect.dao.MeterCollectDataMapper;
import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.dto.response.*;
import cn.com.cdboost.collect.model.MeterCollectData;
import cn.com.cdboost.collect.service.MeterCollectDataService;
import cn.com.cdboost.collect.util.CNoUtil;
import cn.com.cdboost.collect.util.DateUtil;
import cn.com.cdboost.collect.util.MathUtil;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 抄表数据服务接口实现类
 */
@Service("meterCollectDataService")
public class MeterCollectDataServiceImpl extends BaseServiceImpl<MeterCollectData> implements MeterCollectDataService {

    @Autowired
    private MeterCollectDataMapper meterCollectDataMapper;
    @Autowired
    private DeviceEventMapper deviceEventMapper;
    @Override
    public List<CustomerData4Month> getDataList(RealTimeDataListParam realTimeDataListParam) {
        return meterCollectDataMapper.getDataList(realTimeDataListParam);
    }
    @Override
    public List<MeterCollectDataInfo> listRealMeterCollectData(RealMeterCollectQueryVo queryVo) {
        return meterCollectDataMapper.listRealMeterCollectData(queryVo);
    }
    @Override
    public List<MeterCollectDataInfo> listRealMeterCollectDataNew(RealMeterCollectQueryNewVo queryVo) {
        return meterCollectDataMapper.listRealMeterCollectDataNew(queryVo);
    }
    @Override
    public List<MeterCollectImpDataInfo> listImpRealTimeData(RealMeterCollectQueryVo queryVo) {
        List<MeterCollectImpDataInfo> impDataInfos = meterCollectDataMapper.listRealMeterCollectImpData(queryVo);
        return impDataInfos;
    }
    @Override
    public List<MeterCollectImpDataInfo> listImpRealTimeDataNew(RealMeterCollectQueryNewVo queryVo) {
        List<MeterCollectImpDataInfo> impDataInfos = meterCollectDataMapper.listRealMeterCollectImpDataNew(queryVo);
        return impDataInfos;
    }
    @Override
    public List<MeterCollectDataFailInfo> listRealMeterCollectFailData(RealMeterCollectFailQueryVo queryVo) {
        List<MeterCollectDataFailInfo> failInfos = meterCollectDataMapper.listRealMeterCollectFailData(queryVo);
        if (!CollectionUtils.isEmpty(failInfos)) {
            for (MeterCollectDataFailInfo failInfo : failInfos) {
                String jzqNo = CNoUtil.getJzqNoByJzqCno(failInfo.getJzqCno());
                failInfo.setJzqNo(jzqNo);
            }
        }
        return failInfos;
    }

    @Override
    public List<CollectRecordDTO> listReadCollectRecord(Integer userId, String deviceType) {
        return meterCollectDataMapper.listReadCollectRecord(userId, deviceType);
    }

    @Override
    public List<CollectDetialDTO> listReadCollectRecordDetail(ReadCollectRecordDetailQueryVo queryVo) {
        return meterCollectDataMapper.listReadCollectRecordDetail(queryVo);
    }

    @Override
    public String sumFreeForDay() {
        return meterCollectDataMapper.sumFreeForDay();
    }

    @Override
    public List<CollectDataGetInfo> listMeterCollectData(CollectDataGetQueryVo queryVo) {
        return meterCollectDataMapper.listMeterCollectData(queryVo);
    }

    @Override
    public List<ImpCollectDataGetInfo> listImpCollectHistoryData(ImpCollectDataGetQueryVo queryVo) {
        return meterCollectDataMapper.listImpCollectHistoryData(queryVo);
    }

    @Override
    public List<CollectDataDownInfo> collectDataDown(CollectDataGetQueryVo queryVo) {
        return meterCollectDataMapper.collectDataDown(queryVo);
    }

    @Override
    public List<ArrearageCustomer> listArrearageCustomers(ArrearageCustomersQueryVo queryVo) {
        return meterCollectDataMapper.listArrearageCustomers(queryVo);
    }

    @Override
    public List<CustomerData4Month> getDataForMonth(String customerNo, String deviceCno, Integer yearMonth) {
        String deviceType = deviceCno.substring(0, 2);
        return meterCollectDataMapper.getDataForMonth(customerNo, deviceType, yearMonth, deviceCno);
    }

    @Override
    public void getAlarmUserCount(Map<String, Object> map) {
        meterCollectDataMapper.getAlarmUserCount(map);
    }

    @Override
    public HistogramBuildingData statisticsByBuildingNo(HistogramDataQueryVo queryVo) {
        return null;
    }

    @Override
    public Map getCustomElechart(ElecDataVo elecDataVo) {
        CustomElectReturnDTO customElectReturnDTO=new CustomElectReturnDTO();
        Map map= Maps.newHashMap();
        List<CustomElectDTO> customElechart = meterCollectDataMapper.getCustomElechart(elecDataVo);
        ImmutableMap<String, CustomElectDTO> dtoMap;
        if(!CollectionUtils.isEmpty(customElechart)&&(customElechart.get(0).getTransformerNo()!=null||customElechart.get(0).getBuildingNo()!=null)){
            if("0".equals(elecDataVo.getTransformerNo())){
                dtoMap = Maps.uniqueIndex(customElechart, new Function<CustomElectDTO, String>() {
                    @Nullable
                    @Override
                    public String apply(@Nullable CustomElectDTO customElectDTO) {
                        return customElectDTO.getBuildingNo();
                    }

                });
            }else {
                dtoMap = Maps.uniqueIndex(customElechart, new Function<CustomElectDTO, String>() {
                    @Nullable
                    @Override
                    public String apply(@Nullable CustomElectDTO customElectDTO) {
                        return customElectDTO.getTransformerNo();
                    }

                });
            }
            ArrayList arrayList= Lists.newArrayList();
            ArrayList dataList= Lists.newArrayList();
            for(String key:dtoMap.keySet()){
                arrayList.add(key);
                CustomElectDTO customElectDTO=dtoMap.get(key);
                dataList.add(customElectDTO.getElecDatas());
            }
            String[] xData;
            xData= (String[]) arrayList.toArray(new String[arrayList.size()]);
            BigDecimal[] yData;
            yData= (BigDecimal[]) dataList.toArray(new BigDecimal[dataList.size()]);
            customElectReturnDTO.setxData(xData);
            customElectReturnDTO.setyData(yData);
            if("0".equals(elecDataVo.getTransformerNo())){
                map.put("building",customElectReturnDTO);
            }else {
                map.put("transform",customElectReturnDTO);
            }
            return map;
        }else{
            customElectReturnDTO.setxData(new String[]{});
            customElectReturnDTO.setyData(new BigDecimal[]{});
            if("0".equals(elecDataVo.getTransformerNo())){
                map.put("building",customElectReturnDTO);
            }else {
                map.put("transform",customElectReturnDTO);
            }
            return  map;
        }
    }

    @Override
    public List getCustomEledata(ElecDataVo elecDataVo) {
        List<CustomElectDTO> customElechart = meterCollectDataMapper.getCustomElecData(elecDataVo);
        return customElechart;
    }

    @Override
    public CollectDataPerDay getCollectDataForDay(CollectDataForPerDayQueryVo queryVo) {
        List<List<?>> dataForPerDay = meterCollectDataMapper.getCollectDataForPerDay(queryVo);
        // 柱状图数据
        List<PerDayData4Histogram> histogramsList = (List<PerDayData4Histogram>) dataForPerDay.get(0);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        CollectDataPerDay perDay = new CollectDataPerDay();
        if (!CollectionUtils.isEmpty(histogramsList)) {
            int size = histogramsList.size();
            String[] xData = new String[size];
            BigDecimal[] yData = new BigDecimal[size];
            for (int i = 0; i < histogramsList.size(); i++) {
                PerDayData4Histogram histogram = histogramsList.get(i);
                Date calcData = histogram.getCalcData();
                xData[i] = format.format(calcData);
                BigDecimal eqValue = histogram.getEqValue();
                yData[i] = MathUtil.setPrecision(eqValue);
            }
            perDay.setxData(xData);
            perDay.setyData(yData);
        } else {
            // 处理数据为空的情况
            String startTime = queryVo.getsTime();
            int day = DateUtil.dayNumBetween(startTime, queryVo.geteTime());
            int length = day + 1;
            String[] xData = new String[length];
            BigDecimal[] yData = new BigDecimal[length];
            for (int i = 0; i < length; i++) {
                String date = DateUtil.dayAfter(startTime, i);
                xData[i] = date;
            }

            perDay.setxData(xData);
            perDay.setyData(yData);
        }

        // 列表数据
        List<PerDayData4List> perDayData4Lists = (List<PerDayData4List>) dataForPerDay.get(1);
        if (!CollectionUtils.isEmpty(perDayData4Lists)) {
            for (PerDayData4List list : perDayData4Lists) {
                BigDecimal balance = list.getBalance();
                list.setBalance(MathUtil.setPrecision(balance));
                BigDecimal pr0 = list.getPr0();
                list.setPr0(MathUtil.setPrecision(pr0));
                BigDecimal payMoney = list.getPayMoney();
                list.setPayMoney(MathUtil.setPrecision(payMoney));
            }
        }
        perDay.setListData(perDayData4Lists);
        return perDay;
    }
}
