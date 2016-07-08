package ua.sg.academy.java2.habraclone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.sg.academy.java2.habraclone.dbModel.dao.ArticleDao;
import ua.sg.academy.java2.habraclone.dbModel.entity.Article;

import java.util.List;

@Service
@Transactional
public class ArticleService {

    private final ArticleDao articleDao;

    @Autowired
    public ArticleService(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:application-context.xml");
        ArticleService articleService = ctx.getBean(ArticleService.class);
        System.out.println(articleService.getAllArticles().size());
    }

    public Article getArticleById(Long id) {
        if (id == null || id < 1) {
            throw new IllegalArgumentException("Article id can't be negative or null");
        }
        return (Article) articleDao.getById(id);
    }

    @SuppressWarnings("unchecked")
    public List<Article> getAllArticles() {
        return (List<Article>) articleDao.getAll();
    }

}
