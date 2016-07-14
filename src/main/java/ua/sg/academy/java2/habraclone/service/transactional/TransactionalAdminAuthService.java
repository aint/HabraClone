package ua.sg.academy.java2.habraclone.service.transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ua.sg.academy.java2.habraclone.dbModel.entity.User;
import ua.sg.academy.java2.habraclone.dbModel.entity.UserRole;
import ua.sg.academy.java2.habraclone.service.UserService;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TransactionalAdminAuthService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public TransactionalAdminAuthService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {;
        User user = userService.getByUserName(username);
        if (user == null ) {
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

}
