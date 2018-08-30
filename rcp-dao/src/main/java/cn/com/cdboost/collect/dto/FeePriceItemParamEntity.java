package cn.com.cdboost.collect.dto;

/**
 * @author zc
 * @desc 具体参数实体类
 * @create 2017-07-13 10:43
 **/
public class FeePriceItemParamEntity {

    private String priceSolsCode;

    private String itemCode1;

    private String itemName1;

    private double dataValue1;

    private String itemCode2;

    private String itemName2;

    private double dataValue2;

    public String getPriceSolsCode() {
        return priceSolsCode;
    }

    public void setPriceSolsCode(String priceSolsCode) {
        this.priceSolsCode = priceSolsCode;
    }

    public String getItemCode1() {
        return itemCode1;
    }

    public void setItemCode1(String itemCode1) {
        this.itemCode1 = itemCode1;
    }

    public String getItemName1() {
        return itemName1;
    }

    public void setItemName1(String itemName1) {
        this.itemName1 = itemName1;
    }

    public double getDataValue1() {
        return dataValue1;
    }

    public void setDataValue1(double dataValue1) {
        this.dataValue1 = dataValue1;
    }

    public String getItemCode2() {
        return itemCode2;
    }

    public void setItemCode2(String itemCode2) {
        this.itemCode2 = itemCode2;
    }

    public String getItemName2() {
        return itemName2;
    }

    public void setItemName2(String itemName2) {
        this.itemName2 = itemName2;
    }

    public double getDataValue2() {
        return dataValue2;
    }

    public void setDataValue2(double dataValue2) {
        this.dataValue2 = dataValue2;
    }
}
