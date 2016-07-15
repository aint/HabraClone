package ua.sg.academy.java2.habraclone.dbModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.sg.academy.java2.habraclone.dbModel.dao.ArticleDao;
import ua.sg.academy.java2.habraclone.dbModel.entity.Article;
import ua.sg.academy.java2.habraclone.dbModel.util.H2ConnectorFactory;
import ua.sg.academy.java2.habraclone.dbModel.util.HibernateDaoFactory;
import ua.sg.academy.java2.habraclone.dbModel.util.TestHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static ua.sg.academy.java2.habraclone.dbModel.util.TestHelper.*;

public class ArticleDaoTest {

    private Connection connection = null;
    private ArticleDao articleDao;

    @Before
    public void init() throws SQLException {
        connection = H2ConnectorFactory.getInstance();
        articleDao = HibernateDaoFactory.getArticleDao();

        HibernateDaoFactory.beginTransaction();
    }

    @After
    public void clean() {
        TestHelper.clean();
    }

    @Test(expected = IllegalArgumentException.class)
    public void addNull() {
        articleDao.save(null);
        HibernateDaoFactory.commitTransaction();
    }

    @Test
    public void addAcademy() throws SQLException {
        articleDao.save(new Article("Title", "Preview", "Body", "Keywords"));
        HibernateDaoFactory.commitTransaction();

        try (ResultSet rs = connection.createStatement().executeQuery(SELECT_COUNT_FROM_ARTICLE)) {
            assertEquals(1, rs.next() ? rs.getInt(1) : 0);
        }
    }

    @Test
    public void addAcademiesCollection() throws SQLException {
        List<Article> list = Arrays.asList(new Article("Title1", "Preview", "Body", "Keywords"),
                new Article("Title2", "Preview", "Body", "Keywords"));
        for (Article a : list) {
            articleDao.save(a);
        }
        HibernateDaoFactory.commitTransaction();

        try (ResultSet rs = connection.createStatement().executeQuery(SELECT_COUNT_FROM_ARTICLE)) {
            assertEquals(2, rs.next() ? rs.getInt(1) : 0);
        }
    }


}
