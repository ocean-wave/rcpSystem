package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.dto.ImportantCurveDerailDTO;
import cn.com.cdboost.collect.dto.ImportantCurveInfo;
import cn.com.cdboost.collect.dto.ImportantCustomerInfo;
import cn.com.cdboost.collect.dto.LoginUser;
import cn.com.cdboost.collect.dto.param.*;

import cn.com.cdboost.collect.enums.Action;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.model.DeviceMeterParam;
import cn.com.cdboost.collect.model.User;
import cn.com.cdboost.collect.model.UserLog;
import cn.com.cdboost.collect.service.DeviceMeterParamService;
import cn.com.cdboost.collect.service.GenerateFileService;
import cn.com.cdboost.collect.service.UserLogService;
import cn.com.cdboost.collect.util.CNoUtil;
import cn.com.cdboost.collect.util.StringUtil;
import cn.com.cdboost.collect.vo.PageResult;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.axis.transport.http.HTTPSender;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HTTP;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static cn.com.cdboost.collect.util.DateUtil.getEndDate;
import static cn.com.cdboost.collect.util.DateUtil.getStartDate;


/**
 * @author wt
 * @desc
 * @create in  2018/3/16
 **/
@Controller
@RequestMapping("/importantCustomer")
public class ImportantCustomerController {
    private static final Logger logger= LoggerFactory.getLogger(ImportantCustomerController.class);

    @Autowired
    private DeviceMeterParamService deviceMeterParamService;
    @Autowired
    private UserLogService userLogService;
    @Autowired
    private GenerateFileService generateFileService;

    @SystemControllerLog(description = "设置/取消重点用户")
    @RequestMapping("/setImportant")
    @ResponseBody
    public String setImportant(HttpSession session, @RequestParam String cno, @RequestParam Integer isImportant){
        LoginUser user= (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        Map map= Maps.newHashMap();
        map.put("cno",cno);map.put("isImportant",isImportant);

        DeviceMeterParam deviceMeterParam=new DeviceMeterParam();
        deviceMeterParam.setCno(cno);
        deviceMeterParam.setIsImportant(isImportant);
        Result<String> result=new Result();
        if(StringUtil.isEmpty(cno)){
            result.error("cno不能为空");
            return JSON.toJSONString(result);
        }
        if(isImportant==null){
            result.error("isImportant不能为空");
            return JSON.toJSONString(result);
        }

        int flag=deviceMeterParamService.updateSelectiveByCno(deviceMeterParam);
        if(flag==1){
            result.setMessage("设置成功");
            if(1==isImportant){
                userLogService.create(user.getId(), Action.ON_OFF.getActionId(),"客户档案","list", "", "设备["+ CNoUtil.getDeviceNoByCno(cno)+"]设置重点用户" , JSON.toJSONString(map));
            }else{
                userLogService.create(user.getId(), Action.ON_OFF.getActionId(),"客户档案","list", "", "设备["+CNoUtil.getDeviceNoByCno(cno)+"]取消重点用户" , JSON.toJSONString(map));
            }
           return JSON.toJSONString(result);
        }else {
            result.error("设置失败");
            return JSON.toJSONString(result);
        }
    }
    @SystemControllerLog(description = "查询重点用户档案")
    @RequestMapping("/queryImportant")
    @ResponseBody
    public String queryImportant(HttpSession session,@Valid @RequestBody ImportantParam importantParam){
        PageResult<List<ImportantCustomerInfo>> result=new PageResult<>();
        ImportantCustomerVo importantCustomerVo=new ImportantCustomerVo();
        BeanUtils.copyProperties(importantParam,importantCustomerVo);
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        Integer userId = currentUser.getId();
        importantCustomerVo.setCustomerId(userId.toString());
        List<ImportantCustomerInfo> importantCustomerInfos = deviceMeterParamService.queryImportantCustomer(importantCustomerVo);
        result.setTotal(importantCustomerVo.getRowCount());
        result.setData(importantCustomerInfos);
        return JSON.toJSONString(result);
    }
    @SystemControllerLog(description = "查询重点用户曲线数据")
    @RequestMapping("/queryImportantCurve")
    @ResponseBody
    public String queryImportantCurve(@Valid @RequestBody ImportantCurveParam importantParam)  {
        PageResult<List<ImportantCurveInfo>>result=new PageResult<>();
        ImportantCurveVo importantCustomerVo=new ImportantCurveVo();
        String Startdate=importantParam.getDataMark();
        String enddate=getEndDate(Startdate);
        importantParam.setStartTime( getStartDate(Startdate));
        importantParam.setEndTime(enddate);
        BeanUtils.copyProperties(importantParam,importantCustomerVo);
        List<ImportantCurveInfo> importantCustomerInfos = deviceMeterParamService.queryImportantCurve(importantCustomerVo);
        result.setData(importantCustomerInfos);
        return JSON.toJSONString(result);
    }

    @SystemControllerLog(description = "查询重点用户采集数据")
    @RequestMapping("/queryImpDetail")
    @ResponseBody
    public String queryImpDetail(@Valid @RequestBody ImportantCurveParam importantParam)  {
        Result<List<ImportantCurveDerailDTO>>result=new Result<>();
        ImportantCurveVo importantCustomerVo=new ImportantCurveVo();
        BeanUtils.copyProperties(importantParam,importantCustomerVo);
        String Startdate=importantParam.getDataMark();
        String enddate=getEndDate(Startdate);
        importantCustomerVo.setStartTime( getStartDate(Startdate));
        importantCustomerVo.setEndTime(enddate);
        List<ImportantCurveDerailDTO> importantCustomerInfos = deviceMeterParamService.queryImportantCollection(importantCustomerVo);
        result.setData(importantCustomerInfos);
        return JSON.toJSONString(result);
    }
    @SystemControllerLog(description = "查询重点用户日采集数据详细")
    @RequestMapping("/queryImpDetailByDay")
    @ResponseBody
    public String queryImpDetailByDay(@Valid @RequestBody ImportantDetailByDayParam importantParam){
        Result<List<ImportantCurveDerailDTO>>result=new Result<>();
        ImportantCurveVo importantCustomerVo=new ImportantCurveVo();
        BeanUtils.copyProperties(importantParam,importantCustomerVo);
        String Startdate=importantParam.getDataMark();
        String enddate=getEndDate(Startdate);
        importantCustomerVo.setStartTime( getStartDate(Startdate));
        importantCustomerVo.setEndTime(enddate);
        List<ImportantCurveDerailDTO> importantCustomerInfos = deviceMeterParamService.queryImportantCollection(importantCustomerVo);
        result.setData(importantCustomerInfos);
        return JSON.toJSONString(result);
    }
    @SystemControllerLog(description = "查询重点用户日采集数据详细")
    @RequestMapping("/downLoadImpDetailByDay")
    public void downLoadImpDetailByDay(HttpSession session, HttpServletResponse response,
                                       @RequestParam String customerNo,
                                       @RequestParam String meterUserNo,
                                       @RequestParam String dataMark,
                                       @RequestParam String model,
                                       @RequestParam String deviceType
                                       ) throws Exception {
        ImportantCurveVo importantCustomerVo=new ImportantCurveVo();
        importantCustomerVo.setCustomerNo(customerNo);
        importantCustomerVo.setMeterUserNo(meterUserNo);
        importantCustomerVo.setModel(model);
        importantCustomerVo.setDeviceType(deviceType);
        String Startdate=dataMark;
        String enddate=getEndDate(Startdate);
        importantCustomerVo.setStartTime( getStartDate(Startdate));
        importantCustomerVo.setEndTime(enddate);
        List<ImportantCurveDerailDTO> importantCustomerInfos = deviceMeterParamService.queryImportantCollection(importantCustomerVo);
        XSSFWorkbook workBook = generateFileService.generateImportantExcel("重点用户日采集用能详情", importantCustomerInfos);
        //通过Response把数据以Excel格式保存
        response.reset();
        //设置response流信息的头类型，MIME码
        response.setContentType("application/vnd.ms-excel");
        ServletOutputStream out;
        response.addHeader("Content-Disposition", "attachment;filename=\""
                + new String(("导出表" + System.currentTimeMillis() + ".xls").getBytes("utf-8"),
                "ISO8859_1") + "\"");
        //创建输出流对象
        out=response.getOutputStream();
        //将创建的Excel对象利用二进制流的形式强制输出到客户端去
        //强制将数据从内存中保存
        workBook.write(out);
        LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        userLogService.create(user.getId(), Action.DOWNLOAD.getActionId(),"重点用户","","","下载重点用户日采集用能详情列表", JSON.toJSONString(importantCustomerVo));
        out.flush();
        out.close();
    }
    @SystemControllerLog(description = "查询重点用户ABC三相电流、电压曲线数据")
    @RequestMapping("/queryImportantABC")
    @ResponseBody
    public String queryImportantABC(@Valid @RequestBody ImportantABCParam importantParam)  {
        Result<List<ImportantCurveInfo>>result=new Result<>();
        ImportantABCVo important =new ImportantABCVo();
        BeanUtils.copyProperties(importantParam,important);
        String Startdate=importantParam.getDataMark();
        String enddate=getEndDate(Startdate);
        important.setStartTime( getStartDate(Startdate));
        important.setEndTime(enddate);
        List<ImportantCurveInfo> importantCustomerInfos = deviceMeterParamService.queryImportantABC(important);
        result.setData(importantCustomerInfos);
        return JSON.toJSONString(result);
    }
}
