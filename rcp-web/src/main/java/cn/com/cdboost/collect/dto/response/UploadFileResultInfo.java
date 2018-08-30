package cn.com.cdboost.collect.dto.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * @author zc
 * @desc
 * @create 2017-12-14 14:14
 **/
public class UploadFileResultInfo {
    private String saveName;
    private String importId;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date importTime;
    private String fileName;

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    public String getImportId() {
        return importId;
    }

    public void setImportId(String importId) {
        this.importId = importId;
    }

    public Date getImportTime() {
        return importTime;
    }

    public void setImportTime(Date importTime) {
        this.importTime = importTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
