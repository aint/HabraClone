package ua.sg.academy.java2.habraclone.webController;

import ua.sg.academy.java2.habraclone.dbModel.dao.factory.HibernateDaoFactory;
import ua.sg.academy.java2.habraclone.service.LoginService;
import ua.sg.academy.java2.habraclone.service.util.HibernateConnectionFactory;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet(urlPatterns = {"", "/index"})
public class IndexServlet extends HttpServlet {

    private static final String ARTICLES_ATTRIBUTE = "ARTICLES";

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
        setRequestAndResponse(request, response);
        setAttributeAndForward(null);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setRequestAndResponse(request, response);

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

    private void setRequestAndResponse(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    private void loginAction() throws IOException, ServletException {
        String email = request.getParameter(EMAIL_FIELD);
        String password = request.getParameter(PASSWORD_FIELD);
        if (LoginService.login(email, password)) {
            setSessionAndCookie(email);
            response.sendRedirect(USERS_URL);
        } else {
            setAttributeAndForward(WRONG_EMAIL_OR_PASSWORD);
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
            setAttributeAndForward(USER_WITH_THAT_EMAIL_ALREADY_EXIST);
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

    private void setAttributeAndForward(String errorMsg) throws ServletException, IOException {
        HibernateConnectionFactory.getSessionFactory().getCurrentSession().beginTransaction();
        request.setAttribute(ARTICLES_ATTRIBUTE, HibernateDaoFactory.getArticleDao().getAll());
        HibernateConnectionFactory.getSessionFactory().getCurrentSession().getTransaction().commit();
        request.setAttribute(ERROR_MSG, errorMsg);
        request.getRequestDispatcher(INDEX_JSP).forward(request, response);
    }

    private void setSessionAndCookie(String email) {
        request.getSession(true).setAttribute(USER_SESSION, email);
        Cookie authenticationCookie = new Cookie(USER_COOKIE, email);
        authenticationCookie.setMaxAge(60 * 60);
        authenticationCookie.setPath("/");
        response.addCookie(authenticationCookie);
    }

}
