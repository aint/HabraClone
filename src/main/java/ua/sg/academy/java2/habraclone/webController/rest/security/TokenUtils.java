package ua.sg.academy.java2.habraclone.webController.rest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ua.sg.academy.java2.habraclone.service.transactional.UserService;

@Component
public class TokenUtils {

    private final UserService userService;

    @Autowired
    public TokenUtils(UserService userService) {
        this.userService = userService;
    }

//    public String getToken(UserDetails userDetails) {
//
//    }
//
//    public String getToken(UserDetails userDetails, Long expiration) {
//
//    }

    public boolean validate(String token) {
        return token.matches("(.){3,}:(\\d){10}");
    }

    public UserDetails getUserFromToken(String token) {
        String username = token.split(":")[0];
        System.out.println("TOKEN USERNAME " + username);
        return userService.getByUserName(username);
    }
}
