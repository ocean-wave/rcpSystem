package cn.com.cdboost.collect.dto;

/**
 * 设备档案，电表明细页面显示信息
 */
public class ElectricDetailInfo extends DeviceDetailBaseInfo{

    /**
     * 电流互感变比
     */
    private Integer ratio;

    /**
     * 费控方式
     */
    private Integer localControl;

    /**
     * 设备类型 0其他 1表示09电表 2表示13表
     */
    private Integer meterType;

    /**
     * 计量点
     */
    private Integer commSetupSn;

    /**
     * 测量点
     */
    private Integer commPointCode;

    /**
     * 波特率
     */
    private Integer commBaudrate;
    /**
     * 设备型号
     */
    private String meterMode;

    /**
     * 设备类别
     */
    private String meterCategory;

    /**
     * 费率数
     */
    private Integer commFactorCnt;

    /**
     * 密钥等级
     */
    private String keyGrade;
    /**
     * 操作员代码
     */
    private String meterUserName;
    /**
     * 密码
     */
    private String meterUserPwd;
    /**
     * 支持阀控
     */
    private Integer isValveControl;

    public void setCommFactorCnt(Integer commFactorCnt) {
        this.commFactorCnt = commFactorCnt;
    }

    public Integer getCommFactorCnt() {
        return commFactorCnt;
    }

    public String getMeterMode() {
        return meterMode;
    }

    public void setMeterMode(String meterMode) {
        this.meterMode = meterMode;
    }

    public String getMeterCategory() {
        return meterCategory;
    }

    public void setMeterCategory(String meterCategory) {
        this.meterCategory = meterCategory;
    }


    public String getKeyGrade() {
        return keyGrade;
    }

    public void setKeyGrade(String keyGrade) {
        this.keyGrade = keyGrade;
    }

    public String getMeterUserName() {
        return meterUserName;
    }

    public void setMeterUserName(String meterUserName) {
        this.meterUserName = meterUserName;
    }

    public String getMeterUserPwd() {
        return meterUserPwd;
    }

    public void setMeterUserPwd(String meterUserPwd) {
        this.meterUserPwd = meterUserPwd;
    }

    public Integer getIsValveControl() {
        return isValveControl;
    }

    public void setIsValveControl(Integer isValveControl) {
        this.isValveControl = isValveControl;
    }

    public Integer getRatio() {
        return ratio;
    }

    public void setRatio(Integer ratio) {
        this.ratio = ratio;
    }

    public Integer getLocalControl() {
        return localControl;
    }

    public void setLocalControl(Integer localControl) {
        this.localControl = localControl;
    }

    public Integer getMeterType() {
        return meterType;
    }

    public void setMeterType(Integer meterType) {
        this.meterType = meterType;
    }

    public Integer getCommSetupSn() {
        return commSetupSn;
    }

    public void setCommSetupSn(Integer commSetupSn) {
        this.commSetupSn = commSetupSn;
    }

    @Override
    public Integer getCommPointCode() {
        return commPointCode;
    }

    @Override
    public void setCommPointCode(Integer commPointCode) {
        this.commPointCode = commPointCode;
    }

    public Integer getCommBaudrate() {
        return commBaudrate;
    }

    public void setCommBaudrate(Integer commBaudrate) {
        this.commBaudrate = commBaudrate;
    }
}
