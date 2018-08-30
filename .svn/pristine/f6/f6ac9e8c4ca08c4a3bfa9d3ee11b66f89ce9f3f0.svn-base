package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.dto.response.ChargeUserDetail;
import cn.com.cdboost.collect.dto.response.FeePayDetailDto;
import cn.com.cdboost.collect.dto.response.RemoteRechargeInfo;
import cn.com.cdboost.collect.enums.Action;
import cn.com.cdboost.collect.enums.Auth;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.service.*;
import cn.com.cdboost.collect.util.CNoUtil;
import cn.com.cdboost.collect.util.MathUtil;
import cn.com.cdboost.collect.util.UuidUtil;
import cn.com.cdboost.collect.vo.PageResult;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import net.sf.json.util.JSONUtils;
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
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * @author xzy
 * @desc 充值缴费操作
 * @create 2017/7/5 0005
 **/
@Controller
@RequestMapping(value = "pay")
public class FeePayController {
    private static final Logger logger = LoggerFactory.getLogger(FeePayController.class);

    @Autowired
    private FeePayService feePayService;
    @Autowired
    private UserLogService userLogService;
    @Autowired
    private GenerateFileService generateFileService;
    @Autowired
    private InstructService instructService;

    @Autowired
    private CustomerInfoService customerInfoService;

    // 充值缴费页面，远程充值
    @Auth(menuID=10001201L,actionID={10L,11L,12L})
    @SystemControllerLog(description = "充值缴费")
    @RequestMapping(value = "remoteRecharge" , method = RequestMethod.POST)
    @ResponseBody
    public String remoteRecharge(HttpSession session, @Valid @RequestBody RemoteRechargeParam param){
        Result<RemoteRechargeInfo> result = new Result("充值成功");
        LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        String payGuid = UuidUtil.getUuid();
        RemoteRechargeVo rechargeVo = new RemoteRechargeVo();
        BeanUtils.copyProperties(param,rechargeVo);
        rechargeVo.setPayGuid(payGuid);
        rechargeVo.setUserId(user.getId());
        BigDecimal remainAmount = feePayService.remoteRecharge(rechargeVo);

        RemoteRechargeInfo rechargeInfo = new RemoteRechargeInfo();
        rechargeInfo.setCreateDate(new Date());
        rechargeInfo.setPayGuid(payGuid);
        result.setData(rechargeInfo);
        cn.com.cdboost.collect.model.CustomerInfo customerInfo = customerInfoService.queryByCustomerNo(param.getCustomerNo());
        userLogService.create(user.getId(), Action.FEE_PAY.getActionId(),"充值缴费","customerNo", param.getCustomerNo(),"用户["+customerInfo.getCustomerName()+"]充值缴费["+param.getPayMoney()+"]元" , JSONUtils.valueToString(param));

        // 充值后剩余金额大于0，发送取消拉闸指令
        int i = MathUtil.compareTo(remainAmount, BigDecimal.ZERO);
        if (i > 0) {
            CancelOffParam cancelOffParam=new CancelOffParam();
            cancelOffParam.setCno("");
            cancelOffParam.setCustomerNo(param.getCustomerNo());
            cancelOffParam.setGuid(rechargeVo.getPayGuid());
            cancelOffParam.setJzqNo("999999999");
            // 发送取消拉闸指令
            int sendResult = instructService.CancelOff(cancelOffParam);
            if(sendResult != 1){
                // 表示调用中间件失败
                result.setMessage("充值成功，发送取消拉闸指令失败");
            }
        }
        return JSON.toJSONString(result);
    }

    // 查询远程充值结果状态
    @Auth(menuID = {10001202L,10001201L}, actionID = {5L,11L})
    @SystemControllerLog(description = "查询远程充值结果状态")
    @RequestMapping(value = "readStatus", method = RequestMethod.POST)
    @ResponseBody
    public String readStatus(HttpSession session, @RequestParam String payGuid, @RequestParam Integer type) {
        Result<Integer> result = new Result("召测成功");
        if (StringUtils.isEmpty(payGuid)) {
            result.error("payGuid不能为空");
            return JSON.toJSONString(result);
        }
        int status;
        if (type == 1) {
            status = feePayService.rechargeStatus(payGuid);
        } else {
            status = feePayService.getReadStatus(payGuid);
        }
        // 前端根据状态是否是101，如果是101则轮询
        result.setData(status);
        if (status == 1) {
            // success
        } else if (status == 101) {
            result.setMessage("正在执行中");
        } else {
            result.setMessage(type == 1 ? "远程充值失败" : "召测数据失败");
        }

        return JSON.toJSONString(result);
    }

    // 查询充值用户信息列表，包含最后一次充值记录信息
    @SystemControllerLog(description = "查询用户的最后一次购电记录")
    @RequestMapping(value = "queryUser", method = RequestMethod.POST)
    @ResponseBody
    public String queryUser(@RequestBody RechargeUserQueryParam queryParam){
        Result<ChargeUserDetail> result = new Result<>();
        ChargeUserDetail detail = feePayService.queryChargeUserDetail(queryParam.getCustomerNo());
        result.setData(detail);
        return JSON.toJSONString(result);
    }

    // 查询用户的IC卡基础信息
    @SystemControllerLog(description = "查询用户的IC卡基础信息")
    @RequestMapping(value = "queryUserPay", method = RequestMethod.POST)
    @ResponseBody
    public String queryUserPay(@RequestParam String customerNo, @RequestParam String deviceNo){
        Result<QueryUserPayDTO> result = new Result<>();
        if (StringUtils.isEmpty(customerNo)) {
            result.error("customerNo不能为空");
            return JSON.toJSONString(result);
        }
        if (StringUtils.isEmpty(deviceNo)) {
            result.error("deviceNo不能为空");
            return JSON.toJSONString(result);
        }

        QueryUserPayDTO queryUserPayDTO = feePayService.queryUserPay(customerNo,deviceNo);
        result.setData(queryUserPayDTO);
        return JSON.toJSONString(result);
    }

    // 费控查询：缴费记录列表查询
    @Auth(menuID=10003401L,actionID={4L})
    @SystemControllerLog(description = "费控查询：缴费记录列表查询")
    @RequestMapping(value = "queryList" , method = RequestMethod.POST)
    @ResponseBody
    public String queryList(HttpSession session, @Valid @RequestBody RechargeRecordQueryParam queryParam){
        PageResult<FeePayRecordInfo> result = new PageResult<>();
        RechargeRecordQueryVo queryVo = new RechargeRecordQueryVo();
        BeanUtils.copyProperties(queryParam,queryVo);
        LoginUser currentUser = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        queryVo.setUserId(Long.valueOf(currentUser.getId()));
        queryVo.setSdate(queryParam.getStartDate());
        queryVo.setEdate(queryParam.getEndDate());

        FeePayRecordInfo info = feePayService.queryByProc(queryVo);
        result.setData(info);
        result.setTotal(queryVo.getTotal());
        return JSON.toJSONString(result);
    }


    @Auth(menuID=10001201L,actionID={10L,11L,12L})
    @SystemControllerLog(description = "更新充值记录的状态")
    @RequestMapping(value = "updateWriteMeter", method = RequestMethod.POST)
    @ResponseBody
    public String updateWriteMeter(HttpSession session,
                                   @RequestParam String payGuid,
                                   @RequestParam Integer writeMeter,
                                   @RequestParam Integer payModel){
        Result result = new Result();
        if (StringUtils.isEmpty(payGuid)) {
            result.error("payGuid不能为空");
            return JSON.toJSONString(result);
        }

        LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        /*userLogService.create(user.getId(), Action.MODIFY.getActionId(),"充值缴费", "payGuid", payGuid,"更新充值记录状态","更新充值记录的状态: payGuid:"+ payGuid + ",writeMeter:" + writeMeter + ",payModel:" + payModel);*/
        feePayService.updateWriteMeter(payGuid, writeMeter, payModel, user.getId(),9);

        return JSON.toJSONString(result);
    }

    // 保存读取的IC卡记录(并更新fee_acct表)
    @Auth(menuID=10001201L,actionID={10L,11L,12L})
    @SystemControllerLog(description = "保存读取的IC卡记录(并更新fee_acct表)")
    @RequestMapping(value = "saveCardRecord", method = RequestMethod.POST)
    @ResponseBody
    public String saveCardRecord(HttpSession session, @Valid @RequestBody FeeReadCardParam param){
        Result result = new Result();
        LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        Integer userId = user.getId();
//        userLogService.create(user.getId(), Action.MODIFY.getActionId(),"充值缴费","customerNo", param.getCustomerNo(), "保存客户"+param.getCustomerNo()+"读取的IC卡记录" , JSON.toJSONString(param));

        feePayService.saveReadCardAndUpdateFeeAcct(param,Long.valueOf(userId));
        return JSON.toJSONString(result);
    }

    // 充值缴费模块，模糊查询用户基础信息(下拉选择)
    @SystemControllerLog(description = "充值缴费模块，模糊查询用户基础信息(下拉选择)")
    @RequestMapping(value = "fuzzyQuery", method = RequestMethod.POST)
    @ResponseBody
    public String fuzzyQuery(HttpSession session, @RequestBody UserFuzzyQueryParam queryParam){
        Result<List<FuzzyQueryDTO>> result = new Result<>();
        LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);

        UserFuzzyQueryVo queryVo = new UserFuzzyQueryVo();
        BeanUtils.copyProperties(queryParam,queryVo);
        queryVo.setUserId(String.valueOf(user.getId()));

        List<FuzzyQueryDTO> list = feePayService.fuzzyQuery(queryVo);
        result.setData(list);

        return JSON.toJSONString(result);
    }

    // 费控管理：缴费记录列表下载
    @Auth(menuID=10003401L,actionID=6L)
    @SystemControllerLog(description = "费控管理：缴费记录列表下载")
    @RequestMapping("/generateExcel")
    public void generateExcel(HttpServletResponse response, HttpSession session, RechargeRecordQueryParam queryParam) {
        LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        userLogService.create(user.getId(), Action.DOWNLOAD.getActionId(),"缴费记录", "deviceNo", queryParam.getDeviceNo(), "下载缴费记录" , JSON.toJSONString(queryParam));

        try {
            RechargeRecordQueryVo queryVo = new RechargeRecordQueryVo();
            BeanUtils.copyProperties(queryParam,queryVo);
            queryVo.setUserId(Long.valueOf(user.getId()));
            queryVo.setSdate(queryParam.getStartDate());
            queryVo.setEdate(queryParam.getEndDate());
            FeePayRecordInfo info = feePayService.queryByProc(queryVo);
            List<QueryProcDTO> list = info.getList();

            XSSFWorkbook workBook = generateFileService.generateFeePayExcel("缴费记录数据", list);
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

    // 通过表计户号获取购电记录信息
    @Auth(menuID=10001201L,actionID={10L,11L,12L})
    @SystemControllerLog(description = "通过表计户号获取购电记录信息")
    @RequestMapping("/getPurchaseRecord")
    @ResponseBody
    public String getPurchaseRecord(HttpSession session, @RequestBody FeePayDetailQueryParam queryParam){
        PageResult<List<QueryProcDTO>> result = new PageResult<>();
        LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);

        FeePayDetailParam param = new FeePayDetailParam();
        BeanUtils.copyProperties(queryParam,param);
        param.setUserId(user.getId());
        String startDate = queryParam.getStartDate();
        if (startDate != null) {
            param.setSdate(startDate);
        }
        String endDate = queryParam.getEndDate();
        if (endDate != null) {
            param.setEdate(endDate);
        }

        FeePayRecordInfo recordInfo = feePayService.searchByMeterUserNo(param);
        List<QueryProcDTO> list = recordInfo.getList();
        result.setData(list);
        result.setTotal(param.getTotal());

        return JSON.toJSONString(result);
    }


    // 通过payGuid获取购电记录详情
    @SystemControllerLog(description = "通过payGuid获取购电记录详情")
    @RequestMapping("/powerPurchaseDetails")
    @ResponseBody
    public String powerPurchaseDetails(@RequestParam String payGuid){
        Result<FeePayDetailDto> result = new Result<>();
        if (StringUtils.isEmpty(payGuid)) {
            result.error("payGuid不能为空");
            return JSON.toJSONString(result);
        }

        FeePayDetailDto feePayDetailDto = feePayService.searchFeePayByPayGuid(payGuid);
        result.setData(feePayDetailDto);
        return JSON.toJSONString(result);
    }

    // 撤销记录(缴费记录页面的撤销充值权限)
    @Auth(menuID=10003401L,actionID=18L)
    @SystemControllerLog(description = "撤销记录(缴费记录页面的撤销充值权限)")
    @RequestMapping("/disablePay")
    @ResponseBody
    public String disablePay(HttpSession session,
                             @RequestParam("payGuid") String payGuid,
                             @RequestParam("reason") String reason,
                             @RequestParam (value = "cNo",required = false) String cNo
    ){
        Result result = new Result();
        if (StringUtils.isEmpty(payGuid)) {
            result.error("payGuid不能为空");
            return JSON.toJSONString(result);
        }

        if (StringUtils.isEmpty(reason)) {
            result.error("reason不能为空");
            return JSON.toJSONString(result);
        }
        LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        userLogService.create(user.getId(), Action.FEE_PAY.getActionId(),"充值缴费", "payGuid", payGuid,"撤销设备["+CNoUtil.getDeviceNoByCno(cNo)+"]缴费记录" ,"设置充值无效操作: payGuid:" + payGuid + ",reason:" + reason);
        DisablePayVo disablePayVo = new DisablePayVo();
        disablePayVo.setPayGuid(payGuid);
        disablePayVo.setReason(reason);
        disablePayVo.setUserId(String.valueOf(user.getId()));
        feePayService.disablePay(disablePayVo);

        result.setMessage("撤销成功");
        return JSON.toJSONString(result);
}

    @Auth(menuID=10001201L,actionID=5L)
    @SystemControllerLog(description = "实时召测剩余与充值次数")
    @RequestMapping(value = "sendReadAmountAndCntCmd", method = RequestMethod.POST)
    @ResponseBody
    public String sendReadAmountAndCntCmd(HttpSession session, @RequestParam String deviceCno) {
        Result<String> result = new Result<>("召测成功");
        if (StringUtils.isEmpty(deviceCno)) {
            result.error("deviceCno不存在");
            return JSON.toJSONString(result);
        }
        LoginUser user = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        userLogService.create(user.getId(), Action.FEE_PAY.getActionId(), "充值缴费", "deviceCno", deviceCno,"设备:["+CNoUtil.getDeviceNoByCno(deviceCno)+"]实时召测剩余及充值次数"," 实时召测剩余与充值次数  deviceCno: " + deviceCno);

        String guid = UuidUtil.getUuid();
        feePayService.sendReadAmountAndCntCmd(user.getId(), deviceCno, guid);
        result.setData(guid);

        return JSON.toJSONString(result);
    }

}
