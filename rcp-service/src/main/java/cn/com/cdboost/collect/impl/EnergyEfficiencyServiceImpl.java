package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.dao.DayEfficiencyMapper;
import cn.com.cdboost.collect.dao.MeterDayPowerMapper;
import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.ChartsQueryVo;
import cn.com.cdboost.collect.dto.param.EnergyEfficiencyQueryVo;
import cn.com.cdboost.collect.dto.param.MeterDayPowerQueryVo;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.ChargingUseDetailed;
import cn.com.cdboost.collect.model.DayEfficiency;
import cn.com.cdboost.collect.model.FeeOnOff;
import cn.com.cdboost.collect.model.MeterDayPower;
import cn.com.cdboost.collect.service.EnergyEfficiencyService;
import cn.com.cdboost.collect.service.OrgService;
import cn.com.cdboost.collect.service.RedisService;
import cn.com.cdboost.collect.util.CNoUtil;
import cn.com.cdboost.collect.util.DateUtil;
import cn.com.cdboost.collect.util.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service("energyEfficiencyService")
public class EnergyEfficiencyServiceImpl extends BaseServiceImpl<MeterDayPower> implements EnergyEfficiencyService {

    @Autowired
    MeterDayPowerMapper meterDayPowerMapper;
    @Autowired
    RedisService redisService;
    @Autowired
    DayEfficiencyMapper dayEfficiencyMapper;
    @Override
    public List<VillageElectricityDto> queryVillageList(EnergyEfficiencyQueryVo queryVo) {
        return meterDayPowerMapper.queryVillageList(queryVo);
    }

    @Override
    public ElectricChartsInfo electricCount(ChartsQueryVo queryVo) {
        if (queryVo.getModel() == null){
            throw new BusinessException("统计类型不能为null");
        }
        List<ElectricChartDto> list = meterDayPowerMapper.electricCount(queryVo);
        List<BigDecimal> yLastData = Lists.newArrayList();
        List<BigDecimal> yThisData = Lists.newArrayList();
        List<String> xData = Lists.newArrayList();
        for (ElectricChartDto electricChartDto : list) {
            yLastData.add(electricChartDto.getyLastData());
            yThisData.add(electricChartDto.getyThisData());
            xData.add(electricChartDto.getxData());
        }

        ElectricChartsDto electricChartsDto = new ElectricChartsDto();
        electricChartsDto.setxData(xData);
        electricChartsDto.setyLastData(yLastData);
        electricChartsDto.setyThisData(yThisData);

        ElectricChartsInfo electricChartsInfo = new ElectricChartsInfo();
        electricChartsInfo.setListCount(electricChartsDto);
        return electricChartsInfo;
    }

    @Override
    public List<ElectricityCountDto> queryDeviceList(EnergyEfficiencyQueryVo queryVo) {

        Condition condition = new Condition(DayEfficiency.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("orgNo",queryVo.getOrgNo());
        criteria.andBetween("analDate",queryVo.getBeginDate(),queryVo.getEndDate());
        // 设置分页信息
        PageHelper.startPage(queryVo.getPageNumber(),queryVo.getPageSize(),"anal_date desc");
        List<DayEfficiency> dayEfficiencies = dayEfficiencyMapper.selectByCondition(condition);
        // 设置分页总条数
        PageInfo<DayEfficiency> pageInfo = new PageInfo<>(dayEfficiencies);
        List<ElectricityCountDto> electricityCountDtos = Lists.newArrayList();
        for (DayEfficiency dayEfficiency : dayEfficiencies) {
            ElectricityCountDto electricityCountDto = new ElectricityCountDto();
            BeanUtils.copyProperties(dayEfficiency,electricityCountDto);
            electricityCountDto.setCountDate(DateUtil.formatDay(dayEfficiency.getAnalDate()));
            electricityCountDtos.add(electricityCountDto);
        }
        return electricityCountDtos;
    }

    @Override
    public List<EnergyEfficiencyDetailDto> queryDeviceDetail(EnergyEfficiencyQueryVo queryVo) {
        queryVo.setBeginDate(queryVo.getCountDate());
        queryVo.setEndDate(queryVo.getCountDate());
        List<EnergyEfficiencyDetailDto> energyEfficiencyDetailDtos = meterDayPowerMapper.queryDeviceDetail(queryVo);
        for (EnergyEfficiencyDetailDto energyEfficiencyDetailDto : energyEfficiencyDetailDtos) {
            String cno = CNoUtil.getNo(energyEfficiencyDetailDto.getMeterNo());
            energyEfficiencyDetailDto.setMeterNo(cno);
        }
        return energyEfficiencyDetailDtos;
    }

    @Override
    public List<EnergyEfficiencyDetailListDto> queryLastPowerDetail(MeterDayPowerQueryVo queryVo) {
        if (StringUtil.isEmpty(queryVo.getCountDate())){
            throw new BusinessException("时间不能为空");
        }
        if (StringUtil.isEmpty(queryVo.getOrgNo())){
            throw new BusinessException("小区标识不能为空");
        }
        if (StringUtil.isEmpty(queryVo.getMeterUserNo())){
            throw new BusinessException("表计户号不能为空");
        }
        if (StringUtil.isEmpty(queryVo.getUsePowerType())){
            throw new BusinessException("用电类型不能为空");
        }
        List<EnergyEfficiencyDetailListDto> energyEfficiencyDetailListDtos = meterDayPowerMapper.queryLastPowerDetail(queryVo);
        Map<String,EnergyEfficiencyDetailListDto> map = Maps.newHashMap();
        for (EnergyEfficiencyDetailListDto energyEfficiencyDetailListDto : energyEfficiencyDetailListDtos) {
            map.put(energyEfficiencyDetailListDto.getTime(),energyEfficiencyDetailListDto);
        }
        List<EnergyEfficiencyDetailListDto> res = Lists.newArrayList();
        //后台组建最近15天数据
        if (energyEfficiencyDetailListDtos.size() > 0){
            for (int i = 0; i<15 ;i++){
                EnergyEfficiencyDetailListDto energyEfficiencyDetailListDto = new EnergyEfficiencyDetailListDto();
                String date = DateUtil.getPastDate(i,queryVo.getCountDate());
                energyEfficiencyDetailListDto.setTime(date);
                if (map.get(date) != null){
                    BigDecimal readValue = map.get(date).getReadValue();
                    if (readValue != null && readValue.longValue()== -1 || readValue.longValue()== 0){
                        res.add(energyEfficiencyDetailListDto);
                    }else {
                        res.add(map.get(date));
                    }
                }else {
                    res.add(energyEfficiencyDetailListDto);
                }
            }
        }else {
            for (int i = 0; i<15 ;i++){
                EnergyEfficiencyDetailListDto energyEfficiencyDetailListDto = new EnergyEfficiencyDetailListDto();
                String date = DateUtil.getPastDate(i,queryVo.getCountDate());
                energyEfficiencyDetailListDto.setTime(date);
                res.add(energyEfficiencyDetailListDto);
            }
        }
        return res;
    }

    @Override
    public Integer queryMeterNum(ChartsQueryVo queryVo) {
        Integer meterNum = meterDayPowerMapper.queryMeterNum(queryVo);
        return meterNum;
    }
}
