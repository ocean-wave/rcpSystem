package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "em_d_device_comparam")
public class DeviceComParam implements Serializable {
    private static final long serialVersionUID = -6216356960151890928L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 协议类型，也是GY类型
     */
    @Column(name = "protocol_type")
    private Integer protocolType;

    /**
     * 波特率(1-600 2-1200 3-2400 4-4800 5-7200 6-9600 7-19200)
     */
    private Integer baund;

    /**
     * 1-  2-奇  3-偶
     */
    private Integer parity;

    /**
     * 1 485  2 MBUG
     */
    private Integer channel;

    /**
     * 前导字符FE
     */
    private String prefix;

    /**
     * 唤醒间隔
     */
    @Column(name = "wake_up_interval")
    private Integer wakeUpInterval;

    /**
     * 唤醒次数
     */
    @Column(name = "wake_up_num")
    private Integer wakeUpNum;

    /**
     * 唤醒数据内容(BC字符串)
     */
    @Column(name = "wake_up_cmd")
    private String wakeUpCmd;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取协议类型，也是GY类型
     *
     * @return protocol_type - 协议类型，也是GY类型
     */
    public Integer getProtocolType() {
        return protocolType;
    }

    /**
     * 设置协议类型，也是GY类型
     *
     * @param protocolType 协议类型，也是GY类型
     */
    public void setProtocolType(Integer protocolType) {
        this.protocolType = protocolType;
    }

    /**
     * 获取波特率(1-600 2-1200 3-2400 4-4800 5-7200 6-9600 7-19200)
     *
     * @return baund - 波特率(1-600 2-1200 3-2400 4-4800 5-7200 6-9600 7-19200)
     */
    public Integer getBaund() {
        return baund;
    }

    /**
     * 设置波特率(1-600 2-1200 3-2400 4-4800 5-7200 6-9600 7-19200)
     *
     * @param baund 波特率(1-600 2-1200 3-2400 4-4800 5-7200 6-9600 7-19200)
     */
    public void setBaund(Integer baund) {
        this.baund = baund;
    }

    /**
     * 获取1-  2-奇  3-偶
     *
     * @return parity - 1-  2-奇  3-偶
     */
    public Integer getParity() {
        return parity;
    }

    /**
     * 设置1-  2-奇  3-偶
     *
     * @param parity 1-  2-奇  3-偶
     */
    public void setParity(Integer parity) {
        this.parity = parity;
    }

    /**
     * 获取1 485  2 MBUG
     *
     * @return channel - 1 485  2 MBUG
     */
    public Integer getChannel() {
        return channel;
    }

    /**
     * 设置1 485  2 MBUG
     *
     * @param channel 1 485  2 MBUG
     */
    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    /**
     * 获取前导字符FE
     *
     * @return prefix - 前导字符FE
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * 设置前导字符FE
     *
     * @param prefix 前导字符FE
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * 获取唤醒间隔
     *
     * @return wake_up_interval - 唤醒间隔
     */
    public Integer getWakeUpInterval() {
        return wakeUpInterval;
    }

    /**
     * 设置唤醒间隔
     *
     * @param wakeUpInterval 唤醒间隔
     */
    public void setWakeUpInterval(Integer wakeUpInterval) {
        this.wakeUpInterval = wakeUpInterval;
    }

    /**
     * 获取唤醒次数
     *
     * @return wake_up_num - 唤醒次数
     */
    public Integer getWakeUpNum() {
        return wakeUpNum;
    }

    /**
     * 设置唤醒次数
     *
     * @param wakeUpNum 唤醒次数
     */
    public void setWakeUpNum(Integer wakeUpNum) {
        this.wakeUpNum = wakeUpNum;
    }

    /**
     * 获取唤醒数据内容(BC字符串)
     *
     * @return wake_up_cmd - 唤醒数据内容(BC字符串)
     */
    public String getWakeUpCmd() {
        return wakeUpCmd;
    }

    /**
     * 设置唤醒数据内容(BC字符串)
     *
     * @param wakeUpCmd 唤醒数据内容(BC字符串)
     */
    public void setWakeUpCmd(String wakeUpCmd) {
        this.wakeUpCmd = wakeUpCmd;
    }
}