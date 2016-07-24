package ua.sg.academy.java2.habraclone.service.transactional.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ua.sg.academy.java2.habraclone.model.User;
import ua.sg.academy.java2.habraclone.model.UserRole;
import ua.sg.academy.java2.habraclone.service.transactional.UserService;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AdminAuthTransactionalService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public AdminAuthTransactionalService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {;
        User user = userService.getByUserName(username);
        if (user == null ) {
            throw new UsernameNotFoundException("No such username");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                true, true, true,
                buildUserAuthority(user.getRoles()));
    }

    private Set<GrantedAuthority> buildUserAuthority(Set<UserRole> roles) {
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority(r.getRole()))
                .collect(Collectors.toSet());
    }

}
