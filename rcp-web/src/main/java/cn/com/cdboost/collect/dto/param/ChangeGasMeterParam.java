package cn.com.cdboost.collect.dto.param;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 气表更换操作输入参数
 */
public class ChangeGasMeterParam extends ChangeMeterBaseParam{

    /**
     * 旧气表相关信息
     */
    @NotNull(message = "oldGasMeter不能为null")
    @Valid
    private OldGasMeterParam oldGasMeter;

    /**
     * 新气表相关信息
     */
    @NotNull(message = "newGasMeter不能为null")
    @Valid
    private NewGasMeterParam newGasMeter;

    public OldGasMeterParam getOldGasMeter() {
        return oldGasMeter;
    }

    public void setOldGasMeter(OldGasMeterParam oldGasMeter) {
        this.oldGasMeter = oldGasMeter;
    }

    public NewGasMeterParam getNewGasMeter() {
        return newGasMeter;
    }

    public void setNewGasMeter(NewGasMeterParam newGasMeter) {
        this.newGasMeter = newGasMeter;
    }
}
