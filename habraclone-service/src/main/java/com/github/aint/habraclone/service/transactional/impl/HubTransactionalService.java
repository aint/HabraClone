package com.github.aint.habraclone.service.transactional.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.aint.habraclone.data.dao.inter.HubDao;
import com.github.aint.habraclone.data.model.Article;
import com.github.aint.habraclone.data.model.Hub;
import com.github.aint.habraclone.service.transactional.inter.HubService;

import java.util.List;

@Service
@Transactional
public class HubTransactionalService extends EntityTransactionalService implements HubService {

    @Autowired
    public HubTransactionalService(HubDao hubDao) {
        super(hubDao);
    }

    @Override
    protected HubDao getDao() {
        return (HubDao) dao;
    }

    @Override
    public List<Article> getAllArticlesOfHub(Long hubId) {
        if (hubId == null || hubId < 0) {
            throw new IllegalArgumentException("Hub id can't be null");
        }
        return ((Hub) getDao().getById(hubId)).getArticles();
    }
}
