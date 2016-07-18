package ua.sg.academy.java2.habraclone.dbModel.dao;

import ua.sg.academy.java2.habraclone.dbModel.entity.IEntity;

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
