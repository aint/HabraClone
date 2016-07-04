package ua.sg.academy.java2.habraclone.dbModel.dao;

import ua.sg.academy.java2.habraclone.dbModel.entity.Article;
import ua.sg.academy.java2.habraclone.dbModel.entity.Comment;
import ua.sg.academy.java2.habraclone.dbModel.entity.User;

import java.util.List;

public interface CommentDao extends GeneralDao {

    /**
     * Returns all comments of the specified {@code user}.
     *
     * @param user to find comments for
     * @return all comments of the given {@code user}
     */
    List<Comment> getAllCommentsOfUser(User user);

    /**
     * Returns the latest comment of the specified {@code user}.
     *
     * @param user to find comments for
     * @return the latest comment of the given {@code user} or {@code null} if the {@code user} has no comments
     */
    Comment getLatestCommentOfUser(User user);

}
