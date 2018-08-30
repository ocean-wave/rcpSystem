package cn.com.cdboost.collect.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "em_d_query_impot")
public class Impot  implements Serializable{

    private static final long serialVersionUID = 5384900181073607186L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 查询号
     */
    @Column(name = "search_no")
    private String searchNo;

    /**
     * 数据类型 1标识表计cno 2-标识customer_no 3 标识org_no
     */
    @Column(name = "data_type")
    private Integer dataType;

    /**
     * 1-查询子级数据 0-不查询子级数据
     */
    @Column(name = "is_search_child")
    private Integer isSearchChild;

    /**
     * 查询批次号
     */
    @Column(name = "impot_batch")
    private String impotBatch;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "child_flag")
    private String childFlag;
    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Integer getId() {
        return id;
    }

    public String getChildFlag() {
        return childFlag;
    }

    public void setChildFlag(String childFlag) {
        this.childFlag = childFlag;
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
     * 获取查询号
     *
     * @return search_no - 查询号
     */
    public String getSearchNo() {
        return searchNo;
    }

    /**
     * 设置查询号
     *
     * @param searchNo 查询号
     */
    public void setSearchNo(String searchNo) {
        this.searchNo = searchNo;
    }

    /**
     * 获取数据类型 1标识表计cno 2-标识customer_no 3 标识org_no
     *
     * @return data_type - 数据类型 1标识表计cno 2-标识customer_no 3 标识org_no
     */
    public Integer getDataType() {
        return dataType;
    }

    /**
     * 设置数据类型 1标识表计cno 2-标识customer_no 3 标识org_no
     *
     * @param dataType 数据类型 1标识表计cno 2-标识customer_no 3 标识org_no
     */
    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    /**
     * 获取1-查询子级数据 0-不查询子级数据
     *
     * @return is_search_child - 1-查询子级数据 0-不查询子级数据
     */
    public Integer getIsSearchChild() {
        return isSearchChild;
    }

    /**
     * 设置1-查询子级数据 0-不查询子级数据
     *
     * @param isSearchChild 1-查询子级数据 0-不查询子级数据
     */
    public void setIsSearchChild(Integer isSearchChild) {
        this.isSearchChild = isSearchChild;
    }

    /**
     * 获取查询批次号
     *
     * @return impot_batch - 查询批次号
     */
    public String getImpotBatch() {
        return impotBatch;
    }

    /**
     * 设置查询批次号
     *
     * @param impotBatch 查询批次号
     */
    public void setImpotBatch(String impotBatch) {
        this.impotBatch = impotBatch;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */

    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}