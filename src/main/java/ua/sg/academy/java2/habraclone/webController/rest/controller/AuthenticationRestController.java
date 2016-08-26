package ua.sg.academy.java2.habraclone.webController.rest.controller;

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
import ua.sg.academy.java2.habraclone.model.User;
import ua.sg.academy.java2.habraclone.service.transactional.UserService;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

@RestController
@RequestMapping(value = "/api")
public class AuthenticationRestController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Autowired
    public AuthenticationRestController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<String> authenticate(@RequestParam("username") String username, @RequestParam("password") String password) {
        User user = userService.getByUserName(username);
        if (user == null) {
            return ResponseEntity
                    .badRequest()
                    .contentType(APPLICATION_JSON_UTF8)
                    .body(getJson("User not found"));
        }

        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    username, password, user.getAuthorities());
            authenticationManager.authenticate(token);
            return ResponseEntity
                    .ok()
                    .contentType(APPLICATION_JSON_UTF8)
                    .body(getJson(user.getUsername() + ":" + System.currentTimeMillis()));
        } catch (BadCredentialsException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .contentType(APPLICATION_JSON_UTF8)
                    .body(getJson("Bad Credentials"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(APPLICATION_JSON_UTF8)
                    .body(getJson(e.getMessage()));
        }
    }

    private String getJson(String message) {
        return new JSONObject().put("message", message).toString();
    }

}
