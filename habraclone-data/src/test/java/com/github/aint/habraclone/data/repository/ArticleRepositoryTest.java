package com.github.aint.habraclone.data.repository;

import com.github.aint.habraclone.data.config.DataSpringConfig;
import com.github.aint.habraclone.data.model.Article;
import com.github.aint.habraclone.data.model.Hub;
import com.github.aint.habraclone.data.model.User;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.operation.CompositeOperation.sequenceOf;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:test-db.properties")
@ContextConfiguration(classes = { DataSpringConfig.class })
public class ArticleRepositoryTest {

    private static final String ARTICLE_TABLE = Article.class.getSimpleName();
    private static final String HUB_TABLE = Hub.class.getSimpleName();
    private static final String USER_TABLE = User.class.getSimpleName();

    private static final Operation DELETE_ALL = sequenceOf(deleteAllFrom(ARTICLE_TABLE, HUB_TABLE, USER_TABLE));
    private static final Operation INSERT_DATA =
            sequenceOf(
                    insertInto(USER_TABLE)
                            .columns("id", "email", "username", "password", "lastLoginTime", "registrationDate",
                                    "enabled", "admin", "banExpirationDate", "birthday", "rating", "about", "fullName",
                                    "articlesCount", "commentsCount",  "favoritesCount", "language", "location")
                            .values(1L, "user1@gmail.com", "user_1", "111111", "2010-01-01 11:11:11", "2016-01-01 15:47:17",
                                    true, true, null, null, 0, "about", "first user",
                                    2, 0, 1, null, null)
                            .build(),
                    insertInto(HUB_TABLE)
                            .columns("id", "name", "creationDate", "description",  "rating")
                            .values(1L, "Hub_1", "2016-07-01 15:47:17", "First Hub", 11)
                            .values(2L, "Hub_2", "2016-07-01 15:47:17", "Second Hub", 12)
                            .build(),
                    insertInto(ARTICLE_TABLE)
                            .columns("id", "title", "body", "preview", "creationDate", "keywords", "favorites", "rating",
                                    "views", "author_id", "hub_id")
                            .values(1L, "title_1", "body_1", "preview_1", "2016-07-01 15:47:17", "keyword_1", 1, 11, 12, 1, 1)
                            .values(2L, "title_2", "body_2", "preview_2", "2016-07-01 15:47:17", "keyword_2", 2, 21, 22, 1, 2)
                            .build());

    @Autowired
    private DataSource dataSource;
    @Autowired
    private ArticleRepository articleRepository;

    @Before
    public void setUp() throws Exception {
        new DbSetup(new DataSourceDestination(dataSource), INSERT_DATA).launch();
    }

    @After
    public void tearDown() throws Exception {
        new DbSetup(new DataSourceDestination(dataSource), DELETE_ALL).launch();
    }


    @Test
    public void findOne() {
        final long expectedId = 1;
        assertEquals(expectedId, articleRepository.findOne(1L).getId());
    }

    @Test
    public void findAll() {
        final int expectedSize = 2;

        List<Article> allArticles = new ArrayList<>();
        articleRepository.findAll().forEach(allArticles::add);

        assertEquals(expectedSize, allArticles.size());
    }

    @Test
    public void getAuthor() {
        final String expectedUsername = "user_1";
        assertEquals(expectedUsername, articleRepository.findOne(1L).getAuthor().getUsername());
    }

    @Test
    public void getHub() {
        final String expectedHub = "Hub_2";
        assertEquals(expectedHub, articleRepository.findOne(2L).getHub().getName());
    }
}