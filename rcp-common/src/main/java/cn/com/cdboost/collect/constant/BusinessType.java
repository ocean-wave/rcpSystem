package cn.com.cdboost.collect.constant;

/**
 * 账户操作业务类型
 */
public enum BusinessType {
    // 提现相关
    WECHAT_WITHDRAW_CASH(1,"微信提现"),
    ALIPAY_WITHDRAW_CASH(2,"支付宝提现"),
    WECHAT_WITHDRAW_CASH_REFUND(3,"微信提现失败退回"),
    ALIPAY_WITHDRAW_CASH_REFUND(4,"支付宝提现失败退回"),

    // 充电相关
    TEMPORARY_CHARGE_SUCCESS(5,"临时充电开电成功"),
    FULL_CHARGE_SUCCESS(6,"一键充满开电成功"),
    MONTH_CARD_CHARGE_SUCCESS(7,"月卡充电开电成功"),

    // 账户充值相关
    MONTH_CARD_RECHARGE(8,"月卡购买充次数"),
    ACTIVITY_RECHARGE(9,"活动页面余额充值"),
    CHARGE_ELECTRIC_RECHARGE(10,"充电余额不足引起的余额充值"),
    BIND_ICCARD_RECHARGE(11,"绑定IC卡时引起的余额充值"),
    UNBIND_ICCARD_RECHARGE(12,"解除绑定IC卡时引起的余额扣减");


    /**
     * 类型
     */
    private Integer type;
    /**
     * 描述
     */
    private String desc;

    BusinessType(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static final String getDescByType(Integer type) {
        for (BusinessType businessType : BusinessType.values()) {
            if (businessType.getType().equals(type)) {
                return businessType.getDesc();
            }
        }
        return "";
    }
}