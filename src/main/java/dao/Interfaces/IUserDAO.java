package dao.Interfaces;

import entities.User;
import exceptions.DAOException;

public interface IUserDAO {

    void create(User user) throws DAOException;

    User findByEmail(String email) throws DAOException;

    User findByEmailAndPassword(String email, String password) throws DAOException;

    void remove(User user) throws DAOException;

    void update(User user) throws DAOException;
}
