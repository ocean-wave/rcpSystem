package cn.com.cdboost.collect.model;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "em_b_meterpointcode")
public class MeterPointCode implements Serializable {
    private static final long serialVersionUID = 2205116943895195247L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 测量点编码
     */
    @Column(name = "point_code")
    private Integer pointCode;

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
     * 获取测量点编码
     *
     * @return point_code - 测量点编码
     */
    public Integer getPointCode() {
        return pointCode;
    }

    /**
     * 设置测量点编码
     *
     * @param pointCode 测量点编码
     */
    public void setPointCode(Integer pointCode) {
        this.pointCode = pointCode;
    }
}