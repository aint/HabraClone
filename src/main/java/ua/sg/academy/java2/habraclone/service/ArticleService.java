package ua.sg.academy.java2.habraclone.service;

import ua.sg.academy.java2.habraclone.dbModel.entity.Article;
import ua.sg.academy.java2.habraclone.dbModel.entity.Comment;

import java.util.List;

public interface ArticleService extends EntityService {

    List<Comment> getCommentsOfArticle(Article article);
}
