package services;

import dao.Interfaces.IOrderManager;
import dao.Interfaces.IUserBookDAO;
import dao.OrderManager;
import dao.UserBookDAO;
import entities.UserBook;
import exceptions.DAOException;
import exceptions.OrderedBookAvailabilityException;
import exceptions.ServiceDBException;
import services.interfaces.IOrderService;

import java.util.List;

public class OrderService implements IOrderService {
    private IOrderManager orderManager;
    private IUserBookDAO userBookDAO;

    private static OrderService instance = null;

    private OrderService(){
       orderManager = new OrderManager();
       userBookDAO = new UserBookDAO();
    }

    synchronized public static IOrderService getInstance() {
        if (instance == null) {
            instance = new OrderService();
        }
        return instance;
    }

    @Override
    public void makeOrder(List<UserBook> ordersList) throws OrderedBookAvailabilityException,
            ServiceDBException {

        try {
            orderManager.proceedOrderTransaction(ordersList);
        }
        catch (OrderedBookAvailabilityException ex){
            throw ex;
        }
        catch (DAOException ex){
            throw new ServiceDBException(ex);
        }
    }

    @Override
    public void updateUserBookCount(long userId,long bookId,int newCount, int oldCount) throws ServiceDBException {
        try {
            orderManager.updateUserBookCount(userId,bookId,newCount,oldCount);
        }
        catch (DAOException ex){
            throw new ServiceDBException(ex);
        }
    }

    @Override
    public List<UserBook> getUserOrderedBooks(String email) throws ServiceDBException {
        try {
            return userBookDAO.getUserBooksByUserEmail(email);
        }
        catch (DAOException ex){
            throw new ServiceDBException(ex);
        }
    }


}
