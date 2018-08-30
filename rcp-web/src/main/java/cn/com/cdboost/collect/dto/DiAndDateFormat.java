package cn.com.cdboost.collect.dto;

import org.hibernate.validator.constraints.NotBlank;

/**
 * App端发送采集指令时包含的di和dateformat
 */
public class DiAndDateFormat {
    @NotBlank(message = "di不能为空")
    private String di;

    @NotBlank(message = "dataFormat不能为空")
    private String dataFormat;

    public String getDi() {
        return di;
    }

    public void setDi(String di) {
        this.di = di;
    }

    public String getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
    }
}
