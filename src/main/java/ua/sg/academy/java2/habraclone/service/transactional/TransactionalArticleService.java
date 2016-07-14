package ua.sg.academy.java2.habraclone.service.transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.sg.academy.java2.habraclone.dbModel.dao.ArticleDao;
import ua.sg.academy.java2.habraclone.dbModel.entity.Article;
import ua.sg.academy.java2.habraclone.dbModel.entity.Hub;
import ua.sg.academy.java2.habraclone.dbModel.entity.User;
import ua.sg.academy.java2.habraclone.service.ArticleService;
import ua.sg.academy.java2.habraclone.service.HubService;
import ua.sg.academy.java2.habraclone.service.UserService;
import ua.sg.academy.java2.habraclone.webController.dto.ArticleForm;

import java.time.LocalDateTime;

@Service
@Transactional
public class TransactionalArticleService extends TransactionalEntityService implements ArticleService {

    private final HubService hubService;
    private final UserService userService;

    @Autowired
    public TransactionalArticleService(ArticleDao articleDao, HubService hubService, UserService userService) {
        super(articleDao);
        this.hubService = hubService;
        this.userService = userService;
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
