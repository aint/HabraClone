package com.github.aint.habraclone.web.mvc.controller;

import com.github.aint.habraclone.service.transactional.ArticleService;
import com.github.aint.habraclone.service.transactional.CommentService;
import com.github.aint.habraclone.service.transactional.UserService;
import com.github.aint.habraclone.web.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdministrationController {

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
        userService.banUser(userService.getByUserName(username).orElseThrow(ResourceNotFoundException::new));
        return new ModelAndView("redirect:/users/" + username);
    }

    @RequestMapping(value = "/users/{username}/unban")
    public ModelAndView unbanUser(@PathVariable("username") String username) {
        userService.unbanUser(userService.getByUserName(username).orElseThrow(ResourceNotFoundException::new));
        return new ModelAndView("redirect:/users/" + username);
    }

    @RequestMapping(value = "/comments/{id}/delete", method = RequestMethod.POST)
    public String deleteComment(@PathVariable("id") Long id, @RequestParam Long articleId) {
        commentService.deleteComment(id);
        return "redirect:/articles/" + articleId;
    }

    @RequestMapping(value = "/articles/{id}/delete")
    public String deleteArticle(@PathVariable("id") Long id) {
        articleService.deleteArticle(id);
        return "redirect:/";
    }

    @RequestMapping(value = "/admin/login")
    public ModelAndView adminEnter() {
        return new ModelAndView("admin");
    }

}
