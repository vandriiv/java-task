package Command;

import Command.Interfaces.ICommand;
import Exceptions.ServiceDBException;
import Exceptions.UserIsAlreadyExistException;
import Services.Interfaces.IUserService;
import Services.UserService;
import Validation.RegistrationValidator;
import ViewModels.RegistrationViewModel;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RegistrationCommand implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        IUserService userService = UserService.getInstance();
        Gson jsonFormatter = new Gson();
        
        String body = request.getReader().lines()
                .reduce("", (accumulator, actual) -> accumulator + actual);

        RegistrationViewModel user = jsonFormatter.fromJson(body,RegistrationViewModel.class);

        RegistrationValidator registrationValidator = new RegistrationValidator();
        PrintWriter out = response.getWriter();

        if(registrationValidator.isValid(user)) {

            try {
                userService.Registration(user.getEmail(), user.getPassword(), user.getPhoneNumber(), 1);
                out.print(jsonFormatter.toJson("Now you can login"));

            } catch (UserIsAlreadyExistException e) {
                out.print(jsonFormatter.toJson(e.getMessage()));

            } catch (ServiceDBException ex) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
               out.print(jsonFormatter.toJson("Server error, try to reload page"));
            }
        }
        else{
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print(jsonFormatter.toJson(registrationValidator.getValidationErrors()));
        }
        out.flush();

    }
}
