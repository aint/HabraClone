package com.github.aint.habraclone.data.dao.inter;

import com.github.aint.habraclone.data.model.Comment;
import com.github.aint.habraclone.data.model.User;

import java.util.List;

public interface CommentDao extends GeneralDao {

    /**
     * Returns all comments of the specified {@code user}.
     *
     * @param user to find comments for
     * @return all comments of the given {@code user}
     */
    List<Comment> getAllCommentsOfUser(User user);

}
