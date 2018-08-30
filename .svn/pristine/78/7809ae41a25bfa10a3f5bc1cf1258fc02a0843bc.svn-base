package cn.com.cdboost.collect.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	public static final char UNDERLINE='_';
	private static Pattern NUMBER_PATTERN=Pattern.compile("[0-9]*");
	private static Pattern INTEGER_PATTERN=Pattern.compile("[0-9]*(\\.[0]*)?");
	private static Pattern DECIMAL_PATTERN=Pattern.compile("([1-9]+[0-9]*|0)(\\.[\\d]+)?");
	private static Pattern PHONE_PATTERN=Pattern.compile("1\\d{10}");
	public static void main(String[] args)
	{

		System.out.println(IsMatch("","[1-9][0-9]?"));
	}
	//判断字符串是否为电话
	public static boolean isPhone(String str) {
		Matcher isNum = PHONE_PATTERN.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	//判断字符串是否为整数
	public static boolean isInteger(String str) {
		Matcher isNum = INTEGER_PATTERN.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	//判断字符串是否为数字包括小数
	public static boolean isDecimal(String str) {
		Matcher isNum = DECIMAL_PATTERN.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	// 正则判断
	public static boolean IsMatch(String str, String patternStr) {
		Pattern pattern = Pattern.compile(patternStr);
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	// 判断是否为空
	public static boolean isEmpty(String string) {
		return (string == null) || (string.length() == 0);
	}

	// 判断字符串Object是否为空
	public static boolean isEmpty(Object obj) {
		if (obj instanceof String) {
			return (obj == null) || (obj.toString().length() == 0);
		} else {
			return false;
		}
	}

	public static String fillString(String str, int n) {
		StringBuffer sb = new StringBuffer();
		if (str.length() > n) {
			sb.append(str);
		} else {
			for (int i = 0; i < n - str.length(); i++) {
				sb.append("0");
			}
			sb.append(str);
		}
		return sb.toString();
	}
	//判断字符串是否为全数字
	public static boolean isNumeric(String str) {
		Matcher isNum = NUMBER_PATTERN.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	//3-电表节点 4-水表节点 5-气表节点
	public static String getMoteType(String deviceType) {
		String result;
		switch (deviceType){
			case "07" :
				result = "7";
				break;
			case "08" :
				result = "8";
				break;
			case "09" :
				result = "9";
				break;
			default:
				result = "7";
		}
		return result;
	}

	//3-电表节点 4-水表节点 5-气表节点
	public static String getDeviceType(String moteType) {
		String result;
		switch (moteType){
			case "7" :
				result = "07";
				break;
			case "8" :
				result = "08";
				break;
			case "9" :
				result = "09";
				break;
			default:
				result = "07";
		}
		return result;
	}

	//节点类型:A\B\C,电表是C类型，其它为A
	public static String getNodeType(String deviceType) {
		String result;
		switch (deviceType){
			case "07" :
				result = "C";
				break;
			default:
				result = "A";
		}
		return result;
	}

	/**
	 * 获取随机字符串
	 * @return
	 */
	public static String getNonceStr() {
		// 随机数
		String currTime = TenpayUtil.getCurrTime();
		// 8位日期
		String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		// 10位序列号,可以自行调整。
		return strTime + strRandom;
	}

	/**
	 * 驼峰字符串转成下划线字符串
	 * @param param
	 * @return
	 */
	public static String camelToUnderline(String param){
		if (param==null||"".equals(param.trim())){
			return "";
		}
		int len=param.length();
		StringBuilder sb=new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c=param.charAt(i);
			if (Character.isUpperCase(c)){
				sb.append(UNDERLINE);
				sb.append(Character.toLowerCase(c));
			}else{
				sb.append(c);
			}
		}
		return sb.toString();
	}

}
