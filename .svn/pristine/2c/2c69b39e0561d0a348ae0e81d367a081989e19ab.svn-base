package cn.com.cdboost.collect.listener;


import cn.com.cdboost.collect.dto.ServiceHostPara;
import cn.com.cdboost.collect.enums.GlobalConstant;
import cn.com.cdboost.collect.service.SysConfigService;
import com.example.clienttest.client.SocketConnectionStatusListener;
import com.example.clienttest.clientfuture.ClientManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

/**
 * 中间件初始化监听
 */
public class InitSocketMiddlewareListener implements InitializingBean, ServletContextAware {
	private static final Logger logger = LoggerFactory.getLogger(InitSocketMiddlewareListener.class);

	private SysConfigService sysConfigService;
	private AFN04ResultDataBackLisener aFN04ResultDataBackLisener;
	private AFN08ResultDataBackLisener aFN08ResultDataBackLisener;
	private AFN14ResultDataBackLisener aFN14ResultDataBackLisener;
	private AFN07ResultDataBackLisener aFN07ResultDataBackLisener;
	private AFN15ResultDataBackLisener aFN15ResultDataBackLisener;
	private AFN09ResultDataBackLisener aFN09ResultDataBackLisener;
	private AFN20ResultDataBackLisener aFN20ResultDataBackLisener;
	private AFN17ResultDataBackLisener aFN17ResultDataBackLisener;
	private AFN19ResultDataBackLisener aFN19ResultDataBackLisener;
	private AFN21ResultDataBackLisener aFN21ResultDataBackLisener;
	private AFN22ResultDataBackLisener aFN22ResultDataBackLisener;
	private AFN23ResultDataBackLisener aFN23ResultDataBackLisener;

	@Override
	public void setServletContext(ServletContext servletContext) {
		logger.info("---------------初始化中间件开始----------------");
		try {
			ServiceHostPara hostPara = sysConfigService.getServiceHostPara();
			if (hostPara != null) {
				// 中间件状态连接，断开监听
				ClientManager.setSocketConnectStatusListener(new SocketConnectionStatusListener() {
					@Override
					public void SocketConnectionStatusCallBack(int status) {
						logger.info("设置前置机状态status=" + status);
						GlobalConstant.FRONT_PROCESSOR_STATUS = status;
					}
				});
				ClientManager.initClientFuture(hostPara.getIp(), Integer.valueOf(hostPara.getPort()));
//				ClientManager.initClientFuture("10.10.1.129", 9000);
				ClientManager.setAFN04DataBackListener(aFN04ResultDataBackLisener);
				ClientManager.setAFN08DataBackListener(aFN08ResultDataBackLisener);
				ClientManager.setAFN14DataBackListener(aFN14ResultDataBackLisener);
				ClientManager.setAFN07DataBackListener(aFN07ResultDataBackLisener);
				ClientManager.setAFN15DataBackListener(aFN15ResultDataBackLisener);
				ClientManager.setAFN09DataBackListener(aFN09ResultDataBackLisener);
				ClientManager.setAFN17DataBackListener(aFN17ResultDataBackLisener);
				ClientManager.setAFN19DataBackListener(aFN19ResultDataBackLisener);
				ClientManager.setAFN20DataBackListener(aFN20ResultDataBackLisener);
				ClientManager.setAFN21DataBackListener(aFN21ResultDataBackLisener);
				ClientManager.setAFN22DataBackListener(aFN22ResultDataBackLisener);
				ClientManager.setAFN23DataBackListener(aFN23ResultDataBackLisener);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {

	}

	public AFN17ResultDataBackLisener getaFN17ResultDataBackLisener() {
		return aFN17ResultDataBackLisener;
	}

	public void setaFN17ResultDataBackLisener(AFN17ResultDataBackLisener aFN17ResultDataBackLisener) {
		this.aFN17ResultDataBackLisener = aFN17ResultDataBackLisener;
	}

	public SysConfigService getSysConfigService() {
		return sysConfigService;
	}

	public void setSysConfigService(SysConfigService sysConfigService) {
		this.sysConfigService = sysConfigService;
	}

	public AFN04ResultDataBackLisener getaFN04ResultDataBackLisener() {
		return aFN04ResultDataBackLisener;
	}

	public void setaFN04ResultDataBackLisener(AFN04ResultDataBackLisener aFN04ResultDataBackLisener) {
		this.aFN04ResultDataBackLisener = aFN04ResultDataBackLisener;
	}

	public AFN08ResultDataBackLisener getaFN08ResultDataBackLisener() {
		return aFN08ResultDataBackLisener;
	}

	public void setaFN08ResultDataBackLisener(AFN08ResultDataBackLisener aFN08ResultDataBackLisener) {
		this.aFN08ResultDataBackLisener = aFN08ResultDataBackLisener;
	}

	public AFN14ResultDataBackLisener getaFN14ResultDataBackLisener() {
		return aFN14ResultDataBackLisener;
	}

	public void setaFN14ResultDataBackLisener(AFN14ResultDataBackLisener aFN14ResultDataBackLisener) {
		this.aFN14ResultDataBackLisener = aFN14ResultDataBackLisener;
	}

	public AFN07ResultDataBackLisener getaFN07ResultDataBackLisener() {
		return aFN07ResultDataBackLisener;
	}

	public void setaFN07ResultDataBackLisener(AFN07ResultDataBackLisener aFN07ResultDataBackLisener) {
		this.aFN07ResultDataBackLisener = aFN07ResultDataBackLisener;
	}

	public AFN15ResultDataBackLisener getaFN15ResultDataBackLisener() {
		return aFN15ResultDataBackLisener;
	}

	public void setaFN15ResultDataBackLisener(AFN15ResultDataBackLisener aFN15ResultDataBackLisener) {
		this.aFN15ResultDataBackLisener = aFN15ResultDataBackLisener;
	}

	public AFN09ResultDataBackLisener getaFN09ResultDataBackLisener() {
		return aFN09ResultDataBackLisener;
	}

	public void setaFN09ResultDataBackLisener(AFN09ResultDataBackLisener aFN09ResultDataBackLisener) {
		this.aFN09ResultDataBackLisener = aFN09ResultDataBackLisener;
	}

	public AFN20ResultDataBackLisener getaFN20ResultDataBackLisener() {
		return aFN20ResultDataBackLisener;
	}

	public void setaFN20ResultDataBackLisener(AFN20ResultDataBackLisener aFN20ResultDataBackLisener) {
		this.aFN20ResultDataBackLisener = aFN20ResultDataBackLisener;
	}

	public AFN19ResultDataBackLisener getaFN19ResultDataBackLisener() {
		return aFN19ResultDataBackLisener;
	}

	public void setaFN19ResultDataBackLisener(AFN19ResultDataBackLisener aFN19ResultDataBackLisener) {
		this.aFN19ResultDataBackLisener = aFN19ResultDataBackLisener;
	}

	public AFN21ResultDataBackLisener getaFN21ResultDataBackLisener() {
		return aFN21ResultDataBackLisener;
	}

	public void setaFN21ResultDataBackLisener(AFN21ResultDataBackLisener aFN21ResultDataBackLisener) {
		this.aFN21ResultDataBackLisener = aFN21ResultDataBackLisener;
	}

	public AFN22ResultDataBackLisener getaFN22ResultDataBackLisener() {
		return aFN22ResultDataBackLisener;
	}

	public void setaFN22ResultDataBackLisener(AFN22ResultDataBackLisener aFN22ResultDataBackLisener) {
		this.aFN22ResultDataBackLisener = aFN22ResultDataBackLisener;
	}

	public AFN23ResultDataBackLisener getaFN23ResultDataBackLisener() {
		return aFN23ResultDataBackLisener;
	}

	public void setaFN23ResultDataBackLisener(AFN23ResultDataBackLisener aFN23ResultDataBackLisener) {
		this.aFN23ResultDataBackLisener = aFN23ResultDataBackLisener;
	}
}
