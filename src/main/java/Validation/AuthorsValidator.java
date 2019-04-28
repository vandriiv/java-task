package Validation;

import Entities.Author;

public class AuthorsValidator {

    public boolean isValid(Author author){
       return (!author.getLastName().isEmpty() && !author.getFirstName().isEmpty());
    }
}
