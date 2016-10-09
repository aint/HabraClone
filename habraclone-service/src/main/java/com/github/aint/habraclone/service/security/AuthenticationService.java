package com.github.aint.habraclone.service.security;

import com.github.aint.habraclone.data.model.User;

public interface AuthenticationService {

    User getPrincipal();

}
