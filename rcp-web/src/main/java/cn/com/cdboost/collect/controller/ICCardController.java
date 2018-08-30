package cn.com.cdboost.collect.controller;


import cn.com.cdboost.collect.aop.SystemControllerLog;
import cn.com.cdboost.collect.constant.FeeControlConstant;
import cn.com.cdboost.collect.dao.FeePayMapper;
import cn.com.cdboost.collect.dto.*;
import cn.com.cdboost.collect.dto.param.*;
import cn.com.cdboost.collect.dto.response.IcCardAddReturnInfo;
import cn.com.cdboost.collect.dto.response.IcCardQueryDetailInfo;
import cn.com.cdboost.collect.enums.Action;
import cn.com.cdboost.collect.enums.Auth;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.service.CustomerInfoService;
import cn.com.cdboost.collect.service.FeePriceItemService;
import cn.com.cdboost.collect.service.ICCardService;
import cn.com.cdboost.collect.service.UserLogService;
import cn.com.cdboost.collect.util.CNoUtil;
import cn.com.cdboost.collect.util.UuidUtil;
import cn.com.cdboost.collect.vo.PageResult;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zc
 * @desc ICCard卡操作Controller类
 * @create 2017-07-06 09:01
 **/
@Controller
@RequestMapping("/icCard")
public class ICCardController {
    private static final Logger logger = LoggerFactory.getLogger(ICCardController.class);

    @Autowired
    private ICCardService iCCardService;
    @Autowired
    private FeePriceItemService feePriceItemService;
    @Autowired
    private UserLogService userLogService;
    @Autowired
    private CustomerInfoService customerInfoService;
    @Autowired
    private FeePayMapper feePayMapper;
    @Auth(menuID=100034L,actionID={4L,15L,18L})
    @SystemControllerLog(description = "用户开户模块：列表查询")
    @RequestMapping(value = "/queryList")
    @ResponseBody
    public String queryList(HttpSession session, @Valid @RequestBody FeeAcctGetQueryParam queryParam) {
        PageResult<List<AcctInfo>> result = new PageResult<>();
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);

        FeeAcctGetQueryVo queryVo = new FeeAcctGetQueryVo();
        BeanUtils.copyProperties(queryParam,queryVo);
        queryVo.setUserId(String.valueOf(currentUser.getId()));
        List<AcctInfo> list = iCCardService.query(queryVo);
        result.setData(list);
        result.setTotal(queryVo.getTotal());

        return JSON.toJSONString(result);
    }

    @SystemControllerLog(description = "用户开户模块：明细查询")
    @RequestMapping(value = "/queryDetail")
    @ResponseBody
    public String queryDetail(@RequestParam("customerNo") String customerNo,
                              @RequestParam("cno") String cno) {
        Result<IcCardQueryDetailInfo> result = new Result<>();
        // 参数校验
        if (StringUtils.isEmpty(customerNo)) {
            result.error("customerNo不能为空字符串");
            return JSON.toJSONString(result);
        }

        if (StringUtils.isEmpty(cno)) {
            result.error("cno不能为空字符串");
            return JSON.toJSONString(result);
        }

        AcctDetailInfo acctDetailInfo = iCCardService.queryDetail(customerNo,cno);
        if (acctDetailInfo == null) {
            result.error("acctDetailInfo为空");
            return JSON.toJSONString(result);
        }

        List<FeePriceItemParamEntity> feePriceItemList = feePriceItemService.queryData(acctDetailInfo.getPriceSolsCode());
        IcCardQueryDetailInfo detailInfo = new IcCardQueryDetailInfo();
        detailInfo.setAcctDetailInfo(acctDetailInfo);
        detailInfo.setFeePriceItemList(feePriceItemList);
        result.setData(detailInfo);

        return JSON.toJSONString(result);
    }

    /**
     * 点击开户，弹出框数据查询
     * @param customerNo
     * @param cno
     * @param flag
     * @return
     */
    @Auth(menuID={100034L,10001202L,10002101L},actionID={14L})
    @SystemControllerLog(description = "点击开户，弹出框数据查询")
    @RequestMapping(value = "/queryAccount")
    @ResponseBody
    public String queryAccount(@RequestParam("customerNo") String customerNo,
                               @RequestParam("cno") String cno,
                               @RequestParam("flag") Integer flag) {
        Result<IcCardQueryDetailInfo> result = new Result<>();
        // 参数校验
        if (StringUtils.isEmpty(customerNo)) {
            result.error("customerNo不能为空");
            return JSON.toJSONString(result);
        }

        if (StringUtils.isEmpty(cno)) {
            result.error("cno不能为空");
            return JSON.toJSONString(result);
        }

        IcCardQueryDetailInfo detailInfo;
        if(FeeControlConstant.FeeAccountFlag.THREE.getFlag().equals(flag)){
            // 标识重新开户
            detailInfo = readdQueryAccount(customerNo, cno, flag);
        }else {
            // flag=0（新用户开户）,1（补开户卡）的情况
            // 获取原来的数据
            detailInfo = addQueryAccount(customerNo, cno, flag);
        }
        result.setData(detailInfo);

        return JSON.toJSONString(result);
    }

    // 缴费记录：缴费详情：补售电卡弹出框信息查询
    @Auth(menuID=100034L,actionID={15L})
    @SystemControllerLog(description = "缴费记录：缴费详情：补售电卡弹出框信息查询")
    @RequestMapping(value = "/queryRepectPay")
    @ResponseBody
    public String queryRepectPay(@RequestParam("customerNo") String customerNo,
                                 @RequestParam("cno") String cno,
                                 @RequestParam("payGuid") String payGuid) {
        Result<AcctDetailInfo> result = new Result<>();
        if (StringUtils.isEmpty(customerNo)) {
            result.error("customerNo不能为空");
            return JSON.toJSONString(result);
        }

        if (StringUtils.isEmpty(cno)) {
            result.error("cno不能为空");
            return JSON.toJSONString(result);
        }

        if (StringUtils.isEmpty(payGuid)) {
            result.error("payGuid不能为空");
            return JSON.toJSONString(result);
        }
        AcctDetailInfo acctDetailInfo = iCCardService.queryRepectPay(customerNo,cno,payGuid);
        result.setData(acctDetailInfo);

        return JSON.toJSONString(result);
    }

    /**
     * 开户操作
     * @param session
     * @param queryParam
     * @return
     */
    @Auth(menuID=10001202L,actionID=14L)
    @SystemControllerLog(description = "开户操作")
    @RequestMapping(value = "/add")
    @ResponseBody
    public String add(HttpSession session, @Valid @RequestBody IcCardAddQueryParam queryParam) {
        Result<IcCardAddReturnInfo> result = new Result<>();
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);

        // 获取登录用户信息
        IcCardAddParamVo addParam = new IcCardAddParamVo();
        BeanUtils.copyProperties(queryParam,addParam);
        String payGuid = UuidUtil.getUuid();
        addParam.setPayGuid(payGuid);
        Integer currentUserId = currentUser.getId();
        iCCardService.add(addParam,Long.valueOf(currentUserId));

        IcCardAddReturnInfo info = new IcCardAddReturnInfo();
        info.setChargeUserName(currentUser.getUserName());
        info.setPayGuid(payGuid);
        info.setCreateDate(new Date());
        result.setData(info);
//        userLogService.create(currentUser.getId(), Action.OPEN_ICCARD.getActionId(),"新用户第一次开户","customerNo",queryParam.getCustomerNo(),"设备["+CNoUtil.getDeviceNoByCno(queryParam.getCno())+"]开户缴费["+queryParam.getPayment().setScale(2,BigDecimal.ROUND_HALF_UP)+"]元,写卡["+queryParam.getPayMoney().setScale(2,BigDecimal.ROUND_HALF_UP)+"]元" , JSON.toJSONString(queryParam));
        return JSON.toJSONString(result);
    }

    /**
     * 前端开卡成功，回调更新
     * @param session
     * @param cno 设备cno
     * @param customerNo 客户唯一标识
     * @param payGuid guid
     * @param flag 1表示补卡开户操作，0表示非补卡开户
     * @return
     */
    @Auth(menuID=10001202L,actionID=14L)
    @SystemControllerLog(description = "前端开卡成功，回调更新")
    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(HttpSession session,
                         @RequestParam("cno") String cno,
                         @RequestParam("customerNo") String customerNo,
                         @RequestParam("payGuid") String payGuid,
                         @RequestParam("flag") Integer flag) {
        Result result = new Result("开户成功");
        if (StringUtils.isEmpty(cno)) {
            result.error("cno不能为空");
            return JSON.toJSONString(result);
        }

        if (StringUtils.isEmpty(customerNo)) {
            result.error("customerNo不能为空");
            return JSON.toJSONString(result);
        }

        if (StringUtils.isEmpty(payGuid)) {
            result.error("payGuid不能为空");
            return JSON.toJSONString(result);
        }
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        if(0==flag){
        }else{
            userLogService.create(currentUser.getId(), Action.MODIFY.getActionId(),"开卡成功（回调）更新","customerNo",customerNo,"设备["+ CNoUtil.getDeviceNoByCno(cno)+"]补开户卡","修改操作: cno:" + cno + ",customerNo:" + customerNo + ",payGuid:" + payGuid+",flag:"+flag);
        }


        // 更新信息
        iCCardService.update(cno, customerNo, Long.valueOf(currentUser.getId()),payGuid,flag);
//        FeePayDTO feePay = feePayMapper.selectByPayGuid(payGuid);
        // 中天没有开户，直接new一个
        FeePayDTO feePay = new FeePayDTO();
        FeePayCopyDTO feePayCopyDTO=new FeePayCopyDTO();
        BeanUtils.copyProperties(feePay,feePayCopyDTO);
        feePayCopyDTO.setPayMethod(feePay);
        result.setData(feePayCopyDTO);
        return JSON.toJSONString(result);
    }


    /**
     * 换卡查询
     * @param queryParam
     * @return
     */
    @Auth(menuID=10001202L,actionID={14L,15L,19L})
    @SystemControllerLog(description = "换卡查询")
    @RequestMapping(value = "/queryChangeICCard")
    @ResponseBody
    public String queryChangeICCard(@Valid @RequestBody ChangeICCardQueryParam queryParam) {
        PageResult<List<FeeChangeICCardInfo>> result = new PageResult<>();
        List<FeeChangeICCardInfo> list = iCCardService.queryChangeICCard(queryParam.getCno(),queryParam.getCustomerNo());
        result.setData(list);
        result.setTotal(Long.valueOf(list.size()));

        return JSON.toJSONString(result);
    }

    /**
     * 换卡(补卡)
     * @param session
     * @param param
     * @return
     */
    @Auth(menuID=10001202L,actionID=15L)
    @SystemControllerLog(description = "换卡(补卡)")
    @RequestMapping(value = "/addChangeICCard")
    @ResponseBody
    public String addChangeICCard(HttpSession session, @Valid @RequestBody ChangeICCardParam param) {
        Result result = new Result();
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        userLogService.create(currentUser.getId(), Action.CHANGE_ICCARD.getActionId(),"换卡(补卡)","cNo",param.getCno(),"设备["+CNoUtil.getDeviceNoByCno(param.getCno())+"]补售电卡操作", JSON.toJSONString(param));
        ChangeICCardParamVo paramVo = new ChangeICCardParamVo();
        BeanUtils.copyProperties(param,paramVo);
        paramVo.setUserId(currentUser.getId());
        Map map = iCCardService.addChangeICCard(paramVo);
        result.setData(map);
        return JSON.toJSONString(result);
    }

    /**
     * 注销用户操作
     * @param session
     * @param customerNo
     * @return
     */
    @Auth(menuID=10002101L,actionID={21L})
    @SystemControllerLog(description = "注销用户操作")
    @RequestMapping(value = "/closeAccount")
    @ResponseBody
    public String closeAccount(HttpSession session, @RequestParam("customerNo")String customerNo) {
        Result result = new Result();
        if (StringUtils.isEmpty(customerNo)) {
            result.error("customerNo不能为空");
            return JSON.toJSONString(customerNo);
        }
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        userLogService.create(currentUser.getId(), Action.CHANGE_ICCARD.getActionId(),"销户","customerNo",customerNo,"用户销户操作" , customerNo);
        customerInfoService.closeAccount(customerNo);

        return JSON.toJSONString(result);
    }

    /**
     * 重新开户
     * @param session
     * @param param
     * @return
     */
    @Auth(menuID=10001202L,actionID=19L)
    @SystemControllerLog(description = "重新开户")
    @RequestMapping(value = "/reAdd")
    @ResponseBody
    public String reAdd(HttpSession session, @Valid @RequestBody CustomerReAcctParam param) {
        Result<IcCardAddReturnInfo> result = new Result<>();
        LoginUser currentUser = (LoginUser)session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);



        CustomerReAcctVo acctVo = new CustomerReAcctVo();
        BeanUtils.copyProperties(param,acctVo);
        acctVo.setUserId(String.valueOf(currentUser.getId()));
        String payGuid = UuidUtil.getUuid();
        acctVo.setPayGuid(payGuid);
        iCCardService.reAdd(acctVo);

        IcCardAddReturnInfo returnInfo = new IcCardAddReturnInfo();
        returnInfo.setPayGuid(payGuid);
        returnInfo.setChargeUserName(currentUser.getUserName());
        returnInfo.setCreateDate(new Date());
        result.setData(returnInfo);
//        userLogService.create(currentUser.getId(), Action.CHANGE_ICCARD.getActionId(),"重新开户","cno",param.getCno(),"设备["+CNoUtil.getDeviceNoByCno(param.getCno())+"]重新开户缴费["+param.getPayment()+"]元,写卡["+param.getPayMoney()+"]元",JSON.toJSONString(param));
        return JSON.toJSONString(result);
    }

    private IcCardQueryDetailInfo addQueryAccount(String customerNo, String cno, int flag) {
        IcCardQueryDetailInfo detailInfo = this.queryAccountMethod(customerNo, cno, flag);
        return detailInfo;
    }

    private IcCardQueryDetailInfo readdQueryAccount(String customerNo, String cno, int flag) {
        IcCardQueryDetailInfo detailInfo = this.queryAccountMethod(customerNo, cno, flag);
        return detailInfo;
    }

    private IcCardQueryDetailInfo queryAccountMethod(String customerNo, String cno, int flag) {
        AcctDetailInfo acctDetailInfo = iCCardService.queryAccount(customerNo,cno,flag);
        List<FeePriceItemParamEntity> feePriceItemList = null;
        if(acctDetailInfo != null){
            feePriceItemList = feePriceItemService.queryData(acctDetailInfo.getPriceSolsCode());
        }

        IcCardQueryDetailInfo detailInfo = new IcCardQueryDetailInfo();
        detailInfo.setAcctDetailInfo(acctDetailInfo);
        detailInfo.setFeePriceItemList(feePriceItemList);
        return detailInfo;
    }
}
