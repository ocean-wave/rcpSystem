package cn.com.cdboost.collect.controller;


import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.RealMeterCollectFailQueryVo;
import cn.com.cdboost.collect.dto.response.MeterCollectDataFailInfo;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.enums.InstructCode;
import cn.com.cdboost.collect.enums.ReplenishEnum;
import cn.com.cdboost.collect.model.*;
import cn.com.cdboost.collect.service.*;
import cn.com.cdboost.collect.util.CipherAESMessage;
import cn.com.cdboost.collect.util.CryptographyUtil;
import cn.com.cdboost.collect.vo.PageResult;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 提供给App端补抄功能的http接口
 */
@Controller
@RequestMapping("/android")
public class AppReplenishController {
    private static final Logger logger = LoggerFactory.getLogger(AppReplenishController.class);
    private static final String ANDROID_SECRET_KEY = "lianghuilonglong";

    @Autowired
    private UserService userService;
    @Autowired
    private OrgService orgService;
    @Autowired
    private ReplenishService replenishService;
    @Autowired
    private MeterReadQueueService meterReadQueueService;
    @Autowired
    private InstructService instructService;
    @Autowired
    private RealTimeDataService realTimeDataService;
    @Autowired
    private DeviceMeterParamService deviceMeterParamService;
    @Autowired
    private DeviceMeterConfigService deviceMeterConfigService;

    /**
     * App登录接口
     * @param loginName
     * @param password
     * @return
     */
    @RequestMapping(value = "/appLoginAuth", method={RequestMethod.POST})
    @SystemControllerLog(description = "APP端登录认证")
    @ResponseBody
    public String appLoginAuth(@RequestParam String loginName, @RequestParam String password) {
        Result<LoginSuccessResponse> result = new Result<>();
        if (StringUtils.isEmpty(loginName)) {
            result.error("loginName不能为空");
            return JSON.toJSONString(result);
        }

        if (StringUtils.isEmpty(password)) {
            result.error("password不能为空");
            return JSON.toJSONString(result);
        }
        // 解密
        String decryptLoginName = CipherAESMessage.EncipherAESMsg(ANDROID_SECRET_KEY,loginName);
        String decryptPwd = CipherAESMessage.EncipherAESMsg(ANDROID_SECRET_KEY,password);
        // 登录验证
        boolean flag = this.loginAuth(decryptLoginName,decryptPwd,result);
        if (!flag) {
            return JSON.toJSONString(result);
        }
        User user = userService.getUserByLoginName(decryptLoginName);
        // 组装返回数据
        LoginSuccessResponse dto = new LoginSuccessResponse();
        dto.setUserId(Long.valueOf(user.getId()));
        dto.setUserName(user.getUserName());
        dto.setPhoneNum(user.getUserMobile());

        Org org =orgService.queryByOrgNo(user.getOrgNo());
        dto.setDepartment(org.getOrgName());
        result.setData(dto);

        return JSON.toJSONString(result);
    }


    /**
     * 补抄工单请求
     * @param userId 用户id
     * @param pageIndex 当前页号
     * @param pageSize 每页显示的数量
     * @return
     */
    @RequestMapping(value = "replenishWorkOrder", method={RequestMethod.POST})
    @SystemControllerLog(description = "App端补抄工单查询")
    @ResponseBody
    public String replenishWorkOrder(@RequestParam Long userId,
                                     @RequestParam Integer pageIndex,
                                     @RequestParam Integer pageSize) {
        PageResult<List<WorkOrder>> result = new PageResult<>();
        StringBuilder sb = new StringBuilder();
        List<WorkOrder> workOrders = replenishService.queryWorkOrder(userId, pageIndex, pageSize, sb);
        // 组装返回数据
        if(!CollectionUtils.isEmpty(workOrders)) {
            result.setData(workOrders);
            result.setTotal(Long.valueOf(sb.toString()));
        } else {
            setResultEmptyList(result);
        }

        return JSON.toJSONString(result);
    }

    /**
     * 工单详情请求
     * @param pageIndex
     * @param pageSize
     * @param taskNo
     * @return
     */
    @RequestMapping(value = "replenishWorkOrderDetail", method={RequestMethod.POST})
    @SystemControllerLog(description = "App端补抄工单详情查询")
    @ResponseBody
    public String replenishWorkOrderDetail(@RequestParam Integer pageIndex,
                                           @RequestParam Integer pageSize,
                                           @RequestParam String taskNo) {
        PageResult<List<WorkOrderDetail>> result = new PageResult<>();
        if (StringUtils.isEmpty(taskNo)) {
            result.error("taskNo不能为空");
            return JSON.toJSONString(result);
        }
        StringBuilder sb = new StringBuilder();
        List<WorkOrderDetail> workOrderDetails = replenishService.queryWorkOrderDetail(pageIndex, pageSize, taskNo, sb);
        // 组装返回数据
        if (!CollectionUtils.isEmpty(workOrderDetails)) {
            result.setData(workOrderDetails);
            result.setTotal(Long.valueOf(sb.toString()));
        } else {
            setResultEmptyList(result);
        }

        return JSON.toJSONString(result);
    }


    /**
     * 工单数据上传，需要校验用户名密码
     * @param collectResult 要上传的工单数据
     * @return
     */
    @RequestMapping(value = "uploadCollectResult", method={RequestMethod.POST})
    @SystemControllerLog(description = "App端补抄工单数据上传")
    @ResponseBody
    public String uploadCollectResult(@Valid @RequestBody UploadCollectResult collectResult) {
        Result result = new Result();
        // 接口权限认证
        String loginName = collectResult.getLoginName();
        String password = collectResult.getPassword();
        String decryptLoginName = CipherAESMessage.EncipherAESMsg(ANDROID_SECRET_KEY,loginName);
        String decryptPwd = CipherAESMessage.EncipherAESMsg(ANDROID_SECRET_KEY,password);
        logger.info("用户" + decryptLoginName + "上传工单");
        boolean flag = this.loginAuth(decryptLoginName,decryptPwd,result);
        if (!flag) {
            return JSON.toJSONString(result);
        }

        User user = userService.getUserByLoginName(decryptLoginName);
        // 更新采集结果信息
        int i = replenishService.uploadCollectResult(collectResult,String.valueOf(user.getId()));
        if (i == ReplenishEnum.UpaloadDbReturn.FAIL.getCode()) {
            result.error(ReplenishEnum.UpaloadDbReturn.FAIL.getMessage());
        } else if (i == ReplenishEnum.UpaloadDbReturn.PARSE_JSON_FAIL.getCode()) {
            result.error(ReplenishEnum.UpaloadDbReturn.PARSE_JSON_FAIL.getMessage());
        } else if (i == ReplenishEnum.UpaloadDbReturn.EXCEPTION.getCode()) {
            result.error(ReplenishEnum.UpaloadDbReturn.EXCEPTION.getMessage());
        }

        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "queryNewWorkOrderDetailByCnos", method={RequestMethod.POST})
    @SystemControllerLog(description = "App端补查询电表对应的customerNo")
    @ResponseBody
    public String queryNewWorkOrderDetailByCnos(@RequestBody CnoInfoDTO[] list) {
        PageResult<List<WorkOrderDetail>> result = new PageResult<>();
        List<String> cnoList = Lists.newArrayList();
        for (CnoInfoDTO cnoInfoDTO : list) {
            cnoList.add(cnoInfoDTO.getCno());
        }
        List<WorkOrderDetail> workOrderDetails = replenishService.appCreateNewWorkOrderDetail(cnoList);

        // 组装返回数据
        if (!CollectionUtils.isEmpty(workOrderDetails)) {
            result.setData(workOrderDetails);
        } else {
            setResultEmptyList(result);
        }

        return JSON.toJSONString(result);
    }

    /**
     * 发送抄表指令
     * @param param
     * @return
     */
    @RequestMapping(value = "sendCollectInstructions", method={RequestMethod.POST})
    @SystemControllerLog(description = "App端发送抄表指令")
    @ResponseBody
    public String sendCollectInstructions(@Valid @RequestBody SendCollectCommandParam param) {
        Result<String> result = new Result<>();

        // 接口权限认证
        String loginName = param.getLoginName();
        String password = param.getPassword();
        String decryptLoginName = CipherAESMessage.EncipherAESMsg(ANDROID_SECRET_KEY,loginName);
        String decryptPwd = CipherAESMessage.EncipherAESMsg(ANDROID_SECRET_KEY,password);
        boolean flag = this.loginAuth(decryptLoginName,decryptPwd,result);
        if (!flag) {
            return JSON.toJSONString(result);
        }
        User user = userService.getUserByLoginName(decryptLoginName);

        String guid = param.getGuid();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        Date date =  calendar.getTime();

        List<SendCollectMeter> meters = param.getMeters();
        List<String> cnoList = Lists.newArrayList();
        for (SendCollectMeter meter : meters) {
            cnoList.add(meter.getDeviceCno());
        }
        // 批量查询设备参数表信息
        List<DeviceMeterParam> deviceMeterParams = deviceMeterParamService.findDeviceMeterParamByCnos(cnoList);
        Map<String,DeviceMeterParam> meterParamMap = Maps.newHashMap();
        Set<String> paramFlagSet = Sets.newHashSet();
        for (DeviceMeterParam deviceMeterParam : deviceMeterParams) {
            paramFlagSet.add(deviceMeterParam.getParamFlag());
            meterParamMap.put(deviceMeterParam.getCno(),deviceMeterParam);
        }

        // 批量查询设备参数配置信息
        ImmutableMap<String, DeviceMeterConfig> meterConfigMap = deviceMeterConfigService.batchQueryByParamFlags(paramFlagSet);

        List<MeterReadQueue> list = Lists.newArrayList();
        for (SendCollectMeter meter : meters) {
            String jzqCno = meter.getJzqCno();
            String deviceCno = meter.getDeviceCno();
            String groupGuid = meter.getGroupGuid();
            MeterReadQueue meterReadQueue = new MeterReadQueue();
            meterReadQueue.setJzqCno(jzqCno);
            meterReadQueue.setMeterCno(deviceCno);
            meterReadQueue.setCreateUserId(Long.valueOf(user.getId()));
            meterReadQueue.setQueueGuid(guid);
            List<DiAndDateFormat> diArray = meter.getDiArray();
            List<String> diList = Lists.newArrayList();
            List<String> dataFormatList = Lists.newArrayList();
            for (DiAndDateFormat format : diArray) {
                diList.add(format.getDi());
                dataFormatList.add(format.getDataFormat());
            }
            meterReadQueue.setDi645(diList.toArray(new String[diList.size()]));
            meterReadQueue.setDataFormat(dataFormatList.toArray(new String[dataFormatList.size()]));
            meterReadQueue.setJzqNo(jzqCno.substring(2).replaceAll("^0*", ""));
            meterReadQueue.setMeterNo(deviceCno.substring(2).replaceAll("^0*", ""));
            meterReadQueue.setCreateTime(date);
            meterReadQueue.setGroupGuid(groupGuid);

            DeviceMeterParam deviceMeterParam = meterParamMap.get(deviceCno);
            if(deviceMeterParam.getCommPort() == 32){
                if(deviceMeterParam.getMoteType().equals("C")){
                    meterReadQueue.setOverTime(20);
                }else {
                    meterReadQueue.setOverTime(10);
                }
            }else {
                meterReadQueue.setOverTime(60);
            }

            MeterReadQueue queueParam = new MeterReadQueue();
            queueParam.setMeterCno(deviceCno);
            queueParam.setQueueGuid(guid);
            int count = meterReadQueueService.selectCount(queueParam);
            if (count > 0) {
                MeterReadQueue updateParam = new MeterReadQueue();
                updateParam.setReadStatus(0);
                updateParam.setUpdateTime(new Date());
                updateParam.setCreateTime(date);
                updateParam.setMeterCno(deviceCno);
                updateParam.setQueueGuid(guid);
                meterReadQueueService.updateSelectiveByGuidAndCno(updateParam);
            } else {
                meterReadQueueService.insertSelective(meterReadQueue);
            }
            list.add(meterReadQueue);
        }

        int sendResult = instructService.sendCollectInstructList4App(meterConfigMap,meterParamMap,list);
        logger.info("采集指令结果sendResult：" + sendResult);
        if (sendResult != InstructCode.Success.getValue()) {
            for (MeterReadQueue meterReadQueue : list) {
                MeterReadQueue updateParam = new MeterReadQueue();
                updateParam.setReadStatus(2);
                updateParam.setUpdateTime(new Date());
                updateParam.setCreateTime(date);
                updateParam.setMeterCno(meterReadQueue.getMeterCno());
                updateParam.setQueueGuid(guid);
                meterReadQueueService.updateSelectiveByGuidAndCno(updateParam);
            }
            String messageTemplate = ReplenishEnum.ParamCheck.SEND_COLLECT_RESULT.getMessage();
            String message = String.format(messageTemplate, sendResult);
            result.error(message);
            return JSON.toJSONString(result);
        }

        result.setData(sdf.format(date));
        result.setMessage("采集指令结果返回成功");
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "queryCollectStatus", method={RequestMethod.POST})
    @SystemControllerLog(description = "App端查询采集状态")
    @ResponseBody
    public String queryCollectStatus(@Valid @RequestBody CollectStatusQueryParam param) {
        Result<RealTimeDataStatuListInfo> result = new Result<>();
        Integer stopFlag = param.getStopFlag();
        if (stopFlag == 0) {
            String guid = param.getGuid();
            String deviceType = param.getDeviceType();
            String date = param.getDate();
            RealTimeDataStatuListInfo info = realTimeDataService.realTimeDataListStatus(param.getUserId(), guid, deviceType, date);
            Integer status = info.getStatus();
            Boolean isUpdate = info.getIsUpdate();
            if(status.intValue() != 101 || isUpdate){
                this.setFailList(guid, deviceType, info, date);
            }

            result.setData(info);
        }

        return JSON.toJSONString(result);
    }


    private void setFailList(String guid, String deviceType,RealTimeDataStatuListInfo info, String date) {
        RealMeterCollectFailQueryVo queryVo = new RealMeterCollectFailQueryVo();
        queryVo.setGuid(guid);
        queryVo.setSearchDate(date);
        queryVo.setDeviceType(deviceType);
        queryVo.setPageSize(info.getTotal());
        List<MeterCollectDataFailInfo> dataFailInfos = realTimeDataService.listRealTimeFailData(queryVo);

        Boolean isUpdate = info.getIsUpdate();
        if(isUpdate) {
            List<String> cnoList = info.getCnoList();
            if (CollectionUtils.isEmpty(cnoList)) {
                dataFailInfos = null;
            } else {
                Iterator<MeterCollectDataFailInfo> iterator = dataFailInfos.iterator();
                while (iterator.hasNext()){
                    MeterCollectDataFailInfo failInfo = iterator.next();
                    if(!cnoList.contains(failInfo.getDeviceCno())){
                        iterator.remove();
                    }
                }
            }
        }
        info.setFailList(dataFailInfos);
    }

    /**
     * 登录验证
     * @param loginName
     * @param password
     * @return
     */
    private boolean loginAuth(String loginName, String password, Result result) {
        boolean flag = true;
        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(loginName, CryptographyUtil.md5(password, GlobalConstant.SECRET_SALT));
            subject.login(token);
        }catch (Exception e) {
            String errorMsg = ReplenishEnum.AppRequstResult.AUTH_FAIL.getMessage();
            logger.error(errorMsg,e);
            result.error(errorMsg);
            flag = false;
        }
        return flag;
    }


    /**
     * 设置空结果集
     * @param result
     */
    private void setResultEmptyList(PageResult result) {
        result.setData(Collections.emptyList());
        result.setTotal(0L);
    }

}
