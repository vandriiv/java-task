package DAO.Interfaces;


import Entities.UserBook;
import Exceptions.DAOException;

import java.sql.Connection;
import java.util.List;

public interface IUserBookDAO {

    void create(UserBook userBook, Connection connection) throws DAOException;

    void updateBookCount(long userId,long bookId,int newCount,Connection connection) throws DAOException;

    void updateBookCount(UserBook userBook,Connection connection) throws DAOException;

    void removeBookFromUsersBook(long bookId,long userId,Connection connection) throws DAOException;

    UserBook getUserBook(long bookId, long userId, Connection connection) throws DAOException;

    List<UserBook> getUserBooksByUserEmail(String email) throws DAOException;
}
