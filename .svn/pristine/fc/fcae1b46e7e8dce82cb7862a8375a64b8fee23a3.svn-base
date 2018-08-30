package cn.com.cdboost.collect.dto.param;


import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import java.util.List;

/**
 * 手动补录采集数据
 */
public class CreateManualRecordParam {
    /**
     * 客户标识
     */
    @NotBlank(message = "customerNo 不能为空")
    private String customerNo;

    /**
     * 数据集合
     */
    @Valid
    private List<ManualRecordVo> manualRecordVoList;

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public List<ManualRecordVo> getManualRecordVoList() {
        return manualRecordVoList;
    }

    public void setManualRecordVoList(List<ManualRecordVo> manualRecordVoList) {
        this.manualRecordVoList = manualRecordVoList;
    }
}
