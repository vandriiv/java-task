package JDBCUtil;

import DAO.ColumnSpecification.UserColumns;
import Entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetUserBuilder {

    public User BuildUserFromResultSet(ResultSet resultSet) throws SQLException{

        User user = new User();
        ResultSetRoleBuilder roleBuilder = new ResultSetRoleBuilder();

        user.setRole(roleBuilder.BuildRoleFromResultSet(resultSet));
        user.setRoleId(resultSet.getLong(UserColumns.ROLE_ID));
        user.setId(resultSet.getLong(UserColumns.ID));
        user.setEmail(resultSet.getString(UserColumns.EMAIL));
        user.setPassword(resultSet.getString(UserColumns.PASSWORD));
        user.setPhoneNumber(resultSet.getString(UserColumns.PHONE_NUMBER));

        return user;
    }
}
