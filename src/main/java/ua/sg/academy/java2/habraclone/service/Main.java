package ua.sg.academy.java2.habraclone.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ua.sg.academy.java2.habraclone.service.util.HibernateConnectionFactory;

public class Main {

    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateConnectionFactory.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.getTransaction().commit();
//        session.close();
        sessionFactory.close();

    }
}
