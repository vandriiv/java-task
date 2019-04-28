package Exceptions;

public class UserIsAlreadyExistException extends Exception{
    public UserIsAlreadyExistException(String message) {
        super(message);
    }
}
