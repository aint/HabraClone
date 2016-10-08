package com.github.aint.habraclone.service.transactional;

import com.github.aint.habraclone.data.model.User;
import com.github.aint.habraclone.service.dto.UserForm;

import java.util.Optional;

public interface UserService extends EntityService<User> {

    void register(UserForm userForm);

    void activate(User user);

    Optional<User> getByUserName(String username);

    Optional<User> getByEmail(String email);

    int getPositionByRating(User user);

    boolean login(String email, String password);

    void updateLoginTime();

    void incrementArticlesCount();

    void decrementArticlesCount();

    void banUser(User user);

    void unbanUser(User user);

    boolean isBaned(User user);

}
