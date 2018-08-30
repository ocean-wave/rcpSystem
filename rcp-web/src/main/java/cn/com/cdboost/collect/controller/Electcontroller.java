package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.dto.LoginUser;
import cn.com.cdboost.collect.dto.param.DaySettlementParam;
import cn.com.cdboost.collect.dto.param.ElectConsumptionParam;
import cn.com.cdboost.collect.dto.param.ElectRecordInfo;
import cn.com.cdboost.collect.enums.Action;
import cn.com.cdboost.collect.enums.Auth;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.model.UserOrg;
import cn.com.cdboost.collect.service.*;
import cn.com.cdboost.collect.util.DownLoadUtil;
import cn.com.cdboost.collect.vo.PageResult;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Sets;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Set;

import static cn.com.cdboost.collect.util.DownLoadUtil.downExcel;

/**
 * 统计报表模块
 */
@Controller
public class Electcontroller {
    final static Logger LOGGER= LoggerFactory.getLogger(Electcontroller.class);
    @Autowired
    private CustomerDevMapService customerDevMapService;
    @Autowired
    private UserOrgService userOrgService;
    @Autowired
    private OrgService orgService;
    @Autowired
    private HttpSession session;
    @Autowired
    private GenerateFileService generateFileService;
    @Autowired
    private CustomerInfoService customerInfoService;
    @Autowired
    private UserLogService userLogService;
    /**
     * 电量电费查询
     * @param queryParam
     * @return
     */
    @SystemControllerLog(description = "电量电费查询")
    @RequestMapping(value = "/customerInfo/electConsumption")
    @ResponseBody
    public String electConsumption(HttpSession session, @Valid @RequestBody ElectConsumptionParam queryParam) {
        PageResult result=new PageResult();
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        long userId = currentUser.getId();
        queryParam.setId(userId);
        ElectRecordInfo electRecordInfo = customerInfoService.queryelectConsumption(queryParam);
        result.setData(electRecordInfo);
        result.setTotal(queryParam.getTotal());
        return JSON.toJSONString(result);
    }
    @SystemControllerLog(description = "电量电费查询下载")
    @RequestMapping("/comprehensive/downelectDetailList")
    public void downelectDetailList(ElectConsumptionParam queryParam, HttpSession session, HttpServletResponse response) {
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        queryParam.setId(currentUser.getId());
        ElectRecordInfo electRecordInfo = customerInfoService.queryelectConsumption(queryParam);
        XSSFWorkbook workBook = generateFileService.generateelectDetailExcel("电量电费明细", electRecordInfo);
        userLogService.create(currentUser.getId(), Action.OPEN_ICCARD.getActionId(),"实时数据下载","customerNo","","下载"+queryParam.getStartDate()+"至"+queryParam.getEndDate()+"电量电费明细详情" , JSON.toJSONString(queryParam));
        try {
            DownLoadUtil.downExcel(response, workBook);
        } catch (IOException e) {
            LOGGER.error("系统异常：",e);
        }
    }
    @SystemControllerLog(description = "客户电能电费日结算")
    @Auth(menuID = {100056L},actionID = {33L})
    @RequestMapping("/report/queryDaySettlement")
    @ResponseBody
    public String queryDaySettlement(@RequestBody DaySettlementParam daySettlementParam)  {
        PageResult result=new PageResult();
        List<Long> orgNoList = queryUserOrg();
        PageInfo pageInfo = customerDevMapService.queryDaySettlement(daySettlementParam,orgNoList);
        result.setData(pageInfo.getList());
        result.setTotal(pageInfo.getTotal());
        return JSON.toJSONString(result);
    }
    @SystemControllerLog(description = "客户电能电费日结算下载")
    @RequestMapping("/report/queryDaySettlementDown")
    public void queryDaySettlementDown(HttpServletResponse session,  DaySettlementParam daySettlementParam) throws IOException {
        List<Long> orgNoList = queryUserOrg();
        PageInfo pageInfo = customerDevMapService.queryDaySettlement(daySettlementParam,orgNoList);
        XSSFWorkbook workBook = generateFileService.queryDaySettlementDown("电能电费日结算", pageInfo.getList());
        downExcel(session,workBook);
    }
    @SystemControllerLog(description = "客户电能电费月结算")
    @Auth(menuID = {100056L},actionID = {34L})
    @RequestMapping("/report/queryMonthSettlement")
    @ResponseBody
    public String queryMonthSettlement(@RequestBody DaySettlementParam daySettlementParam) throws ParseException {
        PageResult result=new PageResult();
        List<Long> orgNoList = queryUserOrg();
        PageInfo pageInfo = customerDevMapService.queryMonthSettlement(daySettlementParam,orgNoList);
        result.setData(pageInfo.getList());
        result.setTotal(pageInfo.getTotal());
        return JSON.toJSONString(result);
    }
    @SystemControllerLog(description = "客户电能电费月结算下载")
    @RequestMapping("/report/queryMonthSettlementDown")
    public void queryMonthSettlementDown(HttpServletResponse session,  DaySettlementParam daySettlementParam) throws IOException, ParseException {
        List<Long> orgNoList = queryUserOrg();
        PageInfo pageInfo = customerDevMapService.queryMonthSettlement(daySettlementParam,orgNoList);
        XSSFWorkbook workBook = generateFileService.queryDaySettlementDown("电能电费月结算", pageInfo.getList());
        downExcel(session,workBook);
    }
    @SystemControllerLog(description = "根据厂家查询表计型号参数")
    @RequestMapping("/meterConfig/queryByFactory")
    @ResponseBody
    public String queryByFactory(@RequestParam String meterFactory, @RequestParam String deviceType)  {
        PageResult result=new PageResult();
        List pageInfo = customerDevMapService.queryByFactory(meterFactory,deviceType);
        result.setData(pageInfo);
        result.setTotal(Long.valueOf(pageInfo.size()));
        return JSON.toJSONString(result);
    }
    private List<Long> queryUserOrg() {
        LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        List<UserOrg> userOrgs = userOrgService.queryByUserId(user.getId());
        Set<Long> orgNoSet = Sets.newHashSet();
        for (UserOrg userOrg : userOrgs) {
            orgNoSet.add(userOrg.getOrgNo());
        }
        // 根据管辖权限，查询用户
        return orgService.queryDataOrg(orgNoSet);
    }
}
