package Command;

import AuthenticationUtil.JWTBasedAuthenticationManager;
import Command.Interfaces.ICommand;
import Exceptions.ServiceDBException;
import GlobalConstants.GlobalConstants;
import Services.Interfaces.IOrderService;
import Services.OrderService;
import TokenUtil.UserTokenModel;
import ViewModels.UpdateUserBookViewModel;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UpdateUserBookCommand implements ICommand {
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

                try {

                    String body = request.getReader().lines()
                            .reduce("", (accumulator, actual) -> accumulator + actual);
                    UpdateUserBookViewModel userBookViewModel = jsonFormatter.fromJson(body, UpdateUserBookViewModel.class);

                    if (userBookViewModel.getOldCount() <= userBookViewModel.getNewCount()) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        out.print(jsonFormatter.toJson("Invalid data format"));
                    }
                    else {
                        try {
                            IOrderService orderService = OrderService.getInstance();
                            orderService.updateUserBookCount(userBookViewModel.getUserId()
                                    , userBookViewModel.getBookId(), userBookViewModel.getNewCount(),
                                    userBookViewModel.getOldCount());
                            out.print(jsonFormatter.toJson("Successfully updated"));
                        } catch (ServiceDBException ex) {
                            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                            out.print(jsonFormatter.toJson("Server error, try to reload page"));
                        }
                    }
                }
                catch (Exception ex){
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    out.print(jsonFormatter.toJson("Server error, try to reload page"));
                }
            }
            else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                out.print(jsonFormatter.toJson("Do not have rights to access"));
            }
        }
        else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.print(jsonFormatter.toJson("User is unauthorized"));
        }
        out.flush();

    }
}

