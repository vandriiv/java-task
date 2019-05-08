package DAO.Interfaces;

import Entities.UserBook;
import Exceptions.DAOException;
import Exceptions.OrderedBookAvailabilityException;

import java.util.List;

public interface IOrderManager {
   void proceedOrderTransaction(List<UserBook> ordersList) throws OrderedBookAvailabilityException, DAOException;



   void updateUserBookCount(long userId,long bookId,int newCount, int oldCount) throws DAOException;
}
