package Command;

import Command.Interfaces.ICommand;
import Entities.Book;
import Exceptions.ServiceDBException;
import Mapping.BooksListMapper;
import Services.BookService;
import Services.Interfaces.IBookService;
import DTO.BooksListDTO;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class GetBooksByGenreCommand implements ICommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        IBookService bookService = BookService.getInstance();

        String genreName = request.getParameter("genre").toLowerCase();
        PrintWriter out = response.getWriter();
        Gson jsonFormatter = new Gson();
        
        try {
            int limit = Integer.parseInt(request.getParameter("limit"));
            int offset = Integer.parseInt(request.getParameter("offset"));

            if(limit<0 || offset<0){
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(jsonFormatter.toJson("Invalid data format"));
            }
            else {
                List<Book> books = bookService.getBooksByGenreName(genreName,limit+1,offset);
                if (books.isEmpty()) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print(jsonFormatter.toJson("There are not books by this genre"));
                }
                else {
                    BooksListMapper mapper = new BooksListMapper();
                    BooksListDTO booksListDTO = mapper.MapToBooksListDTO(books,limit);
                    out.print(jsonFormatter.toJson(booksListDTO));
                }
            }
        }
        catch (NumberFormatException ex){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print(jsonFormatter.toJson("Invalid data format"));
        } catch (ServiceDBException ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
           out.print(jsonFormatter.toJson("Server error, try to reload page"));
        }
        catch (Exception ex){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(jsonFormatter.toJson("Server error, try to reload page"));
        }

        out.flush();
    }
}
