package cn.com.cdboost.collect.dto.param;

import cn.com.cdboost.collect.vo.QueryListParam;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 */
public class KeyCollectQueryParam extends QueryListParam {
    /**
     *
     */
    @NotBlank(message = "queueGuid 不能为空")
    private String queueGuid;
    @NotBlank(message = "collectDate 不能为空")
    private String collectDate ;

    public String getCollectDate() {
        return collectDate;
    }

    public void setCollectDate(String collectDate) {
        this.collectDate = collectDate;
    }

    @Override
    protected String defaultSortName() {
        return null;
    }

    @Override
    protected String defaultSortOrder() {
        return null;
    }

    public String getQueueGuid() {
        return queueGuid;
    }

    public void setQueueGuid(String queueGuid) {
        this.queueGuid = queueGuid;
    }
}
