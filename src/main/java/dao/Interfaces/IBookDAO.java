package dao.Interfaces;

import entities.Book;
import exceptions.DAOException;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

public interface IBookDAO {

    Book findById(long id) throws DAOException;

    List<Book> findByAuthorId(long authorId,int limit, int offset) throws DAOException;

    List<Book> findUserBooks(long userId) throws DAOException;

    List<Book> findBookByTitle(String title,int limit, int offset)  throws DAOException;

    List<Book> findByGenreId(long genreId) throws DAOException;

    Book findById(long id, Connection connection) throws DAOException;

    void reduceAvailableCount(long bookId,int count, Connection connection);

    void create(Book book) throws DAOException;

    void update(Book book) throws DAOException;

    List<Book> getAll() throws DAOException;

    List<Book> findByGenreName(String genreName,int limit, int offset) throws DAOException;

    List<Book> getRange(int limit, int offset) throws DAOException;

    HashMap<Long, Integer> getAvailableCount(List<Long> items) throws DAOException;

    void increaseAvailableCount(long bookId,int count, Connection connection);

}
