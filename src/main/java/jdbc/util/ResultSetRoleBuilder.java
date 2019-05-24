package jdbc.util;

import dao.ColumnSpecification.RoleColumns;
import entities.Role;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetRoleBuilder {

    public Role BuildRoleFromResultSet(ResultSet resultSet) throws SQLException{

        Role role = new Role();

        role.setId(resultSet.getLong(RoleColumns.ID));
        role.setName(resultSet.getString(RoleColumns.NAME));

        return role;
    }
}
