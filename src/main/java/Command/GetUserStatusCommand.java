package Command;

import Command.Interfaces.ICommand;
import TokenUtil.JWTProvider;
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
        
        if(!header.contains("Bearer ")){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.print(jsonFormatter.toJson("User is not authorized"));
        }
        else {
            String token = header.substring("Bearer ".length());

            try {
                String email = JWTProvider.getSubjectFromToken(token);
                out.print(jsonFormatter.toJson(email));
            } catch (Exception ex) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        out.flush();
    }
}
