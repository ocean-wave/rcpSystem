package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "l_d_dlt645")
public class Dlt645 implements Serializable {
    private static final long serialVersionUID = -4503788139384872395L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * DTL645数据项DI, 07规约4个字节， 97规约2个字节
     */
    private String di;

    /**
     * di拆分，如组合有功总，分项之间用，号分隔
     */
    @Column(name = "di_split")
    private String diSplit;

    /**
     * 数据长度
     */
    @Column(name = "data_len")
    private Integer dataLen;

    /**
     * 单个数据项长度
     */
    @Column(name = "data_item_len")
    private Integer dataItemLen;

    /**
     * 小数点从右往左数的位置，字节 如 4.2 , 4字节BCD,转换为字符串8字节，2小数点在字符串中的位置
     */
    @Column(name = "data_item_dec_pos")
    private Integer dataItemDecPos;

    /**
     * 电表规约1-97规约  30-07规约
     */
    private Integer gy;

    /**
     * 数据项描述
     */
    @Column(name = "di_desc")
    private String diDesc;

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
     * 获取DTL645数据项DI, 07规约4个字节， 97规约2个字节
     *
     * @return di - DTL645数据项DI, 07规约4个字节， 97规约2个字节
     */
    public String getDi() {
        return di;
    }

    /**
     * 设置DTL645数据项DI, 07规约4个字节， 97规约2个字节
     *
     * @param di DTL645数据项DI, 07规约4个字节， 97规约2个字节
     */
    public void setDi(String di) {
        this.di = di;
    }

    /**
     * 获取di拆分，如组合有功总，分项之间用，号分隔
     *
     * @return di_split - di拆分，如组合有功总，分项之间用，号分隔
     */
    public String getDiSplit() {
        return diSplit;
    }

    /**
     * 设置di拆分，如组合有功总，分项之间用，号分隔
     *
     * @param diSplit di拆分，如组合有功总，分项之间用，号分隔
     */
    public void setDiSplit(String diSplit) {
        this.diSplit = diSplit;
    }

    /**
     * 获取数据长度
     *
     * @return data_len - 数据长度
     */
    public Integer getDataLen() {
        return dataLen;
    }

    /**
     * 设置数据长度
     *
     * @param dataLen 数据长度
     */
    public void setDataLen(Integer dataLen) {
        this.dataLen = dataLen;
    }

    /**
     * 获取单个数据项长度
     *
     * @return data_item_len - 单个数据项长度
     */
    public Integer getDataItemLen() {
        return dataItemLen;
    }

    /**
     * 设置单个数据项长度
     *
     * @param dataItemLen 单个数据项长度
     */
    public void setDataItemLen(Integer dataItemLen) {
        this.dataItemLen = dataItemLen;
    }

    /**
     * 获取小数点从右往左数的位置，字节 如 4.2 , 4字节BCD,转换为字符串8字节，2小数点在字符串中的位置
     *
     * @return data_item_dec_pos - 小数点从右往左数的位置，字节 如 4.2 , 4字节BCD,转换为字符串8字节，2小数点在字符串中的位置
     */
    public Integer getDataItemDecPos() {
        return dataItemDecPos;
    }

    /**
     * 设置小数点从右往左数的位置，字节 如 4.2 , 4字节BCD,转换为字符串8字节，2小数点在字符串中的位置
     *
     * @param dataItemDecPos 小数点从右往左数的位置，字节 如 4.2 , 4字节BCD,转换为字符串8字节，2小数点在字符串中的位置
     */
    public void setDataItemDecPos(Integer dataItemDecPos) {
        this.dataItemDecPos = dataItemDecPos;
    }

    /**
     * 获取电表规约1-97规约  30-07规约
     *
     * @return gy - 电表规约1-97规约  30-07规约
     */
    public Integer getGy() {
        return gy;
    }

    /**
     * 设置电表规约1-97规约  30-07规约
     *
     * @param gy 电表规约1-97规约  30-07规约
     */
    public void setGy(Integer gy) {
        this.gy = gy;
    }

    /**
     * 获取数据项描述
     *
     * @return di_desc - 数据项描述
     */
    public String getDiDesc() {
        return diDesc;
    }

    /**
     * 设置数据项描述
     *
     * @param diDesc 数据项描述
     */
    public void setDiDesc(String diDesc) {
        this.diDesc = diDesc;
    }
}