package cn.com.cdboost.collect.dto;

import java.math.BigDecimal;

/**
*@author linyang
*@date 2017年6月7日
*/
public class CustomerData4Month {
	/**
	 * 采集日期
	 */
	private String collectDate;
    private Integer dataType;
	/**
	 * 总
	 */
	private BigDecimal pr0;

	/**
	 * 剩余金额
	 */
	private BigDecimal balance;

	/**
	 * 购电总金额
	 */
	private BigDecimal payMoney;
	/**
	 * 购电次数
	 */
	private int payCount;

	/**
	 * 设备编号
	 */
	private String deviceNo;

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public String getCollectDate() {
		return collectDate;
	}

	public void setCollectDate(String collectDate) {
		this.collectDate = collectDate;
	}

	public BigDecimal getPr0() {
		return pr0;
	}

	public void setPr0(BigDecimal pr0) {
		this.pr0 = pr0;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}

	public int getPayCount() {
		return payCount;
	}

	public void setPayCount(int payCount) {
		this.payCount = payCount;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
}
