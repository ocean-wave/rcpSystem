package cn.com.cdboost.collect.dto;

/**
 * @author zc
 * @desc 电价参数实体类
 * @create 2017-07-13 10:13
 **/
public class FeePriceItemParamInfo {

    private String priceSolsCode;

    private String itemCode;

    private String itemName;

    private double dataValue;

    private Integer groupFlag;

    private Integer itemFlag;

    public String getPriceSolsCode() {
        return priceSolsCode;
    }

    public void setPriceSolsCode(String priceSolsCode) {
        this.priceSolsCode = priceSolsCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getDataValue() {
        return dataValue;
    }

    public void setDataValue(double dataValue) {
        this.dataValue = dataValue;
    }

    public Integer getGroupFlag() {
        return groupFlag;
    }

    public void setGroupFlag(Integer groupFlag) {
        this.groupFlag = groupFlag;
    }

    public Integer getItemFlag() {
        return itemFlag;
    }

    public void setItemFlag(Integer itemFlag) {
        this.itemFlag = itemFlag;
    }
}
