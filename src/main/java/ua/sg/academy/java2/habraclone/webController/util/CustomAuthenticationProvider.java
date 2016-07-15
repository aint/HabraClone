package ua.sg.academy.java2.habraclone.webController.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ua.sg.academy.java2.habraclone.service.transactional.TransactionalAdminAuthService;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private TransactionalAdminAuthService adminAuthService;

    @Autowired
    public CustomAuthenticationProvider(TransactionalAdminAuthService transactionalUserService) {
        this.adminAuthService = transactionalUserService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        UserDetails user = adminAuthService.loadUserByUsername(username);

        if (user == null || !user.getUsername().equalsIgnoreCase(username)) {
            throw new BadCredentialsException("");
        }
        if (!password.equals(user.getPassword())) {
            throw new BadCredentialsException("");
        }
        if (hasAdminRole(user.getAuthorities())) {
            throw new AccessDeniedException("");
        }

        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    }

    private boolean hasAdminRole(Collection<? extends GrantedAuthority> userRoles) {
        return userRoles.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet())
                .contains("ROLE_ADMIN");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}