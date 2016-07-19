package ua.sg.academy.java2.habraclone.dao;

import ua.sg.academy.java2.habraclone.model.Article;
import ua.sg.academy.java2.habraclone.model.User;

import java.util.List;

public interface ArticleDao extends GeneralDao {

    /**
     * Returns the 10 most popular articles.
     *
     * @return a list of the most popular articles
     */
    List<Article> getMostPopularArticles();

    /**
     * Returns all articles of the specified {@code user}.
     *
     * @param user to find articles for
     * @return all articles of the given {@code user}
     */
    List<Article> getAllArticlesOfUser(User user);

    /**
     * Returns the latest article of the specified {@code user}.
     *
     * @param user
     *            to find articles for
     * @return the latest article of the given {@code user} or {@code null} if the {@code user} has no articles
     */
    Article getLatestArticleOfUser(User user);

}
