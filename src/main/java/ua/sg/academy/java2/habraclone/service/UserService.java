package ua.sg.academy.java2.habraclone.service;

import ua.sg.academy.java2.habraclone.dbModel.entity.User;

import java.util.List;

public interface UserService extends EntityService {

    List<User> getAllUsersSortedAscByRating();

    boolean login(String email, String password);

    boolean register(String username, String email, String password);

}
