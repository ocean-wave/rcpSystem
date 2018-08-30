package cn.com.cdboost.collect.listener;


import com.example.clienttest.clientfuture.ClientManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Component("tomcatListener")
public class TomcatListener implements ServletContextListener{
	private static final Logger logger = LoggerFactory.getLogger(TomcatListener.class);

    /**
     * 监听web容器关闭
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ClientManager.closeSocket();
    	logger.info("===========================web容器关闭");
    }

    /**
     * 监听web容器启动
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
    	logger.info("===========================web容器启动");
    }

}