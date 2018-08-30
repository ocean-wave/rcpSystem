package cn.com.cdboost.collect.dto.param;




import cn.com.cdboost.collect.vo.QueryListParam;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CustomerLogParam extends QueryListParam implements Serializable {
    private static final long serialVersionUID = 6427586599101066920L;

    @NotBlank(message = "startDate 不能为空")
   private String startDate;
    @NotBlank(message = "endDate 不能为空")
   private String endDate;

   private String userId ;

   private String content;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    protected String defaultSortName() {
        return null;
    }

    @Override
    protected String defaultSortOrder() {
        return null;
    }










}