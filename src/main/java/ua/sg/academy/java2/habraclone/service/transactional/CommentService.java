package ua.sg.academy.java2.habraclone.service.transactional;

public interface CommentService extends EntityService {

    void createAndSave(String body, Long articleId, String authorUsername);

    void deleteComment(Long id);

}
