package JDBCUtil;

import DAO.ColumnSpecification.UserBookColumns;
import Entities.Book;
import Entities.User;
import Entities.UserBook;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetUserBookBuilder {

    public UserBook BuildUserBookFromResultSet(ResultSet resultSet) throws SQLException{

        UserBook userBook = new UserBook();

        ResultSetBookBuilder bookBuilder = new ResultSetBookBuilder();
        ResultSetUserBuilder userBuilder = new ResultSetUserBuilder();

        try {
            userBook.setBook(bookBuilder.BuildBookFromResultSet(resultSet));
            userBook.setUser(userBuilder.BuildUserFromResultSet(resultSet));
        }
        catch (Exception ex){
            userBook.setBook(new Book());
            userBook.setUser(new User());
        }

        userBook.setId(resultSet.getLong(UserBookColumns.ID));
        userBook.setCount(resultSet.getInt(UserBookColumns.COUNT));

        return userBook;
    }
}
