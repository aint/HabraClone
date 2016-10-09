package com.github.aint.habraclone.service.transactional.impl;

import com.github.aint.habraclone.data.model.Comment;
import com.github.aint.habraclone.data.model.User;
import com.github.aint.habraclone.data.repository.CommentRepository;
import com.github.aint.habraclone.service.transactional.ArticleService;
import com.github.aint.habraclone.service.security.AuthenticationService;
import com.github.aint.habraclone.service.transactional.CommentService;
import com.github.aint.habraclone.service.transactional.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@Transactional
public class CommentTransactionalService extends AbstractEntityTransactionalService<Comment> implements CommentService {

    private final AuthenticationService authenticationService;
    private final ArticleService articleService;
    private final UserService userService;

    @Autowired
    public CommentTransactionalService(CommentRepository commentRepository, AuthenticationService authenticationService,
                                       ArticleService articleService, UserService userService) {
        super(commentRepository);
        this.authenticationService = authenticationService;
        this.articleService = articleService;
        this.userService = userService;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void createAndSave(String body, Long articleId) {
        Comment comment = new Comment();
        comment.setBody(body);
        comment.setRating(0);
        comment.setCreationDate(LocalDateTime.now());
        comment.setArticle((articleService.getById(articleId))
                .orElseThrow(() -> new NoSuchElementException(String.format("Article=%s not found", articleId))));
        User author = authenticationService.getPrincipal();
        author.setCommentsCount(author.getCommentsCount() + 1);
        userService.save(author);
        comment.setAuthor(author);
        getRepository().save(comment);
    }

    @Override
    public void deleteComment(Long id) {
        Comment comment = getById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("Comment=%s not found", id)));
        User author = comment.getAuthor();
        author.setCommentsCount(author.getCommentsCount() - 1);
        userService.save(author);
        delete(comment);
    }

    @Override
    protected CommentRepository getRepository() {
        return (CommentRepository) repository;
    }
}
