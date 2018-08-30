package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.dto.LoginUser;
import cn.com.cdboost.collect.dto.MeterCollectGroupDto;
import cn.com.cdboost.collect.dto.QueryRefundRecordInfo;
import cn.com.cdboost.collect.dto.RefundQueryListInfo;
import cn.com.cdboost.collect.dto.param.QueryPr0Param;
import cn.com.cdboost.collect.dto.param.QueryPr0Vo;
import cn.com.cdboost.collect.dto.param.QueryRefundRecordVo;
import cn.com.cdboost.collect.dto.response.QueryRechargeRecordInfo;
import cn.com.cdboost.collect.enums.Auth;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.service.GenerateFileService;
import cn.com.cdboost.collect.service.KeyCollectionService;
import cn.com.cdboost.collect.service.MeterCollectGroupService;
import cn.com.cdboost.collect.service.RefundService;
import cn.com.cdboost.collect.vo.PageResult;
import com.alibaba.fastjson.JSON;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @desc 	数据报表
 **/
@Controller
@RequestMapping(value = "/report")
public class ReportController {

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);
    @Autowired
    private GenerateFileService generateFileService;
    @Autowired
    private MeterCollectGroupService meterCollectGroupService;
    @Autowired
    private KeyCollectionService keyCollectionService;
    @Autowired
    RefundService refundService;
    /**
     * 客户正向有功总报表
     * @param session
     * @param queryParam
     * @return
     */
    @Auth(menuID = {100056L},actionID = {32L})
    @SystemControllerLog(description = "客户正向有功总报表")
    @RequestMapping(value = "queryPr0", method = RequestMethod.POST)
    @ResponseBody
    public String queryPr0(HttpSession session, @RequestBody QueryPr0Param queryParam){
        PageResult<List<MeterCollectGroupDto>> result = new PageResult<>();

        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);

        QueryPr0Vo queryVo = new QueryPr0Vo();
        BeanUtils.copyProperties(queryParam,queryVo);
        List<MeterCollectGroupDto> meterCollectGroupDtos = meterCollectGroupService.queryPr0(currentUser.getId(),queryVo);

        result.setData(meterCollectGroupDtos);
        result.setTotal(queryVo.getTotal());
        return JSON.toJSONString(result);
    }

    /**
     * 客户正向有功总报表下载
     * @param session
     * @param queryParam
     * @return
     */
    @SystemControllerLog(description = "客户正向有功总报表下载")
    @RequestMapping(value = "queryPr0Down")
    @ResponseBody
    public void queryPr0Down(HttpServletResponse response, HttpSession session, QueryPr0Param queryParam){

        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);

        QueryPr0Vo queryVo = new QueryPr0Vo();
        BeanUtils.copyProperties(queryParam,queryVo);
        List<MeterCollectGroupDto> meterCollectGroupDtos = meterCollectGroupService.queryPr0(currentUser.getId(),queryVo);
        try {
            XSSFWorkbook workBook = generateFileService.generateReadValue("客户正向有功总报表",meterCollectGroupDtos);
            // 通过Response把数据以Excel格式保存
            response.reset();
            // 设置response流信息的头类型，MIME码
            response.setContentType("application/vnd.ms-excel");
            ServletOutputStream out;
            response.addHeader("Content-Disposition", "attachment;filename=\""
                    + new String(("导出表" + System.currentTimeMillis() + ".xls").getBytes("utf-8"), "ISO8859_1")
                    + "\"");
            // 创建输出流对象
            out = response.getOutputStream();
            // 将创建的Excel对象利用二进制流的形式强制输出到客户端去
            workBook.write(out);
            // 强制将数据从内存中保存
            out.flush();
            out.close();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    /**
     * 客户充值记录
     * @param session
     * @param queryParam
     * @return
     */
    @SystemControllerLog(description = "客户充值记录")
    @Auth(menuID = {100056L},actionID = {35L})
    @RequestMapping(value = "queryRechargeRecord", method = RequestMethod.POST)
    @ResponseBody
    public String queryRechargeRecord(HttpSession session, @RequestBody QueryPr0Param queryParam){
        //实例化结果集
        PageResult<QueryRechargeRecordInfo> result = new PageResult<QueryRechargeRecordInfo>();
        //获取当前登录用户信息
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        QueryPr0Vo queryVo = new QueryPr0Vo();
        BeanUtils.copyProperties(queryParam,queryVo);
        //查询数据
        QueryRechargeRecordInfo queryRechargeRecordInfo = refundService.queryRechargeRecord(queryVo,currentUser.getId());
        if(queryRechargeRecordInfo==null){
            result.error("未查询到数据");
            return JSON.toJSONString(result);
        }

        result.setData(queryRechargeRecordInfo);
        result.setTotal(queryVo.getTotal());
        return JSON.toJSONString(result);
    }

    /**
     * 客户充值记录报表下载
     * @param session
     * @param queryParam
     * @return
     */
    @SystemControllerLog(description = "客户充值记录报表下载")
    @RequestMapping(value = "queryRechargeRecordDown", method = RequestMethod.POST)
    @ResponseBody
    public void queryRechargeRecordDown(HttpServletResponse response, HttpSession session, QueryPr0Param queryParam){

        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);

        QueryPr0Vo queryVo = new QueryPr0Vo();
        BeanUtils.copyProperties(queryParam,queryVo);
        //查询数据
        QueryRechargeRecordInfo queryRechargeRecordInfo = refundService.queryRechargeRecord(queryVo,currentUser.getId());
        try {
            if(queryRechargeRecordInfo==null){
                return ;
            }
            XSSFWorkbook workBook = generateFileService.generateRechargeRecord("客户充值记录报表下载",queryRechargeRecordInfo.getList());
            // 通过Response把数据以Excel格式保存
            response.reset();
            // 设置response流信息的头类型，MIME码
            response.setContentType("application/vnd.ms-excel");
            ServletOutputStream out;
            response.addHeader("Content-Disposition", "attachment;filename=\""
                    + new String(("导出表" + System.currentTimeMillis() + ".xls").getBytes("utf-8"), "ISO8859_1")
                    + "\"");
            // 创建输出流对象
            out = response.getOutputStream();
            // 将创建的Excel对象利用二进制流的形式强制输出到客户端去
            workBook.write(out);
            // 强制将数据从内存中保存
            out.flush();
            out.close();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    /**
     * 3.4.3.5客户退费记录
     * @param session
     * @param queryRefundRecordVo
     * @return
     */
    @SystemControllerLog(description = "客户退费记录")
    @Auth(menuID = {100056L},actionID = {36L})
    @RequestMapping(value = "/queryRefundRecord")
    @ResponseBody
    public String queryRefundRecord(HttpSession session, @RequestBody QueryRefundRecordVo queryRefundRecordVo){
        //实例化结果集
        PageResult<QueryRefundRecordInfo> result = new PageResult<QueryRefundRecordInfo>();
        //获取当前登录用户信息
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        //查询数据
        QueryRefundRecordInfo queryRefundRecordInfo = refundService.queryRefundRecord(queryRefundRecordVo,currentUser.getId());
        if(queryRefundRecordInfo==null){
            result.error("未查询到数据");
            return JSON.toJSONString(result);
        }
        //查询总数
        Long total = refundService.queryRefundRecordCount(queryRefundRecordVo,currentUser.getId());
        result.setData(queryRefundRecordInfo);
        result.setTotal(total);
        return JSON.toJSONString(result);
    }
    /**
     *客户退费记录下载
     * @param session
     * @param queryRefundRecordVo
     * @return
     */
    @SystemControllerLog(description = "客户退费记录下载")
    @RequestMapping(value = "/refundRecordDown")
    @ResponseBody
    public void downloadRefundRecord(HttpSession session, HttpServletResponse response, QueryRefundRecordVo queryRefundRecordVo){
        //实例化结果集
        PageResult<RefundQueryListInfo> result = new PageResult<RefundQueryListInfo>();
        //获取当前登录用户信息
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        try {
            QueryRefundRecordInfo queryRefundRecordInfo = refundService.queryRefundRecord(queryRefundRecordVo,currentUser.getId());
            if(queryRefundRecordInfo!=null&&queryRefundRecordInfo.getList()!=null&&queryRefundRecordInfo.getList().size()>0){
                XSSFWorkbook workBook = generateFileService.generateQueryRefundRecordExcel("客户退费记录", queryRefundRecordInfo.getList());
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
            }
        } catch (Exception e) {
            logger.error("系统异常：",e);
        }
    }
}
