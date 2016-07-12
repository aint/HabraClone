package ua.sg.academy.java2.habraclone.service.transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.sg.academy.java2.habraclone.dbModel.dao.ArticleDao;
import ua.sg.academy.java2.habraclone.dbModel.entity.Article;
import ua.sg.academy.java2.habraclone.dbModel.entity.User;
import ua.sg.academy.java2.habraclone.service.ArticleService;
import ua.sg.academy.java2.habraclone.service.UserService;

@Service
@Transactional
public class TransactionalArticleService extends TransactionalEntityService implements ArticleService {

    private final UserService userService;

    @Autowired
    public TransactionalArticleService(ArticleDao articleDao, UserService userService) {
        super(articleDao);
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
    protected ArticleDao getDao() {
        return (ArticleDao) dao;
    }
}
