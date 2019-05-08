package ViewModels;

public class UpdateUserBookViewModel {

    private int userId;

    private int bookId;

    private int newCount;

    private int oldCount;

    public UpdateUserBookViewModel(int userId, int bookId, int newCount, int oldCount) {
        this.userId = userId;
        this.bookId = bookId;
        this.newCount = newCount;
        this.oldCount = oldCount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
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
