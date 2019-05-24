package command;

import authentication.util.JWTBasedAuthenticationManager;
import command.interfaces.ICommand;
import toket.util.UserTokenClaimsData;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class GetUserStatusCommand implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        Gson jsonFormatter = new Gson();
        String header = request.getHeader("Authorization");

        JWTBasedAuthenticationManager authenticationManager = new JWTBasedAuthenticationManager();

        UserTokenClaimsData userTokenModel = authenticationManager.getUsetDataFromAuthHeader(header);
        if(userTokenModel==null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        else{
            out.println(jsonFormatter.toJson(userTokenModel));
        }

        out.flush();
    }
}
