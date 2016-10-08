package com.github.aint.habraclone.service.transactional;

import com.github.aint.habraclone.data.model.Article;
import com.github.aint.habraclone.data.model.User;
import com.github.aint.habraclone.service.dto.ArticleForm;

import java.util.List;
import java.util.Optional;

public interface ArticleService extends EntityService<Article> {

    List<Article> getAllSortedAscByDate();

    List<Article> getMostPopularArticles();

    Optional<Article> getLatestArticleOfUser(User user);

    boolean addArticleToFavorites(String username, long articleId);

    void incrementViewsCount(Article article);

    Long createAndSave(ArticleForm articleForm, String authorUsername);

    void deleteArticle(Long id);

    void voteForArticle(Long articleId, String username, boolean plus);

    boolean userCanVote(Long articleId, String username);
}
