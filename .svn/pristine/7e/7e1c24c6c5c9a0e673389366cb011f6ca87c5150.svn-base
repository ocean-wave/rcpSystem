package cn.com.cdboost.collect.dto.param;

import cn.com.cdboost.collect.vo.QueryListParam;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 补抄工单列表，明细查询参数
 */
public class MeterSuppTaskDetailQueryParam extends QueryListParam{

    /**
     * 工单编号
     */
    @NotBlank(message = "taskNo不能为空")
    private String taskNo;

    /**
     * 补抄状态 0待抄 1已采
     */
    @NotNull(message = "status不能为空")
    private Integer status;

    /**
     * 0 已完成 无异常 1标识已完成 有异常
     */
    @NotNull(message = "errCode不能为空")
    private Integer errCode;

    @Override
    protected String defaultSortName() {
        return null;
    }

    @Override
    protected String defaultSortOrder() {
        return null;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }
}
