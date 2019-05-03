package Command;

import AuthenticationUtil.JWTBasedAuthenticationManager;
import Command.Interfaces.ICommand;
import Entities.Author;
import Exceptions.ServiceDBException;
import GlobalConstants.GlobalConstants;
import Services.BookService;
import Services.Interfaces.IBookService;
import TokenUtil.UserTokenModel;
import Validation.AuthorValidator;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AddAuthorCommand implements ICommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        JWTBasedAuthenticationManager authenticationManager = new JWTBasedAuthenticationManager();
        String header = request.getHeader("Authorization");
        UserTokenModel userTokenModel = authenticationManager.getUsetDataFromAuthHeader(header);

        if(userTokenModel!=null) {

            if(userTokenModel.getRoleId()==GlobalConstants.LIBRARIAN_ROLE_ID) {

                AuthorValidator authorValidator = new AuthorValidator();

                Gson jsonFormatter = new Gson();

                String body = request.getReader().lines()
                        .reduce("", (accumulator, actual) -> accumulator + actual);

                Author author = jsonFormatter.fromJson(body, Author.class);
                PrintWriter out = response.getWriter();

                if (authorValidator.isValid(author)) {
                    IBookService bookService = BookService.getInstance();
                    try {
                        bookService.addAuthor(author);
                        out.print(jsonFormatter.toJson("Successfully added"));
                    } catch (ServiceDBException ex) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        out.print(jsonFormatter.toJson("Server error, try to reload page"));
                    }
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print(jsonFormatter.toJson("Invalid data format!"));
                }
            }
            else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        }
        else{
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

    }
}
