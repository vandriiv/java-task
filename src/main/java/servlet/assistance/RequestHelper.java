package servlet.assistance;

import command.*;
import command.interfaces.ICommand;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class RequestHelper {
    private static RequestHelper instance = null;

    private HashMap<String, ICommand> commands = new HashMap<>(17);

    private RequestHelper() {
       commands.put("booksByGenre", new GetBooksByGenreCommand());
       commands.put("authors",new GetAllAuthorsCommand());
       commands.put("booksByAuthor",new GetBooksByAuthorCommand());
       commands.put("books",new GetBooksRangeCommand());
       commands.put("registration",new RegistrationCommand());
       commands.put("login",new LoginCommand());
       commands.put("logout",new LoginCommand());
       commands.put("isLoggedIn", new GetUserStatusCommand());
       commands.put("makeOrder",new MakeOrderCommand());
       commands.put("getAvailability", new GetAvailabilityCommand());
       commands.put("addAuthor", new AddAuthorCommand());
       commands.put("updateAuthor", new UpdateAuthorCommand());
       commands.put("booksByTitle", new GetBooksByTitleCommand());
       commands.put("addBook",new AddBookCommand());
       commands.put("updateBook",new UpdateBookCommand());
       commands.put("userBooks",new GetUserBooksCommand());
       commands.put("updateUserBook",new UpdateUserBookCommand());

    }

    public ICommand getCommand(HttpServletRequest request) {

        String commandName = request.getPathInfo().substring(1);

        ICommand command = commands.get(commandName);
        if (command == null) {
            command = new NoMatchesCommand();
        }
        return command;
    }


    public static RequestHelper getInstance() {
        if (instance == null) {
            instance = new RequestHelper();
        }
        return instance;
    }
}
