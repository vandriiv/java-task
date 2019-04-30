package Services;

import DAO.AuthorDAO;
import DAO.BookDAO;
import DAO.GenreDAO;
import DAO.Interfaces.IAuthorDAO;
import DAO.Interfaces.IBookDAO;
import DAO.Interfaces.IGenreDAO;
import Entities.Author;
import Entities.Book;
import Entities.Genre;
import Exceptions.DAOException;
import Exceptions.ServiceDBException;
import Services.Interfaces.IBookService;
import ViewModels.BooksListViewModel;

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

    public static IBookService getInstance() {
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

    public BooksListViewModel getBooksByAuthorId(long authorId, int limit, int offset) throws ServiceDBException {

        List<Book> books = null;
        try {
            books = bookDAO.findByAuthorId(authorId,limit+1,offset);
        } catch (DAOException ex) {
            throw new ServiceDBException(ex);
        }

        BooksListViewModel booksListViewModel = new BooksListViewModel();

        if(books.size()<=limit){
            booksListViewModel.setBooks(books);
            booksListViewModel.setHasMore(false);
        }
        else{
            booksListViewModel.setBooks(books.subList(0,books.size()-1));
            booksListViewModel.setHasMore(true);
        }
        return booksListViewModel;
    }

    public BooksListViewModel getBooksByGenreName(String genreName, int limit, int offset) throws ServiceDBException {

        List<Book> books = null;
        try {
            books = bookDAO.findByGenreName(genreName,limit+1,offset);
        } catch (DAOException ex) {
            throw new ServiceDBException(ex);
        }

        BooksListViewModel booksListViewModel = new BooksListViewModel();

        if(books.size()<=limit){
            booksListViewModel.setBooks(books);
            booksListViewModel.setHasMore(false);
        }
        else{
            booksListViewModel.setBooks(books.subList(0,books.size()-1));
            booksListViewModel.setHasMore(true);
        }
        return booksListViewModel;
    }

    public List<Book> getUsersBook(long userId) throws ServiceDBException {

        try {
            return bookDAO.findUsersBook(userId);
        } catch (DAOException ex) {
            throw new ServiceDBException(ex);
        }
    }

    public BooksListViewModel getBooksByTitle(String title, int limit,int offset) throws ServiceDBException {

        List<Book> books = null;
        try {
            books = bookDAO.findBookByTitle(title,limit+1,offset);
        } catch (DAOException ex) {
            throw new ServiceDBException(ex);
        }

        BooksListViewModel booksListViewModel = new BooksListViewModel();

        if(books.size()<=limit){
            booksListViewModel.setBooks(books);
            booksListViewModel.setHasMore(false);
        }
        else{
            booksListViewModel.setBooks(books.subList(0,books.size()-1));
            booksListViewModel.setHasMore(true);
        }
        return booksListViewModel;
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
    public BooksListViewModel getBooksRange(int limit, int offset) throws ServiceDBException {

        List<Book> books = null;
        try {
            books = bookDAO.getRange(limit+1,offset);
        } catch (DAOException ex) {
            throw new ServiceDBException(ex);
        }

        BooksListViewModel booksListViewModel = new BooksListViewModel();

        if(books.size()<=limit){
            booksListViewModel.setBooks(books);
            booksListViewModel.setHasMore(false);
        }
        else{
            booksListViewModel.setBooks(books.subList(0,books.size()-1));
            booksListViewModel.setHasMore(true);
        }
        return booksListViewModel;
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
