package ua.sg.academy.java2.habraclone.service.transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.sg.academy.java2.habraclone.dbModel.dao.UserDao;
import ua.sg.academy.java2.habraclone.dbModel.entity.User;
import ua.sg.academy.java2.habraclone.dbModel.entity.UserRole;
import ua.sg.academy.java2.habraclone.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransactionalUserService extends TransactionalEntityService implements UserService, UserDetailsService {

    @Autowired
    public TransactionalUserService(UserDao userDao) {
        super(userDao);
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        User user = getByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("No such username");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                user.isActivated(),
                true, true, true,
                buildUserAuthority(user.getRoles()));
    }

    private Set<GrantedAuthority> buildUserAuthority(Set<UserRole> roles) {
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority(r.getRole()))
                .collect(Collectors.toSet());
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsersSortedAscByRating() {
        List<User> users = (List<User>) getDao().getAll();
        users.sort((u1, u2) -> u2.getRating() - u1.getRating());
        return users;
    }

    @Override
    public User getByUserName(String username) {
        return getDao().getByUserName(username);
    }

    @Override
    public User getByEmail(String email) {
        return getDao().getByEmail(email);
    }

    @Override
    public int getPositionByRating(User user) {
        return getAllUsersSortedAscByRating()
                .stream()
                .map(User::getRating)
                .collect(Collectors.toList())
                .indexOf(user.getRating()) + 1;
    }

    @Override
    public boolean login(String email, String password) {
        User user = getDao().getByEmail(email);
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
    protected UserDao getDao() {
        return (UserDao) dao;
    }

}
