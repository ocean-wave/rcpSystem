package cn.com.cdboost.collect.dto.response;

/**
 * Created by Administrator on 2018/5/16 0016.
 */
public class BuildInfoDto {
    /**
     * 组织编号
     */
    private Long orgNo;

    /**
     * 楼栋编号
     */
    private String buildNo;

    /**
     * 楼栋名称
     */
    private String buildName;

    /**
     * 该楼栋拥有的用户数
     */
    private Long houseHolds;

    public Long getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(Long orgNo) {
        this.orgNo = orgNo;
    }

    public String getBuildNo() {
        return buildNo;
    }

    public void setBuildNo(String buildNo) {
        this.buildNo = buildNo;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public Long getHouseHolds() {
        return houseHolds;
    }

    public void setHouseHolds(Long houseHolds) {
        this.houseHolds = houseHolds;
    }
}
