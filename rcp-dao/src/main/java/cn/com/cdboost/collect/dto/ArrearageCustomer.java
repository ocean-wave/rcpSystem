package cn.com.cdboost.collect.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

public class ArrearageCustomer {
	/**
	 * 客户姓名
	 */
	private String customerName;

	/**
	 * 联系电话
	 */
	private String customerContact;

	/**
	 * 客户唯一标识
	 */
	private String customerNo;

	/**
	 * 表号
	 */
	private String deviceNo;
    private String orgName;
	/**
	 * 抄表日期
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date collectDate;

	/**
	 * 示数(电表最新欠费额度)
	 */
	private BigDecimal readValue;

	/**
	 * 告警阈值
	 */
	private BigDecimal alarmThreshold;

	/**
	 * 设备类型
	 */
	private String deviceType;

	/**
	 * 用户地址
	 */
	private String customerAddr;

	/**
	 * 安装地址
	 */
	private String installAddr;

	/**
	 * 短信最后发送时间
	 */
	private String lastSendSMSTime;

	/**
	 * 是否可发送短信
	 */
	private int sendMessage;

	/**
	 * 门牌编号
	 */
	private String propertyName;
	private BigDecimal remainPower;
	private String customerBrand;

	public BigDecimal getRemainPower() {
		if(remainPower!=null){
			return remainPower.setScale(2,BigDecimal.ROUND_HALF_UP);
		}else {
			return remainPower;
		}

	}

	public void setRemainPower(BigDecimal remainPower) {
		this.remainPower = remainPower;
	}

	public String getCustomerBrand() {
		return customerBrand;
	}

	public void setCustomerBrand(String customerBrand) {
		this.customerBrand = customerBrand;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getLastSendSMSTime() {
		return lastSendSMSTime;
	}
	public void setLastSendSMSTime(String lastSendSMSTime) {
		this.lastSendSMSTime = lastSendSMSTime;
	}
	public int getSendMessage() {
		return sendMessage;
	}
	public void setSendMessage(int sendMessage) {
		this.sendMessage = sendMessage;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getCustomerAddr() {
		return customerAddr;
	}
	public void setCustomerAddr(String customerAddr) {
		this.customerAddr = customerAddr;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerContact() {
		return customerContact;
	}
	public void setCustomerContact(String customerContact) {
		this.customerContact = customerContact;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public Date getCollectDate() {
		return collectDate;
	}

	public void setCollectDate(Date collectDate) {
		this.collectDate = collectDate;
	}

	public BigDecimal getReadValue() {
		return readValue;
	}

	public void setReadValue(BigDecimal readValue) {
		this.readValue = readValue;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public BigDecimal getAlarmThreshold() {
		return alarmThreshold;
	}

	public void setAlarmThreshold(BigDecimal alarmThreshold) {
		this.alarmThreshold = alarmThreshold;
	}

	public String getInstallAddr() {
		return installAddr;
	}

	public void setInstallAddr(String installAddr) {
		this.installAddr = installAddr;
	}
}
