package ua.sg.academy.java2.habraclone.service;

import ua.sg.academy.java2.habraclone.dbModel.entity.User;

import java.util.List;

public interface UserService extends EntityService {

    List<User> getAllUsersSortedAscByRating();

    User getByUserName(String username);

    User getByEmail(String email);

    int getPositionByRating(User user);

    boolean login(String email, String password);

    boolean register(String username, String email, String password);

}
