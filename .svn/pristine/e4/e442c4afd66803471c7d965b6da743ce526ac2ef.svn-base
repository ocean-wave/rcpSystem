package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.ChartsQueryVo;
import cn.com.cdboost.collect.dto.param.EnergyEfficiencyQueryVo;
import cn.com.cdboost.collect.dto.param.MeterDayPowerQueryVo;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.service.EnergyEfficiencyService;
import cn.com.cdboost.collect.service.GenerateFileService;
import cn.com.cdboost.collect.service.MeterDayPowerService;
import cn.com.cdboost.collect.vo.PageResult;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * 能效分析控制类
 */
@Controller
@RequestMapping(value = "/efficiency")
public class EnergyEfficiencyController {
    private static final Logger logger = LoggerFactory.getLogger(EnergyEfficiencyController.class);

    @Autowired
    private EnergyEfficiencyService energyEfficiencyService;
    @Autowired
    private GenerateFileService generateFileService;
    /**
     * 能效分析汇总列表
     * @param queryVo
     * @return
     */
    @SystemControllerLog(description = "能效分析汇总列表")
    @RequestMapping(value = "/queryVillageList")
    @ResponseBody
    public String queryVillageList(HttpSession session,@RequestBody EnergyEfficiencyQueryVo queryVo){
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        queryVo.setUserId(currentUser.getId());
        PageResult result=new PageResult();
        //数据库查询，调用存储过程
        List<VillageElectricityDto> villageElectricityDtos = energyEfficiencyService.queryVillageList(queryVo);
        result.setData(villageElectricityDtos);
        result.setTotal(queryVo.getRowCount());
        return JSON.toJSONString(result);
    }

    /**
     * 能效分析汇总列表下载
     * @param queryVo
     * @return
     */
    @SystemControllerLog(description = "能效分析汇总列表下载")
    @RequestMapping(value = "/downloadVillageList")
    @ResponseBody
    public void downloadVillageList(HttpSession session,HttpServletResponse response, EnergyEfficiencyQueryVo queryVo){
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        queryVo.setUserId(currentUser.getId());
        List<VillageElectricityDto> villageElectricityDtos = energyEfficiencyService.queryVillageList(queryVo);
        try {
            XSSFWorkbook workBook = generateFileService.generatevillageElectricityListExcel("能效分析汇总列表",villageElectricityDtos);
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
            String message = "能效分析汇总列表";
            logger.error(message,e);
        }
    }

    /**
     * 统计台区数
     * @param queryVo
     * @return
     */
    @SystemControllerLog(description = "统计台区数")
    @RequestMapping(value = "/queryMeterNum")
    @ResponseBody
    public String queryMeterNum(@RequestBody ChartsQueryVo queryVo){
        Result result = new Result();
        Integer meterNum = energyEfficiencyService.queryMeterNum(queryVo);
        result.setData(meterNum);
        return JSON.toJSONString(result);
    }

    /**
     * 按年月日柱状图统计
     * @param queryVo
     * @return
     */
    @SystemControllerLog(description = "按年月日柱状图统计")
    @RequestMapping(value = "/electricCount")
    @ResponseBody
    public String electricCount(HttpSession session,@RequestBody ChartsQueryVo queryVo){
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        queryVo.setUserId(currentUser.getId());
        Result result = new Result();
        ElectricChartsInfo electricChartsInfo = energyEfficiencyService.electricCount(queryVo);
        result.setData(electricChartsInfo);
        return JSON.toJSONString(result);
    }

    /**
     * 按天统计设备耗电能效列表
     * @param queryVo
     * @return
     */
    @SystemControllerLog(description = "按天统计设备耗电能效列表")
    @RequestMapping(value = "/queryDeviceList")
    @ResponseBody
    public String queryDeviceList(HttpSession session,@RequestBody EnergyEfficiencyQueryVo queryVo){
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        queryVo.setUserId(currentUser.getId());
        PageResult result = new PageResult();
        List<ElectricityCountDto> electricityCountDtos = energyEfficiencyService.queryDeviceList(queryVo);
        result.setData(electricityCountDtos);
        result.setTotal(queryVo.getRowCount());
        return JSON.toJSONString(result);
    }

    /**
     * 按天统计设备耗电能效列表下载
     * @param queryVo
     * @return
     */
    @SystemControllerLog(description = "按天统计设备耗电能效列表下载")
    @RequestMapping(value = "/downloadDeviceList")
    @ResponseBody
    public void downloadDeviceList(HttpSession session,HttpServletResponse response, EnergyEfficiencyQueryVo queryVo){
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        queryVo.setUserId(currentUser.getId());
        List<ElectricityCountDto> list = energyEfficiencyService.queryDeviceList(queryVo);
        try {
            XSSFWorkbook workBook = generateFileService.generateElectricityListExcel("设备耗电能效列表",list);
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
            String message = "设备耗电能效列表";
            logger.error(message,e);
        }
    }

    /**
     * 查询设备详情列表
     * @param queryVo
     * @return
     */
    @SystemControllerLog(description = "查询设备详情列表")
    @RequestMapping(value = "/queryDeviceDetail")
    @ResponseBody
    public String queryDeviceDetail(@RequestBody EnergyEfficiencyQueryVo queryVo){
        PageResult result=new PageResult();
        List<EnergyEfficiencyDetailDto> energyEfficiencyDetailDtos = energyEfficiencyService.queryDeviceDetail(queryVo);
        result.setData(energyEfficiencyDetailDtos);
        result.setTotal(queryVo.getRowCount());
        return JSON.toJSONString(result);
    }

    /**
     * 设备详情列表下载
     * @param queryVo
     * @return
     */
    @SystemControllerLog(description = "设备详情列表下载")
    @RequestMapping(value = "/downloadDeviceDetail")
    @ResponseBody
    public void downloadDeviceDetail(HttpServletResponse response, EnergyEfficiencyQueryVo queryVo){
        List<EnergyEfficiencyDetailDto> list = energyEfficiencyService.queryDeviceDetail(queryVo);
        try {
            XSSFWorkbook workBook = generateFileService.generateDeviceDetailListExcel("设备详情列表",list);
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
            String message = "设备详情列表下载";
            logger.error(message,e);
        }
    }

    /**
     * 查询用户最近15天用电详情列表
     * @param queryVo
     * @return
     */
    @SystemControllerLog(description = "查询用户最近15天用电详情列表")
    @RequestMapping(value = "/lastPowerDetail")
    @ResponseBody
    public String lastPowerDetail(@RequestBody MeterDayPowerQueryVo queryVo){
        PageResult result=new PageResult();
        List<EnergyEfficiencyDetailListDto> res = energyEfficiencyService.queryLastPowerDetail(queryVo);
        result.setData(res);
        return JSON.toJSONString(result);
    }
}
