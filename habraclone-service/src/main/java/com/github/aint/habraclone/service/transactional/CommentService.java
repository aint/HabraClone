package com.github.aint.habraclone.service.transactional;

import com.github.aint.habraclone.data.model.Comment;

public interface CommentService extends EntityService<Comment> {

    void createAndSave(String body, Long articleId);

    void deleteComment(Long id);

}
