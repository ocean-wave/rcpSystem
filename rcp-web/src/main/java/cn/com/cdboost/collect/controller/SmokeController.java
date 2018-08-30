package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.model.SmokeDevice;
import cn.com.cdboost.collect.model.SmokeDevlog;
import cn.com.cdboost.collect.service.GenerateFileService;
import cn.com.cdboost.collect.service.SmokeDeviceServiceDubbo;
import cn.com.cdboost.collect.service.SmokeDevlogServiceDubbo;
import cn.com.cdboost.collect.util.DownLoadUtil;
import cn.com.cdboost.collect.vo.*;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;
import java.util.List;


/**
 * @author wt
 * @desc
 * @create in  2018/8/22
 **/
@Controller
@RequestMapping("smoke")
public class SmokeController {

    @Autowired
    SmokeDeviceServiceDubbo smokeDeviceService;
    @Autowired
    SmokeDevlogServiceDubbo smokeDevlogService;
    @Autowired
    GenerateFileService generateFileService;

    @SystemControllerLog(description="烟感设备列表")
    @RequestMapping("smokeDeviceList")
    @ResponseBody
    public String smokeDeviceList(@Valid @RequestBody SmokeDeviceListNoTimeVo smokeDeviceListVo) {
        PageResult result = new PageResult();
        List<SmokeDeviceVo> smokeDevices = smokeDeviceService.smokeDeviceList(smokeDeviceListVo);
        PageInfo pageInfo=new PageInfo(smokeDevices);
        result.setData(pageInfo.getList());
        result.setTotal(pageInfo.getTotal());
        return JSON.toJSONString(result);
    }
    @SystemControllerLog(description="烟感设备列表下载")
    @RequestMapping("smokeDeviceListDownload")
    public void smokeDeviceListDownload(HttpServletResponse session, SmokeDeviceListNoTimeVo smokeDeviceListVo) throws IOException {
        List<SmokeDeviceVo> smokeDeviceVos = smokeDeviceService.smokeDeviceList(smokeDeviceListVo);
        List<SmokeDevice> smokeDevices= Lists.newArrayList();
        for (SmokeDeviceVo device : smokeDeviceVos) {
            SmokeDevice smokeDevice=new SmokeDevice();
            BeanUtils.copyProperties(device,smokeDevice);
            smokeDevices.add(smokeDevice);
        }
        XSSFWorkbook xssfWorkbook = generateFileService.smokeDeviceListDownload("烟感设备列表", smokeDevices);
        DownLoadUtil.downExcel(session,xssfWorkbook);
    }
    @SystemControllerLog(description="烟感设备添加")
    @RequestMapping("smokeDeviceAdd")
    @ResponseBody
    public String smokeDeviceAdd(@Valid @RequestBody SmokeDeviceVo smokeDeviceVo) {
        Result result = new Result("添加成功");
        int smokeDevices = smokeDeviceService.insertSelective(smokeDeviceVo);
        if(smokeDevices!=1){
            result.error("添加失败");
        }
        return JSON.toJSONString(result);
    }
    @SystemControllerLog(description="烟感设备删除")
    @RequestMapping("smokeDeviceDelete")
    @ResponseBody
    public String smokeDeviceDelete( @RequestBody List<String> cno) {
        Result result = new Result("删除成功");
        int smokeDevices = smokeDeviceService.deleteByCno(cno);
        if(smokeDevices!=cno.size()){
            result.error("删除失败");
        }
        return JSON.toJSONString(result);
    }
    @SystemControllerLog(description="烟感设备编辑")
    @RequestMapping("smokeDeviceEdit")
    @ResponseBody
    public String smokeDeviceEdit( @Valid @RequestBody SmokeDeviceVo smokeDeviceVo) {
        Result result = new Result("编辑成功");
        int smokeDevices = smokeDeviceService.updateByCno(smokeDeviceVo);
        if(smokeDevices!=1){
            result.error("编辑失败");
        }
        return JSON.toJSONString(result);
    }
    @SystemControllerLog(description="烟感设备详情")
    @RequestMapping("smokeDeviceDetail")
    @ResponseBody
    public String smokeDeviceDetail(String cno) {
        PageResult result = new PageResult();
        SmokeDeviceVo smokeDeviceVo=new SmokeDeviceVo();
        smokeDeviceVo.setCno(cno);
        smokeDeviceVo = smokeDeviceService.selectOne(smokeDeviceVo);
        result.setData(smokeDeviceVo);
        return JSON.toJSONString(result);
    }
    @SystemControllerLog(description="烟感设备数据详情")
    @RequestMapping("smokeDataLog")
    @ResponseBody
    public String smokeDataLog(String cno) {
        PageResult result = new PageResult();
        SmokeDevlogVo smokeDevlogVo=new SmokeDevlogVo();
        smokeDevlogVo.setCno(cno);
        smokeDevlogVo = smokeDevlogService.selectOne(smokeDevlogVo);
        result.setData(smokeDevlogVo);
        return JSON.toJSONString(result);
    }
    @SystemControllerLog(description="烟感设备数据详情")
    @RequestMapping("smokeDeviceStatusList")
    @ResponseBody
    public String smokeDeviceStatusList(@Valid @RequestBody SmokeDeviceListVo smokeDeviceListVo) {
        PageResult result = new PageResult();
        PageInfo pageInfo = smokeDevlogService.smokeDeviceStatusList(smokeDeviceListVo);
        result.setTotal(pageInfo.getTotal());
        result.setData(pageInfo.getList());
        return JSON.toJSONString(result);
    }
    @SystemControllerLog(description="烟感设备数据详情下载")
    @RequestMapping("smokeDeviceStatusListDownload")
    public void smokeDeviceStatusListDownload(HttpServletResponse session,SmokeDeviceListVo smokeDeviceListVo) throws IOException {
        PageInfo pageInfo = smokeDevlogService.smokeDeviceStatusList(smokeDeviceListVo);
        List<SmokeDevlog> smokeDevices= Lists.newArrayList();
        List<SmokeDevlogVo> list = pageInfo.getList();
        for (SmokeDevlogVo device : list) {
            SmokeDevlog smokeDevice=new SmokeDevlog();
            BeanUtils.copyProperties(device,smokeDevice);
            smokeDevices.add(smokeDevice);
        }
        XSSFWorkbook xssfWorkbook = generateFileService.smokeDeviceStatusListDownload("烟感设备列表", smokeDevices);
        DownLoadUtil.downExcel(session,xssfWorkbook);
    }
    @SystemControllerLog(description="烟感状态监测总数")
    @RequestMapping("smokeDeviceTotal")
    @ResponseBody
    public String smokeDeviceTotal() {
        PageResult result = new PageResult();
        cn.com.cdboost.collect.vo.SmokeDeviceSelectTotalInfo smokeDevices = smokeDeviceService.SmokeDeviceSelectTotal();
        result.setData(smokeDevices);
        return JSON.toJSONString(result);
    }
    @SystemControllerLog(description="烟感状态监测列表")
    @RequestMapping("smokeDeviceDetect")
    @ResponseBody
    public String smokeDeviceDetect(@RequestParam("cno") String cno,@RequestParam("status") String status) {
        PageResult result = new PageResult();
        SmokeDeviceVo smokeDeviceVo=new SmokeDeviceVo();
        if(!"0".equals(status)){
            smokeDeviceVo.setStatus(status);
        }
        if(!StringUtils.isEmpty(cno)){
            smokeDeviceVo.setCno(cno);
        }
        List<SmokeDeviceVo> smokeDevices = smokeDeviceService.smokeDeviceDetect(smokeDeviceVo);
        Collections.sort(smokeDevices, (o1, o2) -> o2.getUpdateTime().compareTo(o1.getUpdateTime()));
        result.setData(smokeDevices);
        return JSON.toJSONString(result);
    }
    @SystemControllerLog(description="设备状态启用")
    @RequestMapping("smokeDeviceStatus")
    @ResponseBody
    public String smokeDeviceStatus(@RequestParam("cno") String cno,@RequestParam("status") String status) {
        PageResult result = new PageResult();
        SmokeDeviceVo smokeDeviceVo=new SmokeDeviceVo();
        if(!"0".equals(status)){
            smokeDeviceVo.setStatus(status);
        }
        if(!StringUtils.isEmpty(cno)){
            smokeDeviceVo.setCno(cno);
        }
        int smokeDevices = smokeDeviceService.updateByCno(smokeDeviceVo);
        if(smokeDevices!=1){
            result.error("更新失败");
        }
        return JSON.toJSONString(result);
    }
    @SystemControllerLog(description="短信发送设置")
    @RequestMapping("smokeDeviceIsSms")
    @ResponseBody
    public String smokeDeviceIsSms(@RequestParam("cno") String cno,@RequestParam("isSms") String isSms) {
        PageResult result = new PageResult();
        SmokeDeviceVo smokeDeviceVo=new SmokeDeviceVo();
        if(!StringUtils.isEmpty(isSms)){
            smokeDeviceVo.setIsSms(isSms);
        }
        if(!StringUtils.isEmpty(cno)){
            smokeDeviceVo.setCno(cno);
        }
        int smokeDevices = smokeDeviceService.updateByCno(smokeDeviceVo);
        if(smokeDevices!=1){
            result.error("更新失败");
        }
        return JSON.toJSONString(result);
    }
}