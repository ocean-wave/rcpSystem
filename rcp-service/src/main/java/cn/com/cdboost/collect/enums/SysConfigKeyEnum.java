package cn.com.cdboost.collect.enums;

public enum SysConfigKeyEnum {
	SERVICE_IP("serviceIp", "10.10.1.250"), SERVICE_PORT("servicePort", "7000"),
	NET_APN("apn", "cmnet"), SYS_NAME("sysName", "三表合一采集平台"),
	BALANCE_DATE("balanceTime", "1"), SMS_PAY_ADDR("payAddr", ""),
	SMS_COMPANY_NAME("companyName", "博高科技");
	public final String value;
	public final String defValue;

	SysConfigKeyEnum(String name, String value) {
		this.value = name;
		this.defValue = value;
	}
}
