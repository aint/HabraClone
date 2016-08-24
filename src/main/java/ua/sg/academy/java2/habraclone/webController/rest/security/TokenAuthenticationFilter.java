package ua.sg.academy.java2.habraclone.webController.rest.security;

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

    private final TokenUtils tokenUtils;

    @Autowired
    public TokenAuthenticationFilter(TokenUtils tokenUtils) {
        this.tokenUtils = tokenUtils;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getParameter("token");
        if (token != null) {
            if (tokenUtils.validate(token)) {
                UserDetails user = tokenUtils.getUserFromToken(token);
                System.out.println("TOKEN AUTH " + user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities()));
            } else {
                System.out.println("TOKEN NOT VALID");
            }
        } else {
            System.out.println("TOKEN NOT FOUND");
        }
        chain.doFilter(request, response);
    }
}
