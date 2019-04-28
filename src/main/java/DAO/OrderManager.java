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

                UserBook record = userBookDAO.getUserBook(data.getUser().getId(),data.getBook().getId(),connection);
                int count = data.getCount();
                if(record==null){
                    userBookDAO.create(data, connection);
                }else{
                    data.setCount(data.getCount()+record.getCount());
                    userBookDAO.updateBookCount(data,connection);
                }

                bookDAO.reduceAvailableCount(data.getBook().getId(), count, connection);
                Book book = bookDAO.findById(data.getBook().getId(), connection);

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
                }
            }
            e.printStackTrace();
            throw new DAOException(e.getMessage(), DAOExceptionOperation.SELECT);

        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                }
            }
        }
    }
}