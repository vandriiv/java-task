package services;

import dao.AuthorDAO;
import dao.BookDAO;
import dao.GenreDAO;
import dao.Interfaces.IAuthorDAO;
import dao.Interfaces.IBookDAO;
import dao.Interfaces.IGenreDAO;
import entities.Author;
import entities.Book;
import entities.Genre;
import exceptions.DAOException;
import exceptions.ServiceDBException;
import services.interfaces.IBookService;

import java.util.HashMap;
import java.util.List;

public class BookService implements IBookService {

    private final IBookDAO bookDAO;

    private final IAuthorDAO authorDAO;

    private final IGenreDAO genreDAO;

    private static BookService instance=null;

    private BookService(){
        bookDAO = new BookDAO();
        authorDAO = new AuthorDAO();
        genreDAO= new GenreDAO();
    }

    synchronized public static IBookService getInstance() {
        if (instance == null) {
            instance = new BookService();
        }
        return instance;
    }

    public List<Book> getAllBooks() throws ServiceDBException {

        try {
            return bookDAO.getAll();
        } catch (DAOException ex) {
           throw new ServiceDBException(ex);
        }
    }

    public List<Book> getBooksByAuthorId(long authorId, int limit, int offset) throws ServiceDBException {

        List<Book> books = null;
        try {
            books = bookDAO.findByAuthorId(authorId,limit,offset);
            return books;
        } catch (DAOException ex) {
            throw new ServiceDBException(ex);
        }
    }

    public List<Book> getBooksByGenreName(String genreName, int limit, int offset) throws ServiceDBException {

        List<Book> books = null;
        try {
            books = bookDAO.findByGenreName(genreName,limit,offset);
            return  books;
        } catch (DAOException ex) {
            throw new ServiceDBException(ex);
        }
    }


    public List<Book> getUsersBook(long userId) throws ServiceDBException {

        try {
            return bookDAO.findUserBooks(userId);
        } catch (DAOException ex) {
            throw new ServiceDBException(ex);
        }
    }

    public List<Book> getBooksByTitle(String title, int limit, int offset) throws ServiceDBException {

        List<Book> books = null;
        try {
            books = bookDAO.findBookByTitle(title,limit,offset);
            return books;
        } catch (DAOException ex) {
            throw new ServiceDBException(ex);
        }

    }

    public List<Genre> getAllGenres() throws ServiceDBException {

        try {
            return genreDAO.getAll();
        } catch (DAOException ex) {
            throw new ServiceDBException(ex);
        }
    }

    public List<Author> getAllAuthors() throws ServiceDBException {

        try {
            return authorDAO.getAll();
        } catch (DAOException ex) {
            throw new ServiceDBException(ex);
        }
    }

    public List<Author> getAuthorsByNamePart(String name) throws ServiceDBException {

        try {
            return authorDAO.findByNamePart(name);
        } catch (DAOException ex) {
            throw new ServiceDBException(ex);
        }
    }

    public Book getBookById(long bookId) throws ServiceDBException {

        try {
            return bookDAO.findById(bookId);
        } catch (DAOException ex) {
            throw new ServiceDBException(ex);
        }
    }

    @Override
    public List<Book> getBooksRange(int limit, int offset) throws ServiceDBException {

        List<Book> books = null;
        try {
            books = bookDAO.getRange(limit,offset);
            return  books;
        } catch (DAOException ex) {
            throw new ServiceDBException(ex);
        }       
    }

    @Override
    public HashMap<Long, Integer> getAvailableCount(List<Long> items) throws ServiceDBException {
        try {
            return bookDAO.getAvailableCount(items);
        } catch (DAOException ex) {
            throw new ServiceDBException(ex);
        }
    }

    @Override
    public void addAuthor(Author author) throws ServiceDBException {
        try {
            authorDAO.create(author);
        }
        catch (DAOException ex){
            throw new ServiceDBException(ex);
        }
    }

    @Override
    public void updateAuthor(Author author) throws ServiceDBException {
        try{
            authorDAO.update(author);
        }
        catch (DAOException ex){
            throw new ServiceDBException(ex);
        }
    }

    @Override
    public void addBook(Book book) throws ServiceDBException {
        try {
            bookDAO.create(book);
        }
        catch (DAOException ex){
            throw new ServiceDBException(ex);
        }
    }

    @Override
    public void updateBook(Book book) throws ServiceDBException {
        try {
            bookDAO.update(book);
        }
        catch (DAOException ex){
            throw new ServiceDBException(ex);
        }
    }
}
