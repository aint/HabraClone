package ua.sg.academy.java2.habraclone.service.transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.sg.academy.java2.habraclone.dbModel.dao.UserDao;
import ua.sg.academy.java2.habraclone.dbModel.entity.User;
import ua.sg.academy.java2.habraclone.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TransactionalUserService extends TransactionalEntityService implements UserService {

    @Autowired
    public TransactionalUserService(UserDao userDao) {
        super(userDao);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsersSortedAscByRating() {
        List<User> users = (List<User>) getDao().getAll();
        users.sort((u1, u2) -> u2.getRating() - u1.getRating());
        return users;
    }

    @Override
    public boolean login(String email, String password) {
        User user = getDao().getByEmail(email);
        return user != null && user.getPassword().equalsIgnoreCase(password);
    }

    @Override
    public boolean register(String username, String email, String password) {
        if (getDao().getByEmail(email) == null) {
            return false;
        }
        getDao().save(new User(email, username, password, LocalDateTime.now(), LocalDateTime.now()));
        return true;
    }

    @Override
    protected UserDao getDao() {
        return (UserDao) dao;
    }

}
