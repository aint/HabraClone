package ua.sg.academy.java2.habraclone.service.transactional.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.sg.academy.java2.habraclone.dao.ArticleDao;
import ua.sg.academy.java2.habraclone.model.*;
import ua.sg.academy.java2.habraclone.service.transactional.ArticleService;
import ua.sg.academy.java2.habraclone.service.transactional.HubService;
import ua.sg.academy.java2.habraclone.service.transactional.UserService;
import ua.sg.academy.java2.habraclone.webController.dto.ArticleForm;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ArticleTransactionalService extends EntityTransactionalService implements ArticleService {

    private final HubService hubService;
    private final UserService userService;

    @Autowired
    public ArticleTransactionalService(ArticleDao articleDao, HubService hubService, UserService userService) {
        super(articleDao);
        this.hubService = hubService;
        this.userService = userService;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Article> getAllSortedAscByDate() {
        List<Article> articles = (List<Article>) getDao().getAll();
        articles.sort((a1, a2) -> a2.getCreationDate().compareTo(a1.getCreationDate()));
        return articles;
    }

    @Override
    public List<Article> getMostPopularArticles() {
        return getDao().getMostPopularArticles();
    }

    @Override
    public Article getLatestArticleOfUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User can't be null");
        }
        return getDao().getLatestArticleOfUser(user);
    }

    @Override
    public boolean addArticleToFavorites(String username, long articleId) {
        if (username == null || articleId < 0) {
            throw new IllegalArgumentException("Username can't be null and articleId can't be negative");
        }
        Article article = (Article) getById(articleId);
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
        Hub hub = (Hub) hubService.getById(articleForm.getHubId());
        if (author == null || hub == null) {
            throw new IllegalArgumentException("Username or hub can't be null");
        }
        userService.incrementArticlesCount(author);
        return save(dtoToArticle(articleForm, hub, author));
    }

    @Override
    public void deleteArticle(Long id) {
        Article article = (Article) getById(id);
        User author = article.getAuthor();
        userService.decrementArticlesCount(author);
        article.getComments().stream()
                .map(Comment::getAuthor)
                .forEach(a -> {a.setCommentsCount(a.getCommentsCount() - 1); update(author);});
        delete(article);
    }

    @Override
    public void voteForArticle(Long articleId, String username, boolean plus) {
        Article article = (Article) getById(articleId);
        User user = userService.getByUserName(username);
        if (user == null || article == null) {
            throw new IllegalArgumentException("User or article can't be null");
        }
        article.getUsersVoted().add(user);
        article.setRating(plus ? article.getRating() + 1 : article.getRating() - 1);
        article.getAuthor().setRating(plus ? article.getAuthor().getRating() + 1 : article.getAuthor().getRating() - 1);
        update(article);
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
    protected ArticleDao getDao() {
        return (ArticleDao) dao;
    }
}
