package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.dto.LoginUser;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.service.BigDataService;
import cn.com.cdboost.collect.service.GenerateFileService;
import cn.com.cdboost.collect.vo.PageResult;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static cn.com.cdboost.collect.util.DownLoadUtil.downExcel;

@Controller
@RequestMapping(value = "/lineLoss")
public class LineLossController {

    @Autowired
    BigDataService bigDataService;
    @Autowired
    GenerateFileService generateFileService;
    @RequestMapping(value = "/queryResidentialList")
    @SystemControllerLog(description = "小区列表查询")
    @ResponseBody
    public String queryResidentialList(HttpSession session,@RequestBody @Valid QueryResidentialListDto queryResidentialListDto
    ) {
        PageResult result=new PageResult();
        LoginUser loginUser = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        queryResidentialListDto.setId(loginUser.getId());
        List<QueryResidentialListInfo> queryResidentialListInfos = bigDataService.queryResidentialList(queryResidentialListDto);
        result.setData(queryResidentialListInfos);
        result.setTotal(queryResidentialListDto.getTotal());
        return JSON.toJSONString(result);
    }
    @RequestMapping(value = "/queryResidentialListDownload")
    @SystemControllerLog(description = "小区列表查询下载")
    public void queryResidentialListDownload(HttpServletResponse session,QueryResidentialListDto queryResidentialListDto
    ) throws IOException {
        List<QueryResidentialListInfo> queryLineLostListInfos = bigDataService.queryResidentialList(queryResidentialListDto);
        XSSFWorkbook workBook = generateFileService.queryResidentialListDownload("小区线损数据列表",queryLineLostListInfos);
        downExcel(session,workBook);
    }
    @RequestMapping(value = "queryLineLostList")
    @SystemControllerLog(description = "线损数据列表")
    @ResponseBody
    public String queryLineLostList(@RequestBody @Valid QueryLineLostListDto queryLineLostListDto) {
        PageResult<List<QueryLineLostListInfo>> result = new PageResult<>();
        List<QueryLineLostListInfo> queryLineLostListInfos = bigDataService.queryLineLostList(queryLineLostListDto);
        result.setData(queryLineLostListInfos);
        result.setTotal(queryLineLostListDto.getTotal());
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "queryLineLostListDownload")
    @SystemControllerLog(description = "线损数据列表下载")
    public void queryLineLostListDownload(HttpServletResponse session, QueryLineLostListDto queryLineLostListDto) throws IOException {
        List<QueryLineLostListInfo> queryLineLostListInfos = bigDataService.queryLineLostList(queryLineLostListDto);
        XSSFWorkbook workBook = generateFileService.queryLineLostListDownload("台区线损数据列表",queryLineLostListInfos);
        downExcel(session,workBook);
    }

    @RequestMapping(value = "queryDayLost")
    @SystemControllerLog(description = "日损耗")
    @ResponseBody
    public String queryDayLost(@RequestBody @Valid QueryDayLostDto queryDayLostDto
            ) {
        PageResult result=new PageResult();
        List<QueryDayLostInfo> queryDayLostInfo= bigDataService.queryDayLost(queryDayLostDto);
        result.setData(queryDayLostInfo);
        result.setTotal(queryDayLostDto.getTotal());
        return JSON.toJSONString(result);
    }
    @RequestMapping(value = "queryDayLostDownload")
    @SystemControllerLog(description = "日损耗下载")
    @ResponseBody
    public void queryDayLostDownload(HttpServletResponse session,
                                     QueryDayLostDto queryDayLostDto
    ) throws IOException {
        List<QueryDayLostInfo> queryDayLostInfo= bigDataService.queryDayLost(queryDayLostDto);
        XSSFWorkbook workbook=generateFileService.queryDayLostDownload("日损耗",queryDayLostInfo);
        downExcel(session,workbook);
    }
    @RequestMapping(value = "querySupplyElectDetail")
    @SystemControllerLog(description = "供入电量明细")
    @ResponseBody
    public String querySupplyElectDetail(@RequestBody @Valid QueryDayLostDto queryDayLostDto) {
        PageResult result = new PageResult<>();
        List<QuerySupplyElectDetailInfo> querySupplyElectDetailInfos = bigDataService.querySupplyElectDetail(queryDayLostDto);
        result.setData(querySupplyElectDetailInfos);
        result.setTotal(queryDayLostDto.getTotal());
        return JSON.toJSONString(result);
    }
    @RequestMapping(value = "querySupplyElectDetailDownload")
    @SystemControllerLog(description = "供入电量明细下载")
    public void querySupplyElectDetailDownload(HttpServletResponse session,
                                               QueryDayLostDto queryDayLostDto
    ) throws IOException {
        List<QuerySupplyElectDetailInfo> querySupplyElectDetailInfos= bigDataService.querySupplyElectDetail(queryDayLostDto);
        XSSFWorkbook workbook=generateFileService.querySupplyElectDetailDownload("供入电量明细",querySupplyElectDetailInfos);
        downExcel(session,workbook);
    }

    @RequestMapping(value = "queryConfessElectDetail")
    @SystemControllerLog(description = "供出电量明细")
    @ResponseBody
    public String queryConfessElectDetail(@RequestBody @Valid  QueryDayLostDto queryDayLostDto) {
        PageResult result = new PageResult<>();
        List<QueryConfessElectDetailInfo> queryConfessElectDetailInfos = bigDataService.queryConfessElectDetail(queryDayLostDto);
        result.setData(queryConfessElectDetailInfos);
        result.setTotal(queryDayLostDto.getTotal());
        return JSON.toJSONString(result);
    }
    @RequestMapping(value = "queryConfessElectDetailDownload")
    @SystemControllerLog(description = "供出电量明细下载")
    public void queryConfessElectDetailDownload(HttpServletResponse session,QueryDayLostDto queryDayLostDto) throws IOException {
        List<QueryConfessElectDetailInfo> queryConfessElectDetailInfos = bigDataService.queryConfessElectDetail(queryDayLostDto);
        XSSFWorkbook xssfWorkbook=generateFileService.queryConfessElectDetailDownload("供出电量明细",queryConfessElectDetailInfos);
        downExcel(session,xssfWorkbook);
    }
    @RequestMapping(value = "queryDayCollectSucceedRate")
    @SystemControllerLog(description = "日抄表成功率")
    @ResponseBody
    public String queryDayCollectSucceedRate(@RequestBody @Valid  QueryDayLostDto queryDayLostDto) {
        PageResult result = new PageResult<>();
        List<QueryDayCollectSucceedRateInfo> queryConfessElectDetailInfos = bigDataService.queryDayCollectSucceedRate(queryDayLostDto);
        result.setData(queryConfessElectDetailInfos);
        result.setTotal(queryDayLostDto.getTotal());
        return JSON.toJSONString(result);
    }
    @RequestMapping(value = "queryDayCollectSucceedRateDownload")
    @SystemControllerLog(description = "日抄表成功率下载")
    public void queryDayCollectSucceedRateDownload(HttpServletResponse session, QueryDayLostDto queryDayLostDto) throws IOException {
        Result result = new Result<>();
        List<QueryDayCollectSucceedRateInfo> queryConfessElectDetailInfos = bigDataService.queryDayCollectSucceedRate(queryDayLostDto);
        result.setData(queryConfessElectDetailInfos);
        XSSFWorkbook xssfWorkbook=generateFileService.queryDayCollectSucceedRateDownload("日抄表成功率",queryConfessElectDetailInfos);
        downExcel(session,xssfWorkbook);
    }
}