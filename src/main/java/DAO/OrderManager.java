package DAO;

import DAO.Interfaces.IBookDAO;
import DAO.Interfaces.IOrderManager;
import DAO.Interfaces.IUserBookDAO;
import DBconnection.DBPool;
import Entities.Book;
import Entities.UserBook;
import Exceptions.DAOException;
import Exceptions.Enums.DAOExceptionOperation;
import Exceptions.OrderedBookAvailabilityException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderManager implements IOrderManager {

    private IUserBookDAO userBookDAO;

    private IBookDAO bookDAO;

    public OrderManager() {
        userBookDAO = new UserBookDAO();
        bookDAO = new BookDAO();
    }

    @Override
    public void proceedOrderTransaction(List<UserBook> ordersList) throws OrderedBookAvailabilityException, DAOException {
        Connection connection=null;
        try {
            connection = DBPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            for (UserBook data : ordersList) {

                UserBook record = userBookDAO.getUserBook(data.getUserId(),data.getBookId(),connection);
                int count = data.getCount();
                if(record==null){
                    userBookDAO.create(data, connection);
                }else{
                    data.setCount(data.getCount()+record.getCount());
                    userBookDAO.updateBookCount(data,connection);
                }

                bookDAO.reduceAvailableCount(data.getBookId(), count, connection);
                Book book = bookDAO.findById(data.getBookId(), connection);

                if (book.getAvailableCount() < 0) {
                    connection.rollback();
                    connection.close();
                    throw new OrderedBookAvailabilityException("Book is not available");
                }
            }
            connection.commit();

        } catch (SQLException e) {

            if (connection != null) {

                try {
                    connection.rollback();
                } catch (Exception ex) {
                    throw new DAOException(ex.getMessage(), DAOExceptionOperation.COMPLEX_TRANSACTION);
                }
            }
            e.printStackTrace();
            throw new DAOException(e.getMessage(), DAOExceptionOperation.COMPLEX_TRANSACTION);

        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    throw new DAOException(e.getMessage(), DAOExceptionOperation.COMPLEX_TRANSACTION);
                }
            }
        }
    }

    @Override
    public void updateUserBookCount(long userId,long bookId,int newCount,int oldCount) throws DAOException {
        Connection connection=null;
        try {
            connection = DBPool.getInstance().getConnection();
            connection.setAutoCommit(false);

            if(newCount==0){
                userBookDAO.removeBookFromUsersBook(bookId,userId,connection);
                bookDAO.increaseAvailableCount(bookId,oldCount,connection);
            }
            else{
                userBookDAO.updateBookCount(bookId,userId,newCount,connection);
                bookDAO.increaseAvailableCount(bookId,oldCount-newCount,connection);
            }

            connection.commit();
        } catch (SQLException e) {

            if (connection != null) {

                try {
                    connection.rollback();
                } catch (Exception ex) {
                    throw new DAOException(ex.getMessage(), DAOExceptionOperation.COMPLEX_TRANSACTION);
                }
            }
            e.printStackTrace();
            throw new DAOException(e.getMessage(), DAOExceptionOperation.COMPLEX_TRANSACTION);

        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    throw new DAOException(e.getMessage(), DAOExceptionOperation.COMPLEX_TRANSACTION);
                }
            }
        }
    }
}
