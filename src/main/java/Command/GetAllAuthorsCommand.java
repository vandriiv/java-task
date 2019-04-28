package Command;

import Command.Interfaces.ICommand;
import Entities.Author;
import Exceptions.ServiceDBException;
import Services.BookService;
import Services.Interfaces.IBookService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class GetAllAuthorsCommand implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        IBookService bookService = BookService.getInstance();
        Gson jsonFormatter = new Gson();
        PrintWriter out = response.getWriter();

        List<Author> authors = null;

        try {
            authors = bookService.getAllAuthors();
            out.print(jsonFormatter.toJson(authors));
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
