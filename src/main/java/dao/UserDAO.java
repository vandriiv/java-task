package dao;

import dao.Interfaces.IUserDAO;
import dbconnection.DBPool;
import entities.User;
import exceptions.DAOException;
import exceptions.Enums.DAOExceptionOperation;
import jdbc.util.ResultSetUserBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements IUserDAO {

    private final String  SELECT_USER_BY_EMAIL = "select u.id as user_id, email,role_id as uRole_id, " +
            "password, phone_number, r.id as role_id, r.name " +
            "as role_name from users u inner join role r on u.role_id = r.id where u.email = ?";

    private final String  SELECT_USER_BY_EMAIL_AND_PASSWORD = "select u.id as user_id, email,role_id as uRole_id, " +
            "password, phone_number, r.id as role_id, r.name " +
            "as role_name from users u inner join role r on u.role_id = r.id where (u.email = ? and u.password = ?)";

    private final String CREATE_USER = "INSERT INTO users(" +
            "email, password, phone_number, role_id)" +
            "VALUES (?, ?, ?, ?);";
    
    private final String UPDATE_USER = "UPDATE users SET  email=?, password=?, phone_number=?, role_id=?" +
            " WHERE id = ?";

    private final String REMOVE_USER = "delete from users where id = ?";


    public User findByEmail(String email) throws DAOException {

        User user = null;
        ResultSetUserBuilder userBuilder = new ResultSetUserBuilder();

        try (Connection connection = DBPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL)){

            preparedStatement.setString(1, email);

            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()){
                user = userBuilder.BuildUserFromResultSet(rs);
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e.getMessage(), DAOExceptionOperation.SELECT);
        }

        return  user;
    }

    public User findByEmailAndPassword(String email, String password) throws DAOException {

        User user = null;
        ResultSetUserBuilder userBuilder = new ResultSetUserBuilder();

        try (Connection connection = DBPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL_AND_PASSWORD)){

            preparedStatement.setString(1, email);
            preparedStatement.setString(2,password);

            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()){
                user = userBuilder.BuildUserFromResultSet(rs);
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e.getMessage(), DAOExceptionOperation.SELECT);
        }

        return  user;
    }

    public void create(User user) throws DAOException {

        try (Connection connection = DBPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USER)){

            preparedStatement.setString(1,user.getEmail());
            preparedStatement.setString(2,user.getPassword());
            preparedStatement.setString(3,user.getPhoneNumber());
            preparedStatement.setLong(4,user.getRoleId());


            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e.getMessage(), DAOExceptionOperation.INSERT);
        }
    }

    public void remove(User user) throws DAOException {

        try (Connection connection = DBPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_USER)){

            preparedStatement.setLong(1,user.getId());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e.getMessage(), DAOExceptionOperation.DELETE);
        }
    }

    public void update(User user) throws DAOException {

        try (Connection connection = DBPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER)){

            preparedStatement.setString(1,user.getEmail());
            preparedStatement.setString(2,user.getPassword());
            preparedStatement.setString(3,user.getPhoneNumber());
            preparedStatement.setLong(4,user.getRoleId());
            preparedStatement.setLong(5,user.getId());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e.getMessage(), DAOExceptionOperation.UPDATE);
        }
    }
}
