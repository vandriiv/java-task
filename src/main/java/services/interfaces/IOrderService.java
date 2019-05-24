package services.interfaces;

import entities.UserBook;
import exceptions.OrderedBookAvailabilityException;
import exceptions.ServiceDBException;

import java.util.List;

public interface IOrderService {
    void makeOrder(List<UserBook> ordersList) throws OrderedBookAvailabilityException, ServiceDBException;

    void updateUserBookCount(long userId,long bookId,int newCount, int oldCount) throws ServiceDBException;

    List<UserBook> getUserOrderedBooks(String email) throws ServiceDBException;
}
