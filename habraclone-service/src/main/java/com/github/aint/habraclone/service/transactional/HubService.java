package com.github.aint.habraclone.service.transactional;

import com.github.aint.habraclone.data.model.Article;
import com.github.aint.habraclone.data.model.Hub;

import java.util.List;

public interface HubService extends EntityService<Hub> {

    List<Article> getAllArticlesOfHub(Long hubId);

}
