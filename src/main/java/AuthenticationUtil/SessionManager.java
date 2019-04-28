package AuthenticationUtil;

import javax.servlet.http.HttpSession;

public class SessionManager {

    private final static String SESSION_NAME = "loggedUser";

    public static void setSession(HttpSession session, String email){

        session.setAttribute(SESSION_NAME, email);
    }

    public static String getUserFromSession(HttpSession session){
        String userEmail = (String) session.getAttribute(SESSION_NAME);
        return userEmail;
    }

    public static void finishSession(HttpSession session){
        session.removeAttribute(SESSION_NAME);
    }
}
