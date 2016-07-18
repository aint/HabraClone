package ua.sg.academy.java2.habraclone.service;

import ua.sg.academy.java2.habraclone.dbModel.entity.Article;

import java.util.List;

public interface HubService extends EntityService {

    List<Article> getAllArticlesOfHub(Long hubId);

}
