package cn.com.cdboost.collect.dto.response;

/**
 * @author zc
 * @desc 用户档案批量新增结果
 * @create 2017-12-14 14:06
 **/
public class BatchSaveResultInfo {
    private int successCount;
    private int failCount;

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
}
