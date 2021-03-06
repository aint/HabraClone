package com.github.aint.habraclone.web.mvc.controller;

import com.github.aint.habraclone.data.model.User;
import com.github.aint.habraclone.service.dto.UserForm;
import com.github.aint.habraclone.service.transactional.UserService;
import com.github.aint.habraclone.web.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    private static final String USERS_VIEW = "users";
    private static final String USER_PROFILE_VIEW = "userProfile";
    private static final String USER_ARTICLES_VIEW = "userArticles";
    private static final String USER_COMMENTS_VIEW = "userComments";
    private static final String USERS_ATTRIBUTE = "USERS";
    private static final String USER_ATTRIBUTE = "USER";
    private static final String FAVORITES_ATTRIBUTE = "FAVORITES";
    private static final String USER_POSITION_ATTRIBUTE = "USER_POSITION";

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @RequestMapping(value = "/users")
    public ModelAndView showAllUsers() {
        return new ModelAndView(USERS_VIEW, USERS_ATTRIBUTE, userService.getAllSortedDeskByRating());
    }

    @RequestMapping(value = "/users/{username}/activate")
    public ModelAndView activateUser(@PathVariable("username") String username, HttpServletRequest request) {
        User user = userService.getByUserName(username).orElseThrow(ResourceNotFoundException::new);
        userService.activate(user);
        authenticateUserAndSetSession(user, request);
        return new ModelAndView(USER_PROFILE_VIEW, USER_ATTRIBUTE, user);
    }

    @RequestMapping(value = "/users/{username}")
    public String userProfile(@PathVariable("username") String username, Model model) {
        User user = userService.getByUserName(username).orElseThrow(ResourceNotFoundException::new);
        model.addAttribute(USER_ATTRIBUTE, user);
        model.addAttribute(USER_POSITION_ATTRIBUTE, userService.getPositionByRating(user));
        return USER_PROFILE_VIEW;
    }

    @RequestMapping(value = "/users/{username}/articles")
    public ModelAndView userArticles(@PathVariable("username") String username) {
        return new ModelAndView(USER_ARTICLES_VIEW,
                                USER_ATTRIBUTE,
                                userService.getByUserName(username).orElseThrow(ResourceNotFoundException::new));
    }

    @RequestMapping(value = "/users/{username}/favorites")
    public String userFavorites(@PathVariable("username") String username, Model model) {
        User user = userService.getByUserName(username).orElseThrow(ResourceNotFoundException::new);
        model.addAttribute(USER_ATTRIBUTE, user);
        model.addAttribute(FAVORITES_ATTRIBUTE, true);
        return USER_ARTICLES_VIEW;
    }

    @RequestMapping(value = "/users/{username}/comments")
    public ModelAndView userComments(@PathVariable("username") String username) {
        return new ModelAndView(USER_COMMENTS_VIEW,
                                USER_ATTRIBUTE,
                                userService.getByUserName(username).orElseThrow(ResourceNotFoundException::new));
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public String register(@ModelAttribute("userForm") UserForm userForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/";
        }
        userService.register(userForm);
        return "redirect:/";
    }

    private void authenticateUserAndSetSession(User user, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        request.getSession();
        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }

}
