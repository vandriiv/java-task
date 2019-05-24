package command;

import authentication.util.JWTBasedAuthenticationManager;
import command.interfaces.ICommand;
import entities.User;
import entities.UserBook;
import exceptions.OrderedBookAvailabilityException;
import exceptions.ServiceDBException;
import mapping.OrderedBookMapper;
import services.interfaces.IOrderService;
import services.interfaces.IUserService;
import services.OrderService;
import services.UserService;
import toket.util.UserTokenClaimsData;
import dto.OrderedBookDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class MakeOrderCommand implements ICommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        
        Gson jsonFormatter = new Gson();

        String header = request.getHeader("Authorization");

        JWTBasedAuthenticationManager authenticationManager = new JWTBasedAuthenticationManager();

        UserTokenClaimsData userTokenModel = authenticationManager.getUsetDataFromAuthHeader(header);

        PrintWriter out = response.getWriter();
        if(userTokenModel!=null) {
            IOrderService orderService = OrderService.getInstance();
            IUserService userService = UserService.getInstance();

            try {
                User user;
                try {
                    user = userService.getByEmail(userTokenModel.getEmail());
                } catch (ServiceDBException ex) {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                   out.print(jsonFormatter.toJson("Server error, try to reload page"));
                    return;
                }

                if (user != null) {
                    String body = request.getReader().lines()
                            .reduce("", (accumulator, actual) -> accumulator + actual);

                    List<OrderedBookDTO> orderedBooks = new Gson()
                            .fromJson(body, new TypeToken<List<OrderedBookDTO>>() {
                            }.getType());
                    OrderedBookMapper mapper = new OrderedBookMapper();
                    List<UserBook> userBooks = mapper.MapToUserBookList(orderedBooks, user.getId());
                    try {
                        orderService.makeOrder(userBooks);
                        out.print(jsonFormatter.toJson("Success"));
                    } catch (OrderedBookAvailabilityException e) {
                        response.setStatus(422);
                        out.print(jsonFormatter.toJson("Books available count has been updated. Please, check book availability"));
                    } catch (ServiceDBException ex) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                       out.print(jsonFormatter.toJson("Server error, try to reload page"));
                    }
                } else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    out.print(jsonFormatter.toJson("User is not authorized"));
                }
            }
            catch (Exception ex){
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print(jsonFormatter.toJson("Server error"));
            }
        }
        else{
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.print(jsonFormatter.toJson("User is not authorized"));
        }
    }
}
