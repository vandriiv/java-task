package command;

import command.interfaces.ICommand;
import exceptions.ServiceDBException;
import services.BookService;
import services.interfaces.IBookService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

public class GetAvailabilityCommand implements ICommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        IBookService bookService = BookService.getInstance();
        Gson jsonFormatter = new Gson();
        
        String body = request.getReader().lines()
                .reduce("", (accumulator, actual) -> accumulator + actual);

        List<Long> cartItems = jsonFormatter.fromJson(body,new TypeToken<List<Long>>(){}.getType());

        PrintWriter out = response.getWriter();
        try {
            HashMap<Long,Integer> booksAvailability = bookService.getAvailableCount(cartItems);

            out.print(jsonFormatter.toJson(booksAvailability));

        } catch (ServiceDBException ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
           out.print(jsonFormatter.toJson("Server error, try to reload page"));
        }

        out.flush();

    }
}
