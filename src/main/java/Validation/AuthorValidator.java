package Validation;

import Entities.Author;

public class AuthorValidator {

    public boolean isValid(Author author){
       return (!author.getLastName().isEmpty() && !author.getFirstName().isEmpty());
    }
}
