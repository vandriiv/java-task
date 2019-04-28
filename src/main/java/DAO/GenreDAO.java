package DAO;

import DAO.Interfaces.IGenreDAO;
import Exceptions.DAOException;
import Exceptions.Enums.DAOExceptionOperation;
import JDBCUtil.ResultSetGenreBuilder;
import DBconnection.DBPool;
import Entities.Genre;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO implements IGenreDAO {

    private final String SELECT_ALL_GENRES = "select g.id as genreId, g.name as genreName from genre g";

    private final String SELECT_GENRE_BY_ID = "select g.id as genreId, g.name as genreName " +
            "from genre g where g.id = ?";

    private final String CREATE_GENRE = "insert into genre(name) values(?)";

    private final String UPDATE_GENRE = "update genre set name =? where id =?";

    public List<Genre> getAll() throws DAOException {
        
        List<Genre> genres = new ArrayList<Genre>();
        ResultSetGenreBuilder genreBuilder = new ResultSetGenreBuilder();

        try (Connection connection = DBPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_GENRES)){

            ResultSet rs = preparedStatement.executeQuery();

            Genre genre = null;

            while (rs.next()) {
                genre = genreBuilder.BuildGenreFromResultSet(rs);
                genres.add(genre);
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e.getMessage(), DAOExceptionOperation.SELECT);
        }

        return genres;
    }

    public Genre findById(long id) throws DAOException {

        Genre genre = null;
        ResultSetGenreBuilder genreBuilder = new ResultSetGenreBuilder();

        try (Connection connection = DBPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_GENRE_BY_ID)){

            preparedStatement.setLong(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()) {
                genre = genreBuilder.BuildGenreFromResultSet(rs);
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e.getMessage(), DAOExceptionOperation.SELECT);
        }

        return genre;
    }

    public void update(Genre genre) throws DAOException {

        try (Connection connection = DBPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_GENRE)){

            preparedStatement.setString(1,genre.getName());
            preparedStatement.setLong(2,genre.getId());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e.getMessage(), DAOExceptionOperation.UPDATE);
        }
    }

    public void create(Genre genre) throws DAOException {

        try (Connection connection = DBPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_GENRE)){

            preparedStatement.setString(1,genre.getName());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e.getMessage(), DAOExceptionOperation.INSERT);
        }
    }
}
