package Command;

import AuthenticationUtil.JWTBasedAuthenticationManager;
import Command.Interfaces.ICommand;
import Exceptions.ServiceDBException;
import Services.BookService;
import Services.Interfaces.IBookService;
import TokenUtil.UserTokenModel;
import ViewModels.UserBooksViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class GetUserBooksCommand implements ICommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        IBookService bookService = BookService.getInstance();
        PrintWriter out = response.getWriter();
        Gson jsonFormatter = new GsonBuilder().enableComplexMapKeySerialization().create();

        String header = request.getHeader("Authorization");

        JWTBasedAuthenticationManager authenticationManager = new JWTBasedAuthenticationManager();

        UserTokenModel userTokenModel = authenticationManager.getUsetDataFromAuthHeader(header);

        if(userTokenModel!=null) {
            String email = request.getParameter("email");

            try {
                UserBooksViewModel userBooksViewModel = bookService.getUserBooks(email);
                if (userBooksViewModel != null) {
                    out.println(jsonFormatter.toJson(userBooksViewModel));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print(jsonFormatter.toJson("You do not have ordered books"));
                }
            } catch (ServiceDBException ex) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print(jsonFormatter.toJson("Server error, try to reload page"));
            } catch (Exception ex) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print(jsonFormatter.toJson("Server error, try to reload page"));
            }
        }
        else{
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.print(jsonFormatter.toJson("User is not authorized"));
        }
        out.flush();
    }
}
