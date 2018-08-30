package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.dao.ReportFormMapper;
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
import com.example.clienttest.client.ResultInfo;
import org.apache.http.HttpResponse;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static cn.com.cdboost.collect.util.DateUtil.getEndDate;

/**
 * 统计报表模块
 */
@Controller
@RequestMapping("/reportform")
public class ReportformController {

    @Autowired
    private UserLogService userLogService;
    @Autowired
    private ReportFormService reportFormService;
    @SystemControllerLog(description = "统计报表模块，充值汇总")
    @RequestMapping("/chargeSummary")
    public void queryRecharge(HttpSession session, HttpServletResponse response,
                                  @RequestParam String startDate,
                                  @RequestParam String endDate,
                                  @RequestParam Integer pageSize,
                                  @RequestParam Integer pageNumber) throws IOException {
        ChargeSummaryParam queryParam=new ChargeSummaryParam();
        queryParam.setStartDate(startDate);queryParam.setEndDate(endDate);
        queryParam.setPageSize(pageSize);queryParam.setPageNumber(pageNumber);
        LoginUser currentUser = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        userLogService.create(currentUser.getId(), Action.DOWNLOAD.getActionId(), "统计报表模块", "", "", "充值汇总下载", JSON.toJSONString(queryParam));
        queryParam.setId(Long.valueOf(currentUser.getId()));
        XSSFWorkbook workBook = reportFormService.queryCharge(queryParam);
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
    @SystemControllerLog(description = "统计报表模块，充值汇总显示")
    @RequestMapping("/chargeSummaryList")
    @ResponseBody
    public String queryRechargeList(HttpSession session,
                                  @RequestParam String startDate,
                                  @RequestParam String endDate,
                                  @RequestParam Integer pageSize,
                                  @RequestParam Integer pageNumber)  {
        ChargeSummaryParam queryParam=new ChargeSummaryParam();
        PageResult<List<ChargeSummaryDTO>>result=new PageResult();
        queryParam.setStartDate(startDate);queryParam.setEndDate(endDate);
        queryParam.setPageSize(pageSize);queryParam.setPageNumber(pageNumber);
        LoginUser currentUser = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        queryParam.setId(Long.valueOf(currentUser.getId()));
        List<ChargeSummaryDTO> chargeSummaryDTOS = reportFormService.queryChargeList(queryParam);
        result.setData(chargeSummaryDTOS);
        result.setTotal(queryParam.getRowCount());
        //通过Response把数据以Excel格式保存

      return JSON.toJSONString(result);
    }
    @SystemControllerLog(description = "统计报表模块，充值明细")
    @RequestMapping("/chargeDetailList")
    @ResponseBody
    public String queryChangeMeterList(HttpSession session,
                                     @RequestParam String startDate,
                                     @RequestParam String endDate,
                                     @RequestParam Integer pageSize,
                                     @RequestParam Integer pageNumber
                                     )  {
        ChargeSummaryParam queryParam=new ChargeSummaryParam();
        PageResult<List<ChargeDetailDTO>>result=new PageResult();
        queryParam.setStartDate(startDate);queryParam.setEndDate(endDate);
        queryParam.setPageSize(pageSize);queryParam.setPageNumber(pageNumber);
        LoginUser currentUser = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        queryParam.setId(Long.valueOf(currentUser.getId()));
        List<ChargeDetailDTO> chargeSummaryDTOS = reportFormService.queryDetailList(queryParam);
        result.setData(chargeSummaryDTOS);
        result.setTotal(queryParam.getRowCount());
        return  JSON.toJSONString(result);
    }

    @SystemControllerLog(description = "统计报表模块，充值明细")
    @RequestMapping("/chargeDetail")
    public void queryChangeMeter(HttpSession session,HttpServletResponse response,
                                     @RequestParam String startDate,
                                     @RequestParam String endDate,
                                     @RequestParam Integer pageSize,
                                     @RequestParam Integer pageNumber
    ) throws IOException, ParseException {
        ChargeSummaryParam queryParam=new ChargeSummaryParam();
        queryParam.setStartDate(startDate);queryParam.setEndDate(endDate);
        queryParam.setPageSize(pageSize);queryParam.setPageNumber(pageNumber);
        LoginUser currentUser = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        userLogService.create(currentUser.getId(), Action.DOWNLOAD.getActionId(), "统计报表模块", "", "", "充值明细下载", JSON.toJSONString(queryParam));
        queryParam.setId(Long.valueOf(currentUser.getId()));
        XSSFWorkbook workBook = reportFormService.queryDetail(queryParam);
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
}
