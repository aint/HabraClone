package com.github.aint.habraclone.data.dbModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.github.aint.habraclone.data.dao.CommentDao;
import com.github.aint.habraclone.data.model.Article;
import com.github.aint.habraclone.data.model.Comment;
import com.github.aint.habraclone.data.model.User;
import com.github.aint.habraclone.data.dbModel.util.H2ConnectorFactory;
import com.github.aint.habraclone.data.dbModel.util.HibernateDaoFactory;
import com.github.aint.habraclone.data.dbModel.util.TestHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static com.github.aint.habraclone.data.dbModel.util.TestHelper.SELECT_COUNT_FROM_USER;

public class CommentDaoTest {

    private Connection connection = null;
    private CommentDao commentDao;

    @Before
    public void init() throws SQLException {
        connection = H2ConnectorFactory.getInstance();
        commentDao = HibernateDaoFactory.getCommentDao();

        HibernateDaoFactory.beginTransaction();
    }

    @After
    public void clean() {
        TestHelper.clean();
    }

    @Test(expected = IllegalArgumentException.class)
    public void addNull() {
        commentDao.save(null);
        HibernateDaoFactory.commitTransaction();
    }

    @Test
    public void addComment() throws SQLException {
        commentDao.save(new Comment("Comment", getUser(), getArticle()));
        HibernateDaoFactory.commitTransaction();

        try (ResultSet rs = connection.createStatement().executeQuery(SELECT_COUNT_FROM_USER)) {
            assertEquals(1, rs.next() ? rs.getInt(1) : 0);
        }
    }

    @Test
    public void addUserCollection() throws SQLException {
        List<Comment> list = Arrays.asList(new Comment("Comment", getUser(), getArticle()),
                new Comment("Comment", getUser(), getArticle()));
        for (Comment comment : list) {
            commentDao.save(comment);
        }
        HibernateDaoFactory.commitTransaction();

        try (ResultSet rs = connection.createStatement().executeQuery(SELECT_COUNT_FROM_USER)) {
            assertEquals(2, rs.next() ? rs.getInt(1) : 0);
        }
    }

    private Article getArticle() {
        return new Article("Title", "Preview", "Body", "Keywords");
    }

    private User getUser() {
        return new User("email@mail.com", "user", "111111");
    }


}
