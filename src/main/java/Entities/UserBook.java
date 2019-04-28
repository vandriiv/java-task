package Entities;

public class UserBook {

    private long id;

    private User user;

    private Book book;

    private int count;

    public UserBook(long id, User user, Book book) {
        this.id = id;
        this.user = user;
        this.book = book;
    }

    public UserBook() {
        user = new User();
        book = new Book();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
