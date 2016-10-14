package com.github.aint.habraclone.service.transactional.impl;

import com.github.aint.habraclone.data.model.User;
import com.github.aint.habraclone.data.repository.UserRepository;
import com.github.aint.habraclone.service.dto.UserForm;
import com.github.aint.habraclone.service.security.AuthenticationService;
import org.apache.velocity.app.VelocityEngine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserTransactionalServiceTest {

    private static final String USER_USERNAME = "username-1";
    private static final String USER_EMAIL = "email-1@mail.com";
    private static final String USER_PASSWORD = "123456";

    @Mock
    UserRepository userRepository;
    @Mock
    AuthenticationService authenticationService;
    @Mock
    JavaMailSender mailSender;
    @Mock
    VelocityEngine velocityEngine;

    private UserTransactionalService userService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        userService = new UserTransactionalService(userRepository, authenticationService, mailSender, velocityEngine);
    }



    @Test
    public void loadUserByUsername()  {
        User expectedUser = new User(USER_EMAIL, USER_USERNAME, USER_PASSWORD);
        when(userRepository.findByUsername(USER_USERNAME)).thenReturn(expectedUser);
        assertEquals(expectedUser, userService.loadUserByUsername(USER_USERNAME));
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameNotFound() {
        userService.loadUserByUsername(null);
    }

    @Test
    public void getByEmail() {
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(new User(USER_EMAIL, USER_USERNAME, USER_PASSWORD));
        User user = userService.getByEmail(USER_EMAIL).orElse(null);
        assertNotNull(user);
    }

    @Test
    public void getByEmailNotFound() {
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(new User(USER_EMAIL, USER_USERNAME, USER_PASSWORD));
        Optional<User> user = userService.getByEmail("some-mail@mail.com");
        assertFalse(user.isPresent());
    }

    @Test
    public void getByEmailNull() {
        assertNull(userService.getByEmail(USER_EMAIL).orElse(null));
    }

    @Test
    public void activate() {
        User user = new User(USER_EMAIL, USER_USERNAME, USER_PASSWORD);
        userService.activate(user);
        assertTrue(user.isEnabled());
    }

    @Test
    public void incrementArticlesCount() {
        final int articlesCount = 42;
        User principal = new User(USER_EMAIL, USER_USERNAME, USER_PASSWORD);
        principal.setArticlesCount(articlesCount);
        when(authenticationService.getPrincipal()).thenReturn(principal);

        userService.incrementArticlesCount();
        assertEquals(principal.getArticlesCount(), articlesCount + 1);
    }

    @Test
    public void decrementArticlesCount() {
        final int articlesCount = 42;
        User principal = new User(USER_EMAIL, USER_USERNAME, USER_PASSWORD);
        principal.setArticlesCount(articlesCount);
        when(authenticationService.getPrincipal()).thenReturn(principal);

        userService.decrementArticlesCount();
        assertEquals(principal.getArticlesCount(), articlesCount - 1);
    }

    @Test
    public void updateLoginTime() {
        User principal = new User(USER_EMAIL, USER_USERNAME, USER_PASSWORD);
        principal.setLastLoginTime(LocalDateTime.now().minusMonths(42).minusDays(11));
        when(authenticationService.getPrincipal()).thenReturn(principal);

        userService.updateLoginTime();
        assertTrue(principal.getLastLoginTime().isAfter(LocalDateTime.now().minusSeconds(3)));
    }

    @Test
    public void banUser() {
        User user = new User(USER_EMAIL, USER_USERNAME, USER_PASSWORD);
        userService.banUser(user);
        assertTrue(user.getBanExpirationDate().isAfter(LocalDateTime.now().minusSeconds(3)));
    }

    @Test
    public void unbanUser() {
        User user = new User(USER_EMAIL, USER_USERNAME, USER_PASSWORD);
        user.setBanExpirationDate(LocalDateTime.of(2042, 1, 1, 1, 1));

        userService.unbanUser(user);
        assertNull(user.getBanExpirationDate());
    }

    @Test
    public void isBaned() {
        User user = new User(USER_EMAIL, USER_USERNAME, USER_PASSWORD);
        user.setBanExpirationDate(LocalDateTime.of(2042, 1, 1, 1, 1));

        assertTrue(userService.isBaned(user));
    }

    @Test
    public void isBanedExpire() {
        User user = new User(USER_EMAIL, USER_USERNAME, USER_PASSWORD);
        user.setBanExpirationDate(LocalDateTime.of(2010, 1, 1, 1, 1));

        assertFalse(userService.isBaned(user));
    }

    @Test
    public void isBanedNull() {
        assertFalse(userService.isBaned(new User(USER_EMAIL, USER_USERNAME, USER_PASSWORD)));
    }

    @Test
    public void getPositionByRating() {
        User user1 = new User(USER_EMAIL, USER_USERNAME, USER_PASSWORD);
        user1.setRating(42);
        User user2 = new User("email-2@mail.com", "username-2", "password");
        user1.setRating(14);
        User user3 = new User("email-3@mail.com", "username-3", "password");
        user1.setRating(-11);

        UserTransactionalService userService = spy(this.userService);
        when(userService.getAllSortedDeskByRating()).thenReturn(Arrays.asList(user1, user2, user3));

        assertEquals(2, userService.getPositionByRating(user2));
    }

    @Test
    public void getPositionByRatingWhenNoUser() {
        User user = new User(USER_EMAIL, USER_USERNAME, USER_PASSWORD);

        UserTransactionalService userService = spy(this.userService);
        when(userService.getAllSortedDeskByRating()).thenReturn(Collections.emptyList());

        assertEquals(0, userService.getPositionByRating(user));
    }

    @Test
    public void login() {
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(new User(USER_EMAIL, USER_USERNAME, USER_PASSWORD));
        assertTrue(userService.login(USER_EMAIL, USER_PASSWORD));
    }

    @Test
    public void loginWrongPassword() {
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(new User(USER_EMAIL, USER_USERNAME, USER_PASSWORD));
        assertFalse(userService.login(USER_EMAIL, "password"));
    }

    @Test
    public void loginWrongEmail() {
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(new User(USER_EMAIL, USER_USERNAME, USER_PASSWORD));
        assertFalse(userService.login("some-mail@mail.com", USER_PASSWORD));
    }

//    @Test
    public void register() {
        UserForm userForm = new UserForm();
        userForm.setEmail(USER_EMAIL);
        userForm.setUsername(USER_USERNAME);
        userForm.setPassword(USER_PASSWORD);

        userService.register(userForm);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        assertEquals(USER_EMAIL, userCaptor.getValue().getEmail());
        assertEquals(USER_USERNAME, userCaptor.getValue().getUsername());
        assertEquals(USER_PASSWORD, userCaptor.getValue().getPassword());
    }


}