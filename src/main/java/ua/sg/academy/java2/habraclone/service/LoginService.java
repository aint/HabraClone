package ua.sg.academy.java2.habraclone.service;

import ua.sg.academy.java2.habraclone.dbModel.dao.UserDao;
import ua.sg.academy.java2.habraclone.dbModel.dao.factory.HibernateDaoFactory;
import ua.sg.academy.java2.habraclone.dbModel.entity.User;
import ua.sg.academy.java2.habraclone.service.util.HibernateConnectionFactory;

import java.time.LocalDateTime;

public class LoginService {

    public static boolean login(String email, String password) {
        HibernateConnectionFactory.getSessionFactory().getCurrentSession().beginTransaction();
        User user = HibernateDaoFactory.getUserDao().getByEmail(email);
        HibernateConnectionFactory.getSessionFactory().getCurrentSession().getTransaction().commit();

        return user != null && user.getPassword().equalsIgnoreCase(password);
    }

    public static boolean register(String username, String email, String password) {
        HibernateConnectionFactory.getSessionFactory().getCurrentSession().beginTransaction();
        UserDao userDao = HibernateDaoFactory.getUserDao();
        if (userDao.getByEmail(email) != null) {
            return false;
        }

        userDao.save(new User(email, username, password, LocalDateTime.now(), LocalDateTime.now()));
        HibernateConnectionFactory.getSessionFactory().getCurrentSession().getTransaction().commit();

        return true;
    }

}
