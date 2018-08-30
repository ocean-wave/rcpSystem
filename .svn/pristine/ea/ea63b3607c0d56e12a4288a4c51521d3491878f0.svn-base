package cn.com.cdboost.collect.aspect;

import com.alibaba.fastjson.JSON;
import net.sf.json.JSONObject;
import org.apache.commons.lang.ArrayUtils;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Parameter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;


/**
 * @author wt
 * @desc
 * @create in  2018/5/7
 **/
@Aspect
public class LogAspect  implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
    // before the actual handler will be executed
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        if (handler instanceof HandlerMethod) {
            StringBuilder sb = new StringBuilder(1000);
            HandlerMethod handlerMethod = (HandlerMethod)handler;
            MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
            for(MethodParameter methodParameter : methodParameters) {
                System.out.println(methodParameter.getParameterType().getName());
                JSON.toJSONString(methodParameter);
            }
            sb.append("-----------------------").append(simpleDateFormat.format(new Date()))
                    .append("-------------------------------------\n");
            HandlerMethod h = (HandlerMethod) handler;
            sb.append("Controller: ").append(h.getBean().getClass().getName()).append("\n");
            sb.append("Method    : ").append(h.getMethod().getName()).append("\n");
            sb.append("Params    : ").append(getParamString(h.getMethod().getParameters())).append("\n");
            sb.append("Params    : ").append(h.getMethodParameters()).append("\n");
            sb.append("URI       : ").append(request.getRequestURI()).append("\n");
            logger.info(sb.toString());
        }
        return true;
    }

    // after the handler is executed
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws IOException {
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;
        if(handler instanceof HandlerMethod){
            StringBuilder sb = new StringBuilder(1000);
            sb.append("CostTime  : ").append(executeTime).append("ms").append("\n");
            sb.append("return : ").append(response);
            sb.append("-------------------------------------------------------------------------------");
            logger.info(sb.toString());
        }
    }

    private String getParamString(Parameter[] map) throws IllegalAccessException, InstantiationException {
        StringBuilder sb = new StringBuilder();
        if (!CollectionUtils.isEmpty(Arrays.asList(map))) {
            for (Parameter parameter : map) {
                Class<?> type = parameter.getType();
                Object o = type.newInstance();
                if (o instanceof ServletRequest || o instanceof ServletResponse || o instanceof HttpSession) {
                    continue;
                }
                String argJson = JSON.toJSONString(parameter);
                sb.append(parameter.getName() + "=" + argJson).append(",");
            }
        }
        int lastIndex = sb.lastIndexOf(",");
        if (lastIndex > 0) {
            sb.deleteCharAt(sb.lastIndexOf(","));
        }

        return sb.toString();
    }

    @Override
    public void afterCompletion(HttpServletRequest arg0,
                                HttpServletResponse arg1, Object arg2, Exception arg3)
            {

    }
}
