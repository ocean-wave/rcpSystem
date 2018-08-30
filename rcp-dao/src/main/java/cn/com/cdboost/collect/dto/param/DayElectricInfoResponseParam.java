package cn.com.cdboost.collect.dto.param;

import org.springframework.format.annotation.NumberFormat;

import java.io.Serializable;

/**
 * @author wt
 * @desc
 * @create in  2018/5/7
 **/
public class DayElectricInfoResponseParam implements Serializable{

    private static final long serialVersionUID = -3884773122516757974L;
    private String date;
    @NumberFormat(style= NumberFormat.Style.NUMBER, pattern="#.##")
    private float power;
    private float readValue;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getPower() {
        return power;
    }

    public void setPower(float power) {
        this.power = power;
    }

    public float getReadValue() {
        return readValue;
    }

    public void setReadValue(float readValue) {
        this.readValue = readValue;
    }
}
