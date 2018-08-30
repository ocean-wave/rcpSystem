package cn.com.cdboost.collect.vo;

import java.io.Serializable;

/**
 * 返回给前端的包装类
 */
public class Result<T> implements Serializable{
    private static final long serialVersionUID = -7784581817460701362L;
    private static final int SUCCESS = 1;
    private static final int FAIL = 0;
    public enum errorType{
        fail("调用失败",0),
        datenotexist("批次数据不存在",100),
        tokenerror("token错误",101);
        private String desc;
        private int code;
        errorType(String desc,int code) {
            this.desc=desc;
            this.code=code;
        }
        public String getdesc(){
            return this.desc;
        }

        public int getcode(){
            return this.code;
        }
    }
    /**
     * 结果码
     */
    private int code;
    /**
     * 结果码对应的描述信息
     */
    private String msg;
    private Long total;
    /**
     * 请求返回数据
     * 如果请求失败，则数据返回null
     */
    private T rows;

    public Result() {
        this.code = SUCCESS;
        this.msg = "success";
    }

    public Result(String msg) {
        this.code = SUCCESS;
        this.msg = msg;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public void error(String msg) {
        this.code = FAIL;
        this.msg = msg;
    }
    public void error(int code) {
        this.code = code;
    }
    public void error(int code,String msg) {
        this.code = code;
        this.msg = msg;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getRows() {
        return rows;
    }

    public void setRows(T rows) {
        this.rows = rows;
    }
}
