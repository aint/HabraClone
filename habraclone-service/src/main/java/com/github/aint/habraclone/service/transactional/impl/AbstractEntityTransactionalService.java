package com.github.aint.habraclone.service.transactional.impl;

import com.github.aint.habraclone.data.model.IEntity;
import com.github.aint.habraclone.data.repository.GenericRepository;
import com.github.aint.habraclone.service.transactional.EntityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public abstract class AbstractEntityTransactionalService<T extends IEntity> implements EntityService<T> {

    protected GenericRepository<T, Long> repository;

    protected AbstractEntityTransactionalService(GenericRepository<T, Long> repository) {
        this.repository = repository;
    }

    @Override
    public Optional<T> getById(Long id) {
        return Optional.ofNullable(repository.findOne(id));
    }

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(T entity) {
        repository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);
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
