package Command;

import Command.Interfaces.ICommand;
import Entities.Author;
import Exceptions.ServiceDBException;
import Services.BookService;
import Services.Interfaces.IBookService;
import Validation.AuthorValidator;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UpdateAuthorCommand implements ICommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        AuthorValidator authorsValidator = new AuthorValidator();

        Gson jsonFormatter = new Gson();

        String body = request.getReader().lines()
                .reduce("", (accumulator, actual) -> accumulator + actual);

        Author author = jsonFormatter.fromJson(body,Author.class);
        PrintWriter out = response.getWriter();

        if(authorsValidator.isValid(author)){
            IBookService bookService = BookService.getInstance();
            try{
                bookService.updateAuthor(author);
                out.print(jsonFormatter.toJson("Successfully updated"));
            }
            catch (ServiceDBException ex){
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print(jsonFormatter.toJson("Server error, try to reload page"));
            }
        }
        else{
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print(jsonFormatter.toJson("Invalid data format!"));
        }

    }
}
