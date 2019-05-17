package TokenUtil;

public class LoginResponseData {
    private String access_token;
    private UserTokenClaimsData userInfo;

    public LoginResponseData() {
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public UserTokenClaimsData getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserTokenClaimsData userInfo) {
        this.userInfo = userInfo;
    }
}
