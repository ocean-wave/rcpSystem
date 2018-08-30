package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "em_d_device_meter_config")
public class DeviceMeterConfig implements Serializable{
    /**
     * 标识ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 表计厂家,字典表code=14
     */
    @Column(name = "meter_factory")
    private String meterFactory;

    /**
     * 表计型号
     */
    @Column(name = "meter_mode")
    private String meterMode;

    /**
     * 表计类别
     */
    @Column(name = "meter_category")
    private String meterCategory;

    /**
     * 规约（0 默认，1 DL/T645-1997规约，30 DL/T645-2007）
     */
    @Column(name = "comm_rule")
    private Integer commRule;

    /**
     * 电能费率个数
     */
    @Column(name = "comm_factor_cnt")
    private Integer commFactorCnt;

    /**
     * 设备类型 0其他 1 -09电表 2-13表
     */
    @Column(name = "meter_type")
    private Integer meterType;

    /**
     * 设备类型 7 表示电表 8 表示水表 9气表
     */
    @Column(name = "device_type")
    private Integer deviceType;

    /**
     * 密钥级别
     */
    @Column(name = "key_grade")
    private String keyGrade;

    /**
     * 02 级别的用户名
     */
    @Column(name = "meter_user_name")
    private String meterUserName;

    /**
     * 02 级别的密码
     */
    @Column(name = "meter_user_pwd")
    private String meterUserPwd;

    /**
     * 是否具备远程阀门控制 1-具备 0-不具备
     */
    @Column(name = "is_valve_control")
    private Integer isValveControl;

    /**
     * 通信速率Baud（bps1~7依次表示600，1200，2400，4800,7200，9600，19200 设0：表示规约默认的通信速率)
     */
    @Column(name = "comm_baudrate")
    private Integer commBaudrate;

    /**
     * 参数标识
     */
    @Column(name = "param_flag")
    private String paramFlag;
    /**
     *'02级合闸控制命令字节'
     */
    @Column(name = "switch_on_cmd")
    private String switchOnCmd;
    /**
     *'02级拉闸控制命令字节',
     */
    @Column(name = "switch_off_cmd")
    private String switchOffCmd;

    public String getSwitchOnCmd() {
        return switchOnCmd;
    }

    public void setSwitchOnCmd(String switchOnCmd) {
        this.switchOnCmd = switchOnCmd;
    }

    public String getSwitchOffCmd() {
        return switchOffCmd;
    }

    public void setSwitchOffCmd(String switchOffCmd) {
        this.switchOffCmd = switchOffCmd;
    }

    /**
     * 获取标识ID
     *
     * @return id - 标识ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置标识ID
     *
     * @param id 标识ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取表计厂家,字典表code=14
     *
     * @return meter_factory - 表计厂家,字典表code=14
     */
    public String getMeterFactory() {
        return meterFactory;
    }

    /**
     * 设置表计厂家,字典表code=14
     *
     * @param meterFactory 表计厂家,字典表code=14
     */
    public void setMeterFactory(String meterFactory) {
        this.meterFactory = meterFactory;
    }

    /**
     * 获取表计型号
     *
     * @return meter_mode - 表计型号
     */
    public String getMeterMode() {
        return meterMode;
    }

    /**
     * 设置表计型号
     *
     * @param meterMode 表计型号
     */
    public void setMeterMode(String meterMode) {
        this.meterMode = meterMode;
    }

    /**
     * 获取表计类别
     *
     * @return meter_category - 表计类别
     */
    public String getMeterCategory() {
        return meterCategory;
    }

    /**
     * 设置表计类别
     *
     * @param meterCategory 表计类别
     */
    public void setMeterCategory(String meterCategory) {
        this.meterCategory = meterCategory;
    }

    /**
     * 获取规约（0 默认，1 DL/T645-1997规约，30 DL/T645-2007）
     *
     * @return comm_rule - 规约（0 默认，1 DL/T645-1997规约，30 DL/T645-2007）
     */
    public Integer getCommRule() {
        return commRule;
    }

    /**
     * 设置规约（0 默认，1 DL/T645-1997规约，30 DL/T645-2007）
     *
     * @param commRule 规约（0 默认，1 DL/T645-1997规约，30 DL/T645-2007）
     */
    public void setCommRule(Integer commRule) {
        this.commRule = commRule;
    }

    /**
     * 获取电能费率个数
     *
     * @return comm_factor_cnt - 电能费率个数
     */
    public Integer getCommFactorCnt() {
        return commFactorCnt;
    }

    /**
     * 设置电能费率个数
     *
     * @param commFactorCnt 电能费率个数
     */
    public void setCommFactorCnt(Integer commFactorCnt) {
        this.commFactorCnt = commFactorCnt;
    }

    /**
     * 获取设备类型 0其他 1 -09电表 2-13表
     *
     * @return meter_type - 设备类型 0其他 1 -09电表 2-13表
     */
    public Integer getMeterType() {
        return meterType;
    }

    /**
     * 设置设备类型 0其他 1 -09电表 2-13表
     *
     * @param meterType 设备类型 0其他 1 -09电表 2-13表
     */
    public void setMeterType(Integer meterType) {
        this.meterType = meterType;
    }

    /**
     * 获取设备类型 7 表示电表 8 表示水表 9气表
     *
     * @return device_type - 设备类型 7 表示电表 8 表示水表 9气表
     */
    public Integer getDeviceType() {
        return deviceType;
    }

    /**
     * 设置设备类型 7 表示电表 8 表示水表 9气表
     *
     * @param deviceType 设备类型 7 表示电表 8 表示水表 9气表
     */
    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * 获取密钥级别
     *
     * @return key_grade - 密钥级别
     */
    public String getKeyGrade() {
        return keyGrade;
    }

    /**
     * 设置密钥级别
     *
     * @param keyGrade 密钥级别
     */
    public void setKeyGrade(String keyGrade) {
        this.keyGrade = keyGrade;
    }

    /**
     * 获取02 级别的用户名
     *
     * @return meter_user_name - 02 级别的用户名
     */
    public String getMeterUserName() {
        return meterUserName;
    }

    /**
     * 设置02 级别的用户名
     *
     * @param meterUserName 02 级别的用户名
     */
    public void setMeterUserName(String meterUserName) {
        this.meterUserName = meterUserName;
    }

    /**
     * 获取02 级别的密码
     *
     * @return meter_user_pwd - 02 级别的密码
     */
    public String getMeterUserPwd() {
        return meterUserPwd;
    }

    /**
     * 设置02 级别的密码
     *
     * @param meterUserPwd 02 级别的密码
     */
    public void setMeterUserPwd(String meterUserPwd) {
        this.meterUserPwd = meterUserPwd;
    }

    /**
     * 获取是否具备远程阀门控制 1-具备 0-不具备
     *
     * @return is_valve_control - 是否具备远程阀门控制 1-具备 0-不具备
     */
    public Integer getIsValveControl() {
        return isValveControl;
    }

    /**
     * 设置是否具备远程阀门控制 1-具备 0-不具备
     *
     * @param isValveControl 是否具备远程阀门控制 1-具备 0-不具备
     */
    public void setIsValveControl(Integer isValveControl) {
        this.isValveControl = isValveControl;
    }

    /**
     * 获取通信速率Baud（bps1~7依次表示600，1200，2400，4800,7200，9600，19200 设0：表示规约默认的通信速率)
     *
     * @return comm_baudrate - 通信速率Baud（bps1~7依次表示600，1200，2400，4800,7200，9600，19200 设0：表示规约默认的通信速率)
     */
    public Integer getCommBaudrate() {
        return commBaudrate;
    }

    /**
     * 设置通信速率Baud（bps1~7依次表示600，1200，2400，4800,7200，9600，19200 设0：表示规约默认的通信速率)
     *
     * @param commBaudrate 通信速率Baud（bps1~7依次表示600，1200，2400，4800,7200，9600，19200 设0：表示规约默认的通信速率)
     */
    public void setCommBaudrate(Integer commBaudrate) {
        this.commBaudrate = commBaudrate;
    }

    /**
     * 获取参数标识
     *
     * @return param_flag - 参数标识
     */
    public String getParamFlag() {
        return paramFlag;
    }

    /**
     * 设置参数标识
     *
     * @param paramFlag 参数标识
     */
    public void setParamFlag(String paramFlag) {
        this.paramFlag = paramFlag;
    }
}