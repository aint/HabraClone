package ua.sg.academy.java2.habraclone.service.transactional;

import ua.sg.academy.java2.habraclone.model.User;
import ua.sg.academy.java2.habraclone.webController.dto.UserForm;

public interface UserService extends EntityService {

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
