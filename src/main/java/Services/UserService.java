package Services;

import DAO.Interfaces.IUserDAO;
import DAO.UserDAO;
import Entities.Role;
import Entities.User;
import Exceptions.DAOException;
import Exceptions.ServiceDBException;
import Exceptions.UserIsAlreadyExistException;
import Exceptions.UserNotFoundException;

import Services.Interfaces.IUserService;

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


        User user = null;
        try {
            user = userDAO.findByEmail(email);
        } catch (DAOException ex) {
            throw new ServiceDBException(ex);
        }

        if(user==null){
            throw new UserNotFoundException("Email "+ email+ " is not used!");
        }

        /*PasswordAuthentication passwordAuthentication = new PasswordAuthentication();

        if(!passwordAuthentication.authenticate(user.getPassword(),password)){
            throw new UserNotFoundException("User not found!");
        }*/
        if(!user.getPassword().equals(password)){
            throw new UserNotFoundException("User not found!");
        }

        return user;

    }

    public void Registration(String email, String password, String phoneNumber, long roleId)
            throws UserIsAlreadyExistException, ServiceDBException {
        try {
            boolean isExist = userDAO.findByEmail(email) != null;

            if (isExist) {
                throw new UserIsAlreadyExistException("Email " + email + " is already used!");
            }

            //PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
            User user = new User();
            Role role = new Role();

            role.setId(roleId);
            user.setEmail(email);
            //user.setPassword(passwordAuthentication.hash(password));
            user.setPassword(password);
            user.setPhoneNumber(phoneNumber);
            user.setRole(role);

            userDAO.create(user);
        }
        catch (DAOException ex){
            throw new ServiceDBException(ex);
        }

    }

    public User getByEmail(String email) throws ServiceDBException {
        User user = null;
        try {
            user = userDAO.findByEmail(email);
        } catch (DAOException ex) {
            throw new ServiceDBException(ex);
        }
        return user;
    }

}
