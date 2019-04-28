package Exceptions;

import Exceptions.Enums.DAOExceptionOperation;

public class DAOException extends Exception {

    private final DAOExceptionOperation exceptionOperation;

    public DAOException(String message, DAOExceptionOperation operation) {
        super(message);
        exceptionOperation = operation;
    }

    public DAOExceptionOperation getExceptionOperation() {
        return exceptionOperation;
    }
}
