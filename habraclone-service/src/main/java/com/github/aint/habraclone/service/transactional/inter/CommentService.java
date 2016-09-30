package com.github.aint.habraclone.service.transactional.inter;

import com.github.aint.habraclone.data.model.Comment;

public interface CommentService extends EntityService<Comment> {

    void createAndSave(String body, Long articleId, String authorUsername);

    void deleteComment(Long id);

}
