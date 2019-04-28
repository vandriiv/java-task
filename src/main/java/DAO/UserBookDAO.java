package DAO;

import DAO.Interfaces.IBookDAO;
import DAO.Interfaces.IUserBookDAO;
import DBconnection.DBPool;
import Entities.UserBook;
import Exceptions.DAOException;
import Exceptions.Enums.DAOExceptionOperation;
import JDBCUtil.ResultSetUserBookBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserBookDAO implements IUserBookDAO {

    private final String CREATE_USERBOOK = "insert into userbook(user_id,book_id,count) values (?,?,?)";

    private final String UPDATE_USERBOOK = "update userbook set count = ? where (user_id=? and book_id=?)";

    private final String REMOVE_USERBOOK = "delete userbook where id =?";
    
    private final String GET_USERBOOK = "select ub.id as user_book_id, ub.count as ubCount from userbook ub where (user_id=? and book_id=?)";

    private IBookDAO bookDAO = new BookDAO();

    public void create(UserBook userBook,Connection connection) throws DAOException {

        try (
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USERBOOK)){

            preparedStatement.setLong(1,userBook.getUser().getId());
            preparedStatement.setLong(2,userBook.getBook().getId());
            preparedStatement.setInt(3,userBook.getCount());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e.getMessage(), DAOExceptionOperation.INSERT);
        }

    }

    public void updateBookCount(UserBook userBook, Connection connection) throws DAOException {

        try (
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USERBOOK)){

            preparedStatement.setInt(1,userBook.getCount());
            preparedStatement.setLong(2,userBook.getUser().getId());
            preparedStatement.setLong(3,userBook.getBook().getId());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e.getMessage(), DAOExceptionOperation.UPDATE);
        }

    }

    public void removeBookFromUsersBook(UserBook userBook) throws DAOException {

        try (Connection connection = DBPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_USERBOOK)){

            preparedStatement.setLong(1,userBook.getId());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e.getMessage(), DAOExceptionOperation.DELETE);
        }
    }

    public UserBook getUserBook(long userId, long bookId, Connection connection) throws DAOException {
        UserBook userBook = null;
        ResultSetUserBookBuilder userBookBuilder = new ResultSetUserBookBuilder();
        try (
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USERBOOK)){

            preparedStatement.setLong(1,userId);
            preparedStatement.setLong(2,bookId);

            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()) {
                userBook = userBookBuilder.BuildUserBookFromResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e.getMessage(), DAOExceptionOperation.SELECT);
        }

        return userBook;
    }
}
