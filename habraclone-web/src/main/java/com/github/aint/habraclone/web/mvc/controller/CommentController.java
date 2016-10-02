package com.github.aint.habraclone.web.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.github.aint.habraclone.service.transactional.CommentService;

@Controller
public class CommentController {

    private static final String ARTICLES_URL = "articles/";

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @RequestMapping(value = "/comments/add")
    public String addComment(@RequestParam("comment_body") String commentBody,
                             @RequestParam("article_id") long articleId) {
        commentService.createAndSave(commentBody, articleId, getPrincipal());
        return "redirect:/" + ARTICLES_URL + articleId;
    }

    private String getPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (principal instanceof UserDetails
                ? ((UserDetails)principal).getUsername()
                : principal.toString());
    }

}
