package Services.Interfaces;

import Entities.Author;
import Entities.Book;
import Entities.Genre;
import Exceptions.ServiceDBException;
import ViewModels.BooksListViewModel;

import java.util.HashMap;
import java.util.List;

public interface IBookService {

    List<Book> getAllBooks() throws ServiceDBException;

    BooksListViewModel getBooksByAuthorId(long authorId, int limit, int offset) throws ServiceDBException;

    BooksListViewModel getBooksByGenreName(String genreName,int limit,int offset) throws ServiceDBException;

    List<Book> getUsersBook(long userId) throws ServiceDBException;

    Book getBookById(long bookId) throws ServiceDBException;

    List<Genre> getAllGenres() throws ServiceDBException;

    List<Author> getAllAuthors() throws ServiceDBException;

    List<Author> getAuthorsByNamePart(String name) throws ServiceDBException;

    BooksListViewModel getBooksByTitle(String title, int limit,int offset) throws ServiceDBException;

    BooksListViewModel getBooksRange(int limit,int offset) throws ServiceDBException;

    HashMap<Long,Integer> getAvailableCount(List<Long> items) throws ServiceDBException;

    void addAuthor(Author author) throws ServiceDBException;

    void updateAuthor(Author author) throws ServiceDBException;

    void addBook(Book book) throws ServiceDBException;

    void updateBook(Book book) throws ServiceDBException;

}
