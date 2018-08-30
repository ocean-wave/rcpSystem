package cn.com.cdboost.collect.dto;


/**
 * @author Administrator
 * @Descript 采集数据汇总采集类
 */
public class CollectAnalyzeData {
  
	public String deviceType; //设备类型
	public int cltMonth;  //采集月份
	public double  sumPR;   //汇总

	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public int getCltMonth() {
		return cltMonth;
	}
	public void setCltMonth(int cltMonth) {
		this.cltMonth = cltMonth;
	}
	public double getSumPR() {
		return sumPR;
	}
	public void setSumPR(double sumPR) {
		this.sumPR = sumPR;
	}
	@Override
	public String toString() {
		return "CollectAnalyzeData [deviceType=" + deviceType + ", cltMonth=" + cltMonth + ", sumPR=" + sumPR + "]";
	}
	
}
