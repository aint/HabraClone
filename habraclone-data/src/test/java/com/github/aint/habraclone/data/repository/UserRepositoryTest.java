package com.github.aint.habraclone.data.repository;

import com.github.aint.habraclone.data.config.DataSpringConfig;
import com.github.aint.habraclone.data.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:test-db.properties")
@ContextConfiguration(classes = { DataSpringConfig.class })
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test(expected = NullPointerException.class)
    public void findOne() {
        final long expectedId = 1;
        assertEquals(expectedId, userRepository.findOne(expectedId).getId());
    }

    @Test
    public void findAll() {
        final int expectedSize = 0;

        List<User> allArticles = new ArrayList<>();
        userRepository.findAll().forEach(allArticles::add);

        assertEquals(expectedSize, allArticles.size());
    }
}