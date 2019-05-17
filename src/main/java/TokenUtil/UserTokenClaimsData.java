package TokenUtil;

public class UserTokenClaimsData {
    private long roleId;

    private String email;

    public UserTokenClaimsData(long roleId, String email) {
        this.roleId = roleId;
        this.email = email;
    }

    public UserTokenClaimsData() {

    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
