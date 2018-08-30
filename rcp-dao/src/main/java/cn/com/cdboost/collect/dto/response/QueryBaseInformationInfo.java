package cn.com.cdboost.collect.dto.response;

/**
 * Created by Administrator on 2018/5/16 0016.
 */
public class QueryBaseInformationInfo {

    /**
     * 总户数
     */
   private String userTotal;
    /**
     * 跳闸用户
     */
    private String userTrip;
    /**
     * 告警用户
     */
    private String userAlerm;
    /**
     * 当月电量
     */
    private String mounthEq;
    /**
     * 当月电费
     */
    private String mounthCost;

    public String getUserTotal() {
        return userTotal;
    }

    public void setUserTotal(String userTotal) {
        this.userTotal = userTotal;
    }

    public String getUserTrip() {
        return userTrip;
    }

    public void setUserTrip(String userTrip) {
        this.userTrip = userTrip;
    }

    public String getUserAlerm() {
        return userAlerm;
    }

    public void setUserAlerm(String userAlerm) {
        this.userAlerm = userAlerm;
    }

    public String getMounthEq() {
        return mounthEq;
    }

    public void setMounthEq(String mounthEq) {
        this.mounthEq = mounthEq;
    }

    public String getMounthCost() {
        return mounthCost;
    }

    public void setMounthCost(String mounthCost) {
        this.mounthCost = mounthCost;
    }
}
