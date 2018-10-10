package cn.com.cdboost.collect.controller;


import cn.com.cdboost.collect.aspect.Systemlog;
import cn.com.cdboost.collect.enums.ResultCode;
import cn.com.cdboost.collect.param.*;
import cn.com.cdboost.collect.service.*;
import cn.com.cdboost.collect.util.DateUtil;
import cn.com.cdboost.collect.util.TokenUtil;
import cn.com.cdboost.collect.vo.DayElectricInfoResponseVo;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by Administrator on 2018/5/4 0004.
 */
@Controller
@RequestMapping(value = "/appapi")
public class ZTController {

    @Value("${Token}")
    private  String Token;

    @Resource
    private CustomerOnOffService CustomerOnOffService;

    @Resource
    private DayElectricInfoService DayElectricInfoService;
    @Resource
    private HistoryElectricAmountService HistoryElectricAmountService;

    @Resource
    private PaymentRecordService PaymentRecordService;
    @Resource
    private RemainAmountService RemainAmountService;


    @RequestMapping("pushOnOffMessage")
    @Systemlog("博高能效系统推送允许合闸")
    @ResponseBody
    public String pushOnOffMessage(OnOffMessageParam onOffMessageParam) {

        return null;

    }

    @RequestMapping("pushArrearageMessage")
    @Systemlog("博高能效系统推送欠费消息")
    @ResponseBody
    public String pushArrearageMessage(ArrearageMessageParam arrearageMessageParam) {

        return null;
    }

    @RequestMapping("queryRemainAmount")
    @Systemlog("查询剩余金额")
    @ResponseBody
    public String queryRemainAmount(@Valid @RequestBody RemainAmount remainAmount) throws NoSuchAlgorithmException {
        Result result = new Result();
        String generate = TokenUtil.generate(Token, DateUtil.pareseToken());
        if (!generate.equals(remainAmount.getToken())) {
            result.error(Result.errorType.tokenerror.getcode(), Result.errorType.tokenerror.getdesc());
            return JSON.toJSONString(result);
        }
        RemainAmountResponse remainAmountResponse = RemainAmountService.queryRemainAmount(remainAmount);
        result.setRows(remainAmountResponse);
        return JSON.toJSONString(result);
    }

    @RequestMapping("queryPaymentRecord")
    @Systemlog("查询缴费记录")
    @ResponseBody
    public String queryPaymentRecord(@Valid @RequestBody PaymentRecordParam paymentRecordParam) throws NoSuchAlgorithmException {
        Result result = new Result();
        String generate = TokenUtil.generate(Token, DateUtil.pareseToken());
        if (!generate.equals(paymentRecordParam.getToken())) {
            result.error(Result.errorType.tokenerror.getcode(), Result.errorType.tokenerror.getdesc());
            return JSON.toJSONString(result);
        }
        PageInfo remainAmountResponse = PaymentRecordService.queryPaymentRecord(paymentRecordParam);
        result.setRows(remainAmountResponse.getList());
        result.setTotal(remainAmountResponse.getTotal());
        return JSON.toJSONString(result);

    }

    @RequestMapping("queryDayElectricInfo")
    @Systemlog("查询当日用电信息")
    @ResponseBody
    public String queryDayElectricInfo(@Valid @RequestBody DayElectricInfoParam dayElectricInfoParam) throws NoSuchAlgorithmException {
        Result result = new Result();
        String generate = TokenUtil.generate(Token, DateUtil.pareseToken());
        if (!generate.equals(dayElectricInfoParam.getToken())) {
            result.error(Result.errorType.tokenerror.getcode(), Result.errorType.tokenerror.getdesc());
            return JSON.toJSONString(result);
        }
        List<DayElectricInfoResponseVo> dayElectricInfoResponseParams = DayElectricInfoService.queryDayElectricInfo(dayElectricInfoParam);
        result.setRows(dayElectricInfoResponseParams);
        result.setTotal(Long.valueOf(dayElectricInfoResponseParams.size()));
        return JSON.toJSONString(result);
    }

    @RequestMapping("queryHistoryElectricAmount")
    @Systemlog("查询历史用电量")
    @ResponseBody
    public String queryHistoryElectricAmount(@Valid  @RequestBody HistoryElectricAmountParam historyElectricAmountParam) throws ParseException, NoSuchAlgorithmException {
        Result result = new Result();
        String generate = TokenUtil.generate(Token, DateUtil.pareseToken());
        if (!generate.equals(historyElectricAmountParam.getToken())) {
            result.error(Result.errorType.tokenerror.getcode(), Result.errorType.tokenerror.getdesc());
            return JSON.toJSONString(result);
        }
        List<DayElectricInfoResponseVo> dayElectricInfoResponseParam = HistoryElectricAmountService.queryHistoryElectricAmount(historyElectricAmountParam);
        result.setTotal(Long.valueOf(dayElectricInfoResponseParam.size()));
        result.setRows(dayElectricInfoResponseParam);
        return JSON.toJSONString(result);
    }

    @RequestMapping("customerOnOff")
    @Systemlog("用户拉合闸接口")
    @ResponseBody
    public String customerOnOff(HttpSession session, @Valid  @RequestBody CustomerOnOffParam customerOnOffParam) throws NoSuchAlgorithmException {
        Result<String> result = new Result<>();
        String generate = TokenUtil.generate(Token, DateUtil.pareseToken());
        if (!generate.equals(customerOnOffParam.getToken())) {
            result.error(Result.errorType.tokenerror.getcode(), Result.errorType.tokenerror.getdesc());
            return JSON.toJSONString(result);
        }
        FeeOnOffQueryParam feeOnOffQueryParam = CustomerOnOffService.customerOnOffParam(customerOnOffParam);
        String id = session.getId();
        Integer sendResult = CustomerOnOffService.customerOnOff(id,feeOnOffQueryParam);
        if (sendResult != ResultCode.Success.getValue()) {
            result.error("指令返回结果失败");
        }
        return JSON.toJSONString(result);
    }
}
