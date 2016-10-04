package com.github.aint.habraclone.service.transactional.impl;

import com.github.aint.habraclone.data.model.Role;
import com.github.aint.habraclone.data.model.User;
import com.github.aint.habraclone.service.transactional.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminAuthTransactionalService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public AdminAuthTransactionalService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByUserName(username);
        if (user == null ) {
            throw new UsernameNotFoundException("No such username");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                true, true, true,
                buildUserAuthority(user.getRole()));
    }

    private List<GrantedAuthority> buildUserAuthority(Role role) {
        return AuthorityUtils.createAuthorityList(role.name());
    }

}
