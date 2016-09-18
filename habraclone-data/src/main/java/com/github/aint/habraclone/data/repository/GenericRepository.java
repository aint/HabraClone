package com.github.aint.habraclone.data.repository;

import com.github.aint.habraclone.data.model.IEntity;

import java.util.List;

public interface GenericRepository<T extends IEntity> {

    List<T> findByOrderByRatingDesc();

}
