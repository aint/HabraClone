package com.github.aint.habraclone.data.repository;

import com.github.aint.habraclone.data.config.DataSpringConfig;
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
public class UserRepositoryTest {

    private static final String USER_TABLE = User.class.getSimpleName();

    private static final Operation DELETE_ALL = sequenceOf(deleteAllFrom(USER_TABLE));
    private static final Operation INSERT_DATA =
            sequenceOf(
                    insertInto(USER_TABLE)
                            .columns("id", "email", "username", "password", "lastLoginTime", "registrationDate",
                                    "enabled", "admin", "banExpirationDate", "birthday", "rating", "about", "fullName",
                                    "articlesCount", "commentsCount",  "favoritesCount", "language", "location")
                            .values(1L, "user1@gmail.com", "user_1", "111111", "2010-01-01 11:11:11", "2016-01-01 15:47:17",
                                    true, true, null, null, 0, "about", "first user",
                                    2, 0, 1, null, null)
                            .build());

    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserRepository userRepository;

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
        assertEquals(expectedId, userRepository.findOne(1L).getId());
    }

    @Test
    public void findAll() {
        final int expectedSize = 1;

        List<User> allUsers = new ArrayList<>();
        userRepository.findAll().forEach(allUsers::add);

        assertEquals(expectedSize, allUsers.size());
    }
}