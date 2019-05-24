package services.interfaces;

import entities.User;
import exceptions.ServiceDBException;
import exceptions.UserIsAlreadyExistException;
import exceptions.UserNotFoundException;

public interface IUserService {

    User Login(String email, String password) throws UserNotFoundException, ServiceDBException;

    void Registration(String email,String password,String phoneNumber,long roleId)
            throws UserIsAlreadyExistException, ServiceDBException;

    User getByEmail(String email) throws ServiceDBException;

}
