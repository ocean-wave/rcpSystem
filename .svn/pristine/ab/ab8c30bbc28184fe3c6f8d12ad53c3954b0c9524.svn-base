package cn.com.cdboost.collect.dto.param;




import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class DeviceEventUpdateParam implements Serializable {
    private static final long serialVersionUID = 6427586599101066920L;
    /**
     * 主键
     */
    @NotNull(message = "id不能为空")
    private Integer id;



    /**
     * 事件处理人员
     */
    @NotNull(message = "solve_user_id不能为空")
    private Integer solveUserId ;

    /**
     * 解决方案
     */
    @NotBlank(message = "solve_content不能为空")
    private String solveContent ;

    public Integer getId() {
        return id;
    }



    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSolveUserId() {
        return solveUserId;
    }

    public void setSolveUserId(Integer solveUserId) {
        this.solveUserId = solveUserId;
    }

    public String getSolveContent() {
        return solveContent;
    }

    public void setSolveContent(String solveContent) {
        this.solveContent = solveContent;
    }
}