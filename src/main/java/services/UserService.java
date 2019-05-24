package services;

import dao.Interfaces.IUserDAO;
import dao.UserDAO;
import entities.User;
import exceptions.DAOException;
import exceptions.ServiceDBException;
import exceptions.UserIsAlreadyExistException;
import exceptions.UserNotFoundException;
import services.interfaces.IUserService;

public class UserService implements IUserService {

    private final IUserDAO userDAO;

    private static UserService instance = null;

    private UserService() {
       userDAO = new UserDAO();
    }

    public static IUserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public User Login(String email, String password) throws UserNotFoundException,
            ServiceDBException {

        User user;
        try {
            user = userDAO.findByEmail(email);
        } catch (DAOException ex) {
            throw new ServiceDBException(ex);
        }

        if(user==null){
            throw new UserNotFoundException("Email "+ email+ " is not used");
        }

        if(!user.getPassword().equals(password)){
            throw new UserNotFoundException("User not found");
        }

        return user;

    }

    public void Registration(String email, String password, String phoneNumber, long roleId)
            throws UserIsAlreadyExistException, ServiceDBException {
        try {
            boolean isExist = userDAO.findByEmail(email) != null;

            if (isExist) {
                throw new UserIsAlreadyExistException("Email " + email + " is already used");
            }

            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setPhoneNumber(phoneNumber);
            user.setRoleId(roleId);

            userDAO.create(user);
        }
        catch (DAOException ex){
            throw new ServiceDBException(ex);
        }

    }

    public User getByEmail(String email) throws ServiceDBException {
        User user;
        try {
            user = userDAO.findByEmail(email);
        } catch (DAOException ex) {
            throw new ServiceDBException(ex);
        }
        return user;
    }

}
