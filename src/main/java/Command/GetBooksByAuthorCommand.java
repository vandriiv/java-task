package Command;

import Command.Interfaces.ICommand;
import Exceptions.ServiceDBException;
import Services.BookService;
import Services.Interfaces.IBookService;
import ViewModels.BooksListViewModel;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class GetBooksByAuthorCommand implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        IBookService bookService = BookService.getInstance();
        PrintWriter out = response.getWriter();
        Gson jsonFormatter = new Gson();
        
        try {
            long authorId = Long.parseLong(request.getParameter("authorId"));
            int limit = Integer.parseInt(request.getParameter("limit"));
            int offset = Integer.parseInt(request.getParameter("offset"));

            BooksListViewModel books = bookService.getBooksByAuthorId(authorId, limit, offset);

            if (books.getBooks().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print(jsonFormatter.toJson("There are not books by this author!"));
            } else {
                out.print(jsonFormatter.toJson(books));
            }

        } catch (NumberFormatException ex) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print(jsonFormatter.toJson("Invalid data format"));
        } catch (ServiceDBException ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(jsonFormatter.toJson("DB error." + ex.getMessage()));
        }
        catch (Exception ex){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        out.flush();
    }
}
