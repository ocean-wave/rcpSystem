package cn.com.cdboost.collect.dto;

/**
 * @author xzy
 * @desc 采集器日召测状况分布统计 数据传输对象
 * @create 2017/9/13 0013
 **/
public class CollectNoStatisticsDTO {

    private int successCount;
    private int failCount;
    private int customerCount;
    private String updateTime;
    private String collectNo;
    private String installAddr;

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getFailCount() {
        return failCount;
    }

    public void setFailCount(int failCount) {
        this.failCount = failCount;
    }

    public int getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(int customerCount) {
        this.customerCount = customerCount;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCollectNo() {
        return collectNo;
    }

    public void setCollectNo(String collectNo) {
        this.collectNo = collectNo;
    }

    public String getInstallAddr() {
        return installAddr;
    }

    public void setInstallAddr(String installAddr) {
        this.installAddr = installAddr;
    }
}
