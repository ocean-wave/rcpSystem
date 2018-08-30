package cn.com.cdboost.collect.dto.param;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 充电ic卡添加
 */
public class ChargerICCardAddParam {

    //ic卡号
    @NotBlank(message = "cardId不能为空")
    @Valid
    private String cardId;

    //初始金额
    @NotNull(message = "initAmount不能为null")
    private BigDecimal initAmount;

    //姓名
    private String customerName;

    //联系电话
    @NotBlank(message = "customerContact不能为空")
    private String customerContact;

    //小区标识
    @NotBlank(message = "projectGuid不能为空")
    private String projectGuid;

    //备注
    private String remark;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public BigDecimal getInitAmount() {
        return initAmount;
    }

    public void setInitAmount(BigDecimal initAmount) {
        this.initAmount = initAmount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public String getProjectGuid() {
        return projectGuid;
    }

    public void setProjectGuid(String projectGuid) {
        this.projectGuid = projectGuid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "ChargerICCardAddParam{" +
                "cardId='" + cardId + '\'' +
                ", initAmount=" + initAmount +
                ", name='" + customerName + '\'' +
                ", customerContact='" + customerContact + '\'' +
                ", projectGuid='" + projectGuid + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
