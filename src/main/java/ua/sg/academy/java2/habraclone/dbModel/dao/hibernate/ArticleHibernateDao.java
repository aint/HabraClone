package ua.sg.academy.java2.habraclone.dbModel.dao.hibernate;

import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import ua.sg.academy.java2.habraclone.dbModel.dao.ArticleDao;
import ua.sg.academy.java2.habraclone.dbModel.entity.Article;
import ua.sg.academy.java2.habraclone.dbModel.entity.User;

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
