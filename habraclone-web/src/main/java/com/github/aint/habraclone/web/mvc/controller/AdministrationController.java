package com.github.aint.habraclone.web.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.github.aint.habraclone.data.model.User;
import com.github.aint.habraclone.service.transactional.inter.ArticleService;
import com.github.aint.habraclone.service.transactional.inter.CommentService;
import com.github.aint.habraclone.service.transactional.inter.UserService;

import static com.github.aint.habraclone.web.mvc.util.ConstantsHolder.ERROR_404;

@Controller
public class AdministrationController {

    private static final String SPRING_SECURITY_LAST_EXCEPTION = "SPRING_SECURITY_LAST_EXCEPTION";
    private static final String WRONG_EMAIL_OR_PASSWORD = "Wrong email or password";
    private static final String ACCOUNT_IS_LOCKED = "This account is locked";
    private static final String ACCOUNT_IS_NOT_ACTIVATED = "This account is not activated";
    private static final String ERROR_MSG = "error_msg";

    private final ArticleService articleService;
    private final CommentService commentService;
    private final UserService userService;

    @Autowired
    public AdministrationController(ArticleService articleService, CommentService commentService, UserService userService) {
        this.articleService = articleService;
        this.commentService = commentService;
        this.userService = userService;
    }

    @RequestMapping(value = "/users/{username}/ban")
    public ModelAndView banUser(@PathVariable("username") String username) {
        User user = userService.getByUserName(username);
        if (user == null) {
            return new ModelAndView(ERROR_404);
        }
        userService.banUser(user);
        return new ModelAndView("redirect:/users/" + username);
    }

    @RequestMapping(value = "/users/{username}/unban")
    public ModelAndView unbanUser(@PathVariable("username") String username) {
        User user = userService.getByUserName(username);
        if (user == null) {
            return new ModelAndView(ERROR_404);
        }
        userService.unbanUser(user);
        return new ModelAndView("redirect:/users/" + username);
    }

    @RequestMapping(value = "/comments/{id}/delete", method = RequestMethod.POST)
    public String deleteComment(@PathVariable("id") Long id, @RequestParam Long articleId) {
        if (id == null || id <= 0) {
            return ERROR_404;
        }
        commentService.deleteComment(id);
        return "redirect:/articles/" + articleId;
    }

    @RequestMapping(value = "/articles/{id}/delete")
    public String deleteArticle(@PathVariable("id") Long id) {
        if (id == null || id <= 0) {
            return ERROR_404;
        }
        articleService.deleteArticle(id);
        return "redirect:/";
    }

    @RequestMapping(value = "/admin/login")
    public ModelAndView adminEnter() {
        return new ModelAndView("admin");
    }

}
