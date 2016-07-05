package ua.sg.academy.java2.habraclone.webController;

import ua.sg.academy.java2.habraclone.dbModel.dao.ArticleDao;
import ua.sg.academy.java2.habraclone.dbModel.dao.UserDao;
import ua.sg.academy.java2.habraclone.dbModel.dao.factory.HibernateDaoFactory;
import ua.sg.academy.java2.habraclone.dbModel.entity.Article;
import ua.sg.academy.java2.habraclone.dbModel.entity.User;
import ua.sg.academy.java2.habraclone.service.util.HibernateConnectionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@WebServlet("/users")
public class UsersServlet extends HttpServlet {

    private static final String USERS_JSP = "users.jsp";
    private static final String USERS_ATTRIBUTE = "USERS";

    @Override
    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HibernateConnectionFactory.getSessionFactory().getCurrentSession().beginTransaction();
        List<User> users = (List<User>) HibernateDaoFactory.getUserDao().getAll();
        HibernateConnectionFactory.getSessionFactory().getCurrentSession().getTransaction().commit();

        users.sort((u1, u2) -> u2.getRating() - u1.getRating());
        request.setAttribute(USERS_ATTRIBUTE, users);

        request.getRequestDispatcher(USERS_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
