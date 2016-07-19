package ua.sg.academy.java2.habraclone.service.transactional;

import ua.sg.academy.java2.habraclone.model.Article;

import java.util.List;

public interface HubService extends EntityService {

    List<Article> getAllArticlesOfHub(Long hubId);

}
