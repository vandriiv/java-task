package Services;

import DAO.Interfaces.IOrderManager;
import DAO.OrderManager;
import Entities.UserBook;
import Exceptions.DAOException;
import Exceptions.OrderedBookAvailabilityException;
import Exceptions.ServiceDBException;
import Services.Interfaces.IOrderService;

import java.util.List;

public class OrderService implements IOrderService {
    private IOrderManager orderManager;

    private static OrderService instance = null;

    private OrderService(){
       orderManager = new OrderManager();
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


}
