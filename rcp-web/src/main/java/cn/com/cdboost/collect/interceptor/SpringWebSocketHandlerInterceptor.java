package cn.com.cdboost.collect.interceptor;

import cn.com.cdboost.collect.dto.LoginUser;
import cn.com.cdboost.collect.enums.GlobalConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Random;

/**
 * WebSocket拦截器
 * @author
 *
 */
public class SpringWebSocketHandlerInterceptor extends HttpSessionHandshakeInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(SpringWebSocketHandlerInterceptor.class);
    private static final String DEFAULT_USER_NAME = "default-system";

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            String source = servletRequest.getServletRequest().getParameter("source");
//            logger.info("websocke建立连接握手source=" + source);
            if ("1".equals(source)) {
                Random random = new Random();
                int i = random.nextInt(10);
                if (i % 2 == 0) {
                    attributes.put("businessType",0);
                } else {
                    attributes.put("businessType",1);
                }

                // source = 1 表示来源于app端websocket请求
                return super.beforeHandshake(request, response, wsHandler, attributes);
            }

            HttpSession session = servletRequest.getServletRequest().getSession(false);
            if (session != null) {
                LoginUser loginUser = (LoginUser) session.getAttribute(GlobalConstant.CURRENT_LOGIN_USER);
                if (loginUser == null) {
//                    logger.info("loginUser==null,说明登录用户Session已失效");
                    attributes.put(GlobalConstant.WEB_SOCKET_USERNAME, DEFAULT_USER_NAME);
                    // 跟前端联调时，需要将session超时注释掉，否则前端由于跨域第一次登陆，就会进来
                    attributes.put(GlobalConstant.SESSION_TIMEOUT,true);
                    attributes.put(GlobalConstant.HTTP_SESSION_OBJECT, session);
                    return true;
                }

                String clientIP = (String) session.getAttribute(GlobalConstant.CLIENT_REAL_IP);
                String userName = loginUser.getUserName();
                attributes.put(GlobalConstant.WEB_SOCKET_USERNAME, userName);
                attributes.put(GlobalConstant.CLIENT_REAL_IP,clientIP);
                attributes.put(GlobalConstant.HTTP_SESSION_OBJECT, session);
                attributes.put(GlobalConstant.HTTP_SESSION_ID, session.getId());
//                logger.info("用户userName=" + userName + ",ClientIP=" + clientIP + ",建立连接beforeHandshake");
            }
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception ex) {
    }

}
