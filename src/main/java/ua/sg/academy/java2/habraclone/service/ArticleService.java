package ua.sg.academy.java2.habraclone.service;

import ua.sg.academy.java2.habraclone.dbModel.entity.Article;
import ua.sg.academy.java2.habraclone.dbModel.entity.User;

public interface ArticleService extends EntityService {

    Article getLatestArticleOfUser(User user);

    boolean addArticleToFavorites(String username, long articleId);
}
