package DAO.Interfaces;


import Entities.UserBook;
import Exceptions.DAOException;

import java.sql.Connection;
import java.util.List;

public interface IUserBookDAO {

    void create(UserBook userBook, Connection connection) throws DAOException;

    void updateBookCount(UserBook userBook, Connection connection) throws DAOException;

    void removeBookFromUsersBook(UserBook userBook) throws DAOException;

    UserBook getUserBook(long userId, long bookId, Connection connection) throws DAOException;

    List<UserBook> getUserBooksByUserEmail(String email) throws DAOException;
}
