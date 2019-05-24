package exceptions;

import exceptions.Enums.DAOExceptionOperation;

public class ServiceDBException extends DAOException {

    public ServiceDBException(String message, DAOExceptionOperation operation) {
        super(message, operation);
    }

    public ServiceDBException(DAOException ex){
        super(ex.getMessage(),ex.getExceptionOperation());
    }
}
