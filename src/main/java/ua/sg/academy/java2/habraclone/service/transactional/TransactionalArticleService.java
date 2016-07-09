package ua.sg.academy.java2.habraclone.service.transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.sg.academy.java2.habraclone.dbModel.dao.ArticleDao;
import ua.sg.academy.java2.habraclone.dbModel.entity.Article;
import ua.sg.academy.java2.habraclone.dbModel.entity.Comment;
import ua.sg.academy.java2.habraclone.service.ArticleService;

import java.util.List;

@Service
@Transactional
public class TransactionalArticleService extends TransactionalEntityService implements ArticleService {

    @Autowired
    public TransactionalArticleService(ArticleDao articleDao) {
        super(articleDao);
    }

    @Override
    public List<Comment> getCommentsOfArticle(Article article) {
        if (article == null) {
            throw new IllegalArgumentException("Article can't be null");
        }
        return article.getComments();
    }

    @Override
    protected ArticleDao getDao() {
        return (ArticleDao) dao;
    }
}
