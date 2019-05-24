package services.interfaces;

import entities.Author;
import entities.Book;
import entities.Genre;
import exceptions.ServiceDBException;
import java.util.HashMap;
import java.util.List;

public interface IBookService {

    List<Book> getAllBooks() throws ServiceDBException;

    List<Book> getBooksByAuthorId(long authorId, int limit, int offset) throws ServiceDBException;

    List<Book> getBooksByGenreName(String genreName,int limit,int offset) throws ServiceDBException;

    Book getBookById(long bookId) throws ServiceDBException;

    List<Genre> getAllGenres() throws ServiceDBException;

    List<Author> getAllAuthors() throws ServiceDBException;

    List<Author> getAuthorsByNamePart(String name) throws ServiceDBException;

    List<Book> getBooksByTitle(String title, int limit,int offset) throws ServiceDBException;

    List<Book> getBooksRange(int limit,int offset) throws ServiceDBException;

    HashMap<Long,Integer> getAvailableCount(List<Long> items) throws ServiceDBException;

    void addAuthor(Author author) throws ServiceDBException;

    void updateAuthor(Author author) throws ServiceDBException;

    void addBook(Book book) throws ServiceDBException;

    void updateBook(Book book) throws ServiceDBException;

}
