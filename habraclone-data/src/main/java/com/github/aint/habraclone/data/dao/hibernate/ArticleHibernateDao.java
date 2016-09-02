package com.github.aint.habraclone.data.dao.hibernate;

import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import com.github.aint.habraclone.data.dao.inter.ArticleDao;
import com.github.aint.habraclone.data.model.Article;
import com.github.aint.habraclone.data.model.User;

import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class ArticleHibernateDao extends GeneralHibernateDao implements ArticleDao {

    public ArticleHibernateDao() {
        persistentClass = Article.class;
    }

    @Override
    public List<Article> getMostPopularArticles() {
        return getSession()
                .createCriteria(persistentClass)
                .addOrder(Order.desc("rating"))
                .setMaxResults(10)
                .list();
    }

    @Override
    public List<Article> getAllArticlesOfUser(User user) {
        return getSession()
                .getNamedQuery("getAllArticlesOfUser")
                .setEntity("author", user)
                .list();
    }

    @Override
    public Article getLatestArticleOfUser(User user) {
        return (Article) getSession()
                .getNamedQuery("getLatestArticleOfUser")
                .setEntity("author", user)
                .uniqueResult();
    }

}
