package cn.com.cdboost.collect.interceptor;


import cn.com.cdboost.collect.enums.Auth;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.model.RoleRight;
import cn.com.cdboost.collect.vo.Result;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

public class AuthInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String requestURI = request.getRequestURI();
		logger.info("进入权限验证拦截器：requestURI=" + requestURI);

		boolean flag = true;
		boolean tag = true;
		if (handler instanceof HandlerMethod) {
			Auth auth = ((HandlerMethod) handler).getMethod().getAnnotation(Auth.class);
			if (auth != null) {
				// 有权限控制的就要检查
				List<Long> menuIDs = Lists.newArrayList();
				List<Long> actionIDs = Lists.newArrayList();
				for (int i = 0; i < auth.menuID().length; i++) {
					menuIDs.add(auth.menuID()[i]);
				}
				for (int i = 0; i < auth.actionID().length; i++) {
					actionIDs.add(auth.actionID()[i]);
				}
				List<RoleRight> auths = (List<RoleRight>) request.getSession().getAttribute(GlobalConstant.SESSION_AUTHS);
				for (RoleRight roleRight : auths) {
					if (menuIDs.contains(roleRight.getMenuId()) && actionIDs.contains(roleRight.getActionId())) {
						tag = false;
						break;
					}
				}

				if (tag) {
					// 提示用户没权限
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					Result result = new Result();
					result.error("您没有权限操作");
					out.write(JSON.toJSONString(result));
					out.flush();
					out.close();
					flag = false;
				}
			}
		}
		return flag;
	}

}
