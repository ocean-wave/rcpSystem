package cn.com.cdboost.collect.controller;


import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.constant.DictCodeConstant;
import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.dto.response.ChangeMeterCustomerDetialInfo;
import cn.com.cdboost.collect.dto.response.OldReadValueDto;
import cn.com.cdboost.collect.enums.Action;
import cn.com.cdboost.collect.enums.Auth;
import cn.com.cdboost.collect.enums.DeviceType;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.exception.BusinessException;
import cn.com.cdboost.collect.model.*;
import cn.com.cdboost.collect.service.*;
import cn.com.cdboost.collect.util.CNoUtil;
import cn.com.cdboost.collect.util.DownLoadUtil;
import cn.com.cdboost.collect.util.MathUtil;
import cn.com.cdboost.collect.util.UuidUtil;
import cn.com.cdboost.collect.vo.PageResult;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author zc
 * @desc 换表Controller类
 * @create 2017-10-16 09:46
 **/
@Controller
@RequestMapping("/changeMeter")
public class ChangeMeterController {
    private static final Logger logger = LoggerFactory.getLogger(ChangeMeterController.class);

    @Autowired
    private ChangeMeterService changeMeterService;
    @Autowired
    private CustomerInfoService customerInfoService;
    @Autowired
    private UserLogService userLogService;
    @Autowired
    private InstructService instructService;
    @Autowired
    private DeviceMeterParamService deviceMeterParamService;
    @Autowired
    private DeviceMeterConfigService deviceMeterConfigService;
    @Autowired
    private DictItemService dictItemService;
    @Autowired
    private CostCalculateService costCalculateService;
    @Autowired
    private MeterInitPowerService meterInitPowerService;
    @Autowired
    private CustomerDevMapService customerDevMapService;
    @Autowired
    private GenerateFileService generateFileService;

    @SystemControllerLog(description = "设备更换时，查询旧表最后一次算费的示数")
    @RequestMapping(value = "/queryOldReadValue")
    @ResponseBody
    public String queryOldReadValue(@RequestParam String customerNo, @RequestParam String oldCno) {
        Result<OldReadValueDto> result = new Result();
        if (StringUtils.isEmpty(customerNo)) {
            result.error("customerNo不能为空");
            return JSON.toJSONString(result);
        }

        if (StringUtils.isEmpty(oldCno)) {
            result.error("oldCno不能为空");
            return JSON.toJSONString(result);
        }

        CostCalculate costCalculate = costCalculateService.queryLastRecord(customerNo, oldCno);
        OldReadValueDto dto = new OldReadValueDto();
        if (costCalculate == null) {
            // 查询em_d_meter_init_power表
            MeterInitPower power = meterInitPowerService.queryByParam(customerNo, oldCno);
            dto.setLastReadValue(power.getReadValue());
            dto.setLastReadValue1(power.getReadValue1());
            dto.setLastReadValue2(power.getReadValue2());
            dto.setLastReadValue3(power.getReadValue3());
            dto.setLastReadValue4(power.getReadValue4());
            dto.setLocalDataTime(power.getLocalDataTime());
        } else {
            dto.setLastReadValue(costCalculate.getReadValue());
            dto.setLastReadValue1(costCalculate.getReadValue1());
            dto.setLastReadValue2(costCalculate.getReadValue2());
            dto.setLastReadValue3(costCalculate.getReadValue3());
            dto.setLastReadValue4(costCalculate.getReadValue4());
            dto.setLocalDataTime(costCalculate.getLocalDataTime());
        }

        // 查询剩余金额
//        CustomerDevMap devMap = customerDevMapService.queryByCustomerNoAndCno(customerNo, oldCno);
       // BigDecimal remainAmount = devMap.getRemainAmount();
       /* if (remainAmount != null) {
            dto.setRemainAmount(MathUtil.setPrecision(remainAmount));
        } else {
            dto.setRemainAmount(MathUtil.setPrecision(BigDecimal.ZERO));
        }*/
        dto.setRemainAmount(BigDecimal.ZERO);

        result.setData(dto);
        return JSON.toJSONString(result);
    }

    // 电表更换操作
    @Auth(menuID=10002101L,actionID=22L)
    @SystemControllerLog(description = "电表更换操作")
    @RequestMapping(value = "/changeElectricMeter")
    @ResponseBody
    public String changeElectricMeter(HttpSession session,@Valid @RequestBody ChangeElectricMeterParam param) {
        Result<String> result = new Result<>("换表成功");

        // 校验旧表数据
        boolean flag = this.checkOldElectric(param.getOldElectricMeter(), result);
        if (!flag) {
            return JSON.toJSONString(result);
        }

        // 校验新表数据
        boolean flag2 = this.checkNewElectric(param.getNewElectricMeter(), result);
        if (!flag2) {
            return JSON.toJSONString(result);
        }

        LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);

        // 电表设备类型
        String deviceType = DeviceType.ELECTRIC_METER.getCode();
        ChangeMeterParamVo paramVo = new ChangeMeterParamVo();
        paramVo.setCustomerNo(param.getCustomerNo());

        // 设置旧表相关信息
        OldElectricMeterParam oldElectricMeter = param.getOldElectricMeter();
        String oldCno = CNoUtil.CreateCNo(deviceType, oldElectricMeter.getDeviceNo());
        paramVo.setOldCno(oldCno);
        paramVo.setReadValue(String.valueOf(oldElectricMeter.getReadValue()));
        BigDecimal readValue1 = oldElectricMeter.getReadValue1();
        if (readValue1 != null) {
            paramVo.setReadValue1(String.valueOf(readValue1));
        }

        BigDecimal readValue2 = oldElectricMeter.getReadValue2();
        if (readValue2 != null) {
            paramVo.setReadValue2(String.valueOf(readValue2));
        }

        BigDecimal readValue3 = oldElectricMeter.getReadValue3();
        if (readValue3 != null) {
            paramVo.setReadValue3(String.valueOf(readValue3));
        }

        BigDecimal readValue4 = oldElectricMeter.getReadValue4();
        if (readValue4 != null) {
            paramVo.setReadValue4(String.valueOf(readValue4));
        }
        paramVo.setOldRemainAmount(String.valueOf(oldElectricMeter.getRemainAmount()));

        DeviceMeterParam meterParam = deviceMeterParamService.queryEffectiveParamByCno(oldCno);
        NewElectricMeterParam newElectricMeter = param.getNewElectricMeter();
        String deviceFactoryValue = newElectricMeter.getDeviceFactoryValue();
        DictItem dictItem = dictItemService.findByCodeAndValue(DictCodeConstant.METER_FACTORY.getDictCode(), deviceFactoryValue);

        // 设置新表相关信息
        paramVo.setNewFactory(dictItem.getDictItemName());
        paramVo.setNewFactoryVal(deviceFactoryValue);
        String newCno = CNoUtil.CreateCNo(deviceType, newElectricMeter.getDeviceNo());
        paramVo.setNewCno(newCno);
        paramVo.setNewMeterNo(newElectricMeter.getDeviceNo());
        paramVo.setNewReadValue(String.valueOf(newElectricMeter.getReadValue()));

        BigDecimal newReadValue1 = newElectricMeter.getReadValue1();
        if (newReadValue1 != null) {
            paramVo.setNewReadValue1(String.valueOf(newReadValue1));
        }

        BigDecimal newReadValue2 = newElectricMeter.getReadValue2();
        if (newReadValue2 != null) {
            paramVo.setNewReadValue2(String.valueOf(newReadValue2));
        }

        BigDecimal newReadValue3 = newElectricMeter.getReadValue3();
        if (newReadValue3 != null) {
            paramVo.setNewReadValue3(String.valueOf(newReadValue3));
        }

        BigDecimal newReadValue4 = newElectricMeter.getReadValue4();
        if (newReadValue4 != null) {
            paramVo.setNewReadValue4(String.valueOf(newReadValue4));
        }
        paramVo.setNewRemainAmount(String.valueOf(newElectricMeter.getRemainAmount()));
        paramVo.setParamFlag(newElectricMeter.getParamFlag());
        // 生成新表设备编号
        String guid = UuidUtil.getUuid();
        int retVal = changeMeterService.updateChangeMeter(paramVo, user.getId(), param.getChangeRemark(),guid);
        // 换表成功，发送算费指令
        sendToInstruct(oldCno, meterParam, guid, retVal);
        this.setResult(retVal,result);
        if(retVal==1){
            userLogService.create(user.getId(), Action.CHANGE_METER.getActionId(),"用户档案","customerNo", param.getCustomerNo(), "(换表)从旧电表["+param.getOldElectricMeter().getDeviceNo()+ "]换到新电表["+param.getNewElectricMeter().getDeviceNo()+"]",JSON.toJSONString(param));
        }
        return JSON.toJSONString(result);
    }

    private boolean checkOldElectric(OldElectricMeterParam oldElectricMeter,Result result) {
        // 校验旧表总
        BigDecimal readValue = oldElectricMeter.getReadValue();
        if (readValue == null) {
            result.error("旧表总示数readValue1不能为空");
            return false;
        }

        boolean flag2 = MathUtil.isGreaterEqual(readValue, BigDecimal.ZERO);
        if (!flag2) {
            result.error("旧表总示数不能小于0");
            return false;
        }

        // 旧表总必须大于或等于最后一次算费时的总
        boolean greaterEqual = MathUtil.isGreaterEqual(readValue, oldElectricMeter.getLastReadValue());
        if (!greaterEqual) {
            result.error("旧表总示数不能小于最后一次算费时总示数");
            return false;
        }

        // 查询设备参数配置信息
        String paramFlag = oldElectricMeter.getParamFlag();
        DeviceMeterConfig meterConfig = deviceMeterConfigService.queryByParamFlag(paramFlag);
        Integer commFactorCnt = meterConfig.getCommFactorCnt();
        if (commFactorCnt > 1) {
            BigDecimal readValue1 = oldElectricMeter.getReadValue1();
            if (readValue1 == null) {
                result.error("旧表费率数大于1，尖示数readValue1不能为空");
                return false;
            }
            BigDecimal readValue2 = oldElectricMeter.getReadValue2();
            if (readValue2 == null) {
                result.error("旧表费率数大于1，峰示数readValue2不能为空");
                return false;
            }
            BigDecimal readValue3 = oldElectricMeter.getReadValue3();
            if (readValue3 == null) {
                result.error("旧表费率数大于1，平示数readValue2不能为空");
                return false;
            }
            BigDecimal readValue4 = oldElectricMeter.getReadValue4();
            if (readValue4 == null) {
                result.error("旧表费率数大于1，谷示数readValue2不能为空");
                return false;
            }

            // 总=尖+峰+平+谷
            BigDecimal oldSum = readValue1.add(readValue2).add(readValue3).add(readValue4);
            BigDecimal tempOld = new BigDecimal("0.02");
            BigDecimal mOld = readValue.subtract(tempOld);
            BigDecimal nOld = readValue.add(tempOld);
            boolean betweenOld = MathUtil.isBetween(oldSum, mOld, nOld);
            if (!betweenOld) {
                result.error("旧表表号[" + oldElectricMeter.getDeviceNo() + "],尖峰平谷之和跟总示数误差不在0.02之间");
                return false;
            }

            // 跟最后一次算费时记录校验
            boolean greaterEqual1 = MathUtil.isGreaterEqual(readValue1, oldElectricMeter.getLastReadValue1());
            if (!greaterEqual1) {
                result.error("旧表尖示数不能小于最后一次算费时尖示数");
                return false;
            }

            boolean greaterEqual2 = MathUtil.isGreaterEqual(readValue2, oldElectricMeter.getLastReadValue2());
            if (!greaterEqual2) {
                result.error("旧表峰示数不能小于最后一次算费时峰示数");
                return false;
            }

            boolean greaterEqual3 = MathUtil.isGreaterEqual(readValue3, oldElectricMeter.getLastReadValue3());
            if (!greaterEqual3) {
                result.error("旧表平示数不能小于最后一次算费时平示数");
                return false;
            }

            boolean greaterEqual4 = MathUtil.isGreaterEqual(readValue4, oldElectricMeter.getLastReadValue4());
            if (!greaterEqual4) {
                result.error("旧表谷示数不能小于最后一次算费时谷示数");
                return false;
            }
        }
        return true;
    }

    private boolean checkNewElectric(NewElectricMeterParam newElectricMeter,Result result) {
        // 校验新表总
        BigDecimal readValue = newElectricMeter.getReadValue();
        if (readValue == null) {
            result.error("新表总示数readValue1不能为空");
            return false;
        }

        boolean flag = MathUtil.isGreaterEqual(readValue, BigDecimal.ZERO);
        if (!flag) {
            result.error("新表总示数不能小于0");
            return false;
        }

        String paramFlag = newElectricMeter.getParamFlag();
        // 查询设备参数配置信息
        DeviceMeterConfig meterConfig = deviceMeterConfigService.queryByParamFlag(paramFlag);
        Integer commFactorCnt = meterConfig.getCommFactorCnt();
        if (commFactorCnt > 1) {
            BigDecimal readValue1 = newElectricMeter.getReadValue1();
            if (readValue1 == null) {
                result.error("新表费率数大于1，尖示数readValue1不能为空");
                return false;
            }
            BigDecimal readValue2 = newElectricMeter.getReadValue2();
            if (readValue2 == null) {
                result.error("新表费率数大于1，峰示数readValue2不能为空");
                return false;
            }
            BigDecimal readValue3 = newElectricMeter.getReadValue3();
            if (readValue3 == null) {
                result.error("新表费率数大于1，平示数readValue2不能为空");
                return false;
            }
            BigDecimal readValue4 = newElectricMeter.getReadValue4();
            if (readValue4 == null) {
                result.error("新表费率数大于1，谷示数readValue2不能为空");
                return false;
            }

            // 总=尖+峰+平+谷
            BigDecimal sum = readValue1.add(readValue2).add(readValue3).add(readValue4);
            BigDecimal temp = new BigDecimal("0.02");
            BigDecimal m = readValue.subtract(temp);
            BigDecimal n = readValue.add(temp);
            boolean between = MathUtil.isBetween(sum, m, n);
            if (!between) {
                result.error("新表表号[" + newElectricMeter.getDeviceNo() + "],尖峰平谷之和跟总示数误差不在0.02之间");
                return false;
            }
        }
        return true;
    }

    // 水表更换操作
    @Auth(menuID=10002101L,actionID=22L)
    @SystemControllerLog(description = "水表更换操作")
    @RequestMapping(value = "/changeWaterMeter")
    @ResponseBody
    public String changeWaterMeter(HttpSession session,@Valid @RequestBody ChangeWaterMeterParam param) {
        Result<String> result = new Result<>("换表成功");

        // 校验水表换表
        boolean flag = this.checkWater(param, result);
        if (!flag) {
            return JSON.toJSONString(result);
        }

        LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);


        // 水表设备类型
        String deviceType = DeviceType.WATER_METER.getCode();
        ChangeMeterParamVo paramVo = new ChangeMeterParamVo();
        paramVo.setCustomerNo(param.getCustomerNo());

        // 设置旧表相关信息
        OldWaterMeterParam oldWaterMeter = param.getOldWaterMeter();
        String oldCno = CNoUtil.CreateCNo(deviceType, oldWaterMeter.getDeviceNo());
        paramVo.setOldCno(oldCno);
        paramVo.setReadValue(String.valueOf(oldWaterMeter.getReadValue()));
        paramVo.setOldRemainAmount(String.valueOf(oldWaterMeter.getRemainAmount()));
        DeviceMeterParam meterParam = deviceMeterParamService.queryEffectiveParamByCno(oldCno);

        // 设置新表相关信息
        NewWaterMeterParam newWaterMeter = param.getNewWaterMeter();
        String waterDeviceNo = CNoUtil.createWaterDeviceNo(newWaterMeter.getWaterType(), newWaterMeter.getDeviceNo());
        String newCno = CNoUtil.CreateCNo(deviceType, waterDeviceNo);
        // 查询表计厂家
        String deviceFactoryValue = newWaterMeter.getDeviceFactoryValue();
        DictItem dictItem = dictItemService.findByCodeAndValue(DictCodeConstant.METER_FACTORY.getDictCode(), deviceFactoryValue);
        paramVo.setNewFactory(dictItem.getDictItemName());
        paramVo.setNewFactoryVal(deviceFactoryValue);
        paramVo.setNewCno(newCno);
        paramVo.setNewMeterNo(waterDeviceNo);
        paramVo.setNewReadValue(String.valueOf(newWaterMeter.getReadValue()));
        paramVo.setNewRemainAmount(String.valueOf(newWaterMeter.getRemainAmount()));
        paramVo.setParamFlag(newWaterMeter.getParamFlag());
        // 生成新表设备编号
        String guid = UuidUtil.getUuid();
        int retVal = changeMeterService.updateChangeMeter(paramVo,user.getId(),param.getChangeRemark(),guid);
        // 换表成功，发送算费指令
        sendToInstruct(oldCno, meterParam, guid, retVal);
        this.setResult(retVal,result);
        if(retVal==1){
            userLogService.create(user.getId(), Action.CHANGE_METER.getActionId(),"用户档案","customerNo", param.getCustomerNo(),"(换表)从旧水表["+param.getOldWaterMeter().getDeviceNo()+"]换到新水表["+param.getNewWaterMeter().getDeviceNo()+"]", JSON.toJSONString(param));
        }
        return JSON.toJSONString(result);
    }

    private boolean checkWater(ChangeWaterMeterParam param, Result result) {
        NewWaterMeterParam newWaterMeter = param.getNewWaterMeter();
        BigDecimal readValue = newWaterMeter.getReadValue();
        boolean flag = MathUtil.isGreaterEqual(readValue, BigDecimal.ZERO);
        if (!flag) {
            result.error("新表总不能小于0");
            return false;
        }
        OldWaterMeterParam oldWaterMeter = param.getOldWaterMeter();
        BigDecimal oldReadValue = oldWaterMeter.getReadValue();
        boolean flag2 = MathUtil.isGreaterEqual(oldReadValue, BigDecimal.ZERO);
        if (!flag2) {
            result.error("旧表总不能小于0");
            return false;
        }
        BigDecimal lastReadValue = oldWaterMeter.getLastReadValue();
        boolean greaterEqual = MathUtil.isGreaterEqual(oldReadValue, lastReadValue);
        if (!greaterEqual) {
            result.error("旧表总不能小于最后一次算费总示数");
            return false;
        }
        return true;
    }

    // 气表更换操作
    @Auth(menuID=10002101L,actionID=22L)
    @SystemControllerLog(description = "气表更换操作")
    @RequestMapping(value = "/changeGasMeter")
    @ResponseBody
    public String changeGasMeter(HttpSession session,@Valid @RequestBody ChangeGasMeterParam param) {
        Result<String> result = new Result<>("换表成功");

        // 校验
        boolean flag = this.checkGas(param, result);
        if (!flag) {
            return JSON.toJSONString(result);
        }

        LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);


        // 气表设备类型
        String deviceType = DeviceType.GAS_METER.getCode();
        ChangeMeterParamVo paramVo = new ChangeMeterParamVo();
        paramVo.setCustomerNo(param.getCustomerNo());

        // 设置旧表相关信息
        OldGasMeterParam oldGasMeter = param.getOldGasMeter();
        String oldCno = CNoUtil.CreateCNo(deviceType, oldGasMeter.getDeviceNo());
        paramVo.setOldCno(oldCno);
        paramVo.setReadValue(String.valueOf(oldGasMeter.getReadValue()));
        paramVo.setOldRemainAmount(String.valueOf(oldGasMeter.getRemainAmount()));
        DeviceMeterParam meterParam = deviceMeterParamService.queryEffectiveParamByCno(oldCno);
        // 设置新表相关信息
        NewGasMeterParam newGasMeter = param.getNewGasMeter();
        String gasDeviceNo = CNoUtil.createGasDeviceNo(newGasMeter.getGasType(), newGasMeter.getDeviceNo());
        String newCno = CNoUtil.CreateCNo(deviceType, gasDeviceNo);
        // 查询表计厂家
        String deviceFactoryValue = newGasMeter.getDeviceFactoryValue();
        DictItem dictItem = dictItemService.findByCodeAndValue(DictCodeConstant.METER_FACTORY.getDictCode(), deviceFactoryValue);
        paramVo.setNewFactory(dictItem.getDictItemName());
        paramVo.setNewFactoryVal(deviceFactoryValue);
        paramVo.setNewCno(newCno);
        paramVo.setNewMeterNo(gasDeviceNo);
        paramVo.setNewReadValue(String.valueOf(newGasMeter.getReadValue()));
        paramVo.setNewRemainAmount(String.valueOf(newGasMeter.getRemainAmount()));
        paramVo.setParamFlag(newGasMeter.getParamFlag());
        // 生成新表设备编号
        String guid = UuidUtil.getUuid();
        int retVal = changeMeterService.updateChangeMeter(paramVo,user.getId(),param.getChangeRemark(),guid);
        // 换表成功，发送算费指令
        sendToInstruct(oldCno, meterParam, guid, retVal);
        this.setResult(retVal,result);
        if(retVal==1){
            userLogService.create(user.getId(), Action.CHANGE_METER.getActionId(),"用户档案","customerNo", param.getCustomerNo(),"(换表)从旧气表["+param.getOldGasMeter().getDeviceNo()+"]换到新气表["+param.getNewGasMeter().getDeviceNo()+"]", JSON.toJSONString(param));
        }
        return JSON.toJSONString(result);
    }

    private boolean checkGas(ChangeGasMeterParam param, Result result) {
        NewGasMeterParam newGasMeter = param.getNewGasMeter();
        BigDecimal readValue = newGasMeter.getReadValue();
        boolean flag = MathUtil.isGreaterEqual(readValue, BigDecimal.ZERO);
        if (!flag) {
            result.error("新表总不能小于0");
            return false;
        }
        OldGasMeterParam oldGasMeter = param.getOldGasMeter();
        BigDecimal oldReadValue = oldGasMeter.getReadValue();
        boolean flag2 = MathUtil.isGreaterEqual(oldReadValue, BigDecimal.ZERO);
        if (!flag2) {
            result.error("旧表总不能小于0");
            return false;
        }
        BigDecimal lastReadValue = oldGasMeter.getLastReadValue();
        boolean greaterEqual = MathUtil.isGreaterEqual(oldReadValue, lastReadValue);
        if (!greaterEqual) {
            result.error("旧表总不能小于最后一次算费总示数");
            return false;
        }
        return true;
    }

    private void sendToInstruct(String oldCno, DeviceMeterParam meterParam, String guid, int retVal) {
        if (retVal == 1) {
            CancelOffParam offParam = new CancelOffParam();
            String jzqCno = meterParam.getJzqCno();
            String jzqNo = CNoUtil.getJzqNoByJzqCno(jzqCno);
            offParam.setJzqNo(jzqNo);
            offParam.setCno(oldCno);
            offParam.setCustomerNo(meterParam.getCustomerNo());
            offParam.setGuid(guid);
            int i = instructService.Instantcalculate(offParam);
            if(i!=1){
                // 表示调用中间件失败
                String message = "中间件返回状态 ："+i+" , 调用中间件失败!";
                logger.info(message);
                throw new BusinessException(message);
            }

        }
    }

    // 换表记录查询
    @SystemControllerLog(description = "换表记录查询")
    @RequestMapping(value = "/queryChangeMeters")
    @ResponseBody
    public String queryChangeMeters(HttpSession session, @Valid @RequestBody ChangeMeterListQueryParam queryParam) {
        PageResult<List<ChangeMeterInfo>> result = new PageResult<>();
        LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);

        ChangeMeterListQueryVo queryVo = new ChangeMeterListQueryVo();
        BeanUtils.copyProperties(queryParam,queryVo);
        queryVo.setUserId(String.valueOf(user.getId()));

        List<ChangeMeterInfo> list = changeMeterService.queryChangeMeters(queryVo);
        result.setData(list);
        result.setTotal(queryVo.getTotal());
        return JSON.toJSONString(result);
    }
    // 换表记录查询
    @SystemControllerLog(description = "换表记录查询下载")
    @RequestMapping(value = "/queryChangeMetersDownload")
    public void queryChangeMetersDownload(HttpServletResponse response, HttpSession session,  ChangeMeterListQueryParam queryParam) throws IOException {
        LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        ChangeMeterListQueryVo queryVo = new ChangeMeterListQueryVo();
        BeanUtils.copyProperties(queryParam,queryVo);
        queryVo.setUserId(String.valueOf(user.getId()));
        List<ChangeMeterInfo> list = changeMeterService.queryChangeMeters(queryVo);
        XSSFWorkbook xssfWorkbook=generateFileService.queryChangeMetersDownload("换表记录查询列表",list);
        DownLoadUtil.downExcel(response,xssfWorkbook);

    }
    // 换表详细查询
    @SystemControllerLog(description = "换表详细查询")
    @RequestMapping(value = "/queryChangeMeterDetail")
    @ResponseBody
    public String queryChangeMeterDetail(@RequestParam String customerNo,
                                         @RequestParam String newCno) {
        Result<ChangeMeterCustomerDetialInfo> result = new Result<>();
        if (StringUtils.isEmpty(customerNo)) {
            result.error("customerNo不能为空");
            return JSON.toJSONString(result);
        }

        if (StringUtils.isEmpty(newCno)) {
            result.error("newCno不能为空");
            return JSON.toJSONString(result);
        }

        //用户档案详细信息
        CustomerInfo4ChangeMeterDetail customerInfo = customerInfoService.queryDetail(customerNo, newCno);

        String deviceType = newCno.substring(0, 2);
        ChangeMeterDetailInfo changeMeterDetailInfo = changeMeterService.queryChangeMeterDetail(newCno,customerNo,deviceType);
        ChangeMeterCustomerDetialInfo detailInfo = new ChangeMeterCustomerDetialInfo();
        detailInfo.setCustomerInfo(customerInfo);
        detailInfo.setChangeMeterDetailInfo(changeMeterDetailInfo);
        result.setData(detailInfo);

        return JSON.toJSONString(result);
    }

    private void setResult(int retVal,Result result) {
        // 换表成功，发送算费指令
        if (retVal == 0) {
            logger.info("未知异常，存储过程返回0");
            result.error("系统异常");
        } else if (retVal == 2) {
            logger.info("已经更换");
            result.error("已经更换");
        } else if (retVal == 3) {
            logger.info("解析json数据失败");
            result.error("系统异常");
        } else if (retVal == 4) {
            logger.info("新表在系统中存在");
            result.error("新表在系统中存在");
        }
    }

}
