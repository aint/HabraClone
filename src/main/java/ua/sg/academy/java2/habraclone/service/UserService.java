package ua.sg.academy.java2.habraclone.service;

import ua.sg.academy.java2.habraclone.dbModel.entity.User;
import ua.sg.academy.java2.habraclone.webController.dto.UserForm;

import java.util.List;

public interface UserService extends EntityService {

    void register(UserForm userForm);

    void activate(User user);

    List<User> getAllUsersSortedAscByRating();

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
