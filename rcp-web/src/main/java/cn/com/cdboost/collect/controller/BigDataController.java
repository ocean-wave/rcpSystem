package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.dto.LoginUser;
import cn.com.cdboost.collect.dto.param.QueryBaseInformationDto;
import cn.com.cdboost.collect.dto.param.QueryCurrentMonthDto;
import cn.com.cdboost.collect.dto.response.QueryCategoryInformationInfo;
import cn.com.cdboost.collect.dto.response.QueryCurrentMonthInfo;
import cn.com.cdboost.collect.dto.response.QueryElectTopListInfo;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.service.BigDataService;
import cn.com.cdboost.collect.vo.PageResult;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/bigData")
public class BigDataController {
    @Autowired
    BigDataService bigDataService;

    @RequestMapping(value = "queryBaseInformation")
    @SystemControllerLog(description = "查询基本信息")
    @ResponseBody
    public String queryBaseInformation(HttpSession session) {
        LoginUser loginUser = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        Result result = new Result<>();
        QueryBaseInformationDto queryBaseInformationDto = bigDataService.queryBaseInformation(loginUser.getId());
        result.setData(queryBaseInformationDto);
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "queryElectTopList")
    @SystemControllerLog(description = "获取用电排名列表")
    @ResponseBody
    public String queryElectTopList(HttpSession session) {
        LoginUser loginUser = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        PageResult<List<QueryElectTopListInfo>> result = new PageResult<>();
        List<QueryElectTopListInfo> queryElectTopListInfo = bigDataService.queryElectTopList(loginUser.getId());
        result.setData(queryElectTopListInfo);
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "queryCategoryInformation")
    @SystemControllerLog(description = "获取分项用能情况")
    @ResponseBody
    public String queryCategoryInformation(HttpSession session, @RequestBody @Valid QueryCurrentMonthDto queryCurrentMonthDto) {
        LoginUser loginUser = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        Result<QueryCategoryInformationInfo> result = new Result<>();
        queryCurrentMonthDto.setId(loginUser.getId());
        QueryCategoryInformationInfo queryCategoryInformationInfo = bigDataService.queryCategoryInformation(queryCurrentMonthDto);
        result.setData(queryCategoryInformationInfo);
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "queryCurrentMonth")
    @SystemControllerLog(description = "获取本月用能分析图表")
    @ResponseBody
    public String queryCurrentMonth(HttpSession session, @RequestBody @Valid QueryCurrentMonthDto queryCurrentMonthDto) {
        LoginUser loginUser = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        Result<QueryCurrentMonthInfo> result = new Result<>();
        queryCurrentMonthDto.setId(loginUser.getId());
        QueryCurrentMonthInfo queryCurrentMonthInfo = bigDataService.queryCurrentMonth(queryCurrentMonthDto);
        result.setData(queryCurrentMonthInfo);
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "queryCurrentYear")
    @SystemControllerLog(description = "获取本年的用能分析图表")
    @ResponseBody
    public String queryCurrentYear(HttpSession session, @RequestBody @Valid QueryCurrentMonthDto queryCurrentMonthDto) {
        LoginUser loginUser = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        Result<QueryCurrentMonthInfo> result = new Result<>();
        queryCurrentMonthDto.setId(loginUser.getId());
        QueryCurrentMonthInfo queryCurrentYear = bigDataService.queryCurrentYear(queryCurrentMonthDto);
        result.setData(queryCurrentYear);
        return JSON.toJSONString(result);
    }
}