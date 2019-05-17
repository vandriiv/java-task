package DTO;

public class UpdateUserBookDTO {

    private long userId;

    private long bookId;

    private int newCount;

    private int oldCount;

    public UpdateUserBookDTO(long userId, long bookId, int newCount, int oldCount) {
        this.userId = userId;
        this.bookId = bookId;
        this.newCount = newCount;
        this.oldCount = oldCount;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public int getNewCount() {
        return newCount;
    }

    public void setNewCount(int newCount) {
        this.newCount = newCount;
    }

    public int getOldCount() {
        return oldCount;
    }

    public void setOldCount(int oldCount) {
        this.oldCount = oldCount;
    }
}
