package command;

import authentication.util.JWTBasedAuthenticationManager;
import command.interfaces.ICommand;
import dto.UserBooksDTO;
import entities.UserBook;
import exceptions.ServiceDBException;
import mapping.UserBooksMapper;
import services.interfaces.IOrderService;
import services.OrderService;
import toket.util.UserTokenClaimsData;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class GetUserBooksCommand implements ICommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter out = response.getWriter();
        Gson jsonFormatter = new Gson();

        String header = request.getHeader("Authorization");

        JWTBasedAuthenticationManager authenticationManager = new JWTBasedAuthenticationManager();

        UserTokenClaimsData userTokenModel = authenticationManager.getUsetDataFromAuthHeader(header);

        if(userTokenModel!=null) {
            IOrderService orderService = OrderService.getInstance();

            String email = request.getParameter("email");

            try {
                List<UserBook> userBooks = orderService.getUserOrderedBooks(email);

                if (!userBooks.isEmpty()) {
                    UserBooksMapper mapper = new UserBooksMapper();
                    UserBooksDTO userBooksDTO = mapper.MapToUserBooksDTO(userBooks);
                    out.println(jsonFormatter.toJson(userBooksDTO));
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
