package cn.com.cdboost.collect.dto.param;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 读卡信息保存参数
 */
public class FeeReadCardParam {
    /**
     * 用户编号
     */
    @NotBlank(message = "customerNo不能为空")
    private String customerNo;

    /**
     * 购电卡号
     */
    private String cardId;

    /**
     * 设备唯一标识(生成规则:设备类型+补0(不足30位)+设备编号）
     */
    @NotBlank(message = "cno不能为空")
    private String cno;

    /**
     * 电流互感器变比
     */
    @NotNull(message = "curTranfRto不能为null")
    private Integer curTranfRto;

    /**
     * 非法插卡次数
     */
    @NotNull(message = "errorCnt不能为null")
    private Integer errorCnt;

    /**
     * 电压互感器变比
     */
    @NotNull(message = "volTranfRto不能为null")
    private Integer volTranfRto;

    /**
     * 返写时间
     */
    @NotBlank(message = "reWrtTime不能为空")
    private String reWrtTime;

    /**
     * 剩余金额
     */
    @DecimalMin(value = "0",message = "remainAmount不能小于0")
    private BigDecimal remainAmount;

    /**
     * 密钥条数
     */
    @NotNull(message = "keyCount不能为null")
    private Integer keyCount;

    /**
     * 购电次数
     */
    @NotNull(message = "payCount不能为null")
    private Integer payCount;

    /**
     * 密钥状态
     */
    @NotNull(message = "keyState不能为null")
    private Integer keyState;

    /**
     * 倍率
     */
    private Integer ratio;

    /**
     * 透支金额
     */
    @DecimalMin(value = "0",message = "overdraftFee不能小于0")
    private BigDecimal overdraftFee;

    /**
     * 密钥版本
     */
    @NotNull(message = "keyVer不能为null")
    private Integer keyVer;

    /**
     * 表号
     */
    @NotBlank(message = "meterNo不能为空")
    private String meterNo;

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public Integer getCurTranfRto() {
        return curTranfRto;
    }

    public void setCurTranfRto(Integer curTranfRto) {
        this.curTranfRto = curTranfRto;
    }

    public Integer getErrorCnt() {
        return errorCnt;
    }

    public void setErrorCnt(Integer errorCnt) {
        this.errorCnt = errorCnt;
    }

    public Integer getVolTranfRto() {
        return volTranfRto;
    }

    public void setVolTranfRto(Integer volTranfRto) {
        this.volTranfRto = volTranfRto;
    }

    public String getReWrtTime() {
        return reWrtTime;
    }

    public void setReWrtTime(String reWrtTime) {
        this.reWrtTime = reWrtTime;
    }

    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }

    public Integer getKeyCount() {
        return keyCount;
    }

    public void setKeyCount(Integer keyCount) {
        this.keyCount = keyCount;
    }

    public Integer getPayCount() {
        return payCount;
    }

    public void setPayCount(Integer payCount) {
        this.payCount = payCount;
    }

    public Integer getKeyState() {
        return keyState;
    }

    public void setKeyState(Integer keyState) {
        this.keyState = keyState;
    }

    public Integer getRatio() {
        return ratio;
    }

    public void setRatio(Integer ratio) {
        this.ratio = ratio;
    }

    public BigDecimal getOverdraftFee() {
        return overdraftFee;
    }

    public void setOverdraftFee(BigDecimal overdraftFee) {
        this.overdraftFee = overdraftFee;
    }

    public Integer getKeyVer() {
        return keyVer;
    }

    public void setKeyVer(Integer keyVer) {
        this.keyVer = keyVer;
    }

    public String getMeterNo() {
        return meterNo;
    }

    public void setMeterNo(String meterNo) {
        this.meterNo = meterNo;
    }
}
