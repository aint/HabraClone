package ua.sg.academy.java2.habraclone.webController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.sg.academy.java2.habraclone.dbModel.entity.User;
import ua.sg.academy.java2.habraclone.service.UserService;

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

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/users")
    public ModelAndView showAllUsers() {
        return new ModelAndView(USERS_VIEW, USERS_ATTRIBUTE, userService.getAllUsersSortedAscByRating());
    }

    @RequestMapping(value = "/users/{username}")
    public String userProfile(@PathVariable("username") String username, Model model) {
        User user = userService.getByUserName(username);
        model.addAttribute(USER_ATTRIBUTE, user);
        model.addAttribute(USER_POSITION_ATTRIBUTE, userService.getPositionByRating(user));
        return USER_PROFILE_VIEW;
    }

    @RequestMapping(value = "/users/{username}/articles")
    public ModelAndView userArticles(@PathVariable("username") String username, Model model) {
        return new ModelAndView(USER_ARTICLES_VIEW, USER_ATTRIBUTE, userService.getByUserName(username));
    }

    @RequestMapping(value = "/users/{username}/favorites")
    public String userFavorites(@PathVariable("username") String username, Model model) {
        model.addAttribute(USER_ATTRIBUTE, userService.getByUserName(username));
        model.addAttribute(FAVORITES_ATTRIBUTE, true);
        return USER_ARTICLES_VIEW;
    }

    @RequestMapping(value = "/users/{username}/comments")
    public ModelAndView userComments(@PathVariable("username") String username, Model model) {
        return new ModelAndView(USER_COMMENTS_VIEW, USER_ATTRIBUTE, userService.getByUserName(username));
    }

}
