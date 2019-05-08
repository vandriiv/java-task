package DAO;

import DAO.Interfaces.IBookDAO;
import Exceptions.DAOException;
import Exceptions.Enums.DAOExceptionOperation;
import JDBCUtil.ResultSetBookBuilder;
import DBconnection.DBPool;
import Entities.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookDAO implements IBookDAO {

    private final String REDUCE_AVAILABLE_COUNT = "update book set available_count = (select available_count where id=?)-? where id=?";

    private final String INCREASE_AVAILABLE_COUNT = "update book set available_count = (select available_count where id=?)+? where id=?";

    private final String SELECT_BOOK_BY_ID="select b.id,title,year,author_id,total_count,available_count,genre_id, " +
            "a.id as authorId,first_name, last_name, g.id as genreId, g.name as genreName " +
            "from book b inner join author a on b.author_id=a.id inner join genre g " +
            "on b.genre_id=g.id where b.id = ?";

    private final String SELECT_ALL_BOOKS ="select b.id,title,year,author_id,total_count,available_count,genre_id, " +
            "a.id as authorId,first_name, last_name, g.id as genreId, g.name as genreName " +
            "from book b inner join author a on b.author_id=a.id inner join genre g " +
            "on b.genre_id=g.id ";

    private final String SELECT_BOOK_BY_AUTHOR_ID = "select b.id,title,year,author_id,total_count,available_count,genre_id, " +
            "a.id as authorId,first_name, last_name, g.id as genreId, g.name as genreName " +
            "from book b inner join author a on b.author_id=a.id inner join genre g " +
            "on b.genre_id=g.id where author_id = ? limit ? offset ?";

    private final String SELECT_BOOK_BY_GENRE_ID = "select b.id,title,year,author_id,total_count,available_count,genre_id, " +
            "a.id as authorId,first_name, last_name, g.id as genreId, g.name as genreName " +
            "from book b inner join author a on b.author_id=a.id inner join genre g " +
            "on b.genre_id=g.id where genre_id = ?";

    private final String SELECT_BOOK_BY_TITLE ="select b.id,title,year,author_id,total_count,available_count,genre_id, " +
            "a.id as authorId,first_name, last_name, g.id as genreId, g.name as genreName " +
            "from book b inner join author a on b.author_id=a.id inner join genre g " +
            "on b.genre_id=g.id where title LIKE ? limit ? offset ?";

    private final String CREATE_BOOK = "insert into book (title,year,author_id,total_count,available_count,genre_id)" +
            "values (?,?,?,?,?,?)";

    private final String UPDATE_BOOK = "update book set title = ?, year = ?, author_id = ?, total_count =?," +
            "available_count = ?, genre_id = ? where id = ?";

    private final String SELECT_BOOK_BY_USER_ID = "select b.id,title,year,author_id,total_count,available_count,genre_id, " +
            "a.id as authorId,first_name, last_name, g.id as genreId, g.name as genreName " +
            "from book b inner join author a on b.author_id=a.id inner join genre g " +
            "on b.genre_id=g.id where b.id = (select ub.book_id from userbook ub where ub.user_id = ?)";

    private final String SELECT_BOOK_BY_GENRE_NAME = "select b.id,title,year,author_id,total_count,available_count,genre_id, " +
            "a.id as authorId,first_name, last_name, g.id as genreId, g.name as genreName " +
            "from book b inner join author a on b.author_id=a.id inner join genre g " +
            "on b.genre_id=g.id where g.name = ? limit ? offset ?";

    private final String SELECT_RANGE ="select b.id,title,year,author_id,total_count,available_count,genre_id, " +
            "a.id as authorId,first_name, last_name, g.id as genreId, g.name as genreName " +
            "from book b inner join author a on b.author_id=a.id inner join genre g " +
            "on b.genre_id=g.id limit ? offset ?";

    private final String GET_AVAILABLE_COUNT = "select b.id,available_count from book b where b.id in ";

    public Book findById(long id) throws DAOException {

        Book book = null;
        ResultSetBookBuilder bookBuilder = new ResultSetBookBuilder();

        try (
            Connection connection = DBPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOK_BY_ID)){
            preparedStatement.setLong(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()) {
                book = bookBuilder.BuildBookFromResultSet(rs);
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e.getMessage(), DAOExceptionOperation.SELECT);
        }

        return book;
    }

    public List<Book> findByAuthorId(long auhtorId,int limit, int offset) throws DAOException {

        List<Book> books = new ArrayList<>();
        ResultSetBookBuilder bookBuilder = new ResultSetBookBuilder();

        try (Connection connection = DBPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOK_BY_AUTHOR_ID)){

            preparedStatement.setLong(1,  auhtorId);
            preparedStatement.setInt(2,limit);
            preparedStatement.setInt(3,offset);

            ResultSet rs = preparedStatement.executeQuery();

            Book book;

            while (rs.next()) {
                book = bookBuilder.BuildBookFromResultSet(rs);
                books.add(book);
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e.getMessage(), DAOExceptionOperation.SELECT);
        }

        return books;
    }

    public List<Book> findByGenreId(long genreId) throws DAOException {

        List<Book> books = new ArrayList<>();
        ResultSetBookBuilder bookBuilder = new ResultSetBookBuilder();

        try (Connection connection = DBPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOK_BY_GENRE_ID)){

            preparedStatement.setLong(1, genreId);

            ResultSet rs = preparedStatement.executeQuery();

            Book book = null;

            while (rs.next()) {
                book = bookBuilder.BuildBookFromResultSet(rs);
                books.add(book);
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e.getMessage(), DAOExceptionOperation.SELECT);
        }

        return books;
    }

    public List<Book> findUsersBook(long userId) throws DAOException {

        List<Book> books = new ArrayList<>();
        ResultSetBookBuilder bookBuilder = new ResultSetBookBuilder();

        try (Connection connection = DBPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOK_BY_USER_ID)){

            preparedStatement.setLong(1, userId);

            ResultSet rs = preparedStatement.executeQuery();

            Book book;

            while (rs.next()) {
                book = bookBuilder.BuildBookFromResultSet(rs);
                books.add(book);
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e.getMessage(), DAOExceptionOperation.SELECT);
        }

        return books;
    }


    public List<Book> findBookByTitle(String title,int limit, int offset) throws DAOException {

        List<Book> books = new ArrayList<>();
        ResultSetBookBuilder bookBuilder = new ResultSetBookBuilder();

        try (Connection connection = DBPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOK_BY_TITLE)){

            preparedStatement.setString(1, "%"+title+"%");
            preparedStatement.setInt(2,limit);
            preparedStatement.setInt(3,offset);

            ResultSet rs = preparedStatement.executeQuery();

            Book book;

            while (rs.next()) {
                book = bookBuilder.BuildBookFromResultSet(rs);
                books.add(book);
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e.getMessage(), DAOExceptionOperation.SELECT);
        }

        return books;
    }

    public void create(Book book) throws DAOException {

        try (Connection connection = DBPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_BOOK)){

            preparedStatement.setString(1,book.getTitle());
            preparedStatement.setInt(2,book.getYear());
            preparedStatement.setLong(3,book.getAuthorId());
            preparedStatement.setInt(4,book.getTotalCount());
            preparedStatement.setInt(5,book.getAvailableCount());
            preparedStatement.setLong(6,book.getGenreId());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e.getMessage(), DAOExceptionOperation.INSERT);
        }
    }

    public void update(Book book) throws DAOException {

        try (Connection connection = DBPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BOOK)){

            preparedStatement.setString(1,book.getTitle());
            preparedStatement.setInt(2,book.getYear());
            preparedStatement.setLong(3,book.getAuthorId());
            preparedStatement.setInt(4,book.getTotalCount());
            preparedStatement.setInt(5,book.getAvailableCount());
            preparedStatement.setLong(6,book.getGenreId());
            preparedStatement.setLong(7,book.getId());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e.getMessage(), DAOExceptionOperation.UPDATE);
        }

    }

    public void reduceAvailableCount(long bookId,int count, Connection connection){

        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(REDUCE_AVAILABLE_COUNT)){

            preparedStatement.setLong(1,bookId);
            preparedStatement.setInt(2,count);
            preparedStatement.setLong(3,bookId);
            preparedStatement.execute();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void increaseAvailableCount(long bookId,int count, Connection connection){

        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(INCREASE_AVAILABLE_COUNT)){
            preparedStatement.setLong(1,bookId);
            preparedStatement.setInt(2,count);
            preparedStatement.setLong(3,bookId);
            preparedStatement.execute();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

    public Book findById(long id, Connection connection) throws DAOException {

        Book book = null;
        ResultSetBookBuilder bookBuilder = new ResultSetBookBuilder();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOK_BY_ID)){
            preparedStatement.setLong(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()) {
                book = bookBuilder.BuildBookFromResultSet(rs);
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e.getMessage(), DAOExceptionOperation.SELECT);
        }

        return book;
    }

    public List<Book> getAll() throws DAOException {

        List<Book> books = new ArrayList<>();
        ResultSetBookBuilder bookBuilder = new ResultSetBookBuilder();

        try (Connection connection = DBPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BOOKS)){

            ResultSet rs = preparedStatement.executeQuery();

            Book book;

            while (rs.next()) {
                book = bookBuilder.BuildBookFromResultSet(rs);
                books.add(book);
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e.getMessage(), DAOExceptionOperation.SELECT);
        }

        return books;
    }

    public List<Book> findByGenreName(String genreName,int limit, int offset) throws DAOException {

        List<Book> books = new ArrayList<Book>();
        ResultSetBookBuilder bookBuilder = new ResultSetBookBuilder();

        try (Connection connection = DBPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOK_BY_GENRE_NAME)){

            preparedStatement.setString(1, genreName);
            preparedStatement.setInt(2,limit);
            preparedStatement.setInt(3,offset);

            ResultSet rs = preparedStatement.executeQuery();

            Book book;

            while (rs.next()) {
                book = bookBuilder.BuildBookFromResultSet(rs);
                books.add(book);
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e.getMessage(), DAOExceptionOperation.SELECT);
        }

        return books;
    }

    public List<Book> getRange(int limit, int offset) throws DAOException {
        List<Book> books = new ArrayList<>();
        ResultSetBookBuilder bookBuilder = new ResultSetBookBuilder();

        try (Connection connection = DBPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_RANGE)){

            preparedStatement.setInt(1,limit);
            preparedStatement.setInt(2,offset);
            try (ResultSet rs = preparedStatement.executeQuery()) {

                Book book = null;

                while (rs.next()) {
                    book = bookBuilder.BuildBookFromResultSet(rs);
                    books.add(book);
                }
                rs.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e.getMessage(), DAOExceptionOperation.SELECT);
        }

        return books;
    }

    @Override
    public HashMap<Long, Integer> getAvailableCount(List<Long> items) throws DAOException {

        HashMap<Long, Integer> result = new HashMap<>(items.size());

        StringBuilder idList = new StringBuilder();
        idList.append("(");
        for (long id : items) {
            if (idList.length() > 1) {
                idList.append(",");
            }
            idList.append("?");
        }
        idList.append(")");

        try (
                Connection connection = DBPool.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(GET_AVAILABLE_COUNT+idList.toString())){

            for(int i=0;i<items.size();i++){
                preparedStatement.setLong(i+1,items.get(i));
            }

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                result.put(rs.getLong(1),rs.getInt(2));
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e.getMessage(), DAOExceptionOperation.SELECT);
        }

        return  result;
    }
}
