package cn.com.cdboost.collect.dto.response;


import java.util.List;
import java.util.Set;

/**
 * 方案详情
 */
public class ChargingSchemeInfo {

    private Set<Integer> power;

    public Set<Integer> getPower() {
        return power;
    }

    public void setPower(Set<Integer> power) {
        this.power = power;
    }
}
