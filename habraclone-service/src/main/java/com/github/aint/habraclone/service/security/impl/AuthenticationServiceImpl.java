package com.github.aint.habraclone.service.security.impl;

import com.github.aint.habraclone.data.model.User;
import com.github.aint.habraclone.service.security.AuthenticationService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Override
    public User getPrincipal() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
