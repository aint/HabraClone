package com.github.aint.habraclone.service.transactional.inter;

public interface CommentService extends EntityService {

    void createAndSave(String body, Long articleId, String authorUsername);

    void deleteComment(Long id);

}
