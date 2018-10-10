package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.ChargerDeviceVo;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.service.ChargingDeviceService;
import cn.com.cdboost.collect.service.ChargingProjectService;
import cn.com.cdboost.collect.service.RedisService;
import cn.com.cdboost.collect.vo.PageResult;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

/**
 * 充电桩首页统计管理类
 */
@Controller
@RequestMapping(value = "/firstPage")
public class ChargerFirstPageController {
    private static final Logger logger = LoggerFactory.getLogger(ChargerFirstPageController.class);

    @Autowired
    private ChargingProjectService chargingProjectService;
    @Autowired
    private ChargingDeviceService chargingDeviceService;
    @Autowired
    private RedisService redisService;

    @SystemControllerLog(description = "商户信息查询")
    @RequestMapping(value = "/queryMerchantCount")
    @ResponseBody
    public String queryMerchantCount(HttpSession session){
        LoginUser loginUser = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        Result result=new Result();
        MerchantCountDto merchantCountDto = chargingProjectService.queryMerchantCount(loginUser.getId());
        result.setData(merchantCountDto);
        return JSON.toJSONString(result);
    }
    @SystemControllerLog(description = "近两日数据对比")
    @RequestMapping(value = "/queryCompareDataCount")
    @ResponseBody
    public String queryCompareDataCount(HttpSession session){
        LoginUser loginUser = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        Result result=new Result();
        CompareDataCountInfo compareDataCountInfo = chargingProjectService.queryCompareDataCount(loginUser.getId());
        result.setData(compareDataCountInfo);
        return JSON.toJSONString(result);
    }
    @SystemControllerLog(description = "设备使用状态")
    @RequestMapping(value = "/queryDeviceStateCount")
    @ResponseBody
    public String queryDeviceStateCount(HttpSession session){
        LoginUser loginUser = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        //查询用户所属组织
        Set<Long> dataOrgNos = redisService.queryUserOrgNoByUserId(loginUser.getId());
        List orgNoList = Lists.newArrayList(dataOrgNos);
        Result result=new Result();
        ChargerDeviceVo queryVo = new ChargerDeviceVo();
        queryVo.setOrgNoList(orgNoList);
        ChargingCountByRunState count = chargingDeviceService.monitorDeviceCount(queryVo);
        result.setData(count);
        return JSON.toJSONString(result);
    }
    @SystemControllerLog(description = "累计数值统计")
    @RequestMapping(value = "/queryEnergyCount")
    @ResponseBody
    public String queryEnergyCount(HttpSession session){
        LoginUser loginUser = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        PageResult result=new PageResult();
        ChargerEnergyCountInfo info = chargingProjectService.queryEnergyCount(loginUser.getId());
        result.setData(info);
        return JSON.toJSONString(result);
    }
    @SystemControllerLog(description = "统计曲线")
    @RequestMapping(value = "/queryCountCurve")
    @ResponseBody
    public String queryCountCurve(HttpSession session, @RequestParam String startDate,@RequestParam String endDate){
        LoginUser loginUser = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        PageResult result=new PageResult();
        FirstCurveInfo info = chargingProjectService.queryCountCurve(loginUser.getId(),startDate,endDate);
        result.setData(info);
        return JSON.toJSONString(result);
    }
    @SystemControllerLog(description = "站点列表排行")
    @RequestMapping(value = "/queryProjectCount")
    @ResponseBody
    public String queryProjectCount(HttpSession session){
        LoginUser loginUser = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        PageResult result=new PageResult();
        List<FirstProjectCountDto> dtos = chargingProjectService.queryProjectCount(loginUser.getId());
        result.setData(dtos);
        return JSON.toJSONString(result);
    }
}
