package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "em_d_fee_priceitem")
public class FeePriceItem implements Serializable {
    private static final long serialVersionUID = 2772968419576670729L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 参数项目标识
     */
    @Column(name = "item_code")
    private String itemCode;

    /**
     * 参数项目名称
     */
    @Column(name = "item_name")
    private String itemName;

    /**
     * 展示顺序
     */
    @Column(name = "sort_no")
    private Integer sortNo;

    /**
     * 参数项目单位
     */
    private String unit;

    /**
     * 设备类型(01-LoRa网管，02-LoRa节点，04-集中器，05-采集器，07-电表...)
     */
    @Column(name = "device_type")
    private String deviceType;

    /**
     * 是否有效
     */
    @Column(name = "is_enabled")
    private Integer isEnabled;

    /**
     * 标识为一组数据 例如尖电价 第一套 和第二套为一组数据
     */
    @Column(name = "group_flag")
    private Integer groupFlag;

    /**
     * 参数标识 1-第一套 2-第二套
     */
    @Column(name = "item_flag")
    private Integer itemFlag;

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
     * 获取参数项目标识
     *
     * @return item_code - 参数项目标识
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * 设置参数项目标识
     *
     * @param itemCode 参数项目标识
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * 获取参数项目名称
     *
     * @return item_name - 参数项目名称
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * 设置参数项目名称
     *
     * @param itemName 参数项目名称
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 获取展示顺序
     *
     * @return sort_no - 展示顺序
     */
    public Integer getSortNo() {
        return sortNo;
    }

    /**
     * 设置展示顺序
     *
     * @param sortNo 展示顺序
     */
    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    /**
     * 获取参数项目单位
     *
     * @return unit - 参数项目单位
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 设置参数项目单位
     *
     * @param unit 参数项目单位
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * 获取设备类型(01-LoRa网管，02-LoRa节点，04-集中器，05-采集器，07-电表...)
     *
     * @return device_type - 设备类型(01-LoRa网管，02-LoRa节点，04-集中器，05-采集器，07-电表...)
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * 设置设备类型(01-LoRa网管，02-LoRa节点，04-集中器，05-采集器，07-电表...)
     *
     * @param deviceType 设备类型(01-LoRa网管，02-LoRa节点，04-集中器，05-采集器，07-电表...)
     */
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * 获取是否有效
     *
     * @return is_enabled - 是否有效
     */
    public Integer getIsEnabled() {
        return isEnabled;
    }

    /**
     * 设置是否有效
     *
     * @param isEnabled 是否有效
     */
    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }

    /**
     * 获取标识为一组数据 例如尖电价 第一套 和第二套为一组数据
     *
     * @return group_flag - 标识为一组数据 例如尖电价 第一套 和第二套为一组数据
     */
    public Integer getGroupFlag() {
        return groupFlag;
    }

    /**
     * 设置标识为一组数据 例如尖电价 第一套 和第二套为一组数据
     *
     * @param groupFlag 标识为一组数据 例如尖电价 第一套 和第二套为一组数据
     */
    public void setGroupFlag(Integer groupFlag) {
        this.groupFlag = groupFlag;
    }

    /**
     * 获取参数标识 1-第一套 2-第二套
     *
     * @return item_flag - 参数标识 1-第一套 2-第二套
     */
    public Integer getItemFlag() {
        return itemFlag;
    }

    /**
     * 设置参数标识 1-第一套 2-第二套
     *
     * @param itemFlag 参数标识 1-第一套 2-第二套
     */
    public void setItemFlag(Integer itemFlag) {
        this.itemFlag = itemFlag;
    }
}