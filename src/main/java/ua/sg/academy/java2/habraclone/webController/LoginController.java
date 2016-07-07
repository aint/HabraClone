package ua.sg.academy.java2.habraclone.webController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.sg.academy.java2.habraclone.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private static final String USER_SESSION = "user_session";
    private static final String WRONG_EMAIL_OR_PASSWORD = "Wrong email or password";
    private static final String ERROR_MSG = "error_msg";

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam("email") String email, @RequestParam("password") String password,
                        HttpSession session, RedirectAttributes redirectAttributes) {

        if (userService.login(email, password)) {
            session.setAttribute(USER_SESSION, email);
        } else {
            redirectAttributes.addFlashAttribute(ERROR_MSG, WRONG_EMAIL_OR_PASSWORD);
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

}
