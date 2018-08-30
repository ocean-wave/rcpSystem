package cn.com.cdboost.collect.impl;


import cn.com.cdboost.collect.cache.CustomerCacheVo;
import cn.com.cdboost.collect.cache.DeviceCacheVo;
import cn.com.cdboost.collect.cache.DeviceRelationCacheVo;
import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.constant.*;
import cn.com.cdboost.collect.dao.CustomerInfoMapper;
import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.dto.response.*;
import cn.com.cdboost.collect.enums.DeviceType;
import cn.com.cdboost.collect.enums.ElectCategory;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.*;
import cn.com.cdboost.collect.model.CustomerInfo;
import cn.com.cdboost.collect.service.*;
import cn.com.cdboost.collect.util.*;
import cn.com.cdboost.collect.util.DateUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.csvreader.CsvWriter;
import com.google.common.base.Function;
import com.google.common.collect.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Nullable;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用户档案接口服务实现类
 */
@Service("customerInfoService")
public class CustomerInfoServiceImpl extends BaseServiceImpl<CustomerInfo> implements CustomerInfoService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerInfoServiceImpl.class);

    @Autowired
    private CustomerInfoMapper customerInfoMapper;
    @Autowired
    private DeviceInfoService deviceInfoService;
    @Autowired
    private DeviceConnParamService deviceConnParamService;
    @Autowired
    private DeviceMeterParamService deviceMeterParamService;
    @Autowired
    private DeviceMeterConfigService deviceMeterConfigService;
    @Autowired
    private FeeAcctService feeAcctService;
    @Autowired
    private FeePayService feePayService;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private MeterReadQueueService meterReadQueueService;
    @Autowired
    private DruidDataSource dataSource;
    @Autowired
    private TmpCustomerInfoService tmpCustomerInfoService;
    @Autowired
    private CustomerDevMapService customerDevMapService;
    @Autowired
    private DeviceInfoDeviceStateService deviceInfoDeviceStateService;
    @Autowired
    private DictItemService dictItemService;
    @Autowired
    private FeePriceSolsService feePriceSolsService;
    @Autowired
    private CostCalculateService costCalculateService;
    @Autowired
    private CustomerPhoneBindService customerPhoneBindService;
    @Autowired
    private CustomerWxBindService customerWxBindService;
    @Autowired
    private OrgAppService orgAppService;
    @Autowired
    private CustomerInfoCostService customerInfoCostService;
    @Autowired
    private MeterInitPowerService meterInitPowerService;
    @Autowired
    private MeterRelationService meterRelationService;
    @Autowired
    private ICCardService icCardService;
    @Autowired
    private OrgService orgService;
    @Autowired
    private QuerySchememetService querySchememetService;
    @Autowired
    private QuerySchemeService querySchemeService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public ElectRecordInfo queryelectConsumption(ElectConsumptionParam param) {
        ElectRecordInfo electRecordInfo=new ElectRecordInfo();
        List<ElectConsumptionResponseParam> electConsumptionResponseParams = customerInfoMapper.queryelectConsumption(param);
        electRecordInfo.setList(electConsumptionResponseParams);
        Electstatics electstatics=new Electstatics();
        BigDecimal sumMoney = param.getSumMoney();
        BigDecimal sumPower = param.getSumPower();
        if(sumMoney!=null){
            electstatics.setSumMoney(String.valueOf(sumMoney.setScale(2,BigDecimal.ROUND_HALF_UP)));
        }
        if(sumPower!=null){
            electstatics.setSumPower(String.valueOf(sumPower.setScale(2,BigDecimal.ROUND_HALF_UP)));
        }
        electRecordInfo.setStatistics(electstatics);
        return electRecordInfo;

    }

    @Override
    @Transactional
    public String addCustomerInfoAndDeviceInfo(CustomerInfoCreateParam param, Integer currentUserId) {
        // 校验并设置水气表表号
        this.check(param);

        // 新增客户信息
        CustomerInfoAddParam addParam = param.getCustomerInfo();
        CustomerInfo customerInfo = new CustomerInfo();
        BeanUtils.copyProperties(addParam, customerInfo);
        // 生成客户唯一标识
        String customerNo = UuidUtil.getUuid();
        customerInfo.setCustomerNo(customerNo);
        customerInfo.setCreateUserId(Long.valueOf(currentUserId));
        customerInfoMapper.insertSelective(customerInfo);
        Integer offScheme = addParam.getOffScheme();
        Integer offParam = addParam.getOffParam();
        //是否是远程费控
        boolean localControl = false;

        // 记录所有的水电气表cno
        List<String> cnoList = Lists.newArrayList();
        // 添加电表上相关信息
        List<ElectricMeterAddParam> electricMeter = param.getElectricMeter();
        if (!CollectionUtils.isEmpty(electricMeter)) {
            for (ElectricMeterAddParam meterAddParam : electricMeter) {
                // 设置拉闸策略
                meterAddParam.setOffScheme(offScheme);
                meterAddParam.setOffParam(offParam);

                this.addElectricMeter(meterAddParam,customerInfo);
                String cno = CNoUtil.CreateCNo(DeviceType.ELECTRIC_METER.getCode(), meterAddParam.getDeviceNo());
                cnoList.add(cno);
                if(2==meterAddParam.getLocalControl()){
                    localControl=true;
                }
            }
        }

        // 添加水表上相关信息
        List<WaterMeterAddParam> waterMeter = param.getWaterMeter();
        if (!CollectionUtils.isEmpty(waterMeter)) {
            for (WaterMeterAddParam meterAddParam : waterMeter) {
                // 设置拉闸策略
                meterAddParam.setOffScheme(offScheme);
                meterAddParam.setOffParam(offParam);

                this.addWaterMeter(meterAddParam,customerInfo);

                String cno = CNoUtil.CreateCNo(DeviceType.WATER_METER.getCode(), meterAddParam.getDeviceNo());
                cnoList.add(cno);
            }
        }

        // 添加气表上相关信息
        List<GasMeterAddParam> gasMeter = param.getGasMeter();
        if (!CollectionUtils.isEmpty(gasMeter)) {
            for (GasMeterAddParam meterAddParam : gasMeter) {
                // 设置拉闸策略
                meterAddParam.setOffScheme(offScheme);
                meterAddParam.setOffParam(offParam);

                this.addGasMeter(meterAddParam,customerInfo);

                String cno = CNoUtil.CreateCNo(DeviceType.GAS_METER.getCode(), meterAddParam.getDeviceNo());
                cnoList.add(cno);
            }
        }

        BigDecimal total=param.getCustomerInfo().getInitAmount();
        // 新增客户费用表信息
        CustomerInfoCost cost = new CustomerInfoCost();
        cost.setCustomerNo(customerNo);
        cost.setRemainAmount(total);
        cost.setTotalAmount(total);
        cost.setCalcTime(new Date());
        cost.setAlarmThreshold(addParam.getAlarmThreshold());
        cost.setAlarmThreshold1(addParam.getAlarmThreshold1());
        cost.setAlarmThreshold2(addParam.getAlarmThreshold2());
        cost.setOverdraftAmount(addParam.getOverdraftAmount());
        cost.setInitAmount(addParam.getInitAmount());
        if(localControl&& !total.equals(BigDecimal.ZERO)){
            cost.setPayCount(1);
        }
        customerInfoCostService.insertSelective(cost);
        // 远程费控新增客户缴费记录表信息
        if(localControl&& !total.equals(BigDecimal.ZERO)){
            FeePay feePay = new FeePay();
            feePay.setCustomerNo(customerNo);
            feePay.setCno("");
            feePay.setPayCount(1);
            feePay.setPayDate(new Date());
            feePay.setPayMoney(total);
            feePay.setPayment(total);
            feePay.setPayModel(FeeControlConstant.PayModelValue.APP.getCode());
            feePay.setCreateUserId(Long.valueOf(currentUserId));
            feePay.setWriteMeter(1); // 购电金额是否下发APP充值需要下发，购电卡充值不需要要下发 0- 未写入 1-写入
            feePay.setDeviceType("");
            feePay.setWitMetTime(new Date());
            feePay.setPayGuid(UuidUtil.getUuid());
            feePay.setIsValid(1);
            feePay.setAdjusAmount(BigDecimal.ZERO);
            feePay.setBeforRemainAmount(BigDecimal.ZERO);
            feePay.setRemainAmount(total);
            String serialNum = DateUtil.getSerialNum();
            feePay.setSerialNum(serialNum);
            feePayService.insertSelective(feePay);
        }
        // redis操作
        // 设置用户信息到redis
        CustomerInfo tempInfo = this.queryByCustomerNo(customerNo);
        CustomerCacheVo cacheVo = new CustomerCacheVo();
        BeanUtils.copyProperties(tempInfo,cacheVo);
        String userKey = RedisKeyConstant.CUSTOMER_PREFIX + tempInfo.getCustomerNo();
        redisUtil.set(userKey,JSON.toJSONString(cacheVo));

        // 查询设备参数表新
        List<DeviceMeterParam> meterParams = deviceMeterParamService.findDeviceMeterParamByCnos(cnoList);
        // 分组
        ImmutableMap<String, DeviceMeterParam> meterParamMap = Maps.uniqueIndex(meterParams, new Function<DeviceMeterParam, String>() {
            @Nullable
            @Override
            public String apply(@Nullable DeviceMeterParam deviceMeterParam) {
                return deviceMeterParam.getCno();
            }
        });
        // 设置用户设备信息到redis
        List<DeviceInfo> deviceInfos = deviceInfoService.batchQueryByCnos(cnoList);
        List<DeviceCacheVo> deviceCacheVos = Lists.newArrayList();
        for (DeviceInfo deviceInfo : deviceInfos) {
            DeviceCacheVo deviceCacheVo = new DeviceCacheVo();
            BeanUtils.copyProperties(deviceInfo,deviceCacheVo);
            DeviceMeterParam meterParam = meterParamMap.get(deviceInfo.getCno());
            deviceCacheVo.setIsImportant(meterParam.getIsImportant());
            deviceCacheVos.add(deviceCacheVo);
        }
        redisUtil.setDeviceCacheList(deviceCacheVos);

        // 设置设备关系缓存
        List<MeterRelation> meterRelations = meterRelationService.batchQueryByMeterCnos(cnoList);
        List<DeviceRelationCacheVo> cacheVoList = Lists.newArrayList();
        for (MeterRelation meterRelation : meterRelations) {
            DeviceRelationCacheVo vo = new DeviceRelationCacheVo();
            BeanUtils.copyProperties(meterRelation,vo);
            cacheVoList.add(vo);
        }
        redisUtil.setDeviceRelationList(cacheVoList);
        return customerNo;
    }

    @Override
    public List<CustomerInfoDto> queryList(CustomerInfoQueryVo customerInfoQueryVo) {
        List<CustomerInfoDto> listMap = customerInfoMapper.query(customerInfoQueryVo);
        return listMap;
    }
    @Override
    public List<CustomerInfoDto> queryListNew(CustomerInfoQueryNewVo customerInfoQueryVo) {
        List<CustomerInfoDto> listMap = customerInfoMapper.queryNew(customerInfoQueryVo);
        return listMap;
    }
    @Override
    public List<CustomerInfoDtodownload> querydownlist(CustomerInfoQueryNewVo customerInfoQueryVo) {
        List<CustomerInfoDtodownload> listMap = customerInfoMapper.querynew(customerInfoQueryVo);
        return listMap;
    }
    @Override
    @Transactional
    public void deleteByCustomerNo(String customerNo) {
        CustomerInfo param = new CustomerInfo();
        param.setCustomerNo(customerNo);
        CustomerInfo customerInfo = customerInfoMapper.selectOne(param);
        if (customerInfo == null) {
            throw new BusinessException("要删除的数据不存在，请重新刷新页面");
        }
        customerInfoMapper.delete(param);
    }

    @Override
    @Transactional
    public List queryByCoustomerInfo(CustomerInfo customerInfo) {
        List<CustomerInfo> list=Lists.newArrayList();
        Condition condition = new Condition(customerInfo.getClass());
        Example.Criteria criteria = condition.createCriteria();
        boolean customerName=false;
        boolean propertyName=false;
        boolean customerContact=false;
        boolean customerAddr=false;
        if (!StringUtils.isEmpty(customerInfo.getCustomerName())) {
            criteria.andLike("customerName", "%" + customerInfo.getCustomerName() + "%");
            customerName=true;
        }
        if (!StringUtils.isEmpty(customerInfo.getPropertyName())) {
            criteria.andLike("propertyName", "%" + customerInfo.getPropertyName() + "%");
            propertyName=true;
        }
        if (!StringUtils.isEmpty(customerInfo.getCustomerContact())) {
            criteria.andLike("customerContact", "%" + customerInfo.getCustomerContact() + "%");
            customerContact=true;
        }
        if (!StringUtils.isEmpty(customerInfo.getCustomerAddr())) {
            criteria.andLike("customerAddr", "%" + customerInfo.getCustomerAddr() + "%");
            customerAddr=true;
        }
        for (CustomerInfo info : customerInfoMapper.selectByCondition(condition)) {
            CustomerInfo customerInfo1=new CustomerInfo();
            if(customerName){
                customerInfo1.setCustomerName(info.getCustomerName());
            }
            if(propertyName){
                customerInfo1.setPropertyName(info.getPropertyName());
            }
            if(customerContact){
                customerInfo1.setCustomerContact(info.getCustomerContact());
            }
            if(customerAddr){
                customerInfo1.setCustomerAddr(info.getCustomerAddr());
            }
            customerInfo1.setCustomerNo(info.getCustomerNo());
            list.add(customerInfo1);
        }
        return list;
    }

    @Override
    public List<FuzzyQueryUserDto> fuzzyQueryUserInfo(FuzzyQueryUserVo param) {
        Integer userId = param.getUserId();
        // 查询用户的组织数据权限
        Set<Long> dataOrgNos = redisService.queryUserOrgNoByUserId(userId);

        FuzzyQueryUserParam userParam = new FuzzyQueryUserParam();
        BeanUtils.copyProperties(param,userParam);
        List list = Lists.newArrayList(dataOrgNos);
        userParam.setDataOrgList(list);

        List<FuzzyQueryUserDto> dataList = Lists.newArrayList();
        List<FuzzyQueryUserDto> userDtos = customerInfoMapper.fuzzyQueryUserInfo(userParam);
        if (CollectionUtils.isEmpty(userDtos)) {
            return dataList;
        }

        for (FuzzyQueryUserDto userDto : userDtos) {
            FuzzyQueryUserDto dto = new FuzzyQueryUserDto();
            dto.setCustomerNo(userDto.getCustomerNo());
            if (!StringUtils.isEmpty(param.getCustomerName())) {
                dto.setCustomerName(userDto.getCustomerName());
            }

            if (!StringUtils.isEmpty(param.getPropertyName())) {
                dto.setPropertyName(userDto.getPropertyName());
            }

            if (!StringUtils.isEmpty(param.getCustomerContact())) {
                dto.setCustomerContact(userDto.getCustomerContact());
            }

            if (!StringUtils.isEmpty(param.getCustomerAddr())) {
                dto.setCustomerAddr(userDto.getCustomerAddr());
            }
            dataList.add(dto);
        }
        return dataList;
    }

    /**
     * 新增气表上相关信息
     * @param gasMeter
     * @param customerInfo
     */
    private void addGasMeter(GasMeterAddParam gasMeter, CustomerInfo customerInfo) {
        logger.info("添加气表开始GasMeterAddParam=" + JSON.toJSONString(gasMeter) + ",CustomerInfo=" + JSON.toJSONString(customerInfo));
        Long orgNo = customerInfo.getOrgNo();
        Long currentUserId = customerInfo.getCreateUserId();

        // 新增集中器
        Integer commPort = gasMeter.getCommPort();
        String jzqNo = gasMeter.getJzqNo();
        this.addJzq(jzqNo,commPort,currentUserId,orgNo);

        // 新增转换器
        String cjqNo = gasMeter.getCjqNo();
        Integer collectDevType = gasMeter.getCollectDevType();
        if (!StringUtils.isEmpty(cjqNo)) {
            if (CustomerInfoConstant.GasCollectDeviceType.CONVERTER.getCode().equals(collectDevType)) {
                // 转换器才添加，节点不用添加
                this.addConverter(DeviceType.GAS_METER.getCode(), jzqNo, cjqNo, commPort, currentUserId, orgNo);
            }
        }
        // 查询设备型号
        DeviceMeterConfig meterConfig = deviceMeterConfigService.queryByParamFlag(gasMeter.getParamFlag());

        // 新增气表设备表信息
        this.addGasDeviceInfo(gasMeter,meterConfig,currentUserId,orgNo);

        // 新增气表参数表信息
        String customerNo = customerInfo.getCustomerNo();
        this.addGasMeterParam(gasMeter,meterConfig,customerNo);

        // 新增客户气表关联表信息
        this.addCustomerGasRelation(gasMeter, customerNo);

        // 添加初始电能示值表记录
        this.addGasMeterInitPower(customerNo,gasMeter,meterConfig);

        // 新增设备总子关系
        this.addDeviceRelation(DeviceType.GAS_METER.getCode(),gasMeter.getDeviceNo(),gasMeter.getParentDeviceNo(),customerNo);
    }

    /**
     * 新增水表上相关信息
     * @param waterMeter
     * @param customerInfo
     */
    private void addWaterMeter(WaterMeterAddParam waterMeter, CustomerInfo customerInfo) {
        logger.info("添加水表开始WaterMeterAddParam=" + JSON.toJSONString(waterMeter) + ",CustomerInfo=" + JSON.toJSONString(customerInfo));
        Long orgNo = customerInfo.getOrgNo();
        Long currentUserId = customerInfo.getCreateUserId();

        // 新增集中器
        Integer commPort = waterMeter.getCommPort();
        String jzqNo = waterMeter.getJzqNo();
        this.addJzq(jzqNo,commPort,currentUserId,orgNo);

        // 新增转换器
        String cjqNo = waterMeter.getCjqNo();
        Integer collectDevType = waterMeter.getCollectDevType();
        if (!StringUtils.isEmpty(cjqNo)) {
            if (CustomerInfoConstant.WaterCollectDeviceType.CONVERTER.getCode().equals(collectDevType)) {
                // 转换器才添加，节点不用添加
                this.addConverter(DeviceType.WATER_METER.getCode(), jzqNo, cjqNo, commPort, currentUserId, orgNo);
            }
        }
        // 查询设备型号
        DeviceMeterConfig meterConfig = deviceMeterConfigService.queryByParamFlag(waterMeter.getParamFlag());

        // 新增水表设备表信息
        this.addWaterDeviceInfo(waterMeter,meterConfig,currentUserId,orgNo);

        // 新增水表参数表信息
        String customerNo = customerInfo.getCustomerNo();
        this.addWaterMeterParam(waterMeter,meterConfig,customerNo);

        // 新增客户水表关联表信息
        this.addCustomerWaterRelation(waterMeter, customerNo);

        // 添加初始电能示值表记录
        this.addWaterMeterInitPower(customerNo,waterMeter,meterConfig);

        // 新增设备总子关系
        this.addDeviceRelation(DeviceType.WATER_METER.getCode(),waterMeter.getDeviceNo(),waterMeter.getParentDeviceNo(),customerNo);
    }

    /**
     * 新增电表上相关信息
     *
     * @param electricMeter
     * @param customerInfo
     */
    private void addElectricMeter(ElectricMeterAddParam electricMeter, CustomerInfo customerInfo) {
        logger.info("添加电表开始ElectricMeterAddParam=" + JSON.toJSONString(electricMeter) + ",CustomerInfo=" + JSON.toJSONString(customerInfo));
        Long orgNo = customerInfo.getOrgNo();
        Long currentUserId = customerInfo.getCreateUserId();

        // 新增集中器
        Integer commPort = electricMeter.getCommPort();
        String jzqNo = electricMeter.getJzqNo();
        this.addJzq(jzqNo,commPort,currentUserId,orgNo);

        // 新增采集器
        String cjqNo = electricMeter.getCjqNo();
        Integer collectDevType = electricMeter.getCollectDevType();
        if (!StringUtils.isEmpty(cjqNo)) {
            if (CustomerInfoConstant.ElectricCollectDeviceType.COLLECTOR.getCode().equals(collectDevType)) {
                // 采集器才添加，节点不用添加
                this.addCjq(electricMeter.getJzqNo(),cjqNo,commPort,currentUserId,orgNo);
            }
        }
        // 查询设备型号
        DeviceMeterConfig meterConfig = deviceMeterConfigService.queryByParamFlag(electricMeter.getParamFlag());

        // 新增电表设备表信息
        this.addElectricDeviceInfo(electricMeter,meterConfig,currentUserId,orgNo);

        // 新增电表参数表信息
        String customerNo = customerInfo.getCustomerNo();
        this.addElectricMeterParam(electricMeter,meterConfig,customerNo);

        // 新增客户电表关联表信息
        this.addCustomerElectricRelation(electricMeter, customerNo);

        // 添加初始电能示值表记录
        this.addElectricMeterInitPower(customerNo,electricMeter,meterConfig);

        // 新增设备总子关系
        this.addDeviceRelation(DeviceType.ELECTRIC_METER.getCode(),electricMeter.getDeviceNo(),electricMeter.getParentDeviceNo(),customerNo);
    }

    /**
     * 添加设备关系表记录
     * @param deviceType
     * @param deviceNo
     * @param parentDeviceNo
     * @param customerNo
     */
    private void addDeviceRelation(String deviceType, String deviceNo, String parentDeviceNo,String customerNo) {
        String cno = CNoUtil.CreateCNo(deviceType,deviceNo);
        if (StringUtils.isEmpty(parentDeviceNo)) {
            // 自身默认为总表
            MeterRelation relation = new MeterRelation();
            relation.setpMeterCno("0");
            relation.setMeterCno(cno);
            relation.setCustomerNo(customerNo);
            relation.setLevel("/" + cno);
            meterRelationService.insertSelective(relation);
        } else {
            // 查询总表信息
            String parentCno = CNoUtil.CreateCNo(deviceType,parentDeviceNo);
            MeterRelation meterRelation = meterRelationService.queryByCno(parentCno);
            if (meterRelation == null) {
                String message = DeviceType.getMessageByCode(deviceType);
                throw new BusinessException(message + "表号[" + deviceNo + "]的总表[" + parentDeviceNo + "]记录在设备关系表中不存在");
            }
            MeterRelation relation = new MeterRelation();
            relation.setpMeterCno(parentCno);
            relation.setMeterCno(cno);
            relation.setCustomerNo(customerNo);
            String level = meterRelation.getLevel();
            relation.setLevel(level + "/" + cno);
            meterRelationService.insertSelective(relation);
        }
    }

    private void addElectricMeterInitPower(String customerNo,ElectricMeterAddParam param,DeviceMeterConfig meterConfig) {
        MeterInitPower power = new MeterInitPower();
        power.setCustomerNo(customerNo);
        power.setMeterUserNo(param.getMeterUserNo());
        String cno = CNoUtil.CreateCNo(DeviceType.ELECTRIC_METER.getCode(), param.getDeviceNo());
        power.setCno(cno);

        // 查询对应的电价方案
        FeePriceSols priceSols = new FeePriceSols();
        priceSols.setDictItemValue(param.getDictItemValue());
        priceSols.setIsEnabled(1);
        FeePriceSols feePriceSols = feePriceSolsService.selectOne(priceSols);
        power.setPriceSolsCode(feePriceSols.getPriceSolsCode());
        power.setCommFactorCnt(meterConfig.getCommFactorCnt());
        // 设置时间
        this.setMeterInitPowerDate(power);

        BigDecimal readValue1 = param.getReadValue1();
        if (readValue1 != null) {
            power.setReadValue1(readValue1);
        } else {
            power.setReadValue1(BigDecimal.ZERO);
        }
        BigDecimal readValue2 = param.getReadValue2();
        if (readValue2 != null) {
            power.setReadValue2(readValue2);
        } else {
            power.setReadValue2(BigDecimal.ZERO);
        }
        BigDecimal readValue3 = param.getReadValue3();
        if (readValue3 != null) {
            power.setReadValue3(readValue3);
        } else {
            power.setReadValue3(BigDecimal.ZERO);
        }
        BigDecimal readValue4 = param.getReadValue4();
        if (readValue4 != null) {
            power.setReadValue4(readValue4);
        } else {
            power.setReadValue4(BigDecimal.ZERO);
        }

        BigDecimal readValue = param.getReadValue();
        if (readValue != null) {
            power.setReadValue(readValue);
        } else {
            power.setReadValue(BigDecimal.ZERO);
        }
        power.setCreateTime(new Date());
        meterInitPowerService.insertSelective(power);
    }

//    /**
//     * 电表添加每天费用信息记录
//     * @param customerNo
//     * @param param
//     */
//    private void addElectricCostCalculateRecord(String customerNo, ElectricMeterAddParam param) {
//        CostCalculate calculate = new CostCalculate();
//        calculate.setCustomerNo(customerNo);
//        calculate.setMeterUserNo(param.getMeterUserNo());
//        String cno = CNoUtil.CreateCNo(DeviceType.ELECTRIC_METER.getCode(), param.getDeviceNo());
//        calculate.setCno(cno);
//        // 设置时间
//        this.setCostCalculateDate(calculate);
//        calculate.setCalcData(new Date());
//        BigDecimal readValue1 = param.getReadValue1();
//        if (readValue1 != null) {
//            calculate.setReadValue1(readValue1);
//        } else {
//            calculate.setReadValue1(BigDecimal.ZERO);
//        }
//        BigDecimal readValue2 = param.getReadValue2();
//        if (readValue2 != null) {
//            calculate.setReadValue2(readValue2);
//        } else {
//            calculate.setReadValue2(BigDecimal.ZERO);
//        }
//        BigDecimal readValue3 = param.getReadValue3();
//        if (readValue3 != null) {
//            calculate.setReadValue3(readValue3);
//        } else {
//            calculate.setReadValue3(BigDecimal.ZERO);
//        }
//        BigDecimal readValue4 = param.getReadValue4();
//        if (readValue4 != null) {
//            calculate.setReadValue4(readValue4);
//        } else {
//            calculate.setReadValue4(BigDecimal.ZERO);
//        }
//
//        BigDecimal readValue = param.getReadValue();
//        if (readValue != null) {
//            calculate.setReadValue(readValue);
//        } else {
//            calculate.setReadValue(BigDecimal.ZERO);
//        }
//
//
//        // 查询对应的电价方案
//        FeePriceSols priceSols = new FeePriceSols();
//        priceSols.setDictItemValue(param.getDictItemValue());
//        priceSols.setIsEnabled(1);
//        FeePriceSols feePriceSols = feePriceSolsService.selectOne(priceSols);
//        calculate.setPriceSolsCode(feePriceSols.getPriceSolsCode());
//        calculate.setCommFactorCnt(meterConfig.getCommFactorCnt());
//        String uuid = UuidUtil.getUuid();
//        costCalculateService.insertSelective(calculate);
//    }

    private void addWaterMeterInitPower(String customerNo,WaterMeterAddParam param,DeviceMeterConfig meterConfig) {
        MeterInitPower power = new MeterInitPower();
        power.setCustomerNo(customerNo);
        power.setMeterUserNo(param.getMeterUserNo());
        String cno = CNoUtil.CreateCNo(DeviceType.WATER_METER.getCode(), param.getDeviceNo());
        power.setCno(cno);

        // 查询对应的电价方案
        FeePriceSols priceSols = new FeePriceSols();
        priceSols.setDictItemValue(param.getDictItemValue());
        priceSols.setIsEnabled(1);
        FeePriceSols feePriceSols = feePriceSolsService.selectOne(priceSols);
        power.setPriceSolsCode(feePriceSols.getPriceSolsCode());
        power.setCommFactorCnt(meterConfig.getCommFactorCnt());
        // 设置时间
        this.setMeterInitPowerDate(power);
        BigDecimal readValue = param.getReadValue();
        if (readValue != null) {
            power.setReadValue(readValue);
        } else {
            power.setReadValue(BigDecimal.ZERO);
        }
        power.setReadValue1(BigDecimal.ZERO);
        power.setReadValue2(BigDecimal.ZERO);
        power.setReadValue3(BigDecimal.ZERO);
        power.setReadValue4(BigDecimal.ZERO);
        power.setCreateTime(new Date());
        meterInitPowerService.insertSelective(power);
    }

    /**
     * 水表添加每天费用信息记录
     * @param customerNo
     * @param param
     */
    private void addWaterCostCalculateRecord(String customerNo,WaterMeterAddParam param) {
        CostCalculate calculate = new CostCalculate();
        calculate.setCustomerNo(customerNo);
        calculate.setMeterUserNo(param.getMeterUserNo());
        String cno = CNoUtil.CreateCNo(DeviceType.WATER_METER.getCode(), param.getDeviceNo());
        calculate.setCno(cno);
        // 设置时间
        this.setCostCalculateDate(calculate);
        calculate.setCalcData(new Date());
        calculate.setReadValue1(BigDecimal.ZERO);
        calculate.setReadValue2(BigDecimal.ZERO);
        calculate.setReadValue3(BigDecimal.ZERO);
        calculate.setReadValue4(BigDecimal.ZERO);
        BigDecimal readValue = param.getReadValue();
        if (readValue != null) {
            calculate.setReadValue(readValue);
        } else {
            calculate.setReadValue(BigDecimal.ZERO);
        }

        calculate.setEqValue1(BigDecimal.ZERO);
        calculate.setEqValue2(BigDecimal.ZERO);
        calculate.setEqValue3(BigDecimal.ZERO);
        calculate.setEqValue4(BigDecimal.ZERO);
        calculate.setEqValue(BigDecimal.ZERO);

        // 查询对应的电价方案
        FeePriceSols priceSols = new FeePriceSols();
        priceSols.setDictItemValue(param.getDictItemValue());
        priceSols.setIsEnabled(1);
        FeePriceSols feePriceSols = feePriceSolsService.selectOne(priceSols);
        calculate.setPriceSolsCode(feePriceSols.getPriceSolsCode());
        calculate.setDayCalcMoney(BigDecimal.ZERO);
        costCalculateService.insertSelective(calculate);
    }

    private void addGasMeterInitPower(String customerNo,GasMeterAddParam param,DeviceMeterConfig meterConfig) {
        MeterInitPower power = new MeterInitPower();
        power.setCustomerNo(customerNo);
        power.setMeterUserNo(param.getMeterUserNo());
        String cno = CNoUtil.CreateCNo(DeviceType.GAS_METER.getCode(), param.getDeviceNo());
        power.setCno(cno);

        // 查询对应的电价方案
        FeePriceSols priceSols = new FeePriceSols();
        priceSols.setDictItemValue(param.getDictItemValue());
        priceSols.setIsEnabled(1);
        FeePriceSols feePriceSols = feePriceSolsService.selectOne(priceSols);
        power.setPriceSolsCode(feePriceSols.getPriceSolsCode());
        power.setCommFactorCnt(meterConfig.getCommFactorCnt());
        // 设置时间
        this.setMeterInitPowerDate(power);
        BigDecimal readValue = param.getReadValue();
        if (readValue != null) {
            power.setReadValue(readValue);
        } else {
            power.setReadValue(BigDecimal.ZERO);
        }
        power.setReadValue1(BigDecimal.ZERO);
        power.setReadValue2(BigDecimal.ZERO);
        power.setReadValue3(BigDecimal.ZERO);
        power.setReadValue4(BigDecimal.ZERO);
        power.setCreateTime(new Date());
        meterInitPowerService.insertSelective(power);
    }

    /**
     * 气表添加每天费用信息记录
     * @param customerNo
     * @param param
     */
    private void addGasCostCalculateRecord(String customerNo,GasMeterAddParam param) {
        CostCalculate calculate = new CostCalculate();
        calculate.setCustomerNo(customerNo);
        calculate.setMeterUserNo(param.getMeterUserNo());
        String cno = CNoUtil.CreateCNo(DeviceType.GAS_METER.getCode(), param.getDeviceNo());
        calculate.setCno(cno);
        // 设置时间
        this.setCostCalculateDate(calculate);
        calculate.setCalcData(new Date());
        calculate.setReadValue1(BigDecimal.ZERO);
        calculate.setReadValue2(BigDecimal.ZERO);
        calculate.setReadValue3(BigDecimal.ZERO);
        calculate.setReadValue4(BigDecimal.ZERO);
        BigDecimal readValue = param.getReadValue();
        if (readValue != null) {
            calculate.setReadValue(readValue);
        } else {
            calculate.setReadValue(BigDecimal.ZERO);
        }

        calculate.setEqValue1(BigDecimal.ZERO);
        calculate.setEqValue2(BigDecimal.ZERO);
        calculate.setEqValue3(BigDecimal.ZERO);
        calculate.setEqValue4(BigDecimal.ZERO);
        calculate.setEqValue(BigDecimal.ZERO);

        // 查询对应的电价方案
        FeePriceSols priceSols = new FeePriceSols();
        priceSols.setDictItemValue(param.getDictItemValue());
        priceSols.setIsEnabled(1);
        FeePriceSols feePriceSols = feePriceSolsService.selectOne(priceSols);
        calculate.setPriceSolsCode(feePriceSols.getPriceSolsCode());
        calculate.setDayCalcMoney(BigDecimal.ZERO);
        costCalculateService.insertSelective(calculate);
    }

    private void setCostCalculateDate(CostCalculate calculate) {
        // 查询系统设置的结算日
        SysConfig sysConfig = sysConfigService.queryByConfigName(SysConfigConstant.BALANCE_TIME.getConfigName());
        if (sysConfig != null) {
            String configValue = sysConfig.getConfigValue();
            Integer balanceTime = Integer.parseInt(configValue);
            int currentDay = DateUtil.getCurrentDay();
            if (currentDay < balanceTime) {
                // 取上个月的结算日
                Date lastMonthBalanceDay = DateUtil.getLastMonthBalanceDay(balanceTime);
                calculate.setLocalDataTime(lastMonthBalanceDay);
                calculate.setCalcData(lastMonthBalanceDay);
                calculate.setAccountDate(lastMonthBalanceDay);
            } else {
                // 取当月结算日
                Date currentMonthBalanceDay = DateUtil.getCurrentMonthBalanceDay(balanceTime);
                calculate.setLocalDataTime(currentMonthBalanceDay);
                calculate.setCalcData(currentMonthBalanceDay);
                calculate.setAccountDate(currentMonthBalanceDay);
            }
        }
        calculate.setCalcData(new Date());
        calculate.setReadValue1(BigDecimal.ZERO);
        calculate.setReadValue2(BigDecimal.ZERO);
        calculate.setReadValue3(BigDecimal.ZERO);
        calculate.setReadValue4(BigDecimal.ZERO);
        calculate.setReadValue(BigDecimal.ZERO);
        calculate.setEqValue1(BigDecimal.ZERO);
        calculate.setEqValue2(BigDecimal.ZERO);
        calculate.setEqValue3(BigDecimal.ZERO);
        calculate.setEqValue4(BigDecimal.ZERO);
        calculate.setEqValue(BigDecimal.ZERO);

        // 查询对应的电价方案
        FeePriceSols param = new FeePriceSols();
//        param.setDictItemValue(dictItemValue);
        param.setIsEnabled(1);
        FeePriceSols feePriceSols = feePriceSolsService.selectOne(param);
        calculate.setPriceSolsCode(feePriceSols.getPriceSolsCode());
        costCalculateService.insertSelective(calculate);
    }

    private void setMeterInitPowerDate(MeterInitPower power) {
        power.setCalcData(new Date());
        // 取当天00:00:00时间
        Date currentDayBeginTime = DateUtil.getStartTimeCurrentDay();
        power.setLocalDataTime(currentDayBeginTime);
        // 查询系统设置的结算日
        SysConfig sysConfig = sysConfigService.queryByConfigName(SysConfigConstant.BALANCE_TIME.getConfigName());
        if (sysConfig != null) {
            String configValue = sysConfig.getConfigValue();
            Integer balanceTime = Integer.parseInt(configValue);
            int currentDay = DateUtil.getCurrentDay();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            if (currentDay < balanceTime) {
                // 取上个月的结算日
                Date lastMonthBalanceDay = DateUtil.getLastMonthBalanceDay(balanceTime);
                power.setAccountDate(lastMonthBalanceDay);
            } else {
                // 取当月结算日
                Date currentMonthBalanceDay = DateUtil.getCurrentMonthBalanceDay(balanceTime);
                String format = simpleDateFormat.format(currentMonthBalanceDay);
                power.setAccountDate(currentMonthBalanceDay);
            }
        }
    }

    /**
     * 创建客户气表关联记录
     *
     * @param param
     * @param customerNo
     */
    private void addCustomerGasRelation(GasMeterAddParam param, String customerNo) {
        String meterUserNo = param.getMeterUserNo();
        Boolean flag = customerDevMapService.checkMeterUserNoExist4Add(DeviceType.GAS_METER.getCode(), meterUserNo);
        if (flag) {
            throw new BusinessException("气表表计户号[" + meterUserNo + "]已存在");
        }
        CustomerDevMap customerDevMap = new CustomerDevMap();
        customerDevMap.setCustomerNo(customerNo);
        String deviceType = DeviceType.GAS_METER.getCode();
        String cno = CNoUtil.CreateCNo(deviceType, param.getDeviceNo());
        customerDevMap.setCno(cno);
        customerDevMap.setDeviceType(deviceType);
        customerDevMap.setMeterUserNo(meterUserNo);
        customerDevMap.setTransformerNo(param.getTransformerNo());
        customerDevMap.setBuildingNo(param.getBuildingNo());
        customerDevMap.setDeviceNo(param.getDeviceNo());
        customerDevMapService.insertSelective(customerDevMap);
    }

    /**
     * 添加气表参数表信息
     *
     * @param param
     */
    private void addGasMeterParam(GasMeterAddParam param,DeviceMeterConfig meterConfig,String customerNo) {
        DeviceMeterParam meterParam = new DeviceMeterParam();
        BeanUtils.copyProperties(param, meterParam);
        String deviceType = DeviceType.GAS_METER.getCode();
        String cno = CNoUtil.CreateCNo(deviceType, param.getDeviceNo());
        meterParam.setCno(cno);

        String cjqNo = param.getCjqNo();
        Integer commPort = param.getCommPort();
        if (CustomerInfoConstant.GasCommPort.CARRIER.getCode().equals(commPort)) {
            if (StringUtils.isEmpty(cjqNo)) {
                meterParam.setCommCollectionNo(StringUtil.fillString("0", 12));
            } else {
                String commCollectionNo = CNoUtil.fillStrLengthTo12(cjqNo);
                meterParam.setCommCollectionNo(commCollectionNo);
            }
        } else {
            String commCollectionNo = CNoUtil.fillStrLengthTo16(cjqNo);
            meterParam.setCommCollectionNo(commCollectionNo);
        }

        if (CustomerInfoConstant.GasCommPort.LORA_WAN.getCode().equals(commPort)) {
            // 设置moteEui,appEui,appKey
            this.setSpecialColumn(meterParam);
        }

        // 水表，气表倍率都为1
        meterParam.setRatio(1);
        String jzqCno = CNoUtil.CreateCNo(DeviceType.JZQ.getCode(), param.getJzqNo());
        meterParam.setJzqCno(jzqCno);
        Integer collectDevType = param.getCollectDevType();
        if (CustomerInfoConstant.GasCollectDeviceType.MOTE.getCode().equals(collectDevType)) {
            // 节点
            meterParam.setMoteType(CustomerInfoConstant.MoteType.A_TYPE.getCode());
        } else {
            // 转换器
            meterParam.setMoteType(CustomerInfoConstant.MoteType.C_TYPE.getCode());
        }

        // 冗余字段
        meterParam.setCommFactorCnt(meterConfig.getCommFactorCnt());
        meterParam.setCommRule(meterConfig.getCommRule());

        meterParam.setLocalControl(CustomerInfoConstant.FeeControlType.REMOTE.getCode());
        meterParam.setCommAddr(param.getDeviceNo());
        meterParam.setDeviceType(Integer.parseInt(deviceType));
        meterParam.setCollectDevType(param.getCollectDevType());
        meterParam.setDictItemValue(param.getDictItemValue());
        meterParam.setCustomerNo(customerNo);
        meterParam.setParamFlag(param.getParamFlag());
        deviceMeterParamService.createDeviceMeterParam(meterParam);
    }

    /**
     * 修改气表时，返回设备参数表实体
     * @param param
     * @param meterConfig
     * @return
     */
    private DeviceMeterParam getGasDeviceMeterParam4Edit(GasMeterAddParam param,DeviceMeterConfig meterConfig) {
        DeviceMeterParam meterParam = new DeviceMeterParam();
        BeanUtils.copyProperties(param,meterParam);
        String cjqNo = param.getCjqNo();
        Integer commPort = param.getCommPort();
        if (CustomerInfoConstant.GasCommPort.CARRIER.getCode().equals(commPort)) {
            if (StringUtils.isEmpty(cjqNo)) {
                meterParam.setCommCollectionNo(StringUtil.fillString("0", 12));
            } else {
                String commCollectionNo = CNoUtil.fillStrLengthTo12(cjqNo);
                meterParam.setCommCollectionNo(commCollectionNo);
            }
        } else {
            String commCollectionNo = CNoUtil.fillStrLengthTo16(cjqNo);
            meterParam.setCommCollectionNo(commCollectionNo);
        }

        if (CustomerInfoConstant.GasCommPort.LORA_WAN.getCode().equals(commPort)) {
            // 设置moteEui,appEui,appKey
            this.setSpecialColumn(meterParam);
        }

        String jzqCno = CNoUtil.CreateCNo(DeviceType.JZQ.getCode(), param.getJzqNo());
        meterParam.setJzqCno(jzqCno);
        Integer collectDevType = param.getCollectDevType();
        if (CustomerInfoConstant.GasCollectDeviceType.MOTE.getCode().equals(collectDevType)) {
            // 节点
            meterParam.setMoteType(CustomerInfoConstant.MoteType.A_TYPE.getCode());
        } else {
            // 转换器
            meterParam.setMoteType(CustomerInfoConstant.MoteType.C_TYPE.getCode());
        }

        // 冗余字段
        meterParam.setCommFactorCnt(meterConfig.getCommFactorCnt());
        meterParam.setCommRule(meterConfig.getCommRule());
        meterParam.setCollectDevType(param.getCollectDevType());
        meterParam.setDictItemValue(param.getDictItemValue());
        meterParam.setParamFlag(param.getParamFlag());
        return meterParam;
    }


    /**
     * 添加气表设备信息
     *
     * @param param
     * @param currentUserId
     * @param orgNo
     */
    private void addGasDeviceInfo(GasMeterAddParam param, DeviceMeterConfig meterConfig,Long currentUserId, Long orgNo) {
        DeviceInfo deviceInfo = new DeviceInfo();
        BeanUtils.copyProperties(param, deviceInfo);
        String deviceType = DeviceType.GAS_METER.getCode();
        String deviceNo = param.getDeviceNo();
        String cno = CNoUtil.CreateCNo(deviceType, deviceNo);
        deviceInfo.setCno(cno);

        // 校验表号是否存在
        DeviceInfo info = deviceInfoService.queryDeviceInfoByCno(cno);
        if (info != null) {
            throw new BusinessException("气表表号[" + deviceNo + "]已存在");
        }

        // 气表表号
        deviceInfo.setDeviceNo(param.getDeviceNo());
        deviceInfo.setDeviceType(deviceType);

        String pDeviceNo = this.getGasPdeviceNo(param);
        deviceInfo.setpDeviceNo(pDeviceNo);

        Integer commPort = param.getCommPort();
        if (CustomerInfoConstant.GasCommPort.LORA_WAN.getCode().equals(commPort)) {
            deviceInfo.setRelyCno(cno);
        } else {
            String jzqCno = CNoUtil.CreateCNo(DeviceType.JZQ.getCode(), param.getJzqNo());
            deviceInfo.setRelyCno(jzqCno);
        }
        deviceInfo.setInstallDate(new Date());
        deviceInfo.setCreateUserId(currentUserId);
        deviceInfo.setOrgNo(orgNo);
        deviceInfo.setIsAutoSms(1);
        deviceInfo.setDeviceModel(meterConfig.getMeterMode());
        DictItem dictItem = dictItemService.findByCodeAndValue(DictCodeConstant.METER_FACTORY.getDictCode(), meterConfig.getMeterFactory());
        deviceInfo.setDeviceFactory(dictItem.getDictItemName());
        deviceInfo.setOffScheme(param.getOffScheme());
        deviceInfo.setOffParam(param.getOffParam());
        deviceInfo.setDeviceFactoryVal(meterConfig.getMeterFactory());
        deviceInfoService.insertSelective(deviceInfo);

        // 新增依赖设备表信息
        this.addDeviceInfoDeviceStateInfo(deviceInfo.getRelyCno(),commPort);
    }

    /**
     * 修改气表时，返回待更新的实体
     * @param param
     * @param meterConfig
     * @param orgNo
     * @return
     */
    private DeviceInfo getGasDeviceInfo4Edit(GasMeterAddParam param, DeviceMeterConfig meterConfig, Long orgNo) {
        DeviceInfo deviceInfo = new DeviceInfo();
        BeanUtils.copyProperties(param,deviceInfo);
        String deviceType = DeviceType.GAS_METER.getCode();
        String deviceNo = param.getDeviceNo();
        String cno = CNoUtil.CreateCNo(deviceType, deviceNo);
        String pDeviceNo = this.getGasPdeviceNo(param);
        deviceInfo.setpDeviceNo(pDeviceNo);

        Integer commPort = param.getCommPort();
        if (CustomerInfoConstant.GasCommPort.LORA_WAN.getCode().equals(commPort)) {
            deviceInfo.setRelyCno(cno);
        } else {
            String jzqCno = CNoUtil.CreateCNo(DeviceType.JZQ.getCode(), param.getJzqNo());
            deviceInfo.setRelyCno(jzqCno);
        }
        deviceInfo.setOrgNo(orgNo);
        deviceInfo.setDeviceModel(meterConfig.getMeterMode());
        DictItem dictItem = dictItemService.findByCodeAndValue(DictCodeConstant.METER_FACTORY.getDictCode(), meterConfig.getMeterFactory());
        deviceInfo.setDeviceFactory(dictItem.getDictItemName());
//        deviceInfo.setOffScheme(param.getOffScheme());
//        deviceInfo.setOffParam(param.getOffParam());
        deviceInfo.setDeviceFactoryVal(meterConfig.getMeterFactory());
        return deviceInfo;
    }

    /**
     * 获取气表的pDeviceNo
     * @param param
     * @return
     */
    private String getGasPdeviceNo(GasMeterAddParam param) {
        String cjqNo = param.getCjqNo();
        String jzqNo = param.getJzqNo();
        String pDeviceNo = CNoUtil.CreateCNo(DeviceType.JZQ.getCode(), jzqNo);
        if (!StringUtils.isEmpty(cjqNo)) {
            Integer collectDevType = param.getCollectDevType();
            if (CustomerInfoConstant.GasCollectDeviceType.CONVERTER.getCode().equals(collectDevType)) {
                pDeviceNo = CNoUtil.CreateCNo(DeviceType.CONVERTER.getCode(), cjqNo);
            }
        }
        return pDeviceNo;
    }

    /**
     * 创建客户水表关联记录
     * @param param
     * @param customerNo
     */
    private void addCustomerWaterRelation(WaterMeterAddParam param, String customerNo) {
        String meterUserNo = param.getMeterUserNo();
        Boolean flag = customerDevMapService.checkMeterUserNoExist4Add(DeviceType.WATER_METER.getCode(), meterUserNo);
        if (flag) {
            throw new BusinessException("水表表计户号[" + meterUserNo + "]已存在");
        }
        CustomerDevMap customerDevMap = new CustomerDevMap();
        customerDevMap.setCustomerNo(customerNo);
        String deviceType = DeviceType.WATER_METER.getCode();
        String cno = CNoUtil.CreateCNo(deviceType, param.getDeviceNo());
        customerDevMap.setCno(cno);
        customerDevMap.setDeviceType(deviceType);
        customerDevMap.setMeterUserNo(meterUserNo);
        customerDevMap.setTransformerNo(param.getTransformerNo());
        customerDevMap.setBuildingNo(param.getBuildingNo());
        customerDevMap.setDeviceNo(param.getDeviceNo());
        customerDevMapService.insertSelective(customerDevMap);
    }

    /**
     * 添加水表参数表信息
     * @param param
     */
    private void addWaterMeterParam(WaterMeterAddParam param,DeviceMeterConfig meterConfig,String customerNo) {
        // 添加水表参数表
        DeviceMeterParam meterParam = new DeviceMeterParam();
        BeanUtils.copyProperties(param, meterParam);
        String deviceType = DeviceType.WATER_METER.getCode();
        String cno = CNoUtil.CreateCNo(deviceType, param.getDeviceNo());
        meterParam.setCno(cno);
        String cjqNo = param.getCjqNo();
        Integer commPort = param.getCommPort();
        if (CustomerInfoConstant.WaterCommPort.CARRIER.getCode().equals(commPort)) {
            if (StringUtils.isEmpty(cjqNo)) {
                meterParam.setCommCollectionNo(StringUtil.fillString("0", 12));
            } else {
                String commCollectionNo = CNoUtil.fillStrLengthTo12(cjqNo);
                meterParam.setCommCollectionNo(commCollectionNo);
            }
        } else if (CustomerInfoConstant.WaterCommPort.LORA_WAN.getCode().equals(commPort)) {
            String commCollectionNo = CNoUtil.fillStrLengthTo16(cjqNo);
            meterParam.setCommCollectionNo(commCollectionNo);
        }

        if (CustomerInfoConstant.WaterCommPort.LORA_WAN.getCode().equals(commPort)) {
            // 设置moteEui,appEui,appKey
            this.setSpecialColumn(meterParam);
        }

        // 水表，气表的倍率都为1
        meterParam.setRatio(1);
        String jzqCno = CNoUtil.CreateCNo(DeviceType.JZQ.getCode(), param.getJzqNo());
        meterParam.setJzqCno(jzqCno);
        Integer collectDevType = param.getCollectDevType();
        if (CustomerInfoConstant.WaterCollectDeviceType.MOTE.getCode().equals(collectDevType)) {
            // 节点
            meterParam.setMoteType(CustomerInfoConstant.MoteType.A_TYPE.getCode());
        } else {
            // 转换器
            meterParam.setMoteType(CustomerInfoConstant.MoteType.C_TYPE.getCode());
        }

        // 冗余字段
        meterParam.setCommFactorCnt(meterConfig.getCommFactorCnt());
        meterParam.setCommRule(meterConfig.getCommRule());

        meterParam.setLocalControl(CustomerInfoConstant.FeeControlType.REMOTE.getCode());
        meterParam.setCommAddr(param.getDeviceNo());
        meterParam.setDeviceType(Integer.parseInt(deviceType));
        meterParam.setCollectDevType(param.getCollectDevType());
        meterParam.setDictItemValue(param.getDictItemValue());
        meterParam.setCustomerNo(customerNo);
        meterParam.setParamFlag(param.getParamFlag());
        deviceMeterParamService.createDeviceMeterParam(meterParam);
    }

    /**
     * 水表修改时，返回设备参数表实体
     * @param param
     * @param meterConfig
     * @return
     */
    private DeviceMeterParam getWaterDeviceMeterParam4Edit(WaterMeterAddParam param,DeviceMeterConfig meterConfig) {
        DeviceMeterParam meterParam = new DeviceMeterParam();
        BeanUtils.copyProperties(param,meterParam);
        String cjqNo = param.getCjqNo();
        Integer commPort = param.getCommPort();
        if (CustomerInfoConstant.WaterCommPort.CARRIER.getCode().equals(commPort)) {
            if (StringUtils.isEmpty(cjqNo)) {
                meterParam.setCommCollectionNo(StringUtil.fillString("0", 12));
            } else {
                String commCollectionNo = CNoUtil.fillStrLengthTo12(cjqNo);
                meterParam.setCommCollectionNo(commCollectionNo);
            }
        } else if (CustomerInfoConstant.WaterCommPort.LORA_WAN.getCode().equals(commPort)) {
            String commCollectionNo = CNoUtil.fillStrLengthTo16(cjqNo);
            meterParam.setCommCollectionNo(commCollectionNo);
        }

        if (CustomerInfoConstant.WaterCommPort.LORA_WAN.getCode().equals(commPort)) {
            // 设置moteEui,appEui,appKey
            this.setSpecialColumn(meterParam);
        }

        String jzqCno = CNoUtil.CreateCNo(DeviceType.JZQ.getCode(), param.getJzqNo());
        meterParam.setJzqCno(jzqCno);
        Integer collectDevType = param.getCollectDevType();
        if (CustomerInfoConstant.WaterCollectDeviceType.MOTE.getCode().equals(collectDevType)) {
            // 节点
            meterParam.setMoteType(CustomerInfoConstant.MoteType.A_TYPE.getCode());
        } else {
            // 转换器
            meterParam.setMoteType(CustomerInfoConstant.MoteType.C_TYPE.getCode());
        }

        // 冗余字段
        meterParam.setCommFactorCnt(meterConfig.getCommFactorCnt());
        meterParam.setCommRule(meterConfig.getCommRule());
        meterParam.setCollectDevType(param.getCollectDevType());
        meterParam.setDictItemValue(param.getDictItemValue());
        meterParam.setParamFlag(param.getParamFlag());
        return meterParam;
    }


    /**
     * 添加水表设备信息
     *
     * @param param
     * @param currentUserId
     * @param orgNo
     */
    private void addWaterDeviceInfo(WaterMeterAddParam param, DeviceMeterConfig meterConfig,Long currentUserId, Long orgNo) {
        DeviceInfo deviceInfo = new DeviceInfo();
        BeanUtils.copyProperties(param, deviceInfo);
        String deviceType = DeviceType.WATER_METER.getCode();
        String deviceNo = param.getDeviceNo();
        String cno = CNoUtil.CreateCNo(deviceType, deviceNo);

        // 校验表号是否存在
        DeviceInfo info = deviceInfoService.queryDeviceInfoByCno(cno);
        if (info != null) {
            throw new BusinessException("水表表号[" + deviceNo + "]已存在");
        }

        deviceInfo.setCno(cno);
        // 水表表号
        deviceInfo.setDeviceNo(param.getDeviceNo());
        deviceInfo.setDeviceType(deviceType);

        String pDeviceNo = this.getWaterPdeviceNo(param);
        deviceInfo.setpDeviceNo(pDeviceNo);

        Integer commPort = param.getCommPort();
        if (CustomerInfoConstant.WaterCommPort.LORA_WAN.getCode().equals(commPort)) {
            deviceInfo.setRelyCno(cno);
        } else {
            String jzqCno = CNoUtil.CreateCNo(DeviceType.JZQ.getCode(), param.getJzqNo());
            deviceInfo.setRelyCno(jzqCno);
        }
        deviceInfo.setInstallDate(new Date());
        deviceInfo.setCreateUserId(currentUserId);
        deviceInfo.setOrgNo(orgNo);
        deviceInfo.setIsAutoSms(1);
        deviceInfo.setDeviceModel(meterConfig.getMeterMode());
        DictItem dictItem = dictItemService.findByCodeAndValue(DictCodeConstant.METER_FACTORY.getDictCode(), meterConfig.getMeterFactory());
        deviceInfo.setDeviceFactory(dictItem.getDictItemName());
        deviceInfo.setOffScheme(param.getOffScheme());
        deviceInfo.setOffParam(param.getOffParam());
        deviceInfo.setDeviceFactoryVal(meterConfig.getMeterFactory());
        deviceInfoService.insertSelective(deviceInfo);

        // 新增依赖设备表信息
        this.addDeviceInfoDeviceStateInfo(deviceInfo.getRelyCno(),commPort);
    }

    /**
     * 修改水表时，返回待更新的实体
     * @param param
     * @param meterConfig
     * @param orgNo
     * @return
     */
    private DeviceInfo getWaterDeviceInfo4Edit(WaterMeterAddParam param, DeviceMeterConfig meterConfig,Long orgNo) {
        DeviceInfo deviceInfo = new DeviceInfo();
        BeanUtils.copyProperties(param,deviceInfo);
        String deviceType = DeviceType.WATER_METER.getCode();
        String deviceNo = param.getDeviceNo();
        String cno = CNoUtil.CreateCNo(deviceType, deviceNo);
        String pDeviceNo = this.getWaterPdeviceNo(param);
        deviceInfo.setpDeviceNo(pDeviceNo);

        Integer commPort = param.getCommPort();
        if (CustomerInfoConstant.WaterCommPort.LORA_WAN.getCode().equals(commPort)) {
            deviceInfo.setRelyCno(cno);
        } else {
            String jzqCno = CNoUtil.CreateCNo(DeviceType.JZQ.getCode(), param.getJzqNo());
            deviceInfo.setRelyCno(jzqCno);
        }
        deviceInfo.setOrgNo(orgNo);
        deviceInfo.setDeviceModel(meterConfig.getMeterMode());
        DictItem dictItem = dictItemService.findByCodeAndValue(DictCodeConstant.METER_FACTORY.getDictCode(), meterConfig.getMeterFactory());
        deviceInfo.setDeviceFactory(dictItem.getDictItemName());
//        deviceInfo.setOffScheme(param.getOffScheme());
//        deviceInfo.setOffParam(param.getOffParam());
        deviceInfo.setDeviceFactoryVal(meterConfig.getMeterFactory());
        return deviceInfo;
    }

    /**
     * 获取水表的pDeviceNo
     *
     * @param param
     * @return
     */
    private String getWaterPdeviceNo(WaterMeterAddParam param) {
        String cjqNo = param.getCjqNo();
        String jzqNo = param.getJzqNo();
        // 默认是集中器的cno
        String pDeviceNo = CNoUtil.CreateCNo(DeviceType.JZQ.getCode(), jzqNo);
        if (!StringUtils.isEmpty(cjqNo)) {
            Integer collectDevType = param.getCollectDevType();
            if (CustomerInfoConstant.WaterCollectDeviceType.CONVERTER.getCode().equals(collectDevType)) {
                pDeviceNo = CNoUtil.CreateCNo(DeviceType.CONVERTER.getCode(), cjqNo);
            }
        }
        return pDeviceNo;
    }


    /**
     * 创建客户电表关联记录
     *
     * @param param
     * @param customerNo
     */
    private void addCustomerElectricRelation(ElectricMeterAddParam param, String customerNo) {
        String meterUserNo = param.getMeterUserNo();
        Boolean flag = customerDevMapService.checkMeterUserNoExist4Add(DeviceType.ELECTRIC_METER.getCode(), meterUserNo);
        if (flag) {
            throw new BusinessException("电表表计户号[" + meterUserNo + "]已存在");
        }

        CustomerDevMap customerDevMap = new CustomerDevMap();
        customerDevMap.setCustomerNo(customerNo);
        String deviceType = DeviceType.ELECTRIC_METER.getCode();
        String cno = CNoUtil.CreateCNo(deviceType, param.getDeviceNo());
        customerDevMap.setCno(cno);
        customerDevMap.setDeviceType(deviceType);
        customerDevMap.setMeterUserNo(meterUserNo);
        customerDevMap.setTransformerNo(param.getTransformerNo());
        customerDevMap.setBuildingNo(param.getBuildingNo());
        customerDevMap.setDeviceNo(param.getDeviceNo());
        customerDevMap.setElecMeterCategory(param.getElecMeterCategory());
        customerDevMap.setElecType(param.getElecType());
        customerDevMapService.insertSelective(customerDevMap);
    }

    /**
     * 添加电表参数表信息
     *
     * @param param
     */
    private void addElectricMeterParam(ElectricMeterAddParam param,DeviceMeterConfig meterConfig,String customerNo) {
        // 添加电表参数表
        DeviceMeterParam meterParam = new DeviceMeterParam();
        BeanUtils.copyProperties(param, meterParam);
        String deviceType = DeviceType.ELECTRIC_METER.getCode();
        String cno = CNoUtil.CreateCNo(deviceType, param.getDeviceNo());
        meterParam.setCno(cno);
        String cjqNo = param.getCjqNo();
        Integer commPort = param.getCommPort();
        if (CustomerInfoConstant.ElectricCommPort.CARRIER.getCode().equals(commPort)) {
            if (StringUtils.isEmpty(cjqNo)) {
                meterParam.setCommCollectionNo(StringUtil.fillString("0", 12));
            } else {
                String commCollectionNo = CNoUtil.fillStrLengthTo12(cjqNo);
                meterParam.setCommCollectionNo(commCollectionNo);
            }
        } else if (CustomerInfoConstant.ElectricCommPort.LORA_WAN.getCode().equals(commPort)) {
            String commCollectionNo = CNoUtil.fillStrLengthTo16(cjqNo);
            meterParam.setCommCollectionNo(commCollectionNo);
        } else if (CustomerInfoConstant.ElectricCommPort.DIRECT_ONE.getCode().equals(commPort) ||
                CustomerInfoConstant.ElectricCommPort.DIRECT_TWO.getCode().equals(commPort) ||
                CustomerInfoConstant.ElectricCommPort.DIRECT_THREE.getCode().equals(commPort)) {
            meterParam.setCommCollectionNo(StringUtil.fillString("0", 12));
        }

        if (CustomerInfoConstant.ElectricCommPort.LORA_WAN.getCode().equals(commPort)) {
            // 设置moteEui,appEui,appKey
            this.setSpecialColumn(meterParam);
        }

        // 冗余字段
        meterParam.setCommFactorCnt(meterConfig.getCommFactorCnt());
        meterParam.setCommRule(meterConfig.getCommRule());

        meterParam.setRatio(param.getRatio());
        String jzqCno = CNoUtil.CreateCNo(DeviceType.JZQ.getCode(), param.getJzqNo());
        meterParam.setJzqCno(jzqCno);
        // 电表均是C类节点
        meterParam.setMoteType(CustomerInfoConstant.MoteType.C_TYPE.getCode());
        String commAddr = CNoUtil.fillStrLengthTo12(param.getDeviceNo());
        meterParam.setCommAddr(commAddr);
        meterParam.setDeviceType(Integer.parseInt(deviceType));
        meterParam.setCollectDevType(param.getCollectDevType());
        meterParam.setDictItemValue(param.getDictItemValue());
        meterParam.setCustomerNo(customerNo);
        meterParam.setParamFlag(param.getParamFlag());
        deviceMeterParamService.createDeviceMeterParam(meterParam);
    }

    /**
     * 修改电表时，返回待更新的设备参数表实体
     * @param param
     * @param meterConfig
     * @return
     */
    private DeviceMeterParam getElectricDeviceMeterParam4Edit(ElectricMeterAddParam param, DeviceMeterConfig meterConfig) {
        // 添加电表参数表
        DeviceMeterParam meterParam = new DeviceMeterParam();
        BeanUtils.copyProperties(param,meterParam);
        String deviceType = DeviceType.ELECTRIC_METER.getCode();
        String cno = CNoUtil.CreateCNo(deviceType, param.getDeviceNo());
        meterParam.setCno(cno);
        String cjqNo = param.getCjqNo();
        Integer commPort = param.getCommPort();
        if (CustomerInfoConstant.ElectricCommPort.CARRIER.getCode().equals(commPort)) {
            if (StringUtils.isEmpty(cjqNo)) {
                meterParam.setCommCollectionNo(StringUtil.fillString("0", 12));
            } else {
                String commCollectionNo = CNoUtil.fillStrLengthTo12(cjqNo);
                meterParam.setCommCollectionNo(commCollectionNo);
            }
        } else if (CustomerInfoConstant.ElectricCommPort.LORA_WAN.getCode().equals(commPort)) {
            String commCollectionNo = CNoUtil.fillStrLengthTo16(cjqNo);
            meterParam.setCommCollectionNo(commCollectionNo);
        } else if (CustomerInfoConstant.ElectricCommPort.DIRECT_ONE.getCode().equals(commPort) ||
                CustomerInfoConstant.ElectricCommPort.DIRECT_TWO.getCode().equals(commPort) ||
                CustomerInfoConstant.ElectricCommPort.DIRECT_THREE.getCode().equals(commPort)) {
            meterParam.setCommCollectionNo(StringUtil.fillString("0", 12));
        }

        if (CustomerInfoConstant.ElectricCommPort.LORA_WAN.getCode().equals(commPort)) {
            // 设置moteEui,appEui,appKey
            this.setSpecialColumn(meterParam);
        }

        // 冗余字段
        meterParam.setCommFactorCnt(meterConfig.getCommFactorCnt());
        meterParam.setCommRule(meterConfig.getCommRule());

        meterParam.setRatio(param.getRatio());
        String jzqCno = CNoUtil.CreateCNo(DeviceType.JZQ.getCode(), param.getJzqNo());
        meterParam.setJzqCno(jzqCno);
        meterParam.setCollectDevType(param.getCollectDevType());
        meterParam.setDictItemValue(param.getDictItemValue());
        meterParam.setParamFlag(param.getParamFlag());
        return meterParam;
    }


    /**
     * 添加电表设备信息
     *
     * @param param
     * @param currentUserId
     * @param orgNo
     */
    private void addElectricDeviceInfo(ElectricMeterAddParam param,DeviceMeterConfig meterConfig,Long currentUserId, Long orgNo) {
        DeviceInfo deviceInfo = new DeviceInfo();
        BeanUtils.copyProperties(param, deviceInfo);
        String deviceType = DeviceType.ELECTRIC_METER.getCode();
        String deviceNo = param.getDeviceNo();
        String cno = CNoUtil.CreateCNo(deviceType, deviceNo);

        // 校验表号是否存在
        DeviceInfo info = deviceInfoService.queryDeviceInfoByCno(cno);
        if (info != null) {
            throw new BusinessException("电表表号[" + deviceNo + "]已存在");
        }

        deviceInfo.setCno(cno);
        // 电表表号16位
        deviceInfo.setDeviceNo(param.getDeviceNo());
        deviceInfo.setDeviceType(deviceType);

        String pDeviceNo = this.getElectricPdeviceNo(param);
        deviceInfo.setpDeviceNo(pDeviceNo);

        Integer commPort = param.getCommPort();
        if (CustomerInfoConstant.ElectricCommPort.LORA_WAN.getCode().equals(commPort)) {
            deviceInfo.setRelyCno(cno);
        } else {
            String jzqCno = CNoUtil.CreateCNo(DeviceType.JZQ.getCode(), param.getJzqNo());
            deviceInfo.setRelyCno(jzqCno);
        }
        deviceInfo.setInstallDate(new Date());
        deviceInfo.setCreateUserId(currentUserId);
        deviceInfo.setOrgNo(orgNo);
        deviceInfo.setIsAutoSms(1);
        deviceInfo.setDeviceModel(meterConfig.getMeterMode());
        DictItem dictItem = dictItemService.findByCodeAndValue(DictCodeConstant.METER_FACTORY.getDictCode(), meterConfig.getMeterFactory());
        deviceInfo.setDeviceFactory(dictItem.getDictItemName());
        deviceInfo.setOffScheme(param.getOffScheme());
        deviceInfo.setOffParam(param.getOffParam());
        deviceInfo.setDeviceFactoryVal(meterConfig.getMeterFactory());
        deviceInfoService.insertSelective(deviceInfo);

        // 新增依赖设备表信息
        this.addDeviceInfoDeviceStateInfo(deviceInfo.getRelyCno(),commPort);
    }

    /**
     * 添加设备状态表记录
     * @param relyCno
     * @param commPort
     */
    private void addDeviceInfoDeviceStateInfo(String relyCno,Integer commPort) {
        DeviceInfoDeviceState state = deviceInfoDeviceStateService.queryByCno(relyCno);
        if (state == null) {
            DeviceInfoDeviceState deviceState = new DeviceInfoDeviceState();
            deviceState.setCno(relyCno);
            deviceState.setIsOnline(0);
            deviceState.setCommPort(commPort);

            // TODO 暂时按照orgNo=1000查询
            OrgApp orgApp = orgAppService.queryByOrgNo(1000L);
            deviceState.setAppEui(orgApp.getAppEui());
            deviceInfoDeviceStateService.insertSelective(deviceState);
        }
    }


    /**
     * 修改电表时，获取待更新的电表实体
     * @param param
     * @param meterConfig
     * @param orgNo
     * @return
     */
    private DeviceInfo getElectricDeviceInfo4Edit(ElectricMeterAddParam param, DeviceMeterConfig meterConfig, Long orgNo) {
        DeviceInfo deviceInfo = new DeviceInfo();
        BeanUtils.copyProperties(param,deviceInfo);
        String deviceType = DeviceType.ELECTRIC_METER.getCode();
        String deviceNo = param.getDeviceNo();
        String cno = CNoUtil.CreateCNo(deviceType, deviceNo);
        String pDeviceNo = this.getElectricPdeviceNo(param);
        deviceInfo.setpDeviceNo(pDeviceNo);

        Integer commPort = param.getCommPort();
        if (CustomerInfoConstant.ElectricCommPort.LORA_WAN.getCode().equals(commPort)) {
            deviceInfo.setRelyCno(cno);
        } else {
            String jzqCno = CNoUtil.CreateCNo(DeviceType.JZQ.getCode(), param.getJzqNo());
            deviceInfo.setRelyCno(jzqCno);
        }
        deviceInfo.setOrgNo(orgNo);
        deviceInfo.setDeviceModel(meterConfig.getMeterMode());
        DictItem dictItem = dictItemService.findByCodeAndValue(DictCodeConstant.METER_FACTORY.getDictCode(), meterConfig.getMeterFactory());
        deviceInfo.setDeviceFactory(dictItem.getDictItemName());
//        deviceInfo.setOffScheme(param.getOffScheme());
//        deviceInfo.setOffParam(param.getOffParam());
        deviceInfo.setDeviceFactoryVal(meterConfig.getMeterFactory());
        return deviceInfo;
    }

    /**
     * 获取电表设备pDeviceNo
     * @param param
     * @return
     */
    private String getElectricPdeviceNo(ElectricMeterAddParam param) {
        String cjqNo = param.getCjqNo();
        String jzqNo = param.getJzqNo();
        String pDeviceNo = CNoUtil.CreateCNo(DeviceType.JZQ.getCode(), jzqNo);
        if (!StringUtils.isEmpty(cjqNo)) {
            Integer collectDevType = param.getCollectDevType();
            if (CustomerInfoConstant.ElectricCollectDeviceType.COLLECTOR.getCode().equals(collectDevType)) {
                pDeviceNo = CNoUtil.CreateCNo(DeviceType.CJQ.getCode(), cjqNo);
            }
        }
        return pDeviceNo;
    }

    /**
     * 添加集中器
     *
     * @param jzqNo
     * @param currentUserId
     * @param orgNo
     */
    private void addJzq(String jzqNo,Integer commPort, Long currentUserId, Long orgNo) {
        String cno = CNoUtil.CreateCNo(DeviceType.JZQ.getCode(), jzqNo);
        DeviceInfo tempDeviceInfo = deviceInfoService.queryDeviceInfoByCno(cno);
        if (tempDeviceInfo != null) {
            // 集中器已存在，直接返回
            logger.info("集中器号[" + jzqNo + "]已存在");
            return;
        }

        // 添加集中器参数表信息
        ServiceHostPara hostPara = sysConfigService.getServiceHostPara();
        DeviceConnParam deviceConnParam = new DeviceConnParam();
        deviceConnParam.setCno(cno);
        deviceConnParam.setWebsiteIp(hostPara.getIp());
        deviceConnParam.setWebsitePort(hostPara.getPort());
        deviceConnParam.setSpareIp(hostPara.getIp());
        deviceConnParam.setSparePort(hostPara.getPort());
        deviceConnParam.setApn(hostPara.getApn());
        deviceConnParamService.insertSelective(deviceConnParam);

        // 添加集中器设备表信息
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setCno(cno);
        deviceInfo.setDeviceNo(jzqNo);
        deviceInfo.setDeviceType(DeviceType.JZQ.getCode());
        Date now = new Date();
        deviceInfo.setInstallDate(now);
        deviceInfo.setCreateUserId(currentUserId);
        deviceInfo.setCreateTime(now);
        String relyCno = CNoUtil.CreateCNo(DeviceType.JZQ.getCode(), jzqNo);
        deviceInfo.setRelyCno(relyCno);
        deviceInfo.setOrgNo(orgNo);
        deviceInfoService.insertSelective(deviceInfo);

        // 新增依赖设备表信息
        this.addDeviceInfoDeviceStateInfo(deviceInfo.getRelyCno(),commPort);
    }

    /**
     * 添加采集器
     * @param jzqNo
     * @param cjqNo
     * @param currentUserId
     */
    private void addCjq(String jzqNo, String cjqNo, Integer commPort, Long currentUserId, Long orgNo) {
        // 生成采集器设备编号
        String cno = CNoUtil.CreateCNo(DeviceType.CJQ.getCode(), cjqNo);

        // 判断采集器是否已存在
        DeviceInfo tempDeviceInfo = deviceInfoService.queryDeviceInfoByCno(cno);
        if (tempDeviceInfo != null) {
            logger.info("采集器号[" + cjqNo + "]已存在");
            return;
        }

        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setCno(cno);
        deviceInfo.setDeviceNo(cjqNo);
        deviceInfo.setDeviceType(DeviceType.CJQ.getCode());
        String pDeviceNo = CNoUtil.CreateCNo(DeviceType.JZQ.getCode(), jzqNo);
        deviceInfo.setpDeviceNo(pDeviceNo);

        if (CustomerInfoConstant.ElectricCommPort.LORA_WAN.getCode().equals(commPort)) {
            String relyCno = CNoUtil.CreateCNo(DeviceType.CJQ.getCode(), cjqNo);
            deviceInfo.setRelyCno(relyCno);
        } else {
            String relyCno = CNoUtil.CreateCNo(DeviceType.JZQ.getCode(), jzqNo);
            deviceInfo.setRelyCno(relyCno);
        }

        Date now = new Date();
        deviceInfo.setInstallDate(now);
        deviceInfo.setCreateTime(now);
        deviceInfo.setCreateUserId(currentUserId);
        deviceInfo.setOrgNo(orgNo);
        deviceInfoService.insertSelective(deviceInfo);

        // 新增依赖设备表信息
        this.addDeviceInfoDeviceStateInfo(deviceInfo.getRelyCno(),commPort);
    }


    /**
     * 添加水，气表上的转换器
     * @param jzqNo
     * @param cjqNo
     * @param currentUserId
     */
    private void addConverter(String deviceType, String jzqNo, String cjqNo, Integer commPort, Long currentUserId, Long orgNo) {
        // 生成转换器设备编号
        String cno = CNoUtil.CreateCNo(DeviceType.CONVERTER.getCode(), cjqNo);

        // 判断转换器是否已存在
        DeviceInfo tempDeviceInfo = deviceInfoService.queryDeviceInfoByCno(cno);
        if (tempDeviceInfo != null) {
            logger.info("转换器号[" + cjqNo + "]已存在");
            return;
        }

        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setCno(cno);
        deviceInfo.setDeviceNo(cjqNo);
        deviceInfo.setDeviceType(DeviceType.CONVERTER.getCode());
        String pDeviceNo = CNoUtil.CreateCNo(DeviceType.JZQ.getCode(), jzqNo);
        deviceInfo.setpDeviceNo(pDeviceNo);

        if (DeviceType.WATER_METER.getCode().equals(deviceType)) {
            if (CustomerInfoConstant.WaterCommPort.LORA_WAN.getCode().equals(commPort)) {
                // 采集方式：LoraWAN，都是以自身为relyCno
                String relyCno = CNoUtil.CreateCNo(DeviceType.CONVERTER.getCode(), cjqNo);
                deviceInfo.setRelyCno(relyCno);
            } else if (CustomerInfoConstant.WaterCommPort.CARRIER.getCode().equals(commPort)) {
                // 采集方式：载波，都是以集中器为relyCno
                String relyCno = CNoUtil.CreateCNo(DeviceType.JZQ.getCode(), jzqNo);
                deviceInfo.setRelyCno(relyCno);
            }
        } else if (DeviceType.GAS_METER.getCode().equals(deviceType)) {
            if (CustomerInfoConstant.GasCommPort.LORA_WAN.getCode().equals(commPort)) {
                // 采集方式：LoraWAN，都是以自身为relyCno
                String relyCno = CNoUtil.CreateCNo(DeviceType.CONVERTER.getCode(), cjqNo);
                deviceInfo.setRelyCno(relyCno);
            } else if (CustomerInfoConstant.GasCommPort.CARRIER.getCode().equals(commPort)) {
                // 采集方式：载波，都是以集中器为relyCno
                String relyCno = CNoUtil.CreateCNo(DeviceType.JZQ.getCode(), jzqNo);
                deviceInfo.setRelyCno(relyCno);
            }
        }

        Date now = new Date();
        deviceInfo.setInstallDate(now);
        deviceInfo.setCreateTime(now);
        deviceInfo.setCreateUserId(currentUserId);
        deviceInfo.setOrgNo(orgNo);
        deviceInfoService.insertSelective(deviceInfo);

        // 新增依赖设备表信息
        this.addDeviceInfoDeviceStateInfo(deviceInfo.getRelyCno(),commPort);
    }

    /**
     * 客户档案保存时相关校验
     * @param param
     */
    private void check(CustomerInfoCreateParam param) {
        CustomerInfoAddParam customerInfo = param.getCustomerInfo();
        Long orgNo = customerInfo.getOrgNo();
        // 检查门牌编号是否存在
        this.checkByPropertyName4Add(customerInfo.getPropertyName());

        // 校验告警金额
        this.checkAlarm(customerInfo);

        // 校验电表
        List<ElectricMeterAddParam> electricMeter = param.getElectricMeter();
        if (!CollectionUtils.isEmpty(electricMeter)) {
            // 前端表号，表计户号是否重复
            this.checkElectricList(electricMeter);
            // 跟数据库中对比，表号，表计户号是否存在
            for (ElectricMeterAddParam meterAddParam : electricMeter) {
                this.checkElectricMeter(orgNo,meterAddParam);
            }
        }

        // 校验水表
        List<WaterMeterAddParam> waterMeter = param.getWaterMeter();
        if (!CollectionUtils.isEmpty(waterMeter)) {
            // 前端表号，表计户号是否重复
            this.checkWaterList(waterMeter);
            // 跟数据库中对比，表号，表计户号是否存在
            for (WaterMeterAddParam meterAddParam : waterMeter) {
                this.checkWaterMeter(meterAddParam);
            }
        }

        // 校验气表
        List<GasMeterAddParam> gasMeter = param.getGasMeter();
        if (!CollectionUtils.isEmpty(gasMeter)) {
            // 前端表号，表计户号是否重复
            this.checkGasList(gasMeter);
            // 跟数据库中对比，表号，表计户号是否存在
            for (GasMeterAddParam meterAddParam : gasMeter) {
                this.checkGasMeter(meterAddParam);
            }
        }
    }

    /**
     * 校验告警金额
     * @param param
     */
    private void checkAlarm(CustomerInfoAddParam param) {
        boolean flag = MathUtil.isGreaterEqual(param.getAlarmThreshold(), param.getAlarmThreshold1());
        if (!flag) {
            throw new BusinessException("告警阈值1不能小于告警阈值2");
        }

        boolean flag2 = MathUtil.isGreaterEqual(param.getAlarmThreshold1(), param.getAlarmThreshold2());
        if (!flag2) {
            throw new BusinessException("告警阈值2不能小于告警阈值3");
        }
    }

    /**
     * 用户信息更新时，校验告警金额
     * @param editVo
     */
    private void checkAlarm4Edit(CustomerInfoEditVo editVo) {
        boolean flag = MathUtil.isGreaterEqual(editVo.getAlarmThreshold(), editVo.getAlarmThreshold1());
        if (!flag) {
            throw new BusinessException("告警阈值1不能小于告警阈值2");
        }

        boolean flag2 = MathUtil.isGreaterEqual(editVo.getAlarmThreshold1(), editVo.getAlarmThreshold2());
        if (!flag2) {
            throw new BusinessException("告警阈值2不能小于告警阈值3");
        }
    }

    /**
     * 客户档案添加时，校验前端电表列表中参数合法性
     * @param electricMeter
     */
    private void checkElectricList(List<ElectricMeterAddParam> electricMeter) {
        int size = electricMeter.size();
        if (size > 1) {
            Set<String> deviceNoSet = Sets.newHashSet();
            Set<String> meterUserNoSet = Sets.newHashSet();
            for (ElectricMeterAddParam meterAddParam : electricMeter) {
                // 校验前端传入的电表表号是否重复
                String deviceNo = meterAddParam.getDeviceNo();
                if (!deviceNoSet.contains(deviceNo)) {
                    deviceNoSet.add(deviceNo);
                } else {
                    throw new BusinessException("电表表号[" + deviceNo + "]重复");
                }

                // 校验前端传入的电表表计户号是否重复
                String meterUserNo = meterAddParam.getMeterUserNo();
                // 表计户号先补全12位
                String tempStr = CNoUtil.fillStrLengthTo12(meterUserNo);
                // 设置成最新12位的表计户号
                meterAddParam.setMeterUserNo(tempStr);
                if (!meterUserNoSet.contains(tempStr)) {
                    meterUserNoSet.add(tempStr);
                } else {
                    throw new BusinessException("电表表计户号[" + meterUserNo + "]重复");
                }
            }
        }
    }

    /**
     * 客户档案添加时，校验前端水表列表中参数合法性
     * @param waterMeter
     */
    private void checkWaterList(List<WaterMeterAddParam> waterMeter) {
        int size = waterMeter.size();
        if (size > 1) {
            Set<String> deviceNoSet = Sets.newHashSet();
            Set<String> meterUserNoSet = Sets.newHashSet();
            for (WaterMeterAddParam meterAddParam : waterMeter) {
                String waterType = meterAddParam.getWaterType();
                String deviceNo = meterAddParam.getDeviceNo();
                // 校验前端传入的水表表号是否重复
                String waterDeviceNo = CNoUtil.createWaterDeviceNo(waterType, deviceNo);
                // 设置水表表号
                meterAddParam.setDeviceNo(waterDeviceNo);
                if (!deviceNoSet.contains(waterDeviceNo)) {
                    deviceNoSet.add(waterDeviceNo);
                } else {
                    throw new BusinessException("水表表号[" + deviceNo + "]重复");
                }

                // 校验前端传入的水表表计户号是否重复
                String meterUserNo = meterAddParam.getMeterUserNo();
                String tempStr = CNoUtil.fillStrLengthTo12(meterUserNo);
                meterAddParam.setMeterUserNo(tempStr);
                if (!meterUserNoSet.contains(tempStr)) {
                    meterUserNoSet.add(tempStr);
                } else {
                    throw new BusinessException("水表表计户号[" + meterUserNo + "]重复");
                }
            }
        } else {
            // 设置水表真实表号
            WaterMeterAddParam meterAddParam = waterMeter.get(0);
            String waterDeviceNo = CNoUtil.createWaterDeviceNo(meterAddParam.getWaterType(), meterAddParam.getDeviceNo());
            meterAddParam.setDeviceNo(waterDeviceNo);
        }
    }

    /**
     * 客户档案添加时，校验前端气表列表中参数合法性
     * @param gasMeter
     */
    private void checkGasList(List<GasMeterAddParam> gasMeter) {
        int size = gasMeter.size();
        if (size > 1) {
            Set<String> deviceNoSet = Sets.newHashSet();
            Set<String> meterUserNoSet = Sets.newHashSet();
            for (GasMeterAddParam meterAddParam : gasMeter) {
                String gasType = meterAddParam.getGasType();
                String deviceNo = meterAddParam.getDeviceNo();
                // 校验前端传入的气表表号是否重复
                String gasDeviceNo = CNoUtil.createGasDeviceNo(gasType, deviceNo);
                // 设置气表表号
                meterAddParam.setDeviceNo(gasDeviceNo);
                if (!deviceNoSet.contains(gasDeviceNo)) {
                    deviceNoSet.add(gasDeviceNo);
                } else {
                    throw new BusinessException("气表表号[" + deviceNo + "]重复");
                }

                // 校验前端传入的气表表计户号是否重复
                String meterUserNo = meterAddParam.getMeterUserNo();
                String tempStr = CNoUtil.fillStrLengthTo12(meterUserNo);
                meterAddParam.setMeterUserNo(tempStr);
                if (!meterUserNoSet.contains(tempStr)) {
                    meterUserNoSet.add(tempStr);
                } else {
                    throw new BusinessException("气表表计户号[" + meterUserNo + "]重复");
                }
            }
        } else {
            // 设置气表真实表号
            GasMeterAddParam meterAddParam = gasMeter.get(0);
            String gasDeviceNo = CNoUtil.createGasDeviceNo(meterAddParam.getGasType(), meterAddParam.getDeviceNo());
            meterAddParam.setDeviceNo(gasDeviceNo);
        }
    }

    /**
     * 客户档案新增电表时，相关校验
     * @param electricMeter
     */
    private void checkElectricMeter(Long orgNo,ElectricMeterAddParam electricMeter) {
        String deviceType = DeviceType.ELECTRIC_METER.getCode();
        // 校验电表表号
        String deviceNo = electricMeter.getDeviceNo();
        this.checkDeviceNoExist(deviceType,deviceNo);

        // 校验表计户号
        String meterUserNo = electricMeter.getMeterUserNo();
        this.checkMeterUserNoExist(deviceType,meterUserNo);

        // 校验总尖峰平谷
        this.checkReadValue(electricMeter);

        // 校验总表
        String parentDeviceNo = electricMeter.getParentDeviceNo();
        this.checkParentDevice(parentDeviceNo,orgNo);
    }

    /**
     * 校验总尖峰平谷
     * @param electricMeter
     */
    private void checkReadValue(ElectricMeterAddParam electricMeter) {
        String deviceNo = electricMeter.getDeviceNo();
        String paramFlag = electricMeter.getParamFlag();
        // 查询设备参数配置信息
        DeviceMeterConfig meterConfig = deviceMeterConfigService.queryByParamFlag(paramFlag);
        Integer commFactorCnt = meterConfig.getCommFactorCnt();
        if (commFactorCnt > 1) {
            BigDecimal readValue1 = electricMeter.getReadValue1();
            if (readValue1 == null) {
                throw new BusinessException("表号[" + deviceNo + "]，尖示数readValue1不能为空");
            }
            BigDecimal readValue2 = electricMeter.getReadValue2();
            if (readValue2 == null) {
                throw new BusinessException("表号[" + deviceNo + "]，峰示数readValue2不能为空");
            }
            BigDecimal readValue3 = electricMeter.getReadValue3();
            if (readValue3 == null) {
                throw new BusinessException("表号[" + deviceNo + "]，平示数readValue2不能为空");
            }
            BigDecimal readValue4 = electricMeter.getReadValue4();
            if (readValue4 == null) {
                throw new BusinessException("表号[" + deviceNo + "]，谷示数readValue2不能为空");
            }

            // 总=尖+峰+平+谷
            BigDecimal readValue = electricMeter.getReadValue();
            BigDecimal sum = readValue1.add(readValue2).add(readValue3).add(readValue4);
            BigDecimal temp = new BigDecimal("0.02");
            BigDecimal m = readValue.subtract(temp);
            BigDecimal n = readValue.add(temp);
            boolean between = MathUtil.isBetween(sum, m, n);
            if (!between) {
                throw new BusinessException("表号[" + deviceNo + "],尖峰平谷之和跟总示数误差不在0.02之间");
            }
        }
    }

    /**
     * 客户档案新增水表时，相关校验
     * @param waterMeter
     */
    private void checkWaterMeter(WaterMeterAddParam waterMeter) {
        String deviceType = DeviceType.WATER_METER.getCode();
        // 水表表号
        String deviceNo = waterMeter.getDeviceNo();
        // 检查水表表号是否存在
        this.checkDeviceNoExist(deviceType,deviceNo);

        // 校验表计户号
        String meterUserNo = waterMeter.getMeterUserNo();
        this.checkMeterUserNoExist(deviceType,meterUserNo);
    }

    /**
     * 客户档案新增气表时，相关校验
     * @param gasMeter
     */
    private void checkGasMeter(GasMeterAddParam gasMeter) {
        String deviceType = DeviceType.GAS_METER.getCode();
        // 气表表号
        String deviceNo = gasMeter.getDeviceNo();
        // 检查气表表号是否存在
        this.checkDeviceNoExist(deviceType,deviceNo);

        // 校验表计户号
        String meterUserNo = gasMeter.getMeterUserNo();
        this.checkMeterUserNoExist(deviceType,meterUserNo);
    }


    // 更新客户档案
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateCustomerInfo(CustomerInfoEditVo editVo) throws BusinessException {
        Long id = editVo.getId();
        // 判断用户门牌编号是否已存在
        this.checkByPropertyName4Edit(id, editVo.getPropertyName());

        // 校验用户告警阈值
        this.checkAlarm4Edit(editVo);

        // 查询
        CustomerInfo info = customerInfoMapper.selectByPrimaryKey(id);
        if (info == null) {
            throw new BusinessException("待更新的客户档案记录不存在，请重新刷新页面");
        }

        // 提交客户资料修改
        CustomerInfo customerInfo = new CustomerInfo();
        BeanUtils.copyProperties(editVo,customerInfo);
        customerInfoMapper.updateByPrimaryKeySelective(customerInfo);
        Long orgNo = info.getOrgNo();
        if (!orgNo.equals(editVo.getOrgNo())) {
            // 修改客户档案关联的设备信息所属组织编码
            deviceInfoService.updateDeviceOrgNo(editVo.getOrgNo(), info.getCustomerNo());
        }

        // 更新em_d_customerinfo_cost表记录
        CustomerInfoCost param = new CustomerInfoCost();
        param.setCustomerNo(info.getCustomerNo());
        param.setAlarmThreshold(editVo.getAlarmThreshold());
        param.setAlarmThreshold1(editVo.getAlarmThreshold1());
        param.setAlarmThreshold2(editVo.getAlarmThreshold2());
        param.setOverdraftAmount(editVo.getOverdraftAmount());
        customerInfoCostService.updateByCustomerNoSelective(param);

        List<CustomerDevMap> devMaps = customerDevMapService.queryByCustomerNo(info.getCustomerNo());
        List<String> cnoList = Lists.newArrayList();
        for (CustomerDevMap devMap : devMaps) {
            cnoList.add(devMap.getCno());
        }

        // 查询用户对应的设备列表
        List<DeviceInfo> deviceInfos = deviceInfoService.batchQueryByCnos(cnoList);
        List<DeviceCacheVo> deviceCacheVos = Lists.newArrayList();
        for (DeviceInfo deviceInfo : deviceInfos) {
            DeviceCacheVo deviceCacheVo = new DeviceCacheVo();
            BeanUtils.copyProperties(deviceInfo,deviceCacheVo);
            deviceCacheVos.add(deviceCacheVo);
        }
        deviceInfoService.updateOffSchemeInfo(cnoList,editVo.getOffScheme(),editVo.getOffParam());

        // 设置redis
        // 重新查询用户
        CustomerInfo tempInfo = this.queryByCustomerNo(info.getCustomerNo());
        CustomerCacheVo cacheVo = new CustomerCacheVo();
        BeanUtils.copyProperties(tempInfo,cacheVo);
        String key = RedisKeyConstant.CUSTOMER_PREFIX + info.getCustomerNo();
        redisUtil.set(key,JSON.toJSONString(cacheVo));
        if (!orgNo.equals(editVo.getOrgNo())) {
            // 重新覆盖之前的redis
            redisUtil.setDeviceCacheList(deviceCacheVos);
        }
    }

    @Override
    @Transactional
    public void batchDeleteCustomerInfo(Set<String> customerNoSet) throws BusinessException {
        // 校验电表是否可以删除
        this.checkCustomerBatchDelete(customerNoSet);

        List<String> totalCno = Lists.newArrayList();
        // 循环删除
        for (String customerNo : customerNoSet) {
            // 删除客户信息
            this.deleteByCustomerNo(customerNo);

            // 查询客户关联表信息
            List<CustomerDevMap> customerDevMaps = customerDevMapService.query4DeleteByCustomerNo(customerNo);
            for (CustomerDevMap devMap : customerDevMaps) {
                String cno = devMap.getCno();
                this.deleteSingleDevice(cno);
                totalCno.add(cno);
            }

            // 删除客户关联表信息
            customerDevMapService.deleteByCustomerNo(customerNo);

            // 批量删除客户IC卡信息
            feeAcctService.deleteByCustomerNo(customerNo);

            // 充值缴费记录为无效
            feePayService.disableFeePayByCustomerNo(customerNo);

            // 删除客户绑定电话信息
            customerPhoneBindService.deleteByCustomerNo(customerNo);

            // 删除客户所关联的所有微信
            customerWxBindService.deleteByCustomerNo(customerNo);

            // 删除方案详情并更新方案记录电表数量
            List<QuerySchememet> querySchememets = querySchememetService.queryByCustomerNo(customerNo);
            this.deleteSchemeInfo(querySchememets);
        }

        // redis操作
        Set<String> totalKeys = Sets.newHashSet();
        // 加入用户缓存的key
        for (String customerNo : customerNoSet) {
            String key = RedisKeyConstant.CUSTOMER_PREFIX + customerNo;
            totalKeys.add(key);
        }

        // 加入设备缓存的key以及设备关系缓存的key
        for (String cno : totalCno) {
            totalKeys.add(RedisKeyConstant.DEVICE_PREFIX + cno);
            totalKeys.add(RedisKeyConstant.DEVICE_RELATION_PREFIX + cno);
        }

        // 删除是否存在孩子节点缓存key
        String pattern = RedisKeyConstant.DEVICE_CHILD_PREFIX + "*";
        Set<String> keys = redisUtil.keys(pattern);
        for (String key : keys) {
            totalKeys.add(key);
        }
        redisUtil.batchDeleteKeys(totalKeys);
    }

    /**
     * 用户批量删除时，校验这一批用户下的电表，是否存在挂靠的孩子节点，如果存在则不允许删除
     * @param customerNoSet
     */
    private void checkCustomerBatchDelete(Set<String> customerNoSet) {
        List<CustomerDevMap> devMaps = customerDevMapService.queryByParams(DeviceType.ELECTRIC_METER.getCode(), customerNoSet);
        if (!CollectionUtils.isEmpty(devMaps)) {
            Set<String> cnoSet = Sets.newHashSet();
            for (CustomerDevMap devMap : devMaps) {
                cnoSet.add(devMap.getCno());
            }
            List<MeterRelation> meterRelations = meterRelationService.queryChildNode(cnoSet);
            if (!CollectionUtils.isEmpty(meterRelations)) {
                throw new BusinessException("批量删除的用户电表中,存在挂靠的分表,不允许删除");
            }
        }
    }

    /**
     * 单个水电气设备删除
     * @param cno
     */
    private void deleteSingleDevice(String cno) {
        // 查询设备信息
        DeviceInfo deviceInfo = deviceInfoService.queryDeviceInfoByCno(cno);
        if (deviceInfo == null) {
            // 换表的时候存储过程会把之前表，em_d_deviceinfo、em_d_devicemeterparam中记录删除
            return;
        }
        String pDeviceNo = deviceInfo.getpDeviceNo();

        // 删除设备信息表
        deviceInfoService.deleteDevice(cno);

        // 更新设备参数表
        deviceMeterParamService.updateCommPointCode2Zero(cno);

        // 删除设备关系表记录
        meterRelationService.deleteByCno(cno);

        // 删除父设备(集中器，采集器，转换器)
        Boolean deletable = deviceInfoService.isPdeviceDeletable(pDeviceNo);
        if (deletable) {
            // 删除设备信息
            deviceInfoService.deleteDevice(pDeviceNo);
            String type = pDeviceNo.substring(0, 2);
            if (DeviceType.JZQ.getCode().equals(type)) {
                // 删除集中器连接参数表信息
                deviceConnParamService.deleteByCno(pDeviceNo);
            }
        }

        // 依赖设备是集中器，则删除集中器
        String relyCno = deviceInfo.getRelyCno();
        String relyDeviceType = relyCno.substring(0, 2);
        if (DeviceType.JZQ.getCode().equals(relyDeviceType)) {
            Boolean relyDeviceDeletable = deviceInfoService.isRelyDeviceDeletable(relyCno);
            if (relyDeviceDeletable) {
                // 删除集中器
                deviceInfoService.deleteDevice(relyCno);
                // 删除集中器连接参数表信息
                deviceConnParamService.deleteByCno(relyCno);
                // 删除集中器设备状态信息表
                deviceInfoDeviceStateService.deleteByCno(relyCno);
            }
        } else {
            // 删除水电气表，设备状态信息表
            deviceInfoDeviceStateService.deleteByCno(cno);
        }
    }

    @Override
    @Transactional
    public void editCustomerElectricMeter(ElectricMeterAddParam param, Long currentUserId, Long orgNo, String customerNo) {
        String deviceNo = param.getDeviceNo();
        String cno = CNoUtil.CreateCNo(DeviceType.ELECTRIC_METER.getCode(), deviceNo);

        // 查询修改前记录
        DeviceInfo deviceInfo = deviceInfoService.queryDeviceInfoByCno(cno);
        if (deviceInfo == null) {
            throw new BusinessException("修改的数据不存在，请刷新页面");
        }

        // 校验表计户号是否存在
        String meterUserNo = param.getMeterUserNo();
        String tempStr = CNoUtil.fillStrLengthTo12(meterUserNo);
        param.setMeterUserNo(tempStr);
        Boolean flag = customerDevMapService.checkMeterUserNoExist4Edit(cno, tempStr);
        if (flag) {
            throw new BusinessException("表计户号[" + param.getMeterUserNo() + "]已存在");
        }

        // 校验总表所在组织是否跟用户所在组织一致
        String parentDeviceNo = param.getParentDeviceNo();
        this.checkParentDevice(parentDeviceNo,orgNo);

        // 校验预留金额
        CustomerDevMap devMap = customerDevMapService.queryByCno(cno);
      /*  BigDecimal oldInitAmount = devMap.getInitAmount();
        BigDecimal initAmount = param.getInitAmount();
        boolean equal = MathUtil.isEqual(oldInitAmount, initAmount);
        if (!equal) {
            throw new BusinessException("不允许修改预留金额");
        }*/
//        Integer isAccount = devMap.getIsAccount();
//        Integer localControl = param.getLocalControl();
//        if (CustomerInfoConstant.FeeControlType.REMOTE.getCode().equals(localControl)) {
//            // 远程费控，不允许修改预留金额
//            if (!initAmount.equals(oldInitAmount)) {
//                throw new BusinessException("远程费控表不允许修改预留金额");
//            }
//        } else {
//            // 本地费控
//            if (FeeControlConstant.OpenAccountStatus.OPEN_ACCOUNT_SUCCESS.getStatus().equals(isAccount)) {
//                // 已开户
//                if (!initAmount.equals(oldInitAmount)) {
//                    throw new BusinessException("本地费控表，已开户情况下，不允许修改预留金额");
//                }
//            }
//        }

        // 查询设备型号
        DeviceMeterConfig meterConfig = deviceMeterConfigService.queryByParamFlag(param.getParamFlag());

        // 更新设备信息
        DeviceInfo deviceInfo4Edit = this.getElectricDeviceInfo4Edit(param, meterConfig, orgNo);
        deviceInfo4Edit.setId(deviceInfo.getId());
        deviceInfoService.updateByPrimaryKeySelective(deviceInfo4Edit);

        // 新增依赖设备表信息
        this.addDeviceInfoDeviceStateInfo(deviceInfo4Edit.getRelyCno(),param.getCommPort());

        // 查询待更新的设备参数表信息
        DeviceMeterParam oldMeterParam = deviceMeterParamService.queryEffectiveParamByCno(cno);

        // 更新设备参数表
        DeviceMeterParam meterParam4Edit = this.getElectricDeviceMeterParam4Edit(param, meterConfig);
        meterParam4Edit.setId(oldMeterParam.getId());
        // 下发标志置为0，前置机会重新下发设备信息
        meterParam4Edit.setSendFlag(0);
        deviceMeterParamService.updateByPrimaryKeySelective(meterParam4Edit);

        // 额外的一些删除操作
        String cjqNo = param.getCjqNo();
        String jzqNo = param.getJzqNo();
        Integer collectDevType = param.getCollectDevType();
        Integer commPort = param.getCommPort();
        this.extraDeleteOperate4Electric(oldMeterParam,cjqNo,jzqNo,commPort,collectDevType);

        // 添加集中器
        this.addJzq(jzqNo,commPort,currentUserId,orgNo);

        // 添加采集器
        if (!StringUtils.isEmpty(cjqNo)) {
            if (CustomerInfoConstant.ElectricCollectDeviceType.COLLECTOR.getCode().equals(collectDevType)) {
                this.addCjq(jzqNo, cjqNo, param.getCommPort(), currentUserId, orgNo);
            }
        }

        // 更新客户设备关联表记录
//        CustomerDevMap customerDevMap4Edit = this.getCustomerDevMap4Edit(param, cno);
        CustomerDevMap map = new CustomerDevMap();
        map.setId(devMap.getId());
//        if (!CustomerInfoConstant.FeeControlType.REMOTE.getCode().equals(localControl)) {
//            // 不是远程费控表
//            if (!FeeControlConstant.OpenAccountStatus.OPEN_ACCOUNT_SUCCESS.getStatus().equals(isAccount)) {
//                // 非开户成功状态
//                map.setInitAmount(param.getInitAmount());
//                map.setRemainAmount(param.getInitAmount());
//            }
//        }
        map.setMeterUserNo(param.getMeterUserNo());
        map.setTransformerNo(param.getTransformerNo());
        map.setBuildingNo(param.getBuildingNo());
        map.setElecType(param.getElecType());
        map.setElecMeterCategory(param.getElecMeterCategory());
        customerDevMapService.updateByPrimaryKeySelective(map);

        // 修改总表信息逻辑处理
        this.editDeviceRelation(DeviceType.ELECTRIC_METER.getCode(),param.getDeviceNo(),param.getParentDeviceNo());

        // 设置redis
        this.updateDeviceRedis(cno);
    }

    private void updateDeviceRedis(String cno) {
        DeviceMeterParam meterParam = deviceMeterParamService.queryEffectiveParamByCno(cno);
        DeviceInfo deviceInfo = deviceInfoService.queryDeviceInfoByCno(cno);
        DeviceCacheVo cacheVo = new DeviceCacheVo();
        BeanUtils.copyProperties(deviceInfo,cacheVo);
        cacheVo.setIsImportant(meterParam.getIsImportant());
        String key = RedisKeyConstant.DEVICE_PREFIX + cno;
        redisUtil.set(key,JSON.toJSONString(cacheVo));

        MeterRelation meterRelation = meterRelationService.queryByCno(cno);
        DeviceRelationCacheVo relationCacheVo = new DeviceRelationCacheVo();
        BeanUtils.copyProperties(meterRelation,relationCacheVo);
        String key2 = RedisKeyConstant.DEVICE_RELATION_PREFIX + cno;
        redisUtil.set(key2,JSON.toJSONString(relationCacheVo));
    }

//    /**
//     * 修改电表时，返回客户设备关系记录实体
//     * @param param
//     * @param cno
//     * @return
//     */
//    private CustomerDevMap getCustomerDevMap4Edit(ElectricMeterAddParam param, String cno) {
//        CustomerDevMap map = new CustomerDevMap();
//        map.setId(devMap.getId());
//        if (!CustomerInfoConstant.FeeControlType.REMOTE.getCode().equals(localControl)) {
//            // 不是远程费控表
//            if (!FeeControlConstant.OpenAccountStatus.OPEN_ACCOUNT_SUCCESS.getStatus().equals(isAccount)) {
//                // 非开户成功状态
//                map.setInitAmount(param.getInitAmount());
//                map.setRemainAmount(param.getInitAmount());
//            }
//        }
//        map.setMeterUserNo(param.getMeterUserNo());
//        map.setTransformerNo(param.getTransformerNo());
//        map.setBuildingNo(param.getBuildingNo());
//        map.setAlarmThreshold(param.getAlarmThreshold());
//        map.setAlarmThreshold1(param.getAlarmThreshold1());
//        customerDevMapService.updateByPrimaryKeySelective(map);
//    }

    /**
     * 校验总表信息
     * @param parentDeviceNo
     * @param orgNo
     */
    private void checkParentDevice(String parentDeviceNo,Long orgNo) {
        if (!StringUtils.isEmpty(parentDeviceNo)) {
            String parentDeviceCno = CNoUtil.CreateCNo(DeviceType.ELECTRIC_METER.getCode(), parentDeviceNo);
            DeviceInfo parentDevice = deviceInfoService.queryDeviceInfoByCno(parentDeviceCno);
            if (parentDevice == null) {
                throw new BusinessException("系统中总表[" + parentDeviceNo + "]不存在");
            }
            if (!orgNo.equals(parentDevice.getOrgNo())) {
                throw new BusinessException("总表[" + parentDevice.getDeviceNo() + "]所在的组织，跟客户档案所在组织不一致");
            }
        }
    }

    /**
     * 修改设备关系逻辑处理
     * @param deviceType
     * @param deviceNo
     * @param parentDeviceNo
     */
    private void editDeviceRelation(String deviceType,String deviceNo,String parentDeviceNo) {
        String cno = CNoUtil.CreateCNo(deviceType,deviceNo);
        // 查询设备关系记录
        MeterRelation meterRelation = meterRelationService.queryByCno(cno);
        String oldParentCno = meterRelation.getpMeterCno();
        if (StringUtils.isEmpty(parentDeviceNo)) {
            if (!"0".equals(oldParentCno)) {
                this.commonOperate(cno,null);
            }
        } else {
            // 查询该总表是否存在
            String parentCno = CNoUtil.CreateCNo(deviceType,parentDeviceNo);
            MeterRelation parentRelation = meterRelationService.queryByCno(parentCno);
            if (parentRelation == null) {
                String message = DeviceType.getMessageByCode(deviceType);
                throw new BusinessException(message + "表号[" + deviceNo + "]的总表[" + parentDeviceNo + "]记录在设备关系表中不存在");
            }

            if (!oldParentCno.equals(parentCno)) {
                // 之前是总表 或者修改了之前挂靠的设备
                this.commonOperate(cno,parentRelation);
            }
        }
    }

    private void commonOperate(String cno,MeterRelation parentRelation) {
        // 查询cno下的所有子表信息
        List<MeterRelation> childRelation = meterRelationService.queryChildTreeByCno(cno);

        // 存储需要删除的redis key
        List<String> keys = Lists.newArrayList();

        // 更新cno表的所有子表的level
        List<MeterRelation> updateList = Lists.newArrayList();
        for (MeterRelation relation : childRelation) {
            String key = RedisKeyConstant.DEVICE_RELATION_PREFIX + relation.getMeterCno();
            keys.add(key);
            if (relation.getMeterCno().equals(cno)) {
                if (parentRelation == null) {
                    // 当前修改的表,则升级成总表
                    MeterRelation param = new MeterRelation();
                    param.setId(relation.getId());
                    param.setpMeterCno("0");
                    param.setLevel("/" + cno);
                    updateList.add(param);
                } else {
                    // 当前修改的表,挂靠在parentRelation下
                    MeterRelation param = new MeterRelation();
                    param.setId(relation.getId());
                    param.setpMeterCno(parentRelation.getMeterCno());
                    param.setLevel(parentRelation.getLevel() + "/" + cno);
                    updateList.add(param);
                }
            } else {
                if (parentRelation == null) {
                    // 所有的下级节点
                    String level = relation.getLevel();
                    int i = level.indexOf("/" + cno);
                    String tempStr = level.substring(i);
                    MeterRelation param = new MeterRelation();
                    param.setId(relation.getId());
                    param.setLevel(tempStr);
                    updateList.add(param);
                } else {
                    // 所有的下级节点
                    String level = relation.getLevel();
                    int i = level.indexOf("/" + cno);
                    String tempStr = level.substring(i);
                    String newLevel = parentRelation.getLevel() + tempStr;
                    MeterRelation param = new MeterRelation();
                    param.setId(relation.getId());
                    param.setLevel(newLevel);
                    updateList.add(param);
                }
            }
        }

        int num = meterRelationService.batchUpdateByPrimaryKey(updateList);
        logger.info("批量修改了num=" + num + "条");

        // redis删除之前key
        redisUtil.batchDeleteKeys(keys);
    }


    /**
     * 修改电表时，导致的一些无用数据，删除
     * @param oldMeterParam
     * @param jzqNo
     * @param commPort
     */
    private void extraDeleteOperate4Electric(DeviceMeterParam oldMeterParam, String cjqNo, String jzqNo, Integer commPort,Integer collectDevType) {
        // 采集器被修改的情况，是否删除原来采集器
        String commCollectionNo = null;
        if (CustomerInfoConstant.ElectricCommPort.LORA_WAN.getCode().equals(commPort)) {
            commCollectionNo = CNoUtil.fillStrLengthTo16(cjqNo);
        } else if (CustomerInfoConstant.ElectricCommPort.CARRIER.getCode().equals(commPort)) {
            commCollectionNo = CNoUtil.fillStrLengthTo12(cjqNo);
        } else if (CustomerInfoConstant.ElectricCommPort.DIRECT_THREE.getCode().equals(commPort) ||
                CustomerInfoConstant.ElectricCommPort.DIRECT_TWO.getCode().equals(commPort) ||
                CustomerInfoConstant.ElectricCommPort.DIRECT_ONE.getCode().equals(commPort)) {
            commCollectionNo = StringUtil.fillString("0", 12);
        }

        String oldCommCollectionNo = oldMeterParam.getCommCollectionNo();
        Integer oldCollectDevType = oldMeterParam.getCollectDevType();
        if (CustomerInfoConstant.ElectricCollectDeviceType.COLLECTOR.getCode().equals(collectDevType)) {
            // 现在是采集器
            if (CustomerInfoConstant.ElectricCollectDeviceType.COLLECTOR.getCode().equals(oldCollectDevType)) {
                // 修改前是采集器
                if (!oldCommCollectionNo.equals(commCollectionNo)) {
                    this.deleteOldCjq4Edit(oldCommCollectionNo);
                }
            } else {
                // 修改前是节点(节点时不会新增deviceInfo记录，所以没有需要删除的脏数据)
                // do nothing
            }
        } else {
            // 现在节点
            if (CustomerInfoConstant.ElectricCollectDeviceType.COLLECTOR.getCode().equals(oldCollectDevType)) {
                // 修改前是采集器
                this.deleteOldCjq4Edit(oldCommCollectionNo);
            } else {
                // 修改前是节点
                // do nothing
            }
        }

        // 集中器被修改的情况，是否删除原来集中器
        String oldJzqCno = oldMeterParam.getJzqCno();
        String jzqCno = CNoUtil.CreateCNo(DeviceType.JZQ.getCode(), jzqNo);
        if (!oldJzqCno.equals(jzqCno)) {
            this.deleteOldJzq4Edit(oldJzqCno);
        }

        // 是否删除设备状态信息表记录
        Integer oldCommPort = oldMeterParam.getCommPort();
        // 采集方式修改的情况
        if (!oldCommPort.equals(commPort)) {
            if (CustomerInfoConstant.ElectricCommPort.LORA_WAN.getCode().equals(oldCommPort)) {
                // commPort非LoraWan
                deviceInfoDeviceStateService.deleteByCno(oldMeterParam.getCno());
            }
        }
    }

    /**
     * 修改水,气表时，导致的一些无用数据，删除
     * @param oldMeterParam
     * @param jzqNo
     * @param commPort
     */
    private void extraDeleteOperate4Water(DeviceMeterParam oldMeterParam, String cjqNo, String jzqNo, Integer commPort,Integer collectDevType) {
        // 转换器被修改的情况，是否删除原来转换器
        String commCollectionNo = null;
        if (CustomerInfoConstant.WaterCommPort.LORA_WAN.getCode().equals(commPort)) {
            commCollectionNo = CNoUtil.fillStrLengthTo16(cjqNo);
        } else if (CustomerInfoConstant.WaterCommPort.CARRIER.getCode().equals(commPort)) {
            commCollectionNo = CNoUtil.fillStrLengthTo12(cjqNo);
        }

        String oldCommCollectionNo = oldMeterParam.getCommCollectionNo();
        Integer oldCollectDevType = oldMeterParam.getCollectDevType();
        if (CustomerInfoConstant.WaterCollectDeviceType.CONVERTER.getCode().equals(collectDevType)) {
            // 现在是转换器
            if (CustomerInfoConstant.WaterCollectDeviceType.CONVERTER.getCode().equals(oldCollectDevType)) {
                // 修改前是转换器
                if (!oldCommCollectionNo.equals(commCollectionNo)) {
                    this.deleteOldConverter4Edit(oldCommCollectionNo);
                }
            } else {
                // 修改前是节点
                // do nothing
            }
        } else {
            // 现在是节点
            if (CustomerInfoConstant.WaterCollectDeviceType.CONVERTER.getCode().equals(oldCollectDevType)) {
                // 修改前是转换器
                this.deleteOldConverter4Edit(oldCommCollectionNo);
            } else {
                // 修改前是节点
                // do nothing
            }
        }

        // 集中器被修改的情况，是否删除原来集中器
        String oldJzqCno = oldMeterParam.getJzqCno();
        String jzqCno = CNoUtil.CreateCNo(DeviceType.JZQ.getCode(), jzqNo);
        if (!oldJzqCno.equals(jzqCno)) {
            this.deleteOldJzq4Edit(oldJzqCno);
        }

        // 是否删除设备状态信息表记录
        Integer oldCommPort = oldMeterParam.getCommPort();
        // 采集方式修改的情况
        if (!oldCommPort.equals(commPort)) {
            if (CustomerInfoConstant.WaterCommPort.LORA_WAN.getCode().equals(oldCommPort)) {
                // commPort非LoraWan
                deviceInfoDeviceStateService.deleteByCno(oldMeterParam.getCno());
            }
        }
    }

    /**
     * 修改水,气表时，导致的一些无用数据，删除
     * @param oldMeterParam
     * @param jzqNo
     * @param commPort
     */
    private void extraDeleteOperate4Gas(DeviceMeterParam oldMeterParam, String cjqNo, String jzqNo, Integer commPort,Integer collectDevType) {
        // 转换器被修改的情况，是否删除原来转换器
        String commCollectionNo = null;
        if (CustomerInfoConstant.GasCommPort.LORA_WAN.getCode().equals(commPort)) {
            commCollectionNo = CNoUtil.fillStrLengthTo16(cjqNo);
        } else if (CustomerInfoConstant.GasCommPort.CARRIER.getCode().equals(commPort)) {
            commCollectionNo = CNoUtil.fillStrLengthTo12(cjqNo);
        }

        String oldCommCollectionNo = oldMeterParam.getCommCollectionNo();
        Integer oldCollectDevType = oldMeterParam.getCollectDevType();
        if (CustomerInfoConstant.GasCollectDeviceType.CONVERTER.getCode().equals(collectDevType)) {
            // 现在是转换器
            if (CustomerInfoConstant.GasCollectDeviceType.CONVERTER.getCode().equals(oldCollectDevType)) {
                // 修改前是转换器
                if (!oldCommCollectionNo.equals(commCollectionNo)) {
                    this.deleteOldConverter4Edit(oldCommCollectionNo);
                }
            } else {
                // 修改前是节点
                // do nothing
            }
        } else {
            // 现在是节点
            if (CustomerInfoConstant.GasCollectDeviceType.CONVERTER.getCode().equals(oldCollectDevType)) {
                // 修改前是转换器
                this.deleteOldConverter4Edit(oldCommCollectionNo);
            } else {
                // 修改前是节点
                // do nothing
            }
        }

        // 集中器被修改的情况，是否删除原来集中器
        String oldJzqCno = oldMeterParam.getJzqCno();
        String jzqCno = CNoUtil.CreateCNo(DeviceType.JZQ.getCode(), jzqNo);
        if (!oldJzqCno.equals(jzqCno)) {
            this.deleteOldJzq4Edit(oldJzqCno);
        }

        // 是否删除设备状态信息表记录
        Integer oldCommPort = oldMeterParam.getCommPort();
        // 采集方式修改的情况
        if (!oldCommPort.equals(commPort)) {
            if (CustomerInfoConstant.GasCommPort.LORA_WAN.getCode().equals(oldCommPort)) {
                // commPort非LoraWan
                deviceInfoDeviceStateService.deleteByCno(oldMeterParam.getCno());
            }
        }
    }

    /**
     * 修改水电气表时，如果集中器被修改了，判断是否需要删除该集中器相关信息
     * @param oldJzqCno
     */
    private void deleteOldJzq4Edit(String oldJzqCno) {
        // 判断集中器是否被使用
        Boolean deletable = deviceInfoService.isPdeviceDeletable(oldJzqCno);
        if (deletable) {
            // 删除集中器设备表信息
            deviceInfoService.deleteDevice(oldJzqCno);

            // 删除集中参数表信息
            deviceMeterParamService.updateCommPointCode2Zero(oldJzqCno);

            // 删除集中器设备状态信息表
            deviceInfoDeviceStateService.deleteByCno(oldJzqCno);
        }
    }

    /**
     * 修改电表时，如果采集器被修改了，判断之前采集器是否需要删除
     * @param oldCommCollectionNo
     */
    private void deleteOldCjq4Edit(String oldCommCollectionNo) {
        // 判断采集器是否被使用
        String cjqNo = oldCommCollectionNo.replaceAll("^0*", "");
        String cjqCno = CNoUtil.CreateCNo(DeviceType.CJQ.getCode(), cjqNo);
        Boolean deletable = deviceInfoService.isPdeviceDeletable(cjqCno);
        if (deletable) {
            // 删除采集器设备表信息
            deviceInfoService.deleteDevice(cjqCno);

            // 删除采集器设备状态信息表
            deviceInfoDeviceStateService.deleteByCno(cjqCno);
        }
    }

    /**
     * 修改水表，气表时，如果转换器被修改了，判断之前转换器是否需要删除
     * @param oldCommCollectionNo
     */
    private void deleteOldConverter4Edit(String oldCommCollectionNo) {
        // 判断转换器是否被使用
        String converterNo = oldCommCollectionNo.replaceAll("^0*", "");
        String converterCno = CNoUtil.CreateCNo(DeviceType.CONVERTER.getCode(), converterNo);
        Boolean deletable = deviceInfoService.isPdeviceDeletable(converterCno);
        if (deletable) {
            // 删除转换器设备表信息
            deviceInfoService.deleteDevice(converterCno);

            // 删除转换器设备状态信息表
            deviceInfoDeviceStateService.deleteByCno(converterCno);
        }
    }

    @Override
    @Transactional
    public void editCustomerWaterMeter(WaterMeterAddParam param, Long currentUserId, Long orgNo,String customerNo) {
        String deviceNo = param.getDeviceNo();
        String cno = CNoUtil.CreateCNo(DeviceType.WATER_METER.getCode(), deviceNo);

        // 查询修改前记录
        DeviceInfo deviceInfo = deviceInfoService.queryDeviceInfoByCno(cno);
        if (deviceInfo == null) {
            throw new BusinessException("修改的数据不存在，请刷新页面");
        }

        // 校验表计户号是否存在
        String meterUserNo = param.getMeterUserNo();
        String tempStr = CNoUtil.fillStrLengthTo12(meterUserNo);
        param.setMeterUserNo(tempStr);
        Boolean flag = customerDevMapService.checkMeterUserNoExist4Edit(cno,tempStr);
        if (flag) {
            throw new BusinessException("表计户号[" + param.getMeterUserNo() + "]已存在");
        }

        // 校验预留金额
        CustomerDevMap devMap = customerDevMapService.queryByCno(cno);
     /*   BigDecimal oldInitAmount = devMap.getInitAmount();
        BigDecimal initAmount = param.getInitAmount();
        boolean equal = MathUtil.isEqual(oldInitAmount, initAmount);
        if (!equal) {
            throw new BusinessException("不允许修改预留金额");
        }*/

        // 查询设备型号
        DeviceMeterConfig meterConfig = deviceMeterConfigService.queryByParamFlag(param.getParamFlag());
        // 更新设备信息
        DeviceInfo deviceInfo4Edit = this.getWaterDeviceInfo4Edit(param, meterConfig, orgNo);
        deviceInfo4Edit.setId(deviceInfo.getId());
        deviceInfoService.updateByPrimaryKeySelective(deviceInfo4Edit);

        // 新增依赖设备表信息
        this.addDeviceInfoDeviceStateInfo(deviceInfo4Edit.getRelyCno(),param.getCommPort());

        // 查询待更新的设备参数表信息
        DeviceMeterParam oldMeterParam = deviceMeterParamService.queryEffectiveParamByCno(cno);
        DeviceMeterParam meterParam4Edit = this.getWaterDeviceMeterParam4Edit(param, meterConfig);
        meterParam4Edit.setId(oldMeterParam.getId());
        // 下发标志置为0，前置机会重新下发设备信息
        meterParam4Edit.setSendFlag(0);
        deviceMeterParamService.updateByPrimaryKeySelective(meterParam4Edit);

        // 额外的一些删除操作
        String cjqNo = param.getCjqNo();
        String jzqNo = param.getJzqNo();
        Integer commPort = param.getCommPort();
        Integer collectDevType = param.getCollectDevType();
        this.extraDeleteOperate4Water(oldMeterParam,cjqNo,jzqNo,commPort,collectDevType);

        // 添加集中器
        this.addJzq(jzqNo,commPort,currentUserId,orgNo);

        // 添加转换器设备表信息
        if (!StringUtils.isEmpty(cjqNo)) {
            if (CustomerInfoConstant.WaterCollectDeviceType.CONVERTER.getCode().equals(collectDevType)) {
                this.addConverter(DeviceType.WATER_METER.getCode(), jzqNo, cjqNo, param.getCommPort(), currentUserId, orgNo);
            }
        }

        // 更新客户设备关联表记录
        CustomerDevMap map = new CustomerDevMap();
        map.setId(devMap.getId());
        map.setMeterUserNo(param.getMeterUserNo());
        map.setTransformerNo(param.getTransformerNo());
        map.setBuildingNo(param.getBuildingNo());
        customerDevMapService.updateByPrimaryKeySelective(map);

        // 设置redis
        this.updateDeviceRedis(cno);
    }

    @Override
    @Transactional
    public void editCustomerGasMeter(GasMeterAddParam param, Long currentUserId, Long orgNo,String customerNo) {
        String deviceNo = param.getDeviceNo();
        String cno = CNoUtil.CreateCNo(DeviceType.GAS_METER.getCode(), deviceNo);

        // 查询修改前记录
        DeviceInfo deviceInfo = deviceInfoService.queryDeviceInfoByCno(cno);
        if (deviceInfo == null) {
            throw new BusinessException("修改的数据不存在，请刷新页面");
        }

        // 校验表计户号是否存在
        String meterUserNo = param.getMeterUserNo();
        String tempStr = CNoUtil.fillStrLengthTo12(meterUserNo);
        param.setMeterUserNo(tempStr);
        Boolean flag = customerDevMapService.checkMeterUserNoExist4Edit(cno, tempStr);
        if (flag) {
            throw new BusinessException("表计户号[" + param.getMeterUserNo() + "]已存在");
        }

        // 校验预留金额
        CustomerDevMap devMap = customerDevMapService.queryByCno(cno);
       /* BigDecimal oldInitAmount = devMap.getInitAmount();
        BigDecimal initAmount = param.getInitAmount();
        boolean equal = MathUtil.isEqual(oldInitAmount, initAmount);
        if (!equal) {
            throw new BusinessException("不允许修改预留金额");
        }*/

        // 查询设备型号
        DeviceMeterConfig meterConfig = deviceMeterConfigService.queryByParamFlag(param.getParamFlag());

        // 更新设备信息
        DeviceInfo deviceInfo4Edit = this.getGasDeviceInfo4Edit(param, meterConfig, orgNo);
        deviceInfo4Edit.setId(deviceInfo.getId());
        deviceInfoService.updateByPrimaryKeySelective(deviceInfo4Edit);

        // 新增依赖设备表信息
        this.addDeviceInfoDeviceStateInfo(deviceInfo4Edit.getRelyCno(),param.getCommPort());

        // 查询待更新的设备参数表信息
        DeviceMeterParam oldMeterParam = deviceMeterParamService.queryEffectiveParamByCno(cno);
        DeviceMeterParam meterParam4Edit = this.getGasDeviceMeterParam4Edit(param, meterConfig);
        meterParam4Edit.setId(oldMeterParam.getId());
        // 下发标志置为0，前置机会重新下发设备信息
        meterParam4Edit.setSendFlag(0);
        deviceMeterParamService.updateByPrimaryKeySelective(meterParam4Edit);

        // 额外的一些删除操作
        String cjqNo = param.getCjqNo();
        String jzqNo = param.getJzqNo();
        Integer commPort = param.getCommPort();
        Integer collectDevType = param.getCollectDevType();
        this.extraDeleteOperate4Gas(oldMeterParam,cjqNo,jzqNo,commPort,collectDevType);

        // 添加集中器
        this.addJzq(jzqNo,commPort,currentUserId,orgNo);

        // 添加转换器
        if (!StringUtils.isEmpty(cjqNo)) {
            if (CustomerInfoConstant.GasCollectDeviceType.CONVERTER.getCode().equals(collectDevType)) {
                this.addConverter(DeviceType.GAS_METER.getCode(), jzqNo, cjqNo, param.getCommPort(), currentUserId, orgNo);
            }
        }

        // 更新客户设备关联表记录
        CustomerDevMap map = new CustomerDevMap();
        map.setId(devMap.getId());
        map.setMeterUserNo(param.getMeterUserNo());
        map.setTransformerNo(param.getTransformerNo());
        map.setBuildingNo(param.getBuildingNo());
        customerDevMapService.updateByPrimaryKeySelective(map);

        // 设置redis
        this.updateDeviceRedis(cno);
    }

    @Override
    @Transactional
    public void deleteCustomerMeter(String customerNo, String deviceNo, String deviceType,String operator) {
        String cno = CNoUtil.CreateCNo(deviceType, deviceNo);
        // 校验电表是否可删除
        this.checkDelete4Electric(deviceNo,deviceType);

        // 查询当前设备关系记录
        MeterRelation meterRelation = meterRelationService.queryByCno(cno);

        // 检查设备是否存在
        DeviceInfo deviceInfo = deviceInfoService.queryDeviceInfoByCno(cno);
        if (deviceInfo == null) {
            throw new BusinessException("要删除的设备不存在，请重新刷新页面");
        }

        // 删除客户的水电气设备
        this.deleteSingleDevice(cno);

        // 查询客户关联表信息
        CustomerDevMap devMap = customerDevMapService.queryByCno(cno);

        // 删除表和客户的关系
        customerDevMapService.deleteByPrimaryKey(devMap.getId());

        // 删除客户IC卡信息
        feeAcctService.deleteByParams(customerNo,cno);

        // 充值缴费记录为无效
        feePayService.disableFeePayByCustomerNoAndCno(customerNo,cno);

        // 删除与该设备关联的微信记录
        customerWxBindService.deleteByParam(customerNo,devMap.getMeterUserNo(),devMap.getDeviceType());

        if (DeviceType.ELECTRIC_METER.getCode().equalsIgnoreCase(deviceType)) {
            // 目前方案表中只可能选择到电表
            List<QuerySchememet> querySchememets = querySchememetService.queryByParams(customerNo, cno);
            this.deleteSchemeInfo(querySchememets);
        }

        // redis 操作
        Set<String> totalKey = Sets.newHashSet();

        // 删除设备关系表缓存
        totalKey.add(RedisKeyConstant.DEVICE_RELATION_PREFIX + cno);

        // 删除设备信息缓存
        totalKey.add(RedisKeyConstant.DEVICE_PREFIX + cno);

        // 删除设备是否存在孩子节点缓存
        totalKey.add(RedisKeyConstant.DEVICE_CHILD_PREFIX + cno);

        String parentCno = meterRelation.getpMeterCno();
        if (!"0".equals(parentCno)) {
            totalKey.add(RedisKeyConstant.DEVICE_CHILD_PREFIX + parentCno);
        }
        redisUtil.batchDeleteKeys(totalKey);

    }

    /**
     * 删除设备时，方案表相关删除或更新
     * @param querySchememets
     */
    private void deleteSchemeInfo(List<QuerySchememet> querySchememets) {
        if (!CollectionUtils.isEmpty(querySchememets)) {
            Set<Integer> ids = Sets.newHashSet();
            Set<String> schemeFlagSet = Sets.newHashSet();
            Map<String,Integer> deleteMap = Maps.newHashMap();
            for (QuerySchememet querySchememet : querySchememets) {
                ids.add(querySchememet.getId());
                String schemeFlag = querySchememet.getSchemeFlag();
                schemeFlagSet.add(schemeFlag);
                if (deleteMap.containsKey(schemeFlag)) {
                    Integer integer = deleteMap.get(schemeFlag);
                    integer++;
                    deleteMap.put(schemeFlag,integer);
                } else {
                    deleteMap.put(schemeFlag,1);
                }
            }

            // 批量删除方案设备详情信息
            querySchememetService.batchDeleteByIds(ids);

            // 更新方案记录表中电表数量
            List<QueryScheme> querySchemes = querySchemeService.batchQueryBySchemeFlags(schemeFlagSet);
            Set<Integer> deleteIds = Sets.newHashSet();
            List<QueryScheme> updateIds = Lists.newArrayList();
            for (QueryScheme queryScheme : querySchemes) {
                String schemeFlag = queryScheme.getSchemeFlag();
                Integer deleteCount = deleteMap.get(schemeFlag);
                Integer userCount = queryScheme.getUserCount();
                int num = userCount - deleteCount;
                if (num == 0) {
                    // 减了后，该方案下没有关联表，需要删除该方案
                    deleteIds.add(queryScheme.getId());
                } else {
                    // 需要减一的集合
                    QueryScheme param = new QueryScheme();
                    param.setId(queryScheme.getId());
                    param.setUserCount(num);
                    updateIds.add(param);
                }
            }

            if (!deleteIds.isEmpty()) {
                // 删除方案
                querySchemeService.deleteByIds(deleteIds);
            }

            if (!updateIds.isEmpty()) {
                // 更新方案下关联的电表数量
                querySchemeService.updateUserCountByIds(updateIds);
            }
        }
    }

    /**
     * 校验电表删除时，是否存在挂接孩子节点
     * @param deviceNo
     * @param deviceType
     */
    private void checkDelete4Electric(String deviceNo, String deviceType) {
        if (DeviceType.ELECTRIC_METER.getCode().equals(deviceType)) {
            String cno = CNoUtil.CreateCNo(deviceType,deviceNo);
            // 判断该电表下是否存在孩子节点
            List<MeterRelation> meterRelations = meterRelationService.queryChildNodeByParentCno(cno);
            if (!CollectionUtils.isEmpty(meterRelations)) {
                throw new BusinessException("电表[" + deviceNo + "]下存在挂接的分表,不允许删除");
            }
        }
    }

    @Override
    @Transactional
    public void addSingleElectricMeter(ElectricMeterAddParam param, String customerNo, Long currentUserId) throws BusinessException {
        // 查询客户信息是否存在
        CustomerInfo info = new CustomerInfo();
        info.setCustomerNo(customerNo);
        CustomerInfo customerInfo = customerInfoMapper.selectOne(info);
        if (customerInfo == null) {
            throw new BusinessException("客户档案不存在，请重新刷新页面");
        }

        // 表计户号补全12位
        String meterUserNo = CNoUtil.fillStrLengthTo12(param.getMeterUserNo());
        param.setMeterUserNo(meterUserNo);

        // 校验电表相关信息
        this.checkElectricMeter(customerInfo.getOrgNo(),param);

        // 添加电表相关信息
        this.addElectricMeter(param,customerInfo);

        // 更新用户费用表记录
        this.updateCustomerCostRecord(customerNo);

        // redis操作
        String cno = CNoUtil.CreateCNo(DeviceType.ELECTRIC_METER.getCode(),param.getDeviceNo());
        this.saveDeviceRedis(cno);
    }


    @Override
    @Transactional
    public void addSingleWaterMeter(WaterMeterAddParam param, String customerNo, Long currentUserId) throws BusinessException {
        // 重新设置表号
        String waterDeviceNo = CNoUtil.createGasDeviceNo(param.getWaterType(), param.getDeviceNo());
        param.setDeviceNo(waterDeviceNo);

        // 表计户号补全12位
        String meterUserNo = CNoUtil.fillStrLengthTo12(param.getMeterUserNo());
        param.setMeterUserNo(meterUserNo);

        // 校验水表相关信息
        this.checkWaterMeter(param);

        // 查询客户信息是否存在
        CustomerInfo info = new CustomerInfo();
        info.setCustomerNo(customerNo);
        CustomerInfo customerInfo = customerInfoMapper.selectOne(info);
        if (customerInfo == null) {
            throw new BusinessException("客户档案不存在，请重新刷新页面");
        }

        // 添加水表相关信息
        this.addWaterMeter(param,customerInfo);

        // 更新用户费用表记录
        this.updateCustomerCostRecord(customerNo);

        // redis操作
        String cno = CNoUtil.CreateCNo(DeviceType.WATER_METER.getCode(),param.getDeviceNo());
        this.saveDeviceRedis(cno);
    }

    /**
     * 更新用户费用记录表
     * @param customerNo
     */
    private void updateCustomerCostRecord(String customerNo) {
        CustomerInfoCost cost = customerInfoCostService.queryByCustomerNo(customerNo);
        // 更新
        CustomerInfoCost updateParam = new CustomerInfoCost();
        updateParam.setId(cost.getId());
       /* BigDecimal remainAmount = initAmount.add(cost.getRemainAmount());
        updateParam.setRemainAmount(remainAmount);
        BigDecimal totalAmount = initAmount.add(cost.getTotalAmount());
        updateParam.setTotalAmount(totalAmount);*/
        updateParam.setCalcTime(new Date());
        updateParam.setUpdateTime(new Date());
        customerInfoCostService.updateByPrimaryKeySelective(updateParam);
    }

    @Override
    @Transactional
    public void addSingleGasMeter(GasMeterAddParam param, String customerNo, Long currentUserId) throws BusinessException {
        // 重新设置表号
        String gasDeviceNo = CNoUtil.createGasDeviceNo(param.getGasType(), param.getDeviceNo());
        param.setDeviceNo(gasDeviceNo);

        // 表计户号补全12位
        String meterUserNo = CNoUtil.fillStrLengthTo12(param.getMeterUserNo());
        param.setMeterUserNo(meterUserNo);

        // 校验气表相关信息
        this.checkGasMeter(param);

        // 查询客户信息是否存在
        CustomerInfo info = new CustomerInfo();
        info.setCustomerNo(customerNo);
        CustomerInfo customerInfo = customerInfoMapper.selectOne(info);
        if (customerInfo == null) {
            throw new BusinessException("客户档案不存在，请重新刷新页面");
        }

        // 添加气表相关信息
        this.addGasMeter(param,customerInfo);

        // 更新用户费用表记录
        this.updateCustomerCostRecord(customerNo);

        // redis操作
        String cno = CNoUtil.CreateCNo(DeviceType.GAS_METER.getCode(),param.getDeviceNo());
        this.saveDeviceRedis(cno);
    }

    /**
     * 将设备信息放入redis
     * @param cno
     */
    private void saveDeviceRedis(String cno) {
        DeviceMeterParam meterParam = deviceMeterParamService.queryEffectiveParamByCno(cno);
        DeviceInfo deviceInfo = deviceInfoService.queryDeviceInfoByCno(cno);
        DeviceCacheVo cacheVo = new DeviceCacheVo();
        BeanUtils.copyProperties(deviceInfo,cacheVo);
        cacheVo.setIsImportant(meterParam.getIsImportant());
        String key = RedisKeyConstant.DEVICE_PREFIX + deviceInfo.getCno();
        redisUtil.set(key,JSON.toJSONString(cacheVo));

        MeterRelation meterRelation = meterRelationService.queryByCno(cno);
        DeviceRelationCacheVo relationCacheVo = new DeviceRelationCacheVo();
        BeanUtils.copyProperties(meterRelation,relationCacheVo);
        String relationKey = RedisKeyConstant.DEVICE_RELATION_PREFIX + cno;
        redisUtil.set(relationKey,JSON.toJSONString(relationCacheVo));
    }

    /**
     * 采集方式是LoraWan时，设置一些特殊的字段
     * @param param
     */
    private void setSpecialColumn(DeviceMeterParam param) {
        param.setMoteEui(param.getCommCollectionNo());
        String appEUI = sysConfigService.getConfigValue("AppEUI");
        param.setAppEui(appEUI);
        String appKey = sysConfigService.getConfigValue("AppKey");
        param.setAppKey(appKey);
    }

    @Override
    public CustomerDeviceInfo getCustomerDetails(String customerNo, String deviceCno) throws BusinessException {
        CustomerDeviceInfo customerDeviceInfo = new CustomerDeviceInfo();
        CustomerInfo param = new CustomerInfo();
        param.setCustomerNo(customerNo);
        CustomerInfo customerInfo = customerInfoMapper.selectOne(param);
        BeanUtils.copyProperties(customerInfo, customerDeviceInfo);

        // 查询设备参数表信息
        DeviceMeterParam meterParam = deviceMeterParamService.queryEffectiveParamByCno(deviceCno);
        BeanUtils.copyProperties(meterParam,customerDeviceInfo);
        String paramFlag = meterParam.getParamFlag();
        DeviceMeterConfig deviceMeterConfig = deviceMeterConfigService.queryByParamFlag(paramFlag);
        if(deviceMeterConfig!=null) {
            customerDeviceInfo.setCommBaudrate(deviceMeterConfig.getCommBaudrate());
        }
        if(8==meterParam.getDeviceType()){
            meterParam.setRatio(null);
        }
        if(9==meterParam.getDeviceType()){
            meterParam.setRatio(null);
        }
        customerDeviceInfo.setRatio(meterParam.getRatio());
        String jzqNoByJzqno = CNoUtil.getJzqNoByJzqCno(customerDeviceInfo.getJzqCno());
        customerDeviceInfo.setJzqCno(jzqNoByJzqno);
        return customerDeviceInfo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public CustomerBatchImportInfo excelImport(HttpSession session, String excelName, String uuid) {
        logger.info("CustomerRecordServiceImpl-excelImport query: excelName: " + excelName + ", uuid: " + uuid);
        String parentPath = session.getServletContext().getRealPath("/WEB-INF/upload");
        File parentFile = new File(parentPath);
        if (!parentFile.exists() || !parentFile.isDirectory()) {
            parentFile.mkdirs();
        }
        //清理一天以前的文件
        FileUtil.deleteFile(parentFile, 24 * 60);
        User currentUser = (User) session.getAttribute("CURRENT_LOGIN_USER");
        long createUserID = currentUser.getId();
        // 创建csv文件
        String savePath = session.getServletContext().getRealPath("/WEB-INF/upload/" + uuid + ".csv");
        String errorPath = session.getServletContext().getRealPath("/WEB-INF/upload/" + uuid + "error" + excelName.substring(excelName.lastIndexOf(".")));
        File saveCSV = new File(savePath);
        if (!saveCSV.exists() || !saveCSV.isFile()) {
            try {
                saveCSV.createNewFile();
            } catch (IOException e) {
                logger.error("创建文件异常", e);
            }
        }
        //获取文件流
        File excel = new File(parentFile, excelName);
        Workbook wb = null;
        try {
            InputStream in = new FileInputStream(excel);
            // 兼容不同版本的excel
            if (excelName.endsWith(".xls")) {
                wb = new HSSFWorkbook(in);
            } else if (excelName.endsWith(".xlsx")) {
                wb = new XSSFWorkbook(in);
            }
        } catch (FileNotFoundException e) {
            logger.error("创建文件异常", e);
        } catch (IOException e) {
            logger.error("创建Workbook对象异常", e);
        }

        // 将数据存储在csv文件
        Map<String, Object> returnMap = this.excel2csv(wb, savePath, createUserID, uuid);
        List<Row> errorList = (List<Row>) returnMap.get("errorList");
        int total = (int) returnMap.get("total");
        // jdbc提交.csv文件到MySQL
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = dataSource.getConnection();
            String newSavePath = savePath.replace('\\', '/');
            String sql = "LOAD DATA LOCAL INFILE '" + newSavePath + "' REPLACE INTO TABLE " + "tmp_em_d_customerinfo"
                    + " FIELDS TERMINATED BY ','" + " ESCAPED BY '' LINES TERMINATED BY '\n' "
                    + "(customer_no,customer_name,customer_contact,customer_addr,"
                    + "property_name,create_time,create_user_id,device_type,device_no,comm_setup_sn,"
                    + "comm_point_code,jzq_no,collect_no,install_addr,alarm_threshold,import_id,"
                    + "is_err,err_info,cno,jzq_cno,comm_port,init_amount,org_no,ratio,dict_item_value,org_name,dict_item_name,"
                    + "collect_dev_type,customer_mark,meter_user_no,transformer_no,building_no,local_control,"
                    + "param_flag,device_model,device_factory,device_factory_val,off_scheme,off_param,read_value1,read_value2,read_value3,"
                    + "read_value4,read_value,alarm_threshold2,customer_brand,parent_meter_no,elec_type,elec_meter_category,overdraft_amount,alarm_threshold3,build_no,build_name)";
            pstmt = conn.prepareStatement(sql);
            if (pstmt.isWrapperFor(com.mysql.cj.api.jdbc.Statement.class)) {
                com.mysql.cj.jdbc.PreparedStatement mysqlStatement = pstmt.unwrap(com.mysql.cj.jdbc.PreparedStatement.class);
                mysqlStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("失败");
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        saveCSV.delete();// 操作结束删除csv文件
        //将临时表数据存入数据库
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", "tmp_em_d_customerinfo");
        map.put("importId", uuid);
        customerInfoMapper.importExcel2DB(map);
        CustomerBatchImportInfo importInfo = new CustomerBatchImportInfo();
        int status = (int) map.get("status");
        if (0 == status) {
            //临时表数据存入数据库全部失败
            logger.info("导入存储过程status返回值等于" + status);
            importInfo.setResult(0);
            return importInfo;
        } else {
            Workbook wb0 = null;
            Sheet sheet0 = null;
            // 兼容不同版本的excel
            if (excelName.endsWith(".xls")) {
                wb0 = new HSSFWorkbook();
                sheet0 = wb0.createSheet();
            }
            if (excelName.endsWith(".xlsx")) {
                wb0 = new HSSFWorkbook();
                sheet0 = wb0.createSheet();
            }

            //读取存入数据库异常的数据
            List<TmpCustomerInfo> listCinfo = tmpCustomerInfoService.queryErrorInfoByImportId(uuid);
            //遍历异常数据并存入Row
            for (int i = 0; i < listCinfo.size(); i++) {
                Row row = sheet0.createRow(i);
                row.createCell(0).setCellValue(listCinfo.get(i).getCustomerName());
                row.createCell(1).setCellValue(listCinfo.get(i).getCustomerContact());
                row.createCell(2).setCellValue(listCinfo.get(i).getOrgName());
                row.createCell(3).setCellValue(listCinfo.get(i).getCustomerAddr());
                row.createCell(4).setCellValue(listCinfo.get(i).getPropertyName());

                // 仪表类型
                String deviceType = listCinfo.get(i).getDeviceType();
                Cell cell5 = row.createCell(5);
                if (DeviceType.ELECTRIC_METER.getCode().equals(deviceType)) {
                    cell5.setCellValue("电表");
                } else if (DeviceType.WATER_METER.getCode().equals(deviceType)) {
                    cell5.setCellValue("水表");
                } else if (DeviceType.GAS_METER.getCode().equals(deviceType)) {
                    cell5.setCellValue("气表");
                } else {
                    cell5.setCellValue("");
                }

                // 表计计费类型
                row.createCell(6).setCellValue(listCinfo.get(i).getDictItemName());

                // 设置表计类型
                Cell cell7 = row.createCell(7);
                String deviceNo = listCinfo.get(i).getDeviceNo();
                if (DeviceType.ELECTRIC_METER.getCode().equals(deviceType)) {
                    cell7.setCellValue("");
                } else if (DeviceType.WATER_METER.getCode().equals(deviceType)) {
                    String code = deviceNo.substring(0, 2);
                    String message = CustomerInfoConstant.WaterMeterType.getMessageByCode(code);
                    cell7.setCellValue(message);
                } else if (DeviceType.GAS_METER.getCode().equals(deviceType)) {
                    String code = deviceNo.substring(0, 2);
                    String message = CustomerInfoConstant.GasMeterType.getMessageByCode(code);
                    cell7.setCellValue(message);
                } else {
                    cell7.setCellValue("");
                }

                // 仪表编号
                Cell cell8 = row.createCell(8);
                if (DeviceType.ELECTRIC_METER.getCode().equals(deviceType)) {
                    cell8.setCellValue(deviceNo);
                } else if (DeviceType.WATER_METER.getCode().equals(deviceType)) {
                    String tempNo = deviceNo.substring(2);
                    cell8.setCellValue(tempNo);
                } else if (DeviceType.GAS_METER.getCode().equals(deviceType)) {
                    String tempNo = deviceNo.substring(2);
                    cell8.setCellValue(tempNo);
                } else {
                    cell8.setCellValue(deviceNo);
                }

                // 采集方式
                Cell cell9 = row.createCell(9);
                int commPort = listCinfo.get(i).getCommPort();
                if (DeviceType.ELECTRIC_METER.getCode().equals(deviceType)) {
                    String message = CustomerInfoConstant.ElectricCommPort.getMessageByCode(commPort);
                    cell9.setCellValue(message);
                } else if (DeviceType.WATER_METER.getCode().equals(deviceType)) {
                    String message = CustomerInfoConstant.WaterCommPort.getMessageByCode(commPort);
                    cell9.setCellValue(message);
                } else if (DeviceType.GAS_METER.getCode().equals(deviceType)) {
                    String message = CustomerInfoConstant.GasCommPort.getMessageByCode(commPort);
                    cell9.setCellValue(message);
                }

                // 表计户号
                row.createCell(10).setCellValue(listCinfo.get(i).getMeterUserNo());

                // 倍率
                row.createCell(11).setCellValue(listCinfo.get(i).getRatio());

                // 集中器编号
                row.createCell(12).setCellValue(listCinfo.get(i).getJzqNo());

                // 采集设备类型
                Integer collectDevType = listCinfo.get(i).getCollectDevType();
                String message = null;
                if (DeviceType.ELECTRIC_METER.getCode().equals(deviceType)) {
                    message = CustomerInfoConstant.ElectricCollectDeviceType.getMessageByCode(collectDevType);
                } else if (DeviceType.WATER_METER.getCode().equals(deviceType)) {
                    message = CustomerInfoConstant.WaterCollectDeviceType.getMessageByCode(collectDevType);
                } else if (DeviceType.GAS_METER.getCode().equals(deviceType)) {
                    message = CustomerInfoConstant.GasCollectDeviceType.getMessageByCode(collectDevType);
                }
                row.createCell(13).setCellValue(message);

                // 采集器编号（节点编号或转换器编号，具体是节点还是转换器，根据采集设备类型来定）
                row.createCell(14).setCellValue(listCinfo.get(i).getCollectNo());

                // 安装位置
                row.createCell(15).setCellValue(listCinfo.get(i).getInstallAddr());

                // 告警阈值
                row.createCell(16).setCellValue(String.valueOf(listCinfo.get(i).getAlarmThreshold()));

                // 电表预留金额
                row.createCell(17).setCellValue(listCinfo.get(i).getInitAmount().toString());

                // 变压器号
                row.createCell(18).setCellValue(listCinfo.get(i).getTransformerNo());

                // 楼栋编号
                row.createCell(19).setCellValue(listCinfo.get(i).getBuildingNo());

                // 费控类型
                Integer localControl = listCinfo.get(i).getLocalControl();
                String controlMessage = CustomerInfoConstant.FeeControlType.getMessageByCode(localControl);
                row.createCell(20).setCellValue(controlMessage);

                // 错误信息
                row.createCell(21).setCellValue(listCinfo.get(i).getErrInfo());
                errorList.add(row);
            }
        }
        //导出错误数据
        if (errorList.size() > 1) {
            exportErrorData(errorList, errorPath);
        }
        importInfo.setFailCount(errorList.size() - 1);
        importInfo.setSuccessCount(total - errorList.size());
        importInfo.setResult(1);
        return importInfo;
    }

    /**
     * 删除excel中多余的空行
     * @param sheet
     * @return
     */
    public int checknull(Sheet sheet) {
        CellReference cellReference = new CellReference("A1");
        boolean flag;
        for (int i = cellReference.getRow(); i <= sheet.getLastRowNum(); ) {
            Row r = sheet.getRow(i);
            if (r == null) {
                // 如果是空行（即没有任何数据、格式），直接把它以下的数据往上移动
                sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
                continue;
            }
            flag = false;
            for (Cell c : r) {
                if (c.getCellType() != Cell.CELL_TYPE_BLANK) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                i++;
                continue;
            } else {//如果是空白行（即可能没有数据，但是有一定格式）
                if (i == sheet.getLastRowNum()) {
                    //如果到了最后一行，直接将那一行remove掉
                    sheet.removeRow(r);
                } else {//如果还没到最后一行，则数据往上移一行
                    sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
                }
            }
        }
        int rownum = sheet.getLastRowNum() + 1;
        return rownum;
    }

    /**
     * 判断是否含有特殊字符
     *
     * @param str
     * @return true为包含，false为不包含
     */
    public static boolean isSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？＄]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    private Map<String,Object> excel2csv(Workbook wb, String csvPath, long createUserID,String importID) {
        Map<String,Object> map = new HashMap<>();
        List<Row> errorList = new ArrayList<>();
        try {
            // 创建CSV写对象
            CsvWriter csvWriter = new CsvWriter(csvPath, ',', Charset.forName("UTF-8"));
            Sheet sheet = wb.getSheetAt(0);
            int total=checknull(sheet);

            map.put("total", total);
            List<TmpCustomerInfo> dataList = Lists.newArrayList();

            // 存放设备cno，用于去重
            Set<String> cnoSets = Sets.newHashSet();
            Set<String> meterUserNoSet = Sets.newHashSet();
            f1: for(Row row : sheet) {
                //判断是否是最后一条数据
                if(row.getRowNum()>total-1){
                    break;
                }
                //创建临时表对象
                TmpCustomerInfo cinfo = new TmpCustomerInfo();
                cinfo.setCommPointCode(666);
                cinfo.setCommSetupSn(666);
                boolean flag = true;
                //跳过表头
                if(row.getRowNum() == 0) {
                    //异常数据导出的表头
                    errorList.add(row);
                    Cell cell = row.createCell(row.getLastCellNum());
                    cell.setCellValue("失败原因");
                    continue;
                }
                //设置每个cell的type为String
                for(Cell cell : row) {
                    cell.setCellType(CellType.STRING);
                }
                //excel表的数据
                //用户姓名
                int index=0;
                Cell cell0 = row.getCell(index);
                if(cell0 == null) {
                    errorList.add(row);
                    Cell cell = row.createCell(row.getLastCellNum());
                    cell.setCellValue("用户名不能为空");
                    continue;
                } else {
                    /*if(isSpecialChar(cell0.getStringCellValue()) ){
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("用户名格式不正确");
                        continue;
                    }else */if(!StringUtil.isEmpty(cell0.getStringCellValue())&&cell0.getStringCellValue().length()>20){
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("用户名过长");
                        continue;
                    }else{
                        String customerName = cell0.getStringCellValue();
                        cinfo.setCustomerName(customerName);

                    }
                }

                //联系电话
                index++;
                Cell cell1 = row.getCell(index);
                if(cell1 == null  || StringUtils.isEmpty(cell1.getStringCellValue().replaceAll(" ",""))) {
                    errorList.add(row);
                    Cell cell = row.createCell(row.getLastCellNum());
                    cell.setCellValue("联系方式能为空");
                    continue;
                } else {
                    String contact = cell1.getStringCellValue();
                    if(StringUtil.isEmpty(contact) || !StringUtil.isPhone(contact)){
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("联系方式不正确");
                        continue;
                    } else {
                        cinfo.setCustomerContact(contact);
                    }
                }

                //所属组织机构
                index++;
                Cell cell2 = row.getCell(index);
                if(cell2 == null) {
                    cinfo.setOrgName(null);
                } else {
                    cinfo.setOrgName(cell2.getStringCellValue());
                }
                cinfo.setOrgNo(1000L);

                //用户地址
                index++;
                Cell cell3 = row.getCell(index);
                if(cell3 == null) {
                    cinfo.setCustomerAddr(null);
                }else {
                    String content=cell3.getStringCellValue();
                    if(StringUtil.isEmpty(content)||content.length()>50){
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("用户地址格式不正确");
                        continue;
                    }else{
                        cinfo.setCustomerAddr(cell3.getStringCellValue());
                    }
                }

                //门牌编号
                index++;
                Cell cell4 = row.getCell(index);
                if(cell4 == null) {
                    errorList.add(row);
                    Cell cell = row.createCell(row.getLastCellNum());
                    cell.setCellValue("门牌编号不能为空");
                    continue;
                } else {
                    if( !StringUtil.IsMatch(cell4.getStringCellValue(),"[\\w-]+")){
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("门牌编号格式不正确");
                        continue;
                    }
                    if (!StringUtil.isEmpty(cell4.getStringCellValue())&&cell4.getStringCellValue().length()>20){
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("门牌编号位数过长");
                        continue;
                    }
                    else{
                        String propertyName = cell4.getStringCellValue();
                        cinfo.setPropertyName(propertyName);
                    }
                }
                // 商家品牌
                index++;
                Cell cell31 = row.getCell(index);
                if(cell31==null){
                    cinfo.setCustomerBrand(null);
                }else{
                    if(!StringUtil.isEmpty(cell31.getStringCellValue())&&cell31.getStringCellValue().length()>50){
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("商家品牌位数过长");
                        continue;
                    }else{
                        String cellValue31 = cell31.getStringCellValue();
                        cinfo.setCustomerBrand(cellValue31);
                    }
                }
                //用户楼栋编号+名称
                index++;
                Cell cell37 = row.getCell(index);
                if(cell37==null||StringUtils.isEmpty(cell37.getStringCellValue().trim())){
                    errorList.add(row);
                    Cell cell = row.createCell(row.getLastCellNum());
                    cell.setCellValue("用户楼栋编号不能为空");
                    continue;
                }else{
                    String cellValue37 = cell37.getStringCellValue();
                    DictItem byNameAndValue = dictItemService.findByNameAndValue("15", cellValue37);
                    if (byNameAndValue == null) {
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("用户楼栋编号不存在");
                        continue;
                    }
                    cinfo.setBuildNo(byNameAndValue.getDictItemValue());
                    cinfo.setBuildName(byNameAndValue.getDictItemName());
                }
                //透支金额
                index++;
                Cell cell35 = row.getCell( index);
                if(cell35==null||StringUtils.isEmpty(cell35.getStringCellValue().trim())){
                    cinfo.setOverdraftAmount(BigDecimal.valueOf(Double.valueOf(0)));
                }else{
                    String cellValue35 = cell35.getStringCellValue().trim();
                    if (!StringUtil.isNumeric(cellValue35)) {
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("透支金额不正确");
                        continue;
                    }
                    if(!(0<= Double.valueOf(cell35.getStringCellValue()) && Double.valueOf(cell35.getStringCellValue()) <= 999999))
                    {
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("透支金额范围不正确");
                        continue;
                    }
                    cinfo.setOverdraftAmount(BigDecimal.valueOf(Double.valueOf(cellValue35)));
                }
                //仪表类型
                index++;
                String deviceType;
                Cell cell5 = row.getCell( index);
                if(cell5 == null || StringUtils.isEmpty(cell5.getStringCellValue().replaceAll(" ",""))) {
                    errorList.add(row);
                    Cell cell = row.createCell(row.getLastCellNum());
                    cell.setCellValue("仪表类型不能为空");
                    continue;
                } else {
                    deviceType = cell5.getStringCellValue();
                    if("电表".equals(deviceType)) {
                        cinfo.setDeviceType(DeviceType.ELECTRIC_METER.getCode());
                    } else if("水表".equals(deviceType)){
                        cinfo.setDeviceType(DeviceType.WATER_METER.getCode());
                    } else if("气表".equals(deviceType)) {
                        cinfo.setDeviceType(DeviceType.GAS_METER.getCode());
                    } else {
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("仪表类型不正确");
                        continue;
                    }
                }
                // 仪表编号
                index++;
                Cell cell8 = row.getCell( index);
                if(cell8 == null  || StringUtils.isEmpty(cell8.getStringCellValue().replaceAll(" ",""))) {
                    errorList.add(row);
                    Cell cell = row.createCell(row.getLastCellNum());
                    cell.setCellValue("仪表编号不能为空");
                    continue;
                }
                String deviceNo = cell8.getStringCellValue().trim();
                if(StringUtil.isEmpty(deviceNo) || !StringUtil.isNumeric(deviceNo)){
                    errorList.add(row);
                    Cell cell = row.createCell(row.getLastCellNum());
                    cell.setCellValue("仪表编号不正确");
                    continue;
                } else {
                    cinfo.setDeviceNo(deviceNo);
                }
                // 表计计费类型
                index++;
                Cell cell6 = row.getCell( index);
                if (cell6 == null||StringUtils.isEmpty(cell6.getStringCellValue().trim())) {
                    errorList.add(row);
                    Cell cell = row.createCell(row.getLastCellNum());
                    cell.setCellValue("表计计费类型不能为空");
                    continue;
                }
                String cell6Str = cell6.getStringCellValue();
                if("电表".equals(deviceType)) {
                    Integer value = CustomerInfoConstant.UseElectricType.getValueByMessage(cell6Str);
                    cinfo.setDictItemValue(String.valueOf(value));
                    cinfo.setDictItemName(cell6Str);
                } else if("水表".equals(deviceType)){
                    Integer value = CustomerInfoConstant.UseWaterType.getValueByMessage(cell6Str);
                    cinfo.setDictItemValue(String.valueOf(value));
                    cinfo.setDictItemName(cell6Str);
                } else if("气表".equals(deviceType)) {
                    Integer value = CustomerInfoConstant.UseGasType.getValueByMessage(cell6Str);
                    cinfo.setDictItemValue(String.valueOf(value));
                    cinfo.setDictItemName(cell6Str);
                } else {
                    errorList.add(row);
                    Cell cell = row.createCell(row.getLastCellNum());
                    cell.setCellValue("表计计费类型不正确");
                    continue;
                }
                // 表计类型(设置水表、气表的表号)
                index++;
                Cell cell7 = row.getCell(index);
                if("电表".equals(deviceType)) {
                    // do nothing
                } else {
                    if (cell7 == null) {
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("表计类型不能为空");
                        continue;
                    }

                    String cell7Str = cell7.getStringCellValue();
                    // 水气表，表计类型不能为空
                    if (StringUtils.isEmpty(cell7Str)) {
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("表计类型不能为空");
                        continue;
                    }

                    if("水表".equals(deviceType)){
                        String cellValue = cell7.getStringCellValue().trim();
                        // 水表类型编码
                        String code = CustomerInfoConstant.WaterMeterType.getCodeByMessage(cellValue);
                        if (StringUtils.isEmpty(code)) {
                            errorList.add(row);
                            Cell cell = row.createCell(row.getLastCellNum());
                            cell.setCellValue("水表表计类型不正确");
                            continue;
                        }
                        // 获取水表表号
                        String waterDeviceNo = CNoUtil.createWaterDeviceNo(code, deviceNo);
                        cinfo.setDeviceNo(waterDeviceNo);
                    } else if("气表".equals(deviceType)) {
                        String cellValue = cell7.getStringCellValue().trim();
                        // 气表类型编码
                        String code = CustomerInfoConstant.GasMeterType.getCodeByMessage(cellValue);
                        if (StringUtils.isEmpty(code)) {
                            errorList.add(row);
                            Cell cell = row.createCell(row.getLastCellNum());
                            cell.setCellValue("气表表计类型不正确");
                            continue;
                        }
                        // 获取气表表号
                        String gasDeviceNo = CNoUtil.createGasDeviceNo(code, deviceNo);
                        cinfo.setDeviceNo(gasDeviceNo);
                    }
                }
                //采集方式
                index++;
                Cell cell9 = row.getCell( index);
                if (cell9 == null) {
                    errorList.add(row);
                    Cell cell = row.createCell(row.getLastCellNum());
                    cell.setCellValue("采集方式不能为空");
                    continue;
                }
                String commPort = cell9.getStringCellValue();
                if(StringUtils.isEmpty(commPort.trim())) {
                    errorList.add(row);
                    Cell cell = row.createCell(row.getLastCellNum());
                    cell.setCellValue("采集方式不能为空");
                    continue;
                }
                if ("电表".equals(deviceType)) {
                    if (CustomerInfoConstant.ElectricCommPort.CARRIER.getMessage().equals(commPort)) {
                        cinfo.setCommPort(CustomerInfoConstant.ElectricCommPort.CARRIER.getCode());
                    } else if (CustomerInfoConstant.ElectricCommPort.LORA_WAN.getMessage().equals(commPort)) {
                        cinfo.setCommPort(CustomerInfoConstant.ElectricCommPort.LORA_WAN.getCode());
                    } else if (CustomerInfoConstant.ElectricCommPort.DIRECT_ONE.getMessage().equals(commPort)) {
                        cinfo.setCommPort(CustomerInfoConstant.ElectricCommPort.DIRECT_ONE.getCode());
                    } else if (CustomerInfoConstant.ElectricCommPort.DIRECT_TWO.getMessage().equals(commPort)) {
                        cinfo.setCommPort(CustomerInfoConstant.ElectricCommPort.DIRECT_TWO.getCode());
                    } else if (CustomerInfoConstant.ElectricCommPort.DIRECT_THREE.getMessage().equals(commPort)) {
                        cinfo.setCommPort(CustomerInfoConstant.ElectricCommPort.DIRECT_THREE.getCode());
                    } else {
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("采集方式不正确");
                        continue;
                    }
                } else if ("水表".equals(deviceType)) {
                    if (CustomerInfoConstant.WaterCommPort.LORA_WAN.getMessage().equals(commPort)) {
                        flag = false;
                        cinfo.setCommPort(CustomerInfoConstant.WaterCommPort.LORA_WAN.getCode());
                    } else if (CustomerInfoConstant.WaterCommPort.CARRIER.getMessage().equals(commPort)) {
                        cinfo.setCommPort(CustomerInfoConstant.WaterCommPort.CARRIER.getCode());
                    } else {
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("采集方式不正确");
                        continue;
                    }
                } else if ("气表".equals(deviceType)) {
                    if (CustomerInfoConstant.GasCommPort.LORA_WAN.getMessage().equals(commPort)) {
                        flag = false;
                        cinfo.setCommPort(CustomerInfoConstant.GasCommPort.LORA_WAN.getCode());
                    } else if (CustomerInfoConstant.GasCommPort.CARRIER.getMessage().equals(commPort)) {
                        cinfo.setCommPort(CustomerInfoConstant.GasCommPort.CARRIER.getCode());
                    } else {
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("采集方式不正确");
                        continue;
                    }
                }
                //仪表厂家
                index++;
                List<DeviceMeterConfig> deviceMeterConfigs;
                String factory;
                DictItem dictItem;
                Cell cell10 = row.getCell( index);
                if(cell10 == null || StringUtils.isEmpty(cell10.getStringCellValue().trim())) {
                    errorList.add(row);
                    Cell cell = row.createCell(row.getLastCellNum());
                    cell.setCellValue("仪表厂家不能为空");
                    continue;
                }else{
                    factory = cell10.getStringCellValue().trim();
                    List<DictItem> byDictName = dictItemService.findByDictName(factory);
                    if(CollectionUtils.isEmpty(byDictName)){
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("仪表厂家不正确");
                        continue;
                    }
                    dictItem = byDictName.get(0);
                    deviceMeterConfigs = deviceMeterConfigService.queryByMeterFactory(dictItem.getDictItemValue());
                    if(deviceMeterConfigs==null){
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("仪表厂家没有相关仪表型号");
                        continue;
                    }

                    cinfo.setDeviceFactoryVal(dictItem.getDictItemValue());
                    cinfo.setDeviceFactory(factory);
                }
                //仪表型号
                index++;
                Cell cell11 = row.getCell( index);
                if(cell11 == null || StringUtils.isEmpty(cell11.getStringCellValue().trim())) {
                    errorList.add(row);
                    Cell cell = row.createCell(row.getLastCellNum());
                    cell.setCellValue("仪表型号不能为空");
                    continue;
                }else {
                    boolean flag1=false;
                    for (DeviceMeterConfig deviceMeterConfig : deviceMeterConfigs) {
                        if(cell11.getStringCellValue().trim().equals(deviceMeterConfig.getMeterMode())){
                            flag1=true;
                        }
                    }
                    if(flag1){
                        DeviceMeterConfig deviceMeterConfig = deviceMeterConfigService.queryByMeterFactoryMeterMode(dictItem.getDictItemValue(), cell11.getStringCellValue());
                        cinfo.setParamFlag(deviceMeterConfig.getParamFlag());
                        cinfo.setDeviceModel(cell11.getStringCellValue());
                    }else{
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("仪表型号不正确");
                        continue f1;
                    }

                }
                // 费控类型
                index++;
                Cell cell22 = row.getCell( index);
                if(cell22 == null) {
                    cinfo.setLocalControl(0);
                } else {
                    String cellValue22 = cell22.getStringCellValue();
                    if (StringUtils.isEmpty(cellValue22)) {
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("费控类型不能为空");
                        continue;
                    }
                    Integer code = CustomerInfoConstant.FeeControlType.getCodeByMessage(cellValue22);
                    if (code == null) {
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("对应的费控类型不存在");
                        continue;
                    }
                    cinfo.setLocalControl(code);
                }
                //电表类别
                index++;
                Cell cell34 = row.getCell(  index);
                if(DeviceType.ELECTRIC_METER.getMessage().equals(deviceType)) {
                    if(cell34==null||StringUtils.isEmpty(cell34.getStringCellValue().trim())){
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("电表类别不能为空");
                        continue;
                    }else{
                        String cellValue34 = cell34.getStringCellValue();
                        DictItem byNameAndValue = dictItemService.findByNameAndValue("20", cellValue34);
                        if (byNameAndValue == null) {
                            errorList.add(row);
                            Cell cell = row.createCell(row.getLastCellNum());
                            cell.setCellValue("电表类别不存在");
                            continue;
                        }
                        cinfo.setElecMeterCategory(byNameAndValue.getDictItemValue());
                    }}
                // 上级总表
                index++;
                Cell cell32 = row.getCell( index);
                if(cell32==null||StringUtils.isEmpty(cell32.getStringCellValue().trim())){
                    if(ElectCategory.DEDUCTINGMETER.getCode().equals(cinfo.getElecMeterCategory())
                            ||ElectCategory.SUBMETER.getCode().equals(cinfo.getElecMeterCategory())
                            ){
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("上级总表不能为空");
                        continue;
                    }
                    cinfo.setParentMeterNo(null);
                }else{
                    String cellValue32 = cell32.getStringCellValue().trim();
                    String parentCno = CNoUtil.CreateCNo(cinfo.getDeviceType(),cellValue32);
                    DeviceInfo parentDevice = deviceInfoService.queryDeviceInfoByCno(parentCno);
                    if (parentDevice == null) {
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("系统中总表[" + parentCno + "]不存在");
                        continue;
                    }
                    cinfo.setParentMeterNo(cellValue32);
                }
                //用电类型
                index++;
                Cell cell33 = row.getCell( index);
                if(DeviceType.ELECTRIC_METER.getMessage().equals(deviceType)) {
                    if(cell33==null||StringUtils.isEmpty(cell33.getStringCellValue().trim())){
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("用电类型不能为空");
                        continue;
                    }else{
                        String cellValue33 = cell33.getStringCellValue();
                        DictItem byNameAndValue = dictItemService.findByNameAndValue("19", cellValue33);
                        if (byNameAndValue == null) {
                            errorList.add(row);
                            Cell cell = row.createCell(row.getLastCellNum());
                            cell.setCellValue("用电类型不存在");
                            continue;
                        }
                        cinfo.setElecType(byNameAndValue.getDictItemValue());
                    }
                }
                //表计户号
                index++;
                Cell cell12 = row.getCell( index);
                if(cell12 == null || StringUtils.isEmpty(cell12.getStringCellValue().replaceAll(" ",""))) {
                    errorList.add(row);
                    Cell cell = row.createCell(row.getLastCellNum());
                    cell.setCellValue("表计户号不能为空");
                    continue;
                } else {
                    if(!StringUtils.isEmpty(cell12.getStringCellValue())&&!StringUtil.isNumeric(cell12.getStringCellValue())){
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("表计户号只能输入数字");
                        continue;
                    }else{
                        String meterUserNo = cell12.getStringCellValue();
                        // 补全12位
                        String tempStr = CNoUtil.fillStrLengthTo12(meterUserNo);
                        cinfo.setMeterUserNo(tempStr);
                    }
                }

                // 倍率
                index++;
                Cell cell13 = row.getCell( index);
                if(cell13 == null||(!StringUtil.isEmpty(cell13.getStringCellValue())&&"0".equals(cell13.getStringCellValue()))) {
                    cinfo.setRatio(1);
                } else  {
                    if(!StringUtil.isEmpty(cell13.getStringCellValue())&&!StringUtil.IsMatch(cell13.getStringCellValue(),"\\d{1,10}")){
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("倍率不能输入小数");
                        continue;
                    }else{
                        cinfo.setRatio(Integer.parseInt(cell13.getStringCellValue()));
                    }
                }

                //集中器编号
                index++;
                Cell cell14 = row.getCell( index);
                if(cell14 == null || StringUtils.isEmpty(cell14.getStringCellValue().replaceAll(" ",""))) {
                    errorList.add(row);
                    Cell cell = row.createCell(row.getLastCellNum());
                    cell.setCellValue("集中器编号不能为空");
                    continue;
                } else {
                    if (flag && cell14.getStringCellValue().length() != 9) {
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("集中器编号长度不合法");
                        continue;
                    } else {
                        String jzqNo = cell14.getStringCellValue();
                        cinfo.setJzqNo(jzqNo);
                    }
                }
                // 采集设备类型
                index++;
                Cell cell15 = row.getCell( index);

                // 采集器编号
                index++;
                Cell cell16 = row.getCell( index);
                if (cell16 != null&& !"".equals(cell16.getStringCellValue().trim())) {
                    cinfo.setCollectNo(cell16.getStringCellValue());
                }
                if ("电表".equals(deviceType)) {
                    if (CustomerInfoConstant.ElectricCommPort.DIRECT_THREE.getMessage().equals(commPort) ||
                            CustomerInfoConstant.ElectricCommPort.DIRECT_TWO.getMessage().equals(commPort) ||
                            CustomerInfoConstant.ElectricCommPort.DIRECT_ONE.getMessage().equals(commPort)) {
                        cinfo.setCollectDevType(CustomerInfoConstant.ElectricCollectDeviceType.MOTE.getCode());
                    } else {
                        String cell14Str = cell15.getStringCellValue();
                        Integer code = CustomerInfoConstant.ElectricCollectDeviceType.getCodeByMessage(cell14Str);
                        if (code == null) {
                            errorList.add(row);
                            Cell cell = row.createCell(row.getLastCellNum());
                            cell.setCellValue("采集设备类型不匹配");
                            continue;
                        }
                        cinfo.setCollectDevType(code);
                        this.setCollectNoWhenMote(cinfo,code,cell16);
                    }
                } else if ("水表".equals(deviceType)) {
                    String cell14Str = cell15.getStringCellValue();
                    Integer code = CustomerInfoConstant.WaterCollectDeviceType.getCodeByMessage(cell14Str);
                    if (code == null) {
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("采集设备类型不匹配");
                        continue;
                    }
                    cinfo.setCollectDevType(code);
                    this.setCollectNoWhenMote(cinfo,code,cell16);
                } else if ("气表".equals(deviceType)) {
                    String cell14Str = cell15.getStringCellValue();
                    Integer code = CustomerInfoConstant.GasCollectDeviceType.getCodeByMessage(cell14Str);
                    if (code == null) {
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("采集设备类型不匹配");
                        continue;
                    }
                    cinfo.setCollectDevType(code);
                    this.setCollectNoWhenMote(cinfo,code,cell16);
                }

                // 安装位置
                index++;
                Cell cell17 = row.getCell( index);
                if(cell17 == null) {
                    cinfo.setInstallAddr(null);
                } else {
                    if(!StringUtils.isEmpty(cell17.getStringCellValue())&&cell17.getStringCellValue().length()>50){
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("安装地址长度过长");
                        continue;
                    }else{

                        cinfo.setInstallAddr(cell17.getStringCellValue());
                    }
                }

                // 告警阈值
                index++;
                Cell cell18 = row.getCell( index);
                String stringCellValue = cell18.getStringCellValue();
                if(cell18 == null||StringUtils.isEmpty(stringCellValue)) {
                    cinfo.setAlarmThreshold(BigDecimal.ZERO);
                } else {
                    if(!StringUtil.isInteger(stringCellValue)){
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("告警金额只能是整数");
                        continue;
                    }
                    if(0 > Double.valueOf(stringCellValue) || Double.valueOf(stringCellValue) > 99999){
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("告警金额范围不正确");
                        continue;
                    }
                    cinfo.setAlarmThreshold(BigDecimal.valueOf(Double.valueOf(stringCellValue)));
                }
                //告警阀值2
                index++;
                Cell cell23 = row.getCell( index);
                if(cell23 == null||StringUtils.isEmpty(cell23.getStringCellValue().trim())) {
                    errorList.add(row);
                    Cell cell = row.createCell(row.getLastCellNum());
                    cell.setCellValue("告警阀值2不能为空");
                    continue;
                } else {
                    if (!(0 <= Double.valueOf(cell23.getStringCellValue()) && Double.valueOf(cell23.getStringCellValue()) <= 99999.99)) {
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("告警阀值2不正确");
                        continue;
                    }
                    cinfo.setAlarmThreshold2(BigDecimal.valueOf(Double.valueOf(cell23.getStringCellValue())));
                }
                //告警阈值3
                index++;
                Cell cell36 = row.getCell( index);
                if(cell36==null||StringUtils.isEmpty(cell36.getStringCellValue().trim())){
                    errorList.add(row);
                    Cell cell = row.createCell(row.getLastCellNum());
                    cell.setCellValue("告警阈值3不能为空");
                    continue;
                }else{
                    String cellValue36 = cell36.getStringCellValue().trim();
                    if (!(0 <= Double.valueOf(cell23.getStringCellValue()) && Double.valueOf(cell23.getStringCellValue()) <= 99999.99)) {
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("告警阈值3不正确");
                        continue;
                    }
                    cinfo.setAlarmThreshold3(BigDecimal.valueOf(Double.valueOf(cellValue36)));
                }
                // 表计预留金额
                index++;
                Cell cell19 = row.getCell( index);
                if(cell19 == null) {
                    cinfo.setInitAmount(BigDecimal.valueOf(0));
                } else {
                    if (!StringUtils.isEmpty(cell19.getStringCellValue())) {
                        if (!(0 <= Double.valueOf(cell19.getStringCellValue()) && Double.valueOf(cell19.getStringCellValue()) <= 1000)) {
                            errorList.add(row);
                            Cell cell = row.createCell(row.getLastCellNum());
                            cell.setCellValue("预留金额格式不正确");
                            continue;
                        } else {
                            cinfo.setInitAmount(BigDecimal.valueOf(Double.parseDouble(cell19.getStringCellValue())));
                        }
                    } else {
                        cinfo.setInitAmount(BigDecimal.valueOf(0));
                    }
                }
                // 通断方式
                index++;
                Cell cell24 = row.getCell( index);
                if(cell24 == null||StringUtils.isEmpty(cell24.getStringCellValue().trim())) {
                    errorList.add(row);
                    Cell cell = row.createCell(row.getLastCellNum());
                    cell.setCellValue("通断方式不能为空");
                    continue;
                } else {
                    String onffScheme = cell24.getStringCellValue().trim();
                    Integer code =CustomerInfoConstant.OffScheme.getCodebyMessage(onffScheme);
                    if (code == null) {
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("通断方式不正确");
                        continue;
                    }
                    cinfo.setOffScheme(String.valueOf(code));
                }
                //延时跳闸时间
                index++;
                Cell cell25 = row.getCell( index);
                if(cell25 == null||StringUtils.isEmpty(cell25.getStringCellValue().trim())) {
                    errorList.add(row);
                    Cell cell = row.createCell(row.getLastCellNum());
                    cell.setCellValue("延时拉闸时间不能为空");
                    continue;
                } else {
                    String onffParam = cell25.getStringCellValue().trim();
                    String onffScheme = cell24.getStringCellValue().trim();
                    if(CustomerInfoConstant.OffScheme.delayOff.getDesc().equals(onffScheme)&&
                            !StringUtil.IsMatch(onffParam,"[1-9][0-9]?")
                            ){
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("延时拉闸时间不正确");
                        continue;
                    }
                    cinfo.setOffParam(BigDecimal.valueOf(Double.valueOf(onffParam)));
                }
                // 变压器号
                index++;
                Cell cell20 = row.getCell( index);
                if(cell20 == null) {
                    cinfo.setTransformerNo("");
                } else {
                    cinfo.setTransformerNo(cell20.getStringCellValue());
                }

                // 楼栋编号
                index++;
                Cell cell21 = row.getCell( index);
                if(cell21 == null) {
                    cinfo.setBuildingNo("");
                } else {
                    cinfo.setBuildingNo(cell21.getStringCellValue());
                }




                //表计总示数
                index++;
                Cell cell26 = row.getCell( index);
                if(cell26 == null||StringUtils.isEmpty(cell26.getStringCellValue().trim())) {
                    errorList.add(row);
                    Cell cell = row.createCell(row.getLastCellNum());
                    cell.setCellValue("表计总示数不能为空");
                    continue;
                } else {
                    String onffParam = cell26.getStringCellValue().trim();
                    if (!StringUtil.isDecimal(onffParam)) {
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("表计总示数不正确");
                        continue;
                    }
                    cinfo.setReadValue(BigDecimal.valueOf(Double.valueOf(onffParam)));
                }
                //表计尖示数
                index++;
                Cell cell27 = row.getCell( index);
                if(cell27 == null||StringUtils.isEmpty(cell27.getStringCellValue().trim())) {
                    cinfo.setReadValue1(BigDecimal.valueOf(0));
                } else {
                    String onffParam = cell27.getStringCellValue().trim();
                    if (!StringUtil.isDecimal(onffParam)) {
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("表计尖示数不正确");
                        continue;
                    }
                    cinfo.setReadValue1(BigDecimal.valueOf(Double.valueOf(onffParam)));
                }
                //表计峰示数
                index++;
                Cell cell28 = row.getCell( index);
                if(cell28 == null||StringUtils.isEmpty(cell28.getStringCellValue().trim())) {
                    cinfo.setReadValue2(BigDecimal.valueOf(0));
                } else {
                    String onffParam = cell28.getStringCellValue().trim();
                    if (!StringUtil.isDecimal(onffParam)) {
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("表计峰示数不正确");
                        continue;
                    }
                    cinfo.setReadValue2(BigDecimal.valueOf(Double.valueOf(onffParam)));
                }
                //表计平示数
                index++;
                Cell cell29 = row.getCell( index);
                if(cell29 == null||StringUtils.isEmpty(cell29.getStringCellValue().trim())) {
                    cinfo.setReadValue3(BigDecimal.valueOf(0));
                } else {
                    String onffParam = cell29.getStringCellValue().trim();
                    if (!StringUtil.isDecimal(onffParam)) {
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("表计平示数不正确");
                        continue;
                    }
                    cinfo.setReadValue3(BigDecimal.valueOf(Double.valueOf(onffParam)));
                }
                //表计谷示数
                index++;
                Cell cell30 = row.getCell( index);
                if(cell30 == null||StringUtils.isEmpty(cell30.getStringCellValue().trim())) {
                    cinfo.setReadValue4(BigDecimal.valueOf(0));
                } else {
                    String onffParam = cell30.getStringCellValue().trim();
                    if (!StringUtil.isDecimal(onffParam)) {
                        errorList.add(row);
                        Cell cell = row.createCell(row.getLastCellNum());
                        cell.setCellValue("表计谷示数不正确");
                        continue;
                    }
                    cinfo.setReadValue4(BigDecimal.valueOf(Double.valueOf(onffParam)));
                }
                if(cinfo.getReadValue().subtract(cinfo.getReadValue1().add(cinfo.getReadValue2()).add(cinfo.getReadValue3()).add(cinfo.getReadValue4())).abs().doubleValue()>0.02){
                    errorList.add(row);
                    Cell cell = row.createCell(row.getLastCellNum());
                    cell.setCellValue("表计尖峰平谷之和与总示数相差大于0.02");
                    continue;
                }
                //服务端生成的数据
                String cNo = CNoUtil.CreateCNo(cinfo.getDeviceType(), cinfo.getDeviceNo());
                cinfo.setCno(cNo);
                if (cnoSets.contains(cNo)) {
                    errorList.add(row);
                    Cell cell = row.createCell(row.getLastCellNum());
                    cell.setCellValue("不允许存在重复的设备编号");
                    continue;
                } else {
                    cnoSets.add(cNo);
                }
                String key = cinfo.getDeviceType() + cinfo.getMeterUserNo();
                if (meterUserNoSet.contains(key)) {
                    errorList.add(row);
                    Cell cell = row.createCell(row.getLastCellNum());
                    cell.setCellValue("不允许存在重复的表计户号");
                    continue;
                } else {
                    meterUserNoSet.add(key);
                }
                cinfo.setCreateUserId(createUserID);
                Date date = new Date();
                cinfo.setCreateTime(date);
                cinfo.setImportId(importID);
                cinfo.setIsErr(0);
                cinfo.setCustomerMark(0);
                String jzqCNo = CNoUtil.CreateCNo(DeviceType.JZQ.getCode(), cinfo.getJzqNo());
                cinfo.setJzqCno(jzqCNo);

                dataList.add(cinfo);
            }

            // 生成用户唯一标识
            setCustomerNo(dataList);

            // 写csv文件
            writeCsvFile(csvWriter,dataList);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        map.put("errorList", errorList);
        return map;
    }

    /**
     * 当模板上选择节点的时候，设置采集器号
     * @param cinfo
     * @param code
     * @param cell15
     */
    private void setCollectNoWhenMote(TmpCustomerInfo cinfo, Integer code, Cell cell15) {
        if (CustomerInfoConstant.ElectricCollectDeviceType.MOTE.getCode().equals(code)) {
            if (cell15 == null || StringUtils.isEmpty(cell15.getStringCellValue())) {
                // 采集器号取仪表编号
                cinfo.setCollectNo(cinfo.getDeviceNo());
            } else {
                // 如果输入的全是0字符串
                String str = cell15.getStringCellValue();
                String s = str.trim().replaceAll("0", "");
                if (StringUtils.isEmpty(s)) {
                    // 采集器号取仪表编号
                    cinfo.setCollectNo(cinfo.getDeviceNo());
                } else {
                    cinfo.setCollectNo(str);
                }
            }
        }
    }

    /**
     * 设置用户唯一标识
     * @param dataList
     */
    private void setCustomerNo(List<TmpCustomerInfo> dataList) {
        // 分类
        ImmutableListMultimap<String, TmpCustomerInfo> listMultimap = Multimaps.index(dataList, new Function<TmpCustomerInfo, String>() {
            @Nullable
            @Override
            public String apply(@Nullable TmpCustomerInfo tmpCustomerInfo) {
                return tmpCustomerInfo.getPropertyName();
            }
        });

        // 设置
        for (Map.Entry<String, Collection<TmpCustomerInfo>> entry : listMultimap.asMap().entrySet()) {
            Collection<TmpCustomerInfo> value = entry.getValue();
            String uuid = UuidUtil.getUuid();
            for (TmpCustomerInfo info : value) {
                info.setCustomerNo(uuid);
            }
        }
    }

    /**
     * 写csv文件
     * @param csvWriter
     * @param dataList
     * @throws Exception
     */
    private void writeCsvFile(CsvWriter csvWriter, List<TmpCustomerInfo> dataList) throws IOException, IllegalAccessException {
        for (TmpCustomerInfo info : dataList) {
            //通过反射获取属性字段的值并包装成字符数组写入csv文件
            Class<? extends TmpCustomerInfo> cinfoCla = info.getClass();
            Field[] fs = cinfoCla.getDeclaredFields();
            String[] content = new String[fs.length - 1];
            for (int i = 2; i < fs.length; i++) {
                Field f = fs[i];
                f.setAccessible(true); // 设置些属性是可以访问的
                Object val = f.get(info);// 得到此属性的值
                if (val == null) {
                    content[i - 2] = "";
                    continue;
                }
                if (val instanceof Date) {
                    Date tempDate = (Date) val;
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    content[i - 2] = format.format(tempDate);
                } else {
                    content[i - 2] = val.toString();
                }
            }
            csvWriter.writeRecord(content);
        }
        csvWriter.close();
    }

    public void exportErrorData(List<Row> errorList, String errorPath) {
        // 生成与客户对应版本的excel文件
        Workbook errorwb = null;
        if (errorPath.endsWith(".xls")) {
            errorwb = new HSSFWorkbook();
        }
        if (errorPath.endsWith(".xlsx")) {
            errorwb = new XSSFWorkbook();
        }
        // 创建新数据Sheet,Row,Cell的对象
        Sheet sheet = errorwb.createSheet("sheet0");
        Row row = null;
        Cell cell = null;
        // 错误数据Row,Cell的对象
        Row row1 = null;
        Cell cell1 = null;
        for (int i = 0; i < errorList.size(); i++) {
            row = sheet.createRow(i);
            row1 = errorList.get(i);
            for (int j = 0; j < row1.getLastCellNum(); j++) {
                cell = row.createCell(j);
                cell1 = row1.getCell(j);
                if (cell1 != null) {
                    cell1.setCellType(CellType.STRING);
                    cell.setCellValue(row1.getCell(j).getStringCellValue());
                }
            }
        }
        // 输出errorExcel文件
        OutputStream output = null;
        File errorFile = new File(errorPath);
        if (!errorFile.exists() || !errorFile.isFile()) {
            try {
                errorFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            output = new FileOutputStream(errorFile, true);
            errorwb.write(output);
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //通过customerNo查询详细信息
    @Override
    public CustomerInfo4ChangeMeterDetail queryDetail(String customerNo, String cno) {
        CustomerInfo param = new CustomerInfo();
        param.setCustomerNo(customerNo);
        CustomerInfo customerInfo = customerInfoMapper.selectOne(param);
        CustomerInfo4ChangeMeterDetail meterDetail = new CustomerInfo4ChangeMeterDetail();
        BeanUtils.copyProperties(customerInfo, meterDetail);

        // 查询表计户号
        CustomerDevMap devMap = customerDevMapService.queryByCno4ChangeDetail(cno);

        // 根据表计户号，查询该表计户号对应的最新表信息
        CustomerDevMap newDevMap = customerDevMapService.queryByMeterUserNo(devMap.getDeviceType(), devMap.getMeterUserNo());

        // 查询表计计费类型
        DeviceMeterParam meterParam = deviceMeterParamService.queryDictIteamByCno(newDevMap.getCno());
        String type = cno.substring(0, 2);
        DictItem dictItem = null;
        if (DeviceType.ELECTRIC_METER.getCode().equals(type)) {
            dictItem = dictItemService.findByCodeAndValue(DictCodeConstant.USE_ELECTRIC_TYPE.getDictCode(), meterParam.getDictItemValue());
        } else if (DeviceType.WATER_METER.getCode().equals(type)) {
            dictItem = dictItemService.findByCodeAndValue(DictCodeConstant.USE_WATER_TYPE.getDictCode(), meterParam.getDictItemValue());
        } else if (DeviceType.GAS_METER.getCode().equals(type)) {
            dictItem = dictItemService.findByCodeAndValue(DictCodeConstant.USE_GAS_TYPE.getDictCode(), meterParam.getDictItemValue());
        }

        meterDetail.setDictItemName(dictItem.getDictItemName());
        return meterDetail;
    }

    @Override
    public void checkByPropertyName4Add(String propertyName) throws BusinessException {
        Condition condition = new Condition(CustomerInfo.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("propertyName", propertyName);
        int count = customerInfoMapper.selectCountByCondition(condition);
        if (count > 0) {
            throw new BusinessException("用户门牌编号已存在");
        }
    }

    @Override
    public void checkByPropertyName4Edit(Long id, String propertyName) {
        Condition condition = new Condition(CustomerInfo.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("propertyName", propertyName);
        criteria.andNotEqualTo("id", id);
        int count = customerInfoMapper.selectCountByCondition(condition);
        if (count > 0) {
            throw new BusinessException("用户门牌编号已存在");
        }
    }


    @Override
    public String countByGuidAndSendFlag(String guid) {
        // 查询抄表队列数据
        List<MeterReadQueue> meterReadQueues = meterReadQueueService.selectByQueueGuid(guid);
        if (CollectionUtils.isEmpty(meterReadQueues)) {
            throw new BusinessException("抄表队列数据不存在");
        }

        // 统计未抄数量，成功数量，失败数量
        int unCollectCount = 0;
        int successCount = 0;
        int errorCount = 0;
        for (MeterReadQueue meterReadQueue : meterReadQueues) {
            Integer readStatus = meterReadQueue.getReadStatus();
            if (RealTimeDataConstant.CollectMeterStatus.UN_COLLECT.getStatus().equals(readStatus)) {
                unCollectCount++;
            } else if (RealTimeDataConstant.CollectMeterStatus.COLLECT_SUCCESS.getStatus().equals(readStatus)) {
                successCount++;
            } else if (RealTimeDataConstant.CollectMeterStatus.COLLECT_FAIL.getStatus().equals(readStatus)) {
                errorCount++;
            }
        }

        String resultMesg = "成功: " + successCount + " 条; 失败: " + errorCount + " 条; 未下发: " + unCollectCount + " 条";
        return resultMesg;
    }

    @Override
    @Transactional
    public void closeAccount(String customerNo) {
        CustomerInfo updateParam = new CustomerInfo();
        updateParam.setIsEnabled(0);
        Condition condition = new Condition(CustomerInfo.class);
        condition.createCriteria().andEqualTo("customerNo", customerNo);
        customerInfoMapper.updateByConditionSelective(updateParam, condition);
    }

    @Override
    public List<QueryUserDTO> queryUser(RechargeUserQueryVo queryVo) {
        return customerInfoMapper.queryUser(queryVo);
    }

    @Override
    public QueryUserPayDTO queryUserPay(String customerNo, String deviceNo) {
        String cno = CNoUtil.CreateCNo(DeviceType.ELECTRIC_METER.getCode(), deviceNo);
        return customerInfoMapper.queryUserPay(customerNo, cno);
    }

    @Override
    public List<UserFuzzyQueryInfo> fuzzyQuery(UserFuzzyQueryVo queryVo) {
        return customerInfoMapper.fuzzyQuery(queryVo);
    }

    @Override
    public CustomerInfo queryByCustomerNo(String customerNo) {
        CustomerInfo param = new CustomerInfo();
        param.setCustomerNo(customerNo);
        return customerInfoMapper.selectOne(param);
    }

    @Override
    public CustomerDetailInfo queryDetails(String customerNo) {
        // 获取客户详细信息
        CustomerInfo customerInfo = this.queryByCustomerNo(customerNo);
        // 转成前端需要的
        CustomerInfoDetailInfo detailInfo = new CustomerInfoDetailInfo();
        BeanUtils.copyProperties(customerInfo,detailInfo);

        // 查询用户费用表记录
        CustomerInfoCost cost = customerInfoCostService.queryByCustomerNo(customerNo);
        detailInfo.setAlarmThreshold(cost.getAlarmThreshold());
        detailInfo.setAlarmThreshold1(cost.getAlarmThreshold1());
        detailInfo.setAlarmThreshold2(cost.getAlarmThreshold2());
        detailInfo.setOverdraftAmount(cost.getOverdraftAmount());
        detailInfo.setInitAmount(cost.getInitAmount());
        //查询用户电话信息
        String mobilePhones =  customerPhoneBindService.selectCustomerPhoneBind(customerNo);

        detailInfo.setMobilePhones(mobilePhones);
        // 查询组织信息
        Org org = orgService.queryByOrgNo(customerInfo.getOrgNo());
        detailInfo.setOrgName(org.getOrgName());

        // 查询客户档案下的所有设备
        List<CustomerDevMap> devMaps = customerDevMapService.queryByCustomerNo(customerNo);
        List<String> cnoList = Lists.newArrayList();
        for (CustomerDevMap devMap : devMaps) {
            cnoList.add(devMap.getCno());
        }

        // 批量查询设备信息
        List<DeviceInfo> deviceInfos = deviceInfoService.batchQueryByCnos(cnoList);
        // 取一条数据
        DeviceInfo tempDevice = deviceInfos.get(0);
        detailInfo.setOffScheme(tempDevice.getOffScheme());
        detailInfo.setOffParam(tempDevice.getOffParam());

        // 转map
        ImmutableMap<String, DeviceInfo> deviceInfoMap = Maps.uniqueIndex(deviceInfos, new Function<DeviceInfo, String>() {
            @Nullable
            @Override
            public String apply(@Nullable DeviceInfo deviceInfo) {
                return deviceInfo.getCno();
            }
        });

        // 批量查询设备参数表信息
        List<DeviceMeterParam> meterParams = deviceMeterParamService.findDeviceMeterParamByCnos(cnoList);
        Map<String,DeviceMeterParam> meterParamMap = Maps.newHashMap();
        Set<String> paramFlagSet = Sets.newHashSet();
        List<String> list = Lists.newArrayList();
        for (DeviceMeterParam meterParam : meterParams) {
            paramFlagSet.add(meterParam.getParamFlag());
            meterParamMap.put(meterParam.getCno(),meterParam);
            list.add(meterParam.getDictItemValue());
        }

        // 批量查询设备关系表
        List<MeterRelation> meterRelations = meterRelationService.batchQueryByMeterCnos(cnoList);
        Map<String,String> relationMap = Maps.newHashMap();
        for (MeterRelation meterRelation : meterRelations) {
            String meterCno = meterRelation.getMeterCno();
            String s = meterRelation.getpMeterCno();
            if ("0".equalsIgnoreCase(s)) {
                relationMap.put(meterCno,"");
            } else {
                String deviceNo = CNoUtil.getNo(s);
                relationMap.put(meterCno,deviceNo);
            }
        }

        // 查询字典表
        List<String> dictCodes = Lists.newArrayList();
        dictCodes.add(DictCodeConstant.USE_ELECTRIC_TYPE.getDictCode());
        dictCodes.add(DictCodeConstant.USE_WATER_TYPE.getDictCode());
        dictCodes.add(DictCodeConstant.USE_GAS_TYPE.getDictCode());
        List<DictItem> tempList = dictItemService.batchQuery(dictCodes, list);
        // 转map
        ImmutableMap<String, DictItem> tempMap = Maps.uniqueIndex(tempList, new Function<DictItem, String>() {
            @Nullable
            @Override
            public String apply(@Nullable DictItem dictItem) {
                return dictItem.getDictItemValue();
            }
        });

        // 根据参数标识，查询参数配置表信息
        ImmutableMap<String, DeviceMeterConfig> meterConfigMap = deviceMeterConfigService.batchQueryByParamFlags(paramFlagSet);
        List<String> dictItemValues = Lists.newArrayList();
        for (Map.Entry<String, DeviceMeterConfig> entry : meterConfigMap.entrySet()) {
            DeviceMeterConfig value = entry.getValue();
            dictItemValues.add(String.valueOf(value.getMeterFactory()));
        }

        // 查询设备工厂名称，值
        List<DictItem> dictItems = dictItemService.batchQueryByDictItemValues(dictItemValues);
        // 分组
        ImmutableMap<String, DictItem> dictItemMap = Maps.uniqueIndex(dictItems, new Function<DictItem, String>() {
            @Nullable
            @Override
            public String apply(@Nullable DictItem dictItem) {
                return dictItem.getDictItemValue();
            }
        });

        CustomerDetailInfo customerDetailInfo = new CustomerDetailInfo();
        for (CustomerDevMap devMap : devMaps) {
            String deviceType = devMap.getDeviceType();
            if(DeviceType.ELECTRIC_METER.getCode().equals(deviceType)){
                // 设置电表信息
                ElectricMeterInfo electricMeter = new ElectricMeterInfo();
                String cno = devMap.getCno();
                electricMeter.setCno(cno);
                DeviceInfo deviceInfo = deviceInfoMap.get(cno);
                electricMeter.setDeviceNo(deviceInfo.getDeviceNo());
                electricMeter.setOffScheme(deviceInfo.getOffScheme());
                electricMeter.setOffParam(deviceInfo.getOffParam());

                DeviceMeterParam meterParam = meterParamMap.get(cno);
                String jzqNo = CNoUtil.getJzqNoByJzqCno(meterParam.getJzqCno());
                electricMeter.setJzqNo(jzqNo);
                electricMeter.setCommPort(meterParam.getCommPort());

                DeviceMeterConfig deviceMeterConfig = meterConfigMap.get(meterParam.getParamFlag());
                electricMeter.setCommRule(deviceMeterConfig.getCommRule());
                electricMeter.setInitAmount(MathUtil.setPrecision(devMap.getInitAmount()));
                String meterFactory = deviceMeterConfig.getMeterFactory();
                electricMeter.setDeviceFactoryValue(meterFactory);
                DictItem item = dictItemMap.get(meterFactory);
                electricMeter.setDeviceFactoryName(item.getDictItemName());
                electricMeter.setCommFactorCnt(deviceMeterConfig.getCommFactorCnt());

                electricMeter.setParamFlag(deviceMeterConfig.getParamFlag());
                electricMeter.setMeterModeName(deviceMeterConfig.getMeterMode());
                electricMeter.setAlarmThreshold(MathUtil.setPrecision(devMap.getAlarmThreshold()));
                electricMeter.setAlarmThreshold1(MathUtil.setPrecision(devMap.getAlarmThreshold1()));
                electricMeter.setInstallAddr(deviceInfo.getInstallAddr());
                electricMeter.setRemark(deviceInfo.getRemark());
                // 是否开户
                Integer isAccount = devMap.getIsAccount();
                if (isAccount != 0) {
                    AcctDetailInfo acctDetailInfo = icCardService.queryDetail(customerNo,deviceInfo.getCno());
                    AccountInfo accountInfo = new AccountInfo();
                    BeanUtils.copyProperties(acctDetailInfo,accountInfo);
                    // 设置电表开户信息
                    electricMeter.setAccountInfo(accountInfo);
                }
                electricMeter.setIsAccount(isAccount);
                electricMeter.setMoteType(meterParam.getMoteType());
                electricMeter.setCjqNo(meterParam.getCommCollectionNo());
                electricMeter.setMeterType(deviceMeterConfig.getMeterType());
                electricMeter.setCollectDevType(meterParam.getCollectDevType());
                electricMeter.setMeterUserNo(devMap.getMeterUserNo());
                electricMeter.setTransformerNo(devMap.getTransformerNo());
                electricMeter.setBuildingNo(devMap.getBuildingNo());
                electricMeter.setSendFlag(meterParam.getSendFlag());
                DictItem dictItem = tempMap.get(meterParam.getDictItemValue());
                electricMeter.setDictItemName(dictItem.getDictItemName());
                electricMeter.setDictItemValue(meterParam.getDictItemValue());
                electricMeter.setIsImportant(meterParam.getIsImportant());
                electricMeter.setLocalControl(meterParam.getLocalControl());
                electricMeter.setRatio(meterParam.getRatio());
                electricMeter.setCommSetupSn(meterParam.getCommSetupSn());
                electricMeter.setCommPointCode(meterParam.getCommPointCode());
                // 设置波特率
                Integer rate = CustomerInfoConstant.CommBaudrateEnum.getRateByValue(deviceMeterConfig.getCommBaudrate());
                electricMeter.setCommBaudrate(rate);
                //设置电表类别
                electricMeter.setElecMeterCategory(devMap.getElecMeterCategory());
                // 查询字典项名称
                DictItem elecMeterCategoryName = dictItemService.findByCodeAndValue(DictCodeConstant.ELECMETERCATEGORY.getDictCode(), devMap.getElecMeterCategory());
                electricMeter.setElecMeterCategoryName(elecMeterCategoryName.getDictItemName());
                //设置用电类型
                electricMeter.setElecType(devMap.getElecType());
                // 查询字典项名称
                DictItem elecType = dictItemService.findByCodeAndValue(DictCodeConstant.ELECTYPE.getDictCode(), devMap.getElecType());
                electricMeter.setElecTypeName(elecType.getDictItemName());
                // 设置总表表号
                String parentDeviceNo = relationMap.get(cno);
                electricMeter.setParentDeviceNo(parentDeviceNo);


                List<ElectricMeterInfo> electricMeterList = customerDetailInfo.getElectricMeter();
                if (CollectionUtils.isEmpty(electricMeterList)) {
                    List<ElectricMeterInfo> temp = Lists.newArrayList();
                    temp.add(electricMeter);
                    customerDetailInfo.setElectricMeter(temp);
                } else {
                    electricMeterList.add(electricMeter);
                }
            } else if (DeviceType.WATER_METER.getCode().equals(deviceType)) {
                // 设置水表信息
                WaterMeterInfo waterMeter = new WaterMeterInfo();
                String cno = devMap.getCno();
                waterMeter.setCno(cno);
                DeviceInfo deviceInfo = deviceInfoMap.get(cno);
                waterMeter.setDeviceNo(deviceInfo.getDeviceNo());
                waterMeter.setOffScheme(deviceInfo.getOffScheme());
                waterMeter.setOffParam(deviceInfo.getOffParam());

                DeviceMeterParam meterParam = meterParamMap.get(cno);
                String jzqNo = CNoUtil.getJzqNoByJzqCno(meterParam.getJzqCno());
                waterMeter.setJzqNo(jzqNo);
                waterMeter.setCommPort(meterParam.getCommPort());

                DeviceMeterConfig deviceMeterConfig = meterConfigMap.get(meterParam.getParamFlag());
                waterMeter.setCommRule(deviceMeterConfig.getCommRule());
                waterMeter.setInitAmount(MathUtil.setPrecision(devMap.getInitAmount()));
                String meterFactory = deviceMeterConfig.getMeterFactory();
                waterMeter.setDeviceFactoryValue(meterFactory);
                DictItem item = dictItemMap.get(meterFactory);
                waterMeter.setDeviceFactoryName(item.getDictItemName());
                waterMeter.setCommFactorCnt(deviceMeterConfig.getCommFactorCnt());

                waterMeter.setParamFlag(deviceMeterConfig.getParamFlag());
                waterMeter.setMeterModeName(deviceMeterConfig.getMeterMode());
                waterMeter.setAlarmThreshold(MathUtil.setPrecision(devMap.getAlarmThreshold()));
                waterMeter.setAlarmThreshold1(MathUtil.setPrecision(devMap.getAlarmThreshold1()));
                waterMeter.setInstallAddr(deviceInfo.getInstallAddr());
                waterMeter.setRemark(deviceInfo.getRemark());
                Integer isAccount = devMap.getIsAccount();
                waterMeter.setIsAccount(isAccount);
                waterMeter.setMoteType(meterParam.getMoteType());
                waterMeter.setCjqNo(meterParam.getCommCollectionNo());
                waterMeter.setMeterType(deviceMeterConfig.getMeterType());
                waterMeter.setCollectDevType(meterParam.getCollectDevType());
                waterMeter.setMeterUserNo(devMap.getMeterUserNo());
                waterMeter.setTransformerNo(devMap.getTransformerNo());
                waterMeter.setBuildingNo(devMap.getBuildingNo());
                waterMeter.setSendFlag(meterParam.getSendFlag());
                DictItem dictItem = tempMap.get(meterParam.getDictItemValue());
                waterMeter.setDictItemName(dictItem.getDictItemName());
                waterMeter.setDictItemValue(meterParam.getDictItemValue());
                waterMeter.setIsImportant(meterParam.getIsImportant());
                //设置电表类别
                waterMeter.setElecMeterCategory(devMap.getElecMeterCategory());
                // 查询字典项名称
                DictItem elecMeterCategoryName = dictItemService.findByCodeAndValue(DictCodeConstant.ELECMETERCATEGORY.getDictCode(), devMap.getElecMeterCategory());
                waterMeter.setElecMeterCategoryName(elecMeterCategoryName.getDictItemName());
                //设置用电类型
                waterMeter.setElecType(devMap.getElecType());
                // 查询字典项名称
                DictItem elecType = dictItemService.findByCodeAndValue(DictCodeConstant.ELECTYPE.getDictCode(), devMap.getElecType());
                waterMeter.setElecTypeName(elecType.getDictItemName());
                // 设置总表表号
                String parentDeviceNo = relationMap.get(cno);
                waterMeter.setParentDeviceNo(parentDeviceNo);

                List<WaterMeterInfo> waterMeterList = customerDetailInfo.getWaterMeter();
                if (CollectionUtils.isEmpty(waterMeterList)) {
                    List<WaterMeterInfo> temp = Lists.newArrayList();
                    temp.add(waterMeter);
                    customerDetailInfo.setWaterMeter(temp);
                } else {
                    waterMeterList.add(waterMeter);
                }
            } else if (DeviceType.GAS_METER.getCode().equals(deviceType)) {
                // 设置气表信息
                GasMeterInfo gasMeter = new GasMeterInfo();
                String cno = devMap.getCno();
                gasMeter.setCno(cno);
                DeviceInfo deviceInfo = deviceInfoMap.get(cno);
                gasMeter.setDeviceNo(deviceInfo.getDeviceNo());
                gasMeter.setOffScheme(deviceInfo.getOffScheme());
                gasMeter.setOffParam(deviceInfo.getOffParam());

                DeviceMeterParam meterParam = meterParamMap.get(cno);
                String jzqNo = CNoUtil.getJzqNoByJzqCno(meterParam.getJzqCno());
                gasMeter.setJzqNo(jzqNo);
                gasMeter.setCommPort(meterParam.getCommPort());

                DeviceMeterConfig deviceMeterConfig = meterConfigMap.get(meterParam.getParamFlag());
                gasMeter.setCommRule(deviceMeterConfig.getCommRule());
                gasMeter.setInitAmount(MathUtil.setPrecision(devMap.getInitAmount()));
                String meterFactory = deviceMeterConfig.getMeterFactory();
                gasMeter.setDeviceFactoryValue(meterFactory);
                DictItem item = dictItemMap.get(meterFactory);
                gasMeter.setDeviceFactoryName(item.getDictItemName());
                gasMeter.setCommFactorCnt(deviceMeterConfig.getCommFactorCnt());

                gasMeter.setParamFlag(deviceMeterConfig.getParamFlag());
                gasMeter.setMeterModeName(deviceMeterConfig.getMeterMode());
                gasMeter.setAlarmThreshold(MathUtil.setPrecision(devMap.getAlarmThreshold()));
                gasMeter.setAlarmThreshold1(MathUtil.setPrecision(devMap.getAlarmThreshold1()));
                gasMeter.setInstallAddr(deviceInfo.getInstallAddr());
                gasMeter.setRemark(deviceInfo.getRemark());
                Integer isAccount = devMap.getIsAccount();
                gasMeter.setIsAccount(isAccount);
                gasMeter.setMoteType(meterParam.getMoteType());
                gasMeter.setCjqNo(meterParam.getCommCollectionNo());
                gasMeter.setMeterType(deviceMeterConfig.getMeterType());
                gasMeter.setCollectDevType(meterParam.getCollectDevType());
                gasMeter.setMeterUserNo(devMap.getMeterUserNo());
                gasMeter.setTransformerNo(devMap.getTransformerNo());
                gasMeter.setBuildingNo(devMap.getBuildingNo());
                gasMeter.setSendFlag(meterParam.getSendFlag());
                DictItem dictItem = tempMap.get(meterParam.getDictItemValue());
                gasMeter.setDictItemName(dictItem.getDictItemName());
                gasMeter.setDictItemValue(meterParam.getDictItemValue());
                gasMeter.setIsImportant(meterParam.getIsImportant());
                //设置电表类别
                gasMeter.setElecMeterCategory(devMap.getElecMeterCategory());
                // 查询字典项名称
                DictItem elecMeterCategoryName = dictItemService.findByCodeAndValue(DictCodeConstant.ELECMETERCATEGORY.getDictCode(), devMap.getElecMeterCategory());
                gasMeter.setElecMeterCategoryName(elecMeterCategoryName.getDictItemName());
                //设置用电类型
                gasMeter.setElecType(devMap.getElecType());
                // 查询字典项名称
                DictItem elecType = dictItemService.findByCodeAndValue(DictCodeConstant.ELECTYPE.getDictCode(), devMap.getElecType());
                gasMeter.setElecTypeName(elecType.getDictItemName());
                // 设置总表表号
                String parentDeviceNo = relationMap.get(cno);
                gasMeter.setParentDeviceNo(parentDeviceNo);

                List<GasMeterInfo> gasMeterList = customerDetailInfo.getGasMeter();
                if (CollectionUtils.isEmpty(gasMeterList)) {
                    List<GasMeterInfo> temp = Lists.newArrayList();
                    temp.add(gasMeter);
                    customerDetailInfo.setGasMeter(temp);
                } else {
                    gasMeterList.add(gasMeter);
                }
            }
        }

        customerDetailInfo.setCustomerInfo(detailInfo);
        return customerDetailInfo;
    }

    @Override
    public List<CustomerInfo> batchQueryByCustomerNos(Set<String> customerNos) {
        Condition condition = new Condition(CustomerInfo.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andIn("customerNo", customerNos);
        return customerInfoMapper.selectByCondition(condition);
    }

    @Override
    public void weChatUnBind(String cno) {
        CustomerDevMap devMap = customerDevMapService.queryByCno(cno);
        if (devMap == null) {
            throw new BusinessException("设备关联表记录为空");
        }

        // 删除绑定记录信息
        customerWxBindService.deleteByParam(devMap.getCustomerNo(),devMap.getMeterUserNo(),devMap.getDeviceType());
    }

    @Override
    public List<BuildInfoDto> queryBuildInfo(Set<Long> dataOrgSet) {
        List<Long> list = Lists.newArrayList(dataOrgSet);
        List<BuildInfoDto> buildInfoDtos = customerInfoMapper.queryBuildInfo(list);
        return buildInfoDtos;
    }

    @Override
    public List<MainSubDto> queryMainSubTree(Long orgNo, String deviceType) {
        List<MainSubDto> mainSubDtos = customerInfoMapper.queryMainSubTree(orgNo, deviceType);
        return mainSubDtos;
    }

    @Override
    public List<MainSubDto> fuzzyQueryTree(Long orgNo, String deviceType, String deviceNo) {
        List<MainSubDto> mainSubDtos = customerInfoMapper.fuzzyQueryTree(orgNo, deviceType, deviceNo);
        return mainSubDtos;
    }

    @Override
    public List<CustomerCacheVo> queryAll() {
        return customerInfoMapper.queryAll();
    }

    /**
     * 校验电表表号是否存在
     *
     * @param deviceNo
     */
    private void checkElectricDeviceNoExist(String deviceNo) {
        String electricDeviceCno = CNoUtil.CreateCNo(DeviceType.ELECTRIC_METER.getCode(), deviceNo);
        DeviceInfo deviceInfo = deviceInfoService.queryDeviceInfoByCno(electricDeviceCno);
        if (deviceInfo != null) {
            String message = DeviceType.ELECTRIC_METER.getMessage() + "表号[" + deviceNo + "]已存在";
            throw new BusinessException(message);
        }
    }

    /**
     * 校验水表表号是否存在
     *
     * @param waterType
     * @param deviceNo
     */
    private void checkWaterDeviceNoExist(String waterType, String deviceNo) {
        // 真实的水表表号
        String realDeviceNo = CNoUtil.createWaterDeviceNo(waterType, deviceNo);
        String waterDeviceCno = CNoUtil.CreateCNo(DeviceType.WATER_METER.getCode(), realDeviceNo);
        DeviceInfo deviceInfo = deviceInfoService.queryDeviceInfoByCno(waterDeviceCno);
        if (deviceInfo != null) {
            String message = DeviceType.WATER_METER.getMessage() + "表号[" + deviceNo + "]已存在";
            throw new BusinessException(message);
        }
    }

    /**
     * 校验气表表号是否存在
     *
     * @param gasType
     * @param deviceNo
     */
    private void checkGasDeviceNoExist(String gasType, String deviceNo) {
        // 真实的气表表号
        String realDeviceNo = CNoUtil.createGasDeviceNo(gasType, deviceNo);
        String gasDeviceCno = CNoUtil.CreateCNo(DeviceType.GAS_METER.getCode(), realDeviceNo);
        DeviceInfo deviceInfo = deviceInfoService.queryDeviceInfoByCno(gasDeviceCno);
        if (deviceInfo != null) {
            String message = DeviceType.GAS_METER.getMessage() + "表号[" + deviceNo + "]已存在";
            throw new BusinessException(message);
        }
    }

    /**
     * 校验设备是否存在
     * @param deviceType
     * @param deviceNo
     */
    private void checkDeviceNoExist(String deviceType, String deviceNo) {
        String cno = CNoUtil.CreateCNo(deviceType, deviceNo);
        CustomerDevMap devMap = customerDevMapService.queryByCno(cno);
        if (devMap != null) {
            String message = DeviceType.getMessageByCode(deviceType) + "表号[" + deviceNo + "]已存在";
            throw new BusinessException(message);
        }
    }

    /**
     * 校验表计户号是否存在
     * @param deviceType
     * @param meterUserNo
     */
    private void checkMeterUserNoExist(String deviceType, String meterUserNo) {
        CustomerDevMap devMap = customerDevMapService.queryByMeterUserNo(deviceType, meterUserNo);
        if (devMap != null) {
            String message = DeviceType.getMessageByCode(deviceType) + "表计户号[" + meterUserNo + "]已存在";
            throw new BusinessException(message);
        }
    }
}
