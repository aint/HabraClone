package com.github.aint.habraclone.service.transactional;

import com.github.aint.habraclone.data.model.IEntity;

import java.util.List;
import java.util.Optional;

public interface EntityService<T extends IEntity> {

    /**
     * Returns an entity by the given primary {@code id}.
     *
     * @param id entity's primary key
     * @return the entity with the given {@code id}
     */
    Optional<T> getById(Long id);

    /**
     * Saves or update an entity in a data source.
     *
     * @param entity entity's instance
     * @return the persisted entity
     */
    T save(T entity);

    /**
     * Deletes an entity from a data source.
     *
     * @param entity entity's instance
     */
    void delete(T entity);

    /**
     * Deletes an entity with the given id from a data source.
     *
     * @param id entity's id
     */
    void delete(Long id);

    /**
     * Returns all entities.
     *
     * @return a list of entities
     */
    List<T> getAll();

    /**
     * Checks entity existence by the given primary {@code id}.
     *
     * @param id entity's primary key
     * @return {@code true} if an entity with the given {@code id} exists; {@code false} otherwise
     */
    boolean isExist(Long id);

    List<T> getAllSortedDeskByRating();

}
