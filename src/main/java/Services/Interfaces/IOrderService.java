package Services.Interfaces;

import Entities.UserBook;
import Exceptions.OrderedBookAvailabilityException;
import Exceptions.ServiceDBException;

import java.util.List;

public interface IOrderService {
    void makeOrder(List<UserBook> ordersList) throws OrderedBookAvailabilityException, ServiceDBException;
}
