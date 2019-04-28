package TokenUtil;

public class UserTokenModel {
    private long roleId;

    private String email;

    public UserTokenModel(long roleId, String email) {
        this.roleId = roleId;
        this.email = email;
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
