package com.github.aint.habraclone.service.transactional.impl;

import com.github.aint.habraclone.data.model.Article;
import com.github.aint.habraclone.data.model.Comment;
import com.github.aint.habraclone.data.model.Hub;
import com.github.aint.habraclone.data.model.User;
import com.github.aint.habraclone.data.repository.ArticleRepository;
import com.github.aint.habraclone.service.dto.ArticleForm;
import com.github.aint.habraclone.service.transactional.inter.ArticleService;
import com.github.aint.habraclone.service.transactional.inter.HubService;
import com.github.aint.habraclone.service.transactional.inter.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ArticleTransactionalService extends EntityTransactionalService<Article> implements ArticleService {

    private final HubService hubService;
    private final UserService userService;

    @Autowired
    public ArticleTransactionalService(ArticleRepository articleRepository, HubService hubService, UserService userService) {
        super(articleRepository);
        this.hubService = hubService;
        this.userService = userService;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Article> getAllSortedAscByDate() {
        List<Article> articles = (List<Article>) getRepository().findAll();
        articles.sort((a1, a2) -> a2.getCreationDate().compareTo(a1.getCreationDate()));
        return articles;
    }

    @Override
    public List<Article> getMostPopularArticles() {
        return getRepository().findTop10ByOrderByRatingDesc();
    }

    @Override
    public Article getLatestArticleOfUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User can't be null");
        }
        return getRepository().findLatestArticleOfUser(user);
    }

    @Override
    public boolean addArticleToFavorites(String username, long articleId) {
        if (username == null || articleId < 0) {
            throw new IllegalArgumentException("Username can't be null and articleId can't be negative");
        }
        Article article = getById(articleId);
        article.setFavorites(article.getFavorites() + 1);
        update(article);

        User user = userService.getByUserName(username);
        user.getFavorites().add(article);
        user.setFavoritesCount(user.getFavoritesCount() + 1);
        userService.update(user);

        // check user favorities for duplicates
        return false;
    }

    @Override
    public void incrementViewsCount(Article article) {
        article.setViews(article.getViews() + 1);
        update(article);
    }

    @Override
    public Long createAndSave(ArticleForm articleForm, String authorUsername) {
        User author = userService.getByUserName(authorUsername);
        Hub hub = hubService.getById(articleForm.getHubId());
        if (author == null || hub == null) {
            throw new IllegalArgumentException("Username or hub can't be null");
        }
        userService.incrementArticlesCount(author);
        return save(dtoToArticle(articleForm, hub, author));
    }

    @Override
    public void deleteArticle(Long id) {
        Article article = getById(id);
        User author = article.getAuthor();
        userService.decrementArticlesCount(author);
        article.getComments().stream()
                .map(Comment::getAuthor)
                .forEach(a -> {a.setCommentsCount(a.getCommentsCount() - 1); userService.update(author);});
        delete(article);
    }

    @Override
    public void voteForArticle(Long articleId, String username, boolean plus) {
        Article article = getById(articleId);
        User user = userService.getByUserName(username);
        if (user == null || article == null) {
            throw new IllegalArgumentException("User or article can't be null");
        }
        article.getUsersVoted().add(user);
        article.setRating(plus ? article.getRating() + 1 : article.getRating() - 1);
        article.getAuthor().setRating(plus ? article.getAuthor().getRating() + 1 : article.getAuthor().getRating() - 1);
        update(article);
    }

    @Override
    public boolean userCanVote(Long articleId, String username) {
        Article article = getById(articleId);
        if (article == null) {
            throw new IllegalArgumentException("Article can't be null");
        }
        return article.getUsersVoted().stream().noneMatch(u -> u.getUsername().equals(username));
    }

    private Article dtoToArticle(ArticleForm articleForm, Hub hub, User author) {
        Article article = new Article();
        article.setAuthor(author);
        article.setHub(hub);
        article.setTitle(articleForm.getTitle());
        article.setPreview(articleForm.getPreview());
        article.setBody(articleForm.getBody());
        article.setKeywords(articleForm.getKeywords());
        article.setCreationDate(LocalDateTime.now());
        return article;
    }

    @Override
    protected ArticleRepository getRepository() {
        return (ArticleRepository) repository;
    }
}
