package ua.sg.academy.java2.habraclone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.sg.academy.java2.habraclone.dbModel.dao.UserDao;
import ua.sg.academy.java2.habraclone.dbModel.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        return (List<User>) userDao.getAll();
    }

    public boolean login(String email, String password) {
        User user = userDao.getByEmail(email);
        return user != null && user.getPassword().equalsIgnoreCase(password);
    }

    public boolean register(String username, String email, String password) {
        if (userDao.getByEmail(email) == null) {
            return false;
        }
        userDao.save(new User(email, username, password, LocalDateTime.now(), LocalDateTime.now()));
        return true;
    }

}
