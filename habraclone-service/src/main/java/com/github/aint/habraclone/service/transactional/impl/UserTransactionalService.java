package com.github.aint.habraclone.service.transactional.impl;

import com.github.aint.habraclone.data.model.User;
import com.github.aint.habraclone.data.model.UserRole;
import com.github.aint.habraclone.data.repository.UserRepository;
import com.github.aint.habraclone.service.dto.UserForm;
import com.github.aint.habraclone.service.transactional.inter.UserService;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserTransactionalService extends EntityTransactionalService<User> implements UserService, UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserTransactionalService.class.getName());

    private static final String ACCOUNT_ACTIVATION_EMAIL = "/accountActivation.vm";

    private final JavaMailSender mailSender;
    private final VelocityEngine velocityEngine;

    @Autowired
    public UserTransactionalService(UserRepository userRepository, JavaMailSender mailSender, VelocityEngine velocityEngine) {
        super(userRepository);
        this.mailSender = mailSender;
        this.velocityEngine = velocityEngine;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        User user = getByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("No such username");
        }
        return user;
    }

    @Override
    public void register(UserForm userForm) {
        User user = dtoToUser(userForm);
        sendAccountActivationMail(user);
        save(user);
    }

    @Override
    public void activate(User user) {
        user.setEnabled(true);
        update(user);
    }

    @Override
    public User getByUserName(String username) {
        return getRepository().findByUsername(username);
    }

    @Override
    public User getByEmail(String email) {
        return getRepository().findByEmail(email);
    }

    @Override
    public int getPositionByRating(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User can't be null");
        }
        return getAllSortedDeskByRating()
                .stream()
                .map(User::getRating)
                .collect(Collectors.toList())
                .indexOf(user.getRating()) + 1;
    }

    @Override
    public boolean login(String email, String password) {
        User user = getRepository().findByEmail(email);
        return user != null && user.getPassword().equalsIgnoreCase(password);
    }

    @Override
    public void updateLoginTime(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username can't be null");
        }
        User user = getByUserName(username);
        user.setLastLoginTime(LocalDateTime.now());
        update(user);
    }

    @Override
    public void incrementArticlesCount(User user) {
        user.setArticlesCount(user.getArticlesCount() + 1);
        update(user);
    }

    @Override
    public void decrementArticlesCount(User user) {
        user.setArticlesCount(user.getArticlesCount() - 1);
        update(user);
    }

    @Override
    public void banUser(User user) {
        user.setBanExpirationDate(LocalDateTime.now().plusDays(5));
        update(user);
    }

    @Override
    public void unbanUser(User user) {
        user.setBanExpirationDate(null);
        update(user);
    }

    @Override
    public boolean isBaned(User user) {
        return user.getBanExpirationDate() != null && LocalDateTime.now().isBefore(user.getBanExpirationDate());
    }

    @Override
    protected UserRepository getRepository() {
        return (UserRepository) repository;
    }

    private User dtoToUser(UserForm userForm) {
        User user = new User();
        user.setUsername(userForm.getUsername());
        user.setEmail(userForm.getEmail());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(userForm.getPassword()));
        user.setFullName(userForm.getFullName());
        user.setLastLoginTime(LocalDateTime.now());
        user.setRegistrationDate(LocalDateTime.now());
        UserRole role = new UserRole("ROLE_USER", user);
//        save(role);
        user.setRoles(new HashSet<>(Collections.singletonList(role)));
        return user;
    }


    private void sendAccountActivationMail(User user) {
        Template veloTemp = velocityEngine.getTemplate(ACCOUNT_ACTIVATION_EMAIL, "UTF-8");
        StringWriter sw = new StringWriter();
        veloTemp.merge(getTemplate(user.getUsername()), sw);

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(user.getEmail());
            helper.setSubject("Activation");
            helper.setText(sw.toString(), true);
            sw.close();
        } catch (MessagingException | IOException e) {
            logger.error("Emailing error", e);
        }
        mailSender.send(message);
    }

    private VelocityContext getTemplate(String username) {
        VelocityContext veloContext = new VelocityContext();
        veloContext.put("application_name", "Habra-Clone");
        veloContext.put("account_activation_subject", "Habra-Clone account activation");
        veloContext.put("greeting", "Hello!");
        veloContext.put("account_activation_content_part1", "This mail is to confirm your registration at Habra-Clone.");
        veloContext.put("account_activation_content_part2", "Please follow the link below to activate your account.");
        veloContext.put("account_activation_content_part3", "Activation link: <a href=\"" + "http://localhost:9090/users/" + username + "/activate" + "\">activate</a>");
        veloContext.put("account_activation_content_part4", "Also note, that account will be deleted automatically in 24 hours if not activated.");
        veloContext.put("account_activation_content_part5", "If you were not registered at our site ignore this mail.");
        veloContext.put("wish", "Best regards, Habra-Clone administration.");
        return veloContext;
    }

}
