package DTO;

import Entities.User;

import java.util.List;

public class UserBooksDTO {

    private User user;

    private List<BookCount> books;

    public UserBooksDTO() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<BookCount> getBooks() {
        return books;
    }

    public void setBooks(List<BookCount> books) {
        this.books = books;
    }
}
