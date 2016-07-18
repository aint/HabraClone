package ua.sg.academy.java2.habraclone.service.transactional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.sg.academy.java2.habraclone.dbModel.dao.GeneralDao;
import ua.sg.academy.java2.habraclone.dbModel.entity.IEntity;
import ua.sg.academy.java2.habraclone.service.EntityService;

import java.util.List;

@Service
@Transactional
public abstract class TransactionalEntityService implements EntityService {

    private static final String ENTITY_CANT_BE_NULL = "Entity can't be null";
    private static final String ENTITY_ID_CANT_BE_NULL = "Entity id can't be null";

    protected GeneralDao dao;

    protected TransactionalEntityService(GeneralDao dao) {
        this.dao = dao;
    }

    @Override
    public IEntity getById(Long id) {
        if (id == null || id < 1) {
            throw new IllegalArgumentException(ENTITY_ID_CANT_BE_NULL);
        }
        return dao.getById(id);
    }

    @Override
    public Long save(IEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException(ENTITY_CANT_BE_NULL);
        }
        return dao.save(entity);
    }

    @Override
    public void update(IEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException(ENTITY_CANT_BE_NULL);
        }
        dao.update(entity);
    }

    @Override
    public void delete(IEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException(ENTITY_CANT_BE_NULL);
        }
        dao.delete(entity);
    }

    @Override
    public List<? extends IEntity> getAll() {
        return dao.getAll();
    }

    @Override
    public boolean isExist(Long id) {
        return dao.getById(id) != null;
    }

    @Override
    public List<? extends IEntity> getAllSortedDeskByRating() {
        return dao.getAllSortedDeskByRating();
    }

    protected abstract GeneralDao getDao();

}
