package com.github.aint.habraclone.service.transactional.impl;

import com.github.aint.habraclone.data.model.IEntity;
import com.github.aint.habraclone.data.repository.GenericRepository;
import com.github.aint.habraclone.service.transactional.EntityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public abstract class AbstractEntityTransactionalService<T extends IEntity> implements EntityService<T> {

    private static final String ENTITY_CANT_BE_NULL = "Entity can't be null";
    private static final String ENTITY_ID_CANT_BE_NULL = "Entity id can't be null";

    protected GenericRepository<T, Long> repository;

    protected AbstractEntityTransactionalService(GenericRepository<T, Long> repository) {
        this.repository = repository;
    }

    @Override
    public T getById(Long id) {
        if (id == null || id < 1) {
            throw new IllegalArgumentException(ENTITY_ID_CANT_BE_NULL);
        }
        return repository.findOne(id);
    }

    @Override
    public Long save(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException(ENTITY_CANT_BE_NULL);
        }
        return repository.save(entity).getId();
    }

    @Override
    public void delete(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException(ENTITY_CANT_BE_NULL);
        }
        repository.delete(entity);
    }

    @Override
    public List<T> getAll() {
        List<T> entities = new ArrayList<>();
        repository.findAll().forEach(entities::add);
        return entities;
    }

    @Override
    public boolean isExist(Long id) {
        return repository.exists(id);
    }

    @Override
    public List<T> getAllSortedDeskByRating() {
        return repository.findByOrderByRatingDesc();
    }

    protected abstract GenericRepository<T, Long> getRepository();

}
