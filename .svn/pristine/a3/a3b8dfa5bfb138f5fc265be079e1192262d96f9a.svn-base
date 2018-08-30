package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "em_d_fee_pricesolsdtl")
public class FeePriceSolsDtl implements Serializable {
    private static final long serialVersionUID = -8891407834945876326L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 电价方案代码,时间加序号(2017062701)
     */
    @Column(name = "price_sols_code")
    private String priceSolsCode;

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
     * 获取电价方案代码,时间加序号(2017062701)
     *
     * @return price_sols_code - 电价方案代码,时间加序号(2017062701)
     */
    public String getPriceSolsCode() {
        return priceSolsCode;
    }

    /**
     * 设置电价方案代码,时间加序号(2017062701)
     *
     * @param priceSolsCode 电价方案代码,时间加序号(2017062701)
     */
    public void setPriceSolsCode(String priceSolsCode) {
        this.priceSolsCode = priceSolsCode;
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