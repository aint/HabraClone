package com.github.aint.habraclone.data.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import com.github.aint.habraclone.data.dao.CommentDao;
import com.github.aint.habraclone.data.model.Article;
import com.github.aint.habraclone.data.model.Comment;
import com.github.aint.habraclone.data.model.User;
import com.github.aint.habraclone.service.transactional.ArticleService;
import com.github.aint.habraclone.service.transactional.CommentService;
import com.github.aint.habraclone.service.transactional.impl.CommentTransactionalService;
import com.github.aint.habraclone.service.transactional.UserService;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CommentServiceTest {

    private static final Long COMMENT_ID = 1L;
    private static final int COMMENT_RATING = 0;

    @Mock
    private CommentDao commentDao;
    @Mock
    private ArticleService articleService;
    @Mock
    private UserService userService;
    private CommentService commentService;

    @Before
    public void init() throws SQLException {
        initMocks(this);
        commentService = new CommentTransactionalService(commentDao, articleService, userService);
    }

    @After
    public void clean() {

    }

    @Test
    public void add() {
        final Comment comment = getComment();
        commentService.save(comment);

        assertEquals(comment.getRating(), 0);
        assertNotNull(comment.getAuthor());
        assertNotNull(comment.getArticle());
        assertNotNull(comment.getBody());

        verify(commentDao).save(comment);
    }

    @Test
    public void getById() {
        final Comment expected = getComment();
        when(commentDao.getById(COMMENT_ID)).thenReturn(expected);

        assertEquals(commentService.getById(COMMENT_ID), expected);

        verify(commentDao).getById(COMMENT_ID);
    }

    @Test
    public void getNotFound() {
        when(commentDao.getById(COMMENT_ID)).thenReturn(null);

        assertEquals(commentService.getById(COMMENT_ID), null);

        commentService.getById(COMMENT_ID);
    }

    @Test(expected =  IllegalArgumentException.class)
    public void getByIdNull() {
        commentService.getById(null);
    }

    private Comment getComment() {
        Comment comment = new Comment("Comment", getUser(), getArticle());
        comment.setId(COMMENT_ID);
        comment.setCreationDate(LocalDateTime.now());
        comment.setRating(COMMENT_RATING);
        return comment;
    }

    private Article getArticle() {
        Article article = new Article("title", "preview", "body", "keywords");
        article.setCreationDate(LocalDateTime.now());
        article.setAuthor(getUser());
        return article;
    }

    private User getUser() {
        return new User("userName", "user@gmail.com", "password");
    }

}
