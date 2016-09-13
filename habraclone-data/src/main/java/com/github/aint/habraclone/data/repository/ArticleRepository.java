package com.github.aint.habraclone.data.repository;

import com.github.aint.habraclone.data.model.Article;
import com.github.aint.habraclone.data.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleRepository extends CrudRepository<Article, Long> {

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
