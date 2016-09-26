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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.operation.CompositeOperation.sequenceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:test-db.properties")
@ContextConfiguration(classes = { DataSpringConfig.class })
public class UserRepositoryTest {

    private static final String USER_TABLE = User.class.getSimpleName();

    private static final Operation DELETE_ALL = sequenceOf(deleteAllFrom(USER_TABLE));
    private static final Operation INSERT_DATA = sequenceOf(
            insertInto(USER_TABLE)
                    .columns("id", "email", "username", "password", "lastLoginTime", "registrationDate",
                            "enabled", "admin", "banExpirationDate", "birthday", "rating", "about", "fullName",
                            "articlesCount", "commentsCount",  "favoritesCount", "language", "location")
                    .values(1L, "user1@gmail.com", "user_1", "111111", "2010-01-01 11:11:11", "2016-01-01 15:47:17",
                            true, true, null, null, 0, "about", "first user",
                            2, 0, 1, null, null)
                    .values(2L, "user2@gmail.com", "user_2", "111111", "2012-02-02 22:22:22", "2012-02-02 12:22:12",
                            false, false, null, null, 0, "about", "second user",
                            2, 2, 2, null, null)
                    .build());

    @Autowired
    private DataSource dataSource;
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
    public void findByUsername() {
        assertNotNull(userRepository.findByUsername("user_1"));
    }

    @Test
    public void findByUsernameNull() {
        assertNull(userRepository.findByUsername(null));
    }

    @Test
    public void findByUsernameNotFound() {
        assertNull(userRepository.findByUsername("user_42"));
    }

    @Test
    public void findByEmail() {
        assertNotNull(userRepository.findByEmail("user1@gmail.com"));
    }

    @Test
    public void findByEmailNull() {
        assertNull(userRepository.findByEmail(null));
    }

    @Test
    public void findByEmailNotFound() {
        assertNull(userRepository.findByEmail("user42@gmail.com"));
    }

    @Test
    public void findByEnabledIsFalse() {
        final long expectedSize = 1;
        assertEquals(expectedSize, userRepository.findByEnabledIsFalse().size());
    }

    /*===== Common methods =====*/

    @Test
    public void findOne() {
        final long expectedId = 1;
        assertEquals(expectedId, userRepository.findOne(1L).getId());
    }

    @Test
    public void findOneNotFound() {
        assertNull(userRepository.findOne(-42L));
    }

    @Test
    public void findAll() {
        final int expectedSize = 2;

        List<User> allUsers = new ArrayList<>();
        userRepository.findAll().forEach(allUsers::add);

        assertEquals(expectedSize, allUsers.size());
    }

    @Test
    public void save() {
        User user = new User("test@gmail.com", "new_user", "123456", LocalDateTime.now(), LocalDateTime.now());
        assertNotNull(userRepository.save(user));
    }

    @Test
    public void update() {
        final long userId = 1L;
        final String expectedUsername = "new_username";

        User user = userRepository.findOne(userId);
        user.setUsername(expectedUsername);
        userRepository.save(user);

        assertEquals(expectedUsername, userRepository.findOne(userId).getUsername());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveUsersWithSameEmail() {
        String email = "test@gmail.com";
        userRepository.save(new User(email, "user_2", "123456", LocalDateTime.now(), LocalDateTime.now()));
        userRepository.save(new User(email, "user_3", "123456", LocalDateTime.now(), LocalDateTime.now()));
    }

    @Test
    public void delete() {
        final long userId = 1L;
        userRepository.delete(userId);
        assertNull(userRepository.findOne(userId));
    }
}
