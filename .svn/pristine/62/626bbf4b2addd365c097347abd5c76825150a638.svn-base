package cn.com.cdboost.collect.filter;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UserFormAuthenticationFilter extends FormAuthenticationFilter {
	/**
	 * 主要是处理登入失败的方法
	 */
	@Override
	protected boolean onLoginFailure(AuthenticationToken token,
			AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		 HttpServletResponse httpResponse = (HttpServletResponse) response;
		if (!"XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request)
				.getHeader("X-Requested-With"))) {// 不是ajax请求
			setFailureAttribute(request, e);
			return true;
		}else{
			 // 登陆url
	        String loginUrl = httpRequest.getContextPath() + "/index.html";
			httpResponse.addHeader("sessionstatus", "timeOut");
			httpResponse.addHeader("loginPath", loginUrl);
			try {
				httpResponse.sendRedirect(loginUrl);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		try {
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			String message = e.getClass().getSimpleName();
			if ("IncorrectCredentialsException".equals(message)) {
				out.println("{success:false,message:'密码错误'}");
			} else if ("UnknownAccountException".equals(message)) {
				out.println("{success:false,message:'账号不存在'}");
			} else if ("LockedAccountException".equals(message)) {
				out.println("{success:false,message:'账号被锁定'}");
			} else {
				out.println("{success:false,message:'未知错误'}");
			}
			out.flush();
			out.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return false;
	}
}
