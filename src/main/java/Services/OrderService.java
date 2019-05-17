package Services;

import DAO.Interfaces.IOrderManager;
import DAO.Interfaces.IUserBookDAO;
import DAO.OrderManager;
import DAO.UserBookDAO;
import Entities.UserBook;
import Exceptions.DAOException;
import Exceptions.OrderedBookAvailabilityException;
import Exceptions.ServiceDBException;
import Services.Interfaces.IOrderService;

import java.util.List;

public class OrderService implements IOrderService {
    private IOrderManager orderManager;
    private IUserBookDAO userBookDAO;

    private static OrderService instance = null;

    private OrderService(){
       orderManager = new OrderManager();
       userBookDAO = new UserBookDAO();
    }

    public static IOrderService getInstance() {
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
