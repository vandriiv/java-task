package ViewModels;

import Entities.Book;
import Entities.User;

import java.util.HashMap;

public class UserBooksViewModel {

    private User user;

    private HashMap<Book,Integer> books = new HashMap<>();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public HashMap<Book, Integer> getBooks() {
        return books;
    }

    public void setBooks(HashMap<Book, Integer> books) {
        this.books = books;
    }
}
