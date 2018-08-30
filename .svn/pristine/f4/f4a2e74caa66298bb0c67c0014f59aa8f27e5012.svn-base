package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "em_d_changemeteroth")
public class ChangeMeterOth implements Serializable{
    private static final long serialVersionUID = -3491053817594950409L;
    /**
     * 数据唯一标识
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 当前唯一标识
     */
    @Column(name = "change_flag")
    private String changeFlag;

    /**
     * 参数项目标识
     */
    @Column(name = "item_code")
    private String itemCode;

    /**
     * 参数项设定的值
     */
    @Column(name = "data_value")
    private String dataValue;

    /**
     * 获取数据唯一标识
     *
     * @return id - 数据唯一标识
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置数据唯一标识
     *
     * @param id 数据唯一标识
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取当前唯一标识
     *
     * @return change_flag - 当前唯一标识
     */
    public String getChangeFlag() {
        return changeFlag;
    }

    /**
     * 设置当前唯一标识
     *
     * @param changeFlag 当前唯一标识
     */
    public void setChangeFlag(String changeFlag) {
        this.changeFlag = changeFlag;
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
     * 获取参数项设定的值
     *
     * @return data_value - 参数项设定的值
     */
    public String getDataValue() {
        return dataValue;
    }

    /**
     * 设置参数项设定的值
     *
     * @param dataValue 参数项设定的值
     */
    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }
}