package com.github.aint.habraclone.data.repository;

import com.github.aint.habraclone.data.config.DataSpringConfig;
import com.github.aint.habraclone.data.model.Article;
import com.github.aint.habraclone.data.model.Hub;
import com.github.aint.habraclone.data.model.Role;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.generator.ValueGenerators.dateSequence;
import static com.ninja_squad.dbsetup.generator.ValueGenerators.sequence;
import static com.ninja_squad.dbsetup.generator.ValueGenerators.stringSequence;
import static com.ninja_squad.dbsetup.operation.CompositeOperation.sequenceOf;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.util.Comparator.reverseOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:test-db.properties")
@ContextConfiguration(classes = { DataSpringConfig.class })
public class ArticleRepositoryTest {

    private static final String ARTICLE_TABLE = Article.class.getSimpleName();
    private static final String HUB_TABLE = Hub.class.getSimpleName();
    private static final String USER_TABLE = User.class.getSimpleName();

    private static final int ARTICLES_COUNT = 20;

    private static final Operation DELETE_ALL = sequenceOf(
            deleteAllFrom(ARTICLE_TABLE, HUB_TABLE, USER_TABLE));
    private static final Operation INSERT_DATA = sequenceOf(
            insertInto(USER_TABLE)
                    .columns("id", "email", "username", "password", "lastLoginTime", "registrationDate",
                             "enabled", "admin", "banExpirationDate", "birthday", "rating", "about", "fullName",
                             "articlesCount", "commentsCount",  "favoritesCount", "language", "location", "role")
                    .values(1L, "user1@gmail.com", "user_1", "111111", "2010-01-01 11:11:11", "2016-01-01 15:47:17",
                            true, true, null, null, 0, "about", "first user",
                            2, 0, 1, null, null, Role.USER)
                    .values(2L, "user2@gmail.com", "user_2", "222222", "2012-02-02 12:12:12", "2016-02-02 15:47:17",
                            true, false, null, null, 0, "about", "second user",
                            0, 0, 0, null, null, Role.USER)
                    .build(),
            insertInto(HUB_TABLE)
                    .columns("id", "name", "creationDate", "description",  "rating")
                    .values(1L, "Hub_1", "2016-07-01 15:47:17", "First Hub", 11)
                    .values(2L, "Hub_2", "2016-07-01 15:47:17", "Second Hub", 12)
                    .build(),
            insertInto(ARTICLE_TABLE)
                    .withGeneratedValue("id", sequence().startingAt(1L))
                    .withGeneratedValue("rating", sequence().startingAt(-10))
                    .withGeneratedValue("title", stringSequence("title-").startingAt(1L))
                    .withGeneratedValue("body", stringSequence("body-").startingAt(1L))
                    .withGeneratedValue("preview", stringSequence("preview-").startingAt(1L))
                    .withGeneratedValue("keywords", stringSequence("keywords-").startingAt(1L))
                    .withGeneratedValue("creationDate", dateSequence().startingAt(LocalDate.parse("2012-01-10")).incrementingBy(3, MONTHS))
                    .columns("favorites", "views", "author_id", "hub_id")
                    .repeatingValues(11, 22, 1, 2).times(ARTICLES_COUNT)
                    .build());

    @Autowired
    private DataSource dataSource;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        new DbSetup(new DataSourceDestination(dataSource), sequenceOf(DELETE_ALL, INSERT_DATA)).launch();
    }
    @After
    public void tearDown() throws Exception {
        new DbSetup(new DataSourceDestination(dataSource), DELETE_ALL).launch();
    }


    @Test
    public void findByOrderByRatingDesc() {
        List<Integer> actualRatings = articleRepository.findByOrderByRatingDesc().stream()
                .map(Article::getRating)
                .collect(Collectors.toList());
        List<Integer> expectedRatings = IntStream.range(-10, 10)
                .boxed()
                .sorted(reverseOrder())
                .collect(Collectors.toList());
        assertEquals(expectedRatings, actualRatings);
    }

    @Test
    public void findTop10ByOrderByRatingDesc() {
        List<Integer> actualRatings = articleRepository.findTop10ByOrderByRatingDesc().stream()
                .map(Article::getRating)
                .collect(Collectors.toList());
        List<Integer> expectedRatings = IntStream.range(0, 10)
                .boxed()
                .sorted(reverseOrder())
                .collect(Collectors.toList());
        assertEquals(expectedRatings, actualRatings);
    }

    @Test
    public void findByAuthor() {
        final User author = userRepository.findOne(1L);
        assertFalse(articleRepository.findByAuthor(author).isEmpty());
    }

    @Test
    public void findByAuthorNull() {
        assertTrue(articleRepository.findByAuthor(null).isEmpty());
    }

    @Test
    public void findByAuthorNotFound() {
        final User author = userRepository.findOne(2L);
        assertTrue(articleRepository.findByAuthor(author).isEmpty());
    }

    @Test
    public void findLatestArticleOfUser() {
        final User author = userRepository.findOne(1L);
        assertEquals(ARTICLES_COUNT, articleRepository.findLatestArticleOfUser(author).getId());
    }

    @Test
    public void findLatestArticleOfUserNull() {
        assertNull(articleRepository.findLatestArticleOfUser(null));
    }

    @Test
    public void findLatestArticleOfUserNotFound() {
        final User author = userRepository.findOne(2L);
        assertNull(articleRepository.findLatestArticleOfUser(author));
    }

    /*===== Common methods =====*/

    @Test
    public void findOne() {
        final long expectedId = 1;
        assertEquals(expectedId, articleRepository.findOne(1L).getId());
    }

    @Test
    public void findAll() {
        List<Article> allArticles = new ArrayList<>();
        articleRepository.findAll().forEach(allArticles::add);

        assertEquals(ARTICLES_COUNT, allArticles.size());
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