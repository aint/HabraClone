package com.github.aint.habraclone.data.repository;

import com.github.aint.habraclone.data.model.Comment;
import com.github.aint.habraclone.data.model.User;

import java.util.List;

public interface CommentRepository extends GenericRepository<Comment, Long> {

    /**
     * Returns all comments of the specified {@code user}.
     *
     * @param author to find comments for
     * @return all comments of the given {@code author}
     */
    List<Comment> findByAuthor(User author);

}
