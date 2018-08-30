package cn.com.cdboost.collect.filter;

import cn.com.cdboost.collect.util.CryptographyUtil;
import com.example.clienttest.clientfuture.ClientManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.io.FileOutputStream;
import java.util.Properties;

public class DecryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	private static final Logger logger = LoggerFactory.getLogger(DecryptPropertyPlaceholderConfigurer.class);

	protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)
			throws BeansException {
		//super.processProperties(beanFactory, props);
		try {
			String flag = props.getProperty("flag");
			logger.info("----------------------------------------------------flag:" + flag);
			if("0".equals(flag)){
				try {
					ClientManager.closeSocket();
				} catch (Exception e) {
					e.printStackTrace();
				}
				encryptProperties(props);
				String path = DecryptPropertyPlaceholderConfigurer.class.getResource("/db.properties").getPath();
				path = path.replace("%20", " "); // 处理空格
				logger.info("----------------------------------------------------path:" + path);
				FileOutputStream outputFile = new FileOutputStream(path);  
		        props.store(outputFile, "modify");  
		        outputFile.close();  
		        outputFile.flush();  
		    
		        getProperties(props);
		        
			}else{
				getProperties(props);
			}
			super.processProperties(beanFactory, props);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BeanInitializationException(e.getMessage());
		}
	}

	// 加密数据
	private void encryptProperties(Properties props) throws Exception {
		
		props.setProperty("flag", "1");
		
		String username = props.getProperty("mysql.username");
		if (username != null) {
			props.setProperty("mysql.username", CryptographyUtil.encrypt(username));
		}

		String password = props.getProperty("mysql.password");
		if (password != null) {
			props.setProperty("mysql.password", CryptographyUtil.encrypt(password));
		}

		String url = props.getProperty("mysql.url");
		if (url != null) {
			props.setProperty("mysql.url", CryptographyUtil.encrypt(url));
		}

		String driverClassName = props.getProperty("mysql.driverClass");
		if (driverClassName != null) {
			props.setProperty("mysql.driverClass", CryptographyUtil.encrypt(driverClassName));
		}
	}

	// 解密数据
	private void getProperties(Properties props) throws Exception {
		
		String username = props.getProperty("mysql.username");
		if (username != null) {
			props.setProperty("mysql.username", CryptographyUtil.decrypt(username));
			System.out.println("mysql.username" + props.getProperty("mysql.username"));
		}

		String password = props.getProperty("mysql.password");
		if (password != null) {
			props.setProperty("mysql.password", CryptographyUtil.decrypt(password));
		}

		String url = props.getProperty("mysql.url");
		if (url != null) {
			props.setProperty("mysql.url", CryptographyUtil.decrypt(url));
		}

		String driverClassName = props.getProperty("mysql.driverClass");
		if (driverClassName != null) {
			props.setProperty("mysql.driverClass", CryptographyUtil.decrypt(driverClassName));
		}
	}
}