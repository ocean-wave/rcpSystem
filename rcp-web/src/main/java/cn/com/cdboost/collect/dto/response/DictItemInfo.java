package cn.com.cdboost.collect.dto.response;

/**
 * 字典表返回信息
 */
public class DictItemInfo {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 字典项值
     */
    private String dictItemValue;

    /**
     * 字典项名称
     */
    private String dictItemName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDictItemValue() {
        return dictItemValue;
    }

    public void setDictItemValue(String dictItemValue) {
        this.dictItemValue = dictItemValue;
    }

    public String getDictItemName() {
        return dictItemName;
    }

    public void setDictItemName(String dictItemName) {
        this.dictItemName = dictItemName;
    }
}
