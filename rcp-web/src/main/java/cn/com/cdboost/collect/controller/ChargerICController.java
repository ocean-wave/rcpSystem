package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.ChargerICCardAddParam;
import cn.com.cdboost.collect.dto.param.ChargerICCardEditParam;
import cn.com.cdboost.collect.dto.param.ChargerICCardQueryVo;
import cn.com.cdboost.collect.dto.param.OffOnCardParam;
import cn.com.cdboost.collect.dto.response.BatchSaveResultInfo;
import cn.com.cdboost.collect.dto.response.UploadFileResultInfo;
import cn.com.cdboost.collect.enums.Action;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.filter.MyFilenameFilter;
import cn.com.cdboost.collect.service.*;
import cn.com.cdboost.collect.util.DownLoadUtil;
import cn.com.cdboost.collect.util.UuidUtil;
import cn.com.cdboost.collect.vo.PageResult;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 充电桩IC卡管理
 */
@Controller
@RequestMapping(value = "/chargerIC")
public class ChargerICController {
    private static final Logger logger = LoggerFactory.getLogger(ChargerICController.class);

    @Autowired
    private ChargingCardService chargingCardService;
    @Autowired
    private GenerateFileService generateFileService;
    @Autowired
    private UserLogService userLogService;
    @Autowired
    private ChargingPayService chargingPayService;
    @Autowired
    private ChargingUseDetailedService chargingUseDetailedService;

    @SystemControllerLog(description = "充电IC卡列表查询")
    @RequestMapping(value = "/queryList")
    @ResponseBody
    public String queryList(@RequestBody ChargerICCardQueryVo queryVo){
        PageResult result=new PageResult();
        //查询数据库
        List<ChargingICCardDto> chargingDevices = chargingCardService.queryList(queryVo);
        result.setData(chargingDevices);
        result.setTotal(queryVo.getTotal());
        return JSON.toJSONString(result);
    }

    @SystemControllerLog(description = "充电设备列表下载")
    @RequestMapping(value = "/downloadList")
    @ResponseBody
    public void downloadList(HttpServletResponse response, ChargerICCardQueryVo queryVo){
        //查询数据库
        List<ChargingICCardDto> chargingICCardDtos = chargingCardService.queryList(queryVo);
        try {
            XSSFWorkbook workBook = generateFileService.generateChargingICCardListExcel("充电桩IC卡列表",chargingICCardDtos);
            DownLoadUtil.downExcel(response,workBook);
        } catch (Exception e) {
            String message = "充电桩IC卡列表";
            logger.error(message,e);
        }
    }

    @SystemControllerLog(description = "添加充电IC卡")
    @RequestMapping(value = "/add")
    @ResponseBody
    public String add(HttpSession session, @Valid @RequestBody ChargerICCardAddParam param){
        Result result=new Result();
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);

        //调用业务层
        chargingCardService.addCard(param,currentUser.getId());
        userLogService.create(currentUser.getId(), Action.ADD.getActionId(), "充电桩IC卡", "IC卡编号", param.getCardId(),"新增["+param.getCardId()+"]充电IC卡",JSON.toJSONString(param));
        result.setMessage("添加成功");
        return JSON.toJSONString(result);
    }

    @SystemControllerLog(description = "编辑充电IC卡")
    @RequestMapping(value = "/edit")
    @ResponseBody
    public String edit(HttpSession session, @Valid @RequestBody ChargerICCardEditParam param){
        Result result=new Result();
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);

        //调用业务层
        chargingCardService.editCard(param,currentUser.getId());
        userLogService.create(currentUser.getId(), Action.MODIFY.getActionId(), "充电桩IC卡", "IC卡编号", param.getCardId(),"修改["+param.getCardId()+"]充电IC卡",JSON.toJSONString(param));
        result.setMessage("修改成功");
        return JSON.toJSONString(result);
    }

    @SystemControllerLog(description = "删除充电IC卡")
    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(HttpSession session, @RequestBody List<String> cardIds){
        Result result=new Result();
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);

        //调用业务层
        chargingCardService.delete(cardIds,currentUser.getId());
        userLogService.create(currentUser.getId(), Action.DELETE.getActionId(), "充电桩IC卡", "IC卡编号", cardIds.toString(),"删除["+cardIds.toString()+"]充电IC卡",JSON.toJSONString(cardIds));
        result.setMessage("删除成功");
        return JSON.toJSONString(result);
    }

    @SystemControllerLog(description = "停用启用充电IC卡")
    @RequestMapping(value = "/offOnCard")
    @ResponseBody
    public String offOnCard(HttpSession session, @RequestBody OffOnCardParam param){
        Result result=new Result();
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);

        //调用业务层
        chargingCardService.offOnCard(param,currentUser.getId());
        if (param.getOnOrOff() == 0){
            userLogService.create(currentUser.getId(), Action.MODIFY.getActionId(), "充电桩IC卡", "", "","停用["+param.getCardIds().toString()+"]充电IC卡",JSON.toJSONString(param));
        }else if (param.getOnOrOff() == 1){
            userLogService.create(currentUser.getId(), Action.MODIFY.getActionId(), "充电桩IC卡", "", "","启用["+param.getCardIds().toString()+"]充电IC卡",JSON.toJSONString(param));
        }
        result.setMessage("操作成功");
        return JSON.toJSONString(result);
    }

    @SystemControllerLog(description = "充电IC卡充值记录查询")
    @RequestMapping(value = "/queryPayList")
    @ResponseBody
    public String queryPayList(@RequestBody ChargerICCardQueryVo queryVo){
        PageResult result=new PageResult();
        //查询数据库
        List<ChargingICPayDto> chargingICPayDtos = chargingPayService.queryICCardPayList(queryVo);
        result.setData(chargingICPayDtos);
        result.setTotal(queryVo.getTotal());
        return JSON.toJSONString(result);
    }

    @SystemControllerLog(description = "充电IC卡充值记录下载")
    @RequestMapping(value = "/downloadPayList")
    @ResponseBody
    public void downloadPayList(HttpServletResponse response, ChargerICCardQueryVo queryVo){
        //查询数据库
        List<ChargingICPayDto> chargingICPayDtos = chargingPayService.queryICCardPayList(queryVo);
        try {
            XSSFWorkbook workBook = generateFileService.generateChargingICPayListExcel("充电桩IC卡充值记录列表",chargingICPayDtos);
            DownLoadUtil.downExcel(response,workBook);
        } catch (Exception e) {
            String message = "充电桩IC卡充值记录列表";
            logger.error(message,e);
        }
    }

    @SystemControllerLog(description = "充电IC卡使用记录查询")
    @RequestMapping(value = "/queryUseListByIC")
    @ResponseBody
    public String queryUseListByIC(@RequestBody ChargerICCardQueryVo queryVo){
        PageResult result=new PageResult();
        //查询数据库
        List<ChargingICUseDto> chargingICUseDtos = chargingUseDetailedService.queryICCardUseList(queryVo);
        result.setData(chargingICUseDtos);
        result.setTotal(queryVo.getTotal());
        return JSON.toJSONString(result);
    }

    @SystemControllerLog(description = "充电IC卡使用记录下载")
    @RequestMapping(value = "/downloadUseList")
    @ResponseBody
    public void downloadUseList(HttpServletResponse response, ChargerICCardQueryVo queryVo){
        //查询数据库
        List<ChargingICUseDto> chargingICUseDtos = chargingUseDetailedService.queryICCardUseList(queryVo);
        try {
            XSSFWorkbook workBook = generateFileService.generateChargingICUseListExcel("充电桩IC卡使用记录列表",chargingICUseDtos);
            DownLoadUtil.downExcel(response,workBook);
        } catch (Exception e) {
            String message = "充电桩IC卡使用记录列表";
            logger.error(message,e);
        }
    }


    @SystemControllerLog(description = "充电IC卡档案上传excel文档")
    @RequestMapping(value = "/uploadFile")
    @ResponseBody
    public String uploadFile(HttpServletRequest request, @RequestParam MultipartFile fileName) {
        Result<UploadFileResultInfo> result = new Result();
        String originalFilename = fileName.getOriginalFilename();
        String uuid = UuidUtil.getUuid();
        String parentPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");
        String saveName = uuid + originalFilename.substring(originalFilename.lastIndexOf("."));
        File parentFile = new File(parentPath);
        if(!parentFile.exists() || !parentFile.isDirectory()) {
            parentFile.mkdirs();
        }
        File newFile = new File(parentFile, saveName);
        if(!newFile.exists()) {
            try {
                newFile.createNewFile();
                fileName.transferTo(newFile);
            } catch (IOException e) {
                logger.error("文件IO异常",e);
                result.error("文件IO异常");
                return JSON.toJSONString(result);
            }
        }

        UploadFileResultInfo resultInfo = new UploadFileResultInfo();
        resultInfo.setSaveName(saveName);
        resultInfo.setImportId(uuid);
        resultInfo.setImportTime(new Date());
        resultInfo.setFileName(originalFilename);
        result.setData(resultInfo);
        return JSON.toJSONString(result);
    }


    @SystemControllerLog(description = "IC卡档案批量新增")
    @RequestMapping(value = "/batchSave")
    @ResponseBody
    public String batchSave(HttpSession session, @RequestParam String saveName) {
        Result<BatchSaveResultInfo> result = new Result();
        if (StringUtils.isEmpty(saveName)) {
            result.error("saveName不能为空");
            return JSON.toJSONString(result);
        }
        LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);

        String suffixName = saveName.substring(saveName.lastIndexOf("."));
        if(!".xls".equals(suffixName) && !".xlsx".equals(suffixName)) {
            result.error("文件格式不正确");
            return JSON.toJSONString(result);
        }
        String uuid = saveName.substring(0, saveName.lastIndexOf("."));
        CustomerBatchImportInfo importInfo = chargingCardService.excelImport(session, saveName, uuid);
        /*if(1 == importInfo.getResult()) {
            BatchSaveResultInfo batchSaveResultInfo = new BatchSaveResultInfo();
            batchSaveResultInfo.setSuccessCount(importInfo.getSuccessCount());
            batchSaveResultInfo.setFailCount(importInfo.getFailCount());
            result.setData(batchSaveResultInfo);

            result.setMessage("导入成功");
        } else {
            result.error("导入失败");
        }*/
        if(result.getData().getSuccessCount()>0){
            userLogService.create(user.getId(), Action.ADD.getActionId(),"客户档案","saveName",saveName,"批量新增客户档案",saveName);
        }
        return JSON.toJSONString(result);
    }

    @SystemControllerLog(description = "下载档案导入异常数据")
    @RequestMapping(value = "/downloadErrorData")
    public void downloadErrorData(HttpServletResponse response, HttpSession session, @RequestParam String importId) {
        try {
            LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
            userLogService.create(user.getId(), Action.DOWNLOAD.getActionId(),"IC卡档案","importId",importId,"批量导入异常数据下载",importId);

            String parentPath = session.getServletContext().getRealPath("/WEB-INF/upload/");
            File parentFile = new File(parentPath);
            String type = importId + "icError";
            //通过文件名过滤文件
            FilenameFilter filter = new MyFilenameFilter(type);
            String[]  fileNames = parentFile.list(filter);

            Workbook wb;
            FileInputStream in = new FileInputStream(new File(parentFile,fileNames[0]));
            String suffixName = fileNames[0].substring(fileNames[0].lastIndexOf("."));
            if(".xls".equals(suffixName)) {
                wb = new HSSFWorkbook(in);
            } else {
                wb = new XSSFWorkbook(in);
            }
            //通过Response把数据以Excel格式保存
            response.reset();
            //设置response流信息的头类型，MIME码
            response.setHeader("Content-type","application/x-msexcel");
            response.setHeader("content-disposition","attachment;filename=\"" + System.currentTimeMillis() + ".xls\"");
            ServletOutputStream out;
            //创建输出流对象
            out=response.getOutputStream();
            wb.write(out);
            out.flush();
            out.close();
            wb.close();
        } catch (Exception e) {
            logger.error("系统异常：",e);
        }
    }
}
