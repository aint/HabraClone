package ua.sg.academy.java2.habraclone.webController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.sg.academy.java2.habraclone.service.CommentService;

@Controller
public class CommentController {

    private static final String ARTICLES_URL = "articles/";

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @RequestMapping(value = "/add-comment")
    public String addComment(@RequestParam("comment_body") String commentBody,
                             @RequestParam("article_id") long articleId,
                             @RequestParam("user_email") String userEmail) {
        System.out.println(commentBody);
        commentService.createAndSave(commentBody, articleId, userEmail);
        return "redirect:/" + ARTICLES_URL + articleId;
    }

}
