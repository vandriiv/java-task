package Command;

import Command.Interfaces.ICommand;
import Entities.Book;
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

public class GetAllBooksCommand implements ICommand{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        IBookService bookService = BookService.getInstance();
        List<Book> books = null;
        PrintWriter out = response.getWriter();
        Gson jsonFormatter = new Gson();
        
        try {
            books = bookService.getAllBooks();
        } catch (ServiceDBException ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(jsonFormatter.toJson("DB error." + ex.getMessage()));
        }
        catch (Exception ex){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        out.print(jsonFormatter.toJson(books));
        out.flush();
    }
}
