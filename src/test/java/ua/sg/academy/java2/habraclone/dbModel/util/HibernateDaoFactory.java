package ua.sg.academy.java2.habraclone.dbModel.util;

import org.hibernate.Session;
import ua.sg.academy.java2.habraclone.dao.ArticleDao;
import ua.sg.academy.java2.habraclone.dao.CommentDao;
import ua.sg.academy.java2.habraclone.dao.HubDao;
import ua.sg.academy.java2.habraclone.dao.UserDao;
import ua.sg.academy.java2.habraclone.dao.hibernate.*;

public class HibernateDaoFactory {

    public static ArticleDao getArticleDao() {
        return (ArticleDao) instantiateDAO(ArticleHibernateDao.class);
    }

    public static CommentDao getCommentDao() {
        return (CommentDao) instantiateDAO(CommentDaoHibernate.class);
    }

    public static HubDao getHubDao() {
        return (HubDao) instantiateDAO(HubHibernateDao.class);
    }

    public static UserDao getUserDao() {
        return (UserDao) instantiateDAO(UserHibernateDao.class);
    }

    private static GeneralHibernateDao instantiateDAO(Class daoClass) {
        try {
            GeneralHibernateDao dao = (GeneralHibernateDao) daoClass.newInstance();
            dao.setSession(getCurrentSession());
            return dao;
        } catch (Exception ex) {
            throw new RuntimeException("Can not instantiate DAO: " + daoClass, ex);
        }
    }

    public static Session getCurrentSession() {
        return HibernateConnectionFactory.getSessionFactory().getCurrentSession();
    }

    public static void beginTransaction() {
        HibernateConnectionFactory.getSessionFactory().getCurrentSession().beginTransaction();
    }

    public static void commitTransaction() {
        HibernateConnectionFactory.getSessionFactory().getCurrentSession().getTransaction().commit();
    }

}
