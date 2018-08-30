package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.dto.FeeOnOffInfo;
import cn.com.cdboost.collect.dto.FeeOnOffStatusListInfo;
import cn.com.cdboost.collect.dto.LoginUser;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.enums.Action;
import cn.com.cdboost.collect.enums.Auth;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.enums.ResultCode;
import cn.com.cdboost.collect.model.Impot;
import cn.com.cdboost.collect.service.FeeOnOffService;
import cn.com.cdboost.collect.service.GenerateFileService;
import cn.com.cdboost.collect.service.UserLogService;
import cn.com.cdboost.collect.util.CNoUtil;
import cn.com.cdboost.collect.util.UuidUtil;
import cn.com.cdboost.collect.vo.PageResult;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author zc
 * @desc 通断Controller类
 * @create 2017-07-05 17:09
 **/
@Controller
@RequestMapping("/onOff")
public class FeeOnOffController {
    private static final Logger logger = LoggerFactory.getLogger(FeeOnOffController.class);

    @Autowired
    private FeeOnOffService feeOnOffService;
    @Autowired
    private UserLogService userLogService;
    @Autowired
    private GenerateFileService generateFileService;

    // 通断电列表查询
    @Auth(menuID=10001203L,actionID={13L})
    @SystemControllerLog(description = "通断电列表查询")
    @RequestMapping(value = "/queryList")
    @ResponseBody
    public String queryListOLd(HttpSession session, @Valid @RequestBody CstOnOffGetQueryParam queryParam) {
        PageResult<List<FeeOnOffInfo>> result = new PageResult<>();
        LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        CstOnOffGetQueryVo queryVo = new CstOnOffGetQueryVo();
        BeanUtils.copyProperties(queryParam,queryVo);
        queryVo.setUserId(Long.valueOf(user.getId()));
        List<FeeOnOffInfo> list = feeOnOffService.query(queryVo);
        result.setData(list);
        result.setTotal(queryVo.getTotal());

        return JSON.toJSONString(result);
    }
    // 通断电列表查询
    @Auth(menuID=10001203L,actionID={4L,13L})
    @SystemControllerLog(description = "通断电列表查询")
    @RequestMapping(value = "/queryListNew")
    @ResponseBody
    public String queryListNew(HttpSession session,@Valid @RequestBody CstOnOffGetQueryNewParam queryParam) {
        PageResult<List<FeeOnOffInfo>> result = new PageResult<>();
        LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        CstOnOffGetQueryNewVo queryVo = new CstOnOffGetQueryNewVo();
        BeanUtils.copyProperties(queryParam,queryVo);
        queryVo.setUserId(Long.valueOf(user.getId()));
        String uuid = UuidUtil.getUuid();
        Integer integer = generateInfo( uuid,session, queryParam);
        queryVo.setImportGuid(uuid);
        if (integer!=1){
            queryVo.setImportGuid("");
        }
        List<FeeOnOffInfo> list = feeOnOffService.queryNew(queryVo);
        result.setData(list);
        result.setTotal(queryVo.getTotal());

        return JSON.toJSONString(result);
    }


    // 通电或断电
    @Auth(menuID={10001203L,10001201L},actionID={13L,30L})
    @SystemControllerLog(description = "通电或断电")
    @RequestMapping(value = "/onOff")
    @ResponseBody
    public String onOff(HttpSession session,@Valid @RequestBody FeeOnOffQueryParam queryParam) {
        Result<String> result = new Result<>();
        session.setAttribute(GlobalConstant.ON_OFF_STOP_FLAG, false);
        LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        FeeOnOffParam param = new FeeOnOffParam();
        BeanUtils.copyProperties(queryParam, param);
        param.setUserId(user.getId());

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        Date date =  calendar.getTime();
        param.setDate(date);
        String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
        param.setSessionId(sessionId);
        Integer sendResult = feeOnOffService.onOff(param);
        result.setData(sdf.format(date));
        if (sendResult != ResultCode.Success.getValue()) {
            result.error("指令返回结果失败");
            if(queryParam.getMeters().size()>1){
                userLogService.create(user.getId(), Action.ON_OFF.getActionId(),"远程通断","guid", queryParam.getGuid(), "批量远程"+(1==queryParam.getOnOff()?"合闸":"拉闸")+"操作失败" , JSON.toJSONString(queryParam));
            }else{
                userLogService.create(user.getId(), Action.ON_OFF.getActionId(),"远程通断","guid", queryParam.getGuid(), "设备["+ CNoUtil.getDeviceNoByCno(queryParam.getMeters().get(0).getDeviceCno())+"]远程"+(1==queryParam.getOnOff()?"合闸":"拉闸")+"操作失败" , JSON.toJSONString(queryParam));
            }
        }
        return JSON.toJSONString(result);
    }

    // 停止批量通断
    @Auth(menuID=10001203L,actionID=13L)
    @SystemControllerLog(description = "停止批量通断")
    @RequestMapping("/stopCollectList")
    @ResponseBody
    public String stopCollectList(HttpSession session, @RequestParam String guid) {
        Result<Boolean> result = new Result<>();
        session.setAttribute(GlobalConstant.ON_OFF_STOP_FLAG, true);
        if (StringUtils.isEmpty(guid)) {
            result.error("guid不能为空");
            return JSON.toJSONString(result);
        }
        LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        userLogService.create(user.getId(), Action.ON_OFF.getActionId(),"远程通断", "guid", guid, "停止批量通断" ,guid);

        boolean retValue = feeOnOffService.stopCollectList(guid);
        result.setData(retValue);

        return JSON.toJSONString(result);
    }

//    // 通断电操作，前端轮询查看操作状态
//    @Auth(menuID=100013L,actionID={13L})
//    @SystemControllerLog(description = "通断电操作，前端轮询查看操作状态")
//    @RequestMapping(value = "/queryStatus")
//    @ResponseBody
//    public String queryStatus(HttpSession session,@Valid @RequestBody FeeOnOffStatusQueryParam queryParam) {
//        Result<FeeOnOffStatusListInfo> result = new Result<>();
//        String guid = queryParam.getGuid();
//        String deviceType = queryParam.getDeviceType();
//        String date = queryParam.getDate();
//        LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
//        userLogService.create(user.getId(), Action.ON_OFF.getActionId(),"远程通断","guid", guid, "queryStatus: guid:" + guid + ",deviceType:" + deviceType + ",date:" + date);
//
//        Integer stopFlag = queryParam.getStopFlag();
//        if(stopFlag == 0){
//            FeeOnOffStatusListInfo listInfo = feeOnOffService.queryStatus(guid, deviceType, user.getId(), date);
//            Integer status = listInfo.getStatus();
//            Boolean isUpdate = listInfo.getIsUpdate();
//            if (status != 101 || isUpdate) {
//                setFailList(guid, listInfo,date);
//            }
//            result.setData(listInfo);
//        }
//
//        return JSON.toJSONString(result);
//    }

    // 通断操作结果，成功，失败结果查询
    @Auth(menuID={10001203L,10001201},actionID={13L,30L})
    @SystemControllerLog(description = "通断操作结果，成功，失败结果查询")
    @RequestMapping(value = "/queryResult")
    @ResponseBody
    public String queryResult(@Valid @RequestBody CstOnOffOptRstGetQueryParam queryParam) {
        PageResult<List<FeeOnOffInfo>> result = new PageResult<>();
        CstOnOffOptRstGetQueryVo queryVo = new CstOnOffOptRstGetQueryVo();
        BeanUtils.copyProperties(queryParam,queryVo);
        List<FeeOnOffInfo> list = feeOnOffService.queryResult(queryVo);
        result.setData(list);
        result.setTotal(queryVo.getTotal());
        if(!CollectionUtils.isEmpty(list)){
            FeeOnOffInfo feeOnOffInfo = list.get(0);
            if("1".equals(queryParam.getDataFlag())){
                if(list.size()>1){
                    userLogService.create(1, Action.ON_OFF.getActionId(),"远程通断","guid", queryParam.getGuid(), "批量远程"+(2==feeOnOffInfo.getStatus()?"合闸":"拉闸")+"操作成功" , JSON.toJSONString(queryParam));
                }else{
                    userLogService.create(1, Action.ON_OFF.getActionId(),"远程通断","guid", queryParam.getGuid(), "设备["+ CNoUtil.getDeviceNoByCno(feeOnOffInfo.getCno())+"]远程"+(2==feeOnOffInfo.getStatus()?"合闸":"拉闸")+"操作成功" , JSON.toJSONString(queryParam));
                }
            }else{
                if(list.size()>1){
                    userLogService.create(1, Action.ON_OFF.getActionId(),"远程通断","guid", queryParam.getGuid(), "批量远程"+(2==feeOnOffInfo.getStatus()?"合闸":"拉闸")+"操作失败" , JSON.toJSONString(queryParam));
                }else{
                    userLogService.create(1, Action.ON_OFF.getActionId(),"远程通断","guid", queryParam.getGuid(), "设备["+ CNoUtil.getDeviceNoByCno(feeOnOffInfo.getCno())+"]远程"+(2==feeOnOffInfo.getStatus()?"合闸":"拉闸")+"操作失败" , JSON.toJSONString(queryParam));
                }
            }
        }
        return JSON.toJSONString(result);
    }

    // 通断列表记录上，点击查询某一户的通断记录列表
    @Auth(menuID=10001203L,actionID={13L})
    @SystemControllerLog(description = "通断列表记录上，点击查询某一户的通断记录列表")
    @RequestMapping(value = "/queryHistory")
    @ResponseBody
    public String queryHistory(@Valid @RequestBody CstOnOffDetailQueryParam queryParam) {
        PageResult<List<FeeOnOffInfo>> result = new PageResult<>();

        CstOnOffByNoGetQueryVo queryVo = new CstOnOffByNoGetQueryVo();
        BeanUtils.copyProperties(queryParam,queryVo);
        List<FeeOnOffInfo> list = feeOnOffService.queryHistory(queryVo);
        result.setData(list);
        result.setTotal(queryVo.getTotal());

        return JSON.toJSONString(result);
    }

    private void setFailList(String guid,FeeOnOffStatusListInfo listInfo, String createTime) {
        List<FeeOnOffInfo> failList = feeOnOffService.queryByGuid(guid,2,createTime);

        Boolean isUpdate = listInfo.getIsUpdate();
        if (isUpdate) {
            List<String> cnoList = listInfo.getCnoList();
            if(CollectionUtils.isEmpty(cnoList)){
                failList = null;
            }else {
                Iterator<FeeOnOffInfo> iterator = failList.iterator();
                while (iterator.hasNext()){
                    if(!cnoList.contains(iterator.next().getCno())){
                        iterator.remove();
                    }
                }
            }
        }

        listInfo.setFailList(failList);
    }
    private Integer generateInfo(String uuid,HttpSession session, CstOnOffGetQueryNewParam realTimeDataParam) {
        List<String> customerNoList = realTimeDataParam.getCustomerNoList();
        List<String> deviceNoList = realTimeDataParam.getDeviceNoList();
        List nolist = null;
        if (!CollectionUtils.isEmpty(realTimeDataParam.getCustomerNoList())){
            List list= Lists.newArrayList();
            for (String customerNo : customerNoList) {
                Impot impot=new Impot();
                impot.setCreateTime(new Date());
                impot.setDataType(2);
                impot.setImpotBatch(uuid);
                impot.setSearchNo(customerNo);
                impot.setIsSearchChild(realTimeDataParam.getIsSearchChild());
                list.add(impot);
            }
            nolist=list;
        }
        if (!CollectionUtils.isEmpty(deviceNoList)){
            List list= Lists.newArrayList();
            for (String s : deviceNoList) {
                Impot impot=new Impot();
                impot.setCreateTime(new Date());
                impot.setDataType(1);
                impot.setImpotBatch(uuid);
                String cNo = CNoUtil.CreateCNo(realTimeDataParam.getDeviceType(), s);
                impot.setSearchNo(cNo);
                impot.setIsSearchChild(realTimeDataParam.getIsSearchChild());
                list.add(impot);
            }
            nolist=list;
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
        if(!CollectionUtils.isEmpty(nolist)){
            return generateFileService.generateRealTimeData(savePath, nolist);
        }else{
            return 0;
        }
    }

}
