package Command;

import Command.Interfaces.ICommand;
import Entities.User;
import Exceptions.ServiceDBException;
import Exceptions.UserNotFoundException;
import Services.Interfaces.IUserService;
import Services.UserService;
import TokenUtil.JWTProvider;
import TokenUtil.UserTokenModel;
import ViewModels.LoginViewModel;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginCommand implements ICommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        IUserService userService = UserService.getInstance();
        Gson jsonFormatter = new Gson();
        
        String body = request.getReader().lines()
                .reduce("", (accumulator, actual) -> accumulator + actual);

        LoginViewModel loginUser = jsonFormatter.fromJson(body,LoginViewModel.class);

        String email = loginUser.getEmail();
        String password = loginUser.getPassword();
        PrintWriter out = response.getWriter();

        try {
            User user =  userService.Login(email,password);
            UserTokenModel tokenModel = new UserTokenModel(user.getRoleId(),user.getEmail());
            String tokenData = jsonFormatter.toJson(tokenModel);
            String token = JWTProvider.generateToken(tokenData);
            response.setHeader("Authorization", token);

        } catch (UserNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.print(jsonFormatter.toJson(e.getMessage()));

        } catch (ServiceDBException ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
           out.print(jsonFormatter.toJson("Server error, try to reload page"));
        }
        out.flush();
    }
}
