package cn.com.cdboost.collect.controller;


import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.CollectRecordDetialQueryParam;
import cn.com.cdboost.collect.dto.param.FreeDayByCollGetQueryParam;
import cn.com.cdboost.collect.dto.param.FreeDayByCollGetQueryVo;
import cn.com.cdboost.collect.dto.param.ReadCollectRecordDetailQueryVo;
import cn.com.cdboost.collect.dto.response.RoundPercentInfo;
import cn.com.cdboost.collect.enums.Action;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.service.FreezeAnalyzeService;
import cn.com.cdboost.collect.service.GenerateFileService;
import cn.com.cdboost.collect.service.RealTimeDataService;
import cn.com.cdboost.collect.service.UserLogService;
import cn.com.cdboost.collect.vo.PageResult;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;


/**
 * 采集分析模块相关接口
 */
@Controller
@RequestMapping(value = "/freezeAnalyze")
public class FreezeAnalyzeController {
    private static final Logger logger = LoggerFactory.getLogger(FreezeAnalyzeController.class);

    @Autowired
    private FreezeAnalyzeService freezeAnalyzeService;
    @Autowired
    private RealTimeDataService realTimeDataService;
    @Autowired
    private GenerateFileService generateFileService;
    @Autowired
    private UserLogService userLogService;

    /**
     * 采集分析模块：采集结果分析：各时间段采集情况柱状图
     * @param jzqNo
     * @param freezeDate
     * @return
     */
    @SystemControllerLog(description = "采集分析模块：采集结果分析：各时间段采集情况柱状图")
    @RequestMapping(value = "/roundChart", method = RequestMethod.POST)
    @ResponseBody
    public String roundChart(@RequestParam String jzqNo, @RequestParam String freezeDate) {
        Result<List<RoundChartVo>> result = new Result<>();
        if (StringUtils.isEmpty(jzqNo)) {
            result.error("jzqNo不能为空");
            return JSON.toJSONString(result);
        }

        if (StringUtils.isEmpty(freezeDate)) {
            result.error("freezeDate不能为空");
            return JSON.toJSONString(result);
        }
        List<RoundChartVo> roundChartVos = freezeAnalyzeService.queryRoundChart(jzqNo, freezeDate);
        result.setData(roundChartVos);

        return JSON.toJSONString(result);
    }

    /**
     * 采集分析：冻结数据详情列表查询
     * @param session
     * @param deviceType
     * @return
     */
    @SystemControllerLog(description = "采集分析：冻结数据详情列表查询")
    @RequestMapping(value = "/rateChart", method = RequestMethod.POST)
    @ResponseBody
    public String rateChart(HttpSession session, @RequestParam String deviceType) {
        Result<List<CollectRecordDTO>> result = new Result<>();
        if (StringUtils.isEmpty(deviceType)) {
            result.error("deviceType不能为空");
            return JSON.toJSONString(result);
        }
        LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        List<CollectRecordDTO> dtoList = freezeAnalyzeService.querySucRateChart(deviceType, Long.valueOf(user.getId()));
        result.setData(dtoList);

        return JSON.toJSONString(result);
    }

    /**
     * 采集分析：冻结数据详情列表,右边刷新按钮点击
     * 立即汇总日冻结抄收情况，并返回汇总数据
     * @param session
     * @param deviceType
     * @return
     */
    @SystemControllerLog(description = "立即汇总日冻结抄收情况，并返回汇总数据")
    @RequestMapping(value = "sumFreeForDay", method = RequestMethod.POST)
    @ResponseBody
    public String sumFreeForDay(HttpSession session, @RequestParam String deviceType){
        PageResult<List<CollectRecordDTO>> result = new PageResult<>();
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);

        // 立即汇总日冻结抄收情况
        realTimeDataService.SumFreeForDay();
        StringBuilder sbTotal = new StringBuilder();
        // 返回汇总数据
        List<CollectRecordDTO> dtoList = realTimeDataService.ReadCollectRecord(deviceType, sbTotal, currentUser.getId());
        result.setData(dtoList);
        result.setTotal(Long.valueOf(sbTotal.toString()));

        result.setMessage("刷新成功");
        return JSON.toJSONString(result);
    }


    /**
     * 采集分析：采集数据详情，节点页签数据查询
     * @param session
     * @param queryParam
     * @return
     */
    @SystemControllerLog(description = "采集分析：采集数据详情，节点页签数据查询")
    @RequestMapping(value = "/rateGroupByCjq", method = RequestMethod.POST)
    @ResponseBody
    public String rateGroupByCjq(HttpSession session, @Valid @RequestBody FreeDayByCollGetQueryParam queryParam) {
        PageResult<List<MoteAnalyzeResponse>> result = new PageResult<>();
        LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        FreeDayByCollGetQueryVo queryVo = new FreeDayByCollGetQueryVo();
        BeanUtils.copyProperties(queryParam,queryVo);
        queryVo.setUserId(String.valueOf(user.getId()));
        queryVo.setUpdateTime(queryParam.getFreezeDate());
        queryVo.setCollectNo(queryParam.getMoteEui());
        List<MoteAnalyzeResponse> list = freezeAnalyzeService.queryDataGroupByCjq(queryVo);
        result.setData(list);
        result.setTotal(queryVo.getRowCount());
        return JSON.toJSONString(result);
    }

    /**
     * 采集分析模块：采集轮次分布饼状图
     * @param jzqNo
     * @param freezeDate
     * @return
     */
    @SystemControllerLog(description = "采集分析模块：采集轮次分布饼状图")
    @RequestMapping(value = "/roundPercent", method = RequestMethod.POST)
    @ResponseBody
    public String roundPercent(@RequestParam String jzqNo, @RequestParam String freezeDate) {
        Result<List<RoundPercentInfo>> result = new Result<>();
        if (StringUtils.isEmpty(jzqNo)) {
            result.error("jzqNo不能为空");
            return JSON.toJSONString(result);
        }

        if (StringUtils.isEmpty(freezeDate)) {
            result.error("freezeDate不能为空");
            return JSON.toJSONString(result);
        }
        List<RoundPercentInfo> roundPercentInfos = freezeAnalyzeService.queryRoundPercent(jzqNo, freezeDate);
        result.setData(roundPercentInfos);

        return JSON.toJSONString(result);
    }

    /**
     * 采集分析模块：采集结果统计：采集器页签数据双击，查询单个采集器连续多日成功率曲线
     * @param jzqNo
     * @param moteEui
     * @param dayCnt
     * @return
     */
    @SystemControllerLog(description = "采集分析模块：采集结果统计：采集器页签数据双击，查询单个采集器连续多日成功率曲线")
    @RequestMapping(value = "/sucRateByCjq", method = RequestMethod.POST)
    @ResponseBody
    public String sucRateByCjq(@RequestParam String jzqNo,
                               @RequestParam String moteEui,
                               @RequestParam Integer dayCnt) {
        Result<List<SucRateByCjqVo>> result = new Result<>();
        if (StringUtils.isEmpty(jzqNo)) {
            result.error("jzqNo不能为空");
            return JSON.toJSONString(result);
        }

        if (StringUtils.isEmpty(moteEui)) {
            result.error("moteEui不能为空");
            return JSON.toJSONString(result);
        }

        List<SucRateByCjqVo> sucRateByCjqVos = freezeAnalyzeService.querySucRateByCjq(jzqNo, moteEui, dayCnt);
        result.setData(sucRateByCjqVos);
        return JSON.toJSONString(result);
    }

    /**
     * 采集分析模块：采集结果统计：成功、失败页签，数据查询
     * @param session
     * @param queryParam
     * @return
     */
    @SystemControllerLog(description = "采集分析模块：采集结果统计：成功、失败页签，数据查询")
    @RequestMapping(value = "readCollectRecordDetail", method = RequestMethod.POST)
    @ResponseBody
    public String readCollectRecordDetail(HttpSession session, @Valid @RequestBody CollectRecordDetialQueryParam queryParam){
        PageResult<List<CollectDetialDTO>> result = new PageResult<>();
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);

        ReadCollectRecordDetailQueryVo queryVo = new ReadCollectRecordDetailQueryVo();
        BeanUtils.copyProperties(queryParam,queryVo);
        queryVo.setUserId(currentUser.getId());
        List<CollectDetialDTO> dtoList = realTimeDataService.readCollectRecordDetail(queryVo);
        result.setData(dtoList);
        result.setTotal(queryVo.getRowCount());

        return JSON.toJSONString(result);
    }

    /**
     * 下载冻结数据详情列表
     * @param response
     * @param session
     * @param queryParam
     */
    @SystemControllerLog(description = "下载冻结数据详情列表")
    @RequestMapping("/downloadFrozenDataDetail")
    public void downloadFrozenDataDetail(HttpServletResponse response, HttpSession session, CollectRecordDetialQueryParam queryParam) {
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        userLogService.create(currentUser.getId(), Action.DOWNLOAD.getActionId(),"数据采集","deviceType",queryParam.getDeviceType(),"下载冻结数据详情列表", JSON.toJSONString(queryParam));

        try {
            StringBuilder sbTotal = new StringBuilder();
            List<CollectRecordDTO> dtoList = realTimeDataService.ReadCollectRecord(queryParam.getDeviceType(), sbTotal, currentUser.getId());
            XSSFWorkbook workBook = generateFileService.generateReadCollectRecordExcel("冻结数据详情", dtoList);
            //通过Response把数据以Excel格式保存
            response.reset();
            //设置response流信息的头类型，MIME码
            response.setContentType("application/vnd.ms-excel");
            ServletOutputStream out = null;
            response.addHeader("Content-Disposition", "attachment;filename=\""
                    + new String(("导出表" + System.currentTimeMillis() + ".xls").getBytes("utf-8"),
                    "ISO8859_1") + "\"");
            //创建输出流对象
            out=response.getOutputStream();
            //将创建的Excel对象利用二进制流的形式强制输出到客户端去
            workBook.write(out);
            //强制将数据从内存中保存
            out.flush();
            out.close();
        } catch (Exception e) {
            logger.error("系统异常：",e);
        }
    }

    /**
     * 采集分析模块：采集结果统计，成功、失败列表数据下载
     * @param response
     * @param session
     * @param queryParam
     */
    @SystemControllerLog(description = "采集分析模块：采集结果统计，成功、失败列表数据下载")
    @RequestMapping("/downloadCollectResultData")
    public void downloadCollectResultData(HttpServletResponse response, HttpSession session, CollectRecordDetialQueryParam queryParam) {
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        userLogService.create(currentUser.getId(), Action.DOWNLOAD.getActionId(),"数据采集","deviceType",queryParam.getDeviceType(),"下载"+queryParam.getDate()+"召测"+("1".equals(String.valueOf(queryParam.getFlag()))?"成功":"失败")+"详情列表",JSON.toJSONString(queryParam));
        try {
            ReadCollectRecordDetailQueryVo queryVo = new ReadCollectRecordDetailQueryVo();
            BeanUtils.copyProperties(queryParam,queryVo);
            queryVo.setUserId(currentUser.getId());
            List<CollectDetialDTO> dtoList = realTimeDataService.readCollectRecordDetail(queryVo);

            XSSFWorkbook workBook = generateFileService.generateReadCollectRecordDetialExcel("召测详情", dtoList);
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
            workBook.write(out);
            //强制将数据从内存中保存
            out.flush();
            out.close();
        } catch (Exception e) {
            logger.error("系统异常：",e);
        }
    }
}
