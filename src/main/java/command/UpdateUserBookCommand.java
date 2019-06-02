package command;

import authentication.util.JWTBasedAuthenticationManager;
import command.interfaces.ICommand;
import exceptions.ServiceDBException;
import globalconstants.GlobalConstants;
import services.interfaces.IOrderService;
import services.OrderService;
import token.util.UserTokenClaimsData;
import dto.UpdateUserBookDTO;
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
        UserTokenClaimsData userTokenModel = authenticationManager.getUsetDataFromAuthHeader(header);
        Gson jsonFormatter = new Gson();
        PrintWriter out = response.getWriter();

        if (userTokenModel != null) {
            if (userTokenModel.getRoleId() == GlobalConstants.LIBRARIAN_ROLE_ID) {

                try {

                    String body = request.getReader().lines()
                            .reduce("", (accumulator, actual) -> accumulator + actual);
                    UpdateUserBookDTO userBookDTO = jsonFormatter.fromJson(body, UpdateUserBookDTO.class);

                    if (userBookDTO.getOldCount() <= userBookDTO.getNewCount()) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        out.print(jsonFormatter.toJson("Invalid data format"));
                    }
                    else {
                        try {
                            IOrderService orderService = OrderService.getInstance();
                            orderService.updateUserBookCount(userBookDTO.getUserId()
                                    , userBookDTO.getBookId(), userBookDTO.getNewCount(),
                                    userBookDTO.getOldCount());
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

