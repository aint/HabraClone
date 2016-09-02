package com.github.aint.habraclone.service.transactional.inter;

import com.github.aint.habraclone.data.model.Article;

import java.util.List;

public interface HubService extends EntityService {

    List<Article> getAllArticlesOfHub(Long hubId);

}
