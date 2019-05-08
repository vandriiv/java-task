package Command;

import AuthenticationUtil.JWTBasedAuthenticationManager;
import Command.Interfaces.ICommand;
import Entities.Book;
import Exceptions.ServiceDBException;
import GlobalConstants.GlobalConstants;
import Services.BookService;
import Services.Interfaces.IBookService;
import TokenUtil.UserTokenModel;
import Validation.BookValidator;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AddBookCommand implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {


        JWTBasedAuthenticationManager authenticationManager = new JWTBasedAuthenticationManager();
        String header = request.getHeader("Authorization");
        UserTokenModel userTokenModel = authenticationManager.getUsetDataFromAuthHeader(header);
        Gson jsonFormatter = new Gson();
        PrintWriter out = response.getWriter();

        if (userTokenModel != null) {
            if (userTokenModel.getRoleId() == GlobalConstants.LIBRARIAN_ROLE_ID) {
                BookValidator bookValidator = new BookValidator();

                String body = request.getReader().lines()
                        .reduce("", (accumulator, actual) -> accumulator + actual);

                Book book = jsonFormatter.fromJson(body, Book.class);


                if (bookValidator.isValid(book)) {
                    IBookService bookService = BookService.getInstance();
                    try {
                        bookService.addBook(book);
                        out.print(jsonFormatter.toJson("Successfully added"));
                    } catch (ServiceDBException ex) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        out.print(jsonFormatter.toJson("Server error, try to reload page"));
                    }
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print(jsonFormatter.toJson("Invalid data format"));
                    }
            }
            else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                out.print(jsonFormatter.toJson("Do not have rights to access"));
            }

        }
        else{
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.print(jsonFormatter.toJson("User is unauthorized"));
        }
        out.flush();
    }
}
