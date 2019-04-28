package JDBCUtil;

import DAO.ColumnSpecification.BookColumns;
import Entities.Author;
import Entities.Book;
import Entities.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetBookBuilder {

    private ResultSetAuthorBuilder authorBuilder;

    private ResultSetGenreBuilder genreBuilder;

    public ResultSetBookBuilder() {

        authorBuilder = new ResultSetAuthorBuilder();
        genreBuilder = new ResultSetGenreBuilder();
    }

    public Book BuildBookFromResultSet(ResultSet resultSet) throws SQLException{

        Book book = new Book();

        Genre genre = genreBuilder.BuildGenreFromResultSet(resultSet);

        Author author = authorBuilder.BuildAuthorFromResultSet(resultSet);

        book.setId(resultSet.getLong(BookColumns.ID));
        book.setAvailableCount(resultSet.getInt(BookColumns.AVAILABLE_COUNT));
        book.setTotalCount(resultSet.getInt(BookColumns.TOTAL_COUNT));
        book.setTitle(resultSet.getString(BookColumns.TITLE));
        book.setYear(resultSet.getInt(BookColumns.YEAR));
        book.setAuthor(author);
        book.setGenre(genre);

        return book;
    }

}
