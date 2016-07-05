package ua.sg.academy.java2.habraclone.webController;

import ua.sg.academy.java2.habraclone.service.LoginService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = {"", "/index"})
public class IndexServlet extends HttpServlet {

    private static final String LOG_BUTTON = "log_button";

    private static final String LOGIN = "Login";
    private static final String REGISTER = "Register";
    private static final String LOGOUT = "Logout";

    private static final String INDEX_JSP = "index.jsp";
    private static final String INDEX_URL = "/index";
    private static final String USERS_URL = "/users";

    private static final String EMAIL_FIELD = "email";
    private static final String PASSWORD_FIELD = "password";
    private static final String USERNAME_FIELD = "username";

    private static final String WRONG_EMAIL_OR_PASSWORD = "Wrong email or password";
    private static final String USER_WITH_THAT_EMAIL_ALREADY_EXIST = "User with that email already exist";

    public static final String ERROR_MSG = "error_msg";

    public static final String USER_COOKIE = "user_cookie";
    public static final String USER_SESSION = "user_session";

    private HttpServletRequest request;
    private HttpServletResponse response;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(INDEX_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.request = request;
        this.response = response;

        switch (request.getParameter(LOG_BUTTON)) {
            case LOGIN:
                loginAction();
                break;
            case REGISTER:
                registerAction();
                break;
            case LOGOUT:
                logoutAction();
        }
    }

    private void loginAction() throws IOException, ServletException {
        String email = request.getParameter(EMAIL_FIELD);
        String password = request.getParameter(PASSWORD_FIELD);
        if (LoginService.login(email, password)) {
            setSessionAndCookie(email);
            response.sendRedirect(USERS_URL);
        } else {
            request.setAttribute(ERROR_MSG, WRONG_EMAIL_OR_PASSWORD);
            request.getRequestDispatcher(INDEX_JSP).forward(request, response);
        }
    }

    private void registerAction() throws IOException, ServletException {
        String email = request.getParameter(EMAIL_FIELD);
        String password = request.getParameter(PASSWORD_FIELD);
        String username = request.getParameter(USERNAME_FIELD);
        if (LoginService.register(username, email, password)) {
            setSessionAndCookie(email);
            response.sendRedirect(USERS_URL);
        } else {
            request.setAttribute(ERROR_MSG, USER_WITH_THAT_EMAIL_ALREADY_EXIST);
            request.getRequestDispatcher(INDEX_JSP).forward(request, response);
        }
    }

    private void logoutAction() throws IOException, ServletException {
        request.getSession(true).invalidate();
        Cookie cookie = new Cookie(USER_COOKIE, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        response.sendRedirect(INDEX_URL);
    }

    private void setSessionAndCookie(String email) {
        request.getSession(true).setAttribute(USER_SESSION, email);
        Cookie authenticationCookie = new Cookie(USER_COOKIE, email);
        authenticationCookie.setMaxAge(60 * 60);
        authenticationCookie.setPath("/");
        response.addCookie(authenticationCookie);
    }

}
