package cn.com.cdboost.collect.enums;

public enum SysConfigTypeEnum {
	LINK_SERVIER("1"), SYS_PARA("2"), SMS_PARA("3"), LORA_WAN("4"); // 服务器连接参数

	public final String value;

	SysConfigTypeEnum(String value) {
		this.value = value;
	}
}
