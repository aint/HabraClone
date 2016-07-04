package ua.sg.academy.java2.habraclone.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ua.sg.academy.java2.habraclone.dbModel.dao.CommentDao;
import ua.sg.academy.java2.habraclone.dbModel.dao.factory.HibernateDaoFactory;
import ua.sg.academy.java2.habraclone.dbModel.entity.Comment;
import ua.sg.academy.java2.habraclone.service.util.HibernateConnectionFactory;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        Comment comment = new Comment();
        comment.setBody("body");
        comment.setCreationDate(LocalDateTime.now());
        comment.setRating(2);

        SessionFactory sessionFactory = HibernateConnectionFactory.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        CommentDao commentDao = HibernateDaoFactory.getCommentDao();
//        commentDao.save(comment);

        Comment c = (Comment) commentDao.getById(1);
        System.out.println(c.getBody());
        System.out.println(c.getCreationDate());

        session.getTransaction().commit();
        sessionFactory.close();
    }

}
