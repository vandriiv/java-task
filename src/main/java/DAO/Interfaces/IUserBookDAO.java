package DAO.Interfaces;


import Entities.UserBook;
import Exceptions.DAOException;

import java.sql.Connection;

public interface IUserBookDAO {

    void create(UserBook userBook, Connection connection) throws DAOException;

    void updateBookCount(UserBook userBook, Connection connection) throws DAOException;

    void removeBookFromUsersBook(UserBook userBook) throws DAOException;

    UserBook getUserBook(long userId, long bookId, Connection connection) throws DAOException;
}
