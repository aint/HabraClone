package ua.sg.academy.java2.habraclone.service;

import org.apache.velocity.app.VelocityEngine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.mail.javamail.JavaMailSender;
import ua.sg.academy.java2.habraclone.dao.UserDao;
import ua.sg.academy.java2.habraclone.model.Article;
import ua.sg.academy.java2.habraclone.model.User;
import ua.sg.academy.java2.habraclone.service.transactional.UserService;
import ua.sg.academy.java2.habraclone.service.transactional.impl.UserTransactionalService;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceTest {

    private static final Long USER_ID = 1L;
    private static final Long ARTICLE_ID = 1L;
    private static final int ARTICLE_RATING = 0;

    @Mock
    private UserDao userDao;
    @Mock
    private JavaMailSender mailSender;
    @Mock
    private VelocityEngine velocityEngine;

    private UserService userService;

    @Before
    public void init() throws SQLException {
        initMocks(this);
        userService = new UserTransactionalService(userDao, mailSender, velocityEngine);
    }

    @After
    public void clean() {

    }

    @Test
    public void add() {
        final User user = getUser();
        userService.save(user);

        assertNotNull(user.getUsername());
        assertNotNull(user.getEmail());
        assertNotNull(user.getPassword());
        assertNotNull(user.getRegistrationDate());
        assertNotNull(user.getLastLoginTime());
        assertEquals(user.getRating(), 0);

        verify(userDao).save(user);
    }

    @Test
    public void activateUser() {
        final User user = getUser();
        userService.activate(user);

        assertEquals(user.isEnabled(), true);
    }

    @Test
    public void banUser() {
        final User user = getUser();
        userService.banUser(user);

        assertNotNull(user.getBanExpirationDate());
    }

    @Test
    public void isUserBanned() {
        final User bannedUser = getUser();
        bannedUser.setBanExpirationDate(LocalDateTime.now().plusDays(2));

        assertEquals(userService.isBaned(bannedUser), true);
    }

    @Test
    public void isUserBannedFalse() {
        final User bannedUser = getUser();
        bannedUser.setBanExpirationDate(LocalDateTime.now().minusDays(2));

        assertEquals(userService.isBaned(bannedUser), false);
    }

    @Test
    public void getByUserName() {
        final User expected = getUser();
        when(userDao.getByUserName("userName")).thenReturn(expected);

        assertEquals(userService.getByUserName("userName"), expected);

        verify(userDao).getByUserName("userName");
    }

    @Test
    public void getByUserNameNotFound() {
        when(userDao.getByUserName("")).thenReturn(null);

        assertEquals(userService.getByUserName(""), null);

        verify(userDao).getByUserName("");
    }

    @Test
    public void getNotFound() {
        when(userDao.getById(USER_ID)).thenReturn(null);

        assertEquals(userService.getById(USER_ID), null);

        userService.getById(USER_ID);
    }

    @Test
    public void getById() {
        final User expected = getUser();
        when(userDao.getById(USER_ID)).thenReturn(expected);

        assertEquals(userService.getById(USER_ID), expected);

        verify(userDao).getById(USER_ID);
    }

    @Test(expected =  IllegalArgumentException.class)
    public void getByIdNull() {
        userService.getById(null);
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
        User user = new User("userName", "user@gmail.com", "password");
        user.setId(USER_ID);
        user.setRegistrationDate(LocalDateTime.now());
        user.setLastLoginTime(LocalDateTime.now());
        return user;
    }

}
