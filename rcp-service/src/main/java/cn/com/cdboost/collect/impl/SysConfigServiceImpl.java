package cn.com.cdboost.collect.impl;


import cn.com.cdboost.collect.common.BaseServiceImpl;
import cn.com.cdboost.collect.constant.SysConfigConstant;
import cn.com.cdboost.collect.dao.SysConfigMapper;
import cn.com.cdboost.collect.dto.ServiceHostPara;
import cn.com.cdboost.collect.dto.SmsConfigPara;
import cn.com.cdboost.collect.dto.SumDataGetDTO;
import cn.com.cdboost.collect.dto.SysConfigPara;
import cn.com.cdboost.collect.dto.param.GetForMonthQueryVo;
import cn.com.cdboost.collect.dto.param.SystemConfigParamVo;
import cn.com.cdboost.collect.enums.SysConfigKeyEnum;
import cn.com.cdboost.collect.enums.SysConfigTypeEnum;
import cn.com.cdboost.collect.model.SysConfig;
import cn.com.cdboost.collect.service.SysConfigService;
import com.example.clienttest.clientfuture.ClientManager;
import com.example.clienttest.protocol3761.Tool;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统配置服务接口实现类
 */
@Service("sysConfigService")
public class SysConfigServiceImpl extends BaseServiceImpl<SysConfig> implements SysConfigService {
	private static final Logger logger = LoggerFactory.getLogger(SysConfigServiceImpl.class);

	@Autowired
	private SysConfigMapper sysConfigMapper;

	@Override
	public String getConfigValue(String configName) {
		SysConfig param = new SysConfig();
		param.setConfigName(configName);
		SysConfig config = sysConfigMapper.selectOne(param);
		return config.getConfigValue();
	}

	@Override
	public ServiceHostPara getServiceHostPara() {
		ServiceHostPara hostPara = new ServiceHostPara();
		SysConfig param = new SysConfig();
		param.setConfigType(Integer.valueOf(SysConfigTypeEnum.LINK_SERVIER.value));
		List<SysConfig> list = sysConfigMapper.select(param);
		if (list != null && list.size() > 0) {
			for (SysConfig cfg : list) {
				String value = cfg.getConfigValue();
				String configName = cfg.getConfigName();
				if (Tool.isEmpyt(value))
					continue;
				if (SysConfigKeyEnum.SERVICE_IP.value.equalsIgnoreCase(configName)) {
					hostPara.setIp(value);
				} else if (SysConfigKeyEnum.SERVICE_PORT.value.equalsIgnoreCase(configName)) {
					hostPara.setPort(value);
				} else if (SysConfigKeyEnum.NET_APN.value.equalsIgnoreCase(configName)) {
					hostPara.setApn(value);
				}
			}
		}
		return hostPara;
	}

	@Override
	public SysConfigPara getSysConfigPara() {
		SysConfigPara sysConfig = new SysConfigPara();

		SysConfig param = new SysConfig();
		param.setConfigType(Integer.valueOf(SysConfigTypeEnum.SYS_PARA.value));
		List<SysConfig> list = sysConfigMapper.select(param);
		if (list != null && list.size() > 0) {
			for (SysConfig cfg : list) {
				String value = cfg.getConfigValue();
				if (Tool.isEmpyt(value))
					continue;
				if (SysConfigKeyEnum.SYS_NAME.value.equalsIgnoreCase(cfg.getConfigName())) {
					sysConfig.setName(value);
				} else if (SysConfigKeyEnum.BALANCE_DATE.value.equalsIgnoreCase(cfg.getConfigName())) {
					sysConfig.setBalanceDate(value);
				}
			}
		}
		return sysConfig;
	}

	@Override
	public SmsConfigPara getSmsConfigPara() {
		SmsConfigPara smsConfig = new SmsConfigPara();
		SysConfig param = new SysConfig();
		param.setConfigType(Integer.valueOf(SysConfigTypeEnum.SMS_PARA.value));
		List<SysConfig> list = sysConfigMapper.select(param);
		if (list != null && list.size() > 0) {
			for (SysConfig cfg : list) {
				String value = cfg.getConfigValue();
				if (Tool.isEmpyt(value))
					continue;
				if (SysConfigKeyEnum.SMS_PAY_ADDR.value.equalsIgnoreCase(cfg.getConfigName())) {
					smsConfig.setPayAddr(value);
				} else if (SysConfigKeyEnum.SMS_COMPANY_NAME.value.equalsIgnoreCase(cfg.getConfigName())) {
					smsConfig.setCompanyName(value);
				}
			}
		}
		return smsConfig;
	}

	@Override
	public String getWebSocketUrl() {
		SysConfig param = new SysConfig();
		param.setConfigType(SysConfigConstant.WEB_SOCKET_URL.getConfigType());
		param.setConfigName(SysConfigConstant.WEB_SOCKET_URL.getConfigName());
		SysConfig sysConfig = sysConfigMapper.selectOne(param);
		return sysConfig.getConfigValue();
	}

	@Override
	public String getcopyRight() {
		SysConfig param = new SysConfig();
		param.setConfigType(SysConfigConstant.COPY_RIGHT.getConfigType());
		param.setConfigName(SysConfigConstant.COPY_RIGHT.getConfigName());
		SysConfig sysConfig = sysConfigMapper.selectOne(param);
		return sysConfig.getConfigValue();
	}

	@Override
	public SysConfig queryByConfigName(String configName) {
		SysConfig param = new SysConfig();
		param.setConfigName(configName);
		return sysConfigMapper.selectOne(param);
	}

	@Override
	public List<SumDataGetDTO> sumGetForMonth(GetForMonthQueryVo queryVo) {
		return sysConfigMapper.sumGetForMonth(queryVo);
	}


	@Override
	@Transactional
	public void batchUpdate(SystemConfigParamVo paramVo) {
		List<SysConfig> list = Lists.newArrayList();

		String frontProcessorIp = paramVo.getFrontProcessorIp();
		Integer frontProcessorPort = paramVo.getFrontProcessorPort();

		// 前置机重新连接
		ClientManager.closeSocket();
		logger.info("前置机重新连接IP=" + frontProcessorIp + ",Port=" + frontProcessorPort);
		ClientManager.initClientFuture(frontProcessorIp,frontProcessorPort);

		//通道参数
		SysConfig sysConfig1 = new SysConfig();
		sysConfig1.setConfigName(SysConfigConstant.FRONT_PROCESSOR_IP.getConfigName());
		sysConfig1.setConfigType(SysConfigConstant.FRONT_PROCESSOR_IP.getConfigType());
		sysConfig1.setConfigValue(frontProcessorIp);

		SysConfig sysConfig2 = new SysConfig();
		sysConfig2.setConfigName(SysConfigConstant.FRONT_PROCESSOR_PORT.getConfigName());
		sysConfig2.setConfigType(SysConfigConstant.FRONT_PROCESSOR_PORT.getConfigType());
		sysConfig2.setConfigValue(String.valueOf(frontProcessorPort));

		SysConfig sysConfig3 = new SysConfig();
		sysConfig3.setConfigName(SysConfigConstant.FRONT_PROCESSOR_APN.getConfigName());
		sysConfig3.setConfigType(SysConfigConstant.FRONT_PROCESSOR_APN.getConfigType());
		sysConfig3.setConfigValue(String.valueOf(paramVo.getApn()));

		list.add(sysConfig1);
		list.add(sysConfig2);
		list.add(sysConfig3);

		// 系统参数
		SysConfig sysConfig4 = new SysConfig();
		sysConfig4.setConfigName(SysConfigConstant.SYSTEM_NAME.getConfigName());
		sysConfig4.setConfigType(SysConfigConstant.SYSTEM_NAME.getConfigType());
		sysConfig4.setConfigValue(String.valueOf(paramVo.getSysName()));

		SysConfig sysConfig5 = new SysConfig();
		sysConfig5.setConfigName(SysConfigConstant.BALANCE_TIME.getConfigName());
		sysConfig5.setConfigType(SysConfigConstant.BALANCE_TIME.getConfigType());
		sysConfig5.setConfigValue(String.valueOf(paramVo.getBalanceDate()));

		list.add(sysConfig4);
		list.add(sysConfig5);

		// 短信参数
		SysConfig sysConfig6 = new SysConfig();
		sysConfig6.setConfigName(SysConfigConstant.PAY_ADDR.getConfigName());
		sysConfig6.setConfigType(SysConfigConstant.PAY_ADDR.getConfigType());
		sysConfig6.setConfigValue(String.valueOf(paramVo.getPayAddr()));

		SysConfig sysConfig7 = new SysConfig();
		sysConfig7.setConfigName(SysConfigConstant.COMPANY_NAME.getConfigName());
		sysConfig7.setConfigType(SysConfigConstant.COMPANY_NAME.getConfigType());
		sysConfig7.setConfigValue(String.valueOf(paramVo.getCompanyName()));

		list.add(sysConfig6);
		list.add(sysConfig7);

		// webSocket 地址
		SysConfig sysConfig8 = new SysConfig();
		sysConfig8.setConfigName(SysConfigConstant.WEB_SOCKET_URL.getConfigName());
		sysConfig8.setConfigType(SysConfigConstant.WEB_SOCKET_URL.getConfigType());
		sysConfig8.setConfigValue(String.valueOf(paramVo.getWebSocketUrl()));
		list.add(sysConfig8);
		//版权信息
		SysConfig sysConfig9 = new SysConfig();
        sysConfig9.setConfigName(SysConfigConstant.COPY_RIGHT.getConfigName());
        sysConfig9.setConfigType(SysConfigConstant.COPY_RIGHT.getConfigType());
        sysConfig9.setConfigValue(String.valueOf(paramVo.getCopyright()==null?"":paramVo.getCopyright()));
		list.add(sysConfig9);
		sysConfigMapper.batchUpdate(list);
	}
}
