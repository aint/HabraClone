package com.github.aint.habraclone.service.transactional.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.aint.habraclone.data.repository.HubRepository;
import com.github.aint.habraclone.data.model.Article;
import com.github.aint.habraclone.data.model.Hub;
import com.github.aint.habraclone.service.transactional.HubService;

import java.util.List;

@Service
@Transactional
public class HubTransactionalService extends AbstractEntityTransactionalService<Hub> implements HubService {

    @Autowired
    public HubTransactionalService(HubRepository hubRepository) {
        super(hubRepository);
    }

    @Override
    protected HubRepository getRepository() {
        return (HubRepository) repository;
    }

    @Override
    public List<Article> getAllArticlesOfHub(Long hubId) {
        return (getRepository().findOne(hubId)).getArticles();
    }
}
