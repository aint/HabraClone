package com.github.aint.habraclone.web.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.github.aint.habraclone.service.transactional.CommentService;

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
        commentService.createAndSave(commentBody, articleId, getPrincipal());
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    TODO retrieve principal in service layer
    private String getPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (principal instanceof UserDetails
                ? ((UserDetails)principal).getUsername()
                : principal.toString());
    }

}
