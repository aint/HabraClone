package ua.sg.academy.java2.habraclone.webController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.sg.academy.java2.habraclone.dbModel.entity.User;
import ua.sg.academy.java2.habraclone.service.UserService;

import java.util.List;
import java.util.Map;

@Controller
public class UsersController {

    private static final String USERS_VIEW = "users";
    private static final String USERS_ATTRIBUTE = "USERS";

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/users")
    public String users(Map<String, Object> model) {
        List<User> users = userService.getAllUsers();
        users.sort((u1, u2) -> u2.getRating() - u1.getRating());
        model.put(USERS_ATTRIBUTE, users);
        return USERS_VIEW;
    }

}
