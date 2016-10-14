package com.github.aint.habraclone.service.transactional.impl;

import com.github.aint.habraclone.data.model.User;
import com.github.aint.habraclone.data.repository.UserRepository;
import com.github.aint.habraclone.service.dto.UserForm;
import com.github.aint.habraclone.service.security.AuthenticationService;
import com.github.aint.habraclone.service.transactional.UserService;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserTransactionalService extends AbstractEntityTransactionalService<User> implements UserService, UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserTransactionalService.class.getName());

    private static final String ACCOUNT_ACTIVATION_EMAIL = "/accountActivation.vm";

    private final AuthenticationService authenticationService;
    private final JavaMailSender mailSender;
    private final VelocityEngine velocityEngine;

    @Autowired
    public UserTransactionalService(UserRepository userRepository, AuthenticationService authenticationService,
                                    JavaMailSender mailSender, VelocityEngine velocityEngine) {
        super(userRepository);
        this.authenticationService = authenticationService;
        this.mailSender = mailSender;
        this.velocityEngine = velocityEngine;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return getByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User=%s not found", username)));
    }

//    TODO inject MailService 'cause now this method is untestable
    @Override
    public void register(UserForm userForm) {
        User user = dtoToUser(userForm);
        sendAccountActivationMail(user);
        save(user);
    }

    @Override
    public void activate(User user) {
        user.setEnabled(true);
        save(user);
    }

    @Override
    public Optional<User> getByUserName(String username) {
        return Optional.ofNullable(getRepository().findByUsername(username));
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return Optional.ofNullable(getRepository().findByEmail(email));
    }

    @Override
    public int getPositionByRating(User user) {
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
    public void updateLoginTime() {
        User user = authenticationService.getPrincipal();
        user.setLastLoginTime(LocalDateTime.now());
        save(user);
    }

    @Override
    public void incrementArticlesCount() {
        User user = authenticationService.getPrincipal();
        user.setArticlesCount(user.getArticlesCount() + 1);
        save(user);
    }

    @Override
    public void decrementArticlesCount() {
        User user = authenticationService.getPrincipal();
        user.setArticlesCount(user.getArticlesCount() - 1);
        save(user);
    }

    @Override
    public void banUser(User user) {
        user.setBanExpirationDate(LocalDateTime.now().plusDays(5));
        save(user);
    }

    @Override
    public void unbanUser(User user) {
        user.setBanExpirationDate(null);
        save(user);
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
