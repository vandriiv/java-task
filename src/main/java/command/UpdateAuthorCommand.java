package command;

import authentication.util.JWTBasedAuthenticationManager;
import command.interfaces.ICommand;
import entities.Author;
import exceptions.ServiceDBException;
import globalconstants.GlobalConstants;
import services.BookService;
import services.interfaces.IBookService;
import toket.util.UserTokenClaimsData;
import validation.AuthorValidator;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UpdateAuthorCommand implements ICommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        JWTBasedAuthenticationManager authenticationManager = new JWTBasedAuthenticationManager();
        String header = request.getHeader("Authorization");
        UserTokenClaimsData userTokenModel = authenticationManager.getUsetDataFromAuthHeader(header);

        Gson jsonFormatter = new Gson();
        PrintWriter out = response.getWriter();

        if (userTokenModel != null) {

            if (userTokenModel.getRoleId() == GlobalConstants.LIBRARIAN_ROLE_ID) {
                AuthorValidator authorsValidator = new AuthorValidator();

                try {

                    String body = request.getReader().lines()
                            .reduce("", (accumulator, actual) -> accumulator + actual);

                    Author author = jsonFormatter.fromJson(body, Author.class);


                    if (authorsValidator.isValid(author)) {
                        IBookService bookService = BookService.getInstance();
                        try {
                            bookService.updateAuthor(author);
                            out.print(jsonFormatter.toJson("Successfully updated"));
                        } catch (ServiceDBException ex) {
                            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                            out.print(jsonFormatter.toJson("Server error, try to reload page"));
                        }
                    } else {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        out.print(jsonFormatter.toJson("Invalid data format"));
                    }
                }
                catch (Exception ex){
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                out.print(jsonFormatter.toJson("Do not have rights to access"));
            }

        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.print(jsonFormatter.toJson("User is unauthorized"));
        }
        out.flush();
    }
}
