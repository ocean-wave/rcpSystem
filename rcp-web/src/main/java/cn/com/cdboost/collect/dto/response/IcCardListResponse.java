package cn.com.cdboost.collect.dto.response;

import java.math.BigDecimal;
import java.util.List;

/**
 * 查询用户所绑定的IC卡列表返回信息
 */
public class IcCardListResponse {
    /**
     * 用户账户剩余金额
     */
    private BigDecimal remainAmount;

    /**
     * 该用户所绑定的IC卡卡号
     */
    private List<IcCardListVo> list;

    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }

    public List<IcCardListVo> getList() {
        return list;
    }

    public void setList(List<IcCardListVo> list) {
        this.list = list;
    }
}
