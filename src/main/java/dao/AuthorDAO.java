package dao;

import dao.Interfaces.IAuthorDAO;
import exceptions.DAOException;
import exceptions.Enums.DAOExceptionOperation;
import jdbc.util.ResultSetAuthorBuilder;
import dbconnection.DBPool;
import entities.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO implements IAuthorDAO {

    private final String SELECT_ALL_AUTHORS = "select a.id as authorId,first_name, last_name " +
            "from author a";

    private final String SELECT_AUTHOR_BY_ID = "select a.id as authorId,first_name, last_name " +
            "from author a where a.id = ?";

    private final String SELECT_AUTHOR_BY_LASTNAME = "select a.id as authorId,first_name, last_name" +
            " from author a where a.last_name = ?";

    private final String SELECT_AUTHOR_BY_FIRSTNAME = "select a.id as authorId,first_name, last_name" +
            " from author a where a.first_name = ?";

    private final String SELECT_AUTHOR_BY_NAMEPART = "select a.id as authorId,first_name, last_name" +
            " from author a where (a.first_name = ? or a.last_name = ?)";

    private final String CREATE_AUTHOR = "insert into author(first_name,last_name) values (?,?)";

    private final String UPDATE_AUTHOR = "update author set first_name=?, last_name=? where id =?";

    public List<Author> findByFirstName(String firstName) throws DAOException {

        List<Author> authors = new ArrayList<Author>();
        ResultSetAuthorBuilder authorBuilder = new ResultSetAuthorBuilder();

        try (Connection connection = DBPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AUTHOR_BY_FIRSTNAME)){

            preparedStatement.setString(1, firstName);

            ResultSet rs = preparedStatement.executeQuery();

            Author author = null;

            while (rs.next()) {
                author = authorBuilder.BuildAuthorFromResultSet(rs);
                authors.add(author);
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e.getMessage(), DAOExceptionOperation.SELECT);
        }

        return authors;
    }

    public List<Author> findByLastName(String lastName) throws DAOException {
        List<Author> authors = new ArrayList<Author>();
        ResultSetAuthorBuilder authorBuilder = new ResultSetAuthorBuilder();

        try (Connection connection = DBPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AUTHOR_BY_LASTNAME)){

            preparedStatement.setString(1, lastName);

            ResultSet rs = preparedStatement.executeQuery();

            Author author = null;

            while (rs.next()) {
                author = authorBuilder.BuildAuthorFromResultSet(rs);
                authors.add(author);
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e.getMessage(), DAOExceptionOperation.SELECT);
        }

        return authors;
    }

    public List<Author> findByNamePart(String namePart) throws DAOException {

        List<Author> authors = new ArrayList<Author>();
        ResultSetAuthorBuilder authorBuilder = new ResultSetAuthorBuilder();

        try (Connection connection = DBPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AUTHOR_BY_NAMEPART)){

            preparedStatement.setString(1, namePart);
            preparedStatement.setString(2,namePart);

            ResultSet rs = preparedStatement.executeQuery();

            Author author = null;

            while (rs.next()) {
                author = authorBuilder.BuildAuthorFromResultSet(rs);
                authors.add(author);
            }

            rs.close();
            

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e.getMessage(), DAOExceptionOperation.SELECT);
        }

        return authors;

    }

    public Author findById(long id) throws DAOException {

        Author author = null;
        ResultSetAuthorBuilder authorBuilder = new ResultSetAuthorBuilder();

        try (Connection connection = DBPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AUTHOR_BY_ID)){

            preparedStatement.setLong(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()) {
                author = authorBuilder.BuildAuthorFromResultSet(rs);
            }
            rs.close();


        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e.getMessage(), DAOExceptionOperation.SELECT);
        }

        return author;
    }

    public void create(Author author) throws DAOException {

        try (Connection connection = DBPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_AUTHOR)){

            preparedStatement.setString(1,author.getFirstName());
            preparedStatement.setString(2,author.getLastName());
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e.getMessage(), DAOExceptionOperation.INSERT);
        }
    }

    public void update(Author author) throws DAOException {

        try (Connection connection = DBPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_AUTHOR)){

            preparedStatement.setString(1,author.getFirstName());
            preparedStatement.setString(2,author.getLastName());
            preparedStatement.setLong(3,author.getId());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e.getMessage(), DAOExceptionOperation.UPDATE);
        }
    }

    public List<Author> getAll() throws DAOException {

        List<Author> authors = new ArrayList<Author>();
        ResultSetAuthorBuilder authorBuilder = new ResultSetAuthorBuilder();

        try (Connection connection = DBPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_AUTHORS)){

            ResultSet rs = preparedStatement.executeQuery();

            Author author = null;

            while (rs.next()) {
                author = authorBuilder.BuildAuthorFromResultSet(rs);
                authors.add(author);
            }

            rs.close();
            

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e.getMessage(), DAOExceptionOperation.SELECT);
        }

        return authors;
    }
}
