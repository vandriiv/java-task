package Command;

import Command.Interfaces.ICommand;
import Entities.User;
import Entities.UserBook;
import Exceptions.OrderedBookAvailabilityException;
import Exceptions.ServiceDBException;
import Mapping.OrderedBookMapper;
import Services.Interfaces.IOrderService;
import Services.Interfaces.IUserService;
import Services.OrderService;
import Services.UserService;
import TokenUtil.JWTProvider;
import TokenUtil.UserTokenModel;
import ViewModels.OrderedBookViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class MakeOrderCommand implements ICommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        IOrderService orderService = OrderService.getInstance();
        IUserService userService = UserService.getInstance();
        
        Gson jsonFormatter = new Gson();
        
        String body = request.getReader().lines()
                .reduce("", (accumulator, actual) -> accumulator + actual);
        List<OrderedBookViewModel> orderedBooks = new Gson()
                .fromJson(body, new TypeToken<List<OrderedBookViewModel>>(){}.getType());

        String token = JWTProvider.getSubjectFromToken(request.getHeader("Authorization")
                .substring("Bearer ".length()));

        UserTokenModel tokenUser = jsonFormatter.fromJson(token,UserTokenModel.class);
        PrintWriter out = response.getWriter();

        User user = null;
        try {
            user = userService.getByEmail(tokenUser.getEmail());
        } catch (ServiceDBException ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(jsonFormatter.toJson("DB error." + ex.getMessage()));
        }

        if(user!=null){
            OrderedBookMapper mapper = new OrderedBookMapper();
            List<UserBook> userBooks = mapper.MapToUserBookList(orderedBooks,user.getId());
            try {
                orderService.makeOrder(userBooks);
                out.print(jsonFormatter.toJson("Success"));
            } catch (OrderedBookAvailabilityException e) {
                response.setStatus(422);
                out.print(jsonFormatter.toJson("Books available count has been updated. Please, check book availability"));
            } catch (ServiceDBException ex) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print(jsonFormatter.toJson("DB error." + ex.getMessage()));
            }
        }
        else{
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.print(jsonFormatter.toJson("User is not authorized"));
        }



    }
}