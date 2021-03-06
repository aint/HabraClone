package com.github.aint.habraclone.web.rest.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Component
public class TokenAuthenticationFilter extends GenericFilterBean {
    private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationFilter.class.getName());
    private static final String TOKEN_PARAMETER = "token";

    private final TokenHelper tokenHelper;

    @Autowired
    public TokenAuthenticationFilter(TokenHelper tokenHelper) {
        this.tokenHelper = tokenHelper;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getParameter(TOKEN_PARAMETER);
        if (tokenHelper.validate(token)) {
            authenticateUser(token);
        } else {
            logger.info("Token not valid");
        }

        chain.doFilter(request, response);
    }

    private void authenticateUser(String token) {
        UserDetails user = tokenHelper.getUserFromToken(token);
        logger.info("User '{}' {} authorized successful", user.getUsername(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities()));
    }
}
