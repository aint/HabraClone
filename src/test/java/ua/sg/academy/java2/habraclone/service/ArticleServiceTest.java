package ua.sg.academy.java2.habraclone.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import ua.sg.academy.java2.habraclone.dbModel.dao.ArticleDao;
import ua.sg.academy.java2.habraclone.dbModel.entity.Article;
import ua.sg.academy.java2.habraclone.dbModel.entity.User;
import ua.sg.academy.java2.habraclone.service.transactional.TransactionalArticleService;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ArticleServiceTest {

    private static final Long ARTICLE_ID = 1L;
    private static final int ARTICLE_RATING = 0;

    @Mock
    private ArticleDao articleDao;
    @Mock
    private HubService hubService;
    @Mock
    private UserService userService;
    private ArticleService articleService;

    @Before
    public void init() throws SQLException {
        initMocks(this);
        articleService = new TransactionalArticleService(articleDao, hubService, userService);
    }

    @After
    public void clean() {

    }

    @Test
    public void add() {
        final Article article = getArticle();
        articleService.save(article);

        assertEquals(article.getRating(), 0);
        assertNotNull(article.getAuthor());
        assertNotNull(article.getTitle());
        assertNotNull(article.getBody());
        assertNotNull(article.getKeywords());

        verify(articleDao).save(article);
    }

    @Test
    public void getMostPopularArticles() {
        final List<Article> expected = new ArrayList<Article>(Arrays.asList(
                getArticle(),
                getArticle(),
                getArticle()
        ));
        when(articleDao.getMostPopularArticles()).thenReturn(expected);

        assertEquals(articleService.getMostPopularArticles(), expected);

        verify(articleDao).getMostPopularArticles();
    }

    @Test
    public void getLatestArticleOfUser() {
        final Article expected = getArticle();
        when(articleDao.getLatestArticleOfUser(expected.getAuthor())).thenReturn(expected);

        assertEquals(articleService.getLatestArticleOfUser(expected.getAuthor()), expected);

        verify(articleDao).getLatestArticleOfUser(expected.getAuthor());
    }

    @Test
    public void getById() {
        final Article expected = getArticle();
        when(articleDao.getById(ARTICLE_ID)).thenReturn(expected);

        assertEquals(articleService.getById(ARTICLE_ID), expected);

        verify(articleDao).getById(ARTICLE_ID);
    }

    @Test
    public void getNotFound() {
        when(articleDao.getById(ARTICLE_ID)).thenReturn(null);

        assertEquals(articleService.getById(ARTICLE_ID), null);

        articleService.getById(ARTICLE_ID);
    }

    @Test(expected =  IllegalArgumentException.class)
    public void getByIdNull() {
        articleService.getById(null);
    }



    private Article getArticle() {
        Article article = new Article("title", "preview", "body", "keywords");
        article.setId(ARTICLE_ID);
        article.setCreationDate(LocalDateTime.now());
        article.setRating(ARTICLE_RATING);
        article.setAuthor(getUser());
        return article;
    }

    private User getUser() {
        return new User("userName", "user@gmail.com", "password");
    }

}
