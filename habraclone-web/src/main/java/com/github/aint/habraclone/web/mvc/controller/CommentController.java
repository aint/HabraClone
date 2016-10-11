package com.github.aint.habraclone.web.mvc.controller;

import com.github.aint.habraclone.service.transactional.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        commentService.createAndSave(commentBody, articleId);
        return "redirect:/" + ARTICLES_URL + articleId;
    }

}
