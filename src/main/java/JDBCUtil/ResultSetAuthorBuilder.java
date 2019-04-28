package JDBCUtil;

import DAO.ColumnSpecification.AuthorColumns;
import Entities.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetAuthorBuilder {

    public Author BuildAuthorFromResultSet(ResultSet resultSet) throws SQLException {

        Author author = new Author();

        author.setId(resultSet.getLong(AuthorColumns.ID));
        author.setFirstName(resultSet.getString(AuthorColumns.FIRST_NAME));
        author.setLastName(resultSet.getString(AuthorColumns.LAST_NAME));

        return  author;
    }
}
