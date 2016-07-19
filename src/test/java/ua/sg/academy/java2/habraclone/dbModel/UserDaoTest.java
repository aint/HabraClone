package ua.sg.academy.java2.habraclone.dbModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.sg.academy.java2.habraclone.dao.UserDao;
import ua.sg.academy.java2.habraclone.model.User;
import ua.sg.academy.java2.habraclone.dbModel.util.H2ConnectorFactory;
import ua.sg.academy.java2.habraclone.dbModel.util.HibernateDaoFactory;
import ua.sg.academy.java2.habraclone.dbModel.util.TestHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static ua.sg.academy.java2.habraclone.dbModel.util.TestHelper.SELECT_COUNT_FROM_USER;

public class UserDaoTest {

    private Connection connection = null;
    private UserDao userDao;

    @Before
    public void init() throws SQLException {
        connection = H2ConnectorFactory.getInstance();
        userDao = HibernateDaoFactory.getUserDao();

        HibernateDaoFactory.beginTransaction();
    }

    @After
    public void clean() {
        TestHelper.clean();
    }

    @Test(expected = IllegalArgumentException.class)
    public void addNull() {
        userDao.save(null);
        HibernateDaoFactory.commitTransaction();
    }

    @Test
    public void addUser() throws SQLException {
        userDao.save(new User("email@mail.com", "user", "111111"));
        HibernateDaoFactory.commitTransaction();

        try (ResultSet rs = connection.createStatement().executeQuery(SELECT_COUNT_FROM_USER)) {
            assertEquals(1, rs.next() ? rs.getInt(1) : 0);
        }
    }

    @Test
    public void addUserCollection() throws SQLException {
        List<User> list = Arrays.asList(new User("email@mail.com", "user", "111111"),
                new User("email@mail.com", "user", "111111"));
        for (User u : list) {
            userDao.save(u);
        }
        HibernateDaoFactory.commitTransaction();

        try (ResultSet rs = connection.createStatement().executeQuery(SELECT_COUNT_FROM_USER)) {
            assertEquals(2, rs.next() ? rs.getInt(1) : 0);
        }
    }


}
