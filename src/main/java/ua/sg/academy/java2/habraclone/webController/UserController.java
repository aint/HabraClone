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
    private static final String USERS_ATTRIBUTE = "USERS";
    private static final String USER_ATTRIBUTE = "USER";
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

}
