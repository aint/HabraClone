package com.github.aint.habraclone.data.dao.inter;

import com.github.aint.habraclone.data.model.IEntity;

import java.util.List;

public interface GeneralDao {

    IEntity getById(long id);

    Long save(IEntity entity);

    void update(IEntity entity);

    void delete(IEntity entity);

    void deleteById(long id);

    List<? extends IEntity> getAll();

    List<? extends IEntity> getAllSortedDeskByRating();

}
