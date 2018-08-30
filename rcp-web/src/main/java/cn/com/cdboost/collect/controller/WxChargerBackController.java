package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.service.WxChargerPayService;
import cn.com.cdboost.collect.util.WeiXinUtil2;
import cn.com.cdboost.collect.util.XMLBeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Controller
@RequestMapping(value = "/back")
public class WxChargerBackController {
    private static final Logger logger = LoggerFactory.getLogger(WxChargerBackController.class);

    @Autowired
    WxChargerPayService wxChargerPayService;

    @Value("${partnerkey}")
    private String partnerkey;


    /**
     * 微信支付通知
     * @return
     */
    @RequestMapping(value = "/notify")
    public void notify(HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("~~~~~~~~~~~~~~~~微信支付通知来啦~~~~~~~~~");
        InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();

        response.setContentType("text/xml");

        String result = new String(outSteam.toByteArray(), "utf-8");
        if(StringUtils.isEmpty(result)) {
            String resString =  "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[非法访问]]></return_msg></xml>";
            response(response,resString);
        }
        logger.info("微信支付通知结果--------->"+result);
        Map<String, String> map =  XMLBeanUtils.readStringXmlOut(result);
        logger.info("微信通知结果map类型:-------->"+result);

        // 商户系统对于支付结果通知的内容一定要做签名验证，防止数据泄漏导致出现“假通知”，造成资金损失。
        boolean flag = WeiXinUtil2.checkSign(map,partnerkey);
        if (!flag) {
            logger.info("支付结果通知的内容签名校验不通过,直接抛弃");
            return;
        }

        if(map.get("result_code").equals("SUCCESS") && map.get("return_code").equals("SUCCESS")) {
            String outTradeNo =  map.get("out_trade_no");
            String totalFee = map.get("total_fee");
            wxChargerPayService.notify(outTradeNo,totalFee);
        } else {
            logger.info("系统通知内容为支付失败");
        }
        String resString = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
        response(response,resString);
    }

    /**
     * 返回输出
     * @param res
     * @param xml
     * @throws IOException
     */
    private void response(HttpServletResponse res,String xml) throws IOException
    {
        res.getWriter().print(xml);
        res.getWriter().flush();
        res.getWriter().close();
    }
}
