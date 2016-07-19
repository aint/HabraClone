package ua.sg.academy.java2.habraclone.service.transactional.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.sg.academy.java2.habraclone.dao.CommentDao;
import ua.sg.academy.java2.habraclone.model.Article;
import ua.sg.academy.java2.habraclone.model.Comment;
import ua.sg.academy.java2.habraclone.model.User;
import ua.sg.academy.java2.habraclone.service.transactional.ArticleService;
import ua.sg.academy.java2.habraclone.service.transactional.CommentService;
import ua.sg.academy.java2.habraclone.service.transactional.UserService;

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
