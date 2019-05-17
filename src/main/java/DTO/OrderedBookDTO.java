package DTO;

public class OrderedBookDTO {
    private long bookId;

    private int count;

    public OrderedBookDTO(long bookId, int count) {
        this.bookId = bookId;
        this.count = count;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
