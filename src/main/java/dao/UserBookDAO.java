package dao;

import dao.Interfaces.IBookDAO;
import dao.Interfaces.IUserBookDAO;
import dbconnection.DBPool;
import entities.UserBook;
import exceptions.DAOException;
import exceptions.Enums.DAOExceptionOperation;
import jdbc.util.ResultSetUserBookBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBookDAO implements IUserBookDAO {

    private final String CREATE_USERBOOK = "insert into userbook(user_id,book_id,count) values (?,?,?)";

    private final String UPDATE_USERBOOK = "update userbook set count = ? where (user_id=? and book_id=?)";

    private final String REMOVE_USERBOOK = "delete from userbook where (user_id=? and book_id=?)";
    
    private final String GET_USERBOOK = "select ub.id as user_book_id, ub.count as ubCount from userbook ub where (user_id=? and book_id=?)";

    private final String GET_USERBOOKS_BY_USER_EMAIL="select ub.id as user_book_id,ub.book_id as ubBook_id, ub.user_id as ubUser_id, ub.count as ubCount,title,year,email,phone_number from userbook ub inner join book b on ub.book_id=b.id inner join users u on ub.user_id=u.id  where ub.user_id= (select id from users where email = ?)";

    private IBookDAO bookDAO = new BookDAO();

    public void create(UserBook userBook,Connection connection) throws DAOException {

        try (
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USERBOOK)){

            preparedStatement.setLong(1,userBook.getUserId());
            preparedStatement.setLong(2,userBook.getBookId());
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
            preparedStatement.setLong(2,userBook.getUserId());
            preparedStatement.setLong(3,userBook.getBookId());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e.getMessage(), DAOExceptionOperation.UPDATE);
        }

    }

    public void updateBookCount(long bookId,long userId,int newCount,Connection connection) throws DAOException {

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USERBOOK)){

            preparedStatement.setInt(1,newCount);
            preparedStatement.setLong(2,userId);
            preparedStatement.setLong(3,bookId);

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e.getMessage(), DAOExceptionOperation.UPDATE);
        }

    }

    public void removeBookFromUsersBook(long bookId,long userId,Connection connection) throws DAOException {

        try (
             PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_USERBOOK)){

            preparedStatement.setLong(1,userId);
            preparedStatement.setLong(2,bookId);

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

    @Override
    public List<UserBook> getUserBooksByUserEmail(String email) throws DAOException {

        List<UserBook> userBookList = new ArrayList<>();
        ResultSetUserBookBuilder userBookBuilder = new ResultSetUserBookBuilder();
        try (Connection connection = DBPool.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(GET_USERBOOKS_BY_USER_EMAIL)){

            preparedStatement.setString(1,email);

            ResultSet rs = preparedStatement.executeQuery();
            UserBook userBook;

            while (rs.next()) {
                userBook = userBookBuilder.BuildUserBookFromResultSet(rs);
                userBookList.add(userBook);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e.getMessage(), DAOExceptionOperation.SELECT);
        }

        return userBookList;
    }
}
