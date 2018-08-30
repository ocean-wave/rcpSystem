package cn.com.cdboost.collect.util;

import java.io.Serializable;


/**
 * 自定义Json返回类
 * @author snow
 *
 */
public class Ajax implements Serializable {
    public int status;
    public String message; 
    public Object data; 
     
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
    
}
