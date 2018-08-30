package cn.com.cdboost.collect.listener;

import cn.com.cdboost.collect.dto.param.DeviceRechargeSuccessParam;
import cn.com.cdboost.collect.dto.param.SmsRemoteSuccessParam;
import cn.com.cdboost.collect.model.CustomerDevMap;
import cn.com.cdboost.collect.model.CustomerInfo;
import cn.com.cdboost.collect.model.CustomerWxBind;
import cn.com.cdboost.collect.model.FeePay;
import cn.com.cdboost.collect.service.*;
import cn.com.cdboost.collect.util.CNoUtil;
import cn.com.cdboost.collect.util.MathUtil;
import com.alibaba.fastjson.JSON;
import com.example.clienttest.client.AFN09DataBackLisener;
import com.example.clienttest.client.AFN09Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 远程下发充值成功，回调监听
 */
@Component("aFN09ResultDataBackLisener")
public class AFN09ResultDataBackLisener implements AFN09DataBackLisener{
    private static final Logger logger = LoggerFactory.getLogger(AFN09ResultDataBackLisener.class);

    @Autowired
    private CustomerDevMapService customerDevMapService;
    @Autowired
    private CustomerWxBindService customerWxBindService;
    @Autowired
    private WeixinService weixinService;
    @Autowired
    private CustomerInfoService customerInfoService;
    @Autowired
    private FeePayService feePayService;
    @Autowired
    private AliyunSmsService aliyunSmsService;

    @Override
    public void onDataBack(AFN09Object afn09Object) {
        logger.info("远程下发充值成功回调接口接收数据：AFN09Object=" + JSON.toJSONString(afn09Object) );
        int status = afn09Object.getStatus();
        if (status == 1) {
            // 远程下发成功,
            String queueGuid = afn09Object.getQueueGuid();
            String deviceType = afn09Object.getMoteType();
            String dbdz = afn09Object.getDbdz();
            String cno = CNoUtil.CreateCNo(deviceType, dbdz);

            // 查询该设备所关联的所有微信号
            CustomerDevMap devMap = customerDevMapService.queryByCno(cno);
            List<CustomerWxBind> wxBinds = customerWxBindService.queryByParams(devMap.getMeterUserNo(), deviceType);
            if (CollectionUtils.isEmpty(wxBinds)) {
                return;
            }

            CustomerInfo customerInfo = customerInfoService.queryByCustomerNo(devMap.getCustomerNo());
            String customerNo = customerInfo.getCustomerNo();
            String customerName = customerInfo.getCustomerName();
            // 查询支付信息
            FeePay feePay = feePayService.queryByParams(queueGuid, cno);
            Integer payMethod = feePay.getPayMethod();

            // 发送微信通知
            DeviceRechargeSuccessParam param = new DeviceRechargeSuccessParam();
            param.setCno(cno);
            param.setCustomerName(customerName);
            param.setPayMethod(payMethod);
            BigDecimal decimal = MathUtil.setPrecision(feePay.getPayMoney());
            param.setPayMoney(decimal.toString());
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
            String formatStr = format.format(new Date());
            param.setPayTime(formatStr);
            weixinService.sendWeChatRechargeSuccess(param);

            // 发送短信通知
            SmsRemoteSuccessParam remoteParam = new SmsRemoteSuccessParam();
            remoteParam.setCustomerNo(customerNo);
            remoteParam.setCno(cno);
            remoteParam.setCustomerName(customerName);
            remoteParam.setPayMethod(payMethod);
            remoteParam.setPayMoney(decimal.toString());
            remoteParam.setPayTime(formatStr);
            aliyunSmsService.remoteRechargeSuccess(remoteParam);
        }
    }
}
