package com.github.aint.habraclone.web.rest.controller;

import com.github.aint.habraclone.service.transactional.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class CommentRestController {

    private final CommentService commentService;

    @Autowired
    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @RequestMapping(value = "/comments/add", method = RequestMethod.POST)
    public ResponseEntity getArticleById(@RequestParam("comment_body") String commentBody,
                                         @RequestParam("article_id") Long articleId) {
        commentService.createAndSave(commentBody, articleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
