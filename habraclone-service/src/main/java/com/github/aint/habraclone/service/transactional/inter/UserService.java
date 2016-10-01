package com.github.aint.habraclone.service.transactional.inter;

import com.github.aint.habraclone.data.model.User;
import com.github.aint.habraclone.service.dto.UserForm;

public interface UserService extends EntityService<User> {

    void register(UserForm userForm);

    void activate(User user);

    User getByUserName(String username);

    User getByEmail(String email);

    int getPositionByRating(User user);

    boolean login(String email, String password);

    void updateLoginTime(String username);

    void incrementArticlesCount(User user);

    void decrementArticlesCount(User user);

    void banUser(User user);

    void unbanUser(User user);

    boolean isBaned(User user);

}
