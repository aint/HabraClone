package ua.sg.academy.java2.habraclone.service.transactional.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.sg.academy.java2.habraclone.dao.HubDao;
import ua.sg.academy.java2.habraclone.model.Article;
import ua.sg.academy.java2.habraclone.model.Hub;
import ua.sg.academy.java2.habraclone.service.transactional.HubService;

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
