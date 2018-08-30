package cn.com.cdboost.collect.dto.response;

/**
 * 阿里支付用户相关信息
 */
public class AlipayUserInfo {
    /**
     * 支付宝用户id
     */
    private String userId;

    /**
     * 支付宝用户昵称
     */
    private String nickName;

    /**
     * 支付宝用户头像
     */
    private String avatar;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
