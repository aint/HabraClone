package ua.sg.academy.java2.habraclone.service;

public interface CommentService extends EntityService {

    void createAndSave(String body, Long articleId, String authorEmail);

}
