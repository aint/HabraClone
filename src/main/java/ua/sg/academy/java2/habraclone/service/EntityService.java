package ua.sg.academy.java2.habraclone.service;

import ua.sg.academy.java2.habraclone.dbModel.entity.IEntity;

import java.util.List;

public interface EntityService {

    /**
     * Returns an entity by the given primary {@code id}.
     *
     * @param id entity's primary key
     * @return the entity with the given {@code id} or {@code null} if not found
     */
    IEntity getById(Long id);

    /**
     * Saves an entity in a data source.
     *
     * @param entity entity's instance
     */
    void save(IEntity entity);

    /**
     * Updates an entity in a data source.
     *
     * @param entity entity's instance
     */
    void update(IEntity entity);

    /**
     * Deletes an entity from a data source.
     *
     * @param entity entity's instance
     */
    void delete(IEntity entity);

    /**
     * Deletes an entity by the given primary {@code id}.
     *
     * @param id entity's primary key
     */
    void deleteById(Long id);

    /**
     * Returns all entities.
     *
     * @return a list of entities
     */
    List<? extends IEntity> getAll();

    /**
     * Checks entity existence by the given primary {@code id}.
     *
     * @param id entity's primary key
     * @return {@code true} if an entity with the given {@code id} exists; {@code false} otherwise
     */
    boolean isExist(Long id);

}
