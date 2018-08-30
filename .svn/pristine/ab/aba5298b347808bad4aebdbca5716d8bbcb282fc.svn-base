package cn.com.cdboost.collect.controller;


import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.dto.response.ReplenishDataDetailResult;
import cn.com.cdboost.collect.enums.Action;
import cn.com.cdboost.collect.enums.Auth;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.service.ReplenishService;
import cn.com.cdboost.collect.service.UserLogService;
import cn.com.cdboost.collect.service.UserService;
import cn.com.cdboost.collect.vo.PageResult;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author xzy
 * @desc 掌机补抄接口
 * @create 2017/9/15 0015
 **/
@Controller
@RequestMapping(value = "/replenish")
public class ReplenishController {

    @Autowired
    private ReplenishService replenishService;
    @Autowired
    private UserLogService userLogService;
    @Autowired
    private UserService userService;

    /**
     * 添加工单页面，补抄设备列表中，查询需要补抄的用户信息
     * @param session
     * @param queryParam
     * @return
     */
    @SystemControllerLog(description = "添加工单页面，补抄设备列表中，查询需要补抄的用户信息")
    @RequestMapping(value = "queryReplenishMeter", method = RequestMethod.POST)
    @ResponseBody
    public String queryReplenishMeter(HttpSession session, @Valid @RequestBody MeterSuppCstQueryParam queryParam){
        PageResult<List<MeterSuppCstValueText>> result = new PageResult<>();
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        MeterSuppCstQueryVo queryVo = new MeterSuppCstQueryVo();
        BeanUtils.copyProperties(queryParam,queryVo);
        queryVo.setUserId(currentUser.getId());
        List<MeterSuppCstValueText> valueTexts = replenishService.queryReplenishMeter(queryVo);
        result.setData(valueTexts);
        result.setTotal(queryVo.getRowCount());

        return JSON.toJSONString(result);
    }

    /**
     * 创建补抄工单
     * @param session
     * @param param
     * @return
     */
    @Auth(menuID=10001103L,actionID={1L})
    @SystemControllerLog(description = "创建补抄工单")
    @RequestMapping(value = "createReplenishWorkOrder", method = RequestMethod.POST)
    @ResponseBody
    public String createReplenishWorkOrder(HttpSession session, @Valid @RequestBody WorkOrderAddParam param){
        Result result = new Result("工单创建成功");
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        userLogService.create(currentUser.getId(), Action.REPLENISH.getActionId(),"掌机补抄","meters","","创建补抄工单", JSON.toJSONString(param));
        // 组织参数
        ReplenishWorkOrderStrJsonVo jsonVo = new ReplenishWorkOrderStrJsonVo();
        BeanUtils.copyProperties(param,jsonVo);
        String taskContent = param.getTaskContent();
        if (taskContent == null) {
            jsonVo.setTaskContent("");
        }
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        jsonVo.setStartTime(sdf.format(now));
        String endTime = param.getEndTime();
        jsonVo.setEndTime(endTime + " 23:59:59");
        replenishService.createReplenishWorkOrder(jsonVo, currentUser.getId());

        return JSON.toJSONString(result);
    }

    /**
     * 补抄工单列表查询
     * @param session
     * @param queryParam
     * @return
     */
    @Auth(menuID=10001103L,actionID={1L,2L,3L,4L})
    @SystemControllerLog(description = "补抄工单列表查询")
    @RequestMapping(value = "replenishWorkOrderList", method = RequestMethod.POST)
    @ResponseBody
    public String replenishWorkOrderList(HttpSession session, @Valid @RequestBody MeterSuppTaskGetQueryParam queryParam){
        PageResult<List<WorkOrderDetialDTO>> result = new PageResult<>();
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        MeterSuppTaskGetQueryVo queryVo = new MeterSuppTaskGetQueryVo();
        BeanUtils.copyProperties(queryParam,queryVo);
        queryVo.setUserId(currentUser.getId());
        List<WorkOrderDetialDTO> dtoList = replenishService.replenishWorkOrderList(queryVo);
        result.setData(dtoList);
        result.setTotal(queryVo.getRowCount());

        return JSON.toJSONString(result);
    }

    /**
     * 补抄数据详情
     * @param session
     * @param queryParam
     * @return
     */
    @SystemControllerLog(description = "补抄数据详情")
    @RequestMapping(value = "replenishDataDetail", method = RequestMethod.POST)
    @ResponseBody
    public String replenishDataDetail(HttpSession session, @Valid @RequestBody MeterSuppTaskDetailQueryParam queryParam){
        ReplenishDataDetailResult<List<ReplenishDataDTO>> result = new ReplenishDataDetailResult<>();
       /* LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        userLogService.create(currentUser.getId(), Action.REPLENISH.getActionId(),"掌机补抄","taskNo",queryParam.getTaskNo(),"补抄数据详情",JSON.toJSONString(queryParam));*/

        MeterSuppTaskDetailQueryVo queryVo = new MeterSuppTaskDetailQueryVo();
        BeanUtils.copyProperties(queryParam,queryVo);
        List<ReplenishDataDTO> dtoList = replenishService.replenishDataDetial(queryVo);
        result.setData(dtoList);
        Integer successCount = queryVo.getSuccessCount();
        result.setSuccessCount(successCount);
        Integer failCount = queryVo.getFailCount();
        result.setFailCount(failCount);
        Integer unfinishCount = queryVo.getUnfinishCount();
        result.setUnfinishCount(unfinishCount);
        Integer status = queryParam.getStatus();
        Integer errCode = queryParam.getErrCode();
        if (status == 1) {
            if (errCode == 0) {
                // 成功页签数量
                result.setTotal(Long.valueOf(successCount));
            } else {
                // 失败页签数量
                result.setTotal(Long.valueOf(failCount));
            }
        } else {
            // 请求中页签返回
            result.setTotal(Long.valueOf(unfinishCount));
        }

        return JSON.toJSONString(result);
    }

    /**
     * 查询工单执行人员信息
     * @param session
     * @param userName
     * @return
     */
    @SystemControllerLog(description = "查询工单执行人员信息")
    @RequestMapping(value = "queryUser", method = RequestMethod.POST)
    @ResponseBody
    public String queryUser(HttpSession session, @RequestParam String userName){
        Result<List<WorkRuntorNameOrgInfo>> result = new Result();
        if (StringUtils.isEmpty(userName)) {
            result.error("userName不能为空");
            return JSON.toJSONString(result);
        }
        List<WorkRuntorNameOrgInfo> nameOrgInfos = userService.queryUsersByUserName(userName);
        result.setData(nameOrgInfos);

        return JSON.toJSONString(result);
    }

    /**
     * 工单详情页面，派发状态页签数据查询
     * @param session
     * @param taskNo
     * @return
     */
    @SystemControllerLog(description = "工单详情页面，派发状态页签数据查询")
    @RequestMapping(value = "requestWorkOrderList", method = RequestMethod.POST)
    @ResponseBody
    public String requestWorkOrderList(HttpSession session, @RequestParam String taskNo){
        Result<List<MeterSuppQueryInfo>> result = new Result<>();
        if (StringUtils.isEmpty(taskNo)) {
            result.error("taskNo不能为空");
            return JSON.toJSONString(result);
        }
        /*LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        userLogService.create(currentUser.getId(), Action.REPLENISH.getActionId(),"掌机补抄","taskNo",taskNo,"工单请求列表["+taskNo+"]", taskNo);*/
        List<MeterSuppQueryInfo> meterSuppQueryInfos = replenishService.requestWorkOrderList(taskNo);
        result.setData(meterSuppQueryInfos);

        return JSON.toJSONString(result);
    }

    /**
     * 查询待补录数据
     * @param session
     * @param param
     * @return
     */
    @SystemControllerLog(description = "查询待补录数据")
    @RequestMapping(value = "queryData", method = RequestMethod.POST)
    @ResponseBody
    public String queryData(HttpSession session, @RequestBody MakeupDataParam param){
        Result<List<MakeupDataDto>> result = new Result<>();

        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        MakeupDataVo queryVo = new MakeupDataVo();
        BeanUtils.copyProperties(param,queryVo);
        queryVo.setUserId(currentUser.getId());

        //查询数据
        List<MakeupDataDto> MakeupDataDtos = replenishService.queryMakeupData(queryVo);
        result.setData(MakeupDataDtos);

        return JSON.toJSONString(result);
    }

    /**
     * 手动补录采集数据
     * @param session
     * @param param
     * @return
     */
    @SystemControllerLog(description = "手动补录采集数据")
    @RequestMapping(value = "manualRecordData", method = RequestMethod.POST)
    @ResponseBody
    public String manualRecordData(HttpSession session, @RequestBody CreateManualRecordParam param){
        Result result = new Result();
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        int flag = replenishService.manualRecordData(param,currentUser.getId());
        switch (flag){
            case 0:
                result.setMessage("保存失败");
                result.setCode(0);
                return JSON.toJSONString(result);
            case 1:
                userLogService.create(currentUser.getId(), Action.ADD.getActionId(),"手动补录采集数据","CreateManualRecordParam","","手动补录采集数据", JSON.toJSONString(param));
                result.setMessage("提交保存成功");
                result.setCode(1);
                return JSON.toJSONString(result);
            case 2:
                result.setMessage("数据解析失败");
                result.setCode(0);
                return JSON.toJSONString(result);
            case 3:
                result.setMessage("提交数据超长");
                result.setCode(0);
                return JSON.toJSONString(result);
            default:
                return JSON.toJSONString(result);
        }
    }
}
