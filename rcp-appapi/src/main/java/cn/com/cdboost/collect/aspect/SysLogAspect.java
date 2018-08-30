package cn.com.cdboost.collect.aspect;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 系统日志切面类
 */
@Component
@Aspect
public class SysLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(SysLogAspect.class);

    // Controller层切点
    @Pointcut("@annotation(cn.com.cdboost.collect.aspect.Systemlog)")
    public void controllerAspect() {
    }

    @Before("controllerAspect() && @annotation(systemControllerLog)")
    public void doBefore(JoinPoint joinPoint, Systemlog systemControllerLog) {
        StringBuilder sb = new StringBuilder();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        sb.append("\n");
        sb.append("url=" + url).append("\n");
        sb.append("method=" + method).append("\n");
        sb.append("uri=" + uri).append("\n");

        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        sb.append("请求方法：" + targetName + "." + methodName + "()").append("\n");
        sb.append("方法描述：" + systemControllerLog.value()).append("\n");
        String argsNameAndValue = this.getArgsNameAndValue(joinPoint);
        sb.append("方法参数：" + argsNameAndValue);

        logger.info(sb.toString());
    }

    @AfterReturning(pointcut = "controllerAspect()", returning = "returnVal")
    public void doAfterReturning(JoinPoint joinPoint, Object returnVal) {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        String className = targetName.substring(targetName.lastIndexOf(".") + 1);
        String prefix = className + "." + methodName + "()";
        StringBuffer sb = new StringBuffer();
        sb.append("\n");
        sb.append("请求方法：" + prefix).append("\n");
        sb.append("请求返回：" + returnVal);

        logger.info(sb.toString());
    }

    /**
     * 获取切面方法的参数名称和参数值
     * @param joinPoint
     * @return param1=value1,param2=value2,param3=value3
     */
    private String getArgsNameAndValue(JoinPoint joinPoint) {
        StringBuilder sb = new StringBuilder();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        String[] parameterNames = methodSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        if (!ArrayUtils.isEmpty(parameterNames)) {
            for (int i = 0; i < parameterNames.length; i++) {
                Object arg = args[i];
                if (arg instanceof ServletRequest || arg instanceof ServletResponse || arg instanceof HttpSession) {
                    continue;
                }
                String argJson = JSON.toJSONString(arg);
                sb.append(parameterNames[i] + "=" + argJson).append(",");
            }
        }
        int lastIndex = sb.lastIndexOf(",");
        if (lastIndex > 0) {
            sb.deleteCharAt(sb.lastIndexOf(","));
        }

        return sb.toString();
    }

}
