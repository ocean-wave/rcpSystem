package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.dto.LoginUser;
import cn.com.cdboost.collect.dto.QueryDetailDto;
import cn.com.cdboost.collect.dto.RefundQueryListInfo;
import cn.com.cdboost.collect.dto.param.CustomerRefundVo;
import cn.com.cdboost.collect.dto.param.QueryDetialVo;
import cn.com.cdboost.collect.dto.param.QueryListByCstVo;
import cn.com.cdboost.collect.dto.param.RefundQueryListVo;
import cn.com.cdboost.collect.enums.Action;
import cn.com.cdboost.collect.enums.Auth;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.model.CustomerInfo;
import cn.com.cdboost.collect.service.CustomerInfoService;
import cn.com.cdboost.collect.service.GenerateFileService;
import cn.com.cdboost.collect.service.RefundService;
import cn.com.cdboost.collect.service.UserLogService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 退费控制器
 */
@Controller
@RequestMapping(value = "/refund")
public class RefundController {

    private static final Logger logger = LoggerFactory.getLogger(RefundController.class);
    @Autowired
    RefundService refundService;
    @Autowired
    private GenerateFileService generateFileService;
    @Autowired
    private UserLogService userLogService;
    @Autowired
    private CustomerInfoService customerInfoService;
    /**
     * 3.4.1.1	客户退费列表信息查询接口
     * @param session
     * @param refundQueryListVo
     * @return
     */
     @SystemControllerLog(description = "客户退费列表信息查询")
     @RequestMapping(value = "/queryList")
     @ResponseBody
     public String queryList(HttpSession session,@RequestBody RefundQueryListVo refundQueryListVo){
         //实例化结果集
         PageResult<RefundQueryListInfo> result = new PageResult<RefundQueryListInfo>();
         //获取当前登录用户信息
         LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
         //查询数据
         RefundQueryListInfo refundQueryListInfo = refundService.queryList(refundQueryListVo,currentUser.getId());
         //查询总数
         Long total = refundService.queryCount(refundQueryListVo,currentUser.getId());
         result.setData(refundQueryListInfo);
         result.setTotal(total);
         return JSON.toJSONString(result);
     }
    /**
     * 3.4.1.1	客户退费列表信息下载
     * @param session
     * @param refundQueryListVo
     * @return
     */
    @Auth(menuID=10003402L,actionID={6L})
    @SystemControllerLog(description = "客户退费列表信息下载")
    @RequestMapping(value = "/queryListDown")
    @ResponseBody
    public void downloadQueryList(HttpSession session,HttpServletResponse response, RefundQueryListVo refundQueryListVo){
        //获取当前登录用户信息
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);

        try {
            RefundQueryListInfo refundQueryListInfo = refundService.queryList(refundQueryListVo,currentUser.getId());
            if(refundQueryListInfo!=null){
                XSSFWorkbook workBook = generateFileService.generateQueryListExcel("客户退费列表信息", refundQueryListInfo.getList());
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
    /**
     * 客户退费操作
     * @param session
     * @param customerRefundVo
     * @return
     */
     @Auth(menuID = {10001204L},actionID = {4L,31L})
     @SystemControllerLog(description = "客户退费操作")
     @RequestMapping(value = "/customerRefund")
     @ResponseBody
     public String customerRefund(HttpSession session,@RequestBody CustomerRefundVo customerRefundVo){
        //实例化结果集
         Result result = new Result();
         //获取当前登录用户信息
         LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
         //操作退费
         int is_success = refundService.customerRefund(customerRefundVo,currentUser.getId());
         if(is_success==0){
             result.error("退费失败");
             result.setCode(0);
             return JSON.toJSONString(result);
         }else if(is_success==2){
             result.error("已退费");
             result.setCode(0);
             return JSON.toJSONString(result);
         }else if(is_success==3){
             result.error("剩余金额不足");
             result.setCode(0);
             return JSON.toJSONString(result);
         }else if(is_success==1) {
             //退费成功，记录日志
             CustomerInfo customerInfo = customerInfoService.queryByCustomerNo(customerRefundVo.getCustomerNo());
             userLogService.create(currentUser.getId(), Action.MODIFY.getActionId(), "客户退费操作", "CustomerNo", customerRefundVo.getCustomerNo(), "用户[" + customerInfo.getCustomerName() + "]" + "退费[" + customerRefundVo.getRefundMoney() + "]元", JSON.toJSONString(customerRefundVo));
             result.setData("退费成功");
             result.setMessage("操作成功");
             return JSON.toJSONString(result);
         }else{
             result.error("其他错误");
             result.setCode(0);
             return JSON.toJSONString(result);
         }
     }

    /**
     * 客户退费详细查询
     * @param session
     * @return
     */
     @SystemControllerLog(description = "客户退费详细查询")
     @RequestMapping(value = "/queryDetail")
     @ResponseBody
     public String queryDetail(HttpSession session, @RequestParam String refundGuid){
         //实例化结果集
         Result<QueryDetailDto> result = new Result();
         //实例化查询对象
         QueryDetialVo queryDetialVo = new QueryDetialVo();
         //设置查询参数
         queryDetialVo.setRefundGuid(refundGuid);
         //获取当前登录用户信息
         LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
         //查询数据
         QueryDetailDto queryDetailDto = refundService.queryDetai(queryDetialVo,currentUser.getId());
         //设置数据
         result.setData(queryDetailDto);
         return JSON.toJSONString(result);
     }
    @SystemControllerLog(description = "查询客户的退费记录")
    @RequestMapping(value = "/queryListByCst")
    @ResponseBody
     public String queryListByCst(HttpSession session,@RequestBody QueryListByCstVo queryListByCstVo ){
        //实例化结果集
        Result<List<QueryDetailDto>> result = new Result();
        //获取当前登录用户信息
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        //查询数据
        List<QueryDetailDto> list = refundService.queryListByCst(queryListByCstVo,currentUser.getId());
        //设置数据
        result.setData(list);
        //返回结果集
        return JSON.toJSONString(result);
     }

    /**
     * 查询客户的退费记录下载
     * @param session
     * @param queryListByCstVo
     * @return
     */
    @Auth(menuID = {10001204L},actionID = {4L,6L})
    @SystemControllerLog(description = "查询客户的退费记录下载")
    @RequestMapping(value = "/listByCstDown")
    @ResponseBody
    public void listByCstDown(HttpSession session,HttpServletResponse response,QueryListByCstVo queryListByCstVo){
        //获取当前登录用户信息
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);

        try {
            List<QueryDetailDto> list = refundService.queryListByCst(queryListByCstVo,currentUser.getId());
            if(list!=null&&list.size()>0){
                XSSFWorkbook workBook = generateFileService.generateListByCstExcel("客户的退费记录", list);
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
