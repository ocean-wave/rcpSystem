package cn.com.cdboost.collect.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * session是否失效拦截验证
 */
public class SessionInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);

    // 不需要拦截的url
    private String[] allowUrls;
    // session超时时间
    private Integer timeOut;

    public String[] getAllowUrls() {
        return allowUrls;
    }

    public void setAllowUrls(String[] allowUrls) {
        this.allowUrls = allowUrls;
    }

    public Integer getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Integer timeOut) {
        this.timeOut = timeOut;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        logger.info("进入Session拦截器：请求uri=" + requestURI);

         if (null != allowUrls && allowUrls.length > 0) {
            for (String url : allowUrls) {
                if (requestURI.contains(url)) {
                    return true;
                }
            }
        }

        // 若存在会话则返回该会话，否则返回null
        HttpSession session = request.getSession(false);
        if (session == null) {
            if (requestURI.endsWith("login.do")) {
                // 新建Session并设置超时标志
                request.getSession().setAttribute("TIMEOUT_FLAG", System.currentTimeMillis());
                // 放行，进入controller登录逻辑
                return true;
            } else {
                // session超时
                this.processSession(request,response);
                return false;
            }
        } else {
            if (requestURI.endsWith("login.do")) {
                // 新建Session并设置超时标志
                request.getSession().setAttribute("TIMEOUT_FLAG", System.currentTimeMillis());
                // 放行，进入controller登录逻辑
                return true;
            }
        }

        // 退出系统，删除session标志
        if (requestURI.endsWith("logout.do")) {
            session.removeAttribute("TIMEOUT_FLAG");
            // 放行，进入controller退出逻辑
            return true;
        }

//        // 非前端轮询请求，更新超时标志
//        if(!requestURI.endsWith("websocket.do")){
//            session.setAttribute("TIMEOUT_FLAG", System.currentTimeMillis());
//        }

        Boolean isTimeOut = System.currentTimeMillis() - (long)session.getAttribute("TIMEOUT_FLAG") > timeOut * 60 * 1000;
        if(isTimeOut) {
            logger.info("Session超时，主动将Session置为失效");
            session.invalidate();
            this.processSession(request,response);
            return false;
        }

        // 刷新时间
        session.setAttribute("TIMEOUT_FLAG", System.currentTimeMillis());

        return true;
    }


    /**
     * 处理Session超时返回
     * @param request
     * @param response
     * @throws IOException
     */
    private void processSession(HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS,DELETE,PUT");
        String json = "{\"code\":0,\"data\":302}";
        response.setContentType("text/html;charset=UTF-8");// 解决中文乱码
        PrintWriter writer = response.getWriter();
        writer.write(json);

        // 必须加，否则前端取不到值
        writer.flush();
        writer.close();
        logger.info("请求路径：" + request.getRequestURI() + ",Session超时, 需要重新登录!");
    }
}
