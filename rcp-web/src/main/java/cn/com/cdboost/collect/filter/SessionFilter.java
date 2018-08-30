package cn.com.cdboost.collect.filter;

import cn.com.cdboost.collect.dto.LoginUser;
import cn.com.cdboost.collect.enums.GlobalConstant;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * session过滤
 */
public class SessionFilter implements Filter{

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        LoginUser loginUser = (LoginUser)request.getSession().getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
        if (loginUser != null) {
            //重新设值session
            request.getSession().setAttribute(GlobalConstant.CURRENT_LOGIN_USER, loginUser);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
