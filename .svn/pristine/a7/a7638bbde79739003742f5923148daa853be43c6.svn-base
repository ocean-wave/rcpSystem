package cn.com.cdboost.collect.vo;

import com.alibaba.fastjson.annotation.JSONField;

import javax.validation.constraints.NotNull;


/**
 * @author zc
 * @desc 分页查询参数实体类
 * @create 2017-10-31 09:30
 **/
public abstract class QueryListParam {
    private Long total;
    /**
     * 页号
     */
    @NotNull(message = "pageNumber不能为null")
    private Integer pageNumber;

    /**
     * 每页显示数量
     */
    @NotNull(message = "pageSize不能为null")
    private Integer pageSize;
    /**
     * 页数
     */
    private String pageEndNum;
    /**
     *  排序字段
     */
    private String sortName;

    /**
     * asc或desc
     */
    private String sortOrder;
    private Integer pageEndIndex;

    public Integer getPageEndIndex() {
        return pageEndIndex;
    }

    public void setPageEndIndex(Integer pageEndIndex) {
        this.pageEndIndex = pageEndIndex;
    }

    public String getPageEndNum() {
        return pageEndNum;
    }

    public void setPageEndNum(String pageEndNum) {
        this.pageEndNum = pageEndNum;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    /**
     * 子类继承，sortName为空时，orderBy语句默认使用该字段
     * @return
     */

    protected abstract String defaultSortName();

    /**
     * 子类继承，sortOrder为空时，orderBy语句默认使用该字段
     * @return
     */
    protected abstract String defaultSortOrder();

    /**
     * 获取该查询参数的orderBy语句
     * @return
     */
    @JSONField(serialize=false)
    protected String getOrderBy() {
        String sortName = this.getSortName();
        if (sortName == null | "".equals(sortName)) {
            return this.defaultSortName() + " " + this.defaultSortOrder();
        }

        String sortOrder = this.getSortOrder();
        if (sortOrder == null | "".equals(sortOrder)) {
            return sortName + " " + this.defaultSortOrder();
        }
        String orderBy = sortName + " " + sortOrder;
        return orderBy;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        // 驼峰字符串转成下划线字符串
        if (sortName != null && !"".equals(sortName)) {
            String underlineSortName = this.camelToUnderline(sortName);
            this.sortName = underlineSortName;
        }
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }


    /**
     * 驼峰字符串转成下划线字符串
     * @param param
     * @return
     */
    private String camelToUnderline(String param){
        if (param==null||"".equals(param.trim())){
            return "";
        }
        int len=param.length();
        StringBuilder sb=new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c=param.charAt(i);
            if (Character.isUpperCase(c)){
                sb.append("_");
                sb.append(Character.toLowerCase(c));
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }

}
