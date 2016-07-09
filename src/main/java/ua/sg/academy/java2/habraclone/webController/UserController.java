package ua.sg.academy.java2.habraclone.webController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.sg.academy.java2.habraclone.service.UserService;

@Controller
public class UserController {

    private static final String USERS_VIEW = "users";
    private static final String USERS_ATTRIBUTE = "USERS";

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/users")
    public ModelAndView showAllUsers() {
        return new ModelAndView(USERS_VIEW, USERS_ATTRIBUTE, userService.getAllUsersSortedAscByRating());
    }

}
