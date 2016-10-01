package com.github.aint.habraclone.service.transactional.impl;

import com.github.aint.habraclone.data.model.Comment;
import com.github.aint.habraclone.data.model.User;
import com.github.aint.habraclone.data.repository.CommentRepository;
import com.github.aint.habraclone.service.transactional.inter.ArticleService;
import com.github.aint.habraclone.service.transactional.inter.CommentService;
import com.github.aint.habraclone.service.transactional.inter.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class CommentTransactionalService extends EntityTransactionalService<Comment> implements CommentService {

    private final ArticleService articleService;
    private final UserService userService;

    @Autowired
    public CommentTransactionalService(CommentRepository commentRepository, ArticleService articleService, UserService userService) {
        super(commentRepository);
        this.articleService = articleService;
        this.userService = userService;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void createAndSave(String body, Long articleId, String authorUsername) {
        Comment comment = new Comment();
        comment.setBody(body);
        comment.setRating(0);
        comment.setCreationDate(LocalDateTime.now());
        comment.setArticle((articleService.getById(articleId)));
        User author = userService.getByUserName(authorUsername);
        author.setCommentsCount(author.getCommentsCount() + 1);
        userService.save(author);
        comment.setAuthor(author);
        getRepository().save(comment);
    }

    @Override
    public void deleteComment(Long id) {
        Comment comment = getById(id);
        User author = comment.getAuthor();
        author.setCommentsCount(author.getCommentsCount() + 1);
        userService.save(author);
        delete(comment);
    }

    @Override
    protected CommentRepository getRepository() {
        return (CommentRepository) repository;
    }
}
