package com.github.aint.habraclone.web.rest.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.github.aint.habraclone.data.model.User;
import com.github.aint.habraclone.service.transactional.inter.UserService;
import com.github.aint.habraclone.web.rest.security.TokenHelper;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

@RestController
@RequestMapping(value = "/api")
public class AuthenticationRestController {

    private static final String USER_NOT_FOUND_MESSAGE = "User not found";
    private static final String BAD_CREDENTIALS_MASSAGE = "Bad Credentials";
    private static final String MESSAGE_FIELD = "message";

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final TokenHelper tokenHelper;

    @Autowired
    public AuthenticationRestController(AuthenticationManager authenticationManager, UserService userService, TokenHelper tokenHelper) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.tokenHelper = tokenHelper;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<String> authenticate(@RequestParam("username") String username, @RequestParam("password") String password) {
        User user = userService.getByUserName(username);
        if (user == null) {
            return ResponseEntity
                    .badRequest()
                    .contentType(APPLICATION_JSON_UTF8)
                    .body(getJson(USER_NOT_FOUND_MESSAGE));
        }
        return tryToAuthenticate(user, password);
    }

    private ResponseEntity<String> tryToAuthenticate(User user, String password) {
        try {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), password, user.getAuthorities());
            authenticationManager.authenticate(token);
            return ResponseEntity
                    .ok()
                    .contentType(APPLICATION_JSON_UTF8)
                    .body(getJson(tokenHelper.getToken(user.getUsername())));
        } catch (BadCredentialsException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .contentType(APPLICATION_JSON_UTF8)
                    .body(getJson(BAD_CREDENTIALS_MASSAGE));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(APPLICATION_JSON_UTF8)
                    .body(getJson(e.getMessage()));
        }
    }

    private String getJson(String message) {
        return new JSONObject().put(MESSAGE_FIELD, message).toString();
    }

}
