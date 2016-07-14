package ua.sg.academy.java2.habraclone.service;

import ua.sg.academy.java2.habraclone.dbModel.entity.Article;
import ua.sg.academy.java2.habraclone.dbModel.entity.User;
import ua.sg.academy.java2.habraclone.webController.dto.ArticleForm;

public interface ArticleService extends EntityService {

    Article getLatestArticleOfUser(User user);

    boolean addArticleToFavorites(String username, long articleId);

    void incrementViewsCount(Article article);

    Long createAndSave(ArticleForm articleForm, String authorUsername);
}
