package com.github.aint.habraclone.service.transactional.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.aint.habraclone.data.dao.inter.CommentDao;
import com.github.aint.habraclone.data.model.Article;
import com.github.aint.habraclone.data.model.Comment;
import com.github.aint.habraclone.data.model.User;
import com.github.aint.habraclone.service.transactional.inter.ArticleService;
import com.github.aint.habraclone.service.transactional.inter.CommentService;
import com.github.aint.habraclone.service.transactional.inter.UserService;

import java.time.LocalDateTime;

@Service
@Transactional
public class CommentTransactionalService extends EntityTransactionalService implements CommentService {

    private final ArticleService articleService;
    private final UserService userService;

    @Autowired
    public CommentTransactionalService(CommentDao commentDao, ArticleService articleService, UserService userService) {
        super(commentDao);
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
        comment.setArticle((Article) (articleService.getById(articleId)));
        User author = userService.getByUserName(authorUsername);
        author.setCommentsCount(author.getCommentsCount() + 1);
        userService.update(author);
        comment.setAuthor(author);
        getDao().save(comment);
    }

    @Override
    public void deleteComment(Long id) {
        Comment comment = (Comment) getById(id);
        User author = comment.getAuthor();
        author.setCommentsCount(author.getCommentsCount() + 1);
        update(author);
        delete(comment);
    }

    @Override
    protected CommentDao getDao() {
        return (CommentDao) dao;
    }
}
