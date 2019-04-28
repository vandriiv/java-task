package ViewModels;

import Entities.Book;

import java.util.List;

public class BooksListViewModel {

    private List<Book> books;

    private boolean hasMore;

    public BooksListViewModel(List<Book> books, boolean hasMore) {
        this.books = books;
        this.hasMore = hasMore;
    }

    public BooksListViewModel() {

    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }
}
