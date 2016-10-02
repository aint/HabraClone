package com.github.aint.habraclone.web.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.github.aint.habraclone.service.transactional.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class SuccessfulAuthenticationHandler implements AuthenticationSuccessHandler {

    private static final String RETURN_URL_PARAMETER = "return-url";
    private final UserService userService;

    @Autowired
    public SuccessfulAuthenticationHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        clearAuthenticationAttributes(request);
        updateUserLoginTime(authentication);
        new DefaultRedirectStrategy().sendRedirect(request, response, getPreviousUrl(request));
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        Optional.ofNullable(request.getSession(false))
                .ifPresent(s -> s.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION));
    }

    private void updateUserLoginTime(Authentication authentication) {
        userService.updateLoginTime(((UserDetails) authentication.getPrincipal()).getUsername());

    }

    private String getPreviousUrl(HttpServletRequest request) {
        String url = request.getParameter(RETURN_URL_PARAMETER);
        return (url != null) ? url : "/";
    }

}
