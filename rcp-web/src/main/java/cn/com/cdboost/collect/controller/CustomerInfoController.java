package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.dto.CustomerBatchImportInfo;
import cn.com.cdboost.collect.dto.CustomerInfoDto;
import cn.com.cdboost.collect.dto.CustomerInfoDtodownload;
import cn.com.cdboost.collect.dto.LoginUser;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.dto.response.*;
import cn.com.cdboost.collect.enums.*;
import cn.com.cdboost.collect.filter.MyFilenameFilter;
import cn.com.cdboost.collect.model.CustomerDevMap;
import cn.com.cdboost.collect.model.CustomerInfo;
import cn.com.cdboost.collect.model.Impot;
import cn.com.cdboost.collect.service.*;
import cn.com.cdboost.collect.util.CNoUtil;
import cn.com.cdboost.collect.util.UuidUtil;
import cn.com.cdboost.collect.vo.PageResult;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.*;

/**
 * 客户档案相关
 * @author boost
 */
@Controller
@RequestMapping("/customerInfo")
public class CustomerInfoController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerInfoController.class);

    @Autowired
    private CustomerInfoService customerInfoService;
    @Autowired
    private DeviceMeterParamService deviceMeterParamService;
    @Autowired
    private GenerateFileService generateFileService;
    @Autowired
    private UserLogService userLogService;
    @Autowired
    private CustomerDevMapService customerDevMapService;
    @Autowired
    private InstructService instructService;
    @Autowired
    private CustomerWxBindService customerWxBindService;
    @Autowired
    private CustomerPhoneBindService customerPhoneBindService;

    /**
     * 客户档案列表分页查询
     * @param session
     * @param queryParam
     * @return
     */
    @Auth(menuID=10002101L,actionID={1L,2L,3L,4L})
    @SystemControllerLog(description = "客户档案列表分页查询")
    @RequestMapping(value = "/queryList")
    @ResponseBody
    public String queryListOld(HttpSession session, @Valid @RequestBody CustomerInfoQueryParam queryParam) {
        CustomerInfoQueryVo queryVo = new CustomerInfoQueryVo();
        BeanUtils.copyProperties(queryParam,queryVo);
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        long userId = currentUser.getId();
        queryVo.setUserId(userId);

        List<CustomerInfoListInfo> dataList = Lists.newArrayList();
        List<CustomerInfoDto> customerInfoDtos = customerInfoService.queryList(queryVo);
        if (!CollectionUtils.isEmpty(customerInfoDtos)) {
            List<String> customerNoList = Lists.newArrayList();
            for (CustomerInfoDto dto : customerInfoDtos) {
                customerNoList.add(dto.getCustomerNo());

                CustomerInfoListInfo info = new CustomerInfoListInfo();
                BeanUtils.copyProperties(dto,info);
                dataList.add(info);
            }

            // 批量查询所有客户的所有表的cno
            List<CustomerDevMap> customerDevMaps = customerDevMapService.batchQueryByCustomerNos(customerNoList);
            ImmutableListMultimap<String, CustomerDevMap> multimap = Multimaps.index(customerDevMaps, new Function<CustomerDevMap, String>() {
                @Nullable
                @Override
                public String apply(@Nullable CustomerDevMap devMap) {
                    return devMap.getCustomerNo();
                }
            });

            for (CustomerInfoListInfo info : dataList) {
                String customerNo = info.getCustomerNo();
                ImmutableList<CustomerDevMap> list = multimap.get(customerNo);
                List<String> cnos = Lists.newArrayList();
                for (CustomerDevMap devMap : list) {
                    cnos.add(devMap.getCno());
                }

             //   info.setCnoList(cnos);
            }
        }

        PageResult<List<CustomerInfoListInfo>> result = new PageResult<>();
        result.setData(dataList);
        result.setTotal(queryVo.getTotal());
        return JSON.toJSONString(result);
    }
    /**
     * 客户档案列表分页查询
     * @param session
     * @param queryParam
     * @return
     */
    @Auth(menuID=10002101L,actionID={1L,2L,3L,4L})
    @SystemControllerLog(description = "客户档案列表分页查询")
    @RequestMapping(value = "/queryListNew")
    @ResponseBody
    public String queryListNew(HttpSession session, @Valid @RequestBody CustomerInfoQueryNewParam queryParam) {
        CustomerInfoQueryNewVo queryVo = new CustomerInfoQueryNewVo();
        BeanUtils.copyProperties(queryParam,queryVo);
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        long userId = currentUser.getId();
        queryVo.setUserId(userId);
        String s = generateInfo(session, queryParam);
        queryVo.setImportGuid(s);
        List<CustomerInfoListInfo> dataList = Lists.newArrayList();
        List<CustomerInfoDto> customerInfoDtos = customerInfoService.queryListNew(queryVo);
        if (!CollectionUtils.isEmpty(customerInfoDtos)) {
            List<String> customerNoList = Lists.newArrayList();
            for (CustomerInfoDto dto : customerInfoDtos) {
                customerNoList.add(dto.getCustomerNo());

                CustomerInfoListInfo info = new CustomerInfoListInfo();
                BeanUtils.copyProperties(dto,info);
                dataList.add(info);
            }

            // 批量查询所有客户的所有表的cno
            List<CustomerDevMap> customerDevMaps = customerDevMapService.batchQueryByCustomerNos(customerNoList);
            ImmutableListMultimap<String, CustomerDevMap> multimap = Multimaps.index(customerDevMaps, new Function<CustomerDevMap, String>() {
                @Nullable
                @Override
                public String apply(@Nullable CustomerDevMap devMap) {
                    return devMap.getCustomerNo();
                }
            });

            for (CustomerInfoListInfo info : dataList) {
                String customerNo = info.getCustomerNo();
                ImmutableList<CustomerDevMap> list = multimap.get(customerNo);
                StringBuilder electricDeviceNo = new StringBuilder();
                StringBuilder waterDeviceNo= new StringBuilder();
                StringBuilder gasDeviceNo= new StringBuilder();
                List<String> cnoList = Lists.newArrayList();
                for (CustomerDevMap devMap : list) {
                    cnoList.add(devMap.getCno());
                    if(devMap.getDeviceType().equals(DeviceType.ELECTRIC_METER.getCode())){
                        electricDeviceNo.append(devMap.getDeviceNo()).append(",");
                    }
                    if(devMap.getDeviceType().equals(DeviceType.WATER_METER.getCode())){
                        waterDeviceNo.append(devMap.getDeviceNo()).append(",");
                    }
                    if(devMap.getDeviceType().equals(DeviceType.GAS_METER.getCode())){
                        gasDeviceNo.append(devMap.getDeviceNo()).append(",");
                    }
                }
                if(!StringUtils.isEmpty(electricDeviceNo.toString())){
                    electricDeviceNo.deleteCharAt(electricDeviceNo.length()-1);
                }
                if(!StringUtils.isEmpty(waterDeviceNo.toString())){
                    waterDeviceNo.deleteCharAt(waterDeviceNo.length()-1);
                }
                if(!StringUtils.isEmpty(gasDeviceNo.toString())){
                    gasDeviceNo.deleteCharAt(gasDeviceNo.length()-1);
                }
                DeviceNoParam deviceNoParam=new DeviceNoParam();
                deviceNoParam.setElectricDeviceNo(electricDeviceNo.toString());
                deviceNoParam.setGasDeviceNo(gasDeviceNo.toString());
                deviceNoParam.setWaterDeviceNo(waterDeviceNo.toString());
                info.setDeviceNoParam(deviceNoParam);
                info.setCnoList(cnoList);
            }
        }

        PageResult<List<CustomerInfoListInfo>> result = new PageResult<>();
        result.setData(dataList);
        result.setTotal(queryVo.getTotal());
        return JSON.toJSONString(result);
    }
    /**
     * 客户档案添加
     * @param session
     * @param param
     * @return
     */
    @Auth(menuID=10002101L,actionID=1L)
    @SystemControllerLog(description = "客户档案添加")
    @RequestMapping(value = "/add")
    @ResponseBody
    public String add(HttpSession session, @Valid @RequestBody CustomerInfoCreateParam param) {
        Result<String> result = new Result();
        LoginUser currentUser = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);


        // 添加客户档案和对应的设备信息
        String customerNo = customerInfoService.addCustomerInfoAndDeviceInfo(param,currentUser.getId());

        // 同步设备档案
        this.syncDeviceInfo(param);

        result.setMessage("添加成功");
        result.setData(customerNo);
        userLogService.create(currentUser.getId(), Action.ADD.getActionId(), "客户档案", "CustomerName", param.getCustomerInfo().getCustomerName(),"新增["+param.getCustomerInfo().getCustomerName()+"]客户档案",JSON.toJSONString(param));
        return JSON.toJSONString(result);
    }

    /**
     *
     * @param session 用户ID
     * @param customerInfo 用户信息
     * @return
     */
    @Auth(menuID = 10002101L,actionID = 2L)
    @SystemControllerLog(description = "添加用户信息电话")
    @RequestMapping(value = "/updatePhoneBind")
    @ResponseBody
    public String updatePhoneBind(HttpSession session,@RequestBody CusPhoneBindBatch customerInfo){
        LoginUser currentUser = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        Result result = new Result();
        String customerNo = customerInfo.getCustomerNo();
        LinkedList<CustomerPhoneArray> getMobilPhones = customerInfo.getMobilePhones();
        Boolean flag;
        if(null == customerInfo.getCustomerNo())
        {
            logger.error("客户信息异常：客户添加微信关联电话号码异常_前台参数异常传送");
            result.setMessage("Update PhoneBind failed");
        }
        else
        {
            flag = customerPhoneBindService.addCustomerPhoneBind( getMobilPhones, currentUser.getId(),customerNo);
            if (flag)
            {
                result.setMessage("Update PhoneBind success");
                LoginUser user= (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
                if(!StringUtils.isEmpty(customerInfo.getCustomerNo())&&customerInfo.getMobilePhones().size()>0){
                    CustomerInfo customerInfo1 = customerInfoService.queryByCustomerNo(customerInfo.getCustomerNo());
                    LinkedList<CustomerPhoneArray> linkedList=customerInfo.getMobilePhones();
                    ImmutableListMultimap<String,CustomerPhoneArray> multimap=Multimaps.index(linkedList, new Function<CustomerPhoneArray, String>() {
                        @Nullable
                        @Override
                        public String apply(@Nullable CustomerPhoneArray input) {
                            return input.getOptType();
                        }
                    });
                        if(multimap.get("add").size()>0){
                            ImmutableList<CustomerPhoneArray> immutableList=multimap.get("add");
                            userLogService.create(user.getId(), Action.ON_OFF.getActionId(),"客户档案","list", "", "客户["+customerInfo1.getCustomerName()+"]新增电话"+immutableList.toString() , JSON.toJSONString(customerInfo));
                        } if(multimap.get("delete").size()>0){
                            ImmutableList<CustomerPhoneArray> immutableList=multimap.get("delete");
                            userLogService.create(user.getId(), Action.ON_OFF.getActionId(),"客户档案","list", "", "客户["+customerInfo1.getCustomerName()+"]删除电话"+immutableList.toString() , JSON.toJSONString(customerInfo));
                        }

                }
            }
            else
            {
                logger.error("客户信息异常：客户添加微信关联电话号码异常_数据库插入异常");
                result.setMessage("Update PhoneBind failed");
            }

        }

        return JSON.toJSONString(result);
    }

    /**
     *
     * @param customerNo 电话参数
     * @param cno 用户身份
     * @return
     */
    @SystemControllerLog(description = "查询微信信息")
    @RequestMapping(value = "/selWXBind")
    @ResponseBody
    public String selwxBind(@RequestParam String customerNo,@RequestParam String cno){
        Result<ArrayList<SelectWXData>> result = new Result();
        if (StringUtils.isEmpty(customerNo)) {
            result.error("customerNo不能为空");
            return JSON.toJSONString(result);
        }

        ArrayList<SelectWXData> customerWxBind = customerWxBindService.selCustomerWXBind(customerNo,cno);
        result.setData(customerWxBind);
        return JSON.toJSONString(result);
    }

    /**
     * 客户档案批量删除
     * @param session
     * @param customerNos
     * @return
     */
    @Auth(menuID=10002101L,actionID=3L)
    @SystemControllerLog(description = "客户批量删除")
    @RequestMapping(value = "/batchDelete")
    @ResponseBody
    public String batchDelete(HttpSession session, @RequestBody List<String> customerNos) {
        Result result = new Result();
        LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        Set set=Sets.newHashSet();
        set.addAll(customerNos);
        List<CustomerInfo> list = customerInfoService.batchQueryByCustomerNos(set);
        List list1= Lists.newArrayList();
        for (CustomerInfo customerInfo : list) {
            list1.add(customerInfo.getCustomerName());
        }

        if (CollectionUtils.isEmpty(customerNos)) {
            result.error("参数列表为空");
            return JSON.toJSONString(result);
        }

        // 批量删除
        Set<String> customerNoSet = Sets.newHashSet(customerNos);
        customerInfoService.batchDeleteCustomerInfo(customerNoSet);

        result.setMessage("删除成功");
        userLogService.create(user.getId(), Action.DELETE.getActionId(),"客户档案","customerNos", "","删除客户"+ list1.toString()+"", JSON.toJSONString(customerNos));
        return JSON.toJSONString(result);
    }

    // 下发客户档案
    @Auth(menuID=10002101L,actionID=8L)
    @SystemControllerLog(description = "下发客户档案")
    @RequestMapping("/syncMeterInfo")
    @ResponseBody
    public String syncMeterInfo(HttpSession session, @Valid @RequestBody SyncMeterParam param) {
        Result result = new Result();
        LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);


        List<String> cnoList = param.getCnos();
        if (CollectionUtils.isEmpty(cnoList)) {
            result.error("cnos不能为空");
            return JSON.toJSONString(result);
        }

        // 下发客户设备信息
        Integer userId = user.getId();
        int retVal = instructService.syncCustomerDevices(param.getGuid(),Long.valueOf(userId), cnoList);
        if (retVal != 1) {
            result.error("下发失败");
        }
        if(retVal==1){
            if(param.getCnos().size()==1){
                userLogService.create(user.getId(), Action.DOWN_CUSTOMER_RECORD.getActionId(),"客户档案","guid", param.getGuid(),"下发设备["+ CNoUtil.getDeviceNoByCno(param.getCnos().get(0))+"]档案",  JSON.toJSONString(param));
            }else{
                userLogService.create(user.getId(), Action.DOWN_CUSTOMER_RECORD.getActionId(),"客户档案","guid", param.getGuid(),"批量下发档案",  JSON.toJSONString(param));
            }
        }
        return JSON.toJSONString(result);
    }

    // 读取下发档案状态
    @SystemControllerLog(description = "读取下发档案状态")
    @RequestMapping("/readSyncState")
    @ResponseBody
    public String readSyncState(@RequestParam String guid) {
        Result result = new Result();
        if (StringUtils.isEmpty(guid)) {
            result.error("guid不能为空");
            return JSON.toJSONString(result);
        }

        int status = deviceMeterParamService.readSyncState(guid);
        result.setData(status);
        // status=101,前端继续轮询
        if (status != 101) {
            String message = customerInfoService.countByGuidAndSendFlag(guid);
            result.setMessage(message);
        }
        return JSON.toJSONString(result);
    }

    // 停止下发客户档案
    @SystemControllerLog(description = "停止下发客户档案")
    @RequestMapping(value = "cancelSyncMeterInfo", method = RequestMethod.POST)
    @ResponseBody
    public String cancelSyncMeterInfo(HttpSession session, @RequestParam String guid) {
        Result result = new Result();
        if (StringUtils.isEmpty(guid)) {
            result.error("guid不能为空");
            return JSON.toJSONString(result);
        }

        LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        /*userLogService.create(user.getId(), Action.DOWN_CUSTOMER_RECORD.getActionId(),"客户档案","guid",guid, "取消同步"+guid+"客户档案", JSON.toJSONString(guid));*/
        // 取消同步客户档案
        boolean flag = deviceMeterParamService.cancelSyncMeter(guid);
        if(!flag){
            result.error(ResultCode.Fail.getDesc());
        }
        return JSON.toJSONString(result);
    }

    /**
     * 客户档案修改
     * @param session
     * @param param
     * @return
     */
    @Auth(menuID=10002101L,actionID=2L)
    @SystemControllerLog(description = "客户档案修改")
    @RequestMapping(value = "/edit")
    @ResponseBody
    public String edit(HttpSession session, @Valid @RequestBody CustomerInfoEditParam param) {
        LoginUser currentUser = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        userLogService.create(currentUser.getId(), Action.MODIFY.getActionId(), "客户档案", "id", String.valueOf(param.getId()),"修改客户["+param.getCustomerName()+"]信息", JSON.toJSONString(param));

        CustomerInfoEditVo editVo = new CustomerInfoEditVo();
        BeanUtils.copyProperties(param,editVo);
        customerInfoService.updateCustomerInfo(editVo);

        Result result = new Result();
        result.setMessage("修改成功");
        return JSON.toJSONString(result);
    }

    /**
     * 客户档案点击查看详细信息
     * @param customerNo
     * @return
     */
    @SystemControllerLog(description = "客户档案点击查看详细信息")
    @RequestMapping(value = "/queryDetails")
    @ResponseBody
    public String queryDetails(@RequestParam String customerNo) {
        Result<CustomerDetailInfo> result = new Result<>();
        if (StringUtils.isEmpty(customerNo)) {
            result.error("customerNo不能为空");
            return JSON.toJSONString(result);
        }

        CustomerDetailInfo detailInfo = customerInfoService.queryDetails(customerNo);
        result.setData(detailInfo);
        return JSON.toJSONString(result);
    }

    // 用户档案明细界面，修改电表设备信息
    @Auth(menuID=10002101L,actionID=2L)
    @SystemControllerLog(description = "用户档案明细界面，修改电表设备信息")
    @RequestMapping(value = "/editElectricDevice")
    @ResponseBody
    public String editElectricDevice(HttpSession session, @Valid @RequestBody ElectricMeterEditParam param) {
        LoginUser currentUser = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        userLogService.create(currentUser.getId(), Action.ADD.getActionId(),"客户档案", "customerNo" , param.getCustomerNo(),"修改电表["+param.getDeviceNo()+"]信息", JSON.toJSONString(param));

        // 修改设备
        ElectricMeterAddParam addParam = new ElectricMeterAddParam();
        BeanUtils.copyProperties(param,addParam);
        customerInfoService.editCustomerElectricMeter(addParam,Long.valueOf(currentUser.getId()),param.getOrgNo(),param.getCustomerNo());

        // 同步设备档案
        String cno = CNoUtil.CreateCNo(DeviceType.ELECTRIC_METER.getCode(),param.getDeviceNo());
        this.syncSingleDevice(cno);

        Result result = new Result();
        result.setMessage("修改成功");
        return JSON.toJSONString(result);
    }

    // 用户档案明细界面，修改水表设备信息
    @Auth(menuID=10002101L,actionID=2L)
    @SystemControllerLog(description = "用户档案明细界面，修改水表设备信息")
    @RequestMapping(value = "/editWaterDevice")
    @ResponseBody
    public String editWaterDevice(HttpSession session, @Valid @RequestBody WaterMeterEditParam param) {
        LoginUser currentUser = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        userLogService.create(currentUser.getId(), Action.ADD.getActionId(),"客户档案", "customerNo", param.getCustomerNo(),"修改水表["+param.getDeviceNo()+"]信息" , JSON.toJSONString(param));

        // 修改设备
        WaterMeterAddParam addParam = new WaterMeterAddParam();
        BeanUtils.copyProperties(param,addParam);
        customerInfoService.editCustomerWaterMeter(addParam,Long.valueOf(currentUser.getId()),param.getOrgNo(),param.getCustomerNo());

        // 同步设备档案
        String cno = CNoUtil.CreateCNo(DeviceType.WATER_METER.getCode(),param.getDeviceNo());
        this.syncSingleDevice(cno);

        Result result = new Result();
        result.setMessage("修改成功");
        return JSON.toJSONString(result);
    }

    // 用户档案明细界面，修改气表设备信息
    @Auth(menuID=10002101L,actionID=2L)
    @SystemControllerLog(description = "用户档案明细界面，修改气表设备信息")
    @RequestMapping(value = "/editGasDevice")
    @ResponseBody
    public String editGasDevice(HttpSession session, @Valid @RequestBody GasMeterEditParam param) {
        LoginUser currentUser = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        userLogService.create(currentUser.getId(), Action.ADD.getActionId(),"客户档案", "customerNo" , param.getCustomerNo(),"修改气表["+param.getDeviceNo()+"]信息", JSON.toJSONString(param));

        // 修改设备
        GasMeterAddParam addParam = new GasMeterAddParam();
        BeanUtils.copyProperties(param,addParam);
        customerInfoService.editCustomerGasMeter(addParam,Long.valueOf(currentUser.getId()),param.getOrgNo(),param.getCustomerNo());

        // 同步设备档案
        String cno = CNoUtil.CreateCNo(DeviceType.GAS_METER.getCode(),param.getDeviceNo());
        this.syncSingleDevice(cno);

        Result result = new Result();
        result.setMessage("修改成功");
        return JSON.toJSONString(result);
    }

    // 用户档案明细界面，删除单个设备信息
    @Auth(menuID=10002101L,actionID=3L)
    @SystemControllerLog(description = "用户档案明细界面，删除单个设备信息")
    @RequestMapping(value = "/deleteSingleDevice")
    @ResponseBody
    public String deleteSingleDevice(HttpSession session,
                                     @RequestParam String customerNo,
                                     @RequestParam String deviceNo,
                                     @RequestParam String deviceType) {
        Result result = new Result();
        if (StringUtils.isEmpty(customerNo)) {
            result.error("customerNo不能为空");
            return JSON.toJSONString(result);
        }

        if (StringUtils.isEmpty(deviceNo)) {
            result.error("deviceNo不能为空");
            return JSON.toJSONString(result);
        }

        if (StringUtils.isEmpty(deviceType)) {
            result.error("deviceType不能为空");
            return JSON.toJSONString(result);
        }

        // 是否是水电气类型
        boolean flag = this.checkDeviceType4delete(deviceType);
        if (!flag) {
            result.error("只支持水电气的删除");
            return JSON.toJSONString(result);
        }
        Map map= Maps.newHashMap();
        map.put("customerNo",customerNo); map.put("deviceNo",deviceNo); map.put("deviceType",deviceType);
        // 记录日志
        String devicename = null;
        if (DeviceType.ELECTRIC_METER.getCode().equals(deviceType)) {
            devicename = DeviceType.ELECTRIC_METER.getMessage();
        } else if (DeviceType.WATER_METER.getCode().equals(deviceType)) {
            devicename = DeviceType.WATER_METER.getMessage();
        } else if (DeviceType.GAS_METER.getCode().equals(deviceType)) {
            devicename = DeviceType.GAS_METER.getMessage();
        }
        LoginUser currentUser = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        userLogService.create(currentUser.getId(), Action.ADD.getActionId(),"客户档案","customerNo", customerNo,"删除"+devicename+"["+deviceNo+"]信息", String.valueOf(map));

        // 删除设备
        customerInfoService.deleteCustomerMeter(customerNo,deviceNo,deviceType,currentUser.getUserName());

        result.setMessage("删除成功");
        return JSON.toJSONString(result);
    }

    // 用户档案明细界面，添加电表设备信息
    @Auth(menuID=10002101L,actionID=1L)
    @SystemControllerLog(description = "用户档案明细界面，添加电表设备信息")
    @RequestMapping(value = "/addSingleElectricDevice")
    @ResponseBody
    public String addSingleElectricDevice(HttpSession session, @Valid @RequestBody SingleElectricMeterAddParam param) {
        LoginUser currentUser = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);


        //添加设备
        ElectricMeterAddParam addParam = new ElectricMeterAddParam();
        BeanUtils.copyProperties(param,addParam);
        customerInfoService.addSingleElectricMeter(addParam,param.getCustomerNo(),Long.valueOf(currentUser.getId()));

        // 同步设备档案
        String cno = CNoUtil.CreateCNo(DeviceType.ELECTRIC_METER.getCode(),param.getDeviceNo());
        this.syncSingleDevice(cno);

        Result result = new Result();
        result.setMessage("添加成功");
        userLogService.create(currentUser.getId(), Action.ADD.getActionId(),"客户档案", "customerNo", param.getCustomerNo(), "添加电表["+param.getDeviceNo()+"]信息", JSON.toJSONString(param));
        return JSON.toJSONString(result);
    }

    // 用户档案明细界面，添加水表设备信息
    @Auth(menuID=10002101L,actionID=1L)
    @SystemControllerLog(description = "用户档案明细界面，添加水表设备信息")
    @RequestMapping(value = "/addSingleWaterDevice")
    @ResponseBody
    public String addSingleWaterDevice(HttpSession session, @Valid @RequestBody SingleWaterMeterAddParam param) {
        LoginUser currentUser = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);


        // 添加设备
        WaterMeterAddParam addParam = new WaterMeterAddParam();
        BeanUtils.copyProperties(param,addParam);
        customerInfoService.addSingleWaterMeter(addParam,param.getCustomerNo(),Long.valueOf(currentUser.getId()));

        // 同步设备档案
        String cno = CNoUtil.CreateCNo(DeviceType.WATER_METER.getCode(),param.getDeviceNo());
        this.syncSingleDevice(cno);

        Result result = new Result();
        result.setMessage("添加成功");
        userLogService.create(currentUser.getId(), Action.ADD.getActionId(),"客户档案", "customerNo",  param.getCustomerNo(),"添加水表["+param.getDeviceNo()+"]信息", JSON.toJSONString(param));
        return JSON.toJSONString(result);
    }

    // 用户档案明细界面，添加气表设备信息
    @Auth(menuID=10002101L,actionID=1L)
    @SystemControllerLog(description = "用户档案明细界面，添加气表设备信息")
    @RequestMapping(value = "/addSingleGasDevice")
    @ResponseBody
    public String addSingleGasDevice(HttpSession session, @Valid @RequestBody SingleGasMeterAddParam param) {
        LoginUser currentUser = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);


        // 添加设备
        GasMeterAddParam addParam = new GasMeterAddParam();
        BeanUtils.copyProperties(param,addParam);
        customerInfoService.addSingleGasMeter(addParam,param.getCustomerNo(),Long.valueOf(currentUser.getId()));

        // 同步设备档案
        String cno = CNoUtil.CreateCNo(DeviceType.GAS_METER.getCode(),param.getDeviceNo());
        this.syncSingleDevice(cno);

        Result result = new Result();
        result.setMessage("添加成功");
        userLogService.create(currentUser.getId(), Action.ADD.getActionId(),"客户档案", "customerNo", param.getCustomerNo(),"添加气表["+param.getDeviceNo()+"]信息" ,JSON.toJSONString(param));
        return JSON.toJSONString(result);
    }

    // 客户档案上传excel文档
    @Auth(menuID=10002101L,actionID=9L)
    @SystemControllerLog(description = "客户档案上传excel文档")
    @RequestMapping(value = "/uploadFile")
    @ResponseBody
    public String uploadFile(HttpServletRequest request, @RequestParam MultipartFile fileName) {
        Result<UploadFileResultInfo> result = new Result();
        String originalFilename = fileName.getOriginalFilename();
        String uuid = UuidUtil.getUuid();
        String parentPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");
        String saveName = uuid + originalFilename.substring(originalFilename.lastIndexOf("."));
        File parentFile = new File(parentPath);
        if(!parentFile.exists() || !parentFile.isDirectory()) {
            parentFile.mkdirs();
        }
        File newFile = new File(parentFile, saveName);
        if(!newFile.exists()) {
            try {
                newFile.createNewFile();
                fileName.transferTo(newFile);
            } catch (IOException e) {
                logger.error("文件IO异常",e);
                result.error("文件IO异常");
                return JSON.toJSONString(result);
            }
        }

        UploadFileResultInfo resultInfo = new UploadFileResultInfo();
        resultInfo.setSaveName(saveName);
        resultInfo.setImportId(uuid);
        resultInfo.setImportTime(new Date());
        resultInfo.setFileName(originalFilename);
        result.setData(resultInfo);
        return JSON.toJSONString(result);
    }

    // 客户档案批量新增
    @Auth(menuID=10002101L,actionID=1L)
    @SystemControllerLog(description = "客户档案批量新增")
    @RequestMapping(value = "/batchSave")
    @ResponseBody
    public String batchSave(HttpSession session, @RequestParam String saveName) {
        Result<BatchSaveResultInfo> result = new Result();
        if (StringUtils.isEmpty(saveName)) {
            result.error("saveName不能为空");
            return JSON.toJSONString(result);
        }
        LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);

        String suffixName = saveName.substring(saveName.lastIndexOf("."));
        if(!".xls".equals(suffixName) && !".xlsx".equals(suffixName)) {
            result.error("文件格式不正确");
            return JSON.toJSONString(result);
        }
        String uuid = saveName.substring(0, saveName.lastIndexOf("."));
        CustomerBatchImportInfo importInfo = customerInfoService.excelImport(session, saveName, uuid);
        if(1 == importInfo.getResult()) {
            BatchSaveResultInfo batchSaveResultInfo = new BatchSaveResultInfo();
            batchSaveResultInfo.setSuccessCount(importInfo.getSuccessCount());
            batchSaveResultInfo.setFailCount(importInfo.getFailCount());
            result.setData(batchSaveResultInfo);

            result.setMessage("导入成功");
        } else {
            result.error("导入失败");
        }
        if(result.getData().getSuccessCount()>0){
            userLogService.create(user.getId(), Action.ADD.getActionId(),"客户档案","saveName",saveName,"批量新增客户档案",saveName);
        }
        return JSON.toJSONString(result);
    }

    /**
     * 下载用户档案
     * @param response
     * @param session
     * @param param
     */
    @Auth(menuID=10002101L,actionID=6L)
    @SystemControllerLog(description = "下载用户档案")
    @RequestMapping("/downloadCustomerInfo")
    public void downloadCustomerInfo(HttpServletResponse response, HttpSession session, CustomerInfoQueryNewParam param) {
        LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        userLogService.create(user.getId(), Action.DELETE.getActionId(),"客户档案","deviceNo", param.getDeviceNo(), "下载"+param.getCustomerName()+"客户档案" , JSON.toJSONString(param));

        CustomerInfoQueryNewVo customerInfoQueryVo = new CustomerInfoQueryNewVo();
        BeanUtils.copyProperties(param, customerInfoQueryVo);

        // 暂时只能最大下载10000条数据
        if(customerInfoQueryVo.getPageSize() > 10000){
            customerInfoQueryVo.setPageSize(10000);
        }

        customerInfoQueryVo.setUserId(Long.valueOf(user.getId()));
        try {

            String s = generateInfo(session, param);
            customerInfoQueryVo.setImportGuid(s);
            List<CustomerInfoDtodownload> result = customerInfoService.querydownlist(customerInfoQueryVo);


            XSSFWorkbook workBook = generateFileService.generateCustomerRecordExcel("用户档案", result);
            //通过Response把数据以Excel格式保存
            response.reset();
            //设置response流信息的头类型，MIME码
            response.setContentType("application/vnd.ms-excel");
            ServletOutputStream out = null;
            response.addHeader("Content-Disposition", "attachment;filename=\""
                    + new String(("导出表" + System.currentTimeMillis() + ".xls").getBytes("utf-8"),
                    "ISO8859_1") + "\"");
            //创建输出流对象
            out=response.getOutputStream();
            //将创建的Excel对象利用二进制流的形式强制输出到客户端去
            workBook.write(out);
            //强制将数据从内存中保存
            out.flush();
            out.close();
        } catch (Exception e) {
            logger.error("系统异常：",e);
        }
    }


    // 客户档案批量导入时，下载异常数据
    @SystemControllerLog(description = "下载用户档案")
    @RequestMapping(value = "/downloadErrorData")
    public void downloadErrorData(HttpServletResponse response, HttpSession session, @RequestParam String importId) {
        try {
            LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
            userLogService.create(user.getId(), Action.DOWNLOAD.getActionId(),"客户档案","importId",importId,"批量导入异常数据下载",importId);

            String parentPath = session.getServletContext().getRealPath("/WEB-INF/upload/");
            File parentFile = new File(parentPath);
            String type = importId + "error";
            //通过文件名过滤文件
            FilenameFilter filter = new MyFilenameFilter(type);
            String[]  fileNames = parentFile.list(filter);

            Workbook wb;
            FileInputStream in = new FileInputStream(new File(parentFile,fileNames[0]));
            String suffixName = fileNames[0].substring(fileNames[0].lastIndexOf("."));
            if(".xls".equals(suffixName)) {
                wb = new HSSFWorkbook(in);
            } else {
                wb = new XSSFWorkbook(in);
            }
            //通过Response把数据以Excel格式保存
            response.reset();
            //设置response流信息的头类型，MIME码
            response.setHeader("Content-type","application/x-msexcel");
            response.setHeader("content-disposition","attachment;filename=\"" + System.currentTimeMillis() + ".xls\"");
            ServletOutputStream out;
            //创建输出流对象
            out=response.getOutputStream();
            wb.write(out);
            out.flush();
            out.close();
            wb.close();
        } catch (Exception e) {
            logger.error("系统异常：",e);
        }
    }

    // 客户档案明细，读取按钮处理逻辑
    @SystemControllerLog(description = "客户档案明细，读取按钮处理逻辑")
    @RequestMapping(value = "/jzqReadCustomerInfo")
    @ResponseBody
    public String jzqReadCustomerInfo(@RequestParam String guid,
                                      @RequestParam String jzqNo,
                                      @RequestParam Integer commSetupSn) {
        Result result = new Result();
        if (StringUtils.isEmpty(guid)) {
            result.error("guid不能为空");
            return JSON.toJSONString(result);
        }

        if (StringUtils.isEmpty(jzqNo)) {
            result.error("jzqNo不能为空");
            return JSON.toJSONString(result);
        }

        int msg = instructService.jzqReadCustomerInfo(guid, jzqNo, String.valueOf(commSetupSn));
        if (msg != 1) {
            result.error("指令执行失败");
            logger.info("客户资料读取指令执行失败，msg" + msg);
        }
        return JSON.toJSONString(result);
    }

    // 设备微信解绑,删除该设备关联的所有微信绑定信息
    @Auth(menuID=10002101L,actionID=1L)
    @SystemControllerLog(description = "设备微信解绑")
    @RequestMapping(value = "/weChatUnBind")
    @ResponseBody
    public String weChatUnBind(HttpSession session, @RequestParam String cno) {

        Result result = new Result("解绑成功");
        if (StringUtils.isEmpty(cno)) {
            result.error("cno不能为空");
            return JSON.toJSONString(result);
        }

        customerInfoService.weChatUnBind(cno);
        LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        userLogService.create(user.getId(), Action.DOWNLOAD.getActionId(),"客户档案","importId","","设备["+CNoUtil.getDeviceNoByCno(cno)+"]微信解绑",JSON.toJSONString(cno));
        return JSON.toJSONString(result);

    }

    /**
     * 同步设备档案
     * @param param
     */
    private void syncDeviceInfo(CustomerInfoCreateParam param) {
        List<String> cnoList = Lists.newArrayList();
        List<ElectricMeterAddParam> electricMeter = param.getElectricMeter();
        if (!CollectionUtils.isEmpty(electricMeter)) {
            for (ElectricMeterAddParam meterAddParam : electricMeter) {
                String cno = CNoUtil.CreateCNo(DeviceType.ELECTRIC_METER.getCode(), meterAddParam.getDeviceNo());
                cnoList.add(cno);
            }
        }

        List<WaterMeterAddParam> waterMeter = param.getWaterMeter();
        if (!CollectionUtils.isEmpty(waterMeter)) {
            for (WaterMeterAddParam meterAddParam : waterMeter) {
                String cno = CNoUtil.CreateCNo(DeviceType.WATER_METER.getCode(), meterAddParam.getDeviceNo());
                cnoList.add(cno);
            }
        }

        List<GasMeterAddParam> gasMeter = param.getGasMeter();
        if (!CollectionUtils.isEmpty(gasMeter)) {
            for (GasMeterAddParam meterAddParam : gasMeter) {
                String cno = CNoUtil.CreateCNo(DeviceType.GAS_METER.getCode(), meterAddParam.getDeviceNo());
                cnoList.add(cno);
            }
        }

        for (String cno : cnoList) {
            this.syncSingleDevice(cno);
        }
    }

    /**
     * 删除设备时，校验设备类型是否正确
     * @param deviceType
     * @return
     */
    private boolean checkDeviceType4delete(String deviceType) {
        boolean flag = false;
        if (DeviceType.ELECTRIC_METER.getCode().equals(deviceType)) {
            flag = true;
        } else if (DeviceType.WATER_METER.getCode().equals(deviceType)) {
            flag = true;
        } else if (DeviceType.GAS_METER.getCode().equals(deviceType)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 通过单个设备档案，该方法不能对外抛异常
     * @param deviceCno
     */
    private void syncSingleDevice(String deviceCno) {
        try {
            int returnVal = deviceMeterParamService.syncMeterToJzq(deviceCno);
            logger.info("同步设备档案结果为：" + returnVal);
        } catch (Exception e) {
            logger.error("同步设备档案异常：",e);
        }
    }
    private String generateInfo(HttpSession session, CustomerInfoQueryNewParam realTimeDataParam) {
        String uuid = UuidUtil.getUuid();
        List list= Lists.newArrayList();
        String longs = realTimeDataParam.getpNodeNo();
        String nodeNoList = realTimeDataParam.getNodeNo();
        if(1==realTimeDataParam.getNodeType()){
            Impot impot=new Impot();
            impot.setCreateTime(new Date());
            // impot.setDeviceType(String.valueOf(3));
            impot.setDataType(3);
            impot.setImpotBatch(uuid);
            impot.setSearchNo(nodeNoList);
            impot.setIsSearchChild(realTimeDataParam.getIsSearchChild());
            list.add(impot);

        }
        if(2==realTimeDataParam.getNodeType()){
            Impot impot=new Impot();
            impot.setCreateTime(new Date());
            // impot.setDeviceType(String.valueOf(3));
            impot.setDataType(3);
            impot.setImpotBatch(uuid);
            impot.setSearchNo(longs);
            impot.setIsSearchChild(realTimeDataParam.getIsSearchChild());
            impot.setChildFlag(nodeNoList);
            list.add(impot);

        }


        String savePath = session.getServletContext().getRealPath("/WEB-INF/upload/" + uuid + ".csv");
        File saveCSV = new File(savePath);
        if (!saveCSV.getParentFile().exists()) {
            boolean result1 = saveCSV.getParentFile().mkdirs();
            if (!result1) {
                logger.error("创建文件异常");
            }
        }
        if(!saveCSV.exists()||!saveCSV.isFile()){
            try {
                saveCSV.createNewFile();
            } catch (IOException e) {
                logger.error("创建文件异常", e);
            }
        }
        generateFileService.generateRealTimeData(savePath, list);
        return uuid;
    }
    private List getNolist( CstOnOffGetQueryNewParam realTimeDataParam, List<String> customerNoList,Integer dataType, String uuid) {
        List list= Lists.newArrayList();
        for (String s : customerNoList) {
            Impot impot=new Impot();
            impot.setCreateTime(new Date());
            //impot.setDeviceType(realTimeDataParam.getDeviceType());
            impot.setDataType(dataType);
            impot.setImpotBatch(uuid);
            impot.setSearchNo(s);
            impot.setIsSearchChild(realTimeDataParam.getIsSearchChild());
            list.add(impot);
        }
        return list;
    }
}
