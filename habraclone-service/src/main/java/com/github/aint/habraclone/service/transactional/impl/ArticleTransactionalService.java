package com.github.aint.habraclone.service.transactional.impl;

import com.github.aint.habraclone.data.model.Article;
import com.github.aint.habraclone.data.model.Comment;
import com.github.aint.habraclone.data.model.Hub;
import com.github.aint.habraclone.data.model.User;
import com.github.aint.habraclone.data.repository.ArticleRepository;
import com.github.aint.habraclone.service.dto.ArticleForm;
import com.github.aint.habraclone.service.transactional.ArticleService;
import com.github.aint.habraclone.service.transactional.HubService;
import com.github.aint.habraclone.service.transactional.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class ArticleTransactionalService extends AbstractEntityTransactionalService<Article> implements ArticleService {

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
    public Optional<Article> getLatestArticleOfUser(User user) {
        return Optional.ofNullable(getRepository().findLatestArticleOfUser(user));
    }

    @Override
    public boolean addArticleToFavorites(long articleId) {
        Article article = getById(articleId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Article=%s not found", articleId)));
        article.setFavorites(article.getFavorites() + 1);
        save(article);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.getFavorites().add(article);
        user.setFavoritesCount(user.getFavoritesCount() + 1);
        userService.save(user);

        // check user favorities for duplicates
        return false;
    }

    @Override
    public void incrementViewsCount(Article article) {
        article.setViews(article.getViews() + 1);
        save(article);
    }

    @Override
    public Long createAndSave(ArticleForm articleForm) {
        User author = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Hub hub = hubService.getById(articleForm.getHubId())
                .orElseThrow(() -> new NoSuchElementException(String.format("Hub=%s not found", articleForm.getHubId())));
        userService.incrementArticlesCount();
        return save(dtoToArticle(articleForm, hub, author)).getId();
    }

    @Override
    public void deleteArticle(Long id) {
        Article article = getById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("Article=%s not found", id)));
        User author = article.getAuthor();
        userService.decrementArticlesCount();
        article.getComments().stream()
                .map(Comment::getAuthor)
                .forEach(a -> {a.setCommentsCount(a.getCommentsCount() - 1); userService.save(author);});
        delete(article);
    }

    @Override
    public void voteForArticle(Long articleId, boolean plus) {
        Article article = getById(articleId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Article=%s not found", articleId)));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        article.getUsersVoted().add(user);
        article.setRating(plus ? article.getRating() + 1 : article.getRating() - 1);
        article.getAuthor().setRating(plus ? article.getAuthor().getRating() + 1 : article.getAuthor().getRating() - 1);
        save(article);
    }

    @Override
    public boolean userCanVote(Long articleId) {
        Article article = getById(articleId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Article=%s not found", articleId)));
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
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
