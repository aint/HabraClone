package com.github.aint.habraclone.service.transactional.inter;

import com.github.aint.habraclone.data.model.IEntity;

import java.util.List;

public interface EntityService<T extends IEntity> {

    /**
     * Returns an entity by the given primary {@code id}.
     *
     * @param id entity's primary key
     * @return the entity with the given {@code id} or {@code null} if not found
     */
    T getById(Long id);

    /**
     * Saves an entity in a data source.
     *
     * @param entity entity's instance
     */
    Long save(T entity);

    /**
     * Updates an entity in a data source.
     *
     * @param entity entity's instance
     */
    void update(T entity);

    /**
     * Deletes an entity from a data source.
     *
     * @param entity entity's instance
     */
    void delete(T entity);

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
