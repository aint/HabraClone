package ua.sg.academy.java2.habraclone.service.transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.sg.academy.java2.habraclone.dbModel.dao.HubDao;
import ua.sg.academy.java2.habraclone.dbModel.entity.Article;
import ua.sg.academy.java2.habraclone.dbModel.entity.Hub;
import ua.sg.academy.java2.habraclone.service.HubService;

import java.util.List;

@Service
@Transactional
public class TransactionalHubService extends TransactionalEntityService implements HubService {

    @Autowired
    public TransactionalHubService(HubDao hubDao) {
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
