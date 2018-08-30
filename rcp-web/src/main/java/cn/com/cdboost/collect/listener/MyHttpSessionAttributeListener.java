package cn.com.cdboost.collect.listener;

import cn.com.cdboost.collect.dto.LoginUser;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.util.SessionUtil;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * session属性变化事件监听
 */
public class MyHttpSessionAttributeListener implements HttpSessionAttributeListener {
    /**
     * 添加Session
     */
    public void attributeAdded(HttpSessionBindingEvent event) {
        if (GlobalConstant.CURRENT_LOGIN_USER.equals(event.getName())) {
            SessionUtil.set((LoginUser) event.getValue());
        }
    }

    /**
     * 替代Session
     */
    public void attributeReplaced(HttpSessionBindingEvent event) {
        if (GlobalConstant.CURRENT_LOGIN_USER.equals(event.getName())) {
            SessionUtil.set((LoginUser)event.getValue());
        }
    }

    /**
     * 销毁Session
     */
    public void attributeRemoved(HttpSessionBindingEvent event) {
        if(GlobalConstant.CURRENT_LOGIN_USER.equals(event.getName())){
            SessionUtil.remove();
        }
    }
}
