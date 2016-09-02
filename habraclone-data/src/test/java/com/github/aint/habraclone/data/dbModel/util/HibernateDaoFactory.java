package com.github.aint.habraclone.data.dbModel.util;

import org.hibernate.Session;
import com.github.aint.habraclone.data.dao.inter.ArticleDao;
import com.github.aint.habraclone.data.dao.inter.CommentDao;
import com.github.aint.habraclone.data.dao.inter.HubDao;
import com.github.aint.habraclone.data.dao.inter.UserDao;
import com.github.aint.habraclone.data.dao.hibernate.*;

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
