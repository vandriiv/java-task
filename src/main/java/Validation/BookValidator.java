package Validation;


import Entities.Book;

public class BookValidator {
    public boolean isValid(Book book) {
        if(book.getTitle().isEmpty())
            return false;
        if(book.getAvailableCount()<0)
            return false;
        if(book.getTotalCount()<0)
            return false;
        if (book.getTotalCount()<book.getAvailableCount())
            return false;
        if(book.getYear()<0)
            return false;

        return true;
    }
}
