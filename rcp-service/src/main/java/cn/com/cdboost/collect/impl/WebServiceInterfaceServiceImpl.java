package cn.com.cdboost.collect.impl;


import cn.com.cdboost.collect.service.WebServiceInterfaceService;
import cn.com.cdboost.collect.util.CryptographyUtil;
import net.sf.json.JSONObject;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.message.SOAPHeaderElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.xml.namespace.QName;

/**
 * webservice服务接口实现类
 */
@org.springframework.stereotype.Service("webServiceInterfaceService")
public class WebServiceInterfaceServiceImpl implements WebServiceInterfaceService {
    private static final Logger logger = LoggerFactory.getLogger(WebServiceInterfaceServiceImpl.class);

    @Value("${endPoint}")
    private String endPoint;
    @Value("${targetNamespace}")
    private String targetNamespace;
    @Value("${userName}")
    private String userName;
    @Value("${userPW}")
    private String userPW;

    @Override
    public String RegeditMote(JSONObject obj) {

        logger.info("WebServiceInterfaceServiceImpl-RegeditMote:query 参数:" + obj);

        String result = "";
        try {
            String method="RegeditMote";
            //new 一个服务
            Service sv = new Service();
            //创建一个call对象
            Call call = (Call) sv.createCall();
            //设置要调用的接口地址以上一篇的为例子
            call.setTargetEndpointAddress(new java.net.URL(endPoint));
            //设置要调用的接口方法
            call.setOperationName(new QName(targetNamespace,method));
            //要调用方法的URI
            call.setSOAPActionURI(targetNamespace+method);
            call.setUseSOAPAction(true);

            //把认证头放入Service
            call.addHeader(getHead());
            //设置参数的名称与类型
            //设置参数名 id  第二个参数表示String类型,第三个参数表示入参
            call.addParameter(new QName(targetNamespace,"regeditInfo"), org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);
            //返回参数类型
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
            //开始调用方法,假设我传入的参数id的内容是 1001 调用之后会根据id返回users信息，以xml格式的字符串返回，也可以json格式主要看对方用什么方式返回
            String param = obj.toString();
            result = (String) call.invoke(new Object[]{param});

            logger.info("WebServiceInterfaceServiceImpl-RegeditMote:query返回值:" + result);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }


    @Override
    public String DelMote(String moteEUI, String cusID) {
        logger.info("WebServiceInterfaceServiceImpl-DelMote:query 参数:" + moteEUI+","+cusID);
        String result = "";
        try {
            String method="DelMote";
            //new 一个服务
            Service svs = new Service();
            //创建一个call对象
            Call call = (Call) svs.createCall();
            //设置要调用的接口地址
            call.setTargetEndpointAddress(new java.net.URL(endPoint));
            //要调用方法的URI
            call.setSOAPActionURI(targetNamespace+method);
            //设置要调用的接口方法
            call.setOperationName(new QName(targetNamespace,method));
            //把认证头放入Service
            call.addHeader(getHead());
            //设置参数的名称与类型
            //设置参数名 id  第二个参数表示String类型,第三个参数表示入参
            call.addParameter(new QName(targetNamespace,"moteEUI"), org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);
            call.addParameter(new QName(targetNamespace,"customerID"), org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);
            //返回参数类型
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
            //开始调用方法,假设我传入的参数id的内容是1001   调用之后会根据id返回users信息，以xml格式的字符串返回，也可以json格式主要看对方用什么方式返回
            result = (String) call.invoke(new Object[]{moteEUI,cusID});
            logger.info("WebServiceInterfaceServiceImpl-DelMote:query 返回值:" + result);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取webService认证头
     * @return
     */
    public SOAPHeaderElement getHead(){
        logger.info("WebServiceInterfaceServiceImpl-getHead: 正在获取认证头");
        /*
        认证密码需要使用32位MD5加密,并获取加密字符串中的第10 到第25之间的字符；
        例如：MD5Encrypt32(pw).Substring(10, 26)
        */
        userPW = CryptographyUtil.encrypt32(userPW).substring(10, 26);
        //设置头信息
        SOAPHeaderElement head =
                new SOAPHeaderElement(new QName(targetNamespace,"SrcSoapHeader"));
        try {
            head.addChildElement("UserName").setValue(userName);
            head.addChildElement("UserPW").setValue(userPW);
        }catch (Exception e){
            e.printStackTrace();
        }
        return head;
    }
}
