package dao.Interfaces;

import entities.UserBook;
import exceptions.DAOException;
import exceptions.OrderedBookAvailabilityException;

import java.util.List;

public interface IOrderManager {
   void proceedOrderTransaction(List<UserBook> ordersList) throws OrderedBookAvailabilityException, DAOException;



   void updateUserBookCount(long userId,long bookId,int newCount, int oldCount) throws DAOException;
}
