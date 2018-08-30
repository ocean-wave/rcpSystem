package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.enums.Action;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.model.UserLog;
import cn.com.cdboost.collect.service.*;
import cn.com.cdboost.collect.util.StringUtil;
import cn.com.cdboost.collect.vo.PageResult;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static cn.com.cdboost.collect.util.DownLoadUtil.downExcel;


/**
 * 综合查询模块
 */
@Controller
@RequestMapping("/comprehensive")
public class ComprehensiveQueryController {
    private static final Logger logger = LoggerFactory.getLogger(RealTimeDataController.class);

    @Autowired
    private FeePayService feePayService;
    @Autowired
    private ChangeMeterService changeMeterService;
    @Autowired
    private FeeOnOffService feeOnOffService;
    @Autowired
    private MeterCollectDataService meterCollectDataService;
    @Autowired
    private CustomerDevMapService customerDevMapService;
    @Autowired
    private DeviceEventService deviceEventService;
    @Autowired
    private UserLogService userLogService;
    @Autowired
    private GenerateFileService generateFileService;
    @Autowired
    private CustomerInfoService customerInfoService;

    @SystemControllerLog(description = "统计查询模块，电量电费")
    @RequestMapping("/electDetail")
    @ResponseBody
    public String electDetail(@RequestParam String customerNo) {
        Result<ElectResponseParamDto> result = new Result<>();
        ElectResponseParamDto info = feePayService.electDetail(customerNo);
        result.setData(info);
        return JSON.toJSONString(result);
    }

    @SystemControllerLog(description = "统计查询模块，电量电费明细")
    @RequestMapping("/electDetailList")
    @ResponseBody
    public String electDetailList( @Valid @RequestBody ElectDetailParamDto queryParam) {
        PageResult<ElectDetailResponseDto> result = new PageResult<>();
        ElectDetailResponseDto info = feePayService.electDetailList(queryParam);
        result.setData(info);
        result.setTotal(queryParam.getTotal());
        return JSON.toJSONString(result);
    }

    @SystemControllerLog(description = "统计查询模块，综合查询-充值记录")
    @RequestMapping("/queryRechargeList")
    @ResponseBody
    public String queryRechargeList(HttpSession session, @Valid @RequestBody RechargeListQueryParam queryParam) {
        PageResult<FeePayRecordInfo> result = new PageResult<>();
        LoginUser currentUser = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        RechargeRecordQueryVo queryVo = new RechargeRecordQueryVo();
        BeanUtils.copyProperties(queryParam, queryVo);
        queryVo.setUserId(Long.valueOf(currentUser.getId()));
        queryVo.setSdate(queryParam.getStartDate());
        queryVo.setEdate(queryParam.getEndDate());
        FeePayRecordInfo info = feePayService.queryByProc(queryVo);
        result.setData(info);
        result.setTotal(queryVo.getTotal());
        return JSON.toJSONString(result);
    }

    @SystemControllerLog(description = "统计查询模块，综合查询-换表记录")
    @RequestMapping("/queryChangeMeterList")
    @ResponseBody
    public String queryChangeMeterList(HttpSession session, @Valid @RequestBody SingleBaseQueryParam queryParam) {
        PageResult<List<ChangeMeterInfo>> result = new PageResult<>();
        LoginUser currentUser = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);

        ChangeMeterListQueryVo queryVo = new ChangeMeterListQueryVo();
        BeanUtils.copyProperties(queryParam, queryVo);
        queryVo.setUserId(String.valueOf(currentUser.getId()));
        queryVo.setChangeDateStart(queryParam.getStartDate());
        queryVo.setChangeDateEnd(queryParam.getEndDate());

        List<ChangeMeterInfo> list = changeMeterService.queryChangeMeters(queryVo);
        result.setData(list);
        result.setTotal(queryVo.getTotal());
        return JSON.toJSONString(result);
    }

    @SystemControllerLog(description = "统计查询模块，综合查询-关断记录")
    @RequestMapping("/queryOnOffList")
    @ResponseBody
    public String queryOnOffList(@Valid @RequestBody SingleBaseQueryParam queryParam) {
        PageResult<List<FeeOnOffDetailInfo>> result = new PageResult<>();
        OnOffQueryVo queryVo = new OnOffQueryVo();
        BeanUtils.copyProperties(queryParam, queryVo);
        String startDate = queryParam.getStartDate();
        String endDate = queryParam.getEndDate();
        queryVo.setStartTime(startDate + " 00:00:00");
        queryVo.setEndTime(endDate + " 23:59:59");
        List<FeeOnOffDetailInfo> list = feeOnOffService.queryHistory4Single(queryVo);
        result.setData(list);
        result.setTotal(queryVo.getTotal());
        return JSON.toJSONString(result);
    }

    @SystemControllerLog(description = "统计查询模块，综合查询-抄表数据-查询某个用户下对应的设备信息")
    @RequestMapping("/queryDeviceInfo")
    @ResponseBody
    public String queryDeviceInfo(@RequestParam String customerNo, @RequestParam String deviceType) {
        Result<List<DeviceInfoResponse>> result = new Result<>();
        if (StringUtils.isEmpty(customerNo)) {
            result.error("customerNo不能为空字符串");
            return JSON.toJSONString(result);
        }

        if (StringUtils.isEmpty(deviceType)) {
            result.error("deviceType不能为空字符串");
            return JSON.toJSONString(result);
        }
        List<DeviceInfoResponse> dataList = customerDevMapService.queryDeviceInfo(customerNo, deviceType);
        result.setData(dataList);
        return JSON.toJSONString(result);
    }

    @SystemControllerLog(description = "统计查询模块，综合查询-抄表数据")
    @RequestMapping("/queryCollectDataList")
    @ResponseBody
    public String queryCollectDataList(@Valid @RequestBody SingleBaseQueryParam queryParam) {
        Result<CollectDataPerDay> result = new Result<>();
        CollectDataForPerDayQueryVo queryVo = new CollectDataForPerDayQueryVo();
        BeanUtils.copyProperties(queryParam, queryVo);
        queryVo.setsTime(queryParam.getStartDate());
        queryVo.seteTime(queryParam.getEndDate());

        CollectDataPerDay dataForDay = meterCollectDataService.getCollectDataForDay(queryVo);
        result.setData(dataForDay);
        return JSON.toJSONString(result);
    }


    @SystemControllerLog(description = "统计查询模块, 综合查询-用电量柱状图查询")
    @RequestMapping("/queryElechart")
    @ResponseBody
    public String queryElechart(HttpSession session, @Valid @RequestBody CustomElechartQueryParam queryParam) {
        LoginUser loginUser= (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        PageResult<Map> result = new PageResult<>();
        ElecDataVo elecDataVo = new ElecDataVo();
        BeanUtils.copyProperties(queryParam, elecDataVo);
        if (!StringUtil.isEmpty(elecDataVo.getSortOrder())) {
            if ("desc".equals(elecDataVo.getSortOrder())) {

                elecDataVo.setSortOrder1(0);
            } else {
                elecDataVo.setSortOrder1(1);
            }
        }
        if (!StringUtil.isEmpty(elecDataVo.getTransformerNo())) {
            if ("01".equals(elecDataVo.getTransformerNo())) {

                elecDataVo.setTransformerNo("0");
            } else {
                elecDataVo.setTransformerNo("1");
            }
        }
        elecDataVo.setUserid(Long.valueOf(loginUser.getId()));
        Map customElecData = meterCollectDataService.getCustomElechart(elecDataVo);
        result.setData(customElecData);
        result.setTotal(elecDataVo.getTotal());
        return JSON.toJSONString(result);
    }

    @SystemControllerLog(description = "统计查询模块, 综合查询-客户用电量列表查询")
    @RequestMapping("/queryElecdata")
    @ResponseBody
    public String queryElecData( HttpSession session,@Valid @RequestBody CustomElecdataQueryParam queryParam) {
        LoginUser loginUser= (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        PageResult<List<CustomElecDetailInfo>> result = new PageResult<>();
        ElecDataVo elecDataVo = new ElecDataVo();
        BeanUtils.copyProperties(queryParam, elecDataVo);
        if (!StringUtil.isEmpty(elecDataVo.getSortOrder())) {
            if ("desc".equals(elecDataVo.getSortOrder())) {

                elecDataVo.setSortOrder1(0);
            } else {
                elecDataVo.setSortOrder1(1);
            }
        }
        elecDataVo.setUserid(Long.valueOf(loginUser.getId()));
        List customElecData = meterCollectDataService.getCustomEledata(elecDataVo);
        result.setData(customElecData);
        result.setTotal(elecDataVo.getTotal());
        return JSON.toJSONString(result);
    }

    @SystemControllerLog(description = "统计查询模块, 综合查询-异常记录查询")
    @RequestMapping("/queryExceptionList")
    @ResponseBody
    public String queryExceptionList(HttpSession session, @Valid @RequestBody QueryEventRequestParam queryParam) {
        LoginUser currentUser = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        PageResult<List<QueryEventDTO>> result = new PageResult<>();
        QueryEventVo qnoffqueryvo = new QueryEventVo();
        BeanUtils.copyProperties(queryParam, qnoffqueryvo);
        String[] split = qnoffqueryvo.getPermissionId().split(",");
        qnoffqueryvo.setPermissionIdlist(split);
        qnoffqueryvo.setId(currentUser.getId());
        List<QueryEventDTO> deviceEvent = deviceEventService.getDeviceEvent(qnoffqueryvo);
        result.setData(deviceEvent);
        result.setTotal(qnoffqueryvo.getRowCount());
        return JSON.toJSONStringWithDateFormat(result, "yyyy-MM-dd HH:mm:ss");
    }


    @SystemControllerLog(description = "统计查询模块, 异常事件更新")
    @RequestMapping("/DeviceEventUpdate")
    @ResponseBody
    public String deviceEventUpdate(HttpSession session, @Valid @RequestBody DeviceEventUpdateParam queryParam) {
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        userLogService.create(currentUser.getId(), Action.MODIFY.getActionId(),"异常事件","","","处理异常事件",JSON.toJSONString(queryParam));
        PageResult<String> result = new PageResult<>();
        QueryEventDTO queryEventVo = new QueryEventDTO();
        BeanUtils.copyProperties(queryParam, queryEventVo);
        queryEventVo.setAlarmDealTime(new Date());
        int flag = deviceEventService.updateDeviceEvent(queryEventVo);
        if (flag == 1) {
            result.setMessage("更新成功");
        } else {
            result.error("更新失败");
        }
        result.setTotal((long) flag);
        return JSON.toJSONString(result);
    }

    @SystemControllerLog(description = "统计查询模块, 异常事件统计")
    @RequestMapping("/queryExcAccountList")
    @ResponseBody
    public String queryExcAccountList(HttpSession httpSession,
                                      @RequestParam String permissionId,
                                      @RequestParam String startDate,
                                      @RequestParam String endDate,
                                      @RequestParam String deviceType
        ) {
        LoginUser loginUser= (LoginUser) httpSession.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        PageResult<ExcAccountDTO> result = new PageResult<>();
        if (StringUtils.isEmpty(startDate)) {
            result.error("startDate不能为空");
            return JSON.toJSONString(result);
        }
        if (StringUtils.isEmpty(endDate)) {
            result.error("endDate不能为空");
            return JSON.toJSONString(result);
        }
        if(StringUtils.isEmpty(permissionId)){
            result.error("permissionId不能为空");
            return JSON.toJSONString(result);
        }
        ExcAccountDTO excAccountDTO = deviceEventService.queryExcAccountList(String.valueOf(loginUser.getId()),permissionId,startDate, endDate,deviceType);
        result.setTotal((long) excAccountDTO.getList().size());
        result.setData(excAccountDTO);
        return JSON.toJSONString(result);
    }

    @SystemControllerLog(description = "统计查询模块, 异常事件统计下载excel")
    @RequestMapping("/queryExcAccountExcel")
    public void queryExcAccountExcel(
            @RequestParam String permissionId, HttpServletResponse response, HttpSession session,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam String deviceType
    ) throws Exception {
        LoginUser currentUser = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        Map map= Maps.newHashMap();
        map.put("eventLevel",permissionId); map.put("startDate",startDate); map.put("endDate",endDate); map.put("deviceType",deviceType);
        userLogService.create(currentUser.getId(), Action.DOWNLOAD.getActionId(), "综合查询", "list", "", "下载异常事件统计列表", JSON.toJSONString(map));
        ExcAccountDTO excAccountDTO = deviceEventService.queryExcAccountList(String.valueOf(currentUser.getId()),permissionId,startDate, endDate,deviceType);
        XSSFWorkbook workBook = deviceEventService.downloadExcelDeviceList("异常事件文档", excAccountDTO.getList(),excAccountDTO);
        //通过Response把数据以Excel格式保存
        response.reset();
        //设置response流信息的头类型，MIME码
        response.setContentType("application/vnd.ms-excel");
        ServletOutputStream out;
        response.addHeader("Content-Disposition", "attachment;filename=\""
                + new String(("导出表" + System.currentTimeMillis() + ".xls").getBytes("utf-8"),
                "ISO8859_1") + "\"");
        //创建输出流对象
        out = response.getOutputStream();
        //将创建的Excel对象利用二进制流的形式强制输出到客户端去
        workBook.write(out);
        //强制将数据从内存中保存
        out.flush();
        out.close();

    }
    @SystemControllerLog(description = "统计查询模块, 日志查询")
    @RequestMapping("/queryCustomerLog")
    @ResponseBody
    public String queryCustomerLog(
           @Valid @RequestBody  CustomerLogParam customerLogParam
    ) {
        PageResult< List<UserLog>> result=new PageResult<>();
        PageInfo<UserLog> userLogs = userLogService.queryUserLog(customerLogParam);
        result.setData(userLogs.getList());
        result.setTotal(userLogs.getTotal());
        return  JSON.toJSONStringWithDateFormat(result,"yyyy-MM-dd HH:mm:ss");
    }

}
