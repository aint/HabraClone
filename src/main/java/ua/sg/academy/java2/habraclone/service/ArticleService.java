package ua.sg.academy.java2.habraclone.service;

import ua.sg.academy.java2.habraclone.dbModel.entity.Article;
import ua.sg.academy.java2.habraclone.dbModel.entity.User;
import ua.sg.academy.java2.habraclone.webController.dto.ArticleForm;

import java.util.List;

public interface ArticleService extends EntityService {

    List<Article> getAllSortedAscByDate();

    List<Article> getMostPopularArticles();

    Article getLatestArticleOfUser(User user);

    boolean addArticleToFavorites(String username, long articleId);

    void incrementViewsCount(Article article);

    Long createAndSave(ArticleForm articleForm, String authorUsername);

    void deleteArticle(Long id);
}
