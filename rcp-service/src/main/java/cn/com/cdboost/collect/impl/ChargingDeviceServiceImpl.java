package cn.com.cdboost.collect.impl;

import cn.com.cdboost.collect.cache.OrgCacheVo;
import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.constant.ChargeConstant;
import cn.com.cdboost.collect.constant.DictCodeConstant;
import cn.com.cdboost.collect.dao.*;
import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.enums.ChargingEnum;
import cn.com.cdboost.collect.enums.DeviceType;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.*;
import cn.com.cdboost.collect.service.*;
import cn.com.cdboost.collect.util.*;
import cn.com.cdboost.collect.vo.Result;
import com.example.clienttest.client.AFN19Object;
import com.example.clienttest.client.AFN21Object;
import com.example.clienttest.client.AFN22Object;
import com.example.clienttest.client.AFN23Object;
import com.example.clienttest.clientfuture.ClientManager;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Function;
import com.google.common.collect.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * 充电设备业务层
 */
@Service
public class ChargingDeviceServiceImpl extends BaseServiceImpl<ChargingDevice> implements ChargingDeviceService {

    @Autowired
    private ChargingDeviceMapper chargingDeviceMapper;
    @Autowired
    private ChargingUseDetailedMapper chargingUseDetailedMapper;
    @Autowired
    private ChargingUseDetailedService chargingUseDetailedService;
    @Autowired
    private DictItemService dictItemService;
    @Autowired
    private ChargingProjectService chargingProjectService;
    @Autowired
    private DeviceMeterParamMapper deviceMeterParamMapper;
    @Autowired
    private CustomerDevMapService customerDevMapService;
    @Autowired
    private ChargingCstMapper chargingCstMapper;
    @Autowired
    private ChargingPayMapper chargingPayMapper;
    @Autowired
    private ChargingWithdrawCashMapper chargingWithdrawCashMapper;
    @Autowired
    private ChargingCardListMapper chargingCardListMapper;
    @Autowired
    private ChargingCardMapper chargingCardMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private ChargingProjectMapper chargingProjectMapper;

    @Override
    public DeviceUseCountListDto deviceUseCountDetail(ChargerDeviceQueryVo queryVo) {
        int dayNumBetween = DateUtil.dayNumBetween(queryVo.getStartDate(), queryVo.getEndDate());
        //查询列表
        List<DeviceUseCountListDto> list = chargingDeviceMapper.deviceUseCountList(queryVo);
        //设备使用率
        for (DeviceUseCountListDto deviceUseCountListDto : list) {
            int a = deviceUseCountListDto.getChargingNum() * 100;
            int b = 2 * (dayNumBetween +1);
            BigDecimal projectDeviceUseRate = new BigDecimal((float)a/b).setScale(2, BigDecimal.ROUND_HALF_UP);
            deviceUseCountListDto.setProjectDeviceUseRate(projectDeviceUseRate);
        }
        return list.get(0);
    }

    @Override
    public DeviceUseCountDto deviceUseCountList(ChargerDeviceQueryVo queryVo) {
        if (StringUtil.isEmpty(queryVo.getProjectGuid())){
            throw new BusinessException("projectGuid不能为空");
        }
        int dayNumBetween = DateUtil.dayNumBetween(queryVo.getStartDate(), queryVo.getEndDate());
        DeviceUseCountDto deviceUseCountDto = new DeviceUseCountDto();
        //查询列表
        List<DeviceUseCountListDto> list = chargingDeviceMapper.deviceUseCountList(queryVo);
        //设备使用率
        for (DeviceUseCountListDto deviceUseCountListDto : list) {
            int a = deviceUseCountListDto.getChargingNum() * 100;
            int b = 2 * (dayNumBetween +1);
            BigDecimal projectDeviceUseRate = new BigDecimal((float)a/b).setScale(2, BigDecimal.ROUND_HALF_UP);
            deviceUseCountListDto.setProjectDeviceUseRate(projectDeviceUseRate);
        }
        //查询汇总
        DeviceCountStatic deviceCountStatic = chargingDeviceMapper.countDeviceTotal(queryVo);
        if (deviceCountStatic == null){
            deviceCountStatic = new DeviceCountStatic();
        }
        //查询总记录数
        Integer total = chargingDeviceMapper.queryDeviceUseListTotal(queryVo);
        deviceUseCountDto.setList(list);
        deviceUseCountDto.setStatistics(deviceCountStatic);
        queryVo.setTotal(total.longValue());
        return deviceUseCountDto;
    }

    @Override
    public ProjectUseCountDto projectUseCountList(ChargerSchemeQueryVo queryVo) {
        queryVo.setPageIndex((queryVo.getPageNumber()-1) * queryVo.getPageSize());
        int dayNumBetween = DateUtil.dayNumBetween(queryVo.getBeginTime(), queryVo.getEndTime());
        ProjectUseCountDto projectUseCountDto = new ProjectUseCountDto();
        //查询列表
        List<ProjectUseCountListDto> projectUseCountListDtos =  chargingProjectMapper.queryProjectUseCountList(queryVo);
        for (ProjectUseCountListDto projectUseCountListDto : projectUseCountListDtos) {
            if (projectUseCountListDto.getDeviceNum() != null && projectUseCountListDto.getDeviceNum() != 0){
                int a = projectUseCountListDto.getChargingNum() * 100;
                int b = projectUseCountListDto.getDeviceNum() * (dayNumBetween +1) * 2;
                BigDecimal projectDeviceUseRate = new BigDecimal((float)a/b).setScale(2, BigDecimal.ROUND_HALF_UP);
                projectUseCountListDto.setProjectDeviceUseRate(projectDeviceUseRate);
            }
        }
        //查询汇总
        ProjectCountStatic projectCountStatic = chargingProjectMapper.countProjectTotal(queryVo);
        if (projectCountStatic == null){
            projectCountStatic = new ProjectCountStatic();
        }
        //查询总记录数
        Integer total = chargingProjectMapper.queryProjectUseListTotal(queryVo);
        projectUseCountDto.setList(projectUseCountListDtos);
        projectUseCountDto.setStatistics(projectCountStatic);
        queryVo.setTotal(total.longValue());
        return projectUseCountDto;
    }

    @Override
    public PageInfo withdrawCashList(WithdrawCashListDto withdrawCashListDto) {
        PageHelper.startPage(withdrawCashListDto.getPageNumber(),withdrawCashListDto.getPageSize());
        List<WithdrawCashListInfo> withdrawCashListInfo = chargingWithdrawCashMapper.withdrawCashList(withdrawCashListDto);
        PageInfo pageInfo =new PageInfo(withdrawCashListInfo);
        return pageInfo;
    }

    @Override
    public PageInfo chargeRecordList(ChargeRecordListDto chargeRecordListDto) {
        PageHelper.startPage(chargeRecordListDto.getPageNumber(),chargeRecordListDto.getPageSize());
        List<ChargeRecordListInfo> chargeRecordListInfo = chargingPayMapper.chargeRecordList(chargeRecordListDto);
        PageInfo pageInfo =new PageInfo(chargeRecordListInfo);
        return pageInfo;
    }

    @Override
    public PageInfo useRecordList(UseRecordListDto useRecordListDto) {
        PageHelper.startPage(useRecordListDto.getPageNumber(),useRecordListDto.getPageSize());
        List<UseRecordListInfo> useRecordListInfo = chargingUseDetailedMapper.useRecordList(useRecordListDto);
        PageInfo pageInfo =new PageInfo(useRecordListInfo);
        return pageInfo;
    }

    @Override
    public Integer setCustomerState(String customerGuid, Integer customerState) {
        ChargingCst chargingCst=new ChargingCst();
        chargingCst.setCustomerState(customerState);
        Condition condition=new Condition(ChargingCst.class);
        Example.Criteria criteria=condition.createCriteria();
        criteria.andEqualTo("customerGuid",customerGuid);
        int i = chargingCstMapper.updateByConditionSelective(chargingCst, condition);
        return i;
    }

    @Override
    public ChargeCustomerInfoDetailInfo customerInfoDetail(String customerGuid) {
        ChargeCustomerInfoDetailInfo chargeCustomerInfoDetailInfo = chargingUseDetailedMapper.customerInfoDetail(customerGuid);
        return chargeCustomerInfoDetailInfo;
    }

    @Override
    public PageInfo customerInfoList(CustomerInfoListDto customerInfoListDto, Integer userId)  {
        List<Long> orgNoList = Lists.newArrayList();
        List<String> proGuids = Lists.newArrayList();
        if (customerInfoListDto.getNodeType() != null){//判断是否选择点击树
            if (customerInfoListDto.getNodeType() ==1){//判断点击的树是组织还是项目：1-组织，2-项目
                orgNoList.add(Long.parseLong(customerInfoListDto.getNodeId()));
                List<OrgCacheVo> orgCacheVos = redisService.queryChildOrg(Long.parseLong(customerInfoListDto.getNodeId()));
                for (OrgCacheVo orgCacheVo : orgCacheVos) {
                    orgNoList.add(orgCacheVo.getOrgNo());
                }
                Condition condition = new Condition(ChargingProject.class);
                Example.Criteria criteria = condition.createCriteria();
                criteria.andIn("orgNo",orgNoList);
                criteria.andEqualTo("isDel", ChargeConstant.IsDel.NOTDEL);
                List<ChargingProject> chargingProjects = chargingProjectMapper.selectByCondition(condition);
                for (ChargingProject chargingProject : chargingProjects) {
                    proGuids.add(chargingProject.getProjectGuid());
                }
            }else if (customerInfoListDto.getNodeType() == 2){
                proGuids.add(customerInfoListDto.getNodeId());
            }
        }else {
            //查询用户拥有的组织数据权限
            Set<Long> dataOrgNos = redisService.queryUserOrgNoByUserId(userId);
            Condition condition = new Condition(ChargingProject.class);
            Example.Criteria criteria = condition.createCriteria();
            criteria.andIn("orgNo",dataOrgNos);
            criteria.andEqualTo("isDel", ChargeConstant.IsDel.NOTDEL);
            List<ChargingProject> chargingProjects = chargingProjectMapper.selectByCondition(condition);
            for (ChargingProject chargingProject : chargingProjects) {
                proGuids.add(chargingProject.getProjectGuid());
            }
        }

        customerInfoListDto.setProGuids(proGuids);
        PageHelper.startPage(customerInfoListDto.getPageNumber(),customerInfoListDto.getPageSize());
        List<CustomerInfoListInfo> customerInfoListInfo = chargingUseDetailedMapper.customerInfoList(customerInfoListDto);
        PageInfo pageInfo =new PageInfo(customerInfoListInfo);
        return pageInfo;
    }

    @Override
    public DayLineLossInfo dayLineLoss(DayLineLossDto dayLineLossDto) throws ParseException {
        DayLineLossInfo dayLineLossInfo=new DayLineLossInfo();
        String cNo = CNoUtil.CreateCNo(dayLineLossDto.getDeviceType(), dayLineLossDto.getDeviceNo());
        dayLineLossDto.setDeviceCno(cNo);
        CustomerDevMap customerDevMap = customerDevMapService.queryByCno(cNo);
        ImportantCurveVo ImportantCurveVo=new ImportantCurveVo();
        ImportantCurveVo.setCustomerNo(customerDevMap.getCustomerNo());
        ImportantCurveVo.setMeterUserNo(customerDevMap.getMeterUserNo());
        ImportantCurveVo.setDeviceType(customerDevMap.getDeviceType());
        ImportantCurveVo.setStartTime(dayLineLossDto.getDate()+" 00:00:00");
        ImportantCurveVo.setEndTime(DateUtil.getEndTime(dayLineLossDto.getDate()));
        ImportantCurveVo.setModel("3");
        List<ImportantCurveDerailDTO> importantCurveDerailDTOS = deviceMeterParamMapper.queryImportCollection(ImportantCurveVo);
        ArrayList importlist=Lists.newArrayList();
        for (ImportantCurveDerailDTO importantCurveDerailDTO : importantCurveDerailDTOS) {
            DayLineLossInfoimport dayLineLossInfoimport=new DayLineLossInfoimport();
            dayLineLossInfoimport.setDate(importantCurveDerailDTO.getCollectDate());
            dayLineLossInfoimport.setCurrentA(importantCurveDerailDTO.getCurrentA());
            dayLineLossInfoimport.setVoltageA(importantCurveDerailDTO.getVoltageA());
            importlist.add(dayLineLossInfoimport);
        }
        dayLineLossInfo.setImportlist(importlist);
        List<DayLineLossInfo> dayLineLossInfos = chargingDeviceMapper.dayLineLoss(dayLineLossDto);
        ArrayList lineList=Lists.newArrayList();
        for (DayLineLossInfo lineLossInfo : dayLineLossInfos) {
            lineList.addAll(lineLossInfo.getList());
        }
        dayLineLossInfo.setList(lineList);
        return dayLineLossInfo;
    }

    @Override
    public TotalLineLossInfo totalLineLoss(TotalLineLossDto totalLineLossDto) throws ParseException {
        TotalLineLossInfo totalLineLossInfo=new TotalLineLossInfo();
        if(!StringUtils.isEmpty(totalLineLossDto.getSortName())&&!StringUtils.isEmpty(totalLineLossDto.getSortOrder())){
            PageHelper.startPage(totalLineLossDto.getPageNumber(),totalLineLossDto.getPageSize(),totalLineLossDto.getSortName()+" "+totalLineLossDto.getSortOrder());
        }else {
            PageHelper.startPage(totalLineLossDto.getPageNumber(),totalLineLossDto.getPageSize());
        }
        List<TotalLineLossInfo> totalLineLossInfoList;
        List<TotalLineLossInfo> totalLineLossInfoCurve;
        if(DateUtil.compareDateTime(totalLineLossDto.getStartDate(),totalLineLossDto.getEndDate())==0) {
            if (DateUtil.compareDateTime(totalLineLossDto.getEndDate(), DateUtil.CurrentDate()) == 0) {
                totalLineLossInfoList = chargingDeviceMapper.totalLineLossListInTime(totalLineLossDto);
                totalLineLossInfoCurve = chargingDeviceMapper.totalLineLossCurveInTime(totalLineLossDto);
            }else{
                totalLineLossInfoList = chargingDeviceMapper.totalLineLossListFreeze(totalLineLossDto);
                totalLineLossInfoCurve = chargingDeviceMapper.totalLineLossCurveFreeze(totalLineLossDto);
            }
        }
        else{
            if(DateUtil.compareDateTime(totalLineLossDto.getEndDate(), DateUtil.CurrentDate()) != 0){
                totalLineLossInfoList = chargingDeviceMapper.totalLineLossListFreeze(totalLineLossDto);
                totalLineLossInfoCurve = chargingDeviceMapper.totalLineLossCurveFreeze(totalLineLossDto);
            }else {
                totalLineLossDto.setEndDateLast(DateUtil.getInputlastDayDate(totalLineLossDto.getEndDate()));
                totalLineLossInfoList = chargingDeviceMapper.totalLineLossListFreezeInTime(totalLineLossDto);
                totalLineLossInfoCurve = chargingDeviceMapper.totalLineLossCurveFreezeInTime(totalLineLossDto);
            }

        }
        PageInfo pageInfo=new PageInfo(totalLineLossInfoList);
        totalLineLossDto.setTotal(pageInfo.getTotal());
        ArrayList lineList=Lists.newArrayList();
        BigDecimal meterElect=BigDecimal.ZERO;
        BigDecimal deviceElect=BigDecimal.ZERO;
        BigDecimal lossElect=BigDecimal.ZERO;
        BigDecimal lossRate;
        LineLossStatistics lineLossStatistics=new LineLossStatistics();
        for (TotalLineLossInfo lineLossInfo : totalLineLossInfoList) {
            lineList.addAll(lineLossInfo.getList());
            String meterElect1 = lineLossInfo.getList().get(0).getMeterElect();
            String deviceElect1 = lineLossInfo.getList().get(0).getDeviceElect();
            String lossElect1 = lineLossInfo.getList().get(0).getLossElect();
            if(!StringUtils.isEmpty(meterElect1)){
               if ("-1".equals(meterElect1)){
                   meterElect1="0";
               }
                meterElect=meterElect.add(new BigDecimal(meterElect1));
            }
            if(!StringUtils.isEmpty(deviceElect1)){
                if ("-1".equals(deviceElect1)){
                    deviceElect1="0";
                }
                deviceElect=deviceElect.add(new BigDecimal(deviceElect1));
            }
            if(!StringUtils.isEmpty(lossElect1)){
                if ("-1".equals(lossElect1)){
                    lossElect1="0";
                }
                lossElect=lossElect.add(new BigDecimal(lossElect1));
            }
        }
        totalLineLossInfo.setList(lineList);
        lineLossStatistics.setMeterElect(String.valueOf(meterElect));
        lineLossStatistics.setDeviceElect(String.valueOf(deviceElect));
        lineLossStatistics.setLossElect(String.valueOf(lossElect));
        if(meterElect.doubleValue()!=0){
            lossRate=lossElect.divide(meterElect,4,BigDecimal.ROUND_HALF_UP);
            lineLossStatistics.setLossRate(String.valueOf(lossRate.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP)));
        }
        ArrayList lineLossCurve=Lists.newArrayList();
        for (TotalLineLossInfo lineLossInfo : totalLineLossInfoCurve) {
            lineLossCurve.addAll(lineLossInfo.getLoss());
        }
        totalLineLossInfo.setLoss(lineLossCurve);
        totalLineLossInfo.setStatistics(lineLossStatistics);
        return totalLineLossInfo;
    }

    @Override
    public List<ChargingDeviceDto> deviceList(ChargerDeviceQueryVo queryVo, Integer userId) {
        Set<Long> dataOrgNos = redisService.queryUserOrgNoByUserId(userId);
        List list = Lists.newArrayList(dataOrgNos);
        queryVo.setOrgNoList(list);
        //分页查询所有设备
        Integer pageIndex=(queryVo.getPageNumber()-1)*queryVo.getPageSize();
        queryVo.setPageIndex(pageIndex);
        List<ChargingDeviceDto> chargingDeviceDtos = chargingDeviceMapper.deviceList(queryVo);
        List<String> deviceNos = Lists.newArrayList();
        for (ChargingDeviceDto chargingDeviceDto : chargingDeviceDtos) {
            deviceNos.add(chargingDeviceDto.getDeviceNo());
        }

        //查字典表
        List<DictItem> dictCode = dictItemService.findByDictCode(DictCodeConstant.COMMOTHED.getDictCode());

        // 转map
        Map<String,String> dictMap = new HashMap<>();
        for (DictItem dictItem : dictCode) {
            dictMap.put(dictItem.getDictItemValue(),dictItem.getDictItemName());
        }

        //赋值给前端需要的对象
        for (ChargingDeviceDto chargingDeviceDto:chargingDeviceDtos){
            String comMethodName = dictMap.get(String.valueOf(chargingDeviceDto.getComMethod()));
            chargingDeviceDto.setComMethodName(comMethodName);
        }
        //查询设备下的所有端口
        if (CollectionUtils.isEmpty(deviceNos)){//判断是否有设备
            return null;
        }
        List<ChargingDevicePortDto> chargingDevicePortDtos = chargingDeviceMapper.deviceAndPortList(deviceNos);
        ImmutableListMultimap<String, ChargingDevicePortDto> multimap = Multimaps.index(chargingDevicePortDtos, new Function<ChargingDevicePortDto, String>() {
            @Nullable
            @Override
            public String apply(@Nullable ChargingDevicePortDto chargingDeviceDto) {
                return chargingDeviceDto.getDeviceNo();
            }
        });
        for (ChargingDeviceDto chargingDeviceDto : chargingDeviceDtos) {
            ImmutableList<ChargingDevicePortDto> chargingDevicePortDtos1 = multimap.get(chargingDeviceDto.getDeviceNo());
            for (ChargingDevicePortDto chargingDevicePortDto : chargingDevicePortDtos1) {
                if ("1".equals(chargingDevicePortDto.getPort())){
                    chargingDeviceDto.setRunState1(chargingDevicePortDto.getRunState());
                }else if ("2".equals(chargingDevicePortDto.getPort())){
                    chargingDeviceDto.setRunState2(chargingDevicePortDto.getRunState());
                }
            }
        }
        //查询总数
        Integer total = chargingDeviceMapper.queryTotal(queryVo);
        queryVo.setTotal(total.longValue());
        return chargingDeviceDtos;
    }

    @Override
    public List<ChargingUseDetailedDto> deviceDetialUseList(ChargerDeviceQueryVo queryVo) {
        /*if (StringUtil.isEmpty(queryVo.getChargingPlieGuid())){
            throw new BusinessException("ChargingPlieGuid不能为空");
        }*/
        queryVo.setStartDate(queryVo.getStartDate()+" 00:00:00");
        queryVo.setEndDate(queryVo.getEndDate() + " 23:59:59");
        //分页查询使用列表
        Integer pageIndex=(queryVo.getPageNumber()-1)*queryVo.getPageSize();
        queryVo.setPageIndex(pageIndex);
        List<ChargingUseDetailedDto> chargingDevlogDtos = chargingUseDetailedMapper.deviceDetialUseList(queryVo);
        /*for (int i = 0; i<chargingDevlogDtos.size(); i++){
            if (BigDecimal.ZERO.compareTo(chargingDevlogDtos.get(i).getPayMoney()) == 0){
                chargingDevlogDtos.get(i).setPayCategory(ChargingEnum.MONTH_PAYCATEGORY.getKey());
            } else {
                chargingDevlogDtos.get(i).setPayCategory(ChargingEnum.TEMP_PAYCATEGORY.getKey());
            }
        }*/
        //查询总数
        Integer total = chargingUseDetailedMapper.queryTotal(queryVo);
        queryVo.setTotal(total.longValue());
        return chargingDevlogDtos;
    }

    @Override
    @Transactional
    public void addDevice(ChargingDevice chargingDevice, Integer userId) {
        //根据deviceNo查询数据库
        ChargingDevice queryVo = new ChargingDevice();
        queryVo.setDeviceNo(chargingDevice.getDeviceNo());
        queryVo.setCommNo(chargingDevice.getCommNo());
        List<ChargingDevice> list = chargingDeviceMapper.select(queryVo);
        //判断是否已存在此deviceNo
        if (list.size() > 0){
            for (ChargingDevice device : list) {
                if(device.getIsDel() == 0){
                    throw new BusinessException("设备编号或通信编号不能重复");
                }
            }
        }
        //创建总表cno
        String meterCno = CNoUtil.CreateCNo(DeviceType.ELECTRIC_METER.getCode(), chargingDevice.getMeterNo());
        chargingDevice.setMeterCno(meterCno);
        /*// 暂时不需要前端传值，后期改
        chargingDevice.setProjectGuid(chargingProjectService.queryAllProject().get(0).getProjectGuid());*/
        chargingDevice.setCreateUserId(userId.longValue());
        chargingDevice.setChargingPlieGuid(UuidUtil.getUuid());
        chargingDevice.setOnline(1);
        chargingDevice.setPort("1");
        //数据库写入1号端口
        chargingDeviceMapper.insertSelective(chargingDevice);
        chargingDevice.setId(null);
        chargingDevice.setPort("2");
        chargingDevice.setChargingPlieGuid(UuidUtil.getUuid());
        //数据库写入2号端口
        chargingDeviceMapper.insertSelective(chargingDevice);
    }

    @Override
    public void addCardList(ChargingDevice chargingDevice, Result result) {
        //调用cardlist存储过程
        ChargerICCardListParam chargerICCardListParam = new ChargerICCardListParam();
        chargerICCardListParam.setProjectGuid(chargingDevice.getProjectGuid());
        chargerICCardListParam.setDeviceNo(chargingDevice.getDeviceNo());
        chargerICCardListParam.setCommNo(chargingDevice.getCommNo());
        chargingCardListMapper.addDevCardList(chargerICCardListParam);
        /*if (chargerICCardListParam.getResult() == 1){
            result.setMessage("添加成功！");
        }else if (chargerICCardListParam.getResult() == 0){
            result.setMessage("添加ic卡下发表失败！");
        }*/
    }

    @Override
    @Transactional
    public boolean deleteDevice(List<String> deviceNos, Integer userId) {
        for (String deviceNo : deviceNos) {
            ChargingDevice chargingDevice = new ChargingDevice();
            chargingDevice.setDeviceNo(deviceNo);
            chargingDevice.setRunState(1);
            List<ChargingDevice> select = chargingDeviceMapper.select(chargingDevice);
            if (select.size() > 0){
                throw new BusinessException("不能删除充电中的设备");
            }
        }
        ChargingDevice chargingDevice = new ChargingDevice();
        //设置为删除状态
        chargingDevice.setIsDel(1);
        chargingDevice.setUpdateUserId(userId.longValue());
        chargingDevice.setUpdateTime(new Date());
        //删除设备
        Condition condition = new Condition(ChargingDevice.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andIn("deviceNo",deviceNos);
        int i = chargingDeviceMapper.updateByConditionSelective(chargingDevice,condition);
        if (i>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    @Transactional
    public void deleteCardList(List<String> deviceNos) {
        Condition condition2 = new Condition(ChargingCardList.class);
        Example.Criteria criteria2 = condition2.createCriteria();
        criteria2.andIn("deviceNo",deviceNos);
        chargingCardListMapper.deleteByCondition(condition2);
    }

    @Override
    @Transactional
    public boolean editDevice(ChargingDevice chargingDevice, Integer userId) {
        //修改设备
        chargingDevice.setUpdateUserId(userId.longValue());
        chargingDevice.setUpdateTime(new Date());
        /*//暂时不需要前端传值，后期改
        chargingDevice.setProjectGuid(chargingProjectService.queryAllProject().get(0).getProjectGuid());*/
        chargingDevice.setUpdateUserId(userId.longValue());
        //创建总表cno
        String meterCno = CNoUtil.CreateCNo(DeviceType.ELECTRIC_METER.getCode(), chargingDevice.getMeterNo());
        chargingDevice.setMeterCno(meterCno);

        int n = chargingDeviceMapper.editDevice(chargingDevice);
        if (n>0){
            return  true;
        }else {
            return false;
        }
    }

    @Override
    public void editCardList(ChargingDevice chargingDevice, String oldProjectGuid, Result result) {
        //调用cardlist存储过程
        ChargerICCardListParam chargerICCardListParam = new ChargerICCardListParam();
        chargerICCardListParam.setProjectGuid(chargingDevice.getProjectGuid());
        chargerICCardListParam.setDeviceNo(chargingDevice.getDeviceNo());
        chargerICCardListParam.setCommNo(chargingDevice.getCommNo());
        chargerICCardListParam.setOldProjectGuid(oldProjectGuid);
        chargingCardListMapper.addDevCardList(chargerICCardListParam);
        if (chargerICCardListParam.getResult() == 1){
            result.setMessage("修改成功！");
        }else if (chargerICCardListParam.getResult() == 0){
            result.setMessage("修改ic卡下发表失败！");
        }
    }

    @Override
    public ChargingDeviceDto queryDeviceDetial(String deviceNo) {
        ChargingDevice chargingDevice = this.queryByDeviceNo(deviceNo, "1");
        //查询设备基础信息详情
        ChargingDeviceDto chargingDeviceDto = chargingDeviceMapper.queryDeviceDetial(chargingDevice.getChargingPlieGuid());
        //查询字典表通信状态
        if (chargingDeviceDto.getComMethod() != null){
            DictItem dictItem = dictItemService.findByCodeAndValue(DictCodeConstant.COMMOTHED.getDictCode(), chargingDeviceDto.getComMethod().toString());
            chargingDeviceDto.setComMethodName(dictItem.getDictItemName());
        }
        List<String> deviceNos = Lists.newArrayList();
        deviceNos.add(deviceNo);
        List<ChargingDevicePortDto> chargingDevicePortDtos = chargingDeviceMapper.deviceAndPortList(deviceNos);

        for (ChargingDevicePortDto chargingDevicePortDto : chargingDevicePortDtos) {
            if ("1".equals(chargingDevicePortDto.getPort())){
                chargingDeviceDto.setRunState1(chargingDevicePortDto.getRunState());
            }else if ("2".equals(chargingDevicePortDto.getPort())){
                chargingDeviceDto.setRunState2(chargingDevicePortDto.getRunState());
            }
        }

        return chargingDeviceDto;
    }

    @Override
    public ElectricCountDto queryCountList(ChargerDeviceQueryVo queryVo) {
        ElectricCountDto electricCountDto = new ElectricCountDto();
        queryVo.setStartDate(queryVo.getStartDate()+" 00:00:00");
        queryVo.setEndDate(queryVo.getEndDate() + " 23:59:59");
        Integer pagIndex=(queryVo.getPageNumber()-1)*queryVo.getPageSize();
        queryVo.setPageIndex(pagIndex);
        //查询设备基础信息
        List<ElectricAndFeeDto> chargingDevices =  chargingDeviceMapper.queryCountList(queryVo);
        //查询电量电费统计列表
        List<ElectricAndFeeDto> electricAndFeeDtos =  chargingDeviceMapper.queryListCount(queryVo);

        //按设备分组统计
        ImmutableMap<Object, ElectricAndFeeDto> electricAndFeeDtoMap = Maps.uniqueIndex(electricAndFeeDtos, new Function<ElectricAndFeeDto, Object>() {
            @Nullable
            @Override
            public Object apply(@Nullable ElectricAndFeeDto electricAndFeeDto) {
                return electricAndFeeDto.getDeviceNo();
            }
        });
        for (ElectricAndFeeDto electricAndFeeDto : chargingDevices) {
            ElectricAndFeeDto electricAndFeeDto1 = electricAndFeeDtoMap.get(electricAndFeeDto.getDeviceNo());
            if (electricAndFeeDto1 != null){
                electricAndFeeDto.setUseNumber(electricAndFeeDto1.getUseNumber());
                electricAndFeeDto.setElectricityFees(electricAndFeeDto1.getElectricityFees());
                electricAndFeeDto.setElectricQuantity(electricAndFeeDto1.getElectricQuantity());
                electricAndFeeDto.setProfitable(electricAndFeeDto1.getProfitable());
            }else {
                electricAndFeeDto.setUseNumber(0);
                electricAndFeeDto.setElectricityFees(BigDecimal.ZERO);
                electricAndFeeDto.setElectricQuantity(BigDecimal.ZERO);
                electricAndFeeDto.setProfitable(BigDecimal.ZERO);
            }
        }

        electricCountDto.setElectricAndFeeDtoList(chargingDevices);

        //查询分页总记录数
        Integer total = chargingDeviceMapper.queryCountListTotal(queryVo);
        queryVo.setTotal(total.longValue());

        //查询电量电费汇总信息
        Statistics statistics = chargingDeviceMapper.queryElectricAndFeeTotal(queryVo);
        if (statistics == null){
            statistics = new Statistics();
            statistics.setElectricityFees(0f);
            statistics.setElectricQuantity(0f);
        }
        electricCountDto.setStatistics(statistics);

        return electricCountDto;
    }

    @Override
    public PowerAndFeeCountInfo queryPowerAndFeeCount(ElectricCountQueryVo queryVo) {
        PowerAndFeeCountInfo powerAndFeeCountInfo = new PowerAndFeeCountInfo();
        //获取所有总表表号和对应电价
        List<String> meterNos = chargingDeviceMapper.queryMeterNo(queryVo);
        if(queryVo.getMark() != null){
            //设置年份
            int year = Integer.parseInt(queryVo.getMark().substring(0,4));
            //设置月份
            int month = Integer.parseInt(queryVo.getMark().substring(5,7));
            //List<ListElectricDto> dataList = Lists.newArrayList();
            int monthMaxDay = DateUtil.getMonthMaxDay(year, month);

            // 按月查询
            String monthBeginTime = DateUtil.getMonthBeginTime(year, month);
            String monthEndTime = DateUtil.getMonthEndTime(year, month);
            List<ListElectricDto> list = chargingUseDetailedService.queryMonthList(monthBeginTime, monthEndTime, meterNos);

            List<String> xData = Lists.newArrayList();
            List<BigDecimal> yFeesData  = Lists.newArrayList();
            List<BigDecimal> yQuantityData  = Lists.newArrayList();
            if (CollectionUtils.isEmpty(list)) {
                // 自己组装横坐标，纵坐标
                BigDecimal totalFee = BigDecimal.ZERO;
                BigDecimal totalquantity = BigDecimal.ZERO;
                for (int i = 1; i <= monthMaxDay; i++) {
                    String key = String.valueOf(i);
                    if (i < 10) {
                        key = "0" + i;
                    }
                    xData.add(key);
                    //TODO 项目表读取电价
                    totalFee = MathUtil.setPrecision(totalquantity.multiply(new BigDecimal(0.82)));
                    yFeesData.add(totalFee);
                    yQuantityData.add(totalquantity);
                }
            } else {
                // 按天分组
                ImmutableListMultimap<String, ListElectricDto> multimap = Multimaps.index(list, new Function<ListElectricDto, String>() {
                    @Nullable
                    @Override
                    public String apply(@Nullable ListElectricDto listElectricDto) {
                        return listElectricDto.getDayStr();
                    }
                });

                // 按天统计电费，电量
                for (int i = 1; i <= monthMaxDay; i++) {
                    String key = String.valueOf(i);
                    if (i < 10) {
                        key = "0" + i;
                    }
                    ImmutableList<ListElectricDto> listElectricDtos = multimap.get(key);
                    BigDecimal totalFee = BigDecimal.ZERO;
                    BigDecimal totalquantity = BigDecimal.ZERO;
                    for (ListElectricDto listElectricDto : listElectricDtos) {
                        totalFee = totalFee.add(MathUtil.setPrecision(listElectricDto.getyQuantityData().multiply(new BigDecimal(0.82))));
                        totalquantity = totalquantity.add(listElectricDto.getyQuantityData());
                    }

                    xData.add(key);
                    yFeesData.add(totalFee);
                    yQuantityData.add(totalquantity);
                }
            }

            //汇总该月
            queryVo.setStartTime(monthBeginTime);
            queryVo.setEndTime(monthEndTime);
            PowerAndFeeDto powerAndFeeDto = chargingUseDetailedMapper.queryPowerAndFeeCount(queryVo);
            BigDecimal meterUsePower = new BigDecimal(0);
            for (BigDecimal userPower : yQuantityData) {
                meterUsePower = meterUsePower.add(userPower);
            }

            if (powerAndFeeDto != null){
                powerAndFeeDto.setMeterUsePower(meterUsePower);
                powerAndFeeDto.setMeterUseMoney(MathUtil.setPrecision(meterUsePower.multiply(new BigDecimal(0.82))));
                powerAndFeeDto.setMeterProfit(powerAndFeeDto.getDeductMoneyCount().subtract(powerAndFeeDto.getMeterUseMoney()));
                BeanUtils.copyProperties(powerAndFeeDto,powerAndFeeCountInfo);
            }

            powerAndFeeCountInfo.setxData(xData);
            powerAndFeeCountInfo.setyFeesData(yFeesData);
            powerAndFeeCountInfo.setyQuantityData(yQuantityData);
            //powerAndFeeCountInfo.setListElectric(dataList);
        }
        return powerAndFeeCountInfo;
    }

    /*@Override
    public PowerAndFeeCountInfo queryPowerAndFeeCount(ElectricCountQueryVo queryVo) {
        PowerAndFeeCountInfo powerAndFeeCountInfo = new PowerAndFeeCountInfo();
        if(queryVo.getMark() != null){
            //设置年份
            int year = Integer.parseInt(queryVo.getMark().substring(0,4));
            //设置月份
            int month = Integer.parseInt(queryVo.getMark().substring(5,7));
            //List<ListElectricDto> dataList = Lists.newArrayList();
            int monthMaxDay = DateUtil.getMonthMaxDay(year, month);

            // 按月查询
            String monthBeginTime = DateUtil.getMonthBeginTime(year, month);
            String monthEndTime = DateUtil.getMonthEndTime(year, month);
            List<ListElectricDto> list = chargingUseDetailedService.queryMonthList(monthBeginTime, monthEndTime);

            List<String> xData = Lists.newArrayList();
            List<BigDecimal> yFeesData  = Lists.newArrayList();
            List<BigDecimal> yQuantityData  = Lists.newArrayList();
            if (CollectionUtils.isEmpty(list)) {
                // 自己组装横坐标，纵坐标
                BigDecimal totalFee = BigDecimal.ZERO;
                BigDecimal totalquantity = BigDecimal.ZERO;
                for (int i = 1; i <= monthMaxDay; i++) {
                    String key = String.valueOf(i);
                    if (i < 10) {
                        key = "0" + i;
                    }
                    xData.add(key);
                    yFeesData.add(totalFee);
                    yQuantityData.add(totalquantity);
                }
            } else {
                // 按天分组
                ImmutableListMultimap<String, ListElectricDto> multimap = Multimaps.index(list, new Function<ListElectricDto, String>() {
                    @Nullable
                    @Override
                    public String apply(@Nullable ListElectricDto listElectricDto) {
                        return listElectricDto.getDayStr();
                    }
                });

                // 按天统计电费，电量
                for (int i = 1; i <= monthMaxDay; i++) {
                    String key = String.valueOf(i);
                    if (i < 10) {
                        key = "0" + i;
                    }
                    ImmutableList<ListElectricDto> listElectricDtos = multimap.get(key);
                    BigDecimal totalFee = BigDecimal.ZERO;
                    BigDecimal totalquantity = BigDecimal.ZERO;
                    for (ListElectricDto listElectricDto : listElectricDtos) {
                        totalFee = totalFee.add(listElectricDto.getyFeesData());
                        totalquantity = totalquantity.add(listElectricDto.getyQuantityData());
                    }

                    xData.add(key);
                    yFeesData.add(totalFee);
                    yQuantityData.add(totalquantity);
                }
            }

            //汇总该月
            PowerAndFeeDto powerAndFeeDto = chargingUseDetailedMapper.queryPowerAndFeeCount(monthBeginTime,monthEndTime);
            if (powerAndFeeDto != null){
                BeanUtils.copyProperties(powerAndFeeDto,powerAndFeeCountInfo);
            }

            powerAndFeeCountInfo.setxData(xData);
            powerAndFeeCountInfo.setyFeesData(yFeesData);
            powerAndFeeCountInfo.setyQuantityData(yQuantityData);
            //powerAndFeeCountInfo.setListElectric(dataList);
        }
        return powerAndFeeCountInfo;
    }*/

    @Override
    public List<MonitorDeviceDto> monitorDeviceList(ChargerDeviceQueryVo queryVo) {
        List<MonitorDeviceDto> monitorDeviceDtos = Lists.newArrayList();
        Long total = 0L;
        if ("1".equals(queryVo.getRunState())){//查询充电中
            PageHelper.startPage(queryVo.getPageNumber(),queryVo.getPageSize());
            List<MonitorDeviceDto> monitorDeviceDtos1 = chargingDeviceMapper.monitorDeviceList(queryVo);
            PageInfo pageInfo=new PageInfo(monitorDeviceDtos1);
            total+=pageInfo.getTotal();
            monitorDeviceDtos.addAll(monitorDeviceDtos1);
        }else if ("".equals(queryVo.getRunState()) || queryVo.getRunState() == null){
            PageHelper.startPage(queryVo.getPageNumber(),queryVo.getPageSize());
            List<MonitorDeviceDto> monitorDeviceDtos1 = chargingDeviceMapper.notChargingAndChargingDevice(queryVo);
            monitorDeviceDtos.addAll(monitorDeviceDtos1);
            PageInfo pageInfo=new PageInfo(monitorDeviceDtos1);
            total+=pageInfo.getTotal();
        } else {
            PageHelper.startPage(queryVo.getPageNumber(),queryVo.getPageSize());
            List<MonitorDeviceDto> monitorDeviceDtos1 = chargingDeviceMapper.monitorNotChargingDevice(queryVo);
            monitorDeviceDtos.addAll(monitorDeviceDtos1);
            PageInfo pageInfo=new PageInfo(monitorDeviceDtos1);
            total+=pageInfo.getTotal();
        }

        //通过扣除费用判断充值方式
        for (MonitorDeviceDto monitorDeviceDto : monitorDeviceDtos) {
            //设置充电剩余时长 单位(分钟)
            if (monitorDeviceDto.getRunState().equals(ChargingEnum.ON_STATE.getKey())){
                if (monitorDeviceDto.getChargingTime() != null && monitorDeviceDto.getChargingWay() != null) {
                    if (monitorDeviceDto.getChargingWay() == 1) {
                        Integer chargingTime = monitorDeviceDto.getChargingTime();
                        Integer remainTime = chargingTime * 60 - monitorDeviceDto.getUseTime();
                        if(remainTime < 0){
                            remainTime = 0;
                        }
                        monitorDeviceDto.setRemainTime(BigDecimal.valueOf(remainTime));
                    }
                }
            }
        }
        queryVo.setTotal(total);
        return monitorDeviceDtos;
    }

    @Override
    public ChargingCountByRunState monitorDeviceCount(ChargerDeviceVo queryVo) {
        ChargingCountByRunState count = new ChargingCountByRunState();
        int total = 0;
        int offlineTotal = 0;
        //按状态统计设备数量,按状态返回
        List<MonitorDeviceTotalDto> list = chargingDeviceMapper.monitorDeviceCount(queryVo);
        for (MonitorDeviceTotalDto monitorDeviceTotalDto:list){
            total += monitorDeviceTotalDto.getCount();
            if (monitorDeviceTotalDto.getOnline() == 1){
                if (monitorDeviceTotalDto.getRunState().equals(ChargingEnum.FREE_STATE.getKey())){
                    count.setFreeTotal(monitorDeviceTotalDto.getCount());
                }else if (monitorDeviceTotalDto.getRunState().equals(ChargingEnum.ON_STATE.getKey())){
                    count.setOnTotal(monitorDeviceTotalDto.getCount());
                }else if (monitorDeviceTotalDto.getRunState().equals(ChargingEnum.OFF_STATE.getKey())){
                    count.setOffTotal(monitorDeviceTotalDto.getCount());
                }else if (monitorDeviceTotalDto.getRunState().equals(ChargingEnum.ERROR_STATE.getKey())){
                    count.setErrorTotal(monitorDeviceTotalDto.getCount());
                }
            }else if (monitorDeviceTotalDto.getOnline() == 0){
                offlineTotal += monitorDeviceTotalDto.getCount();
            }

        }
        count.setOfflineTotal(offlineTotal);
        count.setTotal(total);
        return count;
    }

    @Override
    @Transactional
    public void updateRunState(String commNo, Integer runState, String port) {
        Condition condition = new Condition(ChargingDevice.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("commNo",commNo);
        if (!StringUtil.isEmpty(port)){
            criteria.andEqualTo("port",port);
        }
        ChargingDevice chargingDevice = new ChargingDevice();
        chargingDevice.setRunState(runState);
        chargingDeviceMapper.updateByConditionSelective(chargingDevice,condition);
    }

    @Override
    public ChargingDevice queryByDeviceNo(String deviceNo, String port) {
        ChargingDevice param = new ChargingDevice();
        param.setDeviceNo(deviceNo);
        param.setPort(port);
        param.setIsDel(0);
        return chargingDeviceMapper.selectOne(param);
    }

    @Override
    public ChargingDevice queryByCommon(String common, String port) {
        ChargingDevice param = new ChargingDevice();
        param.setCommNo(common);
        param.setPort(port);
        param.setIsDel(0);
        return chargingDeviceMapper.selectOne(param);
    }

    @Override
    public ChargingDevice queryByChargingPlieGuid(String chargingPlieGuid) {
        ChargingDevice param = new ChargingDevice();
        param.setChargingPlieGuid(chargingPlieGuid);
        return chargingDeviceMapper.selectOne(param);
    }

    @Override
    public List<ChargingDevice> batchQueryByChargingPlieGuids(Collection<String> guids) {
        Condition condition = new Condition(ChargingDevice.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andIn("chargingPlieGuid",guids);
        return chargingDeviceMapper.selectByCondition(condition);
    }

    @Override
    public void offOnCharge(OffOnChargeVo offOnChargeVo) {
        String errorMsg = "停用充电桩中间件指令成功";
        AFN19Object afn19Object = null;
        if (offOnChargeVo.getOnOrOff() == 0){// web端停用
             afn19Object = new AFN19Object(UUID.randomUUID().toString(),
                    "19",
                    "999999999",
                    "0042475858fffaa",
                    offOnChargeVo.getCommNo(),
                    "0",
                    offOnChargeVo.getPort(),
                    "off",
                    offOnChargeVo.getSessionId(),
                    "",
                    "",
                    "0",
                    "2",0,0,"");
            afn19Object.setNonUseFlag(String.valueOf(offOnChargeVo.getOnOrOff()));

            // web端停电（中间件停电成功会修改为空闲状态，修改为停用只能在回调函数内进行）
            this.updateRunState(offOnChargeVo.getCommNo(), ChargingEnum.OFF_STATE.getKey(),offOnChargeVo.getPort());
            //下发数据
            int result =  ClientManager.sendAFN19Msg(afn19Object);
            if (result != 1) {
                errorMsg = "停用充电桩中间件指令失败";
                throw new BusinessException(errorMsg);
            }
        } else if (offOnChargeVo.getOnOrOff() == 1){
            // web端启用
            this.updateRunState(offOnChargeVo.getCommNo(), ChargingEnum.FREE_STATE.getKey(),offOnChargeVo.getPort());
        }else if (offOnChargeVo.getOnOrOff() == -1){// web端停电
            if (!StringUtil.isEmpty(offOnChargeVo.getChargingGuid())){
                ChargingUseDetailed chargingUseDetailed = chargingUseDetailedService.queryChargingRecordByChargingGuid(offOnChargeVo.getChargingGuid());
                if (chargingUseDetailed.getState().equals(ChargeConstant.ChargeState.COMPLETED.getState())){//判断该充电订单是否关闭
                    throw new BusinessException("该人员已完成充电，请刷新页面！");
                }
            }
            afn19Object = new AFN19Object(UUID.randomUUID().toString(),
                    "19",
                    "999999999",
                    "0042475858fffaa",
                    offOnChargeVo.getCommNo(),
                    "0",
                    offOnChargeVo.getPort(),
                    "off",
                    offOnChargeVo.getSessionId(),
                    "",
                    "",
                    "0",
                    "2",0,0,"");
            afn19Object.setNonUseFlag(String.valueOf(offOnChargeVo.getOnOrOff()));
            //下发数据
            int result =  ClientManager.sendAFN19Msg(afn19Object);
            if (result != 1) {
                errorMsg = "停电充电桩中间件指令失败";
                throw new BusinessException(errorMsg);
            }
        }
    }

    @Override
    public void queryThreshold(String sessionId, String commNo, String Port) {
        if (StringUtil.isEmpty(commNo)){
            throw new BusinessException("commNo不能为空");
        }
        if (StringUtil.isEmpty(Port)){
            throw new BusinessException("port不能为空");
        }
        AFN22Object afn22Object = new AFN22Object(UuidUtil.getUuid(),"22","999999999","0042475858FFFFDF",commNo,
                sessionId,null,Port);
        //下发指令
        int result =  ClientManager.sendAFN22Msg(afn22Object);
        if (result != 1) {
            throw new BusinessException("查询充电桩阈值中间件指令发送失败");
        }
    }

    @Override
    public void setThreshold(String sessionId, ChargeThresholdVo chargeThresholdVo) {
        AFN21Object afn21Object = new AFN21Object(UuidUtil.getUuid(),"21","999999999","0042475858fffaa",
                chargeThresholdVo.getCommNo(),chargeThresholdVo.getPort(),sessionId,null,
                String.valueOf(chargeThresholdVo.getOverCurrent()),
                String.valueOf(chargeThresholdVo.getOverVoltage()),
                String.valueOf(chargeThresholdVo.getUnderVoltage()),
                String.valueOf(chargeThresholdVo.getUnderCurrent()),
                String.valueOf(chargeThresholdVo.getUnderCurrentDelay()));
        //下发指令
        int result =  ClientManager.sendAFN21Msg(afn21Object);
        if (result != 1) {
            throw new BusinessException("设置充电桩阈值中间件指令发送失败");
        }
    }

    @Override
    public void queryHeartStep(String sessionId, String commNo) {
        if (StringUtil.isEmpty(commNo)){
            throw new BusinessException("commNo不能为空");
        }
        AFN23Object afn23Object = new AFN23Object(UuidUtil.getUuid(),"23","999999999","0042475858fffaa",commNo,
                sessionId,null,"0","");
        //下发指令
        int result =  ClientManager.sendAFN23Msg(afn23Object);
        if (result != 1) {
            throw new BusinessException("查询充电桩心跳间隔中间件指令发送失败");
        }
    }

    @Override
    public void setHeartStep(String sessionId, ChargeHeartStepVo chargeHeartStepVo) {
        AFN23Object afn23Object = new AFN23Object(UuidUtil.getUuid(),"23","999999999","0042475858fffaa",chargeHeartStepVo.getCommNo(),
                sessionId,null,"1",String.valueOf(chargeHeartStepVo.getHeartStep()));
        //下发指令
        int result =  ClientManager.sendAFN23Msg(afn23Object);
        if (result != 1) {
            throw new BusinessException("设置充电桩心跳间隔中间件指令发送失败");
        }
    }
}
