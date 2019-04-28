package Services.Interfaces;

import Entities.User;
import Exceptions.ServiceDBException;
import Exceptions.UserIsAlreadyExistException;
import Exceptions.UserNotFoundException;

public interface IUserService {

    User Login(String email, String password) throws UserNotFoundException, ServiceDBException;

    void Registration(String email,String password,String phoneNumber,long roleId)
            throws UserIsAlreadyExistException, ServiceDBException;

    User getByEmail(String email) throws ServiceDBException;

}
