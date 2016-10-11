package com.github.aint.habraclone.web.mvc.controller;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    private static final String SPRING_SECURITY_LAST_EXCEPTION = "SPRING_SECURITY_LAST_EXCEPTION";
    private static final String WRONG_EMAIL_OR_PASSWORD = "Wrong email or password";
    private static final String ACCOUNT_IS_LOCKED = "This account is banned";
    private static final String ACCOUNT_IS_NOT_ACTIVATED = "This account is not activated";
    private static final String ERROR_MSG = "error_msg";

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam(value = "error", required = false) String error,
                        HttpServletRequest request, RedirectAttributes redirectAttributes) {

        if (error != null) {
            redirectAttributes.addFlashAttribute(ERROR_MSG, getErrorMessage(request, SPRING_SECURITY_LAST_EXCEPTION));
        }

        return "redirect:/";
    }

    private String getErrorMessage(HttpServletRequest request, String key) {
        Exception exception = (Exception) request.getSession().getAttribute(key);

        if (exception instanceof LockedException) {
            return ACCOUNT_IS_LOCKED;
        } else if (exception instanceof DisabledException) {
            return ACCOUNT_IS_NOT_ACTIVATED;
        }
        return WRONG_EMAIL_OR_PASSWORD;
    }

}
